package kdg.view.aboutscherm;

import javafx.stage.Stage;

/**
 * @author Farok
 * @version 1.0 20/04/2026
 */
// Deze Presenter koppelt de About-view aan de actie van de sluitknop.
public class AboutschermPresenter {

    private final AboutschermView view;

    // We bewaren de view en koppelen meteen de event handlers aan de knoppen.
    public AboutschermPresenter(AboutschermView view) {
        this.view = view;
        this.addEventHandlers();
    }

    private void addEventHandlers() {
        // Als de gebruiker op "Sluiten" klikt, sluiten we het huidige venster.
        view.getSluitKnop().setOnAction(e -> {
            // We halen het Stage-object op via de scene van de view.
            Stage stage = (Stage) view.getScene().getWindow();
            stage.close();
        });
    }
}
