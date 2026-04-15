package org.example.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface DisplayInterface extends Remote {
    public void display(String message) throws RemoteException;
}
