package org.example.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class MyServer {
    public static void main(String[] args) throws MalformedURLException, RemoteException {
        DisplayClass display = new DisplayClass();
        Naming.rebind("display", display);
        System.out.println("Server ready");
    }
}
