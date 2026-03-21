package org.example.swing.event;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MyAdapter extends MouseAdapter {

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("mouseClicked from adapter class");
    }

}
