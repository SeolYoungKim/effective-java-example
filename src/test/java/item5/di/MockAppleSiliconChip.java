package item5.di;

public class MockAppleSiliconChip implements AppleSiliconChip {
    @Override
    public int cpuCore() {
        return 0;
    }

    @Override
    public int gpuCore() {
        return 0;
    }

    @Override
    public int memory() {
        return 0;
    }
}
