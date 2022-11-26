package item32;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomTest {

    public static void main(String[] args) {

        // 여러 쓰레드가 동시에 Random을 호출하는 경우가 아니라면, 사용해도 상관 없다.
        // 하지만, 짧은 시간 내에 여러 쓰레드가 동시에 호출한다면 ThreadLocalRandom을 사용하자.
        Random random = new Random();
        System.out.println(random.nextInt(10));

        // 랜덤의 nextInt()는 아래와 같이 구성되어 있다.
        // 멀티 쓰레드 환경에서 random이 공유되어 사용되고, 많이 호출된다면, next()메서드 내부의 compareAndSet() 메서드가 실패하는 경우가 생긴다.
        // 그렇게 되면 값을 가져올 때 까지 반복문을 수행한다.

        // Random 자체는 멀티 쓰레드 환경에서 이미 안전하게 구현되어있다. (낙관적 락/Atomic 사용)
        // 두 쓰레드가 동시에 접근 시, 둘중 하나가 시도한다면 하나는 무조건 실패하도록 구성되어있다.
        // 이 떄는 재시도를 해야하므로, 성능상의 문제가 생길 수 있다.

        // 그러느니, 쓰레드 로컬을 사용하자.
        ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();  // 현재 쓰레드에 할당된 랜덤을 가져온다.
        System.out.println(threadLocalRandom.nextInt(10));
    }

    // 화장실로 비유해보자.
    private int value;  // 현재 화장실의 상태

    // 화장실을 열었는데, 아무도 없다. 내가 기대했던 화장실의 상태다.
    public synchronized int compareAndSwap(int expectedValue, int newValue) {
        int readValue = value;

        // 기대한 화장실의 상태와, 현재 화장실의 상태가 다르다.
        // 그럼 아래의 if문을 수행하지 않는다.
        // 이는 기대한 경우에만 값을 변경할 수 있게 할 수 있다.
        // TODO Atomic이 그런 구성이다.

        if (readValue == expectedValue) {  // 기대한 화장실의 상태와, 현재 화장실의 상태가 같다.
            value = newValue;  // 수행
        }

        return readValue;
    }

}
