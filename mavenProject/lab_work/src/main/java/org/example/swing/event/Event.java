package org.example.swing.event;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Event extends JFrame implements ActionListener {

    public static void main(String[] args) {
        new Event();
    }

    Event() {
        setTitle("Event");
        setSize(1200, 1000);
        setLocation(700, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLayout(null);

        configButton();
    }

    public void configButton() {
        JButton button = new JButton("Button");
        button.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 32));
        button.setFocusable(false);

        button.setBounds(500, 500, 250, 70);

        add(button);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
