package kdg.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Borja
 * @version 1.0 28/03/2026 14:25
 *
 */
public class Room {
    // Attributen
    private String name;
    private String description;
    private List<Item> roomItems;
    private List<Door> exits;

    // Constructor
    public Room(String name, String description) {
        // Check
        if (name == null) {
            throw new IllegalArgumentException("name mag niet null zijn");
        }
        this.name = name;
        this.description = description;
        // initialiseren van lijsten voor geen nullPointerException te krijgen.
        this.roomItems = new ArrayList<>();
        this.exits = new ArrayList<>();
    }

    // Items toevoegen aan Room
    public void addItem(Item item) {
        if (item != null) {
            this.roomItems.add(item);
        }
    }

    // Items uit room halen (wanneer deze zijn opgepakt)
    public boolean removeItem(Item item) {
        return this.roomItems.remove(item);
    }

    // getter voor de items in de Room
    // new arraylist zodat de oorspronkelijke niet kan worden aangepast van buitenaf.
    public List<Item> getItems() {
        return new ArrayList<>(this.roomItems);
    }

    // uitgang van de room toevoegen
    public void addExit(Door door){
        if (door != null) {
            this.exits.add(door);
        }
    }

    // Uitgangen die aan de room zijn verbonden opvragen
    public List<Door> getExits(){
        return new ArrayList<>(this.exits);
    }

    // Getters attributen
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return Objects.equals(getName(), room.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getName());
    }

    @Override
    public String toString() {
        return "room: " + getName();
    }
}
