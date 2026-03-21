package org.example.database;

import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {

        new MyFrame();

        // try {
//    Class.forName("org.postgresql.Driver");
// } catch (ClassNotFoundException e) {
//    System.err.println("Where is your PostgreSQL JDBC Driver? "
//            + "Include in your library path!");
//    e.printStackTrace();
// }

//        // create connection
//        Connection con = DriverManager.getConnection(url, username, password);
//
//        // create statement
//        Statement st  = con.createStatement();
//
//        // execute query
//        ResultSet rs = st.executeQuery("Select * from students");
//
//        while(rs.next()) {
//            int id = rs.getInt("id");
//            String name = rs.getString("name");
//            System.out.println("ID: " + id);
//            System.out.println("Name: " + name);
//        }

    //    con.close();

//        RowSetFactory rowSetFactory = RowSetProvider.newFactory();
//        JdbcRowSet jdbcRowSet = rowSetFactory.createJdbcRowSet();
//        jdbcRowSet.setUrl(url);
//        jdbcRowSet.setUsername(username);
//        jdbcRowSet.setPassword(password);
//        jdbcRowSet.setCommand("select * from students");
//
//        jdbcRowSet.execute();
//
//        while(jdbcRowSet.next()){
//            System.out.println(jdbcRowSet.getInt("id"));
//            System.out.println(jdbcRowSet.getString("name"));
//        }
    }

}
