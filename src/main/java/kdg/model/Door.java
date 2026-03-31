package kdg.model;

/**
 * @author Borja
 * @version 1.0 28/03/2026 14:36
 *
 */
public class Door {
    // Attributen klasse
    private Room fromRoom;
    private Room toRoom;
    private boolean locked;
    private String requiredItemId;

    // Constructor
    public Door(Room fromRoom, Room toRoom, boolean isLocked, String requiredItemId) {
        this.fromRoom = fromRoom;
        this.toRoom = toRoom;
        this.locked = isLocked;
        this.requiredItemId = requiredItemId;
    }

    // Is deur locked of niet
    public boolean isLocked() {
        return locked;
    }

    // Deur openen met Item
    public boolean unlock(String itemId) {
        // requiredItemId != null -> nagaan of deur wel een sleutel nodig heeft. voorkomt crash
        // isLocked -> checken of de deur nog op slot is
        if (locked && requiredItemId != null && requiredItemId.equals(itemId)) {
            locked = false;
            return true;
        }
        return false;
    }

    // volgende room opvragen
    Room getTargetRoom() {
        return toRoom;
    }

}
