package org.code.components;

import java.util.HashMap;

public class Game {

    private static final int BOARD_SIZE = 25+1;
    private static final int CAMELS_NUMBER = 5;

    private boolean isGameFinished;

    private GameObject[] board;

    private int players;

    private final Camel white;
    private final Camel yellow;
    private final Camel orange;
    private final Camel green;
    private final Camel blue;

    private final Camel[] camels;

    private final SpecialField[] specialFields;
    private int firstUnusedSpecialField;
    private final HashMap<Colours, Integer> coloursToNumbers;

    public Game(int players) {
        if(players <= 0){
            System.out.println("Incorrect number of players");
        }
        isGameFinished = false;
        coloursToNumbers = new HashMap<>();
        createColoursToNumbers();
        this.players = players;
        firstUnusedSpecialField = 0;
        board = new GameObject[BOARD_SIZE];
        camels = new Camel[CAMELS_NUMBER];
        white = new Camel(Colours.white);
        camels[coloursToNumbers.get(Colours.white)] = white;
        yellow = new Camel(Colours.yellow);
        camels[coloursToNumbers.get(Colours.yellow)] = yellow;
        orange = new Camel(Colours.orange);
        camels[coloursToNumbers.get(Colours.orange)] = orange;
        green = new Camel(Colours.green);
        camels[coloursToNumbers.get(Colours.green)] = green;
        blue = new Camel(Colours.blue);
        camels[coloursToNumbers.get(Colours.blue)] = blue;
        specialFields = new SpecialField[players];
        for(int i = 0; i < players; i++){
            specialFields[i] = new SpecialField();
        }
    }

    private void createColoursToNumbers(){
        coloursToNumbers.put(Colours.white, 0);
        coloursToNumbers.put(Colours.yellow, 1);
        coloursToNumbers.put(Colours.orange, 2);
        coloursToNumbers.put(Colours.green, 3);
        coloursToNumbers.put(Colours.blue, 4);
    }

    public boolean isGameFinished() {
        return isGameFinished;
    }

    public void setGameFinished(boolean gameFinished) {
        isGameFinished = gameFinished;
    }

    public void setAllCamels(int[] positions){
        if(positions.length != CAMELS_NUMBER){
            System.out.println("Inappropriate length of positions");
            return;
        }
        // the order is always the same
        for(int i=0; i<CAMELS_NUMBER; i++){
            setCamel(i, positions[i]);
        }
    }

    public void moveCamel(Colours colour, int steps){
        if (steps < 0 | steps > 3){
            System.out.println("Wrong number of steps given");
            return;
        }
        Camel camel = camels[coloursToNumbers.get(colour)];
        setCamel(coloursToNumbers.get(colour), camel.getField() + steps);
    }

    public void setCamel(Colours colour, int position){
        setCamel(coloursToNumbers.get(colour), position);
    }

    public void setCamel(int colour, int position){
        Camel camel = camels[colour];
        int previousPosition = camel.getField();

        if (isGameFinished) {
            return;
        }
        if (position > BOARD_SIZE){
            setGameFinished(true);
            camel.setDown(null);
            changeCamelsPosition(camel, position);
        }
        else if (isFieldFree(position)){
            board[position] = camel;
            camel.setDown(null);
            changeCamelsPosition(camel, position);
        }
        else if (!isFieldFree(position) & !isAnyCamelOnTheField(position)){
            //special field case
            SpecialField specialField = (SpecialField) board[position];
            int move = specialField.getMove();
            if (move == 1){
                setCamel(colour, position + 1);
                return;
            }
            else if (move == -1){
                // roll back
                position--;
                // we add from the bottom
                Camel camelUp = getBottomCamel(position);
                if (camelUp == null){
                    setCamel(colour, position);
                    return;
                }
                else{
                    //add to the bottom
                    //it may copy it incorrectly
                    changeCamelsPosition(camel, position);
                    Camel camelTop = camel;
                    while (camelTop != null){
                        camelTop = camelTop.getUp();
                    }
                    camelTop.setUp(camelUp);
                    camelUp.setDown(camelTop);
                    camel.setDown(null);
                }
            }
            else{
                System.out.println("Problem with special field");
            }

        }
        else if (isAnyCamelOnTheField(position)){
            //camel on field
            Camel downCamel = getTopCamel(position);
            downCamel.setUp(camel);
            camel.setDown(downCamel);
            changeCamelsPosition(camel, position);
        }
        else{
            System.out.println("Something went wrong, shouldn't get here");
        }

        //checking the previous position
        Camel lastCamel = getTopCamel(previousPosition);
        if (lastCamel == null){
            //release field
            board[previousPosition] = null;
        }
        else {
            lastCamel.setUp(null);
        }
    }

    private void changeCamelsPosition(Camel camel, int position){
        while (camel != null){
            camel.setField(position);
            camel = camel.getUp();
        }
    }

    private Camel getBottomCamel(int position){
        Camel camel = null;
        for (Camel c: camels){
            if (c.getField() == position){
                camel = c;
                break;
            }
        }
        if (camel == null){
            return camel;
        }
        while (camel.getDown() != null){
            camel = camel.getDown();
        }
        return camel;
    }

    private Camel getTopCamel(int position){
        Camel camel = getBottomCamel(position);
        if (camel == null){
            return camel;
        }
        while (camel.getUp() != null){
            camel = camel.getUp();
        }
        return camel;
    }

    private boolean isFieldFree(int position){
        if (board[position] == null)
            return true;
        return false;
    }

    private boolean isAnyCamelOnTheField(int position){
        for (Camel camel: camels){
            if (camel.getField() == position)
                return true;
        }
        return false;
    }

    public void setSpecialField(int position, int move){
        if (position < 1 | position > BOARD_SIZE){
            System.out.println("Position out of index");
            return;
        }
        if (firstUnusedSpecialField >= specialFields.length){
            System.out.println("All special fields have been already used");
            return;
        }
        if (!isFieldFree(position)){
            System.out.println("This field is taken");
            return;
        }
        if (move != 1 & move != -1){
            System.out.println("Incorrect special field move modifier");
            return;
        }

        if (position + 1 <= BOARD_SIZE){
            if (!isFieldFree(position + 1) & !isAnyCamelOnTheField(position + 1)){
                System.out.println("There is already a special field set on the next field");
                return;
            }
        }
        if (position - 1 > 0){
            if (!isFieldFree(position - 1) & !isAnyCamelOnTheField(position - 1)){
                System.out.println("There is already a special field set on the previous field");
                return;
            }
        }

        SpecialField specialField = specialFields[firstUnusedSpecialField];

        if (specialField.isSet()){
            System.out.println("This special field has been already set");
            return;
        }

        firstUnusedSpecialField++;
        board[position] = specialField;
        specialField.setField(position);
        specialField.setMove(move);
    }

    public void releaseAllSpecialFields(){
        for (SpecialField specialField: specialFields){
            int position = specialField.getField();
            board[position] = null;
            specialField.setField(SpecialField.DEF);
            specialField.setSet(false);
        }
    }

}
