package chapter_12.item89.non_transient;

import java.io.Serializable;

public class ElvisStealer implements Serializable {
    static Elvis impersonator;  // 명의 도용
    private Elvis payload;  // 진짜 Elvis

    private Object readResolve() {
        // resolve 되기 전의 Elvis 인스턴스의 참조를 저장함
        impersonator = payload;

        // favoriteSongs 필드에 맞는 타입의 객체를 반환함
        return new String[]{"A Fool Such as I"};
    }
}
