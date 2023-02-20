# 직렬화된 인스턴스 대신 직렬화 프록시 사용을 검토하라 
`Serializable`을 구현하기로 결정한 순간, 언어의 정상 메커니즘인 생성자 이외의 방법으로 인스턴스를 생성할 수 있게 됨.
- 버그와 보안 문제 발생 가능성 증대 

## 직렬화 프록시 패턴 
바깥 클래스의 논리적 상태를 정밀하게 표현하는 중첩 클래스를 설계하여 `private static`으로 선언하며, 이 중첩 클래스가 바로 바깥 클래스의 직렬화 프록시다. 
- 중첩 클래스의 생성자는 단 하나여야 하고, 바깥 클래스를 매개변수로 받아야 한다.
  - 단순히 인수로 넘어온 인스턴스의 데이터를 복사하는 역할을 수행한다.
  - 일관성 검사나 방어적 복사도 필요 없다. 
- 설계상, 직렬화 프록시의 기본 직렬화 형태는 바깥 클래스의 직렬화 형태로 쓰기에 이상적임 
- 바깥 클래스와 직렬화 프록시 모두 `Serializable`을 구현한다고 선언해야 함

```java
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Date;

public class Period implements Serializable {
    private final Date start;
    private final Date end;

    public Period(Date start, Date end) {
        this.start = start;
        this.end = end;
    }

    private static class SerializationProxy implements Serializable {
        private static final long serialVersionUID = 1L;

        private final Date start;
        private final Date end;

        SerializationProxy(Period p) {
            this.start = p.start;
            this.end = p.end;
        }

        // 외부 클래스와 논리적으로 동일한 인스턴스를 반환하는 readResolve 메서드 
        // 역직렬화 시, 직렬화 시스템이 직렬화 프록시를 다시 외부 클래스의 인스턴스로 변환하게 해줌. 
        private Object readResolve() {
            return new Period(start, end);
        }
    }

    // 외부 클래스 인스턴스 대신 SerializationProxy의 인스턴스를 반환하게 하는 역할을 함 
    private Object writeReplace() {
        return new SerializationProxy(this);
    }

    // 외부 클래스가 직렬화되지 않도록 공격의 여지조차 막아버리자. 
    private void readObject(ObjectInputStream ois) throws InvalidObjectException {
        throw new InvalidObjectException("프록시가 필요합니다.");
    }
}
```

### 직렬화 프록시 패턴을 추천하는 이유 
- 직렬화는 생성자를 이용하지 않고도 인스턴스를 생성하는 기능을 제공함 
- **직렬화 프록시 패턴은 직렬화의 이런 언어도단적 특성을 상당 부분 제거**함
  - 즉, 일반 인스턴스를 만들 때와 똑같은 생성자, 정적 팩터리, 혹은 다른 메서드를 사용해 역직렬화된 인스턴스를 생성하는 것임. 
  - 따라서 역직렬화된 인스턴스가 해당 클래스의 불변식을 만족하는지 검사할 또 다른 수단을 강구하지 않아도 됨 
  - 해당 클래스의 정적 팩터리나 생성자가 불변식을 확인 해주고, 인스턴스 메서드들이 불변식을 잘 지켜준다면, 따로 더 해줘야 할 일이 없는 것임


- 해당 패턴은 가짜 바이트 스트림 공격과 내부 필드 탈취 공격을 프록시 수준에서 차단해줌 
  - 필드를 `final`로 선언 가능 
- 역직렬화한 인스턴스와 원래의 직렬화된 인스턴스의 클래스가 달라도 정상 작동함 
  - 예시 : `EnumSet`


## 한계 
- 클라이언트가 멋대로 확장할 수 있는 클래스에는 적용 불가능 
- 객체 그래프에 순환이 있는 클래스에도 적용 불가능 
- 느림


## 정리 
제 3자가 확장할 수 없는 클래스인 경우, 가능한 한 직렬화 프록시 패턴을 사용하자. 이 패턴이 아마도 중요한 불변식을 안정적으로 직렬화해주는 가장 쉬운 방법일 것이다. 

