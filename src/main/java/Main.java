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

        Player player = createPlayer(terminal);
        List<Monster> monster = createMonster();

      /* List<Position> monsters = new ArrayList<>();
        monsters.add(new Position(67, 3));
        monsters.add(new Position(23, 23));
        monsters.add(new Position(83, 43));
        monsters.add(new Position(73, 55));

       */


        terminal.flush();

        while (continueReadingInput) {

            int index = 0;
            do {
                index++;
                if (index % 20 == 0) {
                    if (latestKeyStroke != null) {
                        movePlayer(latestKeyStroke, player, terminal);
                        if (index % 80 == 0) {
                            continueReadingInput = moveMonsters(monster, player, terminal,wall);
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


            if (keyStroke != null) {
                Character c = keyStroke.getCharacter();

                if (c == Character.valueOf('q')) {
                    continueReadingInput = checkRequestToQuit(terminal);

                }
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

    public static List<Monster> createMonster() throws Exception {
        List<Monster> monsters = new ArrayList<>();
        monsters.add(new Monster(3, 3, 'X'));
        monsters.add(new Monster(23, 23, 'X'));
        monsters.add(new Monster(23, 3, 'C'));
        monsters.add(new Monster(3, 23, 'X'));
        return monsters;

    }


    public static boolean moveMonsters(List<Monster> monsters, Player player, Terminal terminal, Wall wall) throws Exception {
        for (Monster monster : monsters) {
            terminal.setCursorPosition(monster.getMx(), monster.getMy());
            terminal.putCharacter(' ');

            if (player.getX() > monster.getMx()) {
                monster.setMx(monster.getMx() + 1);
            } else if (player.getX() < monster.getMx()) {
                monster.setMx(monster.getMx() - 1);
            }
            if (player.getY() > monster.getMy()) {
                monster.setMy(monster.getMy() + 1);
            } else if (player.getY() < monster.getMx()) {
                monster.setMy(monster.getMy() - 1);
            }


            if (monster.getMx() == wall.getWidth()){
                monster.setMx(monster.getMx()+1);
            } else if (monster.getMx()== wall.getHeight()) {
                monster.setMx(monster.getMx()+1);

            } else if (monster.getMy() == wall.getWidth()){
                monster.setMx(monster.getMx()+1);
            } else if (monster.getMy()== wall.getHeight()) {
                monster.setMx(monster.getMx()+1);

            }


          /* boolean crashIntoObsticle = false;
            for (Monster p : wall. {
                if (p.getMx() == && p.getMy() == y) {
                    crashIntoObsticle = true;
                }
            }
            if (crashIntoObsticle) {
                x = monster.getOldMX();
                y = monster.getOldMY();
            } else {
                terminal.setCursorPosition(monster.getOldMX(), monster.getMy()); // move cursor to old position
                terminal.putCharacter(' '); // clean up by printing space on old position
                terminal.setCursorPosition(x, y);
                terminal.putCharacter(monster.getMonsterSymbol());
            }

           */




            terminal.setCursorPosition(monster.getMx(), monster.getMy());
            terminal.putCharacter('\u123c');

        }


        terminal.flush();

        for (Monster monster : monsters) {
            if (monster.getMx() == player.getX() && monster.getMy() == player.getY()) {
                terminal.bell();

                return false;
            }
        }
        return true;


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

}
