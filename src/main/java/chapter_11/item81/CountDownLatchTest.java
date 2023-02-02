package chapter_11.item81;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CountDownLatchTest {
    public static void main(String[] args) throws InterruptedException {
        final long result = time(Executors.newFixedThreadPool(10), 10, () -> System.out.println("안녕"));
        System.out.println(result);
    }

    /**
     * - 어떤 동작들을 동시에 시작해 모두 완료하기 까지의 시간을 재는 간단한 프레임워크<br/>
     *   - 동작들을 실행할 Executor, 동작을 몇 개나 동시에 수행할 수 있는지를 뜻하는 동시성 수준, 실행할 행동을 매개변수로 받음 <br/>
     * - 타이머 스레드가 시계를 시작하기 전에, 모든 작업자 스레드는 동작 수행 준비를 마침 <br/>
     * - 마지막 작업자 스레드가 준비를 마치면, 타이머 스레드가 "시작 방아쇠"를 당겨 작업자 스레드들이 일을 시작하게 함 <br/>
     * - 마지막 작업자 스레드가 동작을 마치자마자 타이머 스레드는 시계를 멈춤 <br/>
     * @param executor concurrency와 같은 숫자의 스레드 풀 필요. 기아 교착상태(thread starvation deadlock 주의)
     * @param concurrency  작업자의 개수 (excecutor의 스레드 풀의 스레드 개수와 같은 숫자)
     * @param action  수행할 업무
     * @return  완료하는 데 걸리는 시간
     * @throws InterruptedException  예외
     */
    private static long time(Executor executor, int concurrency, Runnable action)
            throws InterruptedException {
        final CountDownLatch ready = new CountDownLatch(concurrency);  // 카운트를 concurrency 개수 만큼 함
        final CountDownLatch start = new CountDownLatch(1);
        final CountDownLatch done = new CountDownLatch(concurrency);

        for (int i = 0; i < concurrency; i++) {
            executor.execute(() -> {
                // 타이머에게 준비를 마쳤음을 알림
                ready.countDown();  // ready의 카운트를 1 감소시킴
                try {
                    // 모든 worker 스레드가 준비될 때까지 기다림
                    start.await();  // start 카운트가 0이 될 때까지 대기
                    action.run();  // start 카운트가 0이된 후 업무 수행
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    // 타이머에게 작업을 마쳤음을 알림
                    done.countDown();  // 끝났다는 카운트 다운
                }
            });
        }

        ready.await();  // 모든 작업자가 준비될 때까지 기다림 (ready 카운트가 0이 될 때까지 대기)
        final long startNanos = System.nanoTime();
        start.countDown();  // 작업자들을 깨움 (start 카운트를 1 감소시켜서 0으로 만듦. 내부에서 업무 시작이 가능해짐
        done.await();  // 모든 작업자가 일을 끝마치기를 기다림 (done 카운트가 0이 될 때까지 대기)
        return System.nanoTime() - startNanos;
    }
}
