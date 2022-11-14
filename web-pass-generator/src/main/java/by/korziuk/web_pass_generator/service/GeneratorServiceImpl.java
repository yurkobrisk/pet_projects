package by.korziuk.web_pass_generator.service;

import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.Random;

@Service
public class GeneratorServiceImpl implements IGeneratorService {

    private Clipboard clipboard;

    @Override
    public int generateNumber() {
        return new Random().nextInt(10);
    }

    @Override
    public char generateSmallChar() {
        String smallStr = "abcdefghijklmnopqrstuvwxyz";
        return smallStr.charAt(new Random().nextInt(smallStr.length()));
    }

    @Override
    public char generateBigChar() {
        String bigStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        return bigStr.charAt(new Random().nextInt(bigStr.length()));
    }

    @Override
    public char generateCpecialSymbol() {
        String symbolString = "!@#&()–{}:;',?/*~$^+=<>";
        return symbolString.charAt(new Random().nextInt(symbolString.length()));
    }

    @Override
    public String generatePassword(int length) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            if (i == 0) {
                result.append(getRandomChar(3));
            } else {
                result.append(getRandomChar(4));
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

    @Override
    public boolean isNumber(String number) {
        return number.matches("[0-9]+");
    }

    @Override
    public void copyToClipboard(String text) {
        clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection stringSelection = new StringSelection(text);
        clipboard.setContents(stringSelection, null);
    }

    @Override
    public String pasteFromClipboard() {
        try {
            return (String) clipboard.getData(DataFlavor.stringFlavor);
        } catch (UnsupportedFlavorException | IOException e) {
            System.out.println("Error paste from Clipboard.");
        }
        return "";
    }
}
