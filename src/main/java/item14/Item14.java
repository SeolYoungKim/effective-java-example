package item14;

import java.math.BigDecimal;
import java.util.Comparator;

import static java.util.Comparator.*;

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
    }

    private static final Comparator<Human> COMPARATOR = comparing((Human human) -> human.name)
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
