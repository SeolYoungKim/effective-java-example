package test;

import java.lang.System.Logger.Level;
import java.util.HashSet;
import java.util.logging.Logger;

public class CodeWithMeTest {

    public static void main(String[] args) {
        // jun0 dfjskldfjklesjlfkdjf
        System.out.println("하하 호 " + "호");
        System.gc();
        System.getLogger("Nooo").log(Level.INFO, "나다.보바는 ");
        System.out.println(System.getLogger("Nooo").getName());

        System.out.println(12321);
        // 딱구
        HashSet<Integer> set = new HashSet<>();
        set.add(1);
        set.add(2);
        set.add(3);
        set.add(4);
        System.out.println(set);
        System.err.println("err!!!");
        Logger.getAnonymousLogger().config("hi!!!!!!!!!!!");
        System.getLogger("No~~~~~~~~").log(Level.ERROR, "error!!!!!!!!!");
    }
}
