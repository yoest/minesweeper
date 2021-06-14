package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import model.GridCreator;
import model.Item;

public class GameView extends BorderPane {

    private GridButton[][] GRID;

    public GameView() {
        GridCreator creator = new GridCreator(10);
        GRID = new GridButton[creator.SIZE][creator.SIZE];

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));

        gridPane.setVgap(5);
        gridPane.setHgap(5);

        gridPane.setAlignment(Pos.CENTER);

        for (int i = 0; i < creator.SIZE; i++) {
            for (int j = 0; j < creator.SIZE; j++) {
                Item item = creator.GRID[i][j];

                int finalI = i, finalJ = j;
                GridButton button = new GridButton(item.show());
                button.setOnAction(event -> clickOnGrid(finalI, finalJ));

                GRID[i][j] = button;
                gridPane.add(button, i, j);
            }
        }

        this.setCenter(gridPane);
    }

    /** Click on a box on the grid.
     *
     * @param x is the x position of the button which was clicked.
     * @param y is the y position of the button which was clicked.
     */
    private void clickOnGrid(int x, int y) {
        GridButton button = GRID[x][y];

        if(!button.isHide())
            return;
        button.show();

        if(button.VALUE == -1) {
            // TODO
            System.out.println("END");
        } else if(button.VALUE == 0) {
            // Check the four box around this box.
            if(x > 0)
                clickOnGrid(x - 1, y);
            if(y > 0)
                clickOnGrid(x, y - 1);
            if(x < GRID.length - 1)
                clickOnGrid(x + 1, y);
            if(y < GRID.length - 1)
                clickOnGrid(x, y + 1);
        }
    }
}
