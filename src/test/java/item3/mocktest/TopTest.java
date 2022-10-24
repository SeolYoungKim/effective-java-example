package item3.mocktest;

import item3.mocktest_success.Top2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class TopTest {

    @DisplayName("Top 객체에 대해 독립적인 테스트를 할 수 없습니다.")
    @Test
    void fail() {
        Top top = new Top(PandaTeemo.PANDA_TEEMO);  // 아무런 기능이 없는 Mock 객체를 만들어서 사용할 수 없습니다. 싱글톤 객체를 생성하여 사용해야 합니다.
        top.fight();

        // 해당 테스트는 독립적인 테스트가 아닙니다.
        // Top 객체의 Test만을 원했지만, 싱글톤 객체인 판다 티모 객체가 관여하기 때문입니다.
        assertThat(top.init).isTrue();
        assertThat(top.teemoIsDead).isTrue();
    }

    @DisplayName("Top2 객체에 대해 독립적인 테스트를 할 수 있습니다.")
    @Test
    void success() {
        Top2 top2 = new Top2(new MockTeemo());  // 싱글톤 오메가 티모가 생성되지 않습니다.
        top2.fight();

        assertThat(top2.init).isTrue();
        assertThat(top2.teemoIsDead).isTrue();
    }
}