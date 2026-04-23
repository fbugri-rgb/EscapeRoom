package kdg.model;

import java.util.List;

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

    // Constructor
    public Game(Player player, List<Room> rooms, Room currentRoom) {
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
    }

    public Game(){}

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
        if(player.getInventory().getItems().size() >= Inventory.MAX_ITEMS) return false;

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
            Item sleutel = new Item("Sleutel_01", "Sleutel",
                    "Een zware metalen sleutel. Past op de bunkerdeur naar de uitgang.");
            currentRoom.addItem(sleutel);
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
}
