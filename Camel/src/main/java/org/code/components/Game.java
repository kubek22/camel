package org.code.components;

import java.util.ArrayList;
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
        Camel lastCamel = camel.getDown();
        int previousPosition = camel.getField();

        if (isGameFinished) {
            return;
        }
        if (camel.hasMoved()){
            System.out.println("This camel has already moved");
            return;
        }
        if (previousPosition == position){
            //not necessary
            //camel.setHasMoved(true);
            System.out.println("Camel stayed on the same field");
            return;
        }
        if (position > BOARD_SIZE){
            setGameFinished(true);
            camel.setDown(null);
            changeCamelsPosition(camel, position);
        }
        else if (isFieldFree(position)){
            //free field
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
                Camel camelUp = getBottomCamel(position);
                if (position == previousPosition){
                    //no move case
                    if (lastCamel == null){
                        //we do nothing
                        saveMove(camel);
                        return;
                    }
                    //we stay on the same field, but the order changes
                    Camel topCamel = getTopCamel(position);
                    Camel bottomCamel = getBottomCamel(position);
                    lastCamel.setUp(null);
                    camel.setDown(null);
                    topCamel.setUp(bottomCamel);
                    bottomCamel.setDown(topCamel);
                    board[position] = camel;
                    saveMove(camel);
                    return;
                }
                // we add from the bottom
                else if (camelUp == null){
                    setCamel(colour, position);
                    return;
                }
                else{
                    //add to the bottom
                    changeCamelsPosition(camel, position);
                    Camel camelTop = camel;
                    while (camelTop.getUp() != null){
                        camelTop = camelTop.getUp();
                    }
                    camelTop.setUp(camelUp);
                    camelUp.setDown(camelTop);
                    camel.setDown(null);
                    board[position] = camel;
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
        if (lastCamel == null){
            //release field
            //winning case
            if (previousPosition <= BOARD_SIZE)
                board[previousPosition] = null;
        }
        else {
            lastCamel.setUp(null);
        }

        //saving move
        saveMove(camel);
    }

    private void saveMove(Camel camel){
        camel.setHasMoved(true);
        for (Camel c: camels){
            if (!c.hasMoved()){
                return;
            }
        }
        for (Camel c: camels){
            c.setHasMoved(false);
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
        //possible moves are 1and -1
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
        specialField.setSet(true);
    }

    public void releaseAllSpecialFields(){
        for (SpecialField specialField: specialFields){
            int position = specialField.getField();
            board[position] = null;
            specialField.setField(SpecialField.DEF);
            specialField.setSet(false);
        }
    }

    public void releaseAllCamels(){
        for (Camel camel: camels){
            int field = camel.getField();
            if (field <= BOARD_SIZE)
                board[field] = null;
            camel.setUp(null);
            camel.setDown(null);
            camel.setField(Camel.DEF);
        }
    }

    private class SavedCamels{
        private ArrayList<Colours> recoveryColours;
        private ArrayList<Integer> recoveryPositions;
        private ArrayList<Boolean> recoveryActivity;

        public SavedCamels(ArrayList<Colours> recoveryColours, ArrayList<Integer> recoveryPositions, ArrayList<Boolean> recoveryActivity) {
            this.recoveryColours = recoveryColours;
            this.recoveryPositions = recoveryPositions;
            this.recoveryActivity = recoveryActivity;
        }

        public ArrayList<Colours> getRecoveryColours() {
            return recoveryColours;
        }

        public ArrayList<Integer> getRecoveryPositions() {
            return recoveryPositions;
        }

        public ArrayList<Boolean> getRecoveryActivity() {
            return recoveryActivity;
        }
    }

    private SavedCamels savePositions(){
        ArrayList<Integer> positions = new ArrayList<>();
        for (Camel camel: camels){
            int position = camel.getField();
            if (!positions.contains(position)){
                positions.add(position);
            }
        }
        ArrayList<Colours> recoveryColours = new ArrayList<>();
        ArrayList<Integer> recoveryPositions = new ArrayList<>();
        ArrayList<Boolean> recoveryActivity = new ArrayList<>();
        for (int position: positions){
            Camel camel = getBottomCamel(position);
            recoveryColours.add(camel.getColour());
            recoveryPositions.add(position);
            recoveryActivity.add(camel.hasMoved());
            while (camel.getUp() != null){
                camel = camel.getUp();
                recoveryColours.add(camel.getColour());
                recoveryPositions.add(position);
                recoveryActivity.add(camel.hasMoved());
            }
        }

        return new SavedCamels(recoveryColours, recoveryPositions, recoveryActivity);
    }

    public void makePredictions(){
        //save previous positions
        SavedCamels savedCamels = savePositions();
        ArrayList<Colours> recoveryColours = savedCamels.getRecoveryColours();
        ArrayList<Integer> recoveryPositions = savedCamels.getRecoveryPositions();
        ArrayList<Boolean> recoveryActivity = savedCamels.getRecoveryActivity();

        //make simulations
        //we want to take only active camels
        ArrayList<Colours> choices = new ArrayList<>();
        for (Camel camel: camels){
            if (!camel.hasMoved()){
                choices.add(camel.getColour());
            }
        }

        int[] winningResults = new int[CAMELS_NUMBER];

        class Predictor{
            private int iterations = 0;

            public int getIterations() {
                return iterations;
            }

            private void predict(ArrayList<Colours> choices, ArrayList<Colours> permutations, ArrayList<Integer> moves){
                if (choices.size() == 0){
                    for (int i=0; i<permutations.size(); i++){
                        moveCamel(permutations.get(i), moves.get(i));
                    }
                    iterations++;
                    //check who won
                    int winner = getWinner();
                    //save results
                    winningResults[winner]++;

                    //roll back all the changes
                    //at first, change all positions to default ones
                    for (Camel c: camels){
                        int position = c.getField();
                        board[position] = null;
                        c.setDown(null);
                        c.setUp(null);
                        c.setField(Camel.DEF);
                    }
                    for (int i=0; i<CAMELS_NUMBER; i++){
                        setCamel(recoveryColours.get(i), recoveryPositions.get(i));
                    }
                    for (int i=0; i<CAMELS_NUMBER; i++){
                        Camel c = camels[coloursToNumbers.get(recoveryColours.get(i))];
                        c.setHasMoved(recoveryActivity.get(i));
                    }
                    isGameFinished = false;
                    return;
                }

                //copy arguments
                //choose one element
                for (int i=0; i<choices.size(); i++){
                    ArrayList<Colours> permutationsCopy = (ArrayList<Colours>) permutations.clone();
                    permutationsCopy.add(choices.get(i));
                    ArrayList<Colours> nextChoices = (ArrayList<Colours>) choices.clone();
                    nextChoices.remove(i);
                    for (int move=1; move<4; move++){
                        ArrayList<Integer> movesCopy = (ArrayList<Integer>) moves.clone();
                        movesCopy.add(move);
                        predict(nextChoices, permutationsCopy, movesCopy);
                    }
                }
            }
        }
        Predictor predictor = new Predictor();
        predictor.predict(choices, new ArrayList<Colours>(), new ArrayList<Integer>());
        Integer iterations = predictor.getIterations();

        //counting percentage results
        ArrayList<Double> finalResults = new ArrayList<>(CAMELS_NUMBER);
        for (Integer el: winningResults){
            finalResults.add(el.doubleValue() / iterations.doubleValue());
        }
        System.out.println(finalResults);
        //TODO
        //return finalResults;
    }



    private int getWinner(){
        int maxField = 0;
        Camel winner = null;
        for (Camel camel: camels){
            if (camel.getField() > maxField){
                maxField = camel.getField();
                winner = camel;
            }
        }
        while (winner.getUp() != null){
            winner = winner.getUp();
        }
        return coloursToNumbers.get(winner.getColour());
    }

}
