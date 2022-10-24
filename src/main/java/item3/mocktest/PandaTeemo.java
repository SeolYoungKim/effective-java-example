package item3.mocktest;

public class PandaTeemo {

    public static PandaTeemo PANDA_TEEMO = new PandaTeemo();

    private String pandaMoja;
    private String daeNaMoo;
    private String samgakMushroom;

    private PandaTeemo() {
    }

    public void poisonShot() {
        System.out.println("판다 티모가 독침을 쏩니다.");
    }

    public void sheared() {
        System.out.println("판다 티모가 찢겼습니다.");
    }
}
