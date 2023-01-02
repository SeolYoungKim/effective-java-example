package chapter_09.item61;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Main {
    static Integer boxedInt;

    public static void main(String[] args) {
        Comparator<Integer> naturalOrder = (i, j) -> (i < j) ? -1 : (i == j) ? 0 : 1;

        int compare = naturalOrder.compare(new Integer(42), new Integer(42));
        System.out.println("compare = " + compare);

//        if (boxedInt == 42) {
//            System.out.println("믿을 수 없군!");
//        }

        List<Integer> li = new ArrayList<>();
        for (int i = 1; i < 50; i += 2)
            li.add(i);
    }
}
