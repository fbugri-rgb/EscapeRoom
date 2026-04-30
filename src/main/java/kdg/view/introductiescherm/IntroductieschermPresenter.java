package kdg.view.introductiescherm;

import javafx.scene.Scene;
import javafx.stage.Stage;
import kdg.model.Game;
import kdg.model.GameBuilder;
import kdg.model.HighscoreManager;
import kdg.model.Timer;
import kdg.view.spelscherm.SpelschermPresenter;
import kdg.view.spelscherm.SpelschermView;
import kdg.view.startscherm.StartschermPresenter;
import kdg.view.startscherm.StartschermView;

/**
 * @author Farok
 * @version 1.0 30/04/2026
 */
public class IntroductieschermPresenter {

    private static final int SPELTIJD_SECONDEN = 600;

    private final IntroductieschermView view;
    private final String spelerNaam;
    private final Stage primaryStage;

    public IntroductieschermPresenter(IntroductieschermView view, String spelerNaam, Stage primaryStage) {
        this.view        = view;
        this.spelerNaam  = spelerNaam;
        this.primaryStage = primaryStage;
        this.addEventHandlers();
    }

    private void addEventHandlers() {
        view.getStartKnop().setOnAction(e -> startMissie());
        view.getTerugKnop().setOnAction(e -> gaNaarStartscherm());
    }

    private void startMissie() {
        Game game           = GameBuilder.buildGame(spelerNaam);
        Timer timer         = new Timer(SPELTIJD_SECONDEN);
        HighscoreManager hs = new HighscoreManager();

        SpelschermView spelView = new SpelschermView();
        new SpelschermPresenter(game, timer, hs, spelView);

        primaryStage.setScene(new Scene(spelView, 1000, 650));
        primaryStage.setTitle("Bunker-17 — " + spelerNaam);
    }

    private void gaNaarStartscherm() {
        StartschermView startView = new StartschermView();
        new StartschermPresenter(GameBuilder.buildGame(), startView);
        primaryStage.setScene(new Scene(startView, 800, 600));
        primaryStage.setTitle("Bunker-17");
    }
}
