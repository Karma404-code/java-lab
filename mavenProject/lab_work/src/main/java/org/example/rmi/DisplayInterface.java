package org.example.rmi;

import java.rmi.Remote;

public interface DisplayInterface extends Remote {
    public void display(String message);
}
