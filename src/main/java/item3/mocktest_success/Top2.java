package item3.mocktest_success;

public class Top2 {

    private final Teemo teemo;

    public Top2(Teemo teemo) {
        this.teemo = teemo;
    }

    public boolean init;
    public boolean teemoIsDead;

    public void fight() {
        init = true;
        teemo.poisonShot();
        teemo.sheared();
        teemoIsDead = true;
    }
}
