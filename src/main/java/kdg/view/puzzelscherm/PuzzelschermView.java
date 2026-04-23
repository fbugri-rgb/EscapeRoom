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
public class PuzzelschermView extends VBox {

    private Label titelLabel;
    private Label instructieLabel;
    private TextField wachtwoordVeld;
    private Button bevestigenKnop;
    private Button annulerenKnop;
    private Label feedbackLabel;

    public PuzzelschermView() {
        this.initialiseNodes();
        this.layoutNodes();
    }

    private void initialiseNodes() {
        titelLabel = new Label("TERMINAL TOEGANG VEREIST");
        titelLabel.setStyle("""
                -fx-text-fill: #00ff41;
                -fx-font-family: monospace;
                -fx-font-size: 16px;
                -fx-font-weight: bold;
                """);

        instructieLabel = new Label("Voer het wachtwoord in:");
        instructieLabel.setStyle("-fx-text-fill: #aaaaaa; -fx-font-family: monospace; -fx-font-size: 13px;");

        wachtwoordVeld = new TextField();
        wachtwoordVeld.setMaxWidth(280);
        wachtwoordVeld.setStyle("""
                -fx-background-color: #2a2a2a;
                -fx-text-fill: #00ff41;
                -fx-border-color: #00ff41;
                -fx-border-width: 1px;
                -fx-font-family: monospace;
                -fx-font-size: 13px;
                """);

        bevestigenKnop = maakKnop("Bevestigen");
        annulerenKnop  = maakKnop("Annuleren");

        feedbackLabel = new Label("");
        feedbackLabel.setStyle("-fx-font-family: monospace; -fx-font-size: 12px;");
    }

    private void layoutNodes() {
        this.setStyle("-fx-background-color: #1a1a1a;");
        this.setPadding(new Insets(28));
        this.setSpacing(16);
        this.setAlignment(Pos.CENTER);

        HBox knoppen = new HBox(12, bevestigenKnop, annulerenKnop);
        knoppen.setAlignment(Pos.CENTER);

        this.getChildren().addAll(titelLabel, instructieLabel, wachtwoordVeld, knoppen, feedbackLabel);
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
    TextField getWachtwoordVeld()  { return wachtwoordVeld; }
    Button getBevestigenKnop()         { return bevestigenKnop; }
    Button getAnnulerenKnop()          { return annulerenKnop; }
    Label getFeedbackLabel()           { return feedbackLabel; }
}
