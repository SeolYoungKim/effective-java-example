package chapter_07.item44;

import java.util.Map;

@FunctionalInterface
public interface EldestEntryRemovalFunction<K, V> {
    //TODO BiPredicate<Map<K,V>, Map.Entry<K,V>>로 대체할 수 있다.
    boolean remove(Map<K, V> map, Map.Entry<K, V> eldest);
}
