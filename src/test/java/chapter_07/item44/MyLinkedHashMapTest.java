package chapter_07.item44;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class MyLinkedHashMapTest {

    @Test
    void name() {
        MyLinkedHashMap<String, Integer> userInfo = new MyLinkedHashMap<>();
        for (int i = 1; i <= 150; i++) {
            userInfo.put("유저" + i, i);
        }

        assertThat(userInfo).hasSize(100);
        assertThat(userInfo.get("유저1")).isNull();
        assertThat(userInfo.get("유저50")).isNull();
        assertThat(userInfo.get("유저51")).isNotNull();
    }
}