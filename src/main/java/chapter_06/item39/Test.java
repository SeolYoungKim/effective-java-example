package chapter_06.item39;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 마커 애너테이션 : 아무 파라미터 없이, 단순히 대상에 "마킹"하는 애너테이션.
 *
 * 이 애너테이션은 static 메서드에만 사용하시오
 * -> 이걸 강제하려면 애너테이션 프로세서가 필요하다. 지금은 강제 불가능.
 *
 * 애너테이션은 추가 정보를 제공할 뿐이다.
 * 대상 코드의 의미는 그대로 둔 채, 그 애너테이션에 관심있는 도구에서 특별한 처리를 할 기회를 준다.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Test {
}
