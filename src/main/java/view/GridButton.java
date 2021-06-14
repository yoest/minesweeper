package view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class GridButton extends Button {

    private final int SIZE_ITEM = 30;
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

        this.setOnMouseEntered(event -> this.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT))));
        this.setOnMouseExited(event -> this.setBorder(null));
    }

    /** Show the button. By default it is hide. */
    public void show() {
        hide = false;
        this.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        switch (VALUE) {
            case -1:
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
