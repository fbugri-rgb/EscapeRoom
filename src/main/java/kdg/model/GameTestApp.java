package kdg.model;

/**
 * @author Borja
 * @version 1.0 29/03/2026 16:51
 *
 */
import java.util.ArrayList;
import java.util.List;

public class GameTestApp {
    public static void main(String[] args) {
        // 1. Items maken
        Item redKey = new Item("red_key", "Rode sleutel", "Een kleine rode sleutel.");

        // 2. Kamers maken
        Room cell = new Room("Cel", "Je wordt wakker in een donkere cel.");
        Room hallway = new Room("Gang", "Een smalle, slecht verlichte gang.");

        // 3. Item in kamer leggen
        cell.addItem(redKey);

        // 4. Deur maken
        Door cellDoor = new Door(cell, hallway, true, "red_key");

        // 5. Deur aan kamer toevoegen
        cell.addExit(cellDoor);

        // 6. Player maken
        Player player = new Player("Borja");

        // 7. Lijst van rooms maken
        List<Room> rooms = new ArrayList<>();
        rooms.add(cell);
        rooms.add(hallway);

        // 8. Game maken
        Game game = new Game(player, rooms, cell);

        // ===== TESTS =====

        System.out.println("Current room: " + game.getCurrentRoom().getName());

        System.out.println("Items in current room:");
        for (Item item : game.getCurrentRoom().getItems()) {
            System.out.println("- " + item.getName());
        }

        System.out.println("\nPlayer picks up red key...");
        boolean pickedUp = game.pickupItem(redKey);
        System.out.println("Pickup successful? " + pickedUp);

        System.out.println("\nPlayer inventory:");
        for (Item item : game.getPlayer().getInventory().getItems()) {
            System.out.println("- " + item.getName());
        }

        System.out.println("\nTrying to unlock door with red key...");
        boolean unlocked = game.useItemOnDoor(redKey, cellDoor);
        System.out.println("Door unlocked? " + unlocked);

        System.out.println("\nMoving through door...");
        game.moveThroughDoor(cellDoor);
        System.out.println("Current room after moving: " + game.getCurrentRoom().getName());
    }
}