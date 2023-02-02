package chapter_11.item81;

import java.util.concurrent.Semaphore;

public class CountingSemaphoreTest {
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Library library = new Library(); // 공유객체 도서관 생성
        Thread[] reader = new Thread[10]; // 접근 스레드 회원 10명 생성


        for(int i = 0; i < reader.length; i++) {

            reader[i] = new Thread(new Reader(library)); // 회원 10명 생성
            reader[i].setName("회원" + (i+1)); //회원 스레드 이름 설정
            reader[i].start(); // 회원 스레드 실행

        }

        try {
            for (final Thread thread : reader) {
                thread.join(); // 회원스레드 일시정지
            }
        }catch(InterruptedException ignored) {}
    }

    private static class Library {
        private final int MAX_PERMIT = 3; // S는 3, 공유 자원 3개
        private Book[] books = {new Book("데미안_1"), new Book("데미안_2"),
                new Book("데미안_3")}; //도서관에 데미안 책이 총 3권 있다.
        private boolean[] beingRead = new boolean[MAX_PERMIT];// 데미안 3권의 책의 대출여부를 알려주는 배열
        private Semaphore s = new Semaphore(MAX_PERMIT, true); // 세마포어 객체 생성 (Counting Semaphore)

        // 책 대출하기
        public Book checkOut() throws InterruptedException {

            s.acquire(); // 대출이 가능한지 세마포어 동기화 검사
            //가능하면 임계영역 접근, 불가능하면 대기

            //------------ 임계영역 시작 -------------//

            return getAvailableBook(); // 대출 메소드 호출
        }

        //대출 메소드
        public Book getAvailableBook() {
            Book book = null;

            for (int i = 0; i < MAX_PERMIT; i++) {
                if (!beingRead[i]) {//데미안 3권 중 이용가능한 책 탐색
                    beingRead[i] = true; // true를 넣어 '대출중'으로 바꿈
                    book = books[i]; // 해당 책의 객체주소 획득
                    System.out.println(
                            Thread.currentThread().getName() + "님 대출 완료 : " + book.getName());
                    break;
                }
            }
            return book; // 대출한 책의 객체 주소 획득
        }

        //책 반납하기
        public void returnBook(Book book) {

            if (getAvailable(book)) { // 책 대출가능 상태로 돌려놓는 메소드 호출
                s.release(); // Lock 해제 공유 자원 내려놓기
                //--------------------- 임계영역 끝 -------------------------//
            }
        }

        // 책 대출가능 상태로 돌려놓기
        public boolean getAvailable(Book book) {

            for (int i = 0; i < MAX_PERMIT; i++) {
                if (book == books[i]) { // 내가 대출했던 책 탐색
                    beingRead[i] = false; //false를 넣어 반납되어 대출가능한 상태임을 표시
                    System.out.println(
                            Thread.currentThread().getName() + "님 반납완료 : " + book.getName());
                    break;
                }
            }
            return true; // 완료
        }
    }

    private static class Reader implements Runnable {
        Library library;
        Book book;

        public Reader(Library library) {
            this.library = library; // 공유 객체 주소 획득
        }

        @Override
        public void run() {

            try {
                book = library.checkOut(); // acquire() 호출
                System.out.println(Thread.currentThread().getName() + "님이 독서 중입니다.");
                Thread.sleep(1000);
                library.returnBook(book); // release() 호출

            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private static class Book {
        private String name;

        public Book(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
