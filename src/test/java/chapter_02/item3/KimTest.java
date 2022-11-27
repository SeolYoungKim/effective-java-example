package chapter_02.item3;

import chapter_02.item3.Kim;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.*;

class KimTest {

    @DisplayName("싱글톤이 유지된다.")
    @Test
    void singleton() {
        Kim singletonKim1 = Kim.SINGLETON_KIM;
        Kim singletonKim2 = Kim.SINGLETON_KIM;

        System.out.println("singletonKim1 = " + singletonKim1);
        System.out.println("singletonKim2 = " + singletonKim2);

        assertThat(singletonKim1).isEqualTo(singletonKim2);
        assertThat(singletonKim1 == singletonKim2).isTrue();
    }

    @DisplayName("리플렉션으로 접근할 경우에는 접근하여 생성할 수 있다.")
    @Test
    void reflection() throws Exception {
        Kim singletonKim = Kim.SINGLETON_KIM;

        Arrays.stream(Kim.class.getDeclaredConstructors())
                .forEach(constructor -> {
                    constructor.setAccessible(true);
                    try {
                        Kim reflectedKim = (Kim) constructor.newInstance();
                        System.out.println("reflectedKim = " + reflectedKim);
                    } catch (InstantiationException | IllegalAccessException | UnsupportedOperationException | InvocationTargetException e) {
                        System.out.println("객체가 생성되지 않습니다!!! 해당 객체는 싱글톤으로 유지되어야 합니다.");
                    }
                });

        System.out.println("singletonKim = " + singletonKim);
    }

}