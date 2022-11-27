package chapter_02.item5.di;

import chapter_02.item5.di.M1;
import chapter_02.item5.di.M2;
import chapter_02.item5.di.M999;
import chapter_02.item5.di.MacBook;
import item5.di.MockAppleSiliconChip;
import org.junit.jupiter.api.Test;

class MacBookTest {

    @Test
    void diTest() {
        // 아래와 같이 사용하는 쪽에서만 리소스를 정해주면 됩니다.
        // MacBook 코드는 이와 같은 변경 사항을 알지 못합니다.

        MacBook m1MacBook = new MacBook(new M1());
        MacBook m2MacBook = new MacBook(new M2());
        MacBook m999MacBook = new MacBook(new M999());
        MacBook mockMacBook = new MacBook(new MockAppleSiliconChip());
    }

}