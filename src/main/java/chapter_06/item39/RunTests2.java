package chapter_06.item39;

import chapter_06.item39.RepeatableTest.RepeatableTestContainer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class RunTests2 {

    public static void main(String[] args) throws ClassNotFoundException {
        int tests = 0;
        int passed = 0;

        Class<?> testClass = Class.forName(args[0]);
        for (Method method : testClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(ExceptionTest.class)
                    || method.isAnnotationPresent(RepeatableTest.class)
                    || method.isAnnotationPresent(RepeatableTestContainer.class)) {
                tests++;
                try {
                    method.invoke(null);
                    System.out.printf("테스트 %s 실패: 예외를 던지지 않음%n", method);
                } catch (InvocationTargetException e) {
                    Throwable exc = e.getCause();  // 발생한 실제 예외
                    int oldPassed = passed;
                    Class<? extends Throwable>[] excTypes = method.getAnnotation(
                                    ExceptionTest.class)
                            .value();  // 애노테이션이 가리키는 예외 타입!
                    RepeatableTest[] repeatableTests = method.getAnnotationsByType(
                            RepeatableTest.class);

                    System.out.println("RepeatableTest : " + Arrays.toString(repeatableTests));

                    for (Class<? extends Throwable> excType : excTypes) {
                        if (excType.isInstance(exc)) {
                            passed++;
                            break;
                        }
                    }

                    if (passed == oldPassed) {
                        System.out.printf("테스트 %s 실패: %s%n", method, exc);
                    }

                } catch (Exception exc) {
                    System.out.println("잘못 사용한 @ExceptionTest: " + method);
                }
            }
        }
    }

}
