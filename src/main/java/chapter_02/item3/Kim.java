package chapter_02.item3;

public class Kim {

    public static final Kim SINGLETON_KIM = new Kim();

    private Kim() {
        if (SINGLETON_KIM != null) {
            throw new UnsupportedOperationException("이미 생성되어 있는 객체입니다.");
        }
    }
}
