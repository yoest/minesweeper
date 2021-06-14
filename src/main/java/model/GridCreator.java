package model;

public class GridCreator {

    public final int SIZE;
    public final Item[][] GRID;

    /** Create a grid which represents the board game.
     *
     * @param SIZE is the size of the grid (same width as height).
     */
    public GridCreator(int SIZE) {
        this.SIZE = SIZE;

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
        // Change this value to get more bombs.
        int probabilityOfBomb = 4;

        int random = Math.round((float) (Math.random() * (probabilityOfBomb - 1)));
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
}
