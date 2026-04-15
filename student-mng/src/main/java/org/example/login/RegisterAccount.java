package org.example.login;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class RegisterAccount extends JDialog {
    LoginService loginService;
    JFrame parentFrame;
    JLabel nameLabel, pwdLabel, confirmPwdLabel, emailLabel;
    JPanel registrationPanel;

    JTextField nameField, emailField;
    JPasswordField pwdField, confirmPwdField;
    JButton registerButton, cancelButton;

    RegisterAccount(JFrame parentFrame) {
        loginService = LoginService.getLoginService();
        setLocation(1000, 800);
        this.parentFrame = parentFrame;
        setTitle("Register Account");
        setSize(800, 600);
        setVisible(true);
        setLayout(new BorderLayout());

        initComponents();

        add(registrationPanel, BorderLayout.CENTER);
    }

    void initComponents() {
        registrationPanel = new JPanel();
        registrationPanel.setLayout(new MigLayout());

        nameLabel = new JLabel("Name:");
        emailLabel = new JLabel("Email:");
        pwdLabel = new JLabel("Password:");
        confirmPwdLabel = new JLabel("Confirm Password:");

        JTextField nameField = new JTextField(15);
        JTextField emailField = new JTextField(15);

        JPasswordField pwdField = new JPasswordField(15);
        JPasswordField confirmPwdField = new JPasswordField(15);

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(e -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String  password = new String(pwdField.getPassword());
            String  confirmPassword = new String(confirmPwdField.getPassword());

            if(name.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(parentFrame, "Username & Password cannot be empty");
            }
            else if(password.equals(confirmPassword)) {
                loginService.register(name, password);
                // System.out.println(name + " " + password + " " + email);
                this.dispose();
                parentFrame.revalidate();
                parentFrame.repaint();
            } else {
                JOptionPane.showMessageDialog(parentFrame, "Passwords do not match");
            }

        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> {
            this.dispose();
            parentFrame.revalidate();
            parentFrame.repaint();
        });

        registrationPanel.add(emailLabel);
        registrationPanel.add(emailField, "wrap");

        registrationPanel.add(nameLabel);
        registrationPanel.add(nameField, "wrap");

        registrationPanel.add(pwdLabel);
        registrationPanel.add(pwdField, "wrap");

        registrationPanel.add(confirmPwdLabel);
        registrationPanel.add(confirmPwdField, "wrap");

        registrationPanel.add(registerButton, "wrap, cell 1 5, growx");
        registrationPanel.add(cancelButton, "wrap, cell 1 5, growx");

    }
}
