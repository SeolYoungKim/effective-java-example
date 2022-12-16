package chapter_07.item44;

import java.math.BigInteger;
import java.time.Instant;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.LongFunction;
import java.util.function.LongToIntFunction;
import java.util.function.ObjLongConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToLongBiFunction;
import java.util.function.ToLongFunction;
import java.util.function.UnaryOperator;

public class Application {
    public static void main(String[] args) {
        UnaryOperator<String> unaryOperator = String::toLowerCase;  // 인수와 반환값의 타입이 같음
        BinaryOperator<BigInteger> binaryOperator = BigInteger::add;

        Predicate<Character> predicate = Character::isAlphabetic;

        Function<String, Integer> function = Integer::valueOf;

        Supplier<Instant> supplier = Instant::now;

        Consumer<Object> consumer = System.out::println;

        //기본형 특화 BinaryOperator... 등은 반환값도 기본형으로 ..!
        //LongFunction<T>는 long을 인수로 받아 T를 반환한다. (즉, 반환 타입은 타입 매개변수다)
        // long -> int : LongToIntFunction
        LongFunction<int[]> longFunction = (long l) -> new int[]{(int) l};  // long -> int[]
        LongToIntFunction longToIntFunction = (long l) -> (int) l + 1;  // long -> int
        ToLongFunction<int[]> toLongFunction = (int[] arr) -> (long) arr[0];  // int[] -> long

        ToLongBiFunction<Integer, Integer> toLongBiFunction = Integer::sum;  // Integer, Integer -> long

        ObjLongConsumer<Member> objLongConsumer = (member, l) -> member.addAge(l);
    }

    static class Member {
        private final long age = 0L;

        public long addAge(long l) {
            return age + l;
        }
    }

}
