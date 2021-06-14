package view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class GridButton extends Button {

    public final int SIZE_ITEM = 40;
    public final int VALUE;

    private boolean hide = true;

    /** Represent a button on the grid.
     *
     * @param value is the item value (-1 if it's a bomb, the number of adjacent bomb otherwise).
     */
    public GridButton(int value) {
        this.VALUE = value;

        this.setMinSize(SIZE_ITEM, SIZE_ITEM);
        this.setMaxSize(SIZE_ITEM, SIZE_ITEM);

        this.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));

        this.setOnMouseEntered(event -> {
            if(hide) this.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        });
        this.setOnMouseExited(event -> {
            if(hide) this.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        });
    }

    /** Show the button. By default it is hide. */
    public void show() {
        hide = false;
        this.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        switch (VALUE) {
            case -1:
                this.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
                this.setTextFill(Color.WHITE);
                this.setText("B");
                break;
            case 0:
                break;
            default:
                this.setText(String.valueOf(VALUE));
                break;
        }
    }

    /** @return is the button hid or not ? */
    public boolean isHide() {
        return hide;
    }
}
