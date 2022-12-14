package chapter_07.item47;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PowerSet {
    static <E> Collection<Set<E>> of(Set<E> set) {
        List<E> src = new ArrayList<>(set);
        if (src.size() > 30) {
            throw new IllegalArgumentException("집합에 원소가 너무 많습니다(최대 30개).: " + src);
        }

        return new AbstractList<Set<E>>() {
            @Override
            public Set<E> get(int index) {
                Set<E> result = new HashSet<>();
                for (int i = 0; index != 0; i++, index = index >> 1) {
                    if ((index & 1) == 1) {
                        result.add(src.get(i));
                    }
                }
                return result;
            }

            @Override
            public boolean contains(Object o) {
                return o instanceof Set && src.containsAll((Set) o);
            }

            @Override
            public int size() {  // 컬렉션을 반환 타입으로 쓸 때의 단점을 잘 보여준다. size가 int값을 반환하기 때문에 개수가 제한된다.
                // 멱집합의 크기는 2를 원래 집합의 원소 수만큼 거듭제곱 한 것과 같다
                return 1 << src.size();
            }
        };
    }

    public static void main(String[] args) {
        int index = 100;
        for (int i = 0; index != 0; i++, index = index >> 1) {
            System.out.println("Before : " + Integer.toBinaryString(index));
            System.out.println("Operation : " + (index & 1));
            if ((index & 1) == 1) {
                System.out.println("After : " + Integer.toBinaryString(index));
            }
            System.out.println("---");
        }
    }
}
