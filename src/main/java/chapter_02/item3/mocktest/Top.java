package chapter_02.item3.mocktest;

public class Top {

    private final PandaTeemo pandaTeemo;

    public Top(PandaTeemo pandaTeemo) {
        this.pandaTeemo = pandaTeemo;
    }

    public boolean init;
    public boolean teemoIsDead;

    public void fight() {
        init = true;
        pandaTeemo.poisonShot();
        pandaTeemo.sheared();
        teemoIsDead = true;
    }
}
