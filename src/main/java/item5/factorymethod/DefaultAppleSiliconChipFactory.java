package item5.factorymethod;

import item5.di.AppleSiliconChip;
import item5.di.M1;

public class DefaultAppleSiliconChipFactory implements AppleSiliconChipFactory{

    @Override
    public AppleSiliconChip getAppleSiliconChip() {
        return new M1();
    }
}
