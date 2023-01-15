package chapter_10.item70;

import java.io.IOException;

public class Main {
    public static void checkedException() throws IOException {
        throw new IOException();
    }

    public static void uncheckedException() {
        throw new RuntimeException();
    }

    public static void error() {
        throw new AssertionError();
    }

    static class CustomException extends RuntimeException {
        private static final String EXCEPTION_MESSAGE = "이 예외는 ooo한 이유로 발생하였습니다. 확인할 것 : 메서드";
        public CustomException() {
            super(EXCEPTION_MESSAGE);
        }
    }

    public static void main(String[] args) {
//        try {
//            checkedException();
//        } catch (IOException e) {
//            System.out.println("검사 예외 발생. 정보=" + e.getClass().getSimpleName());
//        }
//
//        uncheckedException();
//        error();

        throw new CustomException();
    }
}
