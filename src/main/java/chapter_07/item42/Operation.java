package chapter_07.item42;

import java.util.function.DoubleBinaryOperator;

public enum Operation {
    PLUS("+", Double::sum),
    MINUS("-", (x, y) -> x - y),
    TIMES("*", (x, y) -> x * y),
    DIVIDE("/", (x, y) -> x / y),;

    private final String symbol;
    private final DoubleBinaryOperator op;

    Operation(String symbol, DoubleBinaryOperator op) {
        this.symbol = symbol;
        this.op = op;
    }


    @Override
    public String toString() {
        return symbol;
    }

    public double apply(double left, double right) {
        return op.applyAsDouble(left, right);
    }
}
