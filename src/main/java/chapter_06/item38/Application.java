package chapter_06.item38;

import java.util.Arrays;
import java.util.Collection;

//https://www.logicbig.com/how-to/code-snippets/jcode-java-enum-declaring-class.html
//https://stackoverflow.com/questions/5758660/java-enum-getdeclaringclass-vs-getclass
public class Application {

    public static void main(String[] args) {

        BasicOperation minus = BasicOperation.MINUS;
        print(minus);
        System.out.println(minus.getDeclaringClass());

        ExtendedOperation exp = ExtendedOperation.EXP;
        print(exp);
        System.out.println(exp.getDeclaringClass());

        double x = 3.0;
        double y = 3.0;

        System.out.println();
        test(ExtendedOperation.class, x, y);
        test(BasicOperation.class, x, y);

        System.out.println();
        test(Arrays.asList(ExtendedOperation.values()), x, y);
        test(Arrays.asList(BasicOperation.values()), x, y);

        System.out.println();
    }

    private static void print(Operation operation) {
        System.out.println("operation의 클래스는 " + operation.getClass() + "입니다.");
    }

    private static <T extends Enum<T> & Operation> void test(Class<T> opEnumType, double x,
            double y) {
        for (Operation op : opEnumType.getEnumConstants()) {
            System.out.printf("%.1f %s %.1f = %.1f%n", x, op, y, op.apply(x, y));
        }
    }

    private static void test(Collection<? extends Operation> opSet, double x,
            double y) {
        for (Operation op : opSet) {
            System.out.printf("%.1f %s %.1f = %.1f%n", x, op, y, op.apply(x, y));
        }
    }
}
