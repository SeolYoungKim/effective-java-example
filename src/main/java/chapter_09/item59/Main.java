package chapter_09.item59;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;

public class Main {
    public static void main(String[] args) throws IOException {
//        try (InputStream in = new URL("https://www.naver.com").openStream()) {
//            in.transferTo(System.out);
//        }

        Random random = new Random();
        ExecutorService executorService = Executors.newFixedThreadPool(8);
        AtomicLong atomicLong = new AtomicLong();
        long before = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++) {
//            executorService.submit(() -> System.out.println(random.nextInt(100)));
            executorService.submit(() -> System.out.println(ThreadLocalRandom.current().nextInt(100)));
        }

        executorService.shutdownNow();

        long result = System.currentTimeMillis() - before;
        System.out.println("result = " + result);
//        System.out.println(executorService.isShutdown());
    }
}
