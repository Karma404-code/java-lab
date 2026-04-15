package org.example.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class DisplayClass extends UnicastRemoteObject implements DisplayInterface {

    protected DisplayClass() throws RemoteException {
        super();
    }

    public void display(String message) throws RemoteException {
        System.out.println(message);
    }
}
