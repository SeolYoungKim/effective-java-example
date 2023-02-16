# readObject 메서드는 방어적으로 작성하라

```java
public final class Period {
    private final Date start;
    private final Date end;

    /**
     * @param start 시작 시각
     * @param end 종료 시각; 시작 시각보다 뒤여야 한다.
     * @throws IllegalArgumentException 시작 시각이 종료 시각보다 늦을 때 발생한다.
     * @throws NuLLPointerException startL end가 null이면 발행한다.
     */
    public Period(Date start, Date end) {
        this.start = new Date(start.getTime());
        this.end = new Date(end.getTime());
        if (this.start.compareTo(this.end) > 0) {
            throw new IllegalArgumentException(start + " after " + end);
        }
    }

    public Date start() {
        return new Date(start.getTime());
    }

    public Date end() {
        return new Date(end.getTime());
    }

    public String toString() {
        return start + " - " + end; 
    }
    // 나머지 코드는 생략
}
```

위 클래스를 직렬화 하기로 결정했다고 가정하면 
- `Period`객체의 물리적 표현이 논리적 표현과 부합하여, 기본 직렬화 형태를 사용해도 나쁘지 않음 
- 허나, **불변식을 더이상은 보장하지 못하게 됨**

### `readObject`메서드는 또 하나의 `public` 생성자와 같다.
때문에, 다른 생성자와 똑같은 수준으로 주의를 기울여야 함 
- 인수가 유효한지 검사, 방어적으로 복사하는 등의 작업을 수행해야 함 
- 그렇지 않으면 공격자가 해당 클래스의 불변식을 손쉽게 깨뜨릴 것임 


즉, `readObject`는 매개변수로 바이트 스트림을 받는 생성자다. 
- 바이트 스트림은 보통 정상적으로 생성된 인스턴스를 직렬화해 만들어짐
- 하지만 불변식을 깨뜨릴 의도로 임의 생성한 바이트 스트림을 건네면 문제가 생김

```java
public class BogusPeriod {
    // 진짜 Period 인스턴스에서는 만들어질 수 없는 바이트 스트림 
    private static final byte[] serializedForm = {
            (byte) 0xac, (byte) 0xed, 0x00, 0x05, 0x73, 0x72, 0x00, 0x06, 0x50, 0x65, 0x72, 0x69,
            0x6f, 0x64, 0x40, 0x7e, (byte) 0xf8, 0x2b, 0x4f, 0x46, (byte) 0xc0, (byte) 0xf4, 0x02, 0x00,
            0x02, 0x4c, 0x00, 0x03, 0x65, 0x6e, 0x64, 0x74, 0X00, 0x10, 0X4C, 0x6a, 0x61, 0x76, 0x61,
            0x2f, 0x75, 0x74, 0x69, 0x6c, 0x2f, 0x44, 0x61, 0x74, 0x65, 0x3b, 0x4c, 0x00, 0x05, 0x73,
            0x74, 0x61, 0x72, 0x74, 0x71, 0x00, 0x7e, 0x00, 0x01, 0x78, 0x70, 0x73, 0x72, 0x00, 0x0e,
            0x6a, 0x61, 0x76, 0x61, 0x2e, 0x75, 0x74, 0x69, 0x6C, 0x2e, 0x44, 0x61, 0x74, 0x65, 0x68, 0xa,
            (byte) 0x81, 0x01, 0x4b, 0x59, 0x74, 0x19, 0x03, 0x00, 0X00, 0x78, 0x70, 0x77, 0x08,
            0x00, 0x00, 0x00, 0x66, (byte) 0xdf, 0xfe, 0x1e, 0x00, 0x78, 0x73, 0x71, 0x00, 0x7e, 0x00,
            0x03, 0x77, 0x08, 0x00, 0x00, 0x00, (byte) 0xd5, 0x17, 0x69, 0X22, 0x00, 0x78
    };

    public static void main(String[] args) {
        Period p = (Period) deserialize(serializedForm);
        System.out.println(p);
    }

    static Object deserialize(byte[] sf) {
        try {
            return new ObjectOutputStream(new ByteArrayOutputStream(sf)).readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
```

위의 프로그램을 수행하면, 종료 시각이 시작 시각보다 앞서는 Period 인스턴스를 만들 수 있음
- 즉, 애써 방어적 복사로 구현한 불변식이 깨지게 되어버림

위 문제를 고치려면, `Period`의 `readObject` 메서드가 `defaultReadObject` 메서드를 호춣

```java

```
