package chapter_07.item43;

import java.io.EOFException;
import java.io.IOException;

public class FunctionTypes {
    interface X {
        void m() throws IOException;
    }

    interface Y {
        void m() throws EOFException;
    }

    interface Z {
        void m() throws ClassNotFoundException;
    }

    interface XY extends X, Y {  //TODO 더 구체적인 예외인 EOFException을 던짐
    }

    interface XYZ extends X, Y, Z {  //TODO () -> void (throws nothing)
    }

    public static void main(String[] args) throws EOFException {
        XY xy = () -> System.out.println("악ㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱ");   //TODO 얘는 EOFException을 던짐
        xy.m();

        XYZ xyz = () -> System.out.println("dkfjlskdjflkajfke");   //TODO 얘는 아무것도 안 던짐 ㅎ..
        xyz.m();
    }

}
