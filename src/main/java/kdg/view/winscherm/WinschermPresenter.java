package kdg.view.winscherm;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import kdg.model.DifficultyLevel;
import kdg.model.HighscoreManager;
import kdg.view.highscorescherm.HighscoreschermPresenter;
import kdg.view.highscorescherm.HighscoreschermView;
import kdg.view.startscherm.StartschermPresenter;
import kdg.view.startscherm.StartschermView;

import java.util.Random;

/**
 * @author Farok
 * @version 1.0 30/04/2026
 */
public class WinschermPresenter {

    private static final Random RAND = new Random();
    private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private final WinschermView view;
    private final Stage stage;
    private final String spelerNaam;
    private final int verstrekenSeconden;
    private final HighscoreManager hsManager;
    private final DifficultyLevel moeilijkheid;
    private Timeline matrixTimeline;

    public WinschermPresenter(WinschermView view, Stage stage, String spelerNaam,
                              int verstrekenSeconden, HighscoreManager hsManager,
                              DifficultyLevel moeilijkheid) {
        this.view               = view;
        this.stage              = stage;
        this.spelerNaam         = spelerNaam;
        this.verstrekenSeconden = verstrekenSeconden;
        this.hsManager          = hsManager;
        this.moeilijkheid       = moeilijkheid;
        this.updateView();
        this.startAnimatie();
        this.addEventHandlers();
    }

    private void updateView() {
        int min = verstrekenSeconden / 60;
        int sec = verstrekenSeconden % 60;
        view.getNaamLabel().setText(spelerNaam + " heeft de bunker verlaten.");
        view.getTijdLabel().setText(String.format("Tijd: %d min %d sec  |  Moeilijkheid: %s",
                min, sec, moeilijkheid.getLabel()));
    }

    private void startAnimatie() {
        int W = 800, H = 540;
        GraphicsContext gc = view.getCanvas().getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, W, H);

        int colWidth = 20;
        int cols = W / colWidth;
        int[] drops = new int[cols];
        for (int i = 0; i < cols; i++) {
            drops[i] = -(RAND.nextInt(30));
        }

        matrixTimeline = new Timeline(new KeyFrame(Duration.millis(50), e -> {
            gc.setFill(Color.color(0, 0, 0, 0.05));
            gc.fillRect(0, 0, W, H);
            gc.setFont(Font.font("Monospace", 16));

            for (int i = 0; i < cols; i++) {
                if (drops[i] < 0) { drops[i]++; continue; }
                char c = CHARS.charAt(RAND.nextInt(CHARS.length()));
                gc.setFill(Color.web("#00ff41"));
                gc.fillText(String.valueOf(c), i * colWidth, drops[i] * 18);
                if (drops[i] * 18 > H && RAND.nextDouble() > 0.975) {
                    drops[i] = -(RAND.nextInt(20));
                } else {
                    drops[i]++;
                }
            }
        }));
        matrixTimeline.setCycleCount(Timeline.INDEFINITE);
        matrixTimeline.play();

        // Labels verschijnen na vertraging
        PauseTransition p1 = new PauseTransition(Duration.seconds(1.5));
        p1.setOnFinished(e -> fade(view.getOntsnaptLabel(), 1.5));

        PauseTransition p2 = new PauseTransition(Duration.seconds(3.5));
        p2.setOnFinished(e -> fade(view.getNaamLabel(), 1.0));

        PauseTransition p3 = new PauseTransition(Duration.seconds(4.5));
        p3.setOnFinished(e -> fade(view.getTijdLabel(), 1.0));

        p1.play(); p2.play(); p3.play();
    }

    private void fade(javafx.scene.Node node, double duur) {
        FadeTransition ft = new FadeTransition(Duration.seconds(duur), node);
        ft.setFromValue(0); ft.setToValue(1); ft.play();
    }

    private void addEventHandlers() {
        view.getHighscoresKnop().setOnAction(e -> {
            HighscoreschermView hsView = new HighscoreschermView();
            new HighscoreschermPresenter(hsManager, hsView);
            Stage hsStage = new Stage();
            hsStage.setTitle("Highscores");
            hsStage.setScene(new Scene(hsView, 620, 400));
            hsStage.show();
        });

        view.getMenuKnop().setOnAction(e -> {
            if (matrixTimeline != null) matrixTimeline.stop();
            StartschermView startView = new StartschermView();
            new StartschermPresenter(startView, moeilijkheid);
            stage.setScene(new Scene(startView, 800, 600));
            stage.setTitle("Bunker-17");
        });
    }
}
