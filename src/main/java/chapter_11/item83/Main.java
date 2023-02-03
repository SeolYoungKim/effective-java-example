package chapter_11.item83;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {

    }

    private final List<Integer> list = new ArrayList<>();
    private Set<Integer> set;

    private synchronized Set<Integer> getSet() {
        if (set == null) {
            set = new HashSet<>();
        }

        return set;
    }

    // Static holder
    private static class ListHolder {
        static final List<Integer> list = addList();
    }

    private static List<Integer> addList() {
        return new ArrayList<>();
    }

    private static List<Integer> getList() {
        return ListHolder.list;
    }


    // initialization circularity
    static class A {
        private B b;
        public A() {
        }

        public B b() {
            if (b == null) {
                b = new B();
            }
            return b;
        }
    }

    static class B {
        private C c;
        public B() {
        }

        public C c() {
            if (c == null) {
                c = new C();
            }
            return c;
        }
    }

    static class C {
        private A a;

        public A a() {
            if (a == null) {
                a = new A();
            }
            return a;
        }
    }

    // Double-check
    // for instance field lazy initialization
    private volatile String field;

    private String getField() {
        String result = field;
        if (result != null) {
            return result;
        }

        synchronized (this) {
            if (field == null) {
                field = "초기화 값";
            }

            return field;
        }
    }

    // Single-check
    private String getFieldSingle() {
        String result = field;
        if (result == null) {
            field = result = "초기화 값";
        }
        return result;
    }
}
