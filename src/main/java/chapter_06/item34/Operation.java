package chapter_06.item34;

import java.util.Arrays;
import java.util.function.BinaryOperator;

public enum Operation {

    PLUS("+", (x, y) -> x + y),
    MINUS("-", (x, y) -> x - y),
    TIMES("*", (x, y) -> x * y),
    DIVIDE("/", (x, y) -> x / y),
    ;

    private final String symbol;
    private final BinaryOperator<Integer> binaryOperator;

    Operation(String symbol, BinaryOperator<Integer> binaryOperator) {
        this.symbol = symbol;
        this.binaryOperator = binaryOperator;
    }

    public int operationResult(int x, int y) {
        return binaryOperator.apply(x, y);
    }

    // 기존 열거 타입에 상수 별 동작을 혼합해서 넣을 때는 switch문이 좋은 선택이 될 수 있음.
    public static Operation inverse(Operation operation) {
        switch (operation) {
            case PLUS:
                return MINUS;
            case MINUS:
                return PLUS;
            case TIMES:
                return DIVIDE;
            case DIVIDE:
                return TIMES;

            default:
                throw new AssertionError("알 수 없는 연산 : " + operation);
        }
    }

    public static void main(String[] args) {
        Arrays.stream(values())
                .forEach(operation -> System.out.println(operation.operationResult(16, 4)));
    }
}
