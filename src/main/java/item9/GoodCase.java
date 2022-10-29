package item9;

import java.io.*;

public class GoodCase {
    /**
     * 아래와 같이 작성하면 코드가 짧아질 뿐만 아니라, 발생한 예외에 대해 디버그 하기 수월해진다.
     */
    public static String BaekJoonSolve(String a, String b) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out))) {

            br.readLine();
            bw.write("너무어렵다!!!!!");
        }

        return "아무튼 정답임.";
    }

    public static String supressEx(String a, String b) throws IOException {
        try (BufferedReader br = new HaHaBufferedReader(new InputStreamReader(System.in))) {

            br.readLine();
            br.close();
        }

        return "아무튼 정답임.";
    }

    /*
      Suppressed Exception
      - 위 코드에서 br.readLine()과 br.close()(코드엔 없지만)에서 동시에 예외가 발생한다면,
      - close에서 발생한 예외는 숨겨지고, readLine에서 발생한 예외가 기록 됨.
      - 이렇게 숨겨진 예외들도 그냥 버려지지는 않고, 스택 추적 내역에 "숨겨졌다(suppressed)"는 꼬리표를 달고 출력 됨.
      - Throwable의 getSuppressed를 이용하면 프로그램 코드에서도 가져올 수 있음.

      반드시 회수해야 할 자원을 다룰 때는 try-with-resources를 사용하자.
      - 코드가 간결하고 분명해진다.
      - 만들어지는 예외 정보도 유용하다.
      - 정확하고 쉽게 자원을 회수할 수 있다.
     */
}
