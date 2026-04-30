# PLAN.md — Bunker-17 Escape Room

Java 21 + JavaFX 21 | MVP-patroon | KdG 2025-2026
Studenten: **Borja Cools** (model) · **Farok Bugri** (view/GUI)

---

## Huidige status (2026-04-30)

### Wat werkt
- Volledig speelbaar spel van begin tot einde via onderstaand speelpad
- Alle 5 vereiste schermen geïmplementeerd (start, spel, help, highscore, about)
- Twee puzzels als popup-venster:
  - **Terminal** (Labo): wachtwoord `BUNKER17` → deur naar Controlekamer opent
  - **Zekeringkast** (Controlekamer): code `4-7-9` (gegraveerd op schroevendraaier) → Sleutel verschijnt in kamer
- Inventory limiet van 1 item; "Neerleggen"-knop om items te wisselen
- Items oppakken en gebruiken op deuren
- Deurnavigatie met knop en dubbelklik; slot-melding bij vergrendelde deuren
- Item beschrijvingspaneel onderaan (toont `getDescription()` bij selectie)
- Timer (countdown 10 min) met rood-markering onder 60 seconden
- Winconditie: score opslaan in `highscores.txt`, highscorescherm openen
- Terugkeer naar startscherm via Spel → Stoppen (bevestigingsdialoog)
- X-knop: bevestigingsdialoog spelscherm (annuleren houdt open), eenvoudig op startscherm
- MenuBar groen zichtbaar met hover via `css/stijl.css`
- Exception-audit uitgevoerd: alle stille catch-blokken gefixt
  - `HelpschermPresenter`: fallback-tekst met basisregels bij ontbrekend/onleesbaar bestand
  - `HighscoreManager`: `scores.clear()` bij leesfouten; `RuntimeException` bij schrijffouten
  - `SpelschermPresenter`: `voegScoreToe()` omwikkeld in try-catch — crash bij schrijffout onmogelijk

### Speelpad
```
BeginKamer  → zaklamp + batterij oppakken
  ↓ gangNaarOpslag (vereist zaklamp)
Opslagruimte → notitie lezen (hint: BUNKER17 + "code op gereedschap")
               keykard oppakken
  ↓ gangNaarLabo (vereist keykard)
Labo        → terminalPuzzel: "BUNKER17" → deur ontgrendelt
  ↓ laboNaarControle (vereist terminal_01)
Controlekamer → schroevendraaier inspecteren (beschrijving: "4-7-9")
                zekering oppakken → zekeringPuzzel: "4-7-9" → Sleutel_01 verschijnt
                sleutel oppakken
  ↓ controleNaarEinde (vereist Sleutel_01)
Eindkamer   → winconditie + highscore opslaan
```

### Nog open
- JUnit 5 testklassen schrijven (Borja)
- `highscores.txt` toevoegen aan `.gitignore`
- GitHub push-rechten: `fbugri-rgb` heeft nog geen collaborator-toegang tot `Borja-cools/EscapeRoom`
  (workaround: push naar fork `fbugri-rgb/EscapeRoom`)

---

## 1. Overzicht schermen

| Scherm | Klasse | Functie |
|---|---|---|
| **Startscherm** | `startscherm/` | Welkomstpagina, spelernaam invoeren, navigatie naar spel/help/highscore/about |
| **Spelscherm** | `spelscherm/` | Kern van het spel: kamer, items, deuren, inventory, timer, puzzels |
| **Helpscherm** | `helpscherm/` | Spelregels gelezen uit `spelregels.txt` |
| **Highscorescherm** | `highscorescherm/` | Top-scores lezen uit en schrijven naar `highscores.txt` |
| **Aboutscherm** | `aboutscherm/` | Info over de makers, versie, opleidingsinstelling |

Navigatie:
- Startscherm → Spelscherm (nieuwe Scene op hetzelfde Stage)
- Startscherm → Help/Highscore/About (apart Stage/popup)
- Spelscherm → Startscherm via Stoppen-menu (scene-wissel terug)
- Spelscherm → Puzzelscherm (Terminal / Zekeringkast) als popup Stage
- Spelscherm → Highscorescherm na winnen

---

## 2. Package- en klassenstructuur

```
kdg/
├── Main.java                          ← DONE  JavaFX Application, start primary Stage
│
├── model/
│   ├── Game.java                      ← DONE  pickupItem, moveThroughDoor, useItemOnDoor,
│   │                                          losTerminalPuzzelOp, losZekeringPuzzelOp
│   ├── GameBuilder.java               ← DONE  volledig bedraad scenario met 2 puzzels
│   ├── Player.java                    ← DONE  naam + inventory
│   ├── Inventory.java                 ← DONE  MAX_ITEMS=1, add/remove/getById
│   ├── Room.java                      ← DONE  naam, beschrijving, items, deuren, puzzels
│   ├── Door.java                      ← DONE  bidirectioneel, lock/unlock via itemId
│   ├── Item.java                      ← DONE  id, naam, beschrijving
│   ├── Puzzle.java                    ← DONE  probeerOplossen() (trim + case-insensitive)
│   ├── Interactable.java              ← DONE  interface (stub)
│   ├── Timer.java                     ← DONE  countdown-timer
│   └── HighscoreManager.java          ← DONE  lees/schrijf highscores.txt
│
└── view/
    ├── startscherm/
    │   ├── StartschermView.java       ← DONE
    │   └── StartschermPresenter.java  ← DONE
    ├── spelscherm/
    │   ├── SpelschermView.java        ← DONE
    │   └── SpelschermPresenter.java   ← DONE
    ├── puzzelscherm/
    │   ├── PuzzelschermView.java      ← DONE  terminal popup (TextField, feedback)
    │   ├── PuzzelschermPresenter.java ← DONE
    │   ├── ZekeringPuzzelView.java    ← DONE  zekering popup (TextField, hint-label)
    │   └── ZekeringPuzzelPresenter.java ← DONE
    ├── helpscherm/
    │   ├── HelpschermView.java        ← DONE
    │   └── HelpschermPresenter.java   ← DONE
    ├── highscorescherm/
    │   ├── HighscoreschermView.java   ← DONE
    │   └── HighscoreschermPresenter.java ← DONE
    └── aboutscherm/
        ├── AboutschermView.java       ← DONE
        └── AboutschermPresenter.java  ← DONE
```

### Bestanden (resources)
```
src/main/resources/
├── spelregels.txt      ← DONE  gelezen door HelpschermPresenter
├── highscores.txt      ← runtime  gelezen én geschreven door HighscoreManager
└── css/
    └── stijl.css       ← DONE  MenuBar en context-menu stijl
```

---

## 3. Taakverdeling

### Borja Cools — Model

| Taak | Status |
|---|---|
| `Game`, `Player`, `Inventory`, `Room`, `Door`, `Item` | DONE |
| `GameBuilder` met volledig scenario (2 puzzels, 6 kamers) | DONE |
| `GameTestApp` (manuele tests) | DONE |
| `Timer.java` — countdown met callback | DONE |
| `HighscoreManager.java` — bestandsI/O | DONE |
| Public getters op `Game` (`getCurrentRoom`, `getPlayer`, `getRooms`) | DONE |
| `Puzzle.java` — probeerOplossen, isOpgelost | DONE |
| `Room.java` uitbreiden met puzzellijst | DONE |
| `Game.losTerminalPuzzelOp()` + `Game.losZekeringPuzzelOp()` | DONE |
| `Inventory.MAX_ITEMS` limiet | DONE |
| JUnit 5 testklassen in `src/test/java/kdg/` | TODO |

### Farok Bugri — View / GUI

| Taak | Status |
|---|---|
| `Main.java` activeren en primary Stage opzetten | DONE |
| `StartschermView` + `StartschermPresenter` | DONE |
| `SpelschermView` + `SpelschermPresenter` | DONE |
| `HelpschermView` + `HelpschermPresenter` | DONE |
| `HighscoreschermView` + `HighscoreschermPresenter` | DONE |
| `AboutschermView` + `AboutschermPresenter` | DONE |
| `PuzzelschermView` + `PuzzelschermPresenter` (terminal) | DONE |
| `ZekeringPuzzelView` + `ZekeringPuzzelPresenter` (zekeringkast) | DONE |
| Menubalk (`MenuBar`) in spelscherm | DONE |
| Scene-wissel (Startscherm → Spelscherm en terug) | DONE |
| Nieuw Stage openen (Help / Highscore / About / Puzzels als popup) | DONE |
| `spelregels.txt` aanmaken en inlezen in helpscherm | DONE |
| Deurnavigatie (knop + dubbelklik) | DONE |
| Winconditie + highscore opslaan | DONE |
| Terug naar startscherm via Stoppen-menu (bevestigingsdialoog) | DONE |
| X-knop WindowEvent (spelscherm + startscherm) | DONE |
| Item beschrijvingspaneel onderaan spelscherm | DONE |
| Neerleggen-knop + drop-logica | DONE |
| `controleerPuzzel()` — terminal + zekering detectie na deur/oppakken | DONE |
| CSS-bestand voor MenuBar-stijl (`css/stijl.css`) | DONE |
| Exception-audit + stille catch-blokken gefixt | DONE |

---

## 4. Implementatievolgorde

### Fase 1 — Fundament ✅
1. `Timer.java`, public getters op `Game`, `HighscoreManager.java`

### Fase 2 — Skelet GUI ✅
2. `Main.java`, `StartschermView` + `StartschermPresenter`, scene-wissel

### Fase 3 — Spelscherm kern ✅
3. `SpelschermView`, `SpelschermPresenter`, menubalk

### Fase 4 — Secundaire schermen ✅
4. Help, Highscore, About

### Fase 5 — Navigatie & winconditie ✅
5. Deurnavigatie, winconditie, highscore opslaan, terug naar startscherm, X-knop events, item beschrijvingspaneel, CSS

### Fase 6 — Puzzels & inventory ✅
6. `Puzzle.java` + `Room.puzzels`, terminal puzzel (Labo), zekeringkast puzzel (Controlekamer), `PuzzelschermView/Presenter`, `ZekeringPuzzelView/Presenter`, inventory limiet (1 item), neerleggen-knop, volledig speelpad

### Fase 7 — Exception-hardening ✅
7. Exception-audit: alle try-catch blokken geanalyseerd en stille fouten gefixt
8. Fallback-tekst in `HelpschermPresenter` bij ontbrekend bestand
9. `HighscoreManager`: correcte foutafhandeling lezen én schrijven
10. `SpelschermPresenter`: score opslaan crasht nooit meer de applicatie

### Fase 8 — Resterende taken (TODO)
11. JUnit 5 testklassen (Borja)
12. `highscores.txt` toevoegen aan `.gitignore`

---

## 5. Evaluatiecriteria — checklist

### Schermen (min. 5)
| Criterium | Status |
|---|---|
| Startscherm | DONE |
| Spelscherm | DONE |
| Helpscherm | DONE |
| Highscorescherm | DONE |
| Aboutscherm | DONE |

### JavaFX controls (min. 5 verschillende)
| Control | Gebruikt in | Status |
|---|---|---|
| `Button` | Spelscherm, startscherm, puzzelschermen | DONE |
| `Label` | Alle schermen | DONE |
| `TextField` | Startscherm (naam), puzzelschermen (code/wachtwoord) | DONE |
| `TextArea` | Helpscherm (spelregels), spelscherm (kamerbeschrijving) | DONE |
| `ListView` | Spelscherm (deuren, items, inventory), highscorescherm | DONE |
| `MenuBar` / `MenuItem` | Spelscherm | DONE |
| `PasswordField` → vervangen door `TextField` | Puzzelscherm | DONE |

### Events (min. 3 verschillende)
| Event | Waar | Status |
|---|---|---|
| `ActionEvent` (Button/MenuItem) | Alle schermen | DONE |
| `MouseEvent` (dubbelklik ListView) | Spelscherm (deurnavigatie) | DONE |
| `KeyEvent` (TextField onAction / onKeyTyped) | Startscherm + puzzelschermen | DONE |
| `WindowEvent` (X-knop) | Spelscherm + startscherm | DONE |
| `ChangeListener` (selectie in ListView) | Spelscherm (item beschrijving) | DONE |

### Layout panes (min. 3 verschillende)
| Pane | Gebruikt in | Status |
|---|---|---|
| `BorderPane` | Alle schermen | DONE |
| `VBox` | Startscherm, spelscherm, puzzelschermen | DONE |
| `HBox` | Spelscherm (bottom + knoppen), puzzelschermen | DONE |

### Overige vereisten
| Criterium | Status |
|---|---|
| 1x van Scene wisselen (Startscherm ↔ Spelscherm) | DONE |
| 1x van Stage wisselen (popup Help / Highscore / About / Puzzels) | DONE |
| Menubalk bovenaan | DONE |
| Gegevens lezen uit bestand (`spelregels.txt`) | DONE |
| Gegevens schrijven naar bestand (`highscores.txt`) | DONE |
| Applicatie mag NOOIT crashen | Inventory-limiet + null-checks + exception-audit afgedekt |

---

## 6. Bekende beperkingen

| Beperking | Beschrijving |
|---|---|
| **`Interactable` interface niet gebruikt** | Aangemaakt als stub, wordt door geen klasse geïmplementeerd. |
| **Geen JUnit 5 testklassen** | Enkel `GameTestApp` (manuele runner). Geautomatiseerde tests in `src/test/java/kdg/` ontbreken. |
| **`highscores.txt` niet in `.gitignore`** | Runtime-bestand dat per ongeluk gecommit kan worden. |

---

## 7. Risico's en aandachtspunten

| Risico | Kans | Aanpak |
|---|---|---|
| **MVP-scheiding** — logica in View | Gemiddeld | Regel: `Game`-aanroepen horen in de Presenter |
| **Bestandspaden** — classpath vs relatief | Laag | `getResourceAsStream()` voor lezen; relatief pad voor schrijven |
| **Timer op JavaFX thread** | Opgelost | `Timeline` met 1s `KeyFrame` in `SpelschermPresenter` |
| **Inventory-exception naar UI** | Opgelost | Pre-check in `Game.pickupItem()` vangt volle inventory op vóór `addItem()` |
| **Puzzel opent telkens opnieuw** | Laag | `controleerPuzzel()` checkt `isOpgelost()` — popup verschijnt enkel eenmalig |
