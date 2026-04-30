package kdg.model;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Map;

/**
 * Singleton die alle geluidseffecten beheert via JavaFX MediaPlayer.
 * Audio-bestanden worden verwacht in src/main/resources/audio/*.mp3.
 * Ontbrekende bestanden worden stilzwijgend overgeslagen.
 *
 * @author Farok
 * @version 1.0 01/05/2026
 */
public class GeluidManager {

    private static GeluidManager instance;
    private final Map<String, MediaPlayer> spelers = new HashMap<>();

    private GeluidManager() {}

    public static GeluidManager getInstance() {
        if (instance == null) {
            instance = new GeluidManager();
        }
        return instance;
    }

    public void laadGeluiden() {
        String[] namen = {
            "achtergrond", "pickup", "neerleggen",
            "deur_open", "deur_bang", "sleutel",
            "zekering", "puzzel_correct", "puzzel_fout",
            "win", "verlies"
        };
        for (String naam : namen) {
            try {
                Media media = new Media(
                        getClass().getResource("/audio/" + naam + ".mp3").toExternalForm());
                spelers.put(naam, new MediaPlayer(media));
            } catch (NullPointerException e) {
                // bestand ontbreekt — overslaan
            } catch (Exception e) {
                System.err.println("Geluid kon niet worden geladen: " + naam + " — " + e.getMessage());
            }
        }
    }

    public void speel(String naam) {
        MediaPlayer player = spelers.get(naam);
        if (player != null) {
            player.stop();
            player.seek(Duration.ZERO);
            player.play();
        }
    }

    public void speelLoop(String naam) {
        MediaPlayer player = spelers.get(naam);
        if (player != null) {
            player.setCycleCount(MediaPlayer.INDEFINITE);
            player.play();
        }
    }

    public void stop(String naam) {
        MediaPlayer player = spelers.get(naam);
        if (player != null) {
            player.stop();
        }
    }

    public void stopAlles() {
        spelers.values().forEach(MediaPlayer::stop);
    }

    public void setVolume(double volume) {
        double vol = Math.max(0.0, Math.min(1.0, volume));
        spelers.forEach((naam, player) -> {
            if (naam.equals("achtergrond")) {
                player.setVolume(vol * 0.4);
            } else {
                player.setVolume(vol);
            }
        });
    }
}
