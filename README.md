# 🔐 Bunker-17 — Escape Room Game

> Een sfeervol en uitdagend escape room spel gebouwd in **Java 21 + JavaFX 21** als schoolproject voor Karel de Grote Hogeschool (2025-2026).

---

## 📖 Korte beschrijving

Het jaar is **2031**. Een mysterieuze explosie heeft Bunker-17 vergrendeld. Jij bent de enige overlevende.  
De noodgenerator heeft nog beperkte stroom. Daarna sluit het ventilatiesysteem af — voor altijd.

Verken de kamers, verzamel items, los puzzels op en ontsnapt uit de bunker voordat de tijd op is.

---

## 🎮 Speelpad (spoilervrij)

Het spel telt **6 kamers** die je in volgorde doorloopt. Elke kamer heeft zijn eigen sfeer en uitdaging:

1. Begin in de **startkamer** en verzamel wat je nodig hebt om verder te komen.
2. Werk je door de **opslagruimte** en het **labo** — let goed op aanwijzingen.
3. Los de **terminal-puzzel** op om toegang te krijgen tot de volgende zone.
4. In de **controlekamer** wacht een tweede puzzel rond de zekeringkast.
5. Met het juiste item open je de weg naar de **uitgang**.

💡 *Tip: lees item-beschrijvingen aandachtig — ze bevatten hints voor de puzzels.*

---

## ✨ Features

- 🗺️ **6 unieke kamers** met eigen beschrijvingen en sfeer
- 🧩 **2 interactieve puzzels** als popup-venster (terminal + zekeringkast)
- ⚙️ **Moeilijkheidsgraad** — Makkelijk / Normaal / Moeilijk (beïnvloedt tijd én inventorylimiet)
- ⏱️ **Countdown-timer** met visuele waarschuwing onder 60 seconden
- 🏆 **Highscores** opgeslagen per moeilijkheidsgraad
- 🎬 **Matrix-animatie** op het winscherm (Canvas + Timeline)
- 💥 **Animaties** op het verliesscherm (shake, fade, knipperend rood)
- 🔊 **Volledige geluidsondersteuning** — achtergrondmuziek, item-acties, puzzels, klikgeluiden
- 🎚️ **Volume-regelaar** in het optiescherm
- 📜 **Verhaaltekst** op het introductiescherm (dynamisch op basis van moeilijkheid)
- 💾 **Gegevens lezen én schrijven** — spelregels uit bestand, highscores naar bestand
- 🛡️ **Crash-vrije applicatie** — alle uitzonderingen afgehandeld met fallback-gedrag

---

## 🛠️ Vereisten

| Vereiste | Versie | Opmerking |
|---|---|---|
| **Java** | 21 of hoger | [Download JDK 21](https://adoptium.net/) |
| **Maven** | 3.8 of hoger | Meegeleverd via `mvnw` — geen installatie nodig |
| **JavaFX** | 21 | Wordt automatisch gedownload via Maven |
| **Besturingssysteem** | Windows / Mac / Linux | Alle platformen ondersteund |

> ✅ Je hoeft **JavaFX niet apart te installeren**. Maven regelt dit automatisch via de `pom.xml`.

---

## 📦 Maven installeren (optioneel)

Het project bevat een ingebouwde Maven wrapper (`mvnw` / `mvnw.cmd`), dus Maven hoeft **niet** apart geïnstalleerd te worden. Wil je Maven toch globaal installeren, gebruik dan onderstaande commando's.

### Windows

Via [Winget](https://learn.microsoft.com/en-us/windows/package-manager/winget/) (Windows 10/11):
```bash
winget install Apache.Maven
```

Of via [Chocolatey](https://chocolatey.org/):
```bash
choco install maven
```

Controleer de installatie:
```bash
mvn -version
```

### macOS

Via [Homebrew](https://brew.sh/):
```bash
brew install maven
```

Controleer de installatie:
```bash
mvn -version
```

### Linux (Ubuntu / Debian)

```bash
sudo apt update
sudo apt install maven -y
```

Controleer de installatie:
```bash
mvn -version
```

### Linux (Fedora / RHEL)

```bash
sudo dnf install maven -y
```

### Linux (Arch Linux)

```bash
sudo pacman -S maven
```

> 💡 **Niet zeker welke methode?** Gebruik gewoon `mvnw.cmd` (Windows) of `./mvnw` (Mac/Linux) — dan heb je Maven helemaal niet nodig.

---

## 🚀 Installatie & Opstarten

### Stap 1: Repository clonen

```bash
git clone https://github.com/Borja-cools/EscapeRoom.git
cd EscapeRoom
```

### Stap 2: Compileren en starten

**Windows:**
```bash
mvnw.cmd clean javafx:run
```

**Mac / Linux:**
```bash
./mvnw clean javafx:run
```

Het spel opent automatisch in een nieuw venster.

---

### Alleen compileren (zonder starten)

```bash
mvnw.cmd clean compile
```

### Manuele tests uitvoeren

```bash
mvnw.cmd exec:java -Dexec.mainClass="kdg.model.GameTestApp"
```

Dit voert de ingebouwde modeltest uit en toont ✅/❌ in de terminal.

---

## 📁 Projectstructuur

```
EscapeRoom/
├── src/
│   └── main/
│       ├── java/
│       │   └── kdg/
│       │       ├── Main.java              ← Startpunt van de applicatie
│       │       ├── model/                 ← Spellogica (MVP model-laag)
│       │       │   ├── Game.java          ← Centrale spelcontroller
│       │       │   ├── GameBuilder.java   ← Bouwt het volledige scenario
│       │       │   ├── Player.java        ← Speler + inventory
│       │       │   ├── Room.java          ← Kamer met items, deuren en puzzels
│       │       │   ├── Door.java          ← Bidirectionele deur (vergrendeld/open)
│       │       │   ├── Item.java          ← Opraapbaar voorwerp
│       │       │   ├── Puzzle.java        ← Puzzellogica (oplossing controleren)
│       │       │   ├── Timer.java         ← Afteltimer
│       │       │   ├── DifficultyLevel.java ← Enum voor moeilijkheidsgraad
│       │       │   ├── HighscoreManager.java ← Lees/schrijf highscores.txt
│       │       │   └── GeluidManager.java ← Singleton geluidsysteem
│       │       └── view/                  ← Alle schermen (MVP view + presenter)
│       │           ├── startscherm/       ← Welkomstscherm
│       │           ├── introductiescherm/ ← Verhaaltekst voor het spel start
│       │           ├── spelscherm/        ← Kern van het spel
│       │           ├── puzzelscherm/      ← Terminal-puzzel popup
│       │           ├── helpscherm/        ← Spelregels
│       │           ├── highscorescherm/   ← Scorelijst
│       │           ├── optiescherm/       ← Moeilijkheid + volume
│       │           ├── winscherm/         ← Matrix-animatie bij winnen
│       │           ├── verliesscherm/     ← Animatie bij tijdsverlies
│       │           └── aboutscherm/       ← Info over de makers
│       └── resources/
│           ├── spelregels.txt             ← Gelezen door het helpscherm
│           ├── css/stijl.css              ← Stijl voor MenuBar
│           └── audio/                     ← Alle geluidsbestanden (.mp3)
└── pom.xml                                ← Maven configuratie
```

---

## 🎯 Evaluatiecriteria (KdG)

### Schermen (min. 5)
| Scherm | Status |
|---|---|
| Startscherm | ✅ |
| Spelscherm | ✅ |
| Helpscherm | ✅ |
| Highscorescherm | ✅ |
| Aboutscherm | ✅ |
| Introductiescherm | ✅ (bonus) |
| Optiescherm | ✅ (bonus) |
| Winscherm | ✅ (bonus) |
| Verliesscherm | ✅ (bonus) |

### JavaFX Controls (min. 5 verschillende)
| Control | Gebruikt in |
|---|---|
| `Button` | Alle schermen |
| `Label` | Alle schermen |
| `TextField` | Startscherm, puzzelschermen |
| `TextArea` | Helpscherm, spelscherm |
| `ListView` | Spelscherm, highscorescherm |
| `MenuBar` / `MenuItem` | Spelscherm |
| `RadioButton` / `ToggleGroup` | Optiescherm |
| `Slider` | Optiescherm (volume) |
| `Canvas` | Winscherm (matrix-animatie) |

### Events (min. 3 verschillende)
| Event | Waar |
|---|---|
| `ActionEvent` | Alle knoppen en menu-items |
| `MouseEvent` (dubbelklik) | Spelscherm — deur navigatie |
| `KeyEvent` | Startscherm + puzzelschermen |
| `WindowEvent` | X-knop spelscherm + startscherm |
| `ChangeListener` | Spelscherm — item beschrijving bij selectie |

### Layout Panes (min. 3 verschillende)
| Pane | Gebruikt in |
|---|---|
| `BorderPane` | Alle schermen |
| `VBox` | Startscherm, spelscherm, puzzelschermen |
| `HBox` | Spelscherm (knoppen), puzzelschermen |
| `StackPane` | Winscherm (Canvas + overlay) |

### Overige vereisten
| Criterium | Status |
|---|---|
| 1× van Scene wisselen | ✅ (Startscherm ↔ Spelscherm) |
| 1× van Stage wisselen | ✅ (popups: Help, Highscore, Puzzels) |
| Menubalk bovenaan | ✅ |
| Gegevens lezen uit bestand | ✅ (`spelregels.txt`) |
| Gegevens schrijven naar bestand | ✅ (`highscores.txt`) |
| Applicatie mag NOOIT crashen | ✅ (exception-audit uitgevoerd) |

---

## ⚠️ Veelvoorkomende problemen

**Probleem: `BUILD FAILURE` bij `clean`**
> De app draait nog op de achtergrond in IntelliJ.  
> **Oplossing:** Stop eerst de lopende applicatie (rode stop-knop in IntelliJ) voor je opnieuw compileert.

---

**Probleem: Geen geluid**
> De audio-bestanden ontbreken of staan op de verkeerde locatie.  
> **Oplossing:** Controleer of de map `src/main/resources/audio/` bestaat en alle `.mp3`-bestanden bevat. Bij een verse clone worden ze meegedownload vanuit de repository.

---

**Probleem: `JavaFX not found` of `Error: JavaFX runtime components are missing`**
> Je probeert de `.jar` rechtstreeks te starten met `java -jar`.  
> **Oplossing:** Gebruik altijd `mvnw.cmd clean javafx:run` (Windows) of `./mvnw clean javafx:run` (Mac/Linux). Maven regelt JavaFX automatisch — geen aparte installatie nodig.

---

**Probleem: `JAVA_HOME` not found**
> Java is niet correct ingesteld als omgevingsvariabele.  
> **Oplossing:** Installeer [JDK 21](https://adoptium.net/) en zorg dat `JAVA_HOME` verwijst naar de installatiemap. In IntelliJ stel je dit in via *File → Project Structure → SDK*.

---

## 👥 Auteurs

| Naam | Rol |
|---|---|
| **Borja Cools** | Model / spellogica (`kdg.model`) |
| **Farok Bugri** | View / GUI (`kdg.view`, geluiden, animaties) |

---

## 📄 Licentie

MIT Licentie — vrij te gebruiken voor educatieve doeleinden.

---

## 🏫 Opleidingsinstelling

**Karel de Grote Hogeschool**  
Programmeerproject Java (DS2) — academiejaar 2025-2026
