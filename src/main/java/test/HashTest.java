package test;

import java.util.Objects;

public class HashTest {

    private final int number;
    private final char character;

    public HashTest(int number, char character) {
        this.number = number;
        this.character = character;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HashTest hashTest = (HashTest) o;
        return number == hashTest.number && character == hashTest.character;
    }

//    @Override
//    public int hashCode() {
//        return Objects.hash(number, character);
//    }

    public static void main(String[] args) {
        HashTest test1 = new HashTest(1, 'a');
        HashTest test2 = new HashTest(1, 'a');

        System.out.println(test1.equals(test2));

        System.out.println(test1.hashCode());
        System.out.println(test2.hashCode());
    }
}
