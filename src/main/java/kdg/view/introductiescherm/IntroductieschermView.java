package kdg.view.introductiescherm;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

/**
 * @author Farok
 * @version 1.0 30/04/2026
 */
public class IntroductieschermView extends BorderPane {

    private Label titelLabel;
    private TextArea verhaalArea;
    private Button startKnop;
    private Button terugKnop;

    public IntroductieschermView() {
        this.initialiseNodes();
        this.layoutNodes();
    }

    private void initialiseNodes() {
        titelLabel = new Label("BUNKER-17");
        titelLabel.setStyle("""
                -fx-text-fill: #00ff41;
                -fx-font-family: monospace;
                -fx-font-size: 28px;
                -fx-font-weight: bold;
                """);

        verhaalArea = new TextArea(
            "JAAR 2031. Een mysterieuze explosie heeft Bunker-17 vergrendeld. " +
            "Jij bent de enige overlevende.\n\n" +
            "De noodgenerator heeft nog 10 minuten stroom.\n" +
            "Daarna sluit het ventilatiesysteem af — voor altijd.\n\n" +
            "Je moet ontsnappen. Nu.\n\n" +
            "Verken de kamers, verzamel items en los de puzzels op\n" +
            "om de uitgang te vinden. Elke seconde telt.\n\n" +
            "Veel succes, agent."
        );
        verhaalArea.setEditable(false);
        verhaalArea.setWrapText(true);
        verhaalArea.setStyle("""
                -fx-background-color: #1a1a1a;
                -fx-text-fill: #FFD700;
                -fx-border-color: #00ff41;
                -fx-border-width: 1px;
                -fx-font-family: monospace;
                -fx-font-size: 13px;
                -fx-control-inner-background: #1a1a1a;
                """);

        startKnop = maakKnop("Start missie →");
        terugKnop = maakKnop("← Terug");
    }

    private void layoutNodes() {
        this.setStyle("-fx-background-color: #1a1a1a;");
        this.setPadding(new Insets(30));

        BorderPane.setAlignment(titelLabel, Pos.CENTER);
        BorderPane.setMargin(titelLabel, new Insets(0, 0, 20, 0));
        this.setTop(titelLabel);

        BorderPane.setMargin(verhaalArea, new Insets(0, 0, 20, 0));
        this.setCenter(verhaalArea);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        HBox knoppen = new HBox(12, terugKnop, spacer, startKnop);
        knoppen.setAlignment(Pos.CENTER_LEFT);
        this.setBottom(knoppen);
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
                -fx-min-width: 140px;
                """);
        return knop;
    }

    Button getStartKnop()  { return startKnop; }
    Button getTerugKnop()  { return terugKnop; }
}
