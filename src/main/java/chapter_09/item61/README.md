# 박싱된 기본 타입 보다는 기본 타입을 사용하라 
## 자바의 데이터 타입
- 기본 타입 
- 참조 타입 

## 기본 타입 vs 박싱된 기본 타입 
- 기본 타입은 값만 가지고 있지만, 박싱된 기본 타입은 값 + 식별성(identity) 속성을 가짐
  - 박싱된 기본 타입의 두 인스턴스는 값이 같아도 서로 다르다고 식별될 수 있음 
- 기본 타입의 값은 언제나 유효하나, 박싱된 기본 타입은 유효하지 않은 값, 즉 `null`을 가질 수 있음 
- 기본 타입이 박싱된 기본 타입보다 시간과 메모리 사용면에서 더 효율적임 

## 문제가 생기는 예시 1 
```java
Comparator<Integer> naturalOrder = (i, j) -> (i < j) ? -1 : (i == j) ? 0 : 1;

int compare = naturalOrder.compare(new Integer(42), new Integer(42));
System.out.println("compare = " + compare);
```
- 위 결과는 0이 아닌 1이 출력된다.
- i, j가 참조하는 오토박싱된 Integer 인스턴스가 기본 타입 값으로 변환 됨 
  - 오토 박싱은 자바 컴파일러가 `Integer.valueOf`를 넣어줌으로써 해결한다.
- 두 번째 검사인 i==j에서, 두 인스턴스가 다르다고 판단되어 1이 반환됨 

즉, 박싱된 기본 타입에 `==`연산자를 사용하면 오류가 발생한다.


## 문제가 생기는 예시 2
```java
public class Main {
    static Integer i;

    public static void main(String[] args) {
        if (i == 42) {
            System.out.println("믿을 수 없군!");
        }
    }
}
```
- 위 예시는 `NullPointerException`을 던진다. 
- i가 int가 아닌 Integer이며, 초깃값이 null이기 때문이다.
  - `i == 42`는 Integer와 int를 비교하는 것이다.
  - 기본 타입과 박싱된 기본 타입을 혼용한 연산에서는 박싱된 기본 타입의 박싱이 자동으로 풀린다.
  - null을 언박싱하면 `NullPointerException`이 발생한다.


## 박싱된 기본 타입의 사용처 
- 컬렉션의 원소, 키, 값으로 사용 
  - 컬렉션은 기본 타입을 담을 수 없으므로 
- 리플렉션을 통해 메서드를 호출할 때


## 정리 
- 가능하면 기본 타입을 써라 (간단하고 빠름)
- 오토박싱이 박싱된 기본 타입을 사용할 때의 번거로움을 줄여주나, 그 위험까지 없애주지는 얺는다.
- 기본 타입과 박싱된 기본 타입을 혼용하면 언박싱이 발생함
  - 언박싱 과정에서 `NullPointerException`을 던질 수 있음 
- 기본 타입을 박싱하는 작업은 필요 없는 객체를 생성하는 부작용을 낳을 수 있음 
