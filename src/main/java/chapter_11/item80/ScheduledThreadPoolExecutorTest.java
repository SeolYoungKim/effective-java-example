package chapter_11.item80;

import java.time.LocalTime;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

//https://codechacha.com/ko/java-scheduled-thread-pool-executor/
public class ScheduledThreadPoolExecutorTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        final ScheduledThreadPoolExecutor scheduledExec = new ScheduledThreadPoolExecutor(2);
//        runnable(scheduledExec);
//        callable(scheduledExec);
//        atFixRate(scheduledExec);
        withFixedDelay(scheduledExec);
    }

    private static void runnable(ScheduledThreadPoolExecutor exec) {
        final Runnable runnable = () -> System.out.println("Runnable task : " + LocalTime.now());
        final int delay = 3;

        // job scheduling
        System.out.println("Scheduled task : " + LocalTime.now());
        exec.schedule(runnable, delay, TimeUnit.SECONDS);  // 3초 후 Runnable 실행
        exec.shutdown();
    }

    private static void callable(ScheduledThreadPoolExecutor exec)
            throws ExecutionException, InterruptedException {
        final Callable<String> callable = () -> "Callable task : " + LocalTime.now();
        final int delay = 3;

        // job scheduling
        System.out.println("Scheduled task : " + LocalTime.now());
        final ScheduledFuture<String> future = exec.schedule(callable, delay,
                TimeUnit.SECONDS);// 3초 후 Callable 실행

        // 결과 리턴 대기 (Future는 결과가 나올 때까지 대기한다)
        final String result = future.get();
        System.out.println(result);
        exec.shutdown();
    }

    private static void atFixRate(ScheduledThreadPoolExecutor exec) {
        Runnable runnable = runnableForRepeat();

        final int initialDelay = 2;  // 2초 후에 시작
        final int delay = 3;  // 3초마다 Runnable 반복 (3초에 1회 Runnable 수행)

        // job scheduling
        System.out.println("Scheduled task : " + LocalTime.now());
        exec.scheduleAtFixedRate(runnable, initialDelay, delay, TimeUnit.SECONDS);

        sleepSec(20);
        exec.shutdown();
    }

    private static Runnable runnableForRepeat() {
        return () -> {
            System.out.println("++ Repeat task : " + LocalTime.now());
            sleepSec(3);
            System.out.println("-- Repeat task : " + LocalTime.now());
        };
    }

    private static void sleepSec(int sec) {
        try {
            TimeUnit.SECONDS.sleep(sec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private static void withFixedDelay(ScheduledThreadPoolExecutor exec) {
        Runnable runnable = runnableForRepeat();

        final int initialDelay = 2;  // 2초 후에 시작
        final int delay = 3;  // Runnable이 완료되고 난 이후 3초 후 Runnable 반복 (각 실행 간 3초 딜레이 존재)

        // job scheduling
        System.out.println("Scheduled task : " + LocalTime.now());
        exec.scheduleWithFixedDelay(runnable, initialDelay, delay, TimeUnit.SECONDS);

        sleepSec(20);
        exec.shutdown();
    }
}
