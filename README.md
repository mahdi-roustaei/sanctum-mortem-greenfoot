# Sanctum Mortem

A 2D fantasy survival game built with Java and Greenfoot. Move a mage through an arena, cast spells in eight directions, and survive encounters with skeletons, archers, and orcs.

Developed as a **university group project for Startprojekt**, and shared with permission. This repository presents the team's work.

## Features

- Independent movement and shooting controls: move with WASD and cast spells with the arrow keys.
- Animated player and enemy sprites, including movement, spellcasting, attacks, and death sequences.
- Three enemy types: pursuing skeletons, ranged archers, and tougher melee orcs.
- A ten-heart health display, damage cooldown, healing potions, and speed boosts.
- Score-based enemy spawning and increasing skeleton spawn probability.
- A start screen with player-name entry, a game-over transition, and a local leaderboard that keeps each name's best score.
- Background music and sound effects for combat, movement, pickups, and game events.

## Technology

| Area | Implementation |
| --- | --- |
| Language | Java |
| Game environment | Greenfoot (`Actor`, `World`, `GreenfootImage`, `GreenfootSound`) |
| Player-name dialog | Swing (`JOptionPane`) |
| Persistence | Java file I/O and collections |
| Resources | PNG sprites/backgrounds, WAV and MP3 audio |

## Run the game

1. Install **Greenfoot 3.9.0** from the [official download page](https://www.greenfoot.org/download).
2. Download this repository using **Code → Download ZIP**, then extract it.
3. Open `project.greenfoot` in Greenfoot. Keep the Java files, `images/`, and `sounds/` together in the same scenario folder.
4. Compile the scenario. If no start world appears, right-click `StartScreen` in the class diagram and choose `new StartScreen()`.
5. Click Greenfoot's **Run** button, click the in-game play button, and enter a name or nickname.
6. To play another round, use Greenfoot's **Reset** button; if needed, create a new `StartScreen` again.

Greenfoot's [introductory tutorial](https://www.greenfoot.org/doc/tut-1) explains the Compile, Run, Pause, and scenario controls. This project runs inside Greenfoot; it has no standalone Java `main` entry point.

**Verification status:** Source and resource references were reviewed, and all 90 referenced media files are present with matching paths. Compilation and interactive gameplay in Greenfoot have not been verified during repository preparation.

## Controls

| Input | Action |
| --- | --- |
| W / A / S / D | Move up / left / down / right |
| Arrow keys | Cast spells in the corresponding direction |
| Two adjacent arrow keys | Cast diagonally |
| Mouse click | Activate the start button |
| Greenfoot Run / Pause | Start or pause the simulation |
| Greenfoot Reset | Reset the scenario |

Game timing is based on `act()` cycles and is affected by Greenfoot's simulation-speed control.

## Code structure

| Classes | Responsibility |
| --- | --- |
| `StartScreen`, `Startknopf` | Start menu and player-name entry |
| `MyWorld`, `Level2` | Shared world state, arena, enemy spawning, and score display |
| `Entity`, `Enemies` | Shared sprite preparation and enemy behavior |
| `Player`, `Heart` | Movement, spellcasting, health, pickups, and damage handling |
| `Skeleton`, `Archer`, `Orc` | Enemy movement, attacks, animation, and scoring |
| `Projectile`, `PlayerProjectiles`, `EnemyProjectiles` | Player spells and enemy projectiles |
| `Potion`, `HealingPotion`, `SpeedBoost` | Collectible effects |
| `GameOver`, `HighscoreScreen`, `HighscoreManager` | End-of-game flow and local scores |
| `EssentialFunctions` | Random-number and spawn-rate helpers |

## Local data

The game writes player-entered names and scores to `highscores.txt` in the scenario's working directory. The repository excludes this file. On a fresh copy, an `Error reading highscores.` message may appear before the first score file is created; the current reader returns an empty list in that case.

## Repository preparation

The original 20 Java source files and `project.greenfoot` are preserved unchanged. The submitted archive contained `Heart.class` without `Heart.java`; a minimal `Heart` source was reconstructed from its bytecode. It extends `Actor` without custom behavior and uses the image configured in `project.greenfoot`.

The repository retains the 76 images and 14 audio files referenced by the source and scenario settings. Generated files, saved player scores, older archives, and unreferenced media are excluded from this copy.

## Current limitations

- Desktop compilation and a full play-through remain to be checked in Greenfoot.
- Leaderboard input and malformed score-file handling need validation.
- Projectile behavior at world boundaries and audio cleanup after game over need testing.
- Debug output and some generated class comments remain from development.
- Automated tests are not included.

## Credits and licensing

See [CREDITS.md](CREDITS.md) for the asset inventory and attribution status. Publication permission for the university project is confirmed. Specific third-party asset sources and license details are still being documented; no repository-wide license has been assigned in this preparation.
