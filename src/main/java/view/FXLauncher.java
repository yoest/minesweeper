package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class FXLauncher extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Minesweeper");
        primaryStage.setWidth(1200);
        primaryStage.setHeight(700);
        primaryStage.setScene(new Scene(new GameView()));

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
