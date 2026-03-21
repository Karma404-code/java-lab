package org.example.swing.menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class MyMenuBar extends JFrame {
    JMenuBar menuBar;
    JMenu fileMenu, editMenu, viewMenu;
    JMenuItem save, open, exit;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MyMenuBar myMenuBar = new MyMenuBar();
            // myMenuBar.setFont(new Font("Arial", Font.BOLD, 32));
            myMenuBar.setVisible(true);
        });
    }

    public MyMenuBar() {

        setTitle("Menu Bar");
        setSize(2000, 1800);
        setLocation(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        menuBar = new JMenuBar();

        save = new JMenuItem("Save");
        save.setMnemonic(KeyEvent.VK_S);

        open = new JMenuItem("Open");
        open.setMnemonic(KeyEvent.VK_O);

        exit = new JMenuItem("Exit");
        exit.setMnemonic(KeyEvent.VK_X);

        fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        
        fileMenu.add(save);
        fileMenu.add(open);
        fileMenu.add(exit);

        editMenu = new JMenu("Edit");
        viewMenu = new JMenu("View");

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(viewMenu);


        setJMenuBar(menuBar);

    }
}
