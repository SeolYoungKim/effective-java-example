package chapter_11.item80;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceTest {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        final ExecutorService exec = Executors.newFixedThreadPool(6);
        final List<Callable<Integer>> tests = List.of(new Test(), new Test(), new Test(), new Test(), new Test(),
                new Test());

        final long before = System.currentTimeMillis();
        exec.invokeAll(tests);
        final long after = System.currentTimeMillis();

        System.out.println(((after - before) / 1000) + " sec");
        exec.shutdown();

        if (exec.awaitTermination(5, TimeUnit.SECONDS)) {
            System.out.println("안전하게 종료되었습니다.");
        }
    }

    private static class Test implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            Thread.sleep(1000);
            System.out.println("[" + Thread.currentThread().getName() + "]");
            return 1;
        }
    }
}
