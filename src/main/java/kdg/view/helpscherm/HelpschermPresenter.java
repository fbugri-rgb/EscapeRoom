package kdg.view.helpscherm;

import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author Farok
 * @version 1.0 20/04/2026
 */
public class HelpschermPresenter {

    private final HelpschermView view;

    public HelpschermPresenter(HelpschermView view) {
        this.view = view;
        this.laadSpelregels();
        this.addEventHandlers();
    }

    private void laadSpelregels() {
        try (InputStream is = getClass().getResourceAsStream("/spelregels.txt")) {
            if (is == null) {
                view.getSpelregelsArea().setText(
                    "Spelregels konden niet worden geladen.\n\n" +
                    "Basisregels:\n" +
                    "1. Verken de kamers en pak items op.\n" +
                    "2. Gebruik items om deuren te openen.\n" +
                    "3. Los puzzels op om verder te gaan.\n" +
                    "4. Bereik de eindkamer voor de tijd om is!"
                );
                return;
            }
            String tekst = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            view.getSpelregelsArea().setText(tekst);
        } catch (IOException e) {
            view.getSpelregelsArea().setText(
                "Spelregels konden niet worden geladen.\n\n" +
                "Basisregels:\n" +
                "1. Verken de kamers en pak items op.\n" +
                "2. Gebruik items om deuren te openen.\n" +
                "3. Los puzzels op om verder te gaan.\n" +
                "4. Bereik de eindkamer voor de tijd om is!"
            );
        }
    }

    private void addEventHandlers() {
        view.getSluitKnop().setOnAction(e -> {
            Stage stage = (Stage) view.getScene().getWindow();
            stage.close();
        });
    }
}
