package kdg.view.optiescherm;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * @author Farok
 * @version 1.0 30/04/2026
 */
public class OptieschermView extends BorderPane {

    private Label titelLabel;
    private Label moeilijkheidLabel;
    private ToggleGroup toggleGroup;
    private RadioButton makkelijkRadio;
    private RadioButton normaalRadio;
    private RadioButton moeilijkRadio;
    private Button opslaanKnop;
    private Button annulerenKnop;

    public OptieschermView() {
        this.initialiseNodes();
        this.layoutNodes();
    }

    private void initialiseNodes() {
        titelLabel = new Label("OPTIES");
        titelLabel.setStyle("""
                -fx-text-fill: #00ff41;
                -fx-font-family: monospace;
                -fx-font-size: 20px;
                -fx-font-weight: bold;
                """);

        moeilijkheidLabel = new Label("Moeilijkheidsgraad:");
        moeilijkheidLabel.setStyle("-fx-text-fill: #00ff41; -fx-font-family: monospace; -fx-font-size: 13px;");

        toggleGroup   = new ToggleGroup();
        makkelijkRadio = maakRadio("Makkelijk — 15 min, inventory: 2 items");
        normaalRadio   = maakRadio("Normaal — 10 min, inventory: 1 item");
        moeilijkRadio  = maakRadio("Moeilijk — 5 min, inventory: 1 item");
        normaalRadio.setSelected(true);

        opslaanKnop   = maakKnop("Opslaan");
        annulerenKnop = maakKnop("Annuleren");
    }

    private void layoutNodes() {
        this.setStyle("-fx-background-color: #1a1a1a;");
        this.setPadding(new Insets(28));

        Label hint = new Label("Kies een moeilijkheidsgraad voor je volgende spel.");
        hint.setStyle("-fx-text-fill: #aaaaaa; -fx-font-family: monospace; -fx-font-size: 11px; -fx-font-style: italic;");

        VBox midden = new VBox(12,
                moeilijkheidLabel,
                makkelijkRadio,
                normaalRadio,
                moeilijkRadio,
                hint);
        midden.setPadding(new Insets(16, 0, 16, 0));

        HBox knoppen = new HBox(12, annulerenKnop, opslaanKnop);
        knoppen.setAlignment(Pos.CENTER_RIGHT);

        BorderPane.setAlignment(titelLabel, Pos.CENTER);
        BorderPane.setMargin(titelLabel, new Insets(0, 0, 8, 0));
        this.setTop(titelLabel);
        this.setCenter(midden);
        this.setBottom(knoppen);
    }

    private RadioButton maakRadio(String tekst) {
        RadioButton rb = new RadioButton(tekst);
        rb.setToggleGroup(toggleGroup);
        rb.setStyle("""
                -fx-text-fill: #00ff41;
                -fx-font-family: monospace;
                -fx-font-size: 13px;
                """);
        return rb;
    }

    private Button maakKnop(String tekst) {
        Button knop = new Button(tekst);
        knop.setStyle("""
                -fx-background-color: #1a1a1a;
                -fx-text-fill: #00ff41;
                -fx-border-color: #00ff41;
                -fx-border-width: 1px;
                -fx-font-family: monospace;
                -fx-font-size: 13px;
                -fx-cursor: hand;
                -fx-min-width: 110px;
                """);
        return knop;
    }

    // Package-private getters
    RadioButton getMakkelijkRadio()  { return makkelijkRadio; }
    RadioButton getNormaalRadio()    { return normaalRadio; }
    RadioButton getMoeilijkRadio()   { return moeilijkRadio; }
    Button getOpslaanKnop()          { return opslaanKnop; }
    Button getAnnulerenKnop()        { return annulerenKnop; }
}
