package org.code.start;

import org.code.app.AdditionalFrame;
import org.code.app.App;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Starter {
    private JFrame mainFrame;
    private final JFrame additionalFrame;

    public Starter() {
        additionalFrame = prepareAdditionalFrame();
    }

    private JFrame prepareAdditionalFrame(){
        AdditionalFrame additionalFrame = new AdditionalFrame();

        // Create a button to confirm the choice
        JButton confirmButton = new JButton("Select number of players");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    int players = (int) additionalFrame.getComboBox().getSelectedItem();
                    System.out.println("Selected choice: " + players);
                    additionalFrame.dispose(); // Close the additional window
                    mainFrame = prepareMainFrame(players);
                    mainFrame.setLocationRelativeTo(null); // Center main frame
                    mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                    mainFrame.setVisible(true); // Make the main frame visible
                }
                catch (Exception exception){
                    if (exception instanceof NullPointerException)
                        return;
                }
            }
        });

        additionalFrame.getContentPane().add(confirmButton);

        return additionalFrame;
    }

    private JFrame prepareMainFrame(int players){
        mainFrame = new JFrame("App");
        mainFrame.setContentPane(new App(players).getMainPanel());
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.pack();

        return mainFrame;
    }

    public void display() {
        additionalFrame.setLocationRelativeTo(null); // Center main frame
        additionalFrame.setVisible(true);
    }
}

