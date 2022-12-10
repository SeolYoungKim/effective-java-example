package chapter_07.item44;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class MyLinkedHashMap<K, V> extends LinkedHashMap<K, V> {

    private static final int MAX_ENTRIES = 100;

    @Override
    protected boolean removeEldestEntry(Entry<K, V> eldest) {  // TODO 저 eldest가 쓰이는데가 없네?
        return size() > MAX_ENTRIES;
    }
}
