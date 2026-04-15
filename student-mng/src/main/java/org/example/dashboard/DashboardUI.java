package org.example.dashboard;

import org.example.login.LoginService;
import org.example.login.LoginUI;
import org.example.student.ui.StudentTable;

import javax.swing.*;
import java.awt.*;

public class DashboardUI extends JPanel {
    LoginService loginService;
    JFrame parentFrame;

    public DashboardUI(JFrame parentFrame) {
        loginService = LoginService.getLoginService();

        this.parentFrame = parentFrame;

        setLayout(new BorderLayout());
        JLabel welcomeLabel = new JLabel("Welcome to Dashboard!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.addActionListener(e -> {
            loginService.logout();
            switchToLogin();
        });
        
        JPanel topPanel = new JPanel();
        topPanel.add(logoutBtn);

        JButton studentBtn = new JButton("Go to Students table");
        studentBtn.addActionListener(e -> {
            switchToStudent();
        });

        topPanel.add(studentBtn);

        add(welcomeLabel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.SOUTH);
    }

    private void switchToLogin() {
        parentFrame.getContentPane().removeAll();
        parentFrame.add(new LoginUI(parentFrame));
        parentFrame.revalidate();
        parentFrame.repaint();
    }

    private void switchToStudent() {
        parentFrame.getContentPane().removeAll();
        parentFrame.add(new StudentTable(parentFrame));
        parentFrame.revalidate();
        parentFrame.repaint();
    }

}
