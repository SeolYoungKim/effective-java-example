# 지연 초기화는 신중히 사용하라 
## 지연 초기화
- 필드의 초기화 시점을 그 값이 처음 필요할 때까지 늦추는 기법 
  - 값이 전혀 쓰이지 않으면 초기화도 결코 일어나지 않음 
  - 정적 필드, 인스턴스 필드 모두에 사용 가능 
- 주로 최적화 용도로 사용됨
- 클래스와 인스턴스 초기화 때 발생하는 위험한 순환 문제를 해결하는 효과도 있음

## 지연 초기화는 필요할 때까지는 하지 말것 
- 양날의 검임 
- 클래스 혹은 인스턴스 생성 시 초기화 비용은 감소하지만, 그 대신 지연 초기화하는 필드에 접근하는 비용은 커짐 
- 지연 초기화하려는 필드들 중, 결국 초기화가 이뤄지는 비율에 따라, 실제 초기화에 드는 비용에 따라, 초기화된 각 필드를 얼마나 빈번히 호출하느냐에 따라 지연 초기화가 실제로는 성능을 느려지게 할 수 있음 

## 필요한 때 
- 해당 클래스의 인스턴스 중, 그 필드를 사용하는 인스턴스의 비율이 낮은 반면, 그 필드를 초기화하는 비용이 클 때 


## 멀티스레드 환경에서의 지연 초기화는 까다롭다
- 지연 초기화하는 필드를 **둘 이상의 스레드가 공유한다면** 어떤 형태로든 **반드시 동기화 해야**한다.
- 그렇지 않으면 심각한 버그로 이어짐


## 대부분의 상황에서 일반적인 초기화가 지연 초기화보다 나음 
### 일반 초기화
```java
private final List<Integer> list = new ArrayList<>();
```

### 지연 초기화 
- 초기화 순환성(`initialization circularity`)을 깨트리기 위해 지연초기화를 사용하였다면, `synchronized`를 사용해라 
```java
private Set<Integer> set;

private synchronized Set<Integer> getSet() {
    if (set == null) {
        set = new HashSet<>();
    }

    return set;
}
```

#### 초기화 순환성 
- `StackOverFlowError` 발생 
```java
public static void main(String[] args) {
    final A a = new A();
}

// initialization circularity
static class A {
    private B b;
    public A() {
        b = new B();
    }
}

static class B {
    private C c;
    public B() {
        c = new C();
    }
}

static class C {
    private A a;
    public C() {
        a = new A();
    }
}

// 위와 같은 순환성을 꺠기 위해 지연 초기화를 사용할 수 있음
public static void main(String[] args) {
    final A a = new A();
    final B b = a.b();
}

// initialization circularity
static class A {
    private B b;
    public A() {
    }
  
    public B b() {
        if (b == null) {
          b = new B();
        }
        return b;
    }
}

static class B {
    private C c;
    public B() {
    }
  
    public C c() {
        if (c == null) {
          c = new C();
        }
        return c;
    }
}

static class C {
    private A a;
  
    public A a() {
        if (a == null) {
          a = new A();
        }
        return a;
    }
}
```
- 위와 같이 지연 초기화를 이용하면 초기화 순환성이 해결되나, 멀티 스레드 환경에서는 `synchronized` 키워드를 반드시 써야한다.

## 지연 초기화 홀더 클래스
- 성능 때문에 정적 필드를 초기화해야 하는 경우, 지연 초기화 홀더 클래스(lazy initialization holder class)를 사용하자 
```java
private static class ListHolder {
    static final List<Integer> list = addList();
}

private static List<Integer> addList() {
    return new ArrayList<>();
}

private static List<Integer> getList() {
   return ListHolder.list;
}
```
- `getList`가 처음 호출되는 순간 `ListHolder.list`가 처음 읽힘 
  - `ListHolder`클래스 초기화를 trigger 
  - 위와 같이 구성할 경우, 동기화를 전혀 하지 않기 때문에 성능이 느려질 일이 없음 
- 일반적인 VM은 오직 **클래스를 초기화할 때만 필드 접근을 동기화**한다.
  - 클래스 초기화가 끝난 후에는 VM이 동기화 코드를 제거하여, 그 다음부터는 아무런 검사나 동기화 없이 필드에 접근하게 됨


## 이중 검사 관용구 
- 성능 때문에 인스턴스 필드를 지연 초기화해야 한다면 이중 검사(double-check) 관용구를 사용하라 
  - 해당 관용구는 초기화된 필드에 접근할 때의 동기화 비용을 없애준다. 
- 필드의 값을 두 번 검사하는 방식 
  - 한번은 동기화 없이 검사 
  - 두 번째는 동기화 하여 검사 (초기화 되지 않은 경우)
  - 두 번째 검사에서도 필드가 초기화되지 않은 경우에만 필드를 초기화함 
  - 필드 초기화 후로는 동기화하지 않기 때문에 해당 필드는 반드시 `volatile`로 선언해야 함 

```java
private volatile String field;

private String getField() {
    String result = field;  // 필드가 이미 초기화된 경우 해당 필드를 딱 한번만 읽도록 보장함 (여기서만 한 번 읽고, 아래에서 안 읽음)
    if (result != null) {  // 이러한 방법은 성능을 높여주고, 저수준 동시성 프로그래밍에 표준적으로 적용되는 방법임 
        return result;
    }

    synchronized (this) {
        if (field == null) {
            field = "초기화 값";
        }
        
        return field;
    }
}
```

### 단일 검사(single-check) 관용구 
- 반복해서 초기화해도 상관 없는 인스턴스 필드를 지연 초기화해야 할 때가 있음 
- 이런 경우, 이중 검사에서 두 번째 검사를 생략할 수 있음

```java
private volatile String field;

private String getFieldSingle() {
    String result = field;
    if (result == null) {  // 동기화 하지 않았으므로, 초기화가 중복해서 발생할 수 있음 
        field = result = "초기화 값";  
    }
    return result;
}
```

### 짜릿한 단일 검사(racy single-check) 관용구
- 모든 스레드가 필드의 값을 다시 계산해도 상관없고, 필드의 타입이 long과 double을 제외한 다른 기본 타입인 경우, 단일 검사의 필드 선언에서 `volatile`을 없애도 됨
- `volatile`을 제거하여 필드 접근 속도를 높여줄 수 있음 
- 하지만, 초기화가 스레드 당 한번 더 이뤄질 수 있으므로, 보통은 거의 사용하지 않음


## 정리 
- 대부분의 필드는 지연시키지 말고 곧바로 초기화 할 것 
- 성능 혹은 위험한 초기화 순환을 막기 위해 지연 초기화를 사용해야 할 경우, 올바른 기법을 사용할 것 
  - 인스턴스 필드 : 이중 검사 관용구
  - 정적 필드 : 지연 초기화 홀더 클래스 관용구 
  - 반복 초기화 해도 상관 없는 경우 : 단일 검사 관용구 