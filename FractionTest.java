import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FractionTest {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Введите выражение в формате: \n числитель/знаменатель +(-, *, :) числитель/знаменатель");
        String line = in.nextLine();
        Fraction result = Fraction.doCount(line);
    }
}



