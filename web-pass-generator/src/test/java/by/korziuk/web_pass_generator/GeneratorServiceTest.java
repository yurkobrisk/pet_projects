package by.korziuk.web_pass_generator;

import by.korziuk.web_pass_generator.service.GeneratorServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class GeneratorServiceTest {

    @Test
    public void generate_random_number(){
        GeneratorServiceImpl generator = new GeneratorServiceImpl();
        int number = generator.generateNumber();
        int [] iArray = new int[] {0,1,2,3,4,5,6,7,8,9};
        assertTrue(Arrays.binarySearch(iArray, number) >= 0, "It`s not a number");
    }

    @Test
    public void generate_random_small_char() {
        GeneratorServiceImpl generator = new GeneratorServiceImpl();
        char str = generator.generateSmallChar();
        String smallStr = "abcdefghijklmnopqrstuvwxyz";
        assertTrue(smallStr.contains(String.valueOf(str)), "It`s not a small char");
    }

    @Test
    public void generate_random_big_char() {
        GeneratorServiceImpl generator = new GeneratorServiceImpl();
        char str = generator.generateBigChar();
        String bigStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        assertTrue(bigStr.contains(String.valueOf(str)), "It`s not a big char");
    }

    @Test
    public void generate_random_special_symbol() {
        GeneratorServiceImpl generator = new GeneratorServiceImpl();
        char symbol = generator.generateCpecialSymbol();
        String symbolString = "!#$%&'()*+,-./:;<=>?@[]^_`{|}~";
        assertTrue(symbolString.contains(String.valueOf(symbol)), "It`s not a special symbol");
    }

    @Test
    public void generate_random_password() {
        GeneratorServiceImpl generator = new GeneratorServiceImpl();
        String password = generator.generatePassword(20);
        assertEquals(true ,
                password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–{}:;',?/*~$^+=<>]).{8,20}$"));
    }

    @Test
    public void generate_random_password_should_return_nothing() {
        GeneratorServiceImpl generator = new GeneratorServiceImpl();
        String password = generator.generatePassword(0);
        assertEquals("", password);
    }

    @Test
    public void isNumber_should_return_true() {
        GeneratorServiceImpl generator = new GeneratorServiceImpl();
        assertTrue(generator.isNumber("21247"));
    }

    @Test
    public void isNumber_should_return_false() {
        GeneratorServiceImpl generator = new GeneratorServiceImpl();
        assertFalse(generator.isNumber("5738a97"));
    }

    @Test
    public void isFirst_symbol_in_password_number_letter() {
        GeneratorServiceImpl generator = new GeneratorServiceImpl();
        for (int i = 0; i < 30; i++) {
            char firstCharOfPassword = generator.generatePassword(20).charAt(0);
            assertTrue(("" + firstCharOfPassword).matches("\\w"));
        }
    }

    @Test
    public void check_clipboard() {
        GeneratorServiceImpl generator = new GeneratorServiceImpl();
        generator.copyToClipboard("PasSworD");
        assertEquals("PasSworD", generator.pasteFromClipboard());
    }
}
