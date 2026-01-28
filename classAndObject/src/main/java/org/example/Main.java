package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        Student s = new Student("Sushan");
        s.setAge(14);

        Student.Faculty f = s.getFaculty();

        System.out.println(s.toString());
        System.out.println(f.toString());

    }
}
