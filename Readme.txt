Author: Brian Mc George (MCGBRI004)
Date: 17 August 2014

Name: Tetris
Description: Tetris is a game where you need to completely fill a row using set randomly generated blocks in order to gain points. The row gets removed and all blocks above it get moved down. The level increases for every 5 rows destroyed which intern inscreases the block drop rate increasing difficulty. You get bonus points for removing more than one row at a time.

Controls:
Player 1:
Up - Rotate
Down - Move 1 Unit Down
Left - Move 1 Unit Left
Right - Move 1 Unit right
Space / Right Shift - Instantly move block down

Player 2:
W - Rotate
S - Move 1 Unit Down
A - Move 1 Unit Left
D - Move 1 Unit right
Left Shift - Instantly move block down

General:
Enter - Activate Two Player Mode
Escape - Pause
Space - Restarts game when it is over

Potential Compilation Issue:
This issue should be corrected but if it does occur please do the following hotfix to ensure the program runs.

The desktop assets folder sometimes does not properly link to that of the core assets folder. If this occurs the program will throw an exception saying it cannot find CustomFont_0.png. If this occurs, right click on Tetris-desktop -> properties -> Resource -> Linked Resources -> Linked Resources
double click on the assets folder click folder and link it to the core assets folder.

Sound Files made on:
http://www.bfxr.net/

Classes created:
Tetris.java
TetrisLogic.java
Leader.java
Leaderboard.java

Leaderboard:
leaderboard details stored in leaderboard.json in /desktop/leaderboard.json

GSON Dependencies:
Tetris-core Dependencies:
gson-2.3.jar - stored in /core/src/lib/gson-2.3.jar


Grandle / Libgdx Dependencies:
This project uses libgdx 1.3:

Tetris-core Dependencies:
gdx-1.3.0.jar - .gradle\caches\modules-2\files-2.1\com.badlogicgames.gdx\gdx\1.3.0\765fbbc5489f7ddc346c6a39397d41cd2682b80c


Tetris-desktop Dependencies:
gdx-1.3.0.jar -  .gradle\caches\modules-2\files-2.1\com.badlogicgames.gdx\gdx\1.3.0\765fbbc5489f7ddc346c6a39397d41cd2682b80c

gdx-backend-lwjgl-1.3.0.jar - .gradle\caches\modules-2\files-2.1\com.badlogicgames.gdx\gdx-backend-lwjgl\1.3.0\9602ad2ef7528d9c1913d33bc5d34a1e1a3df2b2

gdx-platform-1.3.0-natives-desktop.jar - .gradle\caches\modules-2\files-2.1\com.badlogicgames.gdx\gdx-platform\1.3.0\d066576ebb234abab24ee2afc4d9880277dd1521

jinput-2.0.5-sources.jar - .gradle\caches\modules-2\files-2.1\net.java.jinput\jinput\2.0.5\82604cfeb87b9ab70ed70aa19a137de8ceb21504

jinput-platform-2.0.5-natives-linux.jar - .gradle\caches\modules-2\files-2.1\net.java.jinput\jinput-platform\2.0.5\7ff832a6eb9ab6a767f1ade2b548092d0fa64795

jinput-platform-2.0.5-natives-osx.jar - .gradle\caches\modules-2\files-2.1\net.java.jinput\jinput-platform\2.0.5\53f9c919f34d2ca9de8c51fc4e1e8282029a9232

jinput-platform-2.0.5-natives-windows.jar - .gradle\caches\modules-2\files-2.1\net.java.jinput\jinput-platform\2.0.5\385ee093e01f587f30ee1c8a2ee7d408fd732e16

jlayer-1.0.1-gdx.jar - .gradle\caches\modules-2\files-2.1\com.badlogicgames.jlayer\jlayer\1.0.1-gdx\7cca83cec5c1b2f011362f4d85aabd71a73b049d

jorbis-0.0.17.jar - .gradle\caches\modules-2\files-2.1\org.jcraft\jorbis\0.0.17\8872d22b293e8f5d7d56ff92be966e6dc28ebdc6

jutils-1.0.0.jar - .gradle\caches\modules-2\files-2.1\net.java.jutils\jutils\1.0.0\e12fe1fda814bd348c1579329c86943d2cd3c6a6

lwjgl_util-2.9.1.jar - .gradle\caches\modules-2\files-2.1\org.lwjgl.lwjgl\lwjgl_util\2.9.1\290d7ba8a1bd9566f5ddf16ad06f09af5ec9b20e

lwjgl-2.9.1.jar - .gradle\caches\modules-2\files-2.1\org.lwjgl.lwjgl\lwjgl\2.9.1\f58c5aabcef0e41718a564be9f8e412fff8db847

lwjgl-platform-2.9.1-natives-linux.jar - .gradle\caches\modules-2\files-2.1\org.lwjgl.lwjgl\lwjgl-platform\2.9.1\aa9aae879af8eb378e22cfc64db56ec2ca9a44d1

lwjgl-platform-2.9.1-natives-osx.jar - .gradle\caches\modules-2\files-2.1\org.lwjgl.lwjgl\lwjgl-platform\2.9.1\2d12c83fdfbc04ecabf02c7bc8cc54d034f0daac

lwjgl-platform-2.9.1-natives-windows.jar - .gradle\caches\modules-2\files-2.1\org.lwjgl.lwjgl\lwjgl-platform\2.9.1\4c517eca808522457dd95ee8fc1fbcdbb602efbe


