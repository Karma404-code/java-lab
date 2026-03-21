package org.example.exception;

import java.util.Scanner;

public class TryCatch {
    public static void main(String[] args) {
        try{
            Scanner input = new Scanner(System.in);

            System.out.println("Enter two integer: ");
            int a = input.nextInt();
            int b = input.nextInt();

            double division = a / b;
            System.out.println(division);

        } catch (ArithmeticException e){
            System.out.println("Second value can not be 0");
        } finally {
            System.out.println("finally");
        }
    }
}
