package chapter_07.item43;

import java.io.EOFException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLTransientException;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class FunctionTypes2 {
    interface A {
        List<String> foo(List<String> arg) throws IOException, SQLTransientException;
    }

    interface B {
        List foo(List<String> arg) throws EOFException, SQLException, TimeoutException;
    }

    interface C {
        List foo(List arg) throws Exception;
    }

    interface D extends A, B {  //TODO 더 자세한 예외가 던져지며, TimeoutException은 무시된다..?
    }

    public static void main(String[] args) throws SQLTransientException, EOFException {
        List<String> insert = List.of("카", "악", "퉤");

        A a = arg -> List.copyOf(arg);
        B b = arg -> List.copyOf(arg);
        D d = arg -> List.copyOf(arg);

        List<String> foo = d.foo(insert);
    }

}
