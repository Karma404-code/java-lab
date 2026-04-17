package org.example.login;

import java.sql.*;

public class AccountRepository {

    Connection conn;
    static AccountRepository accountRepository = new AccountRepository();

    private AccountRepository(){
        try {
            String url = "jdbc:h2:mem:school;DB_CLOSE_DELAY=-1;INIT=RUNSCRIPT FROM 'classpath:h2init.sql';USER=admin;PASSWORD=admin";
            String user = "admin";
            String password = "admin";

            conn = DriverManager.getConnection(url, user, password);
        } catch ( SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static AccountRepository getAccountRepository() {
        if(accountRepository == null) accountRepository = new AccountRepository();
        return accountRepository;
    }

    public Boolean findAccount(String username, String password) {
        PreparedStatement pst;
        boolean result = false;
        try {
            pst = conn.prepareStatement("select * from account where username = ? AND password = ?");
            pst.setString(1, username);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();
            result = rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public void addAccount(String username, String password){
        PreparedStatement pst;
        try {
            pst = conn.prepareStatement("INSERT INTO account (username, password) VALUES (?, ?)");
            pst.setString(1, username);
            pst.setString(2, password);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
