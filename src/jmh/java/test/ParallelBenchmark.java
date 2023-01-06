package test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.SECONDS)
@Fork(value = 2, jvmArgs = {"-Xms4G", "-Xmx4G"})
public class ParallelBenchmark {

    private static final long N = 100_000_000L;
    private static final long number = 1L;
    private static final List<Long> numbers;

    static {
        numbers = LongStream.rangeClosed(1, N)
                .boxed()
                .collect(Collectors.toList());

    }

//    @Benchmark
    public long sequentialPi() {
        return LongStream.rangeClosed(2, N)
                .mapToObj(BigInteger::valueOf)
                .filter(i -> i.isProbablePrime(50))
                .count();
    }

//    @Benchmark
    public long parallelPi() {
        return LongStream.rangeClosed(2, N)
                .parallel()
                .mapToObj(BigInteger::valueOf)
                .filter(i -> i.isProbablePrime(50))
                .count();
    }

//    @Benchmark
    public List<Long> fillListWithStream() {
        return LongStream.rangeClosed(1, N)
                .boxed()
                .collect(Collectors.toList());
    }

//    @Benchmark
    public List<Long> fillListWithForLoop() {
        List<Long> results = new ArrayList<>();
        for (long i = 1; i <= N; i++) {
            results.add(i);
        }

        return results;
    }

    @Benchmark
    public long sumWithStream() {
        return numbers.stream()
                .mapToLong(l -> l * l)
                .map(l -> l * l)
                .map(l -> l * l)
                .reduce(Integer.MIN_VALUE, Math::max);
    }

    @Benchmark
    public long sumWithForLoop() {
        long result = Integer.MIN_VALUE;
        for (Long aLong : numbers) {
            long l = aLong * aLong;
            l *= l;
            l *= l;

            result = Math.max(result, l);
        }

        return result;
    }
}
