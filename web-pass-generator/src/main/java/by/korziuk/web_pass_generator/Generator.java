package by.korziuk.web_pass_generator;

import java.util.Random;

public class Generator {
    public int generateNumber() {
        return new Random().nextInt(10);
    }

    public char generateSmallChar() {
        String smallStr = "abcdefghijklmnopqrstuvwxyz";
        return smallStr.charAt(new Random().nextInt(smallStr.length() + 1));
    }

    public char generateBigChar() {
        String bigStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        return bigStr.charAt(new Random().nextInt(bigStr.length() + 1));
    }

    public char generateCpecialSymbol() {
        String symbolString = "!#$%&'()*+,-./:;<=>?@[]^_`{|}~";
        return symbolString.charAt(new Random().nextInt(symbolString.length() + 1));
    }
}
