package org.example.student.ui;

import net.miginfocom.swing.MigLayout;
import org.example.student.Student;
import org.example.student.StudentService;

import javax.swing.*;
import java.awt.*;
import java.util.stream.IntStream;

public class EditStudent extends JFrame {
    JFrame parentFrame;
     // JFrame parentFrame;
    StudentTable studentTable;
    Student student;
    StudentService studentService;

    JLabel nameLabel, emailLabel, genderLabel, batchLabel;
    JTextField name, email;
    JComboBox<Integer> batch;
    ButtonGroup gender;
    JRadioButton male, female, others;
    JPanel formPanel;
    JButton save, cancel;
    DefaultComboBoxModel<Integer> batchModel;

    EditStudent(JFrame parentFrame, StudentTable studentTable, Student student) {
        this.parentFrame = parentFrame;
        this.studentTable = studentTable;
        this.student = student;

        setTitle("Edit Student");
        setSize(900, 650);
        setLocation(1200, 900);

        studentService = StudentService.getInstance();
        setLayout(new BorderLayout());

        initButtons();
        displayStudentInformation();

        add(formPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    public void displayStudentInformation(){
         formPanel = new JPanel();
         formPanel.setLayout(new MigLayout());

        nameLabel = new JLabel("Name:");
        name = new JTextField(student.name(), 15);

        emailLabel = new JLabel("Email:");
        email = new JTextField(student.email(), 15);

        int start_year = 2015;
        batchLabel = new JLabel("Batch:");
        batchModel = new DefaultComboBoxModel<>();
        IntStream.range(start_year, 2025)
                .forEach(batchModel::addElement);
        batch = new JComboBox<>(batchModel);
        batch.setSelectedIndex(student.batch() - start_year);

        genderLabel = new JLabel("Gender:");

        male = new JRadioButton("Male");
        female = new JRadioButton("Female");
        others = new JRadioButton("Others");
        gender = new ButtonGroup();

        if(student.gender().equals("Male")){
            male.setSelected(true);
        } else if(student.gender().equals("Female")){
            female.setSelected(true);
        } else {
            others.setSelected(true);
        }
        gender.add(male);
        gender.add(female);
        gender.add(others);


        formPanel.add(nameLabel);
        formPanel.add(name, "wrap");
        formPanel.add(emailLabel);
        formPanel.add(email, "wrap");
        formPanel.add(batchLabel);
        formPanel.add(batch, "wrap");
        formPanel.add(genderLabel, "wrap");
        formPanel.add(male, "wrap");
        formPanel.add(female, "wrap");
        formPanel.add(others, "wrap");

        formPanel.add(save, "cell 0 8, growx");
        formPanel.add(cancel, "cell 0 8, growx");
    }

    public void initButtons(){
        save = new JButton("Save");
        save.addActionListener(e -> {
            String genderInput = male.isSelected() ? "Male" : female.isSelected() ? "Female" : "Others";
            studentService.updateStudent(new Student(
                    student.id(),
                    name.getText(),
                    email.getText(),
                    batchModel.getElementAt(batch.getSelectedIndex()),
                    genderInput));
            studentTable.refreshTable();
            dispose();
            parentFrame.revalidate();
            parentFrame.repaint();
        });

        cancel = new JButton("Cancel");
        cancel.addActionListener(e -> {
            dispose();
           parentFrame.revalidate();
           parentFrame.repaint();
        });
    }
}
