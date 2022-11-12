package by.korziuk.web_pass_generator;

import java.util.Random;

public class Generator {
    public int generateNumber() {
        return new Random().nextInt(10);
    }

    public char generateSmallChar() {
        String smallStr = "abcdefghijklmnopqrstuvwxyz";
        return smallStr.charAt(new Random().nextInt(smallStr.length()));
    }

    public char generateBigChar() {
        String bigStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        return bigStr.charAt(new Random().nextInt(bigStr.length()));
    }

    public char generateCpecialSymbol() {
        String symbolString = "!@#&()–{}:;',?/*~$^+=<>";
        return symbolString.charAt(new Random().nextInt(symbolString.length()));
    }

    public String generatePassword(int length) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            if (i == 0) {
                result.append(getRandomChar(4));
            } else {
                result.append(getRandomChar(5));
            }
        }
        return result.toString();
    }

    private char getRandomChar(int bound) {
        int random = new Random().nextInt(bound);
        switch (random) {
            case 0 :
                return Character.forDigit(generateNumber(), 10);
            case 1 :
                return generateBigChar();
            case 2 :
                return generateSmallChar();
            default:
                return generateCpecialSymbol();
        }
    }
}
