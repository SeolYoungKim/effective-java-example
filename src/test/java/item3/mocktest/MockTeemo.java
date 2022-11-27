package item3.mocktest;

import chapter_02.item3.mocktest_success.Teemo;

public class MockTeemo implements Teemo {
    @Override
    public void poisonShot() {
        System.out.println("티모가 독침을 쏩니다.");
    }

    @Override
    public void sheared() {
        System.out.println("티모가 찢겼습니다.");
    }
}
