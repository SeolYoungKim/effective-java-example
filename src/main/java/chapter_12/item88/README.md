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

위 문제를 고치려면, `Period`의 `readObject` 메서드가 `defaultReadObject` 메서드를 호춣한 후, 역직렬화된 객체가 유효한지 검사해야 함.
- 이 유효성 검사에 실패하면 `InvalidObjectException`을 던지게 하여 잘못된 역직렬화가 일어나는 것을 막을 수 있음

```java
private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
    s.defaultReadObject();
    
    if (start.compareTo(end) > 0) {
        throw new InvalidObjectException(start + " after " + end);    
    }
}
```

위 코드는 허용되지 않는 `Period` 인스턴스 생성을 막을 수는 있다. 
- 하지만 한 가지 문제가 숨어있다.
- 정상 `Period` 인스턴스에서 시작된 바이트 스트림 끝에 `private Date` 필드로의 참조를 추가하면 가변 `Period` 인스턴스를 만들 수 있음 
  - 악의적인 객체 참조가 추가될 위험이 아직 있다는 뜻 
- `Period`인스턴스가 불변식을 유지한 채 생성되어도, 의도적으로 내부의 값을 수정할 수 있음. 
  - 실제로도 보안 문제를 `String`이 불변이라는 사실에 기댄 클래스들이 존재함 


문제의 근원은 `Period`의 `readObject` 메서드가 방어적 복사를 충분히 하지 않은 데 있음. 
- 객체를 역직렬화할 때는 클라이언트가 소유해서는 안되는 객체 참조를 갖는 필드를 **모두 반드시 방어적으로 복사**해야 함 
  - 불변 클래스 안의 `readObject`메서드 내에서 사용되는 `private` 가변 요소를 모두 방어적으로 복사해야 함

```java
private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
    s.defaultReadObject();
    
    start = new Date(start.getTime());
    end = new Date(end.getTime());
    
    if (start.compareTo(end) > 0) {
        throw new InvalidObjectException(start + " after " + end);    
    }
}
```

위 처럼 구성해야 함 
- 방어적 복사를 유효성 검사보다 앞서 수행해야 하고, `Date`의 `clone` 메서드는 사용하지 않았음. 
- 두 조치 모두 `Period`를 공격으로부터 보호하는 데 필요함.
- `final` 필드는 방어적 복사가 불가능하니 주의하자. 


## 정리 
- `readObject` 메서드를 작성할 때는 언제나 `public` 생성자를 작성하는 자세로 임해야 함
- `readObject`는 어떤 바이트 스트림이 넘어오더라도 유효한 인스턴스를 만들어내야 함 
  - 바이트 스트림이 진짜 직렬화된 인스턴스라고 가정해서는 안됨 
- 안전한 `readObject` 메서드를 작성하는 지침
  - `private`여야 하는 객체 참조 필드는 각 필드가 가리키는 객체를 방어적으로 복사하라. 
    - 불변 클래스 내의 가변 요소가 이에 해당함
  - 모든 불변식을 검사하여 어긋나는게 발견되면 `InvalidObjectException`을 던진다.
    - 방어적 복사 다음에는 반드시 불변식 검사가 뒤따라야 한다.
  - 역직렬화 후 객체 그래프 전체의 유효성을 검사해야 한다면 `ObjectInputValidation` 인터페이스를 사용하라 
  - 직접적이든 간접적이든, 재정의 가능한 메서드는 호출하지 말자.
