package kdg.view.puzzelscherm;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * @author Farok
 * @version 1.0 23/04/2026
 */
public class ZekeringPuzzelView extends VBox {

    private Label titelLabel;
    private Label instructieLabel;
    private Label hintLabel;
    private TextField codeVeld;
    private Button bevestigenKnop;
    private Button annulerenKnop;
    private Label feedbackLabel;

    public ZekeringPuzzelView() {
        this.initialiseNodes();
        this.layoutNodes();
    }

    private void initialiseNodes() {
        titelLabel = new Label("ZEKERINGKAST GEACTIVEERD");
        titelLabel.setStyle("""
                -fx-text-fill: #00ff41;
                -fx-font-family: monospace;
                -fx-font-size: 16px;
                -fx-font-weight: bold;
                """);

        instructieLabel = new Label("Een code verschijnt op het scherm:");
        instructieLabel.setStyle("-fx-text-fill: #aaaaaa; -fx-font-family: monospace; -fx-font-size: 13px;");

        hintLabel = new Label("Voer de code in (bv. 1-2-3 of 123)");
        hintLabel.setStyle("""
                -fx-text-fill: #FFD700;
                -fx-font-family: monospace;
                -fx-font-size: 13px;
                -fx-font-style: italic;
                """);

        codeVeld = new TextField();
        codeVeld.setMaxWidth(280);
        codeVeld.setPromptText("voer code in...");
        codeVeld.setStyle("""
                -fx-background-color: #2a2a2a;
                -fx-text-fill: #00ff41;
                -fx-border-color: #00ff41;
                -fx-border-width: 1px;
                -fx-font-family: monospace;
                -fx-font-size: 13px;
                -fx-prompt-text-fill: #555555;
                """);

        bevestigenKnop = maakKnop("Bevestigen");
        annulerenKnop  = maakKnop("Annuleren");

        feedbackLabel = new Label("");
        feedbackLabel.setStyle("-fx-font-family: monospace; -fx-font-size: 12px;");
    }

    private void layoutNodes() {
        this.setStyle("-fx-background-color: #1a1a1a;");
        this.setPadding(new Insets(28));
        this.setSpacing(14);
        this.setAlignment(Pos.CENTER);

        HBox knoppen = new HBox(12, bevestigenKnop, annulerenKnop);
        knoppen.setAlignment(Pos.CENTER);

        this.getChildren().addAll(titelLabel, instructieLabel, hintLabel, codeVeld, knoppen, feedbackLabel);
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

    // Package-private getters voor Presenter
    TextField getCodeVeld()        { return codeVeld; }
    Button getBevestigenKnop()     { return bevestigenKnop; }
    Button getAnnulerenKnop()      { return annulerenKnop; }
    Label getFeedbackLabel()        { return feedbackLabel; }
}
