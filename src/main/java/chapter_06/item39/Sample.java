package chapter_06.item39;

public class Sample {

    @Test
    public static void method1() {

    }

    public static void method2() {

    }

    @Test
    public static void method3() {
        throw new RuntimeException("fail");
    }

    public static void mehtod4() {

    }

    @Test  // 강제할 수 없어 아래와 같이 사용해도 잘못되었다고 컴파일러 측에서 알려줄 수 없다.
    public void method5() {

    }

    public static void method6() {

    }

    @Test
    public static void mehtod7() {
        throw new RuntimeException("fail");
    }

    public static void method8() {

    }
}
