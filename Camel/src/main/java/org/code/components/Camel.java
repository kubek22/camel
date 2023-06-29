package org.code.components;

public class Camel extends GameObject {

    private final Colours colour;
    private Camel up;
    private Camel down;
    private boolean hasMoved;

    public Camel(Colours colour) {
        super();
        this.colour = colour;
        this.up = null;
        this.down = null;
        this.hasMoved = false;
    }

    public Colours getColour() {
        return colour;
    }

    public Camel getUp() {
        return up;
    }

    public void setUp(Camel up) {
        this.up = up;
    }

    public Camel getDown() {
        return down;
    }

    public void setDown(Camel down) {
        this.down = down;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    /*public Camel copy(){
        Camel camel = new Camel(this.getColour());
        camel.setField(this.getField());
        camel.setDown(this.getDown());
        camel.setUp(this.getUp());
        return camel;
    }*/
}
