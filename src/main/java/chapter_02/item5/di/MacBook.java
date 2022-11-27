package chapter_02.item5.di;

public class MacBook {

    /**
     * 아래와 같이 구성할 경우, MacBook은 AppleSiliconChip이 무엇이 온다 해도 변경하지 않아도 되는 코드가 됩니다.
     * - 스프링에서 빈의 의존관계 주입을 설정할 때, 아래와 같은 방식을 많이 사용합니다.
     * - 유연한 코드가 되었습니다! (개방 폐쇄의 원칙)
     */

    private AppleSiliconChip appleSiliconChip;

    private MacBook() {
    }

    public MacBook(AppleSiliconChip appleSiliconChip) {
        this.appleSiliconChip = appleSiliconChip;
    }

    public boolean on(boolean power) {
        return power;
    }

    public void cpuCheck() {
        System.out.println("CPU core : " + appleSiliconChip.cpuCore());
        System.out.println("GPU core : " + appleSiliconChip.gpuCore());
        System.out.println("Memory   : " + appleSiliconChip.memory());
    }
}
