package kdg.view.startscherm;

import javafx.scene.Scene;
import javafx.stage.Stage;
import kdg.model.Game;
import kdg.model.GameBuilder;
import kdg.model.Timer;
import kdg.view.spelscherm.SpelschermPresenter;
import kdg.view.spelscherm.SpelschermView;

/**
 * @author Farok
 * @version 2.0 20/04/2026
 */
public class StartschermPresenter {

    private static final int SPELTIJD_SECONDEN = 600; // 10 minuten

    private final Game model;
    private final StartschermView view;

    public StartschermPresenter(Game model, StartschermView view) {
        this.model = model;
        this.view = view;
        this.addEventHandlers();
        this.updateView();
    }

    private void addEventHandlers() {
        view.getNieuwSpelKnop().setOnAction(e -> {
            String naam = view.getNaamVeld().getText().trim();
            if (naam.isEmpty()) {
                view.getNaamVeld().setStyle(
                        "-fx-background-color: #2a2a2a; -fx-text-fill: #ff4444; " +
                        "-fx-border-color: #ff4444; -fx-font-family: monospace;");
                view.getNaamVeld().setPromptText("Naam is verplicht!");
                return;
            }

            Game nieuwSpel = GameBuilder.buildGame();
            Timer timer = new Timer(SPELTIJD_SECONDEN);

            SpelschermView spelView = new SpelschermView();
            new SpelschermPresenter(nieuwSpel, timer, spelView);

            Stage stage = (Stage) view.getScene().getWindow();
            stage.setScene(new Scene(spelView, 1000, 650));
            stage.setTitle("Bunker-17 — " + naam);
        });

        view.getNaamVeld().setOnKeyTyped(e -> {
            // Reset rode rand zodra gebruiker begint te typen
            view.getNaamVeld().setStyle(
                    "-fx-background-color: #2a2a2a; -fx-text-fill: #00ff41; " +
                    "-fx-border-color: #00ff41; -fx-font-family: monospace;");
        });

        view.getHighscoresKnop().setOnAction(e -> {
            // TODO: open HighscoreschermView in nieuw Stage
        });

        view.getHelpKnop().setOnAction(e -> {
            // TODO: open HelpschermView in nieuw Stage
        });

        view.getOverOnsKnop().setOnAction(e -> {
            // TODO: open AboutschermView in nieuw Stage
        });
    }

    private void updateView() {
        // Startscherm heeft geen dynamische data bij opstarten
    }
}
