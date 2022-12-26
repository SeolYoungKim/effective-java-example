# 공개된 API 요소에는 항상 문서화 주석을 작성하라 
## API를 올바르게 문서화 하기 위해서는..
- 공개된 모든 클래스, 인터페이스, 메서드, 필드 선언에 문서화 주석을 달아야 함

## 메서드
메서드용 문서화 주석에는 해당 메서드와 클라이언트 사이의 규약을 명료하게 기술해야 함 
- 상속용이 아니라면 무엇을 해야하는지 기술 
  - how가 아닌 what
- 클라이언트가 해당 메서드를 호출하기 위한 전제조건(precondition) 모두 나열
  - `@throws`로 비검사 예외 선언. 비검사 예외 하나 -> 전제조건 하나와 연결 
  - `@param`으로 그 조건에 영향받는 매개변수에 대한 기술 
- 메서드가 성공적으로 수행된 후에 만족해야 하는 사후조건(postcondition) 모두 나열
- 부작용도 문서화 
  - 부작용 : 사후조건으로 명확히 나타나지는 않지만, 시스템 상태에 어떠한 변화를 가져오는 것
  - 예를 들어, 백그라운드 스레드를 시작시키는 메서드의 경우, 해당 사실을 문서에 밝힌다 
- 예시 : `BigInteger`의 API 문서를 보자 
- `@implspec` : 해당 메서드와 하위 클래스 사이의 계약을 설명 
- 클래스 혹은 정적 메서드가 스레드 안전하든 안전하지 않든, 스레드 안전 수준을 API 설명에 포함하자.