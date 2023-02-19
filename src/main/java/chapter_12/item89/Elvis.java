package chapter_12.item89;

import java.io.Serializable;

public class Elvis implements Serializable {
    static final Elvis INSTANCE = new Elvis();

    private Elvis() {

    }

    public void leaveTheBuilding() {
        System.out.println("엘비스가 빌딩을 떠납니다.");
    }
}
