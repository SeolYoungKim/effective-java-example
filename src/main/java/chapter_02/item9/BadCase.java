package chapter_02.item9;

import java.io.*;

public class BadCase {
    /**
     * 아래 방법의 문제점
     * 1. 지저분함.
     * 2. 두번째 발생한 예외가 첫번째 발생한 예외를 삼킨다. -> stackTrace에 두번째(마지막) 예외만 남음. (디버그 힘듬)
     * 한번에 해도 되지 않냐? -> 안된다 -> leak이 생긴다.
     * finally {
     * bw.close();
     * br.close();  -> 이와 같이 작성할 경우, bw.close()에서 에러가 나면 br.close()는 실행되지 않기 때문!
     * }
     */
    public static String BaekJoonSolve(String a, String b) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            br.readLine();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
            try {
                bw.write("백준 문제는 너무 어렵습니다..");
            } finally {
                bw.close();
            }
        } finally {
            br.close();
        }

        return "아무튼 정답임.";
    }

    public static String supressEx(String a, String b) throws IOException {
        BufferedReader br = new TestBufferedReader(new InputStreamReader(System.in));
        try {
            br.readLine();
        } finally {
            br.close();
        }

        return "아무튼 정답임.";
    }
}
