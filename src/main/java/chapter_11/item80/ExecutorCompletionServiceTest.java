package chapter_11.item80;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorCompletionServiceTest {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
//        singleThread();
        multiThread();
    }

    private static void singleThread() throws InterruptedException, ExecutionException {
        final ExecutorService exec = Executors.newSingleThreadExecutor();
        executorService(exec);
    }

    private static void multiThread() throws ExecutionException, InterruptedException {
        final ExecutorService exec = Executors.newFixedThreadPool(10);
        executorService(exec);
    }

    private static void executorService(final ExecutorService exec)
            throws InterruptedException, ExecutionException {
        final ExecutorCompletionService<String> execCompletion = new ExecutorCompletionService<>(
                exec);

        final List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            numbers.add(i);
        }

        for (int i = 0; i < 10; i++) {
            final Integer number = numbers.get(i);
            execCompletion.submit(() -> String.format("[%s] Callable 반환값 %d",
                    Thread.currentThread().getName(), number));
        }

        for (int i = 0; i < 10; i++) {
            final Future<String> take = execCompletion.take();
            System.out.println(take.get());
        }

        exec.shutdown();
    }
}
