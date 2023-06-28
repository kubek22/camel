package org.code.components;

public class Camel extends GameObject {

    private final Colours colour;
    private Camel up;
    private Camel down;

    public Camel(Colours colour) {
        super();
        this.colour = colour;
        this.up = null;
        this.down = null;
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
}
