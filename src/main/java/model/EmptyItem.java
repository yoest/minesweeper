package model;

public class EmptyItem extends Item {

    @Override
    public int show() {
        return adjacentBombCount;
    }
}
