package test;

import java.util.Arrays;
import java.util.List;

public class CloneTest implements Cloneable {

    private final int number;
    private final char character;

    public CloneTest(int number, char character) {
        this.number = number;
        this.character = character;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        CloneTest cloneTest = new CloneTest(1, 'a');
        CloneTest clone = (CloneTest) cloneTest.clone();

        System.out.println(cloneTest != clone);
        System.out.println(cloneTest.getClass() == clone.getClass());
        System.out.println(cloneTest.equals(clone));
        System.out.println(clone.number);
        System.out.println(clone.character);
    }
}
