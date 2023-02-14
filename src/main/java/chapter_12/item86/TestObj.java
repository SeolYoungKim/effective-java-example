package chapter_12.item86;

import java.io.InvalidObjectException;
import java.io.Serializable;

public class TestObj implements Serializable {
    private static final long serialVersionUID = 1L;

    private void readObjectNoData() throws InvalidObjectException {
        throw new InvalidObjectException("스트림 데이터가 필요합니다.");
    }

    private static class InnerStaticClass {
        private String name;
    }

    private class InnerClass {
        private String name;
    }
}
