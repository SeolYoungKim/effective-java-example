# 다른 타입이 적절하다면 문자열 사용을 피하라 
- 기본 타입이든, 참조 타입이든 적절한 값 타입이 있다면 그 것을 사용하고, 없다면 새로 작성해라. 
- 문자열은 열거 타입을 대신하기에 적합하지 않다.
- 문자열은 혼합 타입을 대신하기에 적합하지 않다.

### 혼합 타입을 문자열로 처리한 부적절한 예시
```java
String compoundKey = className + "#" + i.next();
```
 
## 문자열은 권한을 표현하기 적합하지 않다
### 잘못된 예 
```java
public class ThreadLocal {
    private ThreadLocal(){}
    
    // 현 스레드의 값을 키로 구분해 저장 
    public static void set(String key, Object value);
    
    // 키가 가리키는 현재 스레드의 값을 반환 
    public static Object get(String key);
}
```
- 위 클래스를 사용하는 각 클라이언트가 서로 소통하지 못해 같은 키를 쓴다면?
  - 두 클라이언트 모두 제대로 동작하지 않을 것임 
  - 보안도 취약함 -> 악의적인 클라이언트라면, 의도적으로 같은 키를 사용하여 다른 클라이언트의 값을 가져올 수 있음 
- 문자열 대신 **위조할 수 없는 키**를 사용하자.
  - 이 키를 권한(capacity)이라고도 함 

```java
public class ThreadLocal {
    private ThreadLocal(){}
    
    // 권한
    public static class Key {
        Key() {}
    }
    
    // 위조 불가능한 고유 키 생성 
    public static Key getKey() {
        return new Key();
    }

    // 키가 가리키는 현재 스레드의 값을 반환 
    public static void set(Key key, Object value) {}
    public static Object get(Key key) {}
}
```

- 리팩토링하면 아래와 같아짐. 
  - set, get을 Key의 인스턴스 메서드로 바꿈
  - 그렇게되면 ThreadLocal이 딱히 하는 일이 사라지니, 통합을 하면 됨 

```java
public class ThreadLocal {
    public ThreadLocal(){}
    public void set(Object value);
    public Object get();
}
```

- 하지만 타입 안전하지 않으니 아래와 같이 제네릭을 도입

```java
public class ThreadLocal<T> {
    public ThreadLocal(){}
    public void set(Object value);
    public T get();
}
```

## 정리
- 더 적합한 데이터 타입이 있거나, 새로 작성할 수 있다면 문자열을 사용하고 싶은 유혹을 뿌리칠 것 
- 문자열은 잘못 사용하면 번거롭고, 덜 유연하며, 느리고, 오류 가능성이 크다.
- 잘못 사용하는 흔한 예시
  - 열거 타입
  - 기본 타입 
  - 혼합 타입 