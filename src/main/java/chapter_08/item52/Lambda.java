package chapter_08.item52;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Lambda {
    public static void main(String[] args) {
        new Thread(System.out::println).start();

        ExecutorService executorService = Executors.newCachedThreadPool();
//        executorService.submit(System.out::println);
    }
}
