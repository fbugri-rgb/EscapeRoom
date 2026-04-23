package kdg.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Borja
 * @version 1.0 11/04/2026 13:36
 *
 */
public class GameBuilder {
    public static Game buildGame() {
        return buildGame("Speler");
    }

    public static Game buildGame(String spelerNaam) {
        // ----- Items ------
        Item zaklamp  = new Item("Zaklamp_01",  "Zaklamp",  "Een oude zaklamp, heeft batterijen nodig om licht te geven.");
        Item batterij = new Item("Batterij_01", "Batterij", "Een AA batterij.");
        Item notitie  = new Item("Notitie_01",  "Notitie",  "Een verkreukeld papier. Er staat op: 'Toegangscode terminal: naam bunker + bouwjaar (19__). Herstel de stroom via de zekeringkast in de controlekamer. De code vind je op het gereedschap.'");
        Item keykard  = new Item("Keykard_01",  "Keykard",  "De keycard van professor Cools, geeft toegang tot het laboratorium.");
        Item zekering         = new Item("Zekering_01",         "Zekering",         "Een afgeslagen zekering. De stroom kan worden hersteld door de zekering terug te plaatsen.");
        Item schroevendraaier = new Item("Schroevendraaier_01", "Schroevendraaier", "Een oude schroevendraaier. Er is een code op het handvat gegraveerd: 4-7-9");
        Item sleutel          = new Item("Sleutel_01",          "Sleutel",          "Een zware metalen sleutel. Past op de bunkerdeur naar de uitgang.");

        // ------ Rooms ------
        Room beginKamer   = new Room("BeginKamer",   "Je wordt wakker op een koude betonvloer. Rood noodlicht flikkert aan het plafond. Een metalen deur staat op een kier.");
        Room gang         = new Room("Gang",         "Een lange industriële gang. Buizen lopen langs het plafond. Twee deuren, beide op slot. Ergens druppelt water.");
        Room opslagruimte = new Room("Opslagruimte", "Hoge rekken vol materiaal. Stof en roest overal. Misschien ligt hier iets nuttigs tussen het gereedschap.");
        Room labo         = new Room("Labo",         "Computerschermen flikkeren in het donker. Een terminal knippert: WACHTWOORD VEREIST. Een deur achteraan is afgesloten.");
        Room controlekamer = new Room("Controlekamer", "De centrale computer staat hier. Een zekeringkast aan de muur is open. Als je de stroom herstelt verschijnt misschien een toegangscode op het scherm.");
        Room eindkamer    = new Room("Eindkamer",    "Een zware stalen bunkerdeur. Daglicht sijpelt door de kieren. Je hoort sirenes in de verte. VRIJHEID!");

        // ------ Items in Rooms ------
        beginKamer.addItem(zaklamp);
        beginKamer.addItem(batterij);

        opslagruimte.addItem(notitie);
        opslagruimte.addItem(keykard);

        controlekamer.addItem(zekering);
        controlekamer.addItem(schroevendraaier);

        // ------ Puzzels ------
        Puzzle terminalPuzzel = new Puzzle("terminal_01", "BUNKER17");
        labo.addPuzzel(terminalPuzzel);

        Puzzle zekeringPuzzel = new Puzzle("zekering_01", "479");
        controlekamer.addPuzzel(zekeringPuzzel);

        // ------ Doors ------
        Door startNaarGang    = new Door(beginKamer,    gang,         false, null);
        Door gangNaarOpslag   = new Door(gang,          opslagruimte, true,  zaklamp.getId());
        Door gangNaarLabo     = new Door(gang,          labo,         true,  keykard.getId());
        Door laboNaarControle = new Door(labo,          controlekamer, true, "terminal_01");
        Door controleNaarEinde = new Door(controlekamer, eindkamer,   true,  "Sleutel_01");

        // ------ Exits ------
        beginKamer.addExit(startNaarGang);

        gang.addExit(gangNaarOpslag);
        gang.addExit(gangNaarLabo);
        gang.addExit(startNaarGang);

        opslagruimte.addExit(gangNaarOpslag);

        labo.addExit(laboNaarControle);
        labo.addExit(gangNaarLabo);

        controlekamer.addExit(controleNaarEinde);
        controlekamer.addExit(laboNaarControle);

        // ------ Game ------
        Player player = new Player(spelerNaam);
        List<Room> rooms = List.of(beginKamer, gang, opslagruimte, controlekamer, labo, eindkamer);

        List<Item> verborgenItems = new ArrayList<>();
        verborgenItems.add(sleutel);

        return new Game(player, rooms, beginKamer, verborgenItems);
    }
}
