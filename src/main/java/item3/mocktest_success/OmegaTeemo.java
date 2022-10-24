package item3.mocktest_success;

public class OmegaTeemo implements Teemo{

    public static OmegaTeemo OMEGA_TEEMO = new OmegaTeemo();

    private String omegaMoja;
    private String gun;
    private String bombMushroom;

    private OmegaTeemo() {
    }

    @Override
    public void poisonShot() {
        System.out.println("오메가 티모가 독침을 쏩니다.");
    }

    @Override
    public void sheared() {
        System.out.println("오메가 티모가 찢겼습니다.");
    }
}
