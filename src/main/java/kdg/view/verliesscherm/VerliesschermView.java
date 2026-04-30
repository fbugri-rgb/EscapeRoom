package kdg.view.verliesscherm;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * @author Farok
 * @version 1.0 30/04/2026
 */
public class VerliesschermView extends BorderPane {

    private Label tijdVerstrekenLabel;
    private Label subtekstLabel;
    private Label naamLabel;
    private Button opnieuwKnop;
    private Button menuKnop;

    public VerliesschermView() {
        this.initialiseNodes();
        this.layoutNodes();
    }

    private void initialiseNodes() {
        tijdVerstrekenLabel = new Label("GAME IS GAME - RIP!!!");
        tijdVerstrekenLabel.setStyle("""
                -fx-text-fill: #ff4444;
                -fx-font-family: monospace;
                -fx-font-size: 48px;
                -fx-font-weight: bold;
                """);
        tijdVerstrekenLabel.setScaleX(0.1);
        tijdVerstrekenLabel.setScaleY(0.1);

        subtekstLabel = new Label("De bunker is verzegeld voor altijd, GAME OVER...");
        subtekstLabel.setStyle("""
                -fx-text-fill: #aaaaaa;
                -fx-font-family: monospace;
                -fx-font-size: 16px;
                -fx-font-style: italic;
                """);
        subtekstLabel.setOpacity(0);

        naamLabel = new Label("");
        naamLabel.setStyle("""
                -fx-text-fill: #aaaaaa;
                -fx-font-family: monospace;
                -fx-font-size: 13px;
                """);
        naamLabel.setOpacity(0);

        opnieuwKnop = maakKnop("↩ Probeer opnieuw");
        menuKnop    = maakKnop("⌂ Terug naar menu");
    }

    private void layoutNodes() {
        this.setStyle("-fx-background-color: #1a0000;");

        VBox centrum = new VBox(24, tijdVerstrekenLabel, subtekstLabel, naamLabel);
        centrum.setAlignment(Pos.CENTER);
        this.setCenter(centrum);

        HBox knoppen = new HBox(16, opnieuwKnop, menuKnop);
        knoppen.setAlignment(Pos.CENTER);
        knoppen.setPadding(new Insets(12));
        knoppen.setStyle("-fx-background-color: #0f0000;");
        this.setBottom(knoppen);
    }

    private Button maakKnop(String tekst) {
        Button knop = new Button(tekst);
        knop.setMinWidth(180);
        knop.setStyle("""
                -fx-background-color: #1a0000;
                -fx-text-fill: #ff4444;
                -fx-border-color: #ff4444;
                -fx-border-width: 1px;
                -fx-font-family: monospace;
                -fx-font-size: 13px;
                -fx-cursor: hand;
                """);
        return knop;
    }

    Label getTijdVerstrekenLabel() { return tijdVerstrekenLabel; }
    Label getSubtekstLabel()       { return subtekstLabel; }
    Label getNaamLabel()           { return naamLabel; }
    Button getOpnieuwKnop()        { return opnieuwKnop; }
    Button getMenuKnop()           { return menuKnop; }
}
