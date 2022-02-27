import com.sun.jdi.IntegerValue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Fraction {
    int numerator;
    int denominator;
    

    private static int nod(int a, int b) { //НОД - это наибольшее число, на которое оба числа «a» и «b» делятся без остатка
        if (b == 0) {
            return a;
        } else {
            return nod(b, a % b);
        }
    }

    private static int nok(int a, int b) { //НОК - наименьшее натуральное число, которое делится на «a» и «b»
        return a / nod(a, b) * b;
    }

    public static Fraction check(String line) {
        Fraction result = new Fraction(1, 1);
        String fPat = "[-+]?([0-9]+\\/[-+]?([0-9]+))";
        Pattern p = Pattern.compile(fPat);
        Matcher m = p.matcher(line);
        if (m.find()) {
            String[] arrSplit = line.split("/");
            result.denominator = Integer.parseInt(arrSplit[1]);
            result.numerator = Integer.parseInt(arrSplit[0]);
            if (result.denominator == 0) {
                throw new IllegalArgumentException("Знаменатель дроби не может быть равным 0");
            }
        } else {
            throw new IllegalArgumentException("Дробь введена в неверном формате");
        }
        return result;
    }

    public static Fraction doCount(String line) {
        Fraction result = new Fraction(1, 1);
        String pat1 = "([-+]?([0-9]+\\/[-+]?([0-9]+)))\\s[+]\\s([-+]?([0-9]+\\/[-+]?([0-9]+)))";
        String pat2 = "([-+]?([0-9]+\\/[-+]?([0-9]+)))\\s[-]\\s([-+]?([0-9]+\\/[-+]?([0-9]+)))";
        String pat3 = "([-+]?([0-9]+\\/[-+]?([0-9]+)))\\s[*]\\s([-+]?([0-9]+\\/[-+]?([0-9]+)))";
        String pat4 = "([-+]?([0-9]+\\/[-+]?([0-9]+)))\\s[:]\\s([-+]?([0-9]+\\/[-+]?([0-9]+)))";

        Pattern sumP = Pattern.compile(pat1);
        Pattern vychP = Pattern.compile(pat2);
        Pattern umnozhP = Pattern.compile(pat3);
        Pattern razdelP = Pattern.compile(pat4);

        Matcher sumM = sumP.matcher(line);
        Matcher vychM = vychP.matcher(line);
        Matcher umnozhM = umnozhP.matcher(line);
        Matcher razdelM = razdelP.matcher(line);

        if (sumM.find()) {
            System.out.println("Формат строки верный: будет выполнено сложение");
            String[] data = line.split("\\s[+]\\s");
            String frac1 = data[0];
            String frac2 = data[1];
            Fraction f1 = Fraction.check(frac1);
            Fraction f2 = Fraction.check(frac2);
            Fraction sumResult = f1.add(f2);
            result = sumResult.reduction();
            System.out.println(f1 + " + " + f2 + " = " + sumResult+ " = " + result);
        } else if (vychM.find()) {
            System.out.println("Формат строки верный: будет выполнена разность");
            String[] data = line.split("\\s[-]\\s");
            String frac1 = data[0];
            String frac2 = data[1];
            Fraction f1 = Fraction.check(frac1);
            Fraction f2 = Fraction.check(frac2);
            Fraction vychResult = f1.substraction(f2);
            result = vychResult.reduction();
            System.out.println(f1 + " - " + f2 + " = " + vychResult + " = " + result);
        } else if (umnozhM.find()) {
            System.out.println("Формат строки верный: будет выполнено умножение");
            String[] data = line.split("\\s[*]\\s");
            String frac1 = data[0];
            String frac2 = data[1];
            Fraction f1 = Fraction.check(frac1);
            Fraction f2 = Fraction.check(frac2);
            Fraction umnozhResult = f1.multiply(f2);
            result = umnozhResult.reduction();
            System.out.println(f1 + " * " + f2 + " = " + umnozhResult + " = " + result);
        } else if (razdelM.find()) {
            System.out.println("Формат строки верный: будет выполнено деление");
            String[] data = line.split("\\s[:]\\s");
            String frac1 = data[0];
            String frac2 = data[1];
            Fraction f1 = Fraction.check(frac1);
            Fraction f2 = Fraction.check(frac2);
            Fraction razdelResult = f1.division(f2);
            result = razdelResult.reduction();
            System.out.println(f1 + " : " + f2 + " = " + razdelResult + " = " + result);
        } else {
            throw new IllegalArgumentException("Выражение введено неверно");
        }
        return result;
    }


    public Fraction(int numerator, int denominator) {
        if (denominator == 0) {
            throw new IllegalArgumentException("Знаменатель дроби не может быть равным 0");
        }
        if (denominator < 0) {
            numerator *= -1;
            denominator *= -1;
        }
        this.numerator = numerator;
        this.denominator = denominator;
    }


    public Fraction add(Fraction other) { // this - данный объект;other - другой объект
        Fraction result = new Fraction(1, 1);
        if (this.denominator == other.denominator) { //если знаменатели одинаковые
            result.denominator = this.denominator;
            result.numerator = this.numerator + other.numerator;
        } else { //если знаменатели разные
            int nok = nok(this.denominator, other.denominator);
            result.denominator = nok;
            result.numerator = this.numerator * (nok / this.denominator) + other.numerator * (nok / other.denominator);
        }
        return result;
    }

    public static Fraction Sum(Fraction f1, Fraction f2) {
        Fraction result = new Fraction(1, 1);
        if (f1.denominator == f2.denominator) { //если знаменатели одинаковые
            result.denominator = f1.denominator;
            result.numerator = f1.numerator + f2.numerator;
        } else { //если знаменатели разные
            int nok = nok(f1.denominator, f2.denominator);
            result.denominator = nok(f1.denominator, f2.denominator);
            result.numerator = f1.numerator * (nok / f1.denominator) + f2.numerator * (nok / f2.denominator);
        }
        return new Fraction(result.numerator, result.denominator);
    }

    public Fraction substraction(Fraction other) { // this - данный объект;other - другой объект
        Fraction result = new Fraction(1, 1);
        if (this.denominator == other.denominator) { //если знаменатели одинаковые
            result.denominator = this.denominator;
            result.numerator = this.numerator - other.numerator;
        } else { //если знаменатели разные
            int nok = nok(this.denominator, other.denominator);
            result.denominator = nok;
            result.numerator = this.numerator * (nok / this.denominator) - other.numerator * (nok / other.denominator);
        }
        return result;
    }

    public static Fraction Vychit(Fraction f1, Fraction f2) {
        Fraction result = new Fraction(1, 1);
        if (f1.denominator == f2.denominator) { //если знаменатели одинаковые
            result.denominator = f1.denominator;
            result.numerator = f1.numerator - f2.numerator;
        } else { //если знаменатели разные
            int nok = nok(f1.denominator, f2.denominator);
            result.denominator = nok;
            result.numerator = f1.numerator * (nok / f1.denominator) - f2.numerator * (nok / f2.denominator);
        }
        return result;
    }

    public Fraction multiply(Fraction other) {
        Fraction result = new Fraction(1, 1);
        result.numerator = this.numerator * other.numerator;
        result.denominator = this.denominator * other.denominator;
        return result;
    }

    public static Fraction Umnozh(Fraction f1, Fraction f2) {
        Fraction result = new Fraction(1, 1);
        result.numerator = f1.numerator * f2.numerator;
        result.denominator = f1.denominator * f2.denominator;
        return result;
    }

    public Fraction division(Fraction other) {
        Fraction result = new Fraction(1, 1);
        result.numerator = this.numerator * other.denominator;
        result.denominator = this.denominator * other.numerator;
        return result;
    }

    public static Fraction Del(Fraction f1, Fraction f2) {
        Fraction result = new Fraction(1, 1);
        result.numerator = f1.numerator * f2.denominator;
        result.denominator = f1.denominator * f2.numerator;
        return result;
    }

    public Fraction reduction(Fraction this) {
        Fraction result = new Fraction(1, 1);
        int t = nod(this.numerator, this.denominator);
        result.numerator = this.numerator / t;
        result.denominator = this.denominator / t;
        if (result.denominator < 0) {
            result.numerator *= -1;
            result.denominator *= -1;
        }
        return result;
    }

    public String toString() {
        return numerator + "/" + denominator;
    }
}



