package kdg.view.verliesscherm;

import javafx.animation.*;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import kdg.model.DifficultyLevel;
import kdg.model.Game;
import kdg.model.GameBuilder;
import kdg.model.HighscoreManager;
import kdg.model.Timer;
import kdg.view.introductiescherm.IntroductieschermPresenter;
import kdg.view.introductiescherm.IntroductieschermView;
import kdg.view.spelscherm.SpelschermPresenter;
import kdg.view.spelscherm.SpelschermView;
import kdg.view.startscherm.StartschermPresenter;
import kdg.view.startscherm.StartschermView;

/**
 * @author Farok
 * @version 1.0 30/04/2026
 */
public class VerliesschermPresenter {

    private final VerliesschermView view;
    private final Stage stage;
    private final String spelerNaam;
    private final DifficultyLevel moeilijkheid;
    private Timeline knipperTimeline;

    public VerliesschermPresenter(VerliesschermView view, Stage stage,
                                  String spelerNaam, DifficultyLevel moeilijkheid) {
        this.view         = view;
        this.stage        = stage;
        this.spelerNaam   = spelerNaam;
        this.moeilijkheid = moeilijkheid;
        this.updateView();
        this.startAnimatie();
        this.addEventHandlers();
    }

    private void updateView() {
        view.getNaamLabel().setText("Gespeeld als: " + spelerNaam
                + "  |  Moeilijkheid: " + moeilijkheid.getLabel());
    }

    private void startAnimatie() {
        // Achtergrond knippert
        knipperTimeline = new Timeline(
            new KeyFrame(Duration.ZERO,
                new KeyValue(view.styleProperty(), "-fx-background-color: #1a0000;")),
            new KeyFrame(Duration.millis(500),
                new KeyValue(view.styleProperty(), "-fx-background-color: #3a0000;"))
        );
        knipperTimeline.setCycleCount(Timeline.INDEFINITE);
        knipperTimeline.setAutoReverse(true);
        knipperTimeline.play();

        // ScaleTransition + shake + fade-in labels
        ScaleTransition scale = new ScaleTransition(Duration.seconds(1.5), view.getTijdVerstrekenLabel());
        scale.setFromX(0.1); scale.setFromY(0.1);
        scale.setToX(1.0);   scale.setToY(1.0);
        scale.setOnFinished(e -> {
            TranslateTransition shake = new TranslateTransition(Duration.millis(100), view.getTijdVerstrekenLabel());
            shake.setFromX(-10); shake.setToX(10);
            shake.setCycleCount(10);
            shake.setAutoReverse(true);
            shake.play();

            FadeTransition fiSub = new FadeTransition(Duration.seconds(1.0), view.getSubtekstLabel());
            fiSub.setFromValue(0); fiSub.setToValue(1); fiSub.play();

            FadeTransition fiNaam = new FadeTransition(Duration.seconds(0.8), view.getNaamLabel());
            fiNaam.setFromValue(0); fiNaam.setToValue(1);
            fiNaam.setDelay(Duration.seconds(0.4));
            fiNaam.play();
        });
        scale.play();
    }

    private void addEventHandlers() {
        view.getOpnieuwKnop().setOnAction(e -> {
            stopAnimaties();
            IntroductieschermView introView = new IntroductieschermView();
            new IntroductieschermPresenter(introView, spelerNaam, moeilijkheid, stage);
            stage.setScene(new Scene(introView, 800, 500));
            stage.setTitle("Bunker-17 — Introductie");
        });

        view.getMenuKnop().setOnAction(e -> {
            stopAnimaties();
            StartschermView startView = new StartschermView();
            new StartschermPresenter(startView, moeilijkheid);
            stage.setScene(new Scene(startView, 800, 600));
            stage.setTitle("Bunker-17");
        });
    }

    private void stopAnimaties() {
        if (knipperTimeline != null) knipperTimeline.stop();
    }
}
