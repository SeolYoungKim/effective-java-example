package chapter_08.item52;

import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class FunctionalEx {
    static void functionalTest(Runnable runnable) {
        System.out.println("Runnable");
    }

    static <T> void functionalTest(Supplier<T> supplier) {
        System.out.println("Supplier");
    }

    static <T> void functionalTest(Consumer<T> consumer) {
        System.out.println("Consumer");
    }

    static <T> void functionalTest(Predicate<T> predicate) {
        System.out.println("Predicate");
    }

    public static void main(String[] args) {
        Supplier<String> supplier = () -> "하잉";
//        functionalTest(System.out::println);
        functionalTest(supplier);
        functionalTest(() -> System.out.println("하잉"));
    }
}
