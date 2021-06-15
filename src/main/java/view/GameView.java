package view;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import model.CustomTimerTask;
import model.GridCreator;
import model.Item;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Timer;

public class GameView extends VBox {

    private GridButton[][] GRID;
    private int lives;

    private CustomLabel livesNumberLabel;

    private Timer timer;
    private CustomTimerTask task;

    /** Represent the view where the player can play.
     *
     * @param gridSize is the size of the grid.
     * @param bombProbability is the probability of having bombs on the grid.
     * @param lives is the numbers of lives of the players.
     */
    public GameView(int gridSize, int bombProbability, int lives) {
        GridCreator creator = new GridCreator(gridSize, bombProbability);
        GRID = new GridButton[creator.SIZE][creator.SIZE];
        this.lives = lives;

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
        bombsNumberLabel.setMinWidth(GRID[0][0].SIZE_ITEM * 2 + 5);
        bombsNumberLabel.setAlignment(Pos.CENTER);
        Pane space = new Pane();
        livesNumberLabel = new CustomLabel("Lives : " + lives);
        livesNumberLabel.setMinWidth(GRID[0][0].SIZE_ITEM * 2 + 5);
        livesNumberLabel.setAlignment(Pos.CENTER);
        HBox hBox = new HBox(bombsNumberLabel, space, livesNumberLabel);
        HBox.setHgrow(space, Priority.ALWAYS);
        hBox.setPadding(new Insets(10));

        CustomLabel timerLabel = new CustomLabel("00:00:00");
        timerLabel.setMinWidth(GRID[0][0].SIZE_ITEM * GRID.length + 5 * (GRID.length - 1));
        timerLabel.setAlignment(Pos.CENTER);
        HBox timerBox = new HBox(timerLabel);
        timerBox.setPadding(new Insets(10));

        timer = new Timer();
        task = new CustomTimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    timeCounter++;
                    timerLabel.setText(formatTime(timeCounter));
                });
            }
        };
        timer.scheduleAtFixedRate(task, 0, 1000);

        VBox root = new VBox(hBox, gridPane, timerBox);
        root.setMaxWidth(GRID[0][0].SIZE_ITEM * GRID.length + 5 * (GRID.length - 1));

        this.getChildren().add(root);
        this.setAlignment(Pos.CENTER);
        HBox.setHgrow(this, Priority.ALWAYS);
    }

    /** Format the timer to get a pretty printing like hh:mm:ss.
     *
     * @param value is the value of the counter.
     * @return
     */
    private String formatTime(int value) {
        int seconds = value % 60;
        int minutes = (value - seconds) / 60;
        int hours = (value - 60 * minutes - seconds) / 3600;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
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

        if(button.VALUE == 0) {
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
        timer.cancel();

        Stage stage = new Stage();
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);

        Label resultLabel = isVictory ? new Label("Well played !") : new Label("Sorry... Maybe next time.");
        resultLabel.setFont(Font.font(20));

        int score = countScore();
        String time = formatTime(task.getTimeCounter());

        Label scoreLabel = new Label("Score : " + score);
        Label timeLabel = new Label("Time : " + time);
        VBox resultBox = new VBox(5, scoreLabel, timeLabel);

        CustomButton restartButton = new CustomButton("Close");

        HBox root = (HBox) this.getScene().getRoot();
        restartButton.setOnAction(event -> {
            saveScore(score, time);

            closeThread();
            stage.close();
            root.getChildren().remove(1);
        });

        VBox vBox = new VBox(20, resultLabel, resultBox, restartButton);
        vBox.setPrefSize(400, 200);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(30));
        vBox.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        vBox.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        // Center the stage on the other stage.
        Window currentStage = this.getScene().getWindow();
        stage.setX(currentStage.getX() + (currentStage.getWidth() / 2) - 200);
        stage.setY(currentStage.getY() + (currentStage.getHeight() / 2) - 100);

        stage.setScene(new Scene(vBox));
        stage.showAndWait();
    }

    /** Get the score of the player. It is the number of box discovered multiplied by (2 * number of the box).
     *
     * @return score of the player.
     */
    private int countScore() {
        int score = 0;

        for (GridButton[] row : GRID) {
            for (GridButton button : row) {
                if(button.VALUE != -1 && !button.isHide())
                    score += 2 * button.VALUE;
            }
        }

        return score;
    }

    /** Save the score to the score file.
     *
     * @param score is the score to save.
     * @param time is the time of the game.
     */
    private void saveScore(int score, String time) {
        File scoreFile = new File("src/main/resources/score-file.txt");

        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(scoreFile, true))) {
            bufferedWriter.write(score + " " + time + "\n");
        } catch (IOException e) {
            System.err.println("Cannot open the score file : " + e.getMessage());
        }
    }

    /** Close the timer thread to avoid that the application keeps running. */
    public void closeThread() {
        timer.cancel();
    }
}
