package item24;

public class MemberClass {

    private static final int number = 10;
    private final String test;

    public MemberClass(String test) {
        this.test = test;
    }

    void printNumber() {
        // 바깥 클래스의 인스턴스가 있는 상태에서는 내부 클래스를 생성할 수 있다.
        InstanceMemberClass instanceMemberClass = new InstanceMemberClass();
        instanceMemberClass.print();
    }

    static class StaticMemberClass {

        // 바깥 클래스와 독립적일 때 사용.
        // static 처럼 사용되는 클래스이다.
        void print() {
//            System.out.println(MemberClass.this.test);  // static에서는 인스턴스를 참조할 수 없다.
//            System.out.println(test);  // static에서는 인스턴스를 참조할 수 없다.
            // 바깥 클래스의 인스턴스를 필요로 하지 않음. 독립적임.
            System.out.println(number);
        }
    }

    class InstanceMemberClass {
        // 암묵적으로 바깥 인스턴스에 대한 참조가 생겨버린다.
        // Outer 인스턴스 없이는 자신을 생성할 수 없다.
        // 인스턴스처럼 사용되는 클래스이다.
        void print() {
            System.out.println(test);  // static이 아닌 멤버 클래스는 자동으로 외부 클래스의 참조를 갖는다.
            System.out.println(number);
            MemberClass.this.printNumber();  // 바깥 클래스를 참조하는 경우가 많을 경우, 비정적 멤버 클래스로 사용.
        }
    }

    public static void main(String[] args) {
        StaticMemberClass staticMemberClass = new StaticMemberClass();

        MemberClass memberClass = new MemberClass("1");
//        InstanceMemberClass instanceMemberClass = new InstanceMemberClass();  // 안됨
        InstanceMemberClass instanceMemberClass = memberClass.new InstanceMemberClass();  // 이렇게만 됨;;
    }
}
