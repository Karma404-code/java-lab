package org.example.swing.event;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Practice extends JFrame implements ActionListener {


    JButton anonymous2;
    JButton anonymous;

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("self implementation");
    }

    class InnerHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            JButton button = (JButton) e.getSource();

            //System.out.println(e.getSource());
            if(button.equals(anonymous)){
                System.out.println("from button in action event");
            }

//            if(e.getSource().equals(anonymous)) {
//                System.out.println("Inner Button pressed From Button one");
//            }
//            if(e.getSource().equals(anonymous2)){
//                System.out.println("Inner Button pressed From Button Two");
//                System.out.println(e.getSource());
//            }

        }
    }

    JLabel labelButton;
    InnerHandler innerHandler;
    Handler handler;
    JButton button;

    Practice() {
        setTitle("Event");
        setSize(1200, 1000);
        setLocation(700, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLayout(new FlowLayout());

        handler = new Handler();
        innerHandler = new InnerHandler();

        button = new JButton("Clicke me");
        button.addActionListener(handler);

        JButton innerButton = new JButton("Inner Class Button");
        innerButton.addActionListener(innerHandler);

        JButton selfImplementation = new JButton("Self Implementation");
        selfImplementation.addActionListener(this);

        anonymous = new JButton("Anonymous");
        anonymous.addActionListener(innerHandler);

        anonymous2 = new JButton("Anonymous2");
        anonymous2.addActionListener(innerHandler);

        labelButton = new JLabel("Label Button");
        // MouseHandler mouseHandler = new MouseHandler();
        MyAdapter myAdapter = new MyAdapter();
        labelButton.addMouseListener(myAdapter);

        add(labelButton);
        add(button);
        add(innerButton);
        add(selfImplementation);
        add(anonymous);
        add(anonymous2);
    }


}
