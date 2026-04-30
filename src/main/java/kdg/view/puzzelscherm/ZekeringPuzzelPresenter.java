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
public class ZekeringPuzzelPresenter {

    private static final String PUZZEL_ID = "zekering_01";

    private final Game game;
    private final Stage stage;
    private final ZekeringPuzzelView view;
    private final Runnable onOpgelost;

    public ZekeringPuzzelPresenter(Game game, Stage stage, ZekeringPuzzelView view, Runnable onOpgelost) {
        this.game       = game;
        this.stage      = stage;
        this.view       = view;
        this.onOpgelost = onOpgelost;
        this.addEventHandlers();
    }

    private void addEventHandlers() {
        view.getBevestigenKnop().setOnAction(e -> verwerkPoging());
        view.getAnnulerenKnop().setOnAction(e -> stage.close());
        view.getCodeVeld().setOnAction(e -> verwerkPoging());
    }

    private void verwerkPoging() {
        String invoer = view.getCodeVeld().getText();
        boolean gelukt = game.losZekeringPuzzelOp(PUZZEL_ID, invoer);

        if (gelukt) {
            GeluidManager.getInstance().speel("zekering");
            view.getFeedbackLabel().setStyle(
                    "-fx-text-fill: #00ff41; -fx-font-family: monospace; -fx-font-size: 12px;");
            view.getFeedbackLabel().setText("Code correct! Sleutel verschijnt in de kamer.");
            view.getBevestigenKnop().setDisable(true);
            view.getCodeVeld().setDisable(true);

            new Timeline(new KeyFrame(Duration.seconds(1.5), ev -> {
                stage.close();
                onOpgelost.run();
            })).play();
        } else {
            GeluidManager.getInstance().speel("puzzel_fout");
            view.getFeedbackLabel().setStyle(
                    "-fx-text-fill: #ff4444; -fx-font-family: monospace; -fx-font-size: 12px;");
            view.getFeedbackLabel().setText("Foute code. Probeer opnieuw.");
            view.getCodeVeld().clear();
            view.getCodeVeld().requestFocus();
        }
    }
}
