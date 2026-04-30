package kdg.model;

/**
 * @author Borja
 * @version 1.0 04/02/2026 20:29
 *
 */
public class Item implements Interactable {
    private final String id;
    private final String name;
    private final String description;

    public Item(String id, String name, String description) {
        // Check
        if (id == null) {
            throw new IllegalArgumentException("id mag niet null zijn");
        }
        if (name == null) {
            throw new IllegalArgumentException("name mag niet null zijn");
        }

        this.id = id;
        this.name = name;
        this.description = description;
    }

    @Override
    public boolean Interact(Player player) {
        return true;
    }

    @Override
    public String inspect() {
        return description;
    }

    // Getter voor item
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
}
