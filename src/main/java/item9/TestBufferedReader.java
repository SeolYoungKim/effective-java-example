package item9;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class TestBufferedReader extends BufferedReader {

    public TestBufferedReader(Reader in) {
        super(in);
    }

    public TestBufferedReader(Reader in, int sz) {
        super(in, sz);
    }

    @Override
    public String readLine() throws IOException {
        throw new IllegalArgumentException("첫 번째 예외");
    }

    @Override
    public void close() throws IOException {
        throw new IllegalArgumentException("두 번째 예외");
    }
}
