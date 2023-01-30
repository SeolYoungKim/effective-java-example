package chapter_11.item79;

@FunctionalInterface
public interface SetObserverVer2<E> {
    void added(ObservableSetVer2<E> set, E elem);
}
