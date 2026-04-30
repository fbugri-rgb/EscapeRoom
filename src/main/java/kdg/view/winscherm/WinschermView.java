package kdg.view.winscherm;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

/**
 * @author Farok
 * @version 1.0 30/04/2026
 */
public class WinschermView extends BorderPane {

    private Canvas canvas;
    private Label ontsnaptLabel;
    private Label naamLabel;
    private Label tijdLabel;
    private Button highscoresKnop;
    private Button menuKnop;

    public WinschermView() {
        this.initialiseNodes();
        this.layoutNodes();
    }

    private void initialiseNodes() {
        canvas = new Canvas(800, 540);

        ontsnaptLabel = new Label("ONTSNAPT!");
        ontsnaptLabel.setStyle("""
                -fx-text-fill: #FFD700;
                -fx-font-family: monospace;
                -fx-font-size: 48px;
                -fx-font-weight: bold;
                """);
        ontsnaptLabel.setOpacity(0);

        naamLabel = new Label("");
        naamLabel.setStyle("""
                -fx-text-fill: #00ff41;
                -fx-font-family: monospace;
                -fx-font-size: 18px;
                """);
        naamLabel.setOpacity(0);

        tijdLabel = new Label("");
        tijdLabel.setStyle("""
                -fx-text-fill: #aaaaaa;
                -fx-font-family: monospace;
                -fx-font-size: 14px;
                """);
        tijdLabel.setOpacity(0);

        highscoresKnop = maakKnop("🏆 Highscores");
        menuKnop       = maakKnop("↩ Terug naar menu");
    }

    private void layoutNodes() {
        this.setStyle("-fx-background-color: black;");

        javafx.scene.layout.VBox overlay = new javafx.scene.layout.VBox(12,
                ontsnaptLabel, naamLabel, tijdLabel);
        overlay.setAlignment(Pos.CENTER);

        StackPane centrum = new StackPane(canvas, overlay);
        this.setCenter(centrum);

        HBox knoppen = new HBox(16, highscoresKnop, menuKnop);
        knoppen.setAlignment(Pos.CENTER);
        knoppen.setPadding(new Insets(16));
        knoppen.setPrefHeight(60);
        knoppen.setStyle("-fx-background-color: #0a0a0a; -fx-border-color: #00ff41; -fx-border-width: 1 0 0 0;");
        this.setBottom(knoppen);
    }

    private Button maakKnop(String tekst) {
        Button knop = new Button(tekst);
        knop.setMinWidth(180);
        knop.setStyle("""
                -fx-background-color: #0a0a0a;
                -fx-text-fill: #00ff41;
                -fx-border-color: #00ff41;
                -fx-border-width: 1px;
                -fx-font-family: monospace;
                -fx-font-size: 13px;
                -fx-cursor: hand;
                """);
        return knop;
    }

    Canvas getCanvas()              { return canvas; }
    Label getOntsnaptLabel()        { return ontsnaptLabel; }
    Label getNaamLabel()            { return naamLabel; }
    Label getTijdLabel()            { return tijdLabel; }
    Button getHighscoresKnop()      { return highscoresKnop; }
    Button getMenuKnop()            { return menuKnop; }
}
