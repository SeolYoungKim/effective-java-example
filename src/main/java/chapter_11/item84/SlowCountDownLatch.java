package chapter_11.item84;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SlowCountDownLatch {
    private int count;

    public SlowCountDownLatch(final int count) {
        if (count < 0) {
            throw new IllegalArgumentException(count + " < 0");
        }
        this.count = count;
    }

    public void await() {
        while (true) {
            synchronized (this) {
                if (count == 0) {
                    return;
                }
            }
        }
    }

    public synchronized void countDown() {
        if (count != 0) {
            count--;
        }
    }

    public static void main(String[] args) {
        final SlowCountDownLatch slowCountDownLatch = new SlowCountDownLatch(1000);
        final ExecutorService executorService = Executors.newFixedThreadPool(1000);
        for (int i = 0; i < 1000; i++) {
            executorService.submit(slowCountDownLatch::countDown);
        }

        slowCountDownLatch.await();
        System.out.println("하잉");
    }
}
