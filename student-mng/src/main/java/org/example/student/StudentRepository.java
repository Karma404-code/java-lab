package org.example.student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentRepository {
    Connection conn;

    public StudentRepository() {
        try {
            String url = "jdbc:h2:mem:school;DB_CLOSE_DELAY=-1;INIT=RUNSCRIPT FROM 'classpath:h2init.sql';USER=admin;PASSWORD=admin";
            String user = "admin";
            String password = "admin";

            conn = DriverManager.getConnection(url, user, password);
        } catch ( SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Student> findAll() {
        List<Student> students = new ArrayList<>();
        PreparedStatement stmt;
        try {
            stmt = conn.prepareStatement("select * from student");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                students.add(new Student(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getInt("batch"),
                    rs.getString("gender")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return students;
    }

    public Student findById(int id) {
        PreparedStatement stmt;
        Student student = null;
        try {
            stmt = conn.prepareStatement("select * from student where id = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                student = new Student(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getInt("batch"),
                    rs.getString("gender")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  student;
    }


    public void addById(Student student) {
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement("INSERT INTO student (name, email, batch, gender)" +
                    "VALUES (?, ?, ?, ?)");
            ps.setString(1, student.name());
            ps.setString(2, student.email());
            ps.setInt(3, student.batch());
            ps.setString(4, student.gender());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteById(int id) {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM student WHERE id = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateById(Student student) {
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE student SET name = ?, email = ?, batch = ?, gender = ? WHERE id = ?");
            ps.setString(1, student.name());
            ps.setString(2, student.email());
            ps.setInt(3, student.batch());
            ps.setString(4, student.gender());
            ps.setInt(5, student.id());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Student> sortBy(String column) {
        List<Student> students = new ArrayList<>();
        PreparedStatement stmt;
        try {
            stmt = conn.prepareStatement("select * from student ORDER BY  " +  column);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                students.add(new Student(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getInt("batch"),
                    rs.getString("gender")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return students;
    }
}
