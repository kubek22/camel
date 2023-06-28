package org.code.components;

import java.util.HashMap;

public class Game {

    private static final int BOARD_SIZE = 25;
    private static final int CAMELS_NUMBER = 5;

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

    public void setAllCamels(int[] positions){
        if(positions.length != CAMELS_NUMBER){
            System.out.println("Inappropriate length of positions");
            return;
        }
        for(int i=0; i<CAMELS_NUMBER; i++){
            camels[i].setField(positions[i]);
        }
    }

    public void setCamel(Colours colour, int position){
        setCamel(coloursToNumbers.get(colour), position);
    }

    public void setCamel(int colour, int position){

    }

    private boolean isFieldFree(int position){
        return false;
    }

    public void setSpecialField(int position){
        if(firstUnusedSpecialField >= specialFields.length){
            System.out.println("All special fields have been already used");
            return;
        }


    }


}
