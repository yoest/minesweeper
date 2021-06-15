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

    /** Represent the view where the player can change the settings of the game. */
    public MenuView() {
        this.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        this.setPadding(new Insets(10));
        this.setMinWidth(200);

        // TODO : clean each slider (separated function).

        Label sizeGrid = new Label("Grid size :");
        Slider sliderSizeGrid = new Slider(5, 12, 1);
        sliderSizeGrid.setStyle("-fx-focus-color: black;");
        Label sliderSizeGridValueLabel = new Label(String.valueOf((int) sliderSizeGrid.getValue()));
        sliderSizeGrid.valueProperty().addListener((observable, oldValue, newValue) -> sliderSizeGridValueLabel.setText(String.valueOf(newValue.intValue())));
        HBox hBoxSizeGrid = new HBox(5, sliderSizeGrid, sliderSizeGridValueLabel);
        hBoxSizeGrid.setAlignment(Pos.CENTER);
        VBox sizeGridBox = new VBox(5, sizeGrid, hBoxSizeGrid);

        Label bombProbability = new Label("Bomb probability :");
        Slider sliderBombProbability = new Slider(1, 10, 1);
        sliderBombProbability.setStyle("-fx-focus-color: black;");
        Label sliderBombProbabilityValueLabel = new Label(String.valueOf((int) sliderBombProbability.getValue()));
        sliderBombProbability.valueProperty().addListener((observable, oldValue, newValue) -> sliderBombProbabilityValueLabel.setText(String.valueOf(newValue.intValue())));
        HBox hBoxBombProbability = new HBox(5, sliderBombProbability, sliderBombProbabilityValueLabel);
        hBoxBombProbability.setAlignment(Pos.CENTER);
        VBox bombProbabilityBox = new VBox(5, bombProbability, hBoxBombProbability);

        Label numberLives = new Label("Number of lives :");
        Slider sliderNumberLives = new Slider(1, 5, 1);
        sliderNumberLives.setStyle("-fx-focus-color: black;");
        Label sliderNumberLivesValueLabel = new Label(String.valueOf((int) sliderNumberLives.getValue()));
        sliderNumberLives.valueProperty().addListener((observable, oldValue, newValue) -> sliderNumberLivesValueLabel.setText(String.valueOf(newValue.intValue())));
        HBox hBoxNumberLives = new HBox(5, sliderNumberLives, sliderNumberLivesValueLabel);
        hBoxNumberLives.setAlignment(Pos.CENTER);
        VBox numberLivesBox = new VBox(5, numberLives, hBoxNumberLives);

        CustomButton scoreButton = new CustomButton("See score");
        scoreButton.setOnAction(event -> seeScore());

        CustomButton newGameButton = new CustomButton("New game");
        newGameButton.setOnAction(event -> generateNewGame((int) sliderSizeGrid.getValue(), (int) sliderBombProbability.getValue(), (int) sliderNumberLives.getValue()));

        HBox buttonBox = new HBox(10, scoreButton, newGameButton);
        buttonBox.setAlignment(Pos.CENTER);

        this.setSpacing(20);
        this.getChildren().addAll(sizeGridBox, bombProbabilityBox, numberLivesBox, buttonBox);
        this.setAlignment(Pos.TOP_CENTER);
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
