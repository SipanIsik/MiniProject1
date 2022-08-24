import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Main {

    public static void main(String[] args) throws Exception {


        //START-deklaration
        boolean continueReadingInput = true;
        KeyStroke keyStroke = null;
        KeyStroke latestKeyStroke = null;
        Terminal terminal = createTerminal();

        //WALL
        Wall wall = new Wall(100, 50);
        wall.drawMap(terminal);
        //MONSTER
        //Monster monster = createMonster(terminal);
        //PLAYER
        Player player = createPlayer(terminal);
        List<Position> monsters = new ArrayList<>();
        monsters.add(new Position(67, 3));
        monsters.add(new Position(23, 23));
        monsters.add(new Position(83, 43));
        monsters.add(new Position(73, 55));


        terminal.flush();

        while (continueReadingInput) {

            int index = 0;
            do {
                index++;
                if (index % 20 == 0) {
                    if (latestKeyStroke != null) {
                        movePlayer(latestKeyStroke, player, terminal);
                        if (index % 80 == 0) {
                            continueReadingInput = moveMonsters(monsters, player, terminal);
                            terminal.flush();
                            if (!continueReadingInput) {
                                terminal.close();
                                break;
                            }
                        }
                    }
                }


                Thread.sleep(5); // might throw InterruptedException
                keyStroke = terminal.pollInput();


            } while (keyStroke == null);
            latestKeyStroke = keyStroke;


            KeyType type = keyStroke.getKeyType();
            Character c = keyStroke.getCharacter();


            if (c == Character.valueOf('q')) {
                continueReadingInput = checkRequestToQuit(terminal);

            }


            terminal.flush();
        }
    }


    private static Terminal createTerminal() throws IOException {
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
        Terminal terminal = terminalFactory.createTerminal();
        terminal.setCursorVisible(false);

        return terminal;
    }

    public static void movePlayer(KeyStroke type, Player player, Terminal terminal) throws Exception {
        switch (type.getKeyType()) {
            case ArrowUp:
                player.moveUp();
                break;
            case ArrowDown:
                player.moveDown();
                break;
            case ArrowLeft:
                player.moveLeft();
                break;
            case ArrowRight:
                player.moveRight();
                break;
        }
        //Clean old position
        terminal.setCursorPosition(player.getPreviousX(), player.getPreviousY());
        terminal.putCharacter(' ');

        terminal.setCursorPosition(player.getX(), player.getY());
        terminal.putCharacter(player.getSymbol());

        terminal.flush();

    }

    public static Player createPlayer(Terminal terminal) throws Exception {
        Player player = new Player();
        player.setX(10);
        player.setY(15);
        player.setSymbol('\u263A');

        terminal.setCursorPosition(player.getX(), player.getY());
        terminal.putCharacter(player.getSymbol());

        return player;
    }

    public static Monster createMonster(Terminal terminal) throws Exception {
        Monster monster = new Monster(5, 5, 'X');
        terminal.setCursorPosition(monster.getMx(), monster.getMy());
        terminal.putCharacter(monster.getMonsterSymbol());

        return monster;
    }

    private static boolean checkRequestToQuit(Terminal terminal) throws Exception {
        boolean continueReadingInput = false;
        terminal.setCursorPosition(20, 10);
        terminal.putString("Exiting the Game!");
        terminal.putCharacter('\u2639');
        terminal.flush();
        Thread.sleep(500);
        terminal.close();

        return continueReadingInput;
    }

    public static boolean moveMonsters(List<Position> monsters, Player player, Terminal terminal) throws Exception {
        for (Position monster : monsters) {
            terminal.setCursorPosition(monster.x, monster.y);
            terminal.putCharacter(' ');

            if (player.getX() > monster.x) {
                monster.x++;
            } else if (player.getX() < monster.x) {
                monster.x--;
            }
            if (player.getY() > monster.y) {
                monster.y++;
            } else if (player.getY() < monster.y) {
                monster.y--;
            }

            terminal.setCursorPosition(monster.x, monster.y);
            terminal.putCharacter('\u123c');

        }

        terminal.flush();

        for (Position monster : monsters) {
            if (monster.x == player.getX() && monster.y == player.getY()) {
                terminal.bell();
                return false;
            }
        }
        return true;



    }

}
