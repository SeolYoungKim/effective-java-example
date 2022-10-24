package item3;

import item3.GenericSingletonFactory;
import item3.Kim;
import item3.Lee;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

class GenericSingletonFactoryTest {

    @Test
    void genericTest() {
        Set<String> set1 = GenericSingletonFactory.getSet();
        Set<Integer> set2 = GenericSingletonFactory.getSet();
        Set<Kim> set3 = GenericSingletonFactory.getSet();
        Set<Lee> set4 = GenericSingletonFactory.getSet();

        set1.add("이게 된다고?");
        set2.add(123456);
        set3.add(Kim.SINGLETON_KIM);
        set4.add(Lee.getInstance());

        Set<Object> genericSet = GenericSingletonFactory.GENERIC_SET;

        System.out.println(genericSet);
        assertThat(genericSet.size()).isEqualTo(4);

        HashSet hashSet = new HashSet();
        hashSet.add("이게 된다고?");
        hashSet.add(123456);
        hashSet.add(Kim.SINGLETON_KIM);
        hashSet.add(Lee.getInstance());

        System.out.println(hashSet);

    }

}