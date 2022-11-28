package chapter_06.item34;

import java.util.Arrays;

public class WeightTable {

    public static void main(String[] args) {
        double earthWeight = Double.parseDouble("185");
        double mass = earthWeight / Planet.EARTH.surfaceGravity();
        Arrays.stream(Planet.values())
                .forEach(planet -> System.out.println(planet + " : " + planet.surfaceWeight(mass)));
    }
}
