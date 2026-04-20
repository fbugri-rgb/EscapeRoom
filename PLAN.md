# PLAN.md — Bunker-17 Escape Room

Java 21 + JavaFX 21 | MVP-patroon | KdG 2025-2026
Studenten: **Borja Cools** (model) · **Farok Bugri** (view/GUI)

---

## 1. Overzicht schermen

| Scherm | Klasse | Functie |
|---|---|---|
| **Startscherm** | `startscherm/` | Welkomstpagina, spelernaam invoeren, navigatie naar spel/help/highscore/about |
| **Spelscherm** | `spelscherm/` | Kern van het spel: huidige kamer, items, deuren, inventory, timer |
| **Helpscherm** | `helpscherm/` | Spelregels en uitleg gelezen uit een bestand (`spelregels.txt`) |
| **Highscorescherm** | `highscorescherm/` | Top-scores inlezen uit en schrijven naar bestand (`highscores.txt`) |
| **Aboutscherm** | `aboutscherm/` | Info over de makers, versie, opleidingsinstelling |

Navigatie:
- Startscherm → Spelscherm (nieuwe Scene op hetzelfde Stage)
- Startscherm → Help/Highscore/About (apart Stage/popup-venster)
- Spelscherm → Highscorescherm na winnen/verliezen

---

## 2. Package- en klassenstructuur

```
kdg/
├── Main.java                          ← JavaFX Application, start primary Stage
│
├── model/
│   ├── Game.java                      ← DONE  facade: pickupItem, moveThroughDoor, useItemOnDoor
│   ├── GameBuilder.java               ← DONE  fabriek die volledig bedraad Game object maakt
│   ├── Player.java                    ← DONE  naam + inventory
│   ├── Inventory.java                 ← DONE  itemlijst met add/remove/getById
│   ├── Room.java                      ← DONE  naam, beschrijving, items, deuren
│   ├── Door.java                      ← DONE  bidirectioneel, lock/unlock via itemId
│   ├── Item.java                      ← DONE  id, naam, beschrijving
│   ├── Interactable.java              ← DONE  interface (nog niet geïmplementeerd)
│   ├── Timer.java                     ← TODO  countdown-timer, callback bij afloop
│   ├── Puzzle.java                    ← TODO  optionele puzzellogica (wachtwoord, code)
│   └── HighscoreManager.java          ← TODO  lees/schrijf highscores naar highscores.txt
│
└── view/
    ├── startscherm/
    │   ├── StartschermView.java       ← TODO
    │   └── StartschermPresenter.java  ← TODO
    ├── spelscherm/
    │   ├── SpelschermView.java        ← TODO
    │   └── SpelschermPresenter.java   ← TODO
    ├── helpscherm/
    │   ├── HelpschermView.java        ← TODO
    │   └── HelpschermPresenter.java   ← TODO
    ├── highscorescherm/
    │   ├── HighscoreschermView.java   ← TODO
    │   └── HighscoreschermPresenter.java ← TODO
    └── aboutscherm/
        ├── AboutschermView.java       ← TODO
        └── AboutschermPresenter.java  ← TODO
```

### Bestanden (resources)
```
src/main/resources/
├── spelregels.txt      ← gelezen door HelpschermPresenter
└── highscores.txt      ← gelezen én geschreven door HighscoreManager
```

---

## 3. Taakverdeling

### Borja Cools — Model

| Taak | Status |
|---|---|
| `Game`, `Player`, `Inventory`, `Room`, `Door`, `Item` | DONE |
| `GameBuilder` met volledig scenario | DONE |
| `GameTestApp` (manuele tests) | DONE |
| `Timer.java` — countdown met callback | TODO |
| `Puzzle.java` — wachtwoord-/codepuzzel | TODO |
| `HighscoreManager.java` — bestandsI/O | TODO |
| JUnit 5 testklassen in `src/test/java/kdg/` | TODO |
| Public getters toevoegen aan `Game` zodat Presenter data kan ophalen | TODO |

### Farok Bugri — View / GUI

| Taak | Status |
|---|---|
| `Main.java` activeren en primary Stage opzetten | TODO |
| `StartschermView` + `StartschermPresenter` | TODO |
| `SpelschermView` + `SpelschermPresenter` | TODO |
| `HelpschermView` + `HelpschermPresenter` | TODO |
| `HighscoreschermView` + `HighscoreschermPresenter` | TODO |
| `AboutschermView` + `AboutschermPresenter` | TODO |
| Menubalk (`MenuBar`) bovenaan in elk scherm | TODO |
| Scene-wissel (Startscherm → Spelscherm) | TODO |
| Nieuw Stage openen (bv. Help of About als popup) | TODO |
| `spelregels.txt` aanmaken en inlezen in helpscherm | TODO |
| CSS-bestand voor stijl (optioneel maar netjes) | TODO |

---

## 4. Implementatievolgorde

### Fase 1 — Fundament (model klaar maken)
1. **`Timer.java`** — het spel heeft een countdown nodig; presenter kan er pas op binden als het bestaat
2. **Public getters op `Game`** — `getCurrentRoom()`, `getPlayer()`, `getRooms()` moeten public zijn zodat presenters ze kunnen aanroepen
3. **`HighscoreManager.java`** — bestandsI/O isoleren in model vóór de view het nodig heeft

*Waarom eerst?* De view-klassen zijn afhankelijk van het model. Presenters kunnen pas werken als het model compleet en stabiel is.

### Fase 2 — Skelet GUI opzetten
4. **`Main.java`** — decommentarieer en zet primary Stage op met een lege startscene
5. **`StartschermView` + `StartschermPresenter`** — eerste werkend scherm, spelernaam invoeren
6. **Scene-wissel** naar spelscherm (ook al is spelscherm nog leeg)

*Waarom?* Vroeg kunnen runnen geeft vertrouwen en maakt testen van navigatie mogelijk.

### Fase 3 — Spelscherm (kern)
7. **`SpelschermView`** — kameromschrijving, itemlijst, deurknoppen, inventory, timer-label
8. **`SpelschermPresenter`** — koppelt `Game`-acties aan knoppen, update view na elke actie
9. **Menubalk** toevoegen aan spelscherm

*Waarom?* Dit is het zwaarste scherm en vereist het meeste model-view-samenwerking.

### Fase 4 — Secundaire schermen
10. **`HelpschermView` + `HelpschermPresenter`** — lees `spelregels.txt`, toon in TextArea
11. **`HighscoreschermView` + `HighscoreschermPresenter`** — toon lijst, schrijf score na afloop
12. **`AboutschermView` + `AboutschermPresenter`** — statische informatie

### Fase 5 — Afwerking
13. JUnit 5 testklassen schrijven (Borja)
14. CSS-styling verfijnen
15. Crashtesten: elk null-geval, elk foutpad doorlopen
16. Code review: MVP-scheiding controleren (geen JavaFX in model, geen logica in view)

---

## 5. Evaluatiecriteria — checklist

### Schermen
| Criterium | Status |
|---|---|
| Startscherm | TODO |
| Spelscherm | TODO |
| Helpscherm | TODO |
| Highscorescherm | TODO |
| Aboutscherm | TODO |

### JavaFX controls (min. 5 verschillende)
| Control | Gebruikt in | Status |
|---|---|---|
| `Button` | Spelscherm (deuren, items) | TODO |
| `Label` | Alle schermen | TODO |
| `TextField` | Startscherm (spelernaam) | TODO |
| `TextArea` | Helpscherm (spelregels) | TODO |
| `ListView` | Highscorescherm, spelscherm (inventory) | TODO |
| `MenuBar` / `MenuItem` | Alle schermen (menubalk) | TODO |

### Events (min. 3 verschillende)
| Event | Waar | Status |
|---|---|---|
| `ActionEvent` (klikken op Button) | Spelscherm, startscherm | TODO |
| `KeyEvent` | Spelscherm (toetsenbord navigatie) | TODO |
| `WindowEvent` (sluiten venster) | Alle Stages | TODO |

### Layout panes (min. 3 verschillende)
| Pane | Gebruikt in | Status |
|---|---|---|
| `BorderPane` | Spelscherm (menu top, spel center) | TODO |
| `VBox` / `HBox` | Knoppen, lijsten | TODO |
| `GridPane` | Startscherm of highscorescherm | TODO |

### Overige vereisten
| Criterium | Status |
|---|---|
| 1x van Scene wisselen (Startscherm → Spelscherm) | TODO |
| 1x van Stage wisselen (popup Help of About) | TODO |
| Menubalk bovenaan | TODO |
| Gegevens lezen uit bestand (`spelregels.txt`) | TODO |
| Gegevens schrijven naar bestand (`highscores.txt`) | TODO |
| Applicatie mag NOOIT crashen | TODO |

---

## 6. Risico's en aandachtspunten

| Risico | Kans | Aanpak |
|---|---|---|
| **Model-getters zijn package-private** — Presenter in `kdg.view.*` kan `game.getCurrentRoom()` niet aanroepen | Zeker probleem | Borja maakt de nodige getters public vóór Farok de presenters schrijft |
| **MVP-scheiding bewaken** — verleidelijk om logica in de View te zetten | Gemiddeld | Regel: als iets `Game` aanroept, hoort het in de Presenter |
| **Bestandspaden** — `spelregels.txt` en `highscores.txt` moeten vindbaar zijn bij runtime | Gemiddeld | Gebruik `getClass().getResourceAsStream()` voor lezen; absoluut/relatief pad voor schrijven |
| **Timer op JavaFX thread** — `Timeline` of `AnimationTimer` gebruiken, niet `Thread.sleep` | Gemiddeld | `Timer.java` geeft callbacks; Presenter registreert zich en update label op FX-thread |
| **Applicatie crasht op null** — `Game`-methodes geven `false` terug maar gooien geen exceptions | Laag | Presenter controleert returnwaarde en toont foutmelding in de UI, nooit een crash |
| **Git-conflicten** — model en view in aparte packages, maar `Main.java` is gedeeld | Laag | Borja raakt `Main.java` niet aan; Farok is eigenaar |
