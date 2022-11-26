package item32;

import java.text.SimpleDateFormat;
import java.util.Random;

public class ThreadLocalTest implements Runnable {

    // 이는 다른 쓰레드에 영향을 주지 않는다. 한 쓰레드 내에서, 쓰레드 로컬을 변경해도, 다른 쓰레드에서는 변경되지 않는다!!
    // 이를 사용하는 해당 쓰레드에서만 사용되는 공용 저장소다.
    // 스프링의 트랜잭션 커넥션 관리!
    static final ThreadLocal<SimpleDateFormat> FORMATTER = ThreadLocal.withInitial(
            () -> new SimpleDateFormat("yyMMdd HHmm")
    );

    public static void main(String[] args) throws InterruptedException {
        ThreadLocalTest obj = new ThreadLocalTest();
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(obj, "" + i);
            Thread.sleep(new Random().nextInt(1000));
            thread.start();
        }
    }

    @Override
    public void run() {
        System.out.println("Thread name = " + Thread.currentThread().getName() + " formatter = " + FORMATTER.get().toPattern());
        try {
            Thread.sleep(new Random().nextInt(1000));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        FORMATTER.set(new SimpleDateFormat());
        System.out.println("Thread name = " + Thread.currentThread().getName() + "formatter = " + FORMATTER.get().toPattern());
    }

    // TODO Atomic : 멀티 쓰레드 환경에서 쓸 수 있는 유틸리티 패키지. 락을 사용하지 않고도 멀티 쓰레드에 안전하다.

}
