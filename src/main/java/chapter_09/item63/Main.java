package chapter_09.item63;

import java.util.StringJoiner;

public class Main {
    public static void main(String[] args) {
        StringJoiner stringJoiner = new StringJoiner(", ");
        stringJoiner.add("A").add("B");
        String result1 = stringJoiner.toString();
        System.out.println(result1);  // A, B

        String a = "A";
        String b = "B";
        String result2 = String.join(", ", a, b);
        System.out.println(result2);  // A, B

        StringBuilder sb = new StringBuilder();
        sb.append("A").append("B");
        String result3 = sb.toString();
        System.out.println(result3);
    }
}
