package item5.di;

public class M2 implements AppleSiliconChip {
    @Override
    public int cpuCore() {
        return 8;
    }

    @Override
    public int gpuCore() {
        return 10;
    }

    @Override
    public int memory() {
        return 16;
    }
}
