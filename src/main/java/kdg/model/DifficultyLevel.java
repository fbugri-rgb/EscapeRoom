package kdg.model;

/**
 * @author Farok
 * @version 1.0 30/04/2026
 */
public enum DifficultyLevel {
    MAKKELIJK("Makkelijk", 900, 2),
    NORMAAL("Normaal",     600, 1),
    MOEILIJK("Moeilijk",   300, 1);

    private final String label;
    private final int tijdInSeconden;
    private final int maxItems;

    DifficultyLevel(String label, int tijdInSeconden, int maxItems) {
        this.label          = label;
        this.tijdInSeconden = tijdInSeconden;
        this.maxItems       = maxItems;
    }

    public String getLabel()          { return label; }
    public int getTijdInSeconden()    { return tijdInSeconden; }
    public int getMaxItems()          { return maxItems; }
}
