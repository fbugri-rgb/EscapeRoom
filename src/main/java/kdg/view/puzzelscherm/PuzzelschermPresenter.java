package kdg.view.puzzelscherm;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.stage.Stage;
import javafx.util.Duration;
import kdg.model.Game;
import kdg.model.GeluidManager;

/**
 * @author Farok
 * @version 1.0 23/04/2026
 */
public class PuzzelschermPresenter {

    private static final String PUZZEL_ID = "terminal_01";

    private final Game game;
    private final Stage stage;
    private final PuzzelschermView view;
    private final Runnable onOpgelost;

    public PuzzelschermPresenter(Game game, Stage stage, PuzzelschermView view, Runnable onOpgelost) {
        this.game       = game;
        this.stage      = stage;
        this.view       = view;
        this.onOpgelost = onOpgelost;
        this.addEventHandlers();
        GeluidManager.getInstance().voegKlikToe(view.getBevestigenKnop(), view.getAnnulerenKnop());
    }

    private void addEventHandlers() {
        view.getBevestigenKnop().setOnAction(e -> verwerkPoging());
        view.getAnnulerenKnop().setOnAction(e -> stage.close());
        view.getWachtwoordVeld().setOnAction(e -> verwerkPoging());
    }

    private void verwerkPoging() {
        String poging = view.getWachtwoordVeld().getText();
        boolean gelukt = game.losTerminalPuzzelOp(PUZZEL_ID, poging);

        if (gelukt) {
            GeluidManager.getInstance().speel("puzzel_correct");
            view.getFeedbackLabel().setStyle(
                    "-fx-text-fill: #00ff41; -fx-font-family: monospace; -fx-font-size: 12px;");
            view.getFeedbackLabel().setText("Toegang verleend!");
            view.getBevestigenKnop().setDisable(true);
            view.getWachtwoordVeld().setDisable(true);

            new Timeline(new KeyFrame(Duration.seconds(1), ev -> {
                stage.close();
                onOpgelost.run();
            })).play();
        } else {
            GeluidManager.getInstance().speel("puzzel_fout");
            view.getFeedbackLabel().setStyle(
                    "-fx-text-fill: #ff4444; -fx-font-family: monospace; -fx-font-size: 12px;");
            view.getFeedbackLabel().setText("Ongeldig wachtwoord. Probeer opnieuw.");
            view.getWachtwoordVeld().clear();
            view.getWachtwoordVeld().requestFocus();
        }
    }
}
