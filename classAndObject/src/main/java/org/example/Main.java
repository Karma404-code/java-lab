package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {

        int a = 9;
        int b = 9;

        boolean compInt = (a == b );
        System.out.println(compInt);

        var str1 = new String("shrawan");
        var str2 = new String("shrawan");

        boolean compStrObj = str1 == str2;
        System.out.println(compStrObj);

        var str3 = "hello";
        var str4 = "hello";

        boolean compStrPool = str3 == str4;
        System.out.println(compStrPool);

    }
}
