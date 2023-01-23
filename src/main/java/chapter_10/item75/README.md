# 예외의 상세 메세지에 실패 관련 정보를 담으라 
- 자바 시스템
  - 예외를 잡지 못해 프로그램이 실패할 경우, 해당 예외의 stack trace 정보를 자동으로 출력함 
  - stack trace : 예외 객체의 toString 메서드를 호출해서 얻는 문자열 
    - 예외 클래스 이름 + 상세 메세지 
    - 사후 분석을 위해 실패 순간의 상황을 정확히 포착해 예외의 상세 메세지에 담아야 함 

## 실패 순간 포착 
- 예외에 관여된 모든 매개변수와 필드의 값을 실패 메시지에 담아야 함 
- 관련 데이터를 모두 담되, 장황할 필요는 없음. (게다가, 보안상 중요한 정보는 담아서는 안됨)
- 예외 상세 메시지 != 보여줄 오류 메시지 
  - 예외 상세 메시지 : 가독성 보다는 담긴 내용이 중요
  - 보여줄 오류 메시지 : 친절한 안내 메시지 


## 예외 정보
예외는 실패와 관련한 정보를 얻을 수 있는 접근자 메서드를 적절히 제공하는 것이 좋다.
- 정보는 예외 상황을 복구하는 데 유용할 수 있다.
  - 비검사 예외 보다는 검사 예외에서 더 빛을 발함 
  - 검사 예외에서 접근자 메서드를 이용해서 정보를 꺼내서 처리할 수 있기 때문.
- 비검사 예외라도 `toString`을 이용해서 상세 정보를 알려주는 접근자 메서드를 제공하면 좋다
```java
public class MyIndexOutOfBoundsException extends RuntimeException {
    private final int lowerBound;
    private final int upperBound;
    private final int index;

    public MyIndexOutOfBoundsException(final int lowerBound, final int upperBound,
            final int index) {
        super(String.format(
                "최솟값: %d, 최댓값: %d, 인덱스: %d",
                lowerBound, upperBound, index));

        // 정보 저장
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.index = index;
    }

    public int lowerBound() {
      return lowerBound;
    }
  
    public int upperBound() {
      return upperBound;
    }
  
    public int index() {
      return index;
    }
}
```