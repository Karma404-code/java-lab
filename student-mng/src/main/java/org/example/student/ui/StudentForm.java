package org.example.student.ui;

import net.miginfocom.swing.MigLayout;
import org.example.student.Student;
import org.example.student.StudentService;

import javax.swing.*;
import java.awt.*;
import java.util.stream.IntStream;

public class StudentForm extends JFrame {

    // JFrame parentFrame;
    StudentTable studentTable;
    JLabel nameLabel, emailLabel, genderLabel, batchLabel;
    JTextField name, email;
    JComboBox<Integer> batch;
    ButtonGroup gender;
    JRadioButton male, female, others;
    JPanel formPanel, buttonPanel;
    JButton confirm, cancel;
    DefaultComboBoxModel<Integer> batchModel;

    StudentService studentService;

    StudentForm(StudentTable studentTable) {
        studentService = StudentService.getInstance();
        this.studentTable = studentTable;

        setSize(600, 600);
        setTitle("Add Student");

        initForm();
        initButtons();

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void initForm() {
        formPanel = new JPanel();
        formPanel.setLayout(new MigLayout());
        // formPanel.setPreferredSize(new Dimension(600, 600));
        formPanel.setSize(600, 600);
        setLocation(1000, 800);

        nameLabel = new JLabel("Name:");
        name = new JTextField(15);

        emailLabel = new JLabel("Email:");
        email = new JTextField(15);

        batchLabel = new JLabel("Batch:");
        batchModel = new DefaultComboBoxModel<>();
        IntStream.range(2015, 2025)
                .forEach(batchModel::addElement);
        batch = new JComboBox<>(batchModel);

        genderLabel = new JLabel("Gender:");

        male = new JRadioButton("Male");
        female = new JRadioButton("Female");
        others = new JRadioButton("Others");
        gender = new ButtonGroup();
        gender.add(male);
        gender.add(female);
        gender.add(others);


        formPanel.add(nameLabel);
        formPanel.add(name, "wrap");
        formPanel.add(emailLabel);
        formPanel.add(email, "wrap");
        formPanel.add(batchLabel);
        formPanel.add(batch, "wrap");
        formPanel.add(genderLabel);
        formPanel.add(male, "cell 1 3");
        formPanel.add(female, "cell 1 4");
        formPanel.add(others, "cell 1 5");
    }

    public void initButtons() {

        buttonPanel = new JPanel();
        buttonPanel.setSize(600, 200);
        confirm = new JButton("Confirm");
        confirm.addActionListener(e -> {
            String genderInput = male.isSelected() ? "Male" : female.isSelected() ? "Female" : "Others";
            studentService.addStudent(new Student(
                    0,
                    name.getText(),
                    email.getText(),
                    batchModel.getElementAt(batch.getSelectedIndex()),
                    genderInput));
            studentTable.refreshTable();
            dispose();
        });

        cancel = new JButton("Cancel");
        cancel.addActionListener(e -> dispose());

        buttonPanel.add(confirm);
        buttonPanel.add(cancel);

    }
}
