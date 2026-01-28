package org.example;

public class Student {
    String name;
    int age;

    class Faculty{
        String name;
        int duration;

        public Faculty(String f, int d){
            this.name = f;
            this.duration = d;
        }

        @Override
        public String toString() {
            return "Faculty{" +
                    "name='" + name + '\'' +
                    ", duration=" + duration +
                    '}';
        }
    }
    public Student(String name){
        this.name = name;
    }

    public void setAge(int age){
        this.age = age;
    }

    public Faculty getFaculty(){
        return new Faculty("CSIT", 20);
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
