package org.code.components;

public class SpecialField extends GameObject{

    private static int Id = 0;

    private int move;
    // possible values are -1 and 1

    private final int playerId;

    private boolean isSet;

    public SpecialField() {
        super();
        this.playerId = Id++;
        this.move = DEF;
        this.isSet = false;
    }

    public int getMove() {
        return move;
    }

    public void setMove(int move) {
        this.move = move;
    }
    public int getPlayerId() {
        return playerId;
    }

    public boolean isSet() {
        return isSet;
    }

    public void setSet(boolean set) {
        isSet = set;
    }
}
