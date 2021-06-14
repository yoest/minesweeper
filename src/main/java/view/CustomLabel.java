package view;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class CustomLabel extends Label {

    /** Represent a custom label.
     *
     * @param text is the text to put on the label.
     */
    public CustomLabel(String text) {
        super(text);

        this.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        this.setPadding(new Insets(10));
    }
}
