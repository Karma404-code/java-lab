package org.example.polymorphism;

public class AdvCalc extends Calc {

    @Override
    public int add(int a, int b) {
        System.out.printf("%d + %d = %d\n", a, b, a + b);
        return 0;
    }
}
