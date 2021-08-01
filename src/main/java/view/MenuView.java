package view;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

public class MenuView extends VBox {

    private Slider[] sliders = new Slider[3];
    private int sliderIndex = 0;

    /** Represent the view where the player can change the settings of the game. */
    public MenuView() {
        this.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        this.setPadding(new Insets(10));
        this.setMinWidth(200);

        VBox sizeGridBox = this.createSlider("Grid size :", 5, 12);
        VBox bombProbabilityBox = this.createSlider("Bomb probability :", 1, 10);
        VBox numberLivesBox = this.createSlider("Number of lives :", 1, 5);

        CustomButton scoreButton = new CustomButton("See score");
        scoreButton.setOnAction(event -> seeScore());

        CustomButton newGameButton = new CustomButton("New game");
        newGameButton.setOnAction(event -> generateNewGame((int) sliders[0].getValue(), (int) sliders[1].getValue(), (int) sliders[2].getValue()));

        HBox buttonBox = new HBox(10, scoreButton, newGameButton);
        buttonBox.setAlignment(Pos.CENTER);

        this.setSpacing(20);
        this.getChildren().addAll(sizeGridBox, bombProbabilityBox, numberLivesBox, buttonBox);
        this.setAlignment(Pos.TOP_CENTER);
    }

    /** Create a slider box with many component and return the vbox.
     *
     * @param text is the text to put as the title of the slider.
     * @param sliderStart is the first value of the slider.
     * @param sliderEnd is the last value of the slider.
     * @return the vbox containing every component.
     */
    private VBox createSlider(String text, int sliderStart, int sliderEnd) {
        Label titleLabel = new Label(text);

        Slider slider = new Slider(sliderStart, sliderEnd, sliderStart);
        slider.setStyle("-fx-focus-color: black;");

        // Add this sliders to the lists of all sliders.
        this.sliders[sliderIndex] = slider;
        sliderIndex++;

        Label valueLabel = new Label(String.valueOf((int) slider.getValue()));
        slider.valueProperty().addListener((observable, oldValue, newValue) -> valueLabel.setText(String.valueOf(newValue.intValue())));

        HBox hBox = new HBox(5, slider, valueLabel);
        hBox.setAlignment(Pos.CENTER);

        return new VBox(5, titleLabel, hBox);
    }

    /** Generate a new game.
     *
     * @param sizeGrid is the size of the grid.
     * @param bombProbability is the probability of having bombs on the grid.
     * @param lives is the numbers of lives of the players.
     */
    private void generateNewGame(int sizeGrid, int bombProbability, int lives) {
        ObservableList<Node> nodes = ((HBox) this.getScene().getRoot()).getChildren();

        if(nodes.size() > 1) {
            GameView oldGameView = (GameView) nodes.remove(1);
            oldGameView.closeThread();
        }
        GameView gameView = new GameView(sizeGrid, bombProbability, lives);
        nodes.add(gameView);

        // Set the closing of the application so that it also closes the timer.
        this.getScene().getWindow().setOnCloseRequest(event -> gameView.closeThread());
    }

    /** See every previous score. */
    private void seeScore() {
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);

        ScoreView scoreView = new ScoreView();

        CustomButton deleteAllButton = new CustomButton("Delete all");
        deleteAllButton.setOnAction(event -> {
            scoreView.deleteAllScore();
            seeScore();
            stage.close();
        });
        CustomButton keepBestButton = new CustomButton("Keep best scores");
        keepBestButton.setOnAction(event -> {
            scoreView.keepBestOne();
            seeScore();
            stage.close();
        });
        CustomButton closeButton = new CustomButton("Close");
        closeButton.setOnAction(event -> stage.close());
        HBox buttonsBox = new HBox(10, deleteAllButton, keepBestButton, closeButton);
        buttonsBox.setAlignment(Pos.CENTER);

        VBox vBox = new VBox(20, scoreView, buttonsBox);
        vBox.setPrefSize(400, 300);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(30));
        vBox.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        vBox.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        // Center the stage on the other stage.
        Window currentStage = this.getScene().getWindow();
        stage.setX(currentStage.getX() + (currentStage.getWidth() / 2) - 200);
        stage.setY(currentStage.getY() + (currentStage.getHeight() / 2) - 150);

        stage.setScene(new Scene(vBox));
        stage.showAndWait();
    }
}
