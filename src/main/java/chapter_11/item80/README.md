# 스레드보다는 실행자, 태스크, 스트림을 애용하라 
## java.util.concurrent
- `Executor Framework`라고 하는 인터페이스 기반의 유연한 태스크 실행 기능을 담고 있음 

## ExecutorService의 주요 기능들!
```java
public class Main {
    public static void main(String[] args) {
        final ExecutorService exec = Executors.newSingleThreadExecutor();
        exec.submit(() -> System.out.println("메서드 실행"));
        exec.shutdown();
    }
}
```
### 1. 특정 태스크가 완료되기를 기다림 
### 2. task 모음 중 아무거나 하나(invokeAny 메서드) 혹은 모든 태스크(invokeAll 메서드)가 완료되기를 기다림
```java
public class Main {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        final ExecutorService exec = Executors.newSingleThreadExecutor();
        final List<Callable<Integer>> tests = List.of(new Test(), new Test(), new Test(),
                new Test(), new Test(),
                new Test());

        final long before = System.currentTimeMillis();
        exec.invokeAll(tests);
        final long after = System.currentTimeMillis();

        System.out.println(((after - before) / 1000) + " sec");
        exec.shutdown();
    }

    private static class Test implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            Thread.sleep(1000);
            System.out.println("[" + Thread.currentThread().getName() + "]");
            return 1;
        }
    }
}

// result : 6개의 작업이 모두 완료되기를 기다림.
[pool-1-thread-1]
[pool-1-thread-1]
[pool-1-thread-1]
[pool-1-thread-1]
[pool-1-thread-1]
[pool-1-thread-1]
6 sec

// threadPool의 스레드를 6개로 설정하고 해보면 다음과 같은 결과 
final ExecutorService exec = Executors.newFixedThreadPool(6);
[pool-1-thread-3]
[pool-1-thread-1]
[pool-1-thread-2]
[pool-1-thread-5]
[pool-1-thread-6]
[pool-1-thread-4]
1 sec
```

### 3. ExecutorService가 종료하기를 기다림(awaitTermination 메서드)
```java
if (exec.awaitTermination(5, TimeUnit.SECONDS)) {
    System.out.println("안전하게 종료되었습니다.");
}

// result
[pool-1-thread-1]
[pool-1-thread-1]
[pool-1-thread-1]
[pool-1-thread-1]
[pool-1-thread-1]
[pool-1-thread-1]
6 sec
안전하게 종료되었습니다.
```

### 4. 완료된 태스크들의 결과를 차례로 받음(ExecutorCompletionService 이용)
- `ExecutorCompletionService`는 기본적으로 `LinkedBlockingQueue`를 사용한다. 
  - 해당 자료구조의 head는 가장 오래 있었던 요소, tail은 가장 짧은 시간 동안 대기열에 있었던 요소다
  - `Callable`을 먼저 수행하고 `Future`를 먼저 반환한 스레드의 반환값이 가장 먼저 queue에 들어간다.
  - `take()`메서드는 가장 오랫동안 큐에 들어있었던 결과물(가장 먼저 들어갔던 결과물)을 꺼내온다. (*복습 : Future는 연산이 완료될 때까지 기다린다.)
```java
public class Main2 {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        singleThread();
        multiThread();
    }

    private static void singleThread() throws InterruptedException, ExecutionException {
        final ExecutorService exec = Executors.newSingleThreadExecutor();
        executorService(exec);
    }

    private static void multiThread() throws ExecutionException, InterruptedException {
        final ExecutorService exec = Executors.newFixedThreadPool(10);
        executorService(exec);
    }

    private static void executorService(final ExecutorService exec)
            throws InterruptedException, ExecutionException {
        final ExecutorCompletionService<String> execCompletion = new ExecutorCompletionService<>(
                exec);

        final List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            numbers.add(i);
        }

        for (int i = 0; i < 10; i++) {
            final Integer number = numbers.get(i);
            execCompletion.submit(() -> String.format("[%s] Callable 반환값 %d",
                    Thread.currentThread().getName(), number));
//            execCompletion.submit(() -> System.out.println("Runnable 사용법"), "반환값을 넣어줘야 함");
        }

        for (int i = 0; i < 10; i++) {
            final Future<String> take = execCompletion.take();
            System.out.println(take.get());
        }

        exec.shutdown();
    }
}

// singleThread result 
[pool-1-thread-1] Callable 반환값 1
[pool-1-thread-1] Callable 반환값 2
[pool-1-thread-1] Callable 반환값 3
[pool-1-thread-1] Callable 반환값 4
[pool-1-thread-1] Callable 반환값 5
[pool-1-thread-1] Callable 반환값 6
[pool-1-thread-1] Callable 반환값 7
[pool-1-thread-1] Callable 반환값 8
[pool-1-thread-1] Callable 반환값 9
[pool-1-thread-1] Callable 반환값 10

// 멀티 스레드를 사용하면 어떻게 나올까?
// multiThread result
[pool-1-thread-9] Callable 반환값 9
[pool-1-thread-1] Callable 반환값 1
[pool-1-thread-10] Callable 반환값 10
[pool-1-thread-5] Callable 반환값 5
[pool-1-thread-7] Callable 반환값 7
[pool-1-thread-2] Callable 반환값 2
[pool-1-thread-4] Callable 반환값 4
[pool-1-thread-8] Callable 반환값 8
[pool-1-thread-6] Callable 반환값 6
[pool-1-thread-3] Callable 반환값 3
```

### 5. 태스크를 특정 시간에 혹은 주기적으로 실행하게 한다.(ScheduledThreadPoolExecutor)
```java
//https://codechacha.com/ko/java-scheduled-thread-pool-executor/
public class ScheduledThreadPoolExecutorTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        final ScheduledThreadPoolExecutor scheduledExec = new ScheduledThreadPoolExecutor(2);
//        runnable(scheduledExec);
//        callable(scheduledExec);
//        atFixRate(scheduledExec);
        withFixedDelay(scheduledExec);
    }

    private static void runnable(ScheduledThreadPoolExecutor exec) {
        final Runnable runnable = () -> System.out.println("Runnable task : " + LocalTime.now());
        final int delay = 3;

        // job scheduling
        System.out.println("Scheduled task : " + LocalTime.now());
        exec.schedule(runnable, delay, TimeUnit.SECONDS);  // 3초 후 Runnable 실행
        exec.shutdown();
    }

    private static void callable(ScheduledThreadPoolExecutor exec)
            throws ExecutionException, InterruptedException {
        final Callable<String> callable = () -> "Callable task : " + LocalTime.now();
        final int delay = 3;

        // job scheduling
        System.out.println("Scheduled task : " + LocalTime.now());
        final ScheduledFuture<String> future = exec.schedule(callable, delay,
                TimeUnit.SECONDS);// 3초 후 Callable 실행

        // 결과 리턴 대기 (Future는 결과가 나올 때까지 대기한다)
        final String result = future.get();
        System.out.println(result);
        exec.shutdown();
    }

    private static void atFixRate(ScheduledThreadPoolExecutor exec) {
        Runnable runnable = runnableForRepeat();

        final int initialDelay = 2;  // 2초 후에 시작
        final int delay = 3;  // 3초마다 Runnable 반복 (3초에 1회 Runnable 수행)

        // job scheduling
        System.out.println("Scheduled task : " + LocalTime.now());
        exec.scheduleAtFixedRate(runnable, initialDelay, delay, TimeUnit.SECONDS);

        sleepSec(20);
        exec.shutdown();
    }

    private static Runnable runnableForRepeat() {
        return () -> {
            System.out.println("++ Repeat task : " + LocalTime.now());
            sleepSec(3);
            System.out.println("-- Repeat task : " + LocalTime.now());
        };
    }

    private static void sleepSec(int sec) {
        try {
            TimeUnit.SECONDS.sleep(sec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private static void withFixedDelay(ScheduledThreadPoolExecutor exec) {
        Runnable runnable = runnableForRepeat();

        final int initialDelay = 2;  // 2초 후에 시작
        final int delay = 3;  // Runnable이 완료되고 난 이후 3초 후 Runnable 반복 (각 실행 간 3초 딜레이 존재)

        // job scheduling
        System.out.println("Scheduled task : " + LocalTime.now());
        exec.scheduleWithFixedDelay(runnable, initialDelay, delay, TimeUnit.SECONDS);

        sleepSec(20);
        exec.shutdown();
    }
}
```

## 스레드 풀
- 스레드 풀의 스레드 개수는 고정할 수도 있고, 필요에 따라 늘어나거나 줄어들게 설정할 수도 있음 
  - `java.util.concurrent.Executors`의 정적 팩터리를 이용해 Executor 생성 
  - `ThreadPoolExecutor`를 직접 사용하면 모든 속성 설정 가능 


## ExecutorService를 사용하기 까다로운 애플리케이션 
### `newCachedThreadPool` 
- 작은 프로그램이나 가벼운 서버에서는 일반적으로 좋은 선택 
- 무거운 프로덕션 서버에는 좋지 않음 
  - 해당 스레드 풀은 **요청받은 태스크들이 큐에 쌓이지 않고 즉시 스레드에 위임돼 실행**되기 때문 
  - 위와 같은 특성 때문에, **가용 스레드가 없을 경우 새로 하나를 만들어버림**
  - 서버가 아주 무거운 경우 **CPU 이용률이 100%로 치닫을 것** 
  - 게다가 새로운 태스크가 도착하는 족족 또다른 스레드를 생성하며 **상황을 더욱 악화**시킬 것 

> 그러니, 무거운 서버에서는 `newFixedThreadPool`을 선택하거나 완전히 통제할 수 있는 `ThreadPoolExecutor`를 직접 사용하자 

## 주의점
- 작업 큐를 손수 만드는 일은 삼가야 함
- 스레드를 직접 다루는 것도 일반적으로 삼가야 함 
  - 스레드를 직접 다룰 경우, `Thread`가 작업 단위와 수행 메커니즘 역할을 모두 수행하게 됨 
  - 반면, Executor 프레임워크에서는 **작업 단위와 실행 메커니즘이 분리**됨
  
## 기본 개념
### 태스크
- 작업 단위를 나타내는 핵심 추상 개념
- 종류
  - `Runnable` : 값을 반환하지 않음 
  - `Callable` : 값을 반환하고 임의의 예외를 던질 수 있음


### ExecutorService
- 태스크를 수행하는 **일반적인 메커니즘**
- 태스크 수행을 `ExecutorService`에 맡기면 **원하는 태스크 수행 정책을 선택**할 수 있음
  - 생각이 바뀔 경우 언제든 변경 가능 
- 핵심 : **컬렉션 프레임워크가 데이터 모음을 담당하듯, Executor 프레임워크는 작업 수행을 담당해줌**


### 포크 조인 태스크 (fork-join)
- `ForkJoinPool`이라는 특별한 `ExecutorService`가 실행 해줌
- `ForkJoinTask`의 인스턴스는 작은 하위 태스크로 나뉠 수 있음 
- `ForkJoinPool`을 구성하는 스레드들이 해당 태스크들을 처리함 
- 일을 먼저 끝낸 스레드는 다른 스레드의 남은 태스크를 가져와 대신 처리 할수도 있음
- 모든 스레드가 바쁘게 움직여 CPU를 최대한 활용하면서 **높은 처리량과 낮은 지연시간**을 달성함 

> 이를 직접 작성하고 튜닝하는 것은 매우 어렵다. 하지만, 포크-조인 풀을 이용해서 만든 **병렬 스트림**을 사용하면 적은 노력 만으로도 그 이점을 얻을 수 있다. 
> - 물론 이는 포크-조인에 적합한 형태의 작업이어야 한다.


