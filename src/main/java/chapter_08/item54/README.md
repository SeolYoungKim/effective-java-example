# null이 아닌, 빈 컬렉션이나 배열을 반환하라 
- null을 반환할 경우, 클라이언트는 null을 처리하는 코드를 추가로 작성해야 한다.

- 빈 컬렉션 반환하기 
  - `Collections.emptyList`, `Collections.emptySet`, `Collections.emptyMap`...

- 빈 배열 반환하기 
  - `List.of(1, 2, 3).toArray(new Integer[0])`