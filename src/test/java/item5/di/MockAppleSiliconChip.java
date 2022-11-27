package item5.di;

import chapter_02.item5.di.AppleSiliconChip;

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
