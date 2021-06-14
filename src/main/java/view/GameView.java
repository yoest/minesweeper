package view;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.GridCreator;
import model.Item;

public class GameView extends VBox {

    private GridButton[][] GRID;
    private int lives = 3;

    private CustomLabel livesNumberLabel;

    /** Represent the view where the player can play.
     *
     * @param gridSize is the size of the grid.
     */
    public GameView(int gridSize) {
        GridCreator creator = new GridCreator(gridSize);
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
                button.setOnAction(event -> {
                    clickOnGrid(finalI, finalJ);
                    if(checkVictory()) resultDialog(true);
                });

                GRID[i][j] = button;
                gridPane.add(button, i, j);
            }
        }

        CustomLabel bombsNumberLabel = new CustomLabel("Bombs : " + creator.getBombsNumber());
        Pane space = new Pane();
        livesNumberLabel = new CustomLabel("Lives : " + lives);
        HBox hBox = new HBox(bombsNumberLabel, space, livesNumberLabel);
        HBox.setHgrow(space, Priority.ALWAYS);
        hBox.setPadding(new Insets(10));

        VBox root = new VBox(hBox, gridPane);
        root.setMaxWidth(GRID[0][0].SIZE_ITEM * GRID.length + 5 * (GRID.length - 1));

        this.getChildren().add(root);
        this.setAlignment(Pos.CENTER);
        HBox.setHgrow(this, Priority.ALWAYS);
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

        // If this is a bomb, remove a life.
        if(button.VALUE == -1) {
            lives--;
            livesNumberLabel.setText("Lives : " + lives);

            if(lives == 0) resultDialog(false);
        }

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

    /** Loop through the grid to check if the user win or not.
     *
     * @return true if all the box have been visited, false otherwise.
     */
    private boolean checkVictory() {
        for (GridButton[] row : GRID) {
            for (GridButton button : row) {
                if(button.VALUE != -1 && button.isHide())
                    return false;
            }
        }

        return true;
    }

    /** Open a dialog to display the result.
     *
     * @param isVictory is the game a victory or not ?
     */
    private void resultDialog(boolean isVictory) {
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);

        Label resultLabel = isVictory ? new Label("Well played !") : new Label("Sorry... Maybe next time.");
        CustomButton restartButton = new CustomButton("Close");
        restartButton.setOnAction(event -> stage.close());

        VBox vBox = new VBox(10, resultLabel, restartButton);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(30));
        vBox.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        vBox.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        stage.setScene(new Scene(vBox));
        stage.showAndWait();
    }
}
