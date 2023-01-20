# 추상화 수준에 맞는 예외를 던져라 
- 수행하려는 일과 관련 없어 보이는 예외가 튀어나올 때
  - 메서드가 저수준 예외를 처리하지 않고 바깥으로 전파해 버릴 때 종종 발생하는 상황 
  - 내부 구현 방식을 드러내어 윗 레벨 API를 오염시킴 
  - 다음 릴리스에서 구현 방식을 바꾸면 다른 예외가 튀어나와 기존 클라이언트 프로그램을 깨지게 할 수도 있음 

## 이 문제를 피하려면
- 상위 계층에서는 저수준 예외를 잡아 자신의 추상화 수준에 맞는 예외로 바꿔 던져야 함 
  - 이를 예외 번역이라고 함 

```java
try {
    // ...
} catch(LowerLevelException e) {
    throw new HigherLevelException(...);
}
```

## 예외 번역 시, 저수준 예외가 디버깅에 도움이 될 경우
```java
try {
    // ...
} catch(LowerLevelException cause) {
    throw new HigherLevelException(cause);
}
```
- 예외 연쇄 사용 
  - 문제의 근본 윈인인 저수준 예외를 고수준 예외에 실어 보내는 방식 
  - 별도의 접근자 메서드를 통해 필요하면 언제든 저수준 예외를 꺼내볼 수 있음 (`Throwable`의 `getCause()`)

예외 연쇄용으로 설계된 고수준 예외 생성자는 상위 클래스의 생성자에 이 "원인"을 건네주어 최종적으로 Throwable(Throwable) 생성자까지 건네지게 함 
```java
class HigerLevelException extends Exception {
    HigherLevelException(Throwable cause) {
        super(cause); 
    }
}
```
- 대부분의 표준 예외는 예외 연쇄용 생성자를 갖추고 있음 
- 그렇지 않은 예외라도 Throwable의 initCause 메서드를 이용해 원인을 직접 못박을 수 있음 
- 예외 연쇄는 문제의 원인을 getCause 메서드로 프로그램에서 접근할 수 있게 해줌 
  - 원인과 고수준 예외의 스택 추적 정보를 잘 통합해줌 

> 무턱대고 예외를 전파하는 것 보다는 예외 번역이 우수한 방법이나, 남용하지는 말자.
> - 가능하다면 저수준 메서드가 반드시 성공하도록 하여, 아래 계층에서는 예외가 발생하지 않도록 하자.
> - 때로는 상위 계층 메서드의 매개변수 값을 아래 계층 메서드로 건네기 전에 미리 검사하는 방법으로 이 목적을 달성하자 


## 차선책 
- 아래 계층에서 예외를 피할 수 없다면, 상위 계층에서 그 예외를 조용히 처리하여 문제를 API 호출자에게까지 전파하지 않는 방법이 있음 
- 이 경우 발생한 예외는 java.util.logging과 같은 적절한 로깅 기능을 활용하여 기록 해두자.
  - 문제를 전파하지 않으면서도, 프로그래머가 로그를 분석해 추가 조치를 취할 수 있게 해줌


## 핵심 정리 
- 아래 계층의 예외를 예방하거나, 스스로 처리할 수 없고 그 예외를 상위 계층에 그대로 노출하기 곤란한 경우 "예외 번역"을 사용하자 
- 예외 연쇄를 사용하면 상위 계층에는 맥락에 어울리는 고수준 예외를 던지면서, 근본 원인도 함께 알려주어 오류를 분석하기에 좋다.


