package org.example.database;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.jar.JarFile;

public class RegistrationForm extends JFrame {

    int fontSize = 32;

    DBConnect db;

    JLabel username, password, confirmation;
    JTextField usernameTextField, passwordTextField,  confirmationTextField;
    JButton registerButton;

    RegistrationForm() {
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 1200);
        setLayout(new FlowLayout(FlowLayout.CENTER));

        db = new DBConnect();

        username = new JLabel("Username:");
        username.setFont(new Font("Times New Roman", Font.PLAIN, fontSize));

        password = new JLabel("password:");
        password.setFont(new Font("Times New Roman", Font.PLAIN, fontSize));

        confirmation = new JLabel("confirm password:");
        confirmation.setFont(new Font("Times New Roman", Font.PLAIN, fontSize));

        usernameTextField = new JTextField(15);
        passwordTextField = new JTextField(15);
        confirmationTextField = new JTextField(15);

        registerButton = new JButton("Register");
        registerButton.addActionListener(e -> {
            String username = usernameTextField.getText();
            String password = passwordTextField.getText();
            String confirmation = confirmationTextField.getText();

            try {
                if(confirmation.equals(password)){
                    db.createUser(username, password);
                    System.out.println("User created successfully");
                }
            } catch (SQLException exception) {
                System.out.println(exception.getMessage());
            }
        });

        add(username);
        add(usernameTextField);

        add(password);
        add(passwordTextField);

        add(confirmation);
        add(confirmationTextField);

        add(registerButton);
    }
}
