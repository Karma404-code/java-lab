package org.example.swing.layout;

import javax.swing.*;
import java.awt.*;

public class MyLayouts extends JFrame {

    JPanel p1, p2, p3, p4, p5;

    public MyLayouts() {
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Layout Demo");
        setLocation(500, 500);
      // setLayout(new BorderLayout());

    //    setLayout(new FlowLayout(FlowLayout.TRAILING, 50, 15));
        // TODO: look into orientation for leading and trailing
//
     //    setLayout(new GridLayout(1, 3, 10, 15));
       setLayout(new CardLayout());
//       // setLayout(null);
        p1 = new JPanel();
       p1.setBackground(Color.PINK);
//       // p1.setBounds(150, 200, 80, 30);
//
        p2 = new JPanel();
        p2.setBackground(Color.RED);
//        //p2.setBounds(180, 150, 80, 30);
//
        p3 = new JPanel();
        p3.setBackground(Color.BLUE);
//        //p3.setBounds(180, 100, 80, 30);
//
        p4 = new JPanel();
//        p4.setBackground(Color.GREEN);
//        // p4.setBounds(230, 150, 30, 80);
//        // p4.setPreferredSize(new Dimension(800, 200));
//
       p5 = new JPanel();
//        p5.setBackground(Color.GRAY);
//        // p5.setBounds(150, 100, 30, 80);

        add(p1);
        add(p2);
        add(p3);
        add(p4);
        add(p5);

    }
}

