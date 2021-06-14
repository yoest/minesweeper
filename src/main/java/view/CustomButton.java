package view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class CustomButton extends Button {

    /** Represent a custom button.
     *
     * @param text is the text to put on the button.
     */
    public CustomButton(String text) {
        super(text);

        this.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        this.setOnMouseEntered(event -> {
            this.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
            this.setTextFill(Color.WHITE);
        });
        this.setOnMouseExited(event -> {
            this.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
            this.setTextFill(Color.BLACK);
        });
        this.setPadding(new Insets(10));
    }
}
