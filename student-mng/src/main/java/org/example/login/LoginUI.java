package org.example.login;

import net.miginfocom.swing.MigLayout;
import org.example.dashboard.DashboardUI;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

public class LoginUI extends JPanel {

    LoginService loginService;

    JFrame parentFrame;
    JTextField name;
    JPasswordField pwd;
    JLabel nameLabel, pwdLabel, createAccountLabel;
    JButton loginBtn;

    public
    LoginUI(JFrame parentFrame) {
        loginService = LoginService.getLoginService();
        this.parentFrame = parentFrame;

        setLayout(new MigLayout());

        nameLabel = new JLabel("Name:");
        name = new JTextField(15);

        pwdLabel = new JLabel("Password:");
        pwd = new JPasswordField(15);

        loginBtn = new JButton("login");
        loginBtn.addActionListener((e) ->
        {
            String inputName = name.getText();
            String inputPwd = new String(pwd.getPassword());

            if(loginService.login(inputName, inputPwd)) {
                switchToDashboard();
            } else {
                // Show error message
                JOptionPane.showMessageDialog(this, 
                    "Invalid username or password!", 
                    "Login Failed", 
                    JOptionPane.ERROR_MESSAGE);
            }

        });

        createAccountLabel = new JLabel("No Account? Click here to register");
        createAccountLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                new RegisterAccount(parentFrame);
                System.out.println("clicked");
            }
        });

        add(nameLabel);
        add(name, "wrap");

        add(pwdLabel);
        add(pwd, "wrap");

        add(loginBtn, "wrap, cell 1 3, growx");
        add(createAccountLabel, "wrap, cell 1 3, growx");
    }

    private void switchToDashboard() {
        parentFrame.getContentPane().removeAll();
        parentFrame.add(new DashboardUI(parentFrame));
        parentFrame.revalidate();
        parentFrame.repaint();
    }

}
