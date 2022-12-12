package chapter_07.item45;

import static java.math.BigInteger.*;

import java.math.BigInteger;
import java.util.stream.Stream;

public class MersennePrime {
    public static void main(String[] args) {
        primes().map(prime -> TWO.pow(prime.intValueExact()).subtract(ONE))
                .filter(mersenne -> mersenne.isProbablePrime(50))
                .limit(20)
                .forEach(System.out::println);

        primes().map(prime -> TWO.pow(prime.intValueExact()).subtract(ONE))
                .filter(mersenne -> mersenne.isProbablePrime(50))
                .limit(20)
                .forEach(mersenneP -> System.out.println(mersenneP.bitLength() + ": " + mersenneP));
    }

    static Stream<BigInteger> primes() {
        return Stream.iterate(TWO, BigInteger::nextProbablePrime);
    }
}
