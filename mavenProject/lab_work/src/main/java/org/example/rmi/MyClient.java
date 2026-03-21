package org.example.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class MyClient {
    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException {
        DisplayInterface displayInterface = (DisplayInterface) Naming.lookup("display");
        displayInterface.display("Hello World");
    }
}
