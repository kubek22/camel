package org.code.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdditionalFrame extends JFrame {

    private final int MAX_PLAYERS = 8;
    private final JComboBox<Integer> comboBox;


    public AdditionalFrame() throws HeadlessException {
        super("Additional Window");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300, 150);

        // Create a JComboBox
        Integer[] choices = new Integer[MAX_PLAYERS];
        for (int i=2; i<=MAX_PLAYERS; i++){
            choices[i-2] = i;
        }
        this.comboBox = new JComboBox<>(choices);

        this.getContentPane().setLayout(new FlowLayout());
        this.getContentPane().add(comboBox);
    }

    public JComboBox<Integer> getComboBox() {
        return comboBox;
    }
}
