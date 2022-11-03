package test;

public class CloneChild extends CloneTest implements Cloneable {

    public CloneChild(int number, char character) {
        super(number, character);
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        CloneChild cloneChild = new CloneChild(1, 'a');
        CloneChild clone = cloneChild.clone();

        System.out.println(clone.getClass());
    }

    @Override
    public CloneChild clone() {
        try {
            return (CloneChild) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
