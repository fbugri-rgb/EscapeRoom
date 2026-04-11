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
    public Door(Room fromRoom, Room toRoom, boolean locked, String requiredItemId) {
        // Check
        if (fromRoom == null) {
            throw new IllegalArgumentException("fromRoom mag niet null zijn");
        }
        if (toRoom == null) {
            throw new IllegalArgumentException("toRoom mag niet null zijn");
        }

        this.fromRoom = fromRoom;
        this.toRoom = toRoom;
        this.locked = locked;
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
    Room getTargetRoom(Room currentRoom) {
        // bidirectioneel -
        if (currentRoom.equals(fromRoom)) return toRoom;
        if (currentRoom.equals(toRoom)) return fromRoom;
        throw new IllegalArgumentException("CurrentRoom hoort niet bij deze deur");
    }

}
