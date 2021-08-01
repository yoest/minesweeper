package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class FXLauncher extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Minesweeper");
        primaryStage.getIcons().add(new Image("app_icon.png"));
        primaryStage.setWidth(1200);
        primaryStage.setHeight(700);

        IntroView introView = new IntroView();

        primaryStage.setScene(new Scene(introView));
        primaryStage.show();

        introView.startAnimation();
    }

    public static void main(String[] args) {
        launch(args);
    }


}
