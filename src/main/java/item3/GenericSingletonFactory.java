package item3;

import java.util.HashSet;
import java.util.Set;

public class GenericSingletonFactory {

    public static final Set<Object> GENERIC_SET = new HashSet<>();

    private GenericSingletonFactory() {
    }

    @SuppressWarnings("unchecked")
    public static <T> Set<T> getSet() {
        return (Set<T>) GENERIC_SET;
    }
}
