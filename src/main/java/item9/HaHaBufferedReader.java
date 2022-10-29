package item9;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class HaHaBufferedReader extends BufferedReader {

    public HaHaBufferedReader(Reader in) {
        super(in);
    }

    public HaHaBufferedReader(Reader in, int sz) {
        super(in, sz);
    }

    @Override
    public String readLine() throws IOException {
        throw new IllegalArgumentException("키키키키킼키키");
    }

    @Override
    public void close() throws IOException {
        throw new IllegalArgumentException("하하하하핳ㅎ하ㅏ");
    }
}
