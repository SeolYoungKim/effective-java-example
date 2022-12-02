package chapter_06.item38;

public enum Test {

    A,
    B{},
    C{
        @Override
        public String toString() {
            return "this is C";
        }
    };

    public static void main(String[] args) {
        Class<? extends Test> classA = A.getClass();
        Class<Test> declaringClassA = A.getDeclaringClass();

        System.out.println("classA = " + classA);
        System.out.println("declaringClassA = " + declaringClassA);
        System.out.println();

        Class<? extends Test> classB = B.getClass();
        Class<Test> declaringClassB = B.getDeclaringClass();

        System.out.println("classB = " + classB);
        System.out.println("declaringClassB = " + declaringClassB);
        System.out.println();

        Class<? extends Test> classC = C.getClass();
        Class<Test> declaringClassC = C.getDeclaringClass();

        System.out.println("classC = " + classC);
        System.out.println("declaringClassC = " + declaringClassC);
        System.out.println();

        // 공식 문서에, 두 열거형 상수 e1, e2가 있을 때, e1.getDeclaringClass() == e2.getDeclaringClass()인 경우에만 동일한 열거형이라고 한다.

        System.out.println("class 비교");
        System.out.println("A == B : " + (classA == classB));
        System.out.println("B == C : " + (classB == classC));
        System.out.println("A == C : " + (classA == classC));
        System.out.println();

        System.out.println("declaringClass 비교");
        System.out.println("A == B : " + (declaringClassA == declaringClassB));
        System.out.println("B == C : " + (declaringClassB == declaringClassC));
        System.out.println("A == C : " + (declaringClassA == declaringClassC));
    }
}
