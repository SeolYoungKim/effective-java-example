package item9;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class GoodCaseTest {

    @DisplayName("첫번째 예외를 보여준다. 두번째 예외는 suppressed로 명시")
    @Test
    void exTest() throws IOException {
        GoodCase.supressEx("딱", "구");
    }

    @DisplayName("첫 번째 예외를 보여준다.")
    @Test
    void exTest2() throws IOException {
        try {
            GoodCase.supressEx("딱", "구");
        } catch (Throwable e) {
            System.out.println("첫 번째 예외 메시지" + e.getMessage());
            System.out.println("Suppressed : " + Arrays.toString(e.getSuppressed()));
        }
    }

}