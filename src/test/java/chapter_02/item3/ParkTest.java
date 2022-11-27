package chapter_02.item3;

import chapter_02.item3.Park;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ParkTest {

    @DisplayName("Enum은 싱글톤이다.")
    @Test
    void enumTest() {
        Park park1 = Park.INSTANCE;
        Park park2 = Park.INSTANCE;
        Park park3 = Park.INSTANCE;

        park1.hahaha();

        System.out.println("park1 = " + park1);
        System.out.println("park2 = " + park2);
        System.out.println("park3 = " + park3);

        assertThat(park1).isEqualTo(park2).isEqualTo(park3);
        assertThat(park1 == park2 && park2 == park3).isTrue();
    }

    @DisplayName("Enum은 인터페이스를 구현해서 싱글톤으로 쓰세요")
    @Test
    void enumInterface() {
        Park instance = Park.INSTANCE;
        instance.hahaha();
    }


}