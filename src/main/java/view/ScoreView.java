package view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

public class ScoreView extends ScrollPane {

    private File scoreFile = new File("src/main/resources/score-file.txt");

    static class Score implements Comparable<Score> {
        public final int VALUE;
        private final String TIME;

        public Score(int VALUE, String TIME) {
            this.VALUE = VALUE;
            this.TIME = TIME;
        }

        @Override
        public int compareTo(Score o) {
            return Integer.compare(o.VALUE, this.VALUE);
        }
    }

    /** Represent a view where the user can see all the score. */
    public ScoreView() {
        VBox root = new VBox();

        ArrayList<Score> scores = getScoreFromFile();
        scores.sort(Score::compareTo);

        if(scores.size() == 0) {
            Label emptyLabel = new Label("There is no score.");
            root.getChildren().add(emptyLabel);
        } else {
            for (Score score : scores) {
                Label scoreLabel = new Label(score.VALUE + "\t:\t" + score.TIME);
                root.getChildren().add(scoreLabel);
            }
        }

        root.setAlignment(Pos.CENTER);
        this.setContent(root);
        this.setFitToWidth(true);
        this.setFitToHeight(true);
        this.setStyle("-fx-focus-color: black;");
    }

    /** Read each score from the file and add it to an arraylist.
     *
     * @return the arraylist of string containing every score.
     */
    private ArrayList<Score> getScoreFromFile() {
        ArrayList<Score> scores = new ArrayList<>();

        // Read line by line.
        try (Stream<String> stream = Files.lines(Paths.get("src/main/resources/score-file.txt"))) {
            stream.forEach(s -> {
                String[] line = s.split(" ");
                Score score = new Score(Integer.parseInt(line[0]), line[1]);
                scores.add(score);
            });
        } catch (IOException e) {
            System.err.println("Cannot open the score file : " + e.getMessage());
        }

        return scores;
    }

    /** Keep only the 10 first lines. */
    public void keepBestOne() {
        ArrayList<Score> scores = getScoreFromFile();
        scores.sort(Score::compareTo);

        // Rewrite only the first 10 lines to the file.
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(scoreFile))) {
            for(int i = 0; i < 10 && i < scores.size(); i++)
                bufferedWriter.write(scores.get(i).VALUE + " " + scores.get(i).TIME + "\n");
        } catch (IOException e) {
            System.err.println("Cannot open the score file : " + e.getMessage());
        }
    }

    /** Reset all the score. */
    public void deleteAllScore() {
        // Rewrite only the first 10 lines to the file.
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(scoreFile))) {
            bufferedWriter.write("");
        } catch (IOException e) {
            System.err.println("Cannot open the score file : " + e.getMessage());
        }
    }
}
