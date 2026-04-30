package kdg.view.optiescherm;

import javafx.stage.Stage;
import kdg.model.DifficultyLevel;
import kdg.model.GeluidManager;

import java.util.function.Consumer;

/**
 * @author Farok
 * @version 1.0 30/04/2026
 */
public class OptieschermPresenter {

    private final OptieschermView view;
    private final Stage stage;
    private final Consumer<DifficultyLevel> onOpgeslagen;

    public OptieschermPresenter(OptieschermView view, Stage stage,
                                DifficultyLevel huidigLevel,
                                Consumer<DifficultyLevel> onOpgeslagen) {
        this.view         = view;
        this.stage        = stage;
        this.onOpgeslagen = onOpgeslagen;
        this.addEventHandlers();
        GeluidManager.getInstance().voegKlikToe(view.getOpslaanKnop(), view.getAnnulerenKnop());
        this.selecteerHuidigLevel(huidigLevel);
    }

    private void selecteerHuidigLevel(DifficultyLevel level) {
        switch (level) {
            case MAKKELIJK -> view.getMakkelijkRadio().setSelected(true);
            case MOEILIJK  -> view.getMoeilijkRadio().setSelected(true);
            default        -> view.getNormaalRadio().setSelected(true);
        }
    }

    private void addEventHandlers() {
        view.getVolumeSlider().valueProperty().addListener(
                (obs, oud, nieuw) -> GeluidManager.getInstance().setVolume(nieuw.doubleValue()));

        view.getOpslaanKnop().setOnAction(e -> {
            GeluidManager.getInstance().setVolume(view.getVolumeSlider().getValue());
            onOpgeslagen.accept(geselecteerdeLevel());
            stage.close();
        });

        view.getAnnulerenKnop().setOnAction(e -> stage.close());
    }

    private DifficultyLevel geselecteerdeLevel() {
        if (view.getMakkelijkRadio().isSelected()) return DifficultyLevel.MAKKELIJK;
        if (view.getMoeilijkRadio().isSelected())  return DifficultyLevel.MOEILIJK;
        return DifficultyLevel.NORMAAL;
    }
}
