package org.example.threads;

import java.io.Serializable;

public class Account implements Serializable {
    int amount = 1000;

    public void withdraw(int amt) {

        synchronized (this) {
            if (amount >= amt) {
                amount -= amt;
                System.out.println("Transaction successful");
                System.out.println("Remaining balance: " + amount);
            } else {
                System.out.println("Insufficient Amount");
            }
        }
    }

}
