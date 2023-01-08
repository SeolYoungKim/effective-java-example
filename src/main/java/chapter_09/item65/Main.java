package chapter_09.item65;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    public static void main(String[] args) {
        String[] sets = {"java.util.HashSet", "java.util.TreeSet"};
        int rand = ThreadLocalRandom.current().nextInt(2);

        Class<? extends Set<String>> clazz = null;
        try {
            clazz = (Class<? extends Set<String>>) Class.forName(sets[rand]);  // 비검사 형변환
        } catch (ClassNotFoundException e) {
            System.err.println("클래스를 찾을 수 없습니다.");
        }

        // 생성자를 얻는다.
        Constructor<? extends Set<String>> cons = null;
        try {
            cons = clazz.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            System.err.println("기본 생성자를 찾을 수 없습니다.");
        }

        // Set의 인스턴스를 만든다.
        Set<String> s = null;
        try {
            s = cons.newInstance();
        } catch (InvocationTargetException e) {
            System.err.println("생성자에 접근할 수 없습니다.");
        } catch (InstantiationException e) {
            System.err.println("클래스를 인스턴스화 할 수 없습니다.");
        } catch (IllegalAccessException e) {
            System.err.println("올바른 접근이 아닙니다.");
        }

        // 생성한 Set을 사용한다.
        s.addAll(List.of("신기한", "리플렉션의", "세계"));
        System.out.println(s.getClass());
        System.out.println(s);

        Class<Animal> animalClass = null;
        try {
            animalClass = (Class<Animal>) Class.forName("chapter_09.item65.Dog");
        } catch (ClassNotFoundException e) {
            System.err.println(Arrays.toString(e.getStackTrace()));
        }
    }
}
