package item9;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class BadCaseTest {

    @DisplayName("첫번째 예외는 사라진다.")
    @Test
    void exTest() throws IOException {
        BadCase.supressEx("딱", "구");
    }

    @DisplayName("첫번째 예외는 사라진다.")
    @Test
    void exTest2() throws IOException {
        try {
            BadCase.supressEx("딱", "구");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

}