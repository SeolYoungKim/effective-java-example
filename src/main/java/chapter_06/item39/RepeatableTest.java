package chapter_06.item39;

import chapter_06.item39.RepeatableTest.RepeatableTestContainer;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 반복 가능한 애너테이션
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Repeatable(RepeatableTestContainer.class)
public @interface RepeatableTest {

    Class<? extends Throwable> value();

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface RepeatableTestContainer {  // 컨테이너 애너테이션
        RepeatableTest[] value();
    }
}
