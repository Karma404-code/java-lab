package org.example.threads;

public class ThreadOne extends Thread {
    Account a = new Account();

    public ThreadOne(Account account) {
        this.a = account;
    }

    public void run() {
        a.withdraw(200);
    }
}
