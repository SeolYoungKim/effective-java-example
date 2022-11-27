package chapter_04.item21;

public interface Father {

    default void smashed() {
        System.out.println("아빠에게 몽둥이 찜질을 당했습니다.");
    }
}
