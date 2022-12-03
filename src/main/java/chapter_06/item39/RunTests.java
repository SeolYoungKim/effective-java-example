package chapter_06.item39;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

//https://www.baeldung.com/java-method-reflection
//https://www.baeldung.com/java-invoke-static-method-reflection
public class RunTests {

    public static void main(String[] args) throws Exception {
        int tests = 0;
        int passed = 0;

        Class<?> testClass = Class.forName(args[0]);  // 정규화된 클래스 이름을 받아, @Test 애너테이션이 달린 메서드를 차례로 호출함.
        for (Method method : testClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Test.class)) {  // @Test 애너테이션이 달린 메서드를 찾아줌.
                tests++;
                try {
                    // 아래의 경우에는, 인스턴스를 null로 넘겨주었다 -> static 메서드만 실행한다.
                    // 인스턴스 메서드를 실행하려면, obj에 그 메서드를 가진 인스턴스를 넘겨줘야 한다.(new로 생성된)
                    method.invoke(null);  // 테스트 메서드 실행
                    passed++;
                } catch (InvocationTargetException e) {  // 예외를 이걸로 감싸서 다시 던짐. (Invocation의 타겟이 된 메서드가 발생시키는 예외를 잡는 것)
                    Throwable exc = e.getCause();  // 실패 정보 추출
                    System.out.println(method + " 실패: " + exc);
                } catch (Exception exc) {  // InvocationTargetException 이외의 예외는 @Test 메서드를 잘못 사용했다는 것임.
                    System.out.println("잘못 사용한 @Test: " + method);
                }
            }
        }

        System.out.printf("성공: %d, 실패: %d%n", passed, tests - passed);
    }

}
