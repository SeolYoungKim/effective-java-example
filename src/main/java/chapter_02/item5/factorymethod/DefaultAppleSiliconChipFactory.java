package chapter_02.item5.factorymethod;

import chapter_02.item5.di.AppleSiliconChip;
import chapter_02.item5.di.M1;

public class DefaultAppleSiliconChipFactory implements AppleSiliconChipFactory{

    @Override
    public AppleSiliconChip getAppleSiliconChip() {
        return new M1();
    }
}
