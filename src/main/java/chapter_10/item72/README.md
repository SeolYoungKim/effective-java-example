# 표준 예외를 사용하라
- 예외도 재사용하는 것이 좋다.
- 자바 라이브러리는 대부분의 API에서 쓰기에 충분한 수의 예외를 제공한다.

### IllegalArgumentException
- 인수로 부적절한 값을 넘길 때 던지는 예외


### IllegalStateException
- 대상 객체의 상태가 호출된 메서드를 수행하기에 적합하지 않을 때 던지는 예외 
  - ex : 제대로 초기화된 객체를 사용하려 할 때

### ConcurrentModificationException
- 단일 스레드에서 사용하려고 설계한 객체를 여러 스레드가 동시에 수정하려 할 때 던지는 예외 

### UnsupportedOperationException
- 클라이언트가 요청한 동작을 대상 객체가 지원하지 않을 때 던지는 예외 


### 직접 재사용하면 안되는 예외 
- Exception, RuntimeException, Throwable, Error


## 정리
- 상황에 부합하다면 항상 표준 예외를 사용하자 
  - 이름 뿐 아니라, 예외가 던져지는 맥락도 부합해야 한다. 
- 더 많은 정보를 제공하고자 한다면 표준 예외를 확장하여 사용하라. 