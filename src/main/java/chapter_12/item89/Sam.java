package chapter_12.item89;

import java.io.Serializable;

public class Sam implements Serializable {
    public static final Sam INSTANCE = new Sam();

    private Sam() {

    }

    public void leaveTheBuilding() {
        System.out.println("샘이 빌딩을 떠납니다.");
    }

    private Object readResolve() {
        return INSTANCE;
    }
}
