package view;

import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.animation.Transition;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class IntroView extends BorderPane {

    private Label animatedLabel;

    /** Represent the view where we show the animated logo. */
    public IntroView() {
        this.animatedLabel = new Label("Minesweeper");

        this.setCenter(animatedLabel);
        this.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    /** Start to animate the label. */
    public void startAnimation() {
        LabelTransition labelTransition = new LabelTransition(animatedLabel, 10, 140, 1500);
        labelTransition.play();
    }

    class LabelTransition extends Transition {

        private Label node;
        private int start, end;

        /** Animate a label to change the font size.
         *
         * @param node is the node the animate.
         * @param start is the start font size.
         * @param end is the end font size.
         * @param duration is the duration of the animation.
         */
        public LabelTransition(Label node, int start, int end, int duration) {
            this.node = node;
            this.start = start;
            this.end = end - start;

            setCycleDuration(Duration.millis(duration));
            setInterpolator(Interpolator.EASE_BOTH);
        }

        @Override
        protected void interpolate(double frac) {
            int size = (int) ((end * frac) + start);

            if(size <= end) {
                this.node.setFont(new Font("Century Gothic", size));
            } else {
                // At the end, change the scene to play the game.
                HBox hBox = new HBox(new MenuView());
                hBox.setBackground(new Background(new BackgroundFill(Color.web("#e1e1e1"), CornerRadii.EMPTY, Insets.EMPTY)));

                this.node.getScene().setRoot(hBox);
                this.stop();
            }
        }
    }
}
