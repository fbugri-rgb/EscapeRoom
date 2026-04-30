package kdg.view.spelscherm;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import javafx.util.Duration;
import kdg.model.DifficultyLevel;
import kdg.model.Door;
import kdg.model.Game;
import kdg.model.Item;
import kdg.model.Timer;
import kdg.model.HighscoreManager;
import kdg.model.Puzzle;
import kdg.view.helpscherm.HelpschermPresenter;
import kdg.view.helpscherm.HelpschermView;
import kdg.view.puzzelscherm.PuzzelschermPresenter;
import kdg.view.puzzelscherm.PuzzelschermView;
import kdg.view.puzzelscherm.ZekeringPuzzelPresenter;
import kdg.view.puzzelscherm.ZekeringPuzzelView;
import kdg.view.startscherm.StartschermPresenter;
import kdg.view.startscherm.StartschermView;
import kdg.view.verliesscherm.VerliesschermPresenter;
import kdg.view.verliesscherm.VerliesschermView;
import kdg.view.winscherm.WinschermPresenter;
import kdg.view.winscherm.WinschermView;

import java.util.List;

/**
 * @author Farok
 * @version 1.0 20/04/2026
 */
public class SpelschermPresenter {

    private static final String EINDKAMER_NAAM = "Eindkamer";

    private final Game game;
    private final Timer timer;
    private final HighscoreManager hsManager;
    private final SpelschermView view;
    private final Stage stage;
    private final DifficultyLevel moeilijkheid;
    private Timeline timerTimeline;

    public SpelschermPresenter(Game game, Timer timer, HighscoreManager hsManager,
                               SpelschermView view, Stage stage, DifficultyLevel moeilijkheid) {
        this.game         = game;
        this.timer        = timer;
        this.hsManager    = hsManager;
        this.view         = view;
        this.stage        = stage;
        this.moeilijkheid = moeilijkheid;
        this.addEventHandlers();
        this.addWindowEventHandlers();
        this.updateView();
        this.startTimer();
    }

    private void addEventHandlers() {
        // Oppakken
        view.getOppakkenKnop().setOnAction(e -> {
            int index = view.getKamerItemsLijst().getSelectionModel().getSelectedIndex();
            if (index < 0) {
                toonMelding("Selecteer eerst een item om op te pakken.");
                return;
            }
            List<Item> items = game.getCurrentRoom().getItems();
            if (index >= items.size()) return;

            Item item = items.get(index);
            boolean gelukt = game.pickupItem(item);
            if (gelukt) {
                toonInfo("Je pakt op: " + item.getName());
            } else if (game.getPlayer().getInventory().getItems().size() >= game.getPlayer().getInventory().getMaxItems()) {
                int max = game.getPlayer().getInventory().getMaxItems();
                toonMelding("Inventory vol! Je kan maar " + max + (max == 1 ? " item" : " items") + " dragen. Leg eerst iets neer.");
            } else {
                toonMelding("Je hebt dit item al.");
            }
            updateView();
            if (gelukt) controleerPuzzel();
        });

        // Neerleggen
        view.getNeerleggenKnop().setOnAction(e -> {
            int index = view.getInventoryLijst().getSelectionModel().getSelectedIndex();
            if (index < 0) {
                toonMelding("Selecteer eerst een item uit je inventory om neer te leggen.");
                return;
            }
            List<Item> inventory = game.getPlayer().getInventory().getItems();
            if (index >= inventory.size()) return;

            Item item = inventory.get(index);
            game.getPlayer().dropItem(item);
            game.getCurrentRoom().addItem(item);
            updateView();
        });

        // Ga door deur — knop
        view.getGaDoorDeurKnop().setOnAction(e -> beweegDoorGeselecteerdeDeur());

        // Ga door deur — dubbelklik op uitgangenLijst
        view.getUitgangenLijst().setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY && e.getClickCount() == 2) {
                beweegDoorGeselecteerdeDeur();
            }
        });

        // Gebruik item op deur
        view.getGebruikItemKnop().setOnAction(e -> {
            int itemIndex = view.getInventoryLijst().getSelectionModel().getSelectedIndex();
            int deurIndex = view.getUitgangenLijst().getSelectionModel().getSelectedIndex();

            if (itemIndex < 0) {
                toonMelding("Selecteer een item uit je inventory.");
                return;
            }
            if (deurIndex < 0) {
                toonMelding("Selecteer een uitgang.");
                return;
            }

            List<Item> inventory = game.getPlayer().getInventory().getItems();
            List<Door> deuren    = game.getCurrentRoom().getExits();

            if (itemIndex >= inventory.size() || deurIndex >= deuren.size()) return;

            Item item = inventory.get(itemIndex);
            Door deur = deuren.get(deurIndex);

            boolean gelukt = game.useItemOnDoor(item, deur);
            if (gelukt) {
                toonInfo("Deur geopend met: " + item.getName());
            } else {
                toonMelding("Dit item opent deze deur niet.");
            }
            updateView();
        });

        // Beschrijving kameritem bij selectie
        view.getKamerItemsLijst().getSelectionModel().selectedIndexProperty().addListener((obs, oud, nieuw) -> {
            int index = nieuw.intValue();
            List<Item> items = game.getCurrentRoom().getItems();
            if (index >= 0 && index < items.size()) {
                view.getItemBeschrijvingLabel().setText(items.get(index).getDescription());
            }
        });

        // Beschrijving inventory-item bij selectie
        view.getInventoryLijst().getSelectionModel().selectedIndexProperty().addListener((obs, oud, nieuw) -> {
            int index = nieuw.intValue();
            List<Item> inventory = game.getPlayer().getInventory().getItems();
            if (index >= 0 && index < inventory.size()) {
                view.getItemBeschrijvingLabel().setText(inventory.get(index).getDescription());
            }
        });

        // Menu: Pauzeren
        view.getPauzerenItem().setOnAction(e -> {
            timer.pauze();
            timerTimeline.pause();
            game.pause();
        });

        // Menu: Stoppen
        view.getStoppenItem().setOnAction(e -> {
            if (toonBevestigingsdialoog()) {
                timerTimeline.stop();
                timer.pauze();
                StartschermView startView = new StartschermView();
                new StartschermPresenter(startView, moeilijkheid);
                stage.setScene(new Scene(startView, 800, 600));
                stage.setTitle("Bunker-17");
            }
        });

        // Menu: Spelregels
        view.getSpelregelsItem().setOnAction(e -> {
            HelpschermView helpView = new HelpschermView();
            new HelpschermPresenter(helpView);
            Stage helpStage = new Stage();
            helpStage.setTitle("Spelregels");
            helpStage.setScene(new Scene(helpView, 600, 400));
            helpStage.show();
        });
    }

    private void beweegDoorGeselecteerdeDeur() {
        int index = view.getUitgangenLijst().getSelectionModel().getSelectedIndex();
        if (index < 0) {
            toonMelding("Selecteer eerst een uitgang.");
            return;
        }
        List<Door> deuren = game.getCurrentRoom().getExits();
        if (index >= deuren.size()) return;

        Door deur = deuren.get(index);
        if (deur.isLocked()) {
            toonMelding("Deze deur is op slot. Gebruik het juiste item.");
            return;
        }
        game.moveThroughDoor(deur);
        updateView();
        controleerWinConditie();
        controleerPuzzel();
    }

    private void controleerPuzzel() {
        String kamer = game.getCurrentRoom().getName();

        // Terminal puzzel in het Labo
        if (kamer.equals("Labo")) {
            Puzzle terminalPuzzel = game.getCurrentRoom().getPuzzelById("terminal_01");
            if (terminalPuzzel != null && !terminalPuzzel.isOpgelost()) {
                PuzzelschermView puzzelView = new PuzzelschermView();
                Stage puzzelStage = new Stage();
                puzzelStage.setTitle("Terminal");
                puzzelStage.setScene(new Scene(puzzelView, 450, 300));
                new PuzzelschermPresenter(game, puzzelStage, puzzelView, this::updateView);
                puzzelStage.show();
            }
        }

        // Zekeringkast puzzel in de Controlekamer
        if (kamer.equals("Controlekamer")) {
            Puzzle zekeringPuzzel = game.getCurrentRoom().getPuzzelById("zekering_01");
            if (zekeringPuzzel != null && !zekeringPuzzel.isOpgelost()) {
                if (game.getPlayer().hasItemById("Zekering_01")) {
                    ZekeringPuzzelView zekeringView = new ZekeringPuzzelView();
                    Stage zekeringStage = new Stage();
                    zekeringStage.setTitle("Zekeringkast");
                    zekeringStage.setScene(new Scene(zekeringView, 450, 320));
                    new ZekeringPuzzelPresenter(game, zekeringStage, zekeringView, this::updateView);
                    zekeringStage.show();
                } else {
                    toonMelding("Je hebt de Zekering nodig om de zekeringkast te activeren.");
                }
            }
        }
    }

    private void controleerWinConditie() {
        if (!game.getCurrentRoom().getName().equals(EINDKAMER_NAAM)) return;

        timerTimeline.stop();
        timer.pauze();
        game.win();

        int verstreken = timer.getVerstrekenSeconden();
        try {
            hsManager.voegScoreToe(game.getPlayer().getName(), verstreken, moeilijkheid);
        } catch (Exception e) {
            System.err.println("Score opslaan mislukt: " + e.getMessage());
        }

        WinschermView winView = new WinschermView();
        new WinschermPresenter(winView, stage, game.getPlayer().getName(),
                verstreken, hsManager, moeilijkheid);
        stage.setScene(new Scene(winView, 800, 600));
        stage.setTitle("Bunker-17 — Ontsnapt!");
    }

    private void controleerVerlies() {
        timerTimeline.stop();
        game.lose();

        VerliesschermView verliesView = new VerliesschermView();
        new VerliesschermPresenter(verliesView, stage,
                game.getPlayer().getName(), moeilijkheid);
        stage.setScene(new Scene(verliesView, 800, 500));
        stage.setTitle("Bunker-17 — Tijd verstreken");
    }

    private void startTimer() {
        timer.start();
        timerTimeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            timer.tick();
            updateTimerLabel();
            if (timer.isAfgelopen()) {
                controleerVerlies();
            }
        }));
        timerTimeline.setCycleCount(Timeline.INDEFINITE);
        timerTimeline.play();
    }

    private void updateTimerLabel() {
        int seconden = timer.getResterendeSeconden();
        int min = seconden / 60;
        int sec = seconden % 60;
        String tekst = String.format("%02d:%02d", min, sec);
        view.getTimerLabel().setText(tekst);

        // Rood als minder dan 60 seconden over
        if (seconden < 60) {
            view.getTimerLabel().setStyle(
                    "-fx-text-fill: #ff4444; -fx-font-family: monospace; -fx-font-size: 16px; -fx-font-weight: bold;");
        } else {
            view.getTimerLabel().setStyle(
                    "-fx-text-fill: #00ff41; -fx-font-family: monospace; -fx-font-size: 16px; -fx-font-weight: bold;");
        }
    }

    void updateView() {
        // Kamernaam en beschrijving
        view.getKamerNaamLabel().setText(game.getCurrentRoom().getName());
        view.getKamerBeschrijvingArea().setText(game.getCurrentRoom().getDescription());

        // Uitgangen
        view.getUitgangenLijst().getItems().clear();
        for (Door deur : game.getCurrentRoom().getExits()) {
            String slot = deur.isLocked() ? " [op slot]" : " [open]";
            String doelKamer = deur.getTargetRoom(game.getCurrentRoom()).getName();
            view.getUitgangenLijst().getItems().add("→ " + doelKamer + slot);
        }

        // Items in kamer
        view.getKamerItemsLijst().getItems().clear();
        for (Item item : game.getCurrentRoom().getItems()) {
            view.getKamerItemsLijst().getItems().add(item.getName());
        }

        // Inventory
        view.getInventoryLijst().getItems().clear();
        for (Item item : game.getPlayer().getInventory().getItems()) {
            view.getInventoryLijst().getItems().add(item.getName());
        }

        // Beschrijving resetten bij kamerwissel
        view.getItemBeschrijvingLabel().setText("Selecteer een item om de beschrijving te zien.");

        // Timer initieel vullen
        updateTimerLabel();
    }

    private void toonMelding(String bericht) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Let op");
        alert.setHeaderText(null);
        alert.setContentText(bericht);
        alert.showAndWait();
    }

    private void toonInfo(String bericht) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Actie");
        alert.setHeaderText(null);
        alert.setContentText(bericht);
        alert.showAndWait();
    }

    private boolean toonBevestigingsdialoog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Stoppen");
        alert.setHeaderText(null);
        alert.setContentText("Ben je zeker dat je wil stoppen? Je voortgang gaat verloren.");
        ButtonType ja = new ButtonType("Ja, stoppen");
        ButtonType annuleren = new ButtonType("Annuleren");
        alert.getButtonTypes().setAll(ja, annuleren);
        return alert.showAndWait().filter(r -> r == ja).isPresent();
    }

    private void addWindowEventHandlers() {
        view.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.windowProperty().addListener((obs2, oldWin, newWin) -> {
                    if (newWin != null) {
                        newWin.setOnCloseRequest(e -> {
                            if (toonBevestigingsdialoog()) {
                                timerTimeline.stop();
                                Platform.exit();
                            } else {
                                e.consume();
                            }
                        });
                    }
                });
            }
        });
    }
}
