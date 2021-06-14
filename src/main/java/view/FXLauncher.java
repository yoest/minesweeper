package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class FXLauncher extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Minesweeper");
        primaryStage.setWidth(1200);
        primaryStage.setHeight(700);

        MenuView menuView = new MenuView();
        GameView gameView = new GameView(10);
        HBox hBox = new HBox(menuView, gameView);

        primaryStage.setScene(new Scene(hBox));

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
