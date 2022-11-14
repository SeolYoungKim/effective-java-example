package item24;

public class MemberClass {

    private final String test;

    public MemberClass(String test) {
        this.test = test;
    }

    static class StaticMemberClass {

        void print() {
//            System.out.println(MemberClass.this.test);  // static에서는 인스턴스를 참조할 수 없다.
//            System.out.println(test);  // static에서는 인스턴스를 참조할 수 없다.
        }
    }

    class InstanceMemberClass {

        void print() {
            System.out.println(test);  // static이 아닌 멤버 클래스는 자동으로 외부 클래스의 참조를 갖는다.
        }
    }
}
