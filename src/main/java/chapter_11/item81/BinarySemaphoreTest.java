package chapter_11.item81;

import java.util.concurrent.Semaphore;

//https://lordofkangs.tistory.com/27
public class BinarySemaphoreTest {
    private static final int LOOP = 10_000;  // 각 스레드에 대한 반복 접근 횟수

    public static void main(String[] args) throws InterruptedException {
        // TODO Auto-generated method stub

        Semaphore s = new Semaphore(1, true); //세마포어 객체생성 (permit = 1 : 공유자원 1개, fair = true : FIFO)
        Account account = new Account(s); // 공유객체 생성

        //스레드 생성
        Thread depositThread1 = new Thread(new Deposit(account));
        Thread withDrawThread1 = new Thread(new WithDraw(account));

        Thread depositThread2 = new Thread(new Deposit(account));
        Thread withDrawThread2 = new Thread(new WithDraw(account));

        //스레드 이름 설정
        depositThread1.setName("지훈 입금");
        withDrawThread1.setName("지훈 출금");

        depositThread2.setName("민정 입금");
        withDrawThread2.setName("민정 출금");

        //스레드 실행
        depositThread1.start();
        withDrawThread1.start();

        depositThread2.start();
        withDrawThread2.start();

        //스레드 정지
        try {
            depositThread1.join();
            withDrawThread1.join();

            depositThread2.join();
            withDrawThread2.join();

        } catch (InterruptedException ignored) {
        }

        //잔액 출력
        account.printBalance();
    }

    private static void methodInformation() throws InterruptedException {
        final Semaphore randomSemaphore = new Semaphore(1);  // 대기열에서 자고있는 스레드를 랜덤으로 깨움
        final Semaphore semaphore = new Semaphore(1, true);  // 대기열에서 자고있는 스레드를 FIFO로 깨움

        semaphore.acquire();  // lock을 획득하는 operation을 실행 -> permits 검사 -> 1이면 임계 영역 진입, 0이면 대기열에 스레드 넣음
        semaphore.release();  // lock을 내려놓는 operation을 실행 -> 자원을 내려놓고 대기 리스트의 스레드를 하나 깨움
    }

    private static class Account {
        private int balance = 0; // 잔액
        Semaphore s; // 세마포어 객체 참조변수

        public Account(Semaphore s) { // 생성자
            this.s = s;
        }

        public void deposit(int money) {
            try {
                s.acquire(); // 세마포어 객체를 통한 동기화 검사

                // 임계 영역(critical section)
                System.out.println(Thread.currentThread().getName() + " : " + money + "원");

                balance += money;

                System.out.println("현재 잔액 : " + balance + "원");
                System.out.println();

                s.release(); // Lock 해제
            } catch (InterruptedException ignored) {
            }

        }

        public void withDraw(int money) {
            try {
                s.acquire();  // 세마포어 객체를 통한 동기화 검사

                //임계영역
                System.out.println(Thread.currentThread().getName() + " : " + money + "원");

                balance -= money;

                System.out.println("현재 잔액 : " + balance + "원");
                System.out.println();

                s.release(); // Lock 해제

            } catch (InterruptedException ignored) {
            }
        }

        // 계좌 속 잔액 출력
        public void printBalance() {
            System.out.println("현재 잔액 : " + balance);
        }
    }

    private static class Deposit implements Runnable {

        Account account; // 공유객체

        public Deposit(Account account) {
            this.account = account;
        }

        @Override
        public void run() {
            for (int i = 0; i < LOOP; i++) {
                account.deposit(10); // Main.Loop 만큼 접근하여 10원 입금
            }
        }

    }

    private static class WithDraw implements Runnable {
        Account account; // 공유 객체

        public WithDraw(Account account) {
            this.account = account;
        }

        @Override
        public void run() {

            for (int i = 0; i < LOOP; i++) {
                account.withDraw(10); // 10원 입금
            }
        }
    }
}
