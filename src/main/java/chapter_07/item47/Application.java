package chapter_07.item47;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Application {
    public static void main(String[] args) {
        for (ProcessHandle ph : (Iterable<ProcessHandle>) ProcessHandle.allProcesses()::iterator) {
            //끔찍한 우회 방법
        }

        for (ProcessHandle processHandle : iterableOf(ProcessHandle.allProcesses())) {
            //우회 굿
        }
    }

    static <E> Iterable<E> iterableOf(Stream<E> stream) {  // Stream -> Iterable로 중개
        return stream::iterator;
    }

    static <E> Stream<E> streamOf(Iterable<E> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false);
    }
}
