package org.code.components;

public abstract class GameObject {

    private int field;

    protected static final int DEF = 0;

    public GameObject() {
        field = DEF;
    }

    public int getField() {
        return field;
    }

    public void setField(int field) {
        this.field = field;
    }
}
