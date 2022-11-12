package item20;

public class Parent implements JustInterface{

    public Parent() {
        System.out.println(hi());
    }

    @Override
    public String hi() {
        return "hi, I'm Parent";
    }
}
