package org.code.app;

import org.code.components.Camel;
import org.code.components.Colours;
import org.code.components.Game;

import javax.swing.*;
import java.applet.Applet;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class App {

    private static final int PLAYERS = 4;
    private static final int CAMELS = 5;
    private final Game game = new Game(PLAYERS);

    private int setCamels = 0;

    private JPanel mainPanel;
    private JPanel camelPanel;
    private JPanel whitePanel;
    private JPanel yellowPanel;
    private JPanel orangePanel;
    private JPanel greenPanel;
    private JPanel bluePanel;
    private JPanel informationPanel;
    private JTextArea outputTextArea;
    private JPanel whiteColourPanel;
    private JPanel yellowColourPanel;
    private JPanel orangeColourPanel;
    private JPanel greenColourPanel;
    private JPanel blueColourPanel;
    private JPanel whiteSetPanel;
    private JPanel whiteMovePanel;
    private JPanel specialFieldPanel;
    private JTextField inputSpecialFieldTextField;
    private JButton acceptSpecialField;
    private JTextField inputWhiteSet;
    private JTextField inputWhiteMove;
    private JButton acceptWhiteSet;
    private JButton acceptWhiteMove;
    private JTextArea whitePositionOutput;
    private JTextArea whiteUpOutput;
    private JTextArea whiteDownOutput;
    private JTextArea whiteHasMovedOutput;
    private JTextArea whiteChancesOutput;
    private JPanel whiteHasMoved;
    private JPanel whiteDown;
    private JPanel whiteUp;
    private JPanel whitePosition;
    private JPanel whiteChances;
    private JPanel yellowPosition;
    private JPanel yellowUp;
    private JPanel yellowDown;
    private JPanel yellowHasMoved;
    private JPanel yellowChances;
    private JPanel yellowSet;
    private JPanel yellowMove;


    public App() {
    acceptWhiteSet.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            setCamels++;
            System.out.println("acceptWhiteSet");
            String input = inputWhiteSet.getText();
            int position = Integer.valueOf(input);
            game.setCamel(Colours.white, position);
            updateAll();
        }
    });
    acceptWhiteMove.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("acceptWhiteMove");
            String input = inputWhiteMove.getText();
            int move = Integer.valueOf(input);
            game.moveCamel(Colours.white, move);
            updateAll();
        }
    });
}

    private void updateAll(){
        ArrayList<Double> chances = new ArrayList<>(CAMELS);
        for (int i=0; i<CAMELS; i++)
            chances.add(0.0);
        if (setCamels >= CAMELS)
            chances = game.makePredictions();
        updateWhite(chances);
    }

    private void udateTextArea(JTextArea jTextArea, String text){
        jTextArea.setText(text);
    }

    private void updateWhite(ArrayList<Double> chances){
        Integer position = game.getPosition(Colours.white);
        udateTextArea(whitePositionOutput, objectToText(position));

        Colours up = game.getUp(Colours.white);
        udateTextArea(whiteUpOutput, objectToText(up));

        Colours down = game.getDown(Colours.white);
        udateTextArea(whiteDownOutput, objectToText(down));

        Boolean hasMoved = game.hasMoved(Colours.white);
        udateTextArea(whiteHasMovedOutput, objectToText(hasMoved));

        Double chance = chances.get(0);
        udateTextArea(whiteChancesOutput, objectToText(chance));
    }

    private String objectToText(Object o){
        if (o == null)
            return "";
        return o.toString();
    }

    public static void main(String[] args){
        JFrame jFrame = new JFrame("App");
        jFrame.setContentPane(new App().mainPanel);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setVisible(true);
    }

}
