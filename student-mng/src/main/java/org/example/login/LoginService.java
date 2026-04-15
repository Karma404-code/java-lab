package org.example.login;

import org.h2.command.Prepared;

import java.sql.PreparedStatement;

public class LoginService {
    static LoginService loginService = new LoginService();

    boolean loggedIn = false;
    AccountRepository accountRepository = AccountRepository.getAccountRepository();

    LoginService() {}

    public static LoginService getLoginService() {
        if(loginService == null) loginService = new LoginService();
        return loginService;
    }

    public Boolean login(String username, String password) {
        if(accountRepository.findAccount(username,password)) {
            loggedIn = true;
        }
        return loggedIn;
    }

    public void register(String username, String password) {
        accountRepository.addAccount(username, password);
    }

    public void logout() {
        loggedIn = false;
    }

    public Boolean isLoggedIn() {
        return loggedIn;
    }
}
