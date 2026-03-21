package org.example.transaction;

import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {
    TransactionDB transactionDB;
    JDialog dialog;

    MyFrame() {
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 1200);
        setLayout(new FlowLayout(FlowLayout.CENTER));

        dialog = new JDialog();
        dialog.setVisible(false);
        dialog.setModal(true);
        dialog.setTitle("Open Frame");
        dialog.setLocationRelativeTo(null);
        dialog.setSize(400, 600);

        JButton openFrame = new JButton("Open Frame");
        openFrame.addActionListener(e -> {
            dialog.add(new JLabel("This is some text"));
            dialog.setVisible(true);
            JOptionPane.showMessageDialog(dialog, "This is some text");
        });

        add(openFrame);

    }
}
