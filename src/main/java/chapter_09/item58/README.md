# 전통적인 for문 보다는 for-each문을 사용하라 
## for-each
- 향상된 for 문 (enhanced for statement)
- 반복자, 인덱스 변수를 사용하지 않으니 코드가 깔끔해지고 오류가 날 일이 없어진다.

```java
for (Element e : elements){
        ... // e로 무언갈 함     
}
```

## 사용할 수 없는 상황 
- 파괴적인 필터링(destructive filtering)
  - 컬렉션을 순회하면서 선택된 원소를 제거해야 한다면 반복자의 remove를 호출해야 함 (for-each 사용 불가)
  - 이런 경우 `Collection`의 `removeIf` 사용
- 변형(transforming)
  - 리스트나 배열을 순회하면서 그 원소의 값 일부 혹은 전체를 교체해야 할 경우 사용 불가 
  - 전체를 교체해야 한다면 리스트의 반복자나 배열의 인덱스를 사용해야 함 
- 병렬 반복(parallel iteration)
  - 여러 컬렉션을 병렬로 순회해야 할 경우, 각 반복자와 인덱스 변수를 사용ㅇ해 엄격하고 명시적으로 제어해야 함 


## 정리 
- for-each는 `Iterable`을 구현한 객체면 뭐든지 순회할 수 있다.
- 전통적인 for문과 비교했을 때 for-each문은 명료하고, 유연하고, 버그를 예방해준다.
- 성능 저하도 없다.
- 가능한 모든 곳에서 for 대신 for-each를 사용하자. 