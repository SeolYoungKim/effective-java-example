package chapter_08.item52;

import java.util.List;

public class Whopper {
    String patty() {
        return "패티 1장";
    }

    static class TwoStackWhopper extends Whopper {
        @Override
        String patty() {
            return "패티 2장";
        }
    }

    static class ThreeStackWhopper extends Whopper {
        @Override
        String patty() {
            return "패티 3장";
        }
    }

    public static void main(String[] args) {
        List<Whopper> whoppers = List.of(
                new Whopper(),
                new TwoStackWhopper(),
                new ThreeStackWhopper()
        );

        for (Whopper whopper : whoppers) {
            System.out.println(whopper.patty());
        }
    }
}
