# 스레드 안전성 수준을 문서화하라
## 스레드 안전성 수준 
### 불변(immutable) 
- 이 클래스의 인스턴스는 마치 상수와 같아서 외부 동기화가 필요 없음
- String, Long, BigInteger

### 무조건적 스레드 안전(unconditionally thread-safe)
- 이 클래스의 인스턴스는 수정도리 수 있으나, 내부에서 충분히 동기화하여 별도의 외부 동기화 없이 동시에 사용해도 안전함
- AtomicLong, ConcurrentHashMap

### 조건부 스레드 안전(conditionally thread-safe)
- 무조건적 스레드 안전과 같으나, 일부 메서드는 동시에 사용하기 위해서는 외부 동기화가 필요함 
- Collections.synchronized 래퍼 메서드가 반환한 컬렉션들 
  - 해당 컬렉션들이 반환한 Iterator는 외부 동기화 필요

### 스레드 안전하지 않음(not thread-safe)
- 이 클래스의 인스턴스는 수정될 수 있음 
- 동시에 사용하기 위해서는 각각의(혹은 일련의) 메서드 호출을 클라이언트가 선택한 외부 동기화 메커니즘으로 감싸줘야 함
- ArrayList, HashMap 등 기본 컬렉션 

### 스레드 적대적(thread-hostile)
- 모든 메서드 호출을 외부 동기화로 감싸더라도 멀티스레드 환경에서 안전하지 않음 
- 정적 데이터를 아무 동기화 없이 수정함 
- 스레드 적대적으로 밝혀진 클래스나 메서드는 일반적으로 문제 수정 후 재배포 or `@Deprecated` API로 지정 


## 락을 공개할 경우 
- 유연해 질 수 있지만, **서비스 거부 공격(denial-of-service attack)을 수행**할 수도 있음
  - 락을 오랫동안 쥐고 놓지 않는 공격
  - synchronized 혹은 비공개 락 객체를 사용할 것 (락 필드는 항상 `final`로 선언)


