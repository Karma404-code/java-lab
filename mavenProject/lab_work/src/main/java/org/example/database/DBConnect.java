package org.example.database;

import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.sql.*;

public class DBConnect {

    Connection conn;

    DBConnect() {

        String url = "jdbc:postgresql://localhost:5432/testdb";
        String username = "postgres";
        String password = "postgres";

        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Boolean validate(String username, String password) throws SQLException {
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select * from users where username = '" + username + "'"
        + " and password = '" + password + "'");

        if(rs.next()) {
            System.out.println("Login Success");
            return true;
        } else {
            System.out.println("Login Failed");
            return false;
        }
    }

    public void createUser(String username, String password) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("insert into users(username, password) values(?,?)");
        ps.setString(1, username);
        ps.setString(2, password);

        ps.executeUpdate();
        ps.close();
    }
}
