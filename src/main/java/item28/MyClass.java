package item28;

import java.util.List;

public class MyClass {

    /**
     * - 배열은 공변이다.
     * - 배열은 실체화 된다. (reify)
     *
     * - 제네릭은 불공변이다.
     * - E, List<E>, List<String> : 실체화 불가 타입! (non-reifiable type)
     */
    public static void main(String[] args) {
        Object[] objArr = new Long[1];  // Long은 Object의 하위 클래스. 배열은 이를 허용한다;;
        objArr[0] = "문자열은 못넣지롱";  // 이는 컴파일 타임에 체크되지 않아, 런타임이 되어서야 알 수 있다.

        // 배열은 런타임에도 자신이 담기로 한 원소의 타입을 인지하고 확인한다. (런타임에도 타입 검사)
        // 때문에 런타임에 배열에 다른 타입의 값을 넣으면 ArrayStoreException이 발생하는 것이다.

//        List<Object> objectList = new ArrayList<Long>();  // 이건 애초에 안됨

        // 반면에 제네릭은 타입 정보가 런타임에는 소거된다.
        // 때문에, 원소 타입을 컴파일 타임에만 검사하며 런타임에는 알 수조차 없다.

        // 배열은 런타임에 체크를 하지만, 제네릭은 컴파일 시 소거되어 런타임에 체크를 하지 않기 때문에
        // 배열과 제네릭은 잘 어우러지지 못한다.

//        List<String>[] lists = new List<String>[1]; // 컴파일 되지 않는다.

        // E, List<E>, List<String> : 실체화 불가 타입!
        // 런타임 때는 컴파일 타임일 때 보다 타입 정보를 적게 가진다.
        // 소거 메커니즘 때문에 매개변수화 타입 가운데 실체화 될 수 있는 타입은
        // List<?>와 Map<?,?>와 같은 비한정적 와일드카드 타입 뿐이다.

    }

}
