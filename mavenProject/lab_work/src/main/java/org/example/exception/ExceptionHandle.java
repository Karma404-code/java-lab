package org.example.exception;

import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class ExceptionHandle {
    public static void main(String[] args) throws NationalityException, UnderAgeException {

        Scanner scanner = new Scanner(System.in);
            System.out.println("Enter your nationality");
            String nationality = scanner.next();

            if(!nationality.equals("nepali")) throw new NationalityException("User should be nepali");

            System.out.println("Enter your age");
            int age = scanner.nextInt();

            if(age < 18) throw new UnderAgeException("User is not 18 years old");

            System.out.println("Nationality: " + nationality
                    + "\nAge: " + age);

    }
}
