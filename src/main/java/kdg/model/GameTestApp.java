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

        // Test V1.
//        // 1. Items maken
//        Item redKey = new Item("red_key", "Rode sleutel", "Een kleine rode sleutel.");
//
//        // 2. Kamers maken
//        Room cell = new Room("Cel", "Je wordt wakker in een donkere cel.");
//        Room hallway = new Room("Gang", "Een smalle, slecht verlichte gang.");
//
//        // 3. Item in kamer leggen
//        cell.addItem(redKey);
//
//        // 4. Deur maken
//        Door cellDoor = new Door(cell, hallway, true, "red_key");
//
//        // 5. Deur aan kamer toevoegen
//        cell.addExit(cellDoor);
//
//        // 6. Player maken
//        Player player = new Player("Borja");
//
//        // 7. Lijst van rooms maken
//        List<Room> rooms = new ArrayList<>();
//        rooms.add(cell);
//        rooms.add(hallway);
//
//        // 8. Game maken
//        Game game = new Game(player, rooms, cell);
//
//        // ===== TESTS =====
//
//        System.out.println("Current room: " + game.getCurrentRoom().getName());
//
//        System.out.println("Items in current room:");
//        for (Item item : game.getCurrentRoom().getItems()) {
//            System.out.println("- " + item.getName());
//        }
//
//        System.out.println("\nPlayer picks up red key...");
//        boolean pickedUp = game.pickupItem(redKey);
//        System.out.println("Pickup successful? " + pickedUp);
//
//        System.out.println("\nPlayer inventory:");
//        for (Item item : game.getPlayer().getInventory().getItems()) {
//            System.out.println("- " + item.getName());
//        }
//
//        System.out.println("\nTrying to unlock door with red key...");
//        boolean unlocked = game.useItemOnDoor(redKey, cellDoor);
//        System.out.println("Door unlocked? " + unlocked);
//
//        System.out.println("\nMoving through door...");
//        game.moveThroughDoor(cellDoor);
//        System.out.println("Current room after moving: " + game.getCurrentRoom().getName());


        // ============ Test klasse GameBuilder =================

        testPickupItem();
        testMoveThroughDoor();

    }

    // Test voor oppakken Items
    static void testPickupItem() {
        System.out.println("=== Test: pickupItem ===");

        // Arrange - item ophalen uit de huidige room
        Game game = GameBuilder.buildGame();
        Item zaklamp = game.getCurrentRoom().getItems().get(0); // zaklamp is eerste item in startkamer

        // Act & Assert - happy path
        boolean result = game.pickupItem(zaklamp);
        System.out.println(result ? "✅ item opgepakt" : "❌ item niet opgepakt");

        // Act & Assert - item al in inventory
        boolean result2 = game.pickupItem(zaklamp);
        System.out.println(!result2 ? "✅ duplicaat correct geweigerd" : "❌ duplicaat had moeten falen");

        // Act & Assert - null
        boolean result3 = game.pickupItem(null);
        System.out.println(!result3 ? "✅ null correct geweigerd" : "❌ null had moeten falen");

        // Act & Assert - item niet in huidige room
        Item foutItem = new Item("fout_01", "Fout Item", "Dit item zit niet in de room");
        boolean result4 = game.pickupItem(foutItem);
        System.out.println(!result4 ? "✅ item niet in room correct geweigerd" : "❌ had moeten falen");
    }

    static void testMoveThroughDoor() {
        System.out.println("=== Test: moveThroughDoor ===");

        // Arrange
        Game game = GameBuilder.buildGame();
        Door deur = game.getCurrentRoom().getExits().get(0); // eerste deur in startkamer -> gang

        // Act & Assert - happy path, deur is open
        boolean result = game.moveThroughDoor(deur); // naar gang
        System.out.println(result ? "✅ door deur gegaan" : "❌ had moeten werken");

        // Act & Assert - terugkeren via zelfde deur (bidirectioneel)
        boolean result2 = game.moveThroughDoor(deur); // terug naar beginkamer
        System.out.println(result2 ? "✅ terugkeer gelukt" : "❌ terugkeer had moeten werken");

        // Act & Assert - null
        boolean result3 = game.moveThroughDoor(null);
        System.out.println(!result3 ? "✅ null correct geweigerd" : "❌ null had moeten falen");

        // Act & Assert - gesloten deur
        game.moveThroughDoor(deur); // naar gang bewegen
        Door geslotenDeur = game.getCurrentRoom().getExits().get(0); // eerste deur in gang -> opslag, op slot
        boolean result4 = game.moveThroughDoor(geslotenDeur);
        System.out.println(!result4 ? "✅ gesloten deur correct geweigerd" : "❌ gesloten deur had moeten falen");

        System.out.println("Huidige kamer voor terugkeer: " + game.getCurrentRoom().getName());
        System.out.println("Exits in huidige kamer: " + game.getCurrentRoom().getExits().size());
        System.out.println("Deur in exits: " + game.getCurrentRoom().getExits().contains(deur));

    }
}