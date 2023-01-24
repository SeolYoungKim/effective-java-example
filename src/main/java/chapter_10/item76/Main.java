package chapter_10.item76;

public class Main {
    private static class Counter {
        private int count = 0;

        public void addCount(int number) {
            if (count + number < 0) {  // 숫자가 음수가 들어왔거나, 정수형 overflow가 발생했을 경우 예외를 발생시킨다.
                throw new IllegalArgumentException();
            }

            count += number;
        }

        public int count() {
            return count;
        }
    }

    private static final Counter COUNTER = new Counter();

    public static void main(String[] args) {
        final Thread thread1 = new Thread(() -> {
            try {
                failAdd();
            } catch (InterruptedException | RuntimeException e) {
                System.out.println("예외가 발생했습니다.");
            }
        });

        final Thread thread2 = new Thread(Main::readNumber);
        thread1.start();
        thread2.start();
    }

    private static void failAdd() throws InterruptedException {
        COUNTER.addCount(1);
        COUNTER.addCount(1);
        COUNTER.addCount(Integer.MAX_VALUE);
    }

    private static void readNumber() {
        for (int i = 0; i < 1000; i++) {
            System.out.println(COUNTER.count());
        }
    }
}
