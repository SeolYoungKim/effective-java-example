package chapter_11.item81;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
    private static final ConcurrentMap<String, String> map = new ConcurrentHashMap<>();

    public static String internSlowVer(String s) {
        final String previousVal = map.putIfAbsent(s, s);  // 내부에서 동기화를 수행할 수 있음 -> 성능에 그닥 좋지 않은 영향
        return previousVal == null ? s : previousVal;
    }

    public static String internFastVer(String s) {
        String result = map.get(s);
        if (result == null) {
            result = map.putIfAbsent(s, s);
            if (result == null) {
                result = s;
            }
        }
        return result;
    }

    private static final ExecutorService executor;
    private static final ExecutorCompletionService<String> execCompletion;

    static {
        executor = Executors.newFixedThreadPool(10);
        execCompletion = new ExecutorCompletionService<>(executor);
    }


    public static void main(String[] args) throws InterruptedException {
        // intern
        // https://simple-ing.tistory.com/3
        final String a = "apple";
        final String b = new String("apple");
        final String c = b.intern();  // intern() 메서드는 String pool에서 리터럴 문자열이 이미 존재하는지 체크 -> 존재하면 해당 문자열을 반환하고, 아니면 리터럴을 String pool에 넣어줌

        System.out.println(a == b);
        System.out.println(a == c);

        map.put("key", "val");
        map.get("key");

        final Thread producer = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                System.out.printf("[Producer][%s] 데이터를 저장합니다.%n", Thread.currentThread().getName());
                execCompletion.submit(() -> "data");
                System.out.printf("[Producer][%s] 데이터를 저장 완료.%n", Thread.currentThread().getName());
            }
        });

        final Thread consumer = new Thread(() -> {
            while (true){
                System.out.printf("[Consumer][%s] 데이터를 꺼냅니다.%n", Thread.currentThread().getName());
                try {
                    final Future<String> take = execCompletion.take();
                    System.out.printf("[Consumer][%s] 꺼낸 데이터={%s}.%n",
                            Thread.currentThread().getName(), take.get());
                } catch (InterruptedException | ExecutionException e) {
                    System.out.println();
                    System.out.println("데이터를 꺼내는 동작이 종료됩니다.");
                    break;
                }
            }
        });

        producer.start();
        consumer.start();

        Thread.sleep(1000);
        for (int i = 1; i <= 10; i++) {
            Thread.sleep(1000);
            System.out.println();
            System.out.println("데이터를 실시간으로 저장합니다. 횟수:" + i);
            produce();
        }

        Thread.sleep(3000);
        consumer.interrupt();
        executor.shutdown();
    }

    private static void produce() {
        execCompletion.submit(() -> "data");
        execCompletion.submit(() -> "data");
        execCompletion.submit(() -> "data");
    }
}
