package org.code.app;

import org.code.components.Colours;
import org.code.components.Game;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class App {

    private final int PLAYERS;
    private final int CAMELS = 5;
    private final Game game;

    private int setCamels = 0;
    private boolean allCamelsSet = false;
    private int movedCamels = 0;

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
    private JTextField inputSpecialFieldPosition;
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
    private JTextArea yellowPositionOutput;
    private JTextArea yellowUpOutput;
    private JTextArea yellowDownOutput;
    private JTextArea yellowHasMovedOutput;
    private JTextArea yellowChancesOutput;
    private JButton acceptYellowSet;
    private JButton acceptYellowMove;
    private JTextField inputYellowSet;
    private JTextField inputYellowMove;
    private JPanel orangePosition;
    private JPanel orangeUp;
    private JPanel orangeDown;
    private JPanel orangeHasMoved;
    private JPanel orangeChances;
    private JPanel orangeSet;
    private JPanel orangeMove;
    private JTextArea orangePositionOutput;
    private JTextArea orangeUpOutput;
    private JTextArea orangeDownOutput;
    private JTextArea orangeHasMovedOutput;
    private JTextArea orangeChancesOutput;
    private JTextField inputOrangeSet;
    private JTextField inputOrangeMove;
    private JButton acceptOrangeSet;
    private JButton acceptOrangeMove;
    private JPanel greenPosition;
    private JPanel greenUp;
    private JPanel greenDown;
    private JPanel greenHasMoved;
    private JPanel greenChances;
    private JPanel greenSet;
    private JPanel greenMove;
    private JTextArea greenPositionOutput;
    private JTextArea greenUpOutput;
    private JTextArea greenDownOutput;
    private JTextArea greenHasMovedOutput;
    private JTextArea greenChancesOutput;
    private JTextField inputGreenSet;
    private JTextField inputGreenMove;
    private JButton acceptGreenSet;
    private JButton acceptGreenMove;
    private JPanel bluePosition;
    private JPanel blueUp;
    private JPanel blueDown;
    private JPanel blueHasMoved;
    private JPanel blueChances;
    private JPanel blueSet;
    private JPanel blueMove;
    private JTextArea bluePositionOutput;
    private JTextArea blueUpOutput;
    private JTextArea blueDownOutput;
    private JTextArea blueHasMovedOutput;
    private JTextArea blueChancesOutput;
    private JTextField inputBlueSet;
    private JTextField inputBlueMove;
    private JButton acceptBlueSet;
    private JButton acceptBlueMove;
    private JComboBox specialFieldMoveDecision;


    public App(int players) {
        PLAYERS = players;
        game = new Game(PLAYERS);

        //initializing specialFieldMoveDecision
        //specialFieldMoveDecision = new JComboBox();
        specialFieldMoveDecision.addItem("-1");
        specialFieldMoveDecision.addItem("1");

        acceptWhiteSet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setCamel(inputWhiteSet, Colours.white);
            }
        });
        acceptWhiteMove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveCamel(inputWhiteMove, Colours.white);
            }
        });
        acceptYellowSet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setCamel(inputYellowSet, Colours.yellow);
            }
        });
        acceptYellowMove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveCamel(inputYellowMove, Colours.yellow);
            }
        });
        acceptOrangeSet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setCamel(inputOrangeSet, Colours.orange);
            }
        });
        acceptOrangeMove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveCamel(inputOrangeMove, Colours.orange);
            }
        });
        acceptGreenSet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setCamel(inputGreenSet, Colours.green);
            }
        });
        acceptGreenMove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveCamel(inputGreenMove, Colours.green);
            }
        });
        acceptBlueSet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setCamel(inputBlueSet, Colours.blue);
            }
        });
        acceptBlueMove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveCamel(inputBlueMove, Colours.blue);
            }
        });
        acceptSpecialField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = inputSpecialFieldPosition.getText();
                int position = Integer.valueOf(input);
                String input2 = specialFieldMoveDecision.getSelectedItem().toString();
                int move = Integer.valueOf(input2);
                boolean success = game.setSpecialField(position, move);
                updateAll();
                String message = "Setting the special field failed\n";
                if (success){
                    message = "The special field was set on position: " + input + " with move: " + input2 + "\n";
                }
                outputTextArea.append(message);
            }
        });
    }

    private void moveCamel(JTextField inputMove, Colours colour){
        String input = inputMove.getText();
        int move = Integer.valueOf(input);
        game.moveCamel(colour, move);
        if (game.isGameFinished()){
            finishGame();
        }
        updateAll();
        releaseAllSpecialFields();
    }

    private void setCamel(JTextField inputSet, Colours colour){
        String input = inputSet.getText();
        int position = Integer.valueOf(input);
        if (position > Game.getBoardSize()){
            updateTextArea(outputTextArea, "Position out of board");
            return;
        }
        if (setCamels + 1 >= CAMELS)
            allCamelsSet = true;
        else
            setCamels++;
        game.setCamel(colour, position);
        updateAll();
    }

    private void releaseAllSpecialFields(){
        movedCamels = (movedCamels + 1) % CAMELS;
        if (movedCamels == 0){
            game.releaseAllSpecialFields();
            outputTextArea.setText("");
        }
    }

    private void finishGame(){
        updateTextArea(outputTextArea, "The game is finished");
        game.releaseAllSpecialFields();
    }

    private void updateAll(){
        ArrayList<Double> chances = new ArrayList<>(CAMELS);
        for (int i=0; i<CAMELS; i++)
            chances.add(0.0);
        if (allCamelsSet)
            chances = game.makePredictions();
        updateWhite(chances);
        updateYellow(chances);
        updateOrange(chances);
        updateGreen(chances);
        updateBlue(chances);
    }

    private void updateTextArea(JTextArea jTextArea, String text){
        jTextArea.setText(text);
    }

    private void updateBlue(ArrayList<Double> chances){
        Colours colour = Colours.blue;

        Integer position = game.getPosition(colour);
        updateTextArea(bluePositionOutput, objectToText(position));

        Colours up = game.getUp(colour);
        updateTextArea(blueUpOutput, objectToText(up));

        Colours down = game.getDown(colour);
        updateTextArea(blueDownOutput, objectToText(down));

        Boolean hasMoved = game.hasMoved(colour);
        updateTextArea(blueHasMovedOutput, objectToText(hasMoved));

        Double chance = chances.get(game.colourToNumber(colour));
        updateTextArea(blueChancesOutput, objectToText(chance));
    }
    private void updateGreen(ArrayList<Double> chances){
        Colours colour = Colours.green;

        Integer position = game.getPosition(colour);
        updateTextArea(greenPositionOutput, objectToText(position));

        Colours up = game.getUp(colour);
        updateTextArea(greenUpOutput, objectToText(up));

        Colours down = game.getDown(colour);
        updateTextArea(greenDownOutput, objectToText(down));

        Boolean hasMoved = game.hasMoved(colour);
        updateTextArea(greenHasMovedOutput, objectToText(hasMoved));

        Double chance = chances.get(game.colourToNumber(colour));
        updateTextArea(greenChancesOutput, objectToText(chance));
    }
    private void updateOrange(ArrayList<Double> chances){
        Colours colour = Colours.orange;

        Integer position = game.getPosition(colour);
        updateTextArea(orangePositionOutput, objectToText(position));

        Colours up = game.getUp(colour);
        updateTextArea(orangeUpOutput, objectToText(up));

        Colours down = game.getDown(colour);
        updateTextArea(orangeDownOutput, objectToText(down));

        Boolean hasMoved = game.hasMoved(colour);
        updateTextArea(orangeHasMovedOutput, objectToText(hasMoved));

        Double chance = chances.get(game.colourToNumber(colour));
        updateTextArea(orangeChancesOutput, objectToText(chance));
    }
    private void updateYellow(ArrayList<Double> chances){
        Colours colour = Colours.yellow;

        Integer position = game.getPosition(colour);
        updateTextArea(yellowPositionOutput, objectToText(position));

        Colours up = game.getUp(colour);
        updateTextArea(yellowUpOutput, objectToText(up));

        Colours down = game.getDown(colour);
        updateTextArea(yellowDownOutput, objectToText(down));

        Boolean hasMoved = game.hasMoved(colour);
        updateTextArea(yellowHasMovedOutput, objectToText(hasMoved));

        Double chance = chances.get(game.colourToNumber(colour));
        updateTextArea(yellowChancesOutput, objectToText(chance));
    }

    private void updateWhite(ArrayList<Double> chances){
        Colours colour = Colours.white;

        Integer position = game.getPosition(colour);
        updateTextArea(whitePositionOutput, objectToText(position));

        Colours up = game.getUp(colour);
        updateTextArea(whiteUpOutput, objectToText(up));

        Colours down = game.getDown(colour);
        updateTextArea(whiteDownOutput, objectToText(down));

        Boolean hasMoved = game.hasMoved(colour);
        updateTextArea(whiteHasMovedOutput, objectToText(hasMoved));

        Double chance = chances.get(game.colourToNumber(colour));
        updateTextArea(whiteChancesOutput, objectToText(chance));
    }

    private String objectToText(Object o){
        if (o == null)
            return "";
        return o.toString();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    /*public static void main(String[] args){
        JFrame jFrame = new JFrame("App");
        jFrame.setContentPane(new App().mainPanel);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setVisible(true);
    }*/

}
