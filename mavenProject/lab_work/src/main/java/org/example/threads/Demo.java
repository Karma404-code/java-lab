package org.example.threads;

public class Demo extends Thread {

    public void run() {
        for(int i=0; i<100; i++) {
            System.out.println("Demo: " + i);
        }
    }
}
