package item3;

public class Lee {
    private static final Lee SINGLETON_LEE = new Lee();

    private Lee() {
    }

    public static Lee getInstance() {
        return SINGLETON_LEE;
    }

}
