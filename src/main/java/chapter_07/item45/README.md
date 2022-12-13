# 스트림은 주의해서 사용하라
- 스트림 핵심 개념
  - 스트림은 데이터 원소의 유한 혹은 무한 시퀀스를 뜻함
  - 스트림 파이프라인은 이 원소들로 수행하는 연산 단계를 표현하는 개념 


- 스트림 파이프라인
  - 소스 스트림 -> 중간 연산(Optional) -> 종단 연산 
  - 중간 연산 : 스트림을 어떠한 방식으로 변환 함
  - 지연 평가 (lazy evaluation)
    - 종단 연산이 호출될 때 평가가 이뤄짐 
    - 종단 연산에 쓰이지 않는 데이터 원소는 계산에 쓰이지 않음 


- fluentAPI
  - 메서드 연쇄를 지원함

    

### 과유불급 
- 스트림을 과용하면 프로그램을 읽거나 유지보수하기 어려워진다


### char 값들을 처리할 때는 스트림을 삼가는 편이 낫다 -> 왜 ?
- 형변환을 명시적으로 해줘야 해서 ? 


### 코드 블록은 되지만 함수 객체는 안되는 것 
- 코드 블록
  - 범위 안의 지역 변수를 읽고 수정할 수 있음
  - return문을 사용해 메서드에서 빠져나갈 수 있음
  - break, continue 문으로 블록 바깥의 반복문을 종료하거나, 반복을 한번 건너뛸 수 있음 
  - 검사 예외를 던질 수 있음 


- 함수 객체(람다)
  - final이거나 사실상 final인 변수만 읽을 수 있고, 지역 변수를 수정할 수 없음 
  - 위의 모든 것을 할 수 없음 


### 스트림에 아주 안성맞춤인 연산 
- 원소들의 시퀀스를 일관되게 변환함 
- 원소들의 시퀀스를 필터링함 
- 원소들의 시퀀스를 하나의 연산을 사용해 결합함 (더하기, 연결하기, 최솟값 구하기 등) 
- 원소들의 시퀀스를 컬렉션에 모음 
- 원소들의 시퀀스에서 특정 조건을 만족하는 원소를 찾음 


### 스트림으로 처리하기 어려운 일 
- 한 데이터가 파이프라인의 여러 단계를 통과할 때, 이 데이터의 각 단계에서의 값들에 동시에 접근하기 어렵다. 
  - 스트림 파이프라인은 일단 한 값을 다른 값에 매핑하고 나면, 원래의 값은 잃는 구조이기 때문
  - 원래의 값과 새로운 값의 쌍을 저장하는 객체를 사용해 매핑 우회할 수 있으나, 만족스러운 해법은 아닐 것 
  - 매핑 객체가 필요한 단계가 여러곳이라면..?  -> 코드 양도 많고 지저분해지기 때문에 스트림을 사용하지 않는게 나을 것임 
  - 가능한 경우라면 앞 단계 값이 필요할 때 매핑을 거꾸로 수행하는 방법이 나을 것임.. <- ?

