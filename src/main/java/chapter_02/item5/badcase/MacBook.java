package chapter_02.item5.badcase;

public class MacBook {

    /**
     * 이렇게 되면 Chip을 갈아 끼울 때 마다 MacBook 클래스도 고쳐줘야 합니다.
     * 이런 경우, 몇 가지 문제가 발생하는데, 문제점은 아래와 같습니다.
     * - 이는 유연하지 못한 설계입니다.
     * - 이는 MacBook 클래스에 대한 테스트를 어렵게 만듭니다. (Mock 객체를 사용할 수 없습니다. 연산 비용도 고려해야 합니다.)
     */
    public static final M1 m1 = new M1();

    private MacBook() {
    }

    public boolean on(boolean power) {
        return power;
    }

    public void cpuCheck() {
        System.out.println("CPU core : " + m1.cpuCore());
        System.out.println("GPU core : " + m1.gpuCore());
        System.out.println("Memory   : " + m1.memory());
    }
}
