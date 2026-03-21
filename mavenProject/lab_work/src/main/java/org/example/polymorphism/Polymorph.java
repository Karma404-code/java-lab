package org.example.polymorphism;

public class Polymorph {
    public static void main(String[] args) {
        Calc calc = new Calc();
        AdvCalc advCalc = new AdvCalc();
        Calc calcOverloaded = new AdvCalc();

        System.out.println(calc.add(1, 2));
        System.out.println(calc.add(1,4,5));
        System.out.println(calc.add("hello","world"));

        advCalc.add(1, 2);
        calcOverloaded.add(1, 2);
    }
}
