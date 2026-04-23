package kdg.model;

/**
 * @author Farok
 * @version 1.0 23/04/2026
 */
public class Puzzle {
    private String puzzelId;
    private String correctWachtwoord;
    private boolean opgelost;

    public Puzzle(String puzzelId, String correctWachtwoord) {
        if (puzzelId == null) throw new IllegalArgumentException("puzzelId mag niet null zijn");
        if (correctWachtwoord == null) throw new IllegalArgumentException("correctWachtwoord mag niet null zijn");
        this.puzzelId = puzzelId;
        this.correctWachtwoord = correctWachtwoord;
        this.opgelost = false;
    }

    public boolean probeerOplossen(String poging) {
        if (opgelost) return true;
        if (poging != null) {
            String genormaliseerd = poging.trim().replace("-", "").replace(" ", "");
            String correct = correctWachtwoord.replace("-", "").replace(" ", "");
            if (genormaliseerd.equalsIgnoreCase(correct)) {
                opgelost = true;
                return true;
            }
        }
        return false;
    }

    public boolean isOpgelost() {
        return opgelost;
    }

    public String getPuzzelId() {
        return puzzelId;
    }
}
