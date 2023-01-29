package chapter_11.item78;

import java.time.LocalTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class Main {
    static class Counter {
        private final AtomicLong count = new AtomicLong(0);

        public synchronized void increase() {
            count.getAndIncrement();
        }
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        final ExecutorService threadPool = Executors.newFixedThreadPool(10);
        final Counter counter = new Counter();
        for (int i = 0; i < 10000; i++) {
            threadPool.submit(counter::increase);
        }

        Thread.sleep(5000);
        System.out.println(counter.count);
        threadPool.shutdown();

        if (threadPool.awaitTermination(20, TimeUnit.SECONDS)) {
            System.out.println(LocalTime.now() + " All jobs are terminated");
        } else {
            System.out.println(LocalTime.now() + " some jobs are not terminated");
            threadPool.shutdownNow();
        }
    }
}
