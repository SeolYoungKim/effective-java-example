package chapter_08.item52;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

public class FunctionalEx {
    static void functionalTest(Runnable runnable) {
        System.out.println("Runnable");
    }

    static <T> void functionalTest(Callable<T> callable) {
        System.out.println("Callable");
    }

//    static <T> void functionalTest(Supplier<T> supplier) {
//        System.out.println("Supplier");
//    }
//
//    static <T> void functionalTest(Consumer<T> consumer) {
//        System.out.println("Consumer");
//    }
//
//    static <T> void functionalTest(Predicate<T> predicate) {
//        System.out.println("Predicate");
//    }


    public static void main(String[] args) {
        Supplier<String> supplier = () -> "하잉";
//        functionalTest(supplier);
//        functionalTest(System.out::println);
        functionalTest(() -> System.out.println("하잉"));

        List<String> hi = List.of("하잉", "바잉", "뀨잉");
        hi.stream().forEach(System.out::println);
    }
}
