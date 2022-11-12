package by.korziuk.web_pass_generator;

import org.junit.jupiter.api.Test;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class GeneratorTest {

    @Test
    public void generate_random_number(){
        Generator generator = new Generator();
        int number = generator.generateNumber();
        int [] iArray = new int[] {0,1,2,3,4,5,6,7,8,9};
        assertTrue(Arrays.binarySearch(iArray, number) >= 0, "It`s not a number");
    }

    @Test
    public void generate_random_small_char() {
        Generator generator = new Generator();
        char str = generator.generateSmallChar();
        String smallStr = "abcdefghijklmnopqrstuvwxyz";
        assertTrue(smallStr.contains(String.valueOf(str)), "It`s not a small char");
    }

    @Test
    public void generate_random_big_char() {
        Generator generator = new Generator();
        char str = generator.generateBigChar();
        String bigStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        assertTrue(bigStr.contains(String.valueOf(str)), "It`s not a big char");
    }

    @Test
    public void generate_random_special_symbol() {
        Generator generator = new Generator();
        char symbol = generator.generateCpecialSymbol();
        String symbolString = "!#$%&'()*+,-./:;<=>?@[]^_`{|}~";
        assertTrue(symbolString.contains(String.valueOf(symbol)), "It`s not a special symbol");
    }

    @Test
    public void generate_random_password() {
        Generator generator = new Generator();
        String password = generator.generatePassword(20);
        System.out.println(password);
        assertEquals(true ,
                password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–{}:;',?/*~$^+=<>]).{8,20}$"));
    }
}
