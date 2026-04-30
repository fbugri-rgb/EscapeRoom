package kdg.view.introductiescherm;

import javafx.scene.Scene;
import javafx.stage.Stage;
import kdg.model.DifficultyLevel;
import kdg.model.Game;
import kdg.model.GameBuilder;
import kdg.model.GeluidManager;
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

    private final IntroductieschermView view;
    private final String spelerNaam;
    private final DifficultyLevel moeilijkheid;
    private final Stage primaryStage;

    public IntroductieschermPresenter(IntroductieschermView view, String spelerNaam,
                                      DifficultyLevel moeilijkheid, Stage primaryStage) {
        this.view         = view;
        this.spelerNaam   = spelerNaam;
        this.moeilijkheid = moeilijkheid;
        this.primaryStage = primaryStage;
        this.addEventHandlers();
        GeluidManager.getInstance().voegKlikToe(view.getStartKnop(), view.getTerugKnop());
        this.updateView();
    }

    private void updateView() {
        int minuten = moeilijkheid.getTijdInSeconden() / 60;
        String verhaal = String.format("""
                JAAR 2031. Een mysterieuze explosie heeft \
                Bunker-17 vergrendeld. Jij bent de enige \
                overlevende.

                De noodgenerator heeft nog %d minuten stroom.
                Daarna sluit het ventilatiesysteem af, voor altijd!

                Je moet ontsnappen. Nu.

                Verken de kamers, verzamel items en los de puzzels op \
                om de uitgang te vinden. Elke seconde telt.

                Veel succes, agent.""", minuten);
        view.getVerhaalArea().setText(verhaal);
    }

    private void addEventHandlers() {
        view.getStartKnop().setOnAction(e -> startMissie());
        view.getTerugKnop().setOnAction(e -> gaNaarStartscherm());
    }

    private void startMissie() {
        Game game           = GameBuilder.buildGame(spelerNaam);
        game.setMoeilijkheid(moeilijkheid);
        Timer timer         = new Timer(moeilijkheid.getTijdInSeconden());
        HighscoreManager hs = new HighscoreManager();

        SpelschermView spelView = new SpelschermView();
        new SpelschermPresenter(game, timer, hs, spelView, primaryStage, moeilijkheid);

        primaryStage.setScene(new Scene(spelView, 1000, 650));
        primaryStage.setTitle("Bunker-17 — " + spelerNaam);
    }

    private void gaNaarStartscherm() {
        StartschermView startView = new StartschermView();
        new StartschermPresenter(startView, moeilijkheid);
        primaryStage.setScene(new Scene(startView, 800, 600));
        primaryStage.setTitle("Bunker-17");
    }
}
