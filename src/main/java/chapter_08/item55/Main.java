package chapter_08.item55;

import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        Optional<String> hi = Optional.of("하이");
        hi.map(h -> h.length()).get();
    }
}
