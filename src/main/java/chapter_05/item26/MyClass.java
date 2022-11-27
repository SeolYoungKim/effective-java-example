package chapter_05.item26;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MyClass<T> {

    private final T genericField;

    public MyClass(T genericField) {
        this.genericField = genericField;
    }

    public static void main(String[] args) {
        MyClass<String> myClass = new MyClass<>("제네릭 필드!");
        System.out.println(myClass.genericField);

        MyClass rawTypeMyClass = new MyClass("히히 난 raw type이야.");
        System.out.println(rawTypeMyClass.genericField);

        List list = new ArrayList();
        list.add(1000);
        list.add("1000원");
        list.add('W');
        list.add(List.of("ㅎㅎ", "ㅋㅋ", "ㅎㅎㅋㅋ"));
        System.out.println(list);

        List<String> strList = new ArrayList<>();
        strList.add("스트링!!!!!!");

        System.out.println(strList.get(0) + "1000000");

        List<Object> objList = new ArrayList<>();
//        print(objList);
//        print(strList);

        printRaw(objList);
        printRaw(strList);

        printWildCard(objList);
        printWildCard(strList);

        List<?> wildList = new ArrayList<>();  // 뭔가를 넣을 때 쓰는게 아님.
        wildList.add(null);
//        wildList.add("hi");
//        wildList.add(1);
//        wildList.add('h');

        List<Integer> intList = new ArrayList<>();
        intList.add(10);
        System.out.println(intList.get(0) + 100000);
    }

    static void print(List<Object> objList) {
        System.out.println(objList);
    }
    static void printRaw(List rawList) {
        System.out.println(rawList);
    }

    static void printWildCard(List<?> rawList) {  // List가 어떤 타입이든지 다 올수 있다. ? == ? extends Object
        System.out.println(rawList);
    }

    static void check(Object o) {
        if (o instanceof Set) {
            Set<?> s = (Set<?>) o;
        }
    }
}
