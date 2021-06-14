package model;

public abstract class Item {

    protected int adjacentBombCount = 0;

    /** Show the type of item (number if there are bombs next to it, ...).
     *
     * @return the number if there are bombs next to it, 0 otherwise and -1 if this is a bomb.
     */
    public abstract int show();

    /** Increment the value of adjacent bomb. */
    public void incrementCountAdjacentBomb() {
        this.adjacentBombCount++;
    }
}
