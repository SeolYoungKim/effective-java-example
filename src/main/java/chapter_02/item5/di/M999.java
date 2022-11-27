package chapter_02.item5.di;

public class M999 implements AppleSiliconChip {
    @Override
    public int cpuCore() {
        return 100;
    }

    @Override
    public int gpuCore() {
        return 200;
    }

    @Override
    public int memory() {
        return 320;
    }
}
