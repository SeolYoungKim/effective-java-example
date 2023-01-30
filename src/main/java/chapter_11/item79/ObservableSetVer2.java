package chapter_11.item79;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

public class ObservableSetVer2<E> {
    private final Set<E> set;
    private final List<SetObserverVer2<E>> observers = new CopyOnWriteArrayList<>();

    public ObservableSetVer2(final Set<E> set) {
        this.set = set;
    }

    public void addObserver(SetObserverVer2<E> observer) {
        observers.add(observer);
    }

    public void removeObserver(SetObserverVer2<E> observer) {
        observers.remove(observer);
    }

    private void notifyElement(E elem) {
        for (SetObserverVer2<E> observer : observers) {
            observer.added(this, elem);
        }
    }

    public boolean add(E elem) {
        final boolean added = set.add(elem);
        if (added) {
            notifyElement(elem);
        }

        return added;
    }

    public boolean addAll(Collection<? extends E> c) {
        boolean result = false;
        for (E elem : c) {
//            result |= add(elem);
            result = result | add(elem);
        }
        return result;
    }
}
