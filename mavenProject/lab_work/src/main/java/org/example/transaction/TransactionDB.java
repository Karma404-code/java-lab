package org.example.transaction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TransactionDB {
    Connection conn;

    TransactionDB(Connection conn) {
        String url = "jdbc:postgresql://localhost:5432/transaction";
        String username = "postgres";
        String password = "postgres";

        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
