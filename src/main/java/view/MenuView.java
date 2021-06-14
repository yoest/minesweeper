package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class MenuView extends VBox {

    /** Represent the view where the player can change the settings of the game. */
    public MenuView() {
        this.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        this.setPadding(new Insets(10));

        Slider slider = new Slider(4, 12, 1);
        Label sliderValueLabel = new Label(String.valueOf((int) slider.getValue()));

        slider.valueProperty().addListener((observable, oldValue, newValue) -> sliderValueLabel.setText(String.valueOf(newValue.intValue())));
        HBox hBox = new HBox(5, slider, sliderValueLabel);

        CustomButton newGameButton = new CustomButton("New game");
        newGameButton.setOnAction(event -> generateNewGame((int) slider.getValue()));

        this.setSpacing(10);
        this.setAlignment(Pos.TOP_CENTER);
        this.getChildren().addAll(hBox, newGameButton);
    }

    /** Generate a new game.
     *
     * @param sizeGrid is the size of the grid.
     */
    private void generateNewGame(int sizeGrid) {
        ((HBox) this.getScene().getRoot()).getChildren().remove(1);
        ((HBox) this.getScene().getRoot()).getChildren().add(new GameView(sizeGrid));
    }
}
