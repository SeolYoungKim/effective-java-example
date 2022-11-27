package chapter_04.item17;

import java.util.concurrent.CountDownLatch;

public class Count {

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            countDownLatch.countDown();  // 카운트다운을 호출 할 때마다 1씩 줄어듬. 멀티 쓰레드일때, 한 개의 쓰레드가 이걸 한번 호출하면 1 줄어듬.
            long count = countDownLatch.getCount();
            System.out.println(count);
        }
    }
}
