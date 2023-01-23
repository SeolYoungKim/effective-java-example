package chapter_10.item75;

public class MyIndexOutOfBoundsException extends RuntimeException {
    private final int lowerBound;
    private final int upperBound;
    private final int index;

    public MyIndexOutOfBoundsException(final int lowerBound, final int upperBound,
            final int index) {
        super(String.format(
                "최솟값: %d, 최댓값: %d, 인덱스: %d",
                lowerBound, upperBound, index));

        // 정보 저장
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.index = index;
    }

    public int lowerBound() {
        return lowerBound;
    }

    public int upperBound() {
        return upperBound;
    }

    public int index() {
        return index;
    }
}
