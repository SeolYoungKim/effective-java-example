package chapter_11.item79;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class ObservableSet<E> {
    protected final Set<E> set;
    protected final List<SetObserver<E>> observers = new ArrayList<>();

    public ObservableSet(final Set<E> set) {
        this.set = set;
    }

    public void addObserver(SetObserver<E> observer) {
        synchronized (observers) {
            observers.add(observer);
        }
    }

    public void removeObserver(SetObserver<E> observer) {
        // notifyElement가 호출되어 수행되는 도중에 호출됨 -> 메인 스레드가 락을 쥐고 있는 상태라, 다른 스레드가 접근하면 교착 상태에 빠짐
        synchronized (observers) {   // 메인 스레드는 여기가 수행되기만을 기다리는 중 -> 데드락!
            observers.remove(observer);
        }
    }

    private void notifyElement(E elem) {
//        synchronized (observers) {  // 여기서 메인 스레드가 락을 쥐고있다?
//            for (SetObserver<E> observer : observers) {
//                observer.added(this, elem);  // 동시 수정이 발생하지 않도록 보장하나, 자기 자신을 되돌아와 수정하는 것은 못막음
//            }
//        }

        List<SetObserver<E>> snapshot = null;
        synchronized (observers) {
            snapshot = new ArrayList<>(observers);
        }

        for (SetObserver<E> observer : snapshot) {
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
