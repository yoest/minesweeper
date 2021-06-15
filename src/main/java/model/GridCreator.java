package model;

public class GridCreator {

    public final int SIZE;
    public final int BOMB_PROBABILITY;

    public final Item[][] GRID;

    private int bombsNumber = 0;

    /** Create a grid which represents the board game.
     *
     * @param SIZE is the size of the grid (same width as height).
     * @param BOMB_PROBABILITY is the probability of having bombs on the grid.
     */
    public GridCreator(int SIZE, int BOMB_PROBABILITY) {
        this.SIZE = SIZE;

        if(BOMB_PROBABILITY < 11)
            this.BOMB_PROBABILITY = BOMB_PROBABILITY;
        else
            throw new IllegalArgumentException("Bomb probability is to high : " + BOMB_PROBABILITY);

        GRID = new Item[SIZE][SIZE];
        generate();
        countAdjacentBombs();
    }

    /** Generate a grid with all the elements. */
    private void generate() {
        for(int i = 0; i < SIZE; i++) {
            for(int j = 0; j < SIZE; j++) {
                GRID[i][j] = chooseItem();
            }
        }
    }

    /** Choose between a bomb and an empty item.
     *
     * @return the item object (bomb or empty).
     */
    private Item chooseItem() {
        int random = Math.round((float) (Math.random() * (10 / BOMB_PROBABILITY)));
        if(random == 0) bombsNumber++;

        return random == 0 ? new Bomb() : new EmptyItem();
    }

    /** For each item, count the numbers of bombs next to it. */
    private void countAdjacentBombs() {
        for(int i = 0; i < SIZE; i++) {
            for(int j = 0; j < SIZE; j++) {

                if(GRID[i][j].show() == -1) {
                    // Check the eight box around the bomb.
                    for(int x = -1; x <= 1; x += 1) {
                        for(int y = -1; y <= 1; y += 1) {
                            try {
                                if(!(x == 0 && y == 0))
                                    GRID[i + x][j + y].incrementCountAdjacentBomb();
                            } catch (IndexOutOfBoundsException ignored) {}
                        }
                    }
                }
            }
        }
    }

    /** @return the numbers of bombs on the grid. */
    public int getBombsNumber() {
        return bombsNumber;
    }
}
