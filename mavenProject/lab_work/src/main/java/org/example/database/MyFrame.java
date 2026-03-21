package org.example.database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class MyFrame extends JFrame {

    int fontSize = 32;
    DBConnect db = new DBConnect();

    JLabel username, password, createAccount;
    JTextField usernameTextField, passwordTextField;
    JButton submit;

    MyFrame(){
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 1200);
        setLayout(new FlowLayout(FlowLayout.CENTER));

        username = new JLabel("Username:");
        username.setFont(new Font("Times New Roman", Font.PLAIN, fontSize));

        password = new JLabel("Password:");
        password.setFont(new Font("Times New Roman", Font.PLAIN, fontSize));

        usernameTextField = new JTextField(15);
        usernameTextField.setFont(new Font("Times New Roman", Font.PLAIN, fontSize));

        passwordTextField = new JTextField(15);
        passwordTextField.setFont(new Font("Times New Roman", Font.PLAIN, fontSize));

        submit = new JButton("Submit");
        submit.addActionListener(e -> {
            String username = usernameTextField.getText();
            String password = passwordTextField.getText();

            try {
                if(db.validate(username, password)) {
                    new Dashboard();
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        createAccount = new JLabel("Create Account:");
        createAccount.addMouseListener(new  MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                new RegistrationForm();
            }
        });

        add(username);
        add(usernameTextField);

        add(password);
        add(passwordTextField);

        add(submit);

        add(createAccount);
    }

}
