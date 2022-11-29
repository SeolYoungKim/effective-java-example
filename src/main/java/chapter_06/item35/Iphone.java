package chapter_06.item35;

public enum Iphone {

    IPHONE_4, IPHONE_5, IPHONE_6, IPHONE_7, IPHONE_8, IPHONE_X;

    public int orderOfRelease() {
        return ordinal() + 1;
    }

    public static void main(String[] args) {
        int orderOfRelease = Iphone.IPHONE_4.orderOfRelease();
        System.out.println(orderOfRelease);
    }

}
