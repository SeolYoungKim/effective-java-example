package chapter_06.item38;

public enum EnumA implements EnumInterface {
    ENUM_A{
        @Override
        public void test() {
            System.out.println("이 기능은 B에서 중복되어 사용된다.");
        }
    };

    public static void main(String[] args) {
        System.out.println(EnumA.class);
    }
}
