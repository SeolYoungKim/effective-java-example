package chapter_11.item81;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// https://codechacha.com/ko/java-countdownlatch/
public class CountDownLatchEx {
    public static void main(String[] args) throws InterruptedException {
//        notProceed();
//        proceed();
//        awaitUntilTheEndOfOtherThread();
//        awaitUntilReadyForOtherThread();
        awaitOnlyDecidedTime();
    }

    private static void notProceed() throws InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(5);
        countDownLatch.countDown();  // 호출 시 Latch의 숫자가 1씩 감소함
        countDownLatch.await();  // Latch의 숫자가 0이 될 때까지 기다림
        System.out.println("여기는 실행이 안됨");
    }

    private static void proceed() throws InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(5);
        for (int i = 0; i < 5; i++) {
            countDownLatch.countDown();  // 호출 시 Latch의 숫자가 1씩 감소함
        }
        countDownLatch.await();  // Latch의 숫자가 0이 될 때까지 기다림
        System.out.println("이제는 실행이 됨");
    }

    private static void awaitUntilTheEndOfOtherThread() throws InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(5);
        final List<Thread> workers = Stream.generate(() -> new Thread(new Worker(countDownLatch)))
                .limit(5)
                .collect(Collectors.toList());

        System.out.printf("[%s] Start multi threads%n", Thread.currentThread().getName());
        workers.forEach(Thread::start);  // 여기서 작업이 끝날 때 까지

        System.out.printf("[%s] Waiting for some work to be finished%n", Thread.currentThread().getName());
        countDownLatch.await();  // 기다린다

        System.out.printf("[%s] Finished%n", Thread.currentThread().getName());
    }

    private static class Worker implements Runnable {
        private final CountDownLatch countDownLatch;

        private Worker(final CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            System.out.printf("[%s] Do Something...%n", Thread.currentThread().getName());
            countDownLatch.countDown();
        }
    }

    private static void awaitUntilReadyForOtherThread() throws InterruptedException {
        final CountDownLatch ready = new CountDownLatch(5);
        final CountDownLatch start = new CountDownLatch(1);
        final CountDownLatch finish = new CountDownLatch(5);

        final List<Thread> workers = Stream
                .generate(() -> new Thread(new Worker2(ready, start, finish)))
                .limit(5)
                .collect(Collectors.toList());

        System.out.printf("[%s] Start multi threads%n", Thread.currentThread().getName());
        workers.forEach(Thread::start);
        ready.await();  // 모든 스레드가 준비될 때까지 대기

        System.out.printf("[%s] Waited for ready and started doing some work%n", Thread.currentThread().getName());
        start.countDown();  // 시작 신호 !

        finish.await();  // 모든 스레드가 끝날 때까지 대기
        System.out.printf("[%s] Finished%n", Thread.currentThread().getName());

    }

    private static class Worker2 implements Runnable {
        private final CountDownLatch ready;
        private final CountDownLatch start;
        private final CountDownLatch finish;

        private Worker2(final CountDownLatch ready, final CountDownLatch start,
                final CountDownLatch finish) {
            this.ready = ready;
            this.start = start;
            this.finish = finish;
        }

        @Override
        public void run() {
            ready.countDown();

            try {
                start.await();  // start가 0이 될때까지 대기 (즉, start.countDown()이 호출될 때까지 대기)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.printf("[%s] Do Something...%n", Thread.currentThread().getName());
            finish.countDown();
        }
    }

    private static void awaitOnlyDecidedTime() throws InterruptedException {
        // 어떤 스레드가 작업을 완료하지 못할 경우, countDown() 호출이 안되어 메인 스레드가 무한 대기 할수도 있음
        // await()에 Timeout을 설정하면 해결 가능
        final CountDownLatch countDownLatch = new CountDownLatch(5);
        final List<Thread> workers = Stream.generate(() -> new Thread(new Worker3(countDownLatch)))
                .limit(5)
                .collect(Collectors.toList());

        System.out.printf("[%s] Start multi threads%n", Thread.currentThread().getName());
        workers.forEach(Thread::start);

        System.out.printf("[%s] Waiting for some work to be finished%n", Thread.currentThread().getName());
        countDownLatch.await(5, TimeUnit.SECONDS);  // 단 5초만 기다린 후

        System.out.printf("[%s] Finished%n", Thread.currentThread().getName());  // 해당 메서드를 수행
    }

    private static class Worker3 implements Runnable {
        private final CountDownLatch countDownLatch;

        private Worker3(final CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            System.out.printf("[%s] Do Something...%n", Thread.currentThread().getName());

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            countDownLatch.countDown();
            System.out.printf("[%s] Done%n", Thread.currentThread().getName());
        }
    }

}
