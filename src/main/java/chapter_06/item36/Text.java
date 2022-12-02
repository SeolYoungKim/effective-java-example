package chapter_06.item36;

import java.util.EnumSet;
import java.util.Set;

public class Text {
    public static final int STYLE_BOLD = 1 << 0;
    public static final int STYLE_ITALIC = 1 << 1;
    public static final int STYLE_UNDERLINE = 1 << 2;
    public static final int STYLE_STRIKETROUGH = 1 << 3;

    public void applyStyles(int styles) {
        System.out.println(styles);
    }

    /**
     * 아래와 같이 비트별 OR 연산을 사용해 여러 상수를 하나의 집합으로 모을 수 있다.
     * 이를 비트 필드라고 한다.
     * 비트 필드를 사용하면 합집합, 교집합과 같은 집합 연산을 효율적으로 수행할 수 있다.
     * 하지만, 정수 열거 상수 단점을 그대로 지닌다.
     * 비트필드는 다음과 같은 문제점도 있다.
     * - 비트 필드 값이 그대로 출력되면, 해석하기가 너무 어렵다.
     * - 최대 몇 비트가 필요한지 API 작성 시 미리 예측하여 적절한 타입을 선택해야 한다.(int or long)
     * - API를 수정하지 않고는 비트수를 더 늘릴 수 없기 떄문. (32bit, 64bit)
     */
    public static void main(String[] args) {
        Text text = new Text();
        text.applyStyles(STYLE_BOLD | STYLE_ITALIC);  // 0 or 0 = 0 , 0 or 1 = 1, 1 or 1 = 1
        text.applyStyles(EnumSet.of(Style.BOLD, Style.ITALIC));
    }

    // TODO 비트 필드를 통한 집합 보다는, EnumSet을 사용하라. 집합을 효과적으로 표현해준다.
    public enum Style {
        BOLD, ITALIC, UNDERLINE, STRIKETHROUGH
    }

    public void applyStyles(Set<Style> styles) {
        System.out.println(styles);
    }
}
