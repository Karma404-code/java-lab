package org.example.database;

import javax.swing.*;
import java.awt.*;

public class Dashboard extends JFrame {

    Dashboard() {
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 1200);

        JPanel panel = new JPanel();
        panel.setBackground(Color.PINK);

        add(panel);
    }
}
