package item5.di;

public class M1 implements AppleSiliconChip {  // M1 Pro

    @Override
    public int cpuCore() {
        return 10;
    }

    @Override
    public int gpuCore() {
        return 16;
    }

    @Override
    public int memory() {
        return 16;
    }
}
