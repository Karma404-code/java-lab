package org.example.student.ui;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import org.example.login.LoginUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MenuBar extends JMenuBar {
    JFrame parentFrame;
    JMenu logout, settingsMenu, colorMenu;
    JMenuItem light, dark;

    public MenuBar(JFrame parentFrame) {
        this.parentFrame = parentFrame;

        settingsMenu = new JMenu("Settings");
        settingsMenu.setMnemonic(KeyEvent.VK_S);
        colorMenu = new JMenu("Color");

        logout = new JMenu("Logout");
        logout.setMnemonic(KeyEvent.VK_Q);
        logout.addMouseListener(new  MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                logout();
            }
        });

        light = new JMenuItem("Light");
        light.addActionListener(e -> changeColor("Light"));
        light.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));

        dark = new JMenuItem("Dark");
        dark.addActionListener(e -> changeColor("Dark"));
        dark.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK));

        colorMenu.add(light);
        colorMenu.add(dark);
        settingsMenu.add(colorMenu);

        add(settingsMenu);
        add(logout);

    }

    private void logout() {
        parentFrame.getContentPane().removeAll();
        parentFrame.add(new LoginUI(parentFrame));
        parentFrame.revalidate();
        parentFrame.repaint();
    }

    private void changeColor(String option) {
        try {
            switch (option) {
                case "Light":
                    UIManager.setLookAndFeel(new FlatIntelliJLaf());
                    parentFrame.getContentPane().removeAll();
                    parentFrame.add(new StudentTable(parentFrame));
                    parentFrame.revalidate();
                    parentFrame.repaint();
                    break;
                case "Dark":
                    UIManager.setLookAndFeel(new FlatDarkLaf());
                    parentFrame.getContentPane().removeAll();
                    parentFrame.add(new StudentTable(parentFrame));
                    parentFrame.revalidate();
                    parentFrame.repaint();
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
