package chapter_12.item89.non_transient;

import java.io.Serializable;
import java.util.Arrays;

public class Elvis implements Serializable {
    public static final Elvis INSTANCE = new Elvis();

    private Elvis() {

    }

    private String[] favoriteSongs = {"Hound Dog", "Heartbreak Hotel"};  // transient가 아닌 참조 필드

    public void printFavorites() {
        System.out.println(Arrays.toString(favoriteSongs));
    }

    private Object readResolve() {
        return INSTANCE;
    }
}
