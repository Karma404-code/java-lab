package org.example;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import org.example.dashboard.DashboardUI;
import org.example.login.LoginUI;
import org.example.student.ui.StudentTable;

import javax.swing.*;
import java.awt.*;

public class Main {
    static void main() {
        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
            JFrame frame = new JFrame();
            frame.setTitle("Student Management System");
            frame.setLocation(800, 600);
            frame.setSize(1600, 1200);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());

            JPanel loginPanel = new LoginUI(frame);
            // JPanel dashboardPanel = new DashboardUI(frame);
            JPanel studentPanel = new StudentTable(frame);
            // frame.add(new LoginUI(frame), BorderLayout.CENTER);
            frame.add(new DashboardUI(frame), BorderLayout.CENTER);
            frame.setVisible(true);
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }
    }
}
