package test;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.SECONDS)
@Fork(value = 2, jvmArgs = {"-Xms4G", "-Xmx4G"})
public class RandomVsThreadLocalRandom {
    private static final long N = 10_000_000L;
    private static final Random random = new Random();
    private static final ExecutorService executorService = Executors.newFixedThreadPool(8);

    @Benchmark
    public void random() {
        for (int i = 0; i < N; i++) {
            executorService.submit(() -> System.out.println(random.nextInt(100)));
        }

        executorService.shutdown();
    }

    @Benchmark
    public void threadLocalRandom() {
        for (int i = 0; i < N; i++) {
            executorService.submit(() -> System.out.println(ThreadLocalRandom.current().nextInt(100)));
        }

        executorService.shutdown();
    }
}
