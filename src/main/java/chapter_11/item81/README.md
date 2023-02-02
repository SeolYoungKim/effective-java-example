# wait와 notify보다는 동시성 유틸리티를 애용하라 
- wait와 notify는 올바르게 사용하기가 아주 까다로우니 고수준 동시성 유틸리티를 사용하자 

## java.util.concurrent 고수준 유틸리티 
- ExecutorService
- Concurrent collection
- Synchronizer

## Concurrent collection
- List, Queue, Map 같은 표준 컬렉션 인터페이스에 동시성을 가미해 구현한 컬렉션 
- 동기화를 각자의 내부에서 수행 
- 동시성 컬렉션에서 동시성을 무력화 하는 것은 불가능  
- 외부에서 락을 추가로 사용할 시 속도가 오히려 느려짐

### 동시성 컬렉션에서 동시성을 무력화 하는 것은 불가능
- 여러 메서드를 원자적으로 묶어서 호출하는 일 역시 불가능
- 여러 기본 동작을 하나의 원자적 동작으로 묶는 **상태 의존적 수정** 메서드들이 추가되었음
- 예시 : `Map`의 `putIfAbsent(k, v)` 
  - 주어진 키에 매핑된 값이 아직 없을 때만 새 값을 집어넣음 
  - 기존 값이 있었다면 그 값을 반환하고, 없었다면 null을 반환 
  - 해당 메서드 덕에 안전한 정규화 맵(canonicalizing map)을 쉽게 구현할 수 있음

```java
public static String internSlowVer(String s) {
    final String previousVal = map.putIfAbsent(s, s);
    return previousVal == null ? s : previousVal;
}

public static String internFastVer(String s) {
    String result = map.get(s);  // ConcurrentHashMap은 get과 같은 검색 기능에 최적화 되어있음 
    if (result == null) {
        result = map.putIfAbsent(s, s);  // 필요할 때만 putIfAbsent를 호출하면 더 빠름 
        if (result == null) {
            result = s;
        }
    }
    return result;
}
```
- ConcurrentHashMap은 동시성이 뛰어나며 속도도 무척 빠름 

### 동시성 컬렉션은 동기화한 컬렉션을 과거의 유산으로 만들어버림 
- Collections.synchronizedMap 보다는 ConcurrentHashMap을 사용하자 
  - 특히, HashTable은 사용 ㄴㄴ 
  - 동기화 맵을 동시성 맵으로 교체하는 것만으로 동시성 앱의 성능은 극적으로 개선됨 

### 컬렉션 인터페이스 중 일부는 작업이 성공적으로 완료될 때까지 기다리도록(차단되도록) 확장 됨
- `BlockingQueue`
  - `take` : 큐의 첫 원소를 꺼냄
    - 큐가 비었다면 새 원소가 추가될 때까지 기다림 
  - 생산자-소비자 큐 (작업 큐)로 사용하기에 적합 
  - 하나 이상의 생산자(Producer) 스레드가 작업을 큐에 추가 
  - 하나 이상의 소비자(Consumer) 스레드가 큐에 있는 작업을 꺼내 처리 
  - `ThreadPoolExecutor`를 포함한 대부분의 ExecutorService에서 BlockingQueue를 사용 

```java
private static final ExecutorService executor;
private static final ExecutorCompletionService<String> execCompletion;

static {
    executor = Executors.newFixedThreadPool(10);
    execCompletion = new ExecutorCompletionService<>(executor);
}

public static void main(String[] args) throws InterruptedException {
    final Thread producer = new Thread(() -> {
        for (int i = 0; i < 100; i++) {
            System.out.printf("[%s] 데이터를 저장합니다.%n", Thread.currentThread().getName());
            execCompletion.submit(() -> "data");
            System.out.printf("[%s] 데이터를 저장 완료.%n", Thread.currentThread().getName());
        }
    });

    final Thread consumer = new Thread(() -> {
        while (true){
            System.out.printf("[%s] 데이터를 꺼냅니다.%n", Thread.currentThread().getName());
            try {
                final Future<String> take = execCompletion.take();
                System.out.printf("[%s] 꺼낸 데이터={%s}.%n",
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

// result : producer와 consumer가 함께 일할 때 
[Producer][Thread-0] 데이터를 저장합니다.
[Consumer][Thread-1] 꺼낸 데이터={data}.
[Consumer][Thread-1] 데이터를 꺼냅니다.
[Consumer][Thread-1] 꺼낸 데이터={data}.
[Consumer][Thread-1] 데이터를 꺼냅니다.
[Producer][Thread-0] 데이터를 저장 완료.
[Producer][Thread-0] 데이터를 저장합니다.
[Producer][Thread-0] 데이터를 저장 완료.
[Producer][Thread-0] 데이터를 저장합니다.
[Consumer][Thread-1] 꺼낸 데이터={data}.
[Consumer][Thread-1] 데이터를 꺼냅니다.
[Consumer][Thread-1] 꺼낸 데이터={data}

// result2 : 실시간으로 데이터를 추가할 때 (이미 블라킹 큐는 비어있는 상태에서 시작)
// 데이터를 추가하자마자 모두 꺼낸다. 
데이터를 실시간으로 저장합니다. 횟수:9
[Consumer][Thread-1] 꺼낸 데이터={data}.
[Consumer][Thread-1] 데이터를 꺼냅니다.
[Consumer][Thread-1] 꺼낸 데이터={data}.
[Consumer][Thread-1] 데이터를 꺼냅니다.
[Consumer][Thread-1] 꺼낸 데이터={data}.
[Consumer][Thread-1] 데이터를 꺼냅니다.

데이터를 실시간으로 저장합니다. 횟수:10
[Consumer][Thread-1] 꺼낸 데이터={data}.
[Consumer][Thread-1] 데이터를 꺼냅니다.
[Consumer][Thread-1] 꺼낸 데이터={data}.
[Consumer][Thread-1] 데이터를 꺼냅니다.
[Consumer][Thread-1] 꺼낸 데이터={data}.
[Consumer][Thread-1] 데이터를 꺼냅니다.

데이터를 꺼내는 동작이 종료됩니다.
```

<br/><br/>

## 동기화 장치 
- 스레드가 다른 스레드를 기다릴 수 있게 하여, 서로 작업을 조율할 수 있게 해주는 장치 
- 가장 자주 사용되는 것 
  - `CountDownLatch`
  - `Semaphore`
- 덜 쓰이는 것 
  - `CyclicBarrier`
  - `Exchanger`

### 카운트다운 래치(latch; 걸쇠)
- 일회성 장벽
- 하나 이상의 스레드가 또 다른 하나 이상의 스레드 작업이 끝날 때까지 기다리게 함 
- int 값을 받아 생성되며, 해당 값은 래치의 countDown 메서드를 몇 번 호출해야 대기 중인 스레드들을 깨우는지 결정함 

#### 사용 시 주의사항 
- 스레드 기아 교착상태(Thread starvation deadlock) 주의

### **시간 간격**을 잴 때는 `System.nanoTime`을 사용하자
- `System.nanoTime`이 `System.currentTimeMillis`보다 더 정확하고 정밀하다.
- 시스템의 실시간 시계의 시간 보정에 영향을 받지 않는다. 
- 정확한 시간을 측정하기 위해서는 jmh와 같은 특수 프레임워크를 사용하자

> 참고
> - https://stackoverflow.com/questions/351565/system-currenttimemillis-vs-system-nanotime
> - currentTimeMillis : 벽시계(현재 시간)
> - nanoTime : 프로그램의 성능 측정을 위해, 정밀하게 구간 시간 측정 가능 (JVM마다 다를 수 있어, 서버 간 통신 시간 측정 등에는 사용하면 안됨) 


## wait, notify가 사용된 레거시 코드를 다룬다면 
- `wait` : 스레드가 어떤 조건이 충족되기를 기다리게 할 때 사용
- `notify` : 락 객체의 `wait` 메서드는 반드시 그 객체를 잠근 동기화 영역 안에서 호출돼야 함 

```java
class TestClass {
    void method() {
        synchronized (obj) {
            while (/*조건이 충족되지 않음*/) {
                obj.wait();  // 락을 놓고, 깨어날 경우 다시 잡음
            }
            
            // 조건이 충족됐을 때 동작 수행 
            // ... 
        }
    }
}
```
- `wait`메서드 사용 시, 반드시 대기 반복문(wait loop)을 사용할 것.
  - 반복문 밖에서는 절대로 호출하지 말 것 
  - 해당 반복문은 `wait` 호출 전후로 조건이 만족하는지 검사하는 역할을 수행 
  - 대기 전에 조건을 검사하여 조건이 이미 충족된 경우, `wait`을 건너뛰게 한 것은 응답 불가 상태를 예방하는 조치임 
  - 조건이 충족됐는데 스레드가 `notify` 혹은 `notifyAll`을 호출한 후 대기 상태로 빠진다면 해당 스레드를 다시 꺠울 수 있다고 보장할 수 없음


- 대기 후 조건을 검사하여 조건이 충족되지 않을 경우, 다시 대기하게 하는 것 또한 안전 실패를 막는 조치임 
  - 조건이 충족되지 않았는데 동작을 이어갈 경우, 락이 보호하는 불변식을 깨뜨릴 위험이 있음 


### 조건이 만족되지 않아도 스레드가 깨어날 수 있는 경우
- 스레드가 notify 호출 -> 대기 중이던 스레드가 깨어나는 사이에 다른 스레드가 락을 얻어 그 락이 보호하는 상태를 변경 
- 조건이 만족되지 않았음에도 다른 스레드가 실수로 혹은 악의적으로 notify를 호출 
  - 공개된 객체를 락으로 사용해 대기하는 클래스는 이런 위험에 노출됨 
  - 외부에 노출된 객체의 동기화된 메서드 안에서 호출하는 wait는 모두 이 문제에 영향을 받음 
- 깨우는 스레드는 지나치게 관대하다
  - 대기 중인 스레드 중 일부만 조건이 충족되어도 notifyAll을 호출해 모든 스레드를 깨워버릴 수도 있다.
- 대기 중인 스레드가 드물게 notify 없이도 깨어나는 경우가 있다.
  - 허위 각성(spurious wakeup)

## notify vs notifyAll
- 일반적으로 notifyAll로 모든 스레드를 깨우는게 합리적이고 안전함 
  - 깨어나야 하는 모든 스레드가 깨어남을 보장 
  - 항상 정확한 결과로 이어질 수 있음 ???
  - 다른 스레드까지 깨어날 수도 있지만, 정확도에는 영향을 미치지 않을 것 ??? 
  - 깨어난 스레드들은 기다리던 조건이 충족되었는지 확인하여, 충족되지 않았다면 다시 대기할 것임
- 모든 스레드가 같은 조건을 기다리고, 조건이 한 번 충족될 때마다 단 하나의 스레드만 혜택을 받을 수 있는 경우, notifyAll 대신 notify를 사용해 최적화할 수 있음 
- 이상의 전제조건들이 만족된다 하더라도 notifyAll 을 사용해야 하는 이유
  - 외부로 공개된 객체에 대해 실수로 혹은 악의적으로 notify를 호출하는 상황에 대비하기 위해 `wait`를 반복문 내에서 호출했던 것 처럼
  - notify 대신 notifyAll을 사용하면 관련 없는 스레드가 실수로 혹은 악의적으로 `wait`를 호출하는 공격으로부터 보호할 수 있음
  - 스레드가 notify를 삼켜버릴 경우, 꼭 깨어났어야 할 스레드들이 영원히 대기하게 될 수 있음 

> 다른 조건을 기다릴 경우도 대비해서 모두 깨우는게 좋은것인가?
> - 스레드가 한개만 깨어났는데, 스레드를 필요로하는 다른 곳에 뺏김 -> 정작 필요한 곳에서 사용 못함. 이런 상황때문에 notifyAll 이 합리적인 것일까?
> - 스레드가 한개만 깨어났는데, 다른 곳에서 그 스레드를 wait으로 잠재워 버리는 경우 때문에 notifyAll을 사용한다고 함 



## 정리
- `wait`, `notify`를 직접 사용하는 것 == **어셈블리 언어로 프로그래밍 하는 것**
- `java.util.concurrent`를 사용하는 것 == **고수준 언어를 사용하는 것**
- 그러니 코드를 새로 작성하는 경우 `wait`와 `notify`를 쓸 이유가 거의(어쩌면 전혀) 없다.
- 레거시 코드를 유지보수 해야할 경우
  - `wait`는 항상 표준 관용구에 따라 `while`문 안에서 호출 
  - `notify` 보다는 `notifyAll` 사용
  - `notify`를 사용할 경우 응답 불가 상태에 빠지지 않도록 주의 