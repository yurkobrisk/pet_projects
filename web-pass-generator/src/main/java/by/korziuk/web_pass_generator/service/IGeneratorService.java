package by.korziuk.web_pass_generator.service;

public interface IGeneratorService {

    int generateNumber();
    char generateSmallChar();
    char generateBigChar();
    char generateCpecialSymbol();
    String generatePassword(int length);

    boolean isNumber(String number);

    void copyToClipboard(String text);

    String pasteFromClipboard();
}
