# 인스턴스 수를 통제해야 한다면 readResolve 보다는 열거 타입을 사용하라 
## 싱글턴 
- 싱글턴 클래스에 `Serializable`을 추가하는 순간 더 이상 싱글턴이 아니게 됨 
  - 기본 직렬화를 사용하지 않아도, 명시적인 readObject를 제공하더라도 소용 없음 
  - 어떤 readObject를 사용하든 상관 없이, 이 클래스가 초기화될 때 만들어진 인스턴스와는 별개인 인스턴스를 반환함

```java
public class Elvis implements Serializable {
    public static final Elvis INSTANCE = new Elvis();

    private Elvis() {

    }

    public void leaveTheBuilding() {
        System.out.println("엘비스가 빌딩을 떠납니다.");
    }
}
```

```java
class ElvisTest {
    @Test
    void singletonTest() {
        assertThat(Elvis.INSTANCE).isEqualTo(Elvis.INSTANCE);
    }

    @DisplayName("역직렬화를 하게 되면 싱글턴이 깨진다.")
    @Test
    void serializableTest() throws IOException, ClassNotFoundException {
        final ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("elvis.txt"));
        oos.writeObject(Elvis.INSTANCE);
        oos.flush();

        final ObjectInputStream ois = new ObjectInputStream(new FileInputStream("elvis.txt"));
        final Elvis readObject = (Elvis) ois.readObject();

        assertThat(Elvis.INSTANCE).isNotEqualTo(readObject);
    }
}
```

## readResolve
- 해당 메서드를 이용하면 readObject가 만들어낸 인스턴스를 다른 것으로 대체할 수 있음 
  - 역직렬화한 객체의 클래스가 readResolve 메서드를 적절히 정의해둔 경우
    - 역직렬화 후 새로 생성된 객체를 인수로 이 메서드가 호출됨
    - 이 메서드가 반환한 객체 참조가 새로 생성된 객체를 대신해 반환 됨 
    - 새로 생성된 객체의 참조는 유지하지 않으므로 바로 **가비지 컬렉션의 대상**이 됨 

```java
public class Sam implements Serializable {
    public static final Sam INSTANCE = new Sam();

    private Sam() {

    }

    public void leaveTheBuilding() {
        System.out.println("샘이 빌딩을 떠납니다.");
    }

    private Object readResolve() {
        return INSTANCE;
    }
}
```

```java
@DisplayName("readResolve 메서드를 적절히 구현해둔 경우 싱글턴이 유지된다.")
@Test
void readResolveTest() throws IOException, ClassNotFoundException {
    final ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("sam.txt"));
    oos.writeObject(Sam.INSTANCE);
    oos.flush();

    final ObjectInputStream ois = new ObjectInputStream(new FileInputStream("sam.txt"));
    final Sam readObject = (Sam) ois.readObject();  // readObject로 만들어진 새로운 객체는 GC 대상이 되고, readResolve가 반환하는 객체가 반환된다.

    assertThat(Sam.INSTANCE).isEqualTo(readObject);
}
```

`readResolve` 메서드는 역직렬화한 객체는 무시하고, 클래스 초기화 때 만들어진 `Sam` 인스턴스를 반환한다.
- 이 때, Sam 인스턴스의 직렬화 형태는 아무런 실 데이터를 가질 이유가 없다. 따라서, 모든 인스턴스 필드를 `transient`로 선언해야 한다.
  - 아무래도, 싱글턴은 기본적으로 **상태를 갖지 않도록 설계**되기 때문에 이러한 말을 한 것 같다. -> 고 생각했지만, 아니었다. (책 아래 부분에서 설명함)
  - 따라서 싱글턴은 인스턴스 필드를 갖지 않도록 설계가 될텐데, 이게 존재할 경우 `transient`로 선언하라고 한 것 같다. -> 이게 존재할 경우가 있을까? 싱글턴이 무의미해지는 것 아닌지..?


**사실, `readResolve`를 인스턴스 통제 목적으로 사용할 경우 객체 참조 타입 인스턴스 필드는 모두 `transient`로 선언해야 한다고 한다.**
- `MutablePeriod` 공격과 비슷한 방식으로 `readResolve` 메서드가 수행되기 전에 역직렬화된 객체의 참조를 공격할 여지가 남기 때문이다.
- 즉, 싱글턴이 `non-transient` 참조 필드를 가지고 있다면, 그 필드의 내용은 `readResolve` 메서드가 수행되기 전에 역직렬화 된다.
- 그렇다면, 잘 조작된 스트림을 써서 해당 참조 필드의 내용이 역직렬화되는 시점에 그 역직렬화 된 인스턴스의 참조를 훔쳐올 수 있다.


## 열거 타입 이용 
`readResolve` 메서드를 사용해 순간적으로 만들어진 역직렬화된 인스턴스에 접근하지 못하게 하는 방법은 깨지기 쉽고 신경을 많이 써야하는 작업임 
- 반면, 직렬화 가능한 인스턴스 통제 클래스를 열거 타입을 이용해 구현한다면 선언한 상수 이외의 다른 객체는 존재하지 않음을 자바가 보장해줌
  - 공격자가 `AccessibleObject.setAccessible`을 이용한다면 달라지겠지만... (이를 특권(`privileged`) 메서드라고 함)
  - 위 메서드를 악용한다면, 임의의 네이티브 코드를 수행할 수 있는 특권을 가로챌 수 있게 된다. 따라서, 모든 방어가 무력화되어버린다. 

```java
public enum EnumElvis {
    INSTANCE;

    private String[] favoriteSongs = {"Hound Dog", "Heartbreak Hotel"};

    public void printFavorites() {
        System.out.println(Arrays.toString(favoriteSongs));
    }
}
```

- 열거 타입은 **컴파일 타임에 어떤 인스턴스들이 있는지 알 수 없는 상황**에서는 직렬화 가능 인스턴스 통제 클래스를 작성할 수 없다. 
  - 이 때는 `readResolve`를 사용해야 함 


## readResolve의 접근성 
- **`final` 클래스**에서라면 `readResolve`는 `private`이어야 한다. 
- **`final`이 아닌 클래스**에서는 다음 몇 가지를 주의해서 고려해야 함 
  - `private`으로 선언하면 하위 클래스에서 사용할 수 없음 
  - `package-private`으로 선언하면 같은 패키지에 속한 하위 클래스에서만 사용 가능 
  - `protected`, `public`으로 선언하면 이를 재정의하지 않은 모든 하위 클래스에서 사용 가능
    - 이 때는 하위 클래스의 인스턴스를 역직렬화 하면 상위 클래스의 인스턴스를 생성하기 때문에 `ClassCastException`이 발생할 수 있음


## 정리 
- 불변식을 지키기 위해 인스턴스를 통제해야 한다면 가능한 한 열거 타입을 사용하자 
- 여의치 않은 상황에서 직렬화와 인스턴스 통제가 모두 필요하다면 `readResolve`메서드를 작성해서 넣자
  - 주의 : 모든 참조 타입 인스턴스 필드를 `transient`로 선언하자 