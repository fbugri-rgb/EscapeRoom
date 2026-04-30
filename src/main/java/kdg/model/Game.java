package kdg.model;

import java.util.List;
import java.util.Optional;

/**
 * @author Borja
 * @version 1.0 28/03/2026 15:03
 *
 */
public class Game {
    // Attributen
    private Player player;
    private List<Room> rooms;
    private Room currentRoom;
    private List<Item> verborgenItems;
    private DifficultyLevel moeilijkheid = DifficultyLevel.NORMAAL;

    // Constructor
    public Game(Player player, List<Room> rooms, Room currentRoom, List<Item> verborgenItems) {
        // Checks
        if (player == null) {
            throw new IllegalArgumentException("player mag niet null zijn");
        }
        if (rooms == null) {
            throw new IllegalArgumentException("rooms mag niet null zijn");
        }
        if (currentRoom == null) {
            throw new IllegalArgumentException("currentRoom mag niet null zijn");
        }
        if (!rooms.contains(currentRoom)) {
            throw new IllegalArgumentException("currentRoom moet in rooms zitten");
        }

        this.player = player;
        this.rooms = rooms;
        this.currentRoom = currentRoom;
        this.verborgenItems = verborgenItems != null ? verborgenItems : List.of();
    }

    // Methodes voor het spel te delegeren
    public void start(){}
    public void pause(){}
    public void resume(){}
    public void stop(){}
    public void win(){}
    public void lose(){}

    // Deur die de kamers verbindt gebruiken -> volgende room
    public boolean moveThroughDoor(Door door){
        if (door == null) return false;
        if (door.isLocked()) return false;
        if (currentRoom.getExits().contains(door)) {
            this.currentRoom = door.getTargetRoom(currentRoom);
            return true;
        }
        return false;
    }

    // Item van de room oppakken
    public boolean pickupItem(Item item){
        // check
        if(item == null) return false;
        // check of speler al item heeft
        if(player.hasItem(item)) return false;
        // check inventory capaciteit
        if(player.getInventory().getItems().size() >= player.getInventory().getMaxItems()) return false;

        // Item uit room halen en inventory steken van speler
        if(currentRoom.getItems().contains(item)){
            player.pickUpItem(item);
            currentRoom.removeItem(item);
            return true;
        }
        return false;
    }

    // Item op een deur gebruiken
    public boolean useItemOnDoor(Item item, Door door){
        // Check input geldig
        if(item == null || door == null) return false;
        // Check of player item heeft
        if(!player.hasItem(item)) return false;
        // proberen deur te openen en returnen of het gelukt is
        return door.unlock(item.getId());
    }

    // Zekeringpuzzel oplossen: sleutel verschijnt in de kamer
    public boolean losZekeringPuzzelOp(String puzzelId, String poging) {
        Puzzle puzzel = currentRoom.getPuzzelById(puzzelId);
        if (puzzel == null) return false;
        boolean opgelost = puzzel.probeerOplossen(poging);
        if (opgelost) {
            Optional<Item> sleutel = verborgenItems.stream()
                    .filter(item -> item.getId().equals("Sleutel_01"))
                    .findFirst();
            sleutel.ifPresent(currentRoom::addItem);
        }
        return opgelost;
    }

    // Terminalpuzzel oplossen en bijbehorende deur ontgrendelen
    public boolean losTerminalPuzzelOp(String puzzelId, String poging) {
        Puzzle puzzel = currentRoom.getPuzzelById(puzzelId);
        if (puzzel == null) return false;
        boolean opgelost = puzzel.probeerOplossen(poging);
        if (opgelost) {
            for (Door deur : currentRoom.getExits()) {
                deur.unlock(puzzelId);
            }
        }
        return opgelost;
    }

    // Moeilijkheid instellen en inventory aanpassen
    public void setMoeilijkheid(DifficultyLevel level) {
        this.moeilijkheid = level;
        this.player.getInventory().setMaxItems(level.getMaxItems());
    }

    // Getters voor attributen klasse
    public Room getCurrentRoom() {
        return currentRoom;
    }

    public Player getPlayer() {
        return player;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public DifficultyLevel getMoeilijkheid() {
        return moeilijkheid;
    }
}
