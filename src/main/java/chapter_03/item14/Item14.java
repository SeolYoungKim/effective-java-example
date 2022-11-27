package chapter_03.item14;

import java.math.BigDecimal;
import java.util.Comparator;

public class Item14 {
    public static void main(String[] args) {
        int compare = Float.compare(1.0f, 2.0f);
        int compare1 = Double.compare(2.0, 1.0);
        System.out.println(compare);
        System.out.println(compare1);

        Human a = new Human("2", 20);
        Human b = new Human("2", 15);
        Human c = new Human("1", 15);

        int compare2 = COMPARATOR.compare(a, b);
        System.out.println("compare2 = " + compare2);

        int compare3 = COMPARATOR.compare(b, c);
        System.out.println("compare3 = " + compare3);

        int compare4 = COMPARATOR.compare(a, c);
        System.out.println("compare4 = " + compare4);

        System.out.println(Integer.MIN_VALUE - 1);
        System.out.println(Integer.MAX_VALUE + 1);

        int i = 1;
        double d = 0.1;

        System.out.println(i - d * 9);

        BigDecimal bd = BigDecimal.valueOf(0.1);
        System.out.println(BigDecimal.valueOf(1).min(bd.multiply(BigDecimal.valueOf(9))));
    }

    // 성능 비교를 다시 한번 해볼때가 됨..ㅋㅋ 10% 정도 까지는 개의치않고 써도 됨.
    // 비교가 자주 사용이 될 때, 성능 bottleneck이 여기다 싶을 때 사용하지 마라. (그럴 일이 잘 없어요)
    // CompareTo는 natural order를 지원할 클래스에 작성하자.
    private static final Comparator<Human> COMPARATOR = Comparator.comparing((Human human) -> human.name)
            .thenComparingInt(human -> human.age);
}

class Human {
    public final String name;
    public final int age;

    public Human(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
