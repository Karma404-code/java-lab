package org.example.student.ui;

import org.example.dashboard.DashboardUI;
import org.example.student.Student;
import org.example.student.StudentService;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.stream.IntStream;

public class StudentTable extends JPanel {
    JFrame parentFrame;
    JPanel studentOptionPanel;
    JScrollPane scrollPane;
    JTextField searchStudent;
    JButton searchButton, addStudentButton, editStudentButton, deleteStudentButton, dashboardButton;
    DefaultTableModel model;
    JTable table;
    StudentService studentService;

    public StudentTable(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        studentService = StudentService.getInstance();

        setLayout(new BorderLayout());

        initStudentOptionPanel();
        initStudentTable();

        add(studentOptionPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

    }

    public void initStudentOptionPanel() {
        studentOptionPanel = new JPanel();

        searchStudent = new JTextField();
        searchButton = new JButton("Search");

        addStudentButton = new JButton("Add");
        addStudentButton.addActionListener(e -> {
            new StudentForm(this);
        });

        editStudentButton = new JButton("Edit");
        editStudentButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                // todo : get the selected student in edit panel
                int studentId = Integer.parseInt(model.getValueAt(selectedRow, 0).toString());
                Student student = studentService.getStudentById(studentId);
                parentFrame.add(new EditStudent(parentFrame, this, student), BorderLayout.EAST);
                parentFrame.revalidate();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Please select a student to edit",
                        "No Selection",
                        JOptionPane.WARNING_MESSAGE);
            }
        });
        deleteStudentButton = new JButton("Delete");
        deleteStudentButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                int studentId = Integer.parseInt(model.getValueAt(selectedRow, 0).toString());
                // Confirm deletion
                int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete this student?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    studentService.removeStudentById(studentId);
                    refreshTable(); // Refresh after deletion
                }
            } else {
                JOptionPane.showMessageDialog(this,
                    "Please select a student to delete",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE);
            }
        });

        dashboardButton = new JButton("Dashboard");
        dashboardButton.addActionListener(e -> {
           parentFrame.getContentPane().removeAll();
           parentFrame.add(new DashboardUI(parentFrame), BorderLayout.CENTER);
           parentFrame.revalidate();
           parentFrame.repaint();
        });

      /*  studentOptionPanel.add(searchStudent);
        studentOptionPanel.add(searchButton);*/
        studentOptionPanel.add(addStudentButton);
        studentOptionPanel.add(editStudentButton);
        studentOptionPanel.add(deleteStudentButton);
        studentOptionPanel.add(dashboardButton);
    }

    public void initStudentTable() {

            Object[] cols = {"Id", "Name", "Email", "Batch", "Gender"};

            model = new DefaultTableModel(cols, 0);

            // Create a JTable with the data model
            table = new JTable(model);
            table.removeColumn(table.getColumn("Id"));
            table.setDefaultEditor(Object.class, null);

            // Create a renderer for center alignment
            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(JLabel.CENTER);
            // set all columns to center
            IntStream.range(0, table.getColumnCount())
                            .forEach(i -> table.getColumnModel()
                                    .getColumn(i)
                                    .setCellRenderer(centerRenderer));

            refreshTable();
            // Create a JScrollPane to hold the table
            scrollPane = new JScrollPane(table);
        }

    public void refreshTable() {
        // Clear existing rows
        model.setRowCount(0);

        // Add all students from service
        for (Student student : studentService.getStudentList()) {
            model.addRow(new Object[]{
                    student.id(),
                    student.name(),
                    student.email(),
                    student.batch(),
                    student.gender()
            });
        }
    }

}
