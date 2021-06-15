package view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class FXLauncher extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Minesweeper");
        primaryStage.setWidth(1200);
        primaryStage.setHeight(700);

        MenuView menuView = new MenuView();
        HBox hBox = new HBox(menuView);
        hBox.setBackground(new Background(new BackgroundFill(Color.web("#e1e1e1"), CornerRadii.EMPTY, Insets.EMPTY)));

        primaryStage.setScene(new Scene(hBox));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


}
