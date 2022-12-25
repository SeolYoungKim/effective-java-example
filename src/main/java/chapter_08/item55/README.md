# 옵셔널 반환은 신중히 하라 
- `Optional.of`
- `Optional.ofNullable`
- `Optional.empty`

### Optional은 checked exception과 취지가 비슷하다
- 반환 값이 없을 수도 있음을 API 사용자에게 명확히 알려줌 
- 값을 처리하는 방법
  - `orElse`
  - `orElseGet`
    - 기본 값을 설정하는 비용이 너무 커서 부담이 될 때 `Supplier<T>`를 인수로 받는 `orElseGet` 사용 
  - `orElseThrow`
- `filter`, `map`, `flatMap`, `ifPresent` 지원
- `isPresent`
- `stream` 지원
  - `Optional`을 스트림으로 변환 
  - 값이 있으면 그 값을 원소로 담은 스트림으로, 없다면 빈 스트림으로 변환 


### 주의 
- 컬렉션, 스트림, 배열, 옵셔널 같은 컨테이너 타입은 옵셔널로 감싸면 안됨 
- 컨테이너 타입은 그냥 빈 컨테이너를 반환해주자
  - `Optional<List<T>>`보다는 `Collections.emptyList()`


### 어떤 경우에 `Optional<T>`를 써야할까?
- 결과가 없을 수 있으며, 클라이언트가 이 상황을 특별하게 처리해야 할 경우


### 기본 타입 전용 Optional
- `OpitonalInt`, `OptionalLong`, `OptionalDouble`


### 옵셔널을 컬렉션의 키, 값, 원소나 배열의 원소로 사용하는게 적절한 상황은 거의 없다.

### 인스턴스 필드에 옵셔널을 저장해두는 것은?
- 이런 상황 대부분은 **필수 필드를 갖는 클래스**와 **이를 확장해 선택적 필드를 추가한 하위 클래스**를 따로 만들어야 함을 암시하는 나쁜 냄새임 


> - 값을 반환하지 못할 가능성이 있고, 호출할 때 마다 반환 값이 없을 가능성을 염두에 둬야 하는 메서드라면 옵셔널을 반환해야 할 상황일 수 있음
> - 옵셔널 반환에는 성능 저하가 뒤따르므로, 성능에 민감한 메서드라면 null이나 예외를 던지는 편이 나을 수 있음
> - 옵셔널은 반환값 이외의 용도로 쓰이는 경우는 매우 드뭄 