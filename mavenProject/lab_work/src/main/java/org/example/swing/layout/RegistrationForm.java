package org.example.swing.layout;

import java.awt.*;
import java.util.Vector;
import javax.swing.*;

public class RegistrationForm extends JFrame {

    JLabel name, address, number, gender, hobby, description, password;
    JTextField nameTxt, addressTxt, numberTxt;
    JRadioButton male, female;
    JCheckBox coding, sleeping, riding;
    JButton submit, reset;
    JTextArea message;
    JPasswordField passwordField;
    JMenuBar  menuBar;
    JMenu fileMenu, editMenu, settingMenu;
    JMenuItem create, exit, open;

    JComboBox<String> faculty, sections;
    JComboBox<Integer> numbers;

    JList<Integer> list;
    JTable table;

    public RegistrationForm() {
        setTitle("Registration Form");
        setSize(2000, 1800);
        setLocation(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        menuBar = new JMenuBar();
        //menuBar.setFont(new Font("Times New Roman", Font.BOLD, 32));
        setJMenuBar(menuBar);

        create = new JMenuItem("Create");
        exit = new JMenuItem("Exit");
        open = new JMenuItem("Open");


        fileMenu = new JMenu("File");
        fileMenu.add(create);
        fileMenu.add(exit);
        fileMenu.add(open);
        fileMenu.setFont(new Font("Tahoma", Font.BOLD, 32));
        editMenu = new JMenu("Edit");
        editMenu.setFont(new Font("Tahoma", Font.BOLD, 32));
        settingMenu = new JMenu("Settings");
        settingMenu.setFont(new Font("Tahoma", Font.BOLD, 32));


        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(settingMenu);

        faculty = new JComboBox<>();
        faculty.setFont(new Font("Arial", Font.BOLD, 32));
        faculty.addItem("CSIT");
        faculty.addItem("BCA");
        faculty.addItem("BIT");

        String[] str = {"A", "B", "C"};
        DefaultComboBoxModel modelOne = new DefaultComboBoxModel();
        modelOne.addElement("Hello");
        modelOne.addElement("World");
        modelOne.addElement(new Integer(0));

        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(str);
        Vector<String> vector = new Vector<String>();
        vector.add("A");
        vector.add("B");
        vector.add("C");
        vector.add("D");

        sections = new JComboBox<>(vector);
        sections.setFont(new Font("Arial", Font.BOLD, 32));
        add(sections);


        Integer[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        numbers = new JComboBox<>(arr);
        numbers.setFont(new Font("Arial", Font.BOLD, 32));
        add(numbers);



        DefaultListModel<Integer> modelList = new DefaultListModel<>();
        modelList.addElement(1);
        modelList.addElement(2);
        modelList.addElement(3);

        Vector<Integer> vectorList = new Vector<>();
        vectorList.add(10);
        vectorList.add(20);
        vectorList.add(30);

        list = new JList<Integer>(vectorList);
        list.setFont(new Font("Arial", Font.BOLD, 32));
        add(list);

        name = new JLabel(); //
        name.setText("Name");
        name.setFont(new Font("Segoe UI", Font.BOLD, 32));

        // addComponent(name, row, 0, 1);

        address = new JLabel("Address");
        address.setFont(new Font("Segoe UI", Font.BOLD, 32));
        address.setAlignmentX(Component.CENTER_ALIGNMENT);

        // addComponent(address, row++, 0, 1);

        ImageIcon numberIcon= new ImageIcon("16.jpeg");

        number = new JLabel(numberIcon);
        number.setText("Number");
        number.setFont(new Font("Segoe UI", Font.BOLD, 32));
        number.setAlignmentX(Component.CENTER_ALIGNMENT);
        // addComponent(number, row++, 0, 1);

        nameTxt= new JTextField();
        nameTxt.setFont(new Font("Segoe UI", Font.BOLD, 32));
        // addComponent(nameTxt, row++, 0, 1);

        addressTxt= new JTextField(15);
        addressTxt.setFont(new Font("Segoe UI", Font.BOLD, 32));
        // addComponent(addressTxt, row++, 0, 1);

        numberTxt = new JTextField("98XXXXXXXX");
        numberTxt.setFont(new Font("Segoe UI", Font.BOLD, 32));
        //numberTxt.setAlignmentX(Component.CENTER_ALIGNMENT);
        // addComponent(numberTxt, row++, 0, 1);

        gender = new JLabel("Gender");
        gender.setFont(new Font("Segoe UI", Font.BOLD, 32));
        //gender.setAlignmentX(Component.CENTER_ALIGNMENT);
        // addComponent(gender, row++, 0, 1);

        ButtonGroup bg = new ButtonGroup();
        male= new JRadioButton("male");
        male.setFont(new Font("Segoe UI", Font.BOLD, 32));
        // addComponent(male, ++row, 0, 1);

        female= new JRadioButton("female");
        female.setFont(new Font("Segoe UI", Font.BOLD, 32));
        // addComponent(female, row++, 0, 1);

        bg.add(male);
        bg.add(female);

        hobby = new JLabel("Hobby");
        hobby.setFont(new Font("Segoe UI", Font.BOLD, 32));
        // addComponent(hobby, row++, 0, 1);

        coding = new JCheckBox("Coding");
        coding.setFont(new Font("Segoe UI", Font.BOLD, 32));
        // addComponent(coding, row++, 0, 1);

        sleeping = new JCheckBox("Sleeping");
        sleeping.setFont(new Font("Segoe UI", Font.BOLD, 32));
        // addComponent(sleeping, row++, 0, 1);

        riding = new JCheckBox("Riding");
        riding.setFont(new Font("Segoe UI", Font.BOLD, 32));
        // addComponent(riding, row++, 0, 1);

        password= new JLabel("Password");
        passwordField= new JPasswordField(20);
        password.setFont(new Font("Segoe UI", Font.BOLD, 32));
        // addComponent(password, row++, 0, 1);

        description= new JLabel("Description");
        message = new JTextArea(15,30);
        // addComponent(message, row++, 0, 1);

        description.setFont(new Font("Segoe UI", Font.BOLD, 32));
        // addComponent(description, row++, 0, 1);

        submit = new JButton("Submit");
        submit.setFont(new Font("Segoe UI", Font.BOLD, 32));
        // addComponent(submit, row++, 0, 1);

        reset = new JButton("Reset");
        reset.setFont(new Font("Segoe UI", Font.BOLD, 32));
        // addComponent(reset, row++, 0, 1);

        add(name);
        add(nameTxt);

        add(address);
        add(addressTxt);

        add(number);
        add(numberTxt);

        add(password);
        add(passwordField);

        add(faculty);

        add(gender);
        add(male);
        add(female);

        add(hobby);
        add(coding);
        add(sleeping);
        add(riding);

        add(description);
        add(message);


        add(submit);
        add(reset);


    }
}
