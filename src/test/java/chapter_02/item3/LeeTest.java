package chapter_02.item3;

import chapter_02.item3.Lee;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.*;

class LeeTest {

    @DisplayName("싱글톤이 유지된다")
    @Test
    void singleton() {
        Lee singletonLee1 = Lee.getInstance();
        Lee singletonLee2 = Lee.getInstance();

        assertThat(singletonLee1 == singletonLee2).isTrue();
        assertThat(singletonLee1).isEqualTo(singletonLee2);
    }

    @DisplayName("공급자로 사용하기")
    @Test
    void supplier() {
        Supplier<Lee> supplier = Lee::getInstance;  // 공급자로 사용 가능
    }

}