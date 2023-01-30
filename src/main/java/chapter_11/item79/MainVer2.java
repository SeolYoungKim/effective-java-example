package chapter_11.item79;

import java.util.HashSet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainVer2 {
    public static void main(String[] args) {
        final ObservableSetVer2<Integer> set = new ObservableSetVer2<>(new HashSet<>());

//        set.addObserver((s, e) -> System.out.println(e));

//        set.addObserver(new SetObserver<>() {
//            @Override
//            public void added(final ObservableSet<Integer> s, final Integer e) {
//                System.out.println(e);
//                if (e == 23) {
//                    s.removeObserver(this);
//                }
//            }
//        });

        set.addObserver(new SetObserverVer2<>() {
            @Override
            public void added(final ObservableSetVer2<Integer> s, final Integer e) {
                System.out.println(e);
                if (e == 23) {
                    final ExecutorService threadPool = Executors.newSingleThreadExecutor();

                    try {
                        threadPool.submit(() -> s.removeObserver(this)).get();
                    } catch (ExecutionException | InterruptedException ex) {
                        throw new AssertionError(ex);
                    } finally {
                        threadPool.shutdown();
                    }
                }
            }
        });

        for (int i = 0; i < 100; i++) {
            set.add(i);
        }
    }
}
