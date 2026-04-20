package kdg.view.startscherm;

import kdg.model.Game;

/**
 * @author Farok
 * @version 1.0 20/04/2026
 */
public class StartschermPresenter {

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
                System.out.println("Voer eerst een naam in.");
                return;
            }
            System.out.println("Nieuw spel gestart voor: " + naam);
            // TODO: wissel naar SpelschermView
        });

        view.getHighscoresKnop().setOnAction(e -> {
            System.out.println("Highscores geopend.");
            // TODO: open HighscoreschermView in nieuw Stage
        });

        view.getHelpKnop().setOnAction(e -> {
            System.out.println("Help geopend.");
            // TODO: open HelpschermView in nieuw Stage
        });

        view.getOverOnsKnop().setOnAction(e -> {
            System.out.println("Over ons geopend.");
            // TODO: open AboutschermView in nieuw Stage
        });
    }

    private void updateView() {
        // Startscherm heeft geen dynamische data bij opstarten
    }
}
