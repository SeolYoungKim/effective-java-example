package test;

public class dd {

    public static final int WON = 1000;
    public static final String 여기는_아님 = "여기에 적으면 하드코딩";
    public static final String HI = "ㅎㅇ";
    public static final String ERROR_MESSaGE = "[ERROR] ㅎㅇ24324324";

    public static void main(String[] args) {
        int won = WON;
        throw new IllegalArgumentException(여기는_아님);
    } // 반복성 , 의미부여를 해라

    // TODO 로또 > validation 중복이 안된다
    // TODO 새로만들면 validation이 중복이 된다

    // TODO 수정 DTO, 생성 DTO

    // TODO 랜덤 숫자를 받은 로또
    // TODO 로또(종이 로또) + 보너스 == 당첨 번호 || List<Integer>
    // TODO 당첨 번호(6)(공6개) + 보너스(1) == 당첨 세트 || List<Integer>

    // TODO validation 로직 중복 제거는..?
    // TODO LottoNumberValidator.validate() <- 중복 제거...?

    // TODO -> 인터페이스?? (X)  ???????????????????
}