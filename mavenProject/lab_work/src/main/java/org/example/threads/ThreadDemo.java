
package org.example.threads;

public class ThreadDemo implements Runnable {

	@Override
    public void run() {
        System.out.println("From ThreadDemo");
    }
}
