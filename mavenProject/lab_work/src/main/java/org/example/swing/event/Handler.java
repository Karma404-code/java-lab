package org.example.swing.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Handler implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Button pressed");
    }
}
