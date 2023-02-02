package chapter_11.item81;

import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CyclicBarrierTest {
    public static void main(String[] args) throws InterruptedException {
        final List<Thread> workers = Stream.generate(() -> new Thread(new RandomSleepWorker()))
                .limit(5)
                .collect(Collectors.toList());
        workers.forEach(Thread::start);

        Thread.sleep(3000);
        CYCLIC_BARRIER.reset(); // 리셋
        final int parties = CYCLIC_BARRIER.getParties();
        System.out.println(parties);
    }

    private static final CyclicBarrier CYCLIC_BARRIER = new CyclicBarrier(5);

    private static class RandomSleepWorker implements Runnable {
        private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

        @Override
        public void run() {
            System.out.printf("[%s] Start%n", Thread.currentThread().getName());
            final int delay = RANDOM.nextInt(1000) + 1000;

            try {
                System.out.printf("[%s] Sleep during %d ms%n",
                        Thread.currentThread().getName(), delay);
                Thread.sleep(delay);
                System.out.printf("[%s] End sleep%n", Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                CYCLIC_BARRIER.await();
            } catch (BrokenBarrierException | InterruptedException e) {
                e.printStackTrace();
            }

            System.out.printf("[%s] End%n", Thread.currentThread().getName());
        }
    }
}
