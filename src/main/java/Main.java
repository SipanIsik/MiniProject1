import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
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
        Wall wall = new Wall(50, 50);
        wall.drawMap(terminal);

        Player player = createPlayer(terminal);
       /* Monster monster= createMonster2(terminal);

        */
        List<Monster> monster = createMonster(terminal);


        terminal.flush();

        while (continueReadingInput) {

            int index = 0;
            do {
                index++;
                if (index % 20 == 0) {
                    if (latestKeyStroke != null) {
                        movePlayer(latestKeyStroke, player, terminal);
                        if (index % 40 == 0) {
                            continueReadingInput = moveMonsters(monster, player, terminal);
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
        player.setX(50);
        player.setY(50);
        player.setSymbol('\u263A');

        terminal.setCursorPosition(player.getX(), player.getY());
        terminal.putCharacter(player.getSymbol());

        return player;
    }


    public static List<Monster> createMonster( Terminal terminal) throws Exception {
        List<Monster> monsters = new ArrayList<>();
        terminal.setForegroundColor(TextColor.ANSI.GREEN);
        monsters.add(new Monster(6, 3,'\u123c'));

        return monsters;

    }




    public static boolean moveMonsters(List<Monster> monster, Player player, Terminal terminal) throws Exception {
        for (Monster m : monster) {
            m.setOldMX(m.getMx());
            m.setOldMY(m.getMy());
            terminal.setCursorPosition(m.getMx(), m.getMy());
            terminal.putCharacter(' ');

            if (player.getX() > m.getMx()) {
                m.setMx(m.getMx() + 1);
            } else if (player.getX() < m.getMx()) {
                m.setMx(m.getMx() - 1);
            }
            if (player.getY() > m.getMy()) {
                m.setMy(m.getMy() + 1);
            } else if (player.getY() < m.getMx()) {
                m.setMy(m.getMy() - 1);
            }

          }

        blockMonster(monster,terminal);
        for (Monster m :monster) {

            terminal.setCursorPosition(m.getOldMX(), m.getOldMY());
            terminal.putCharacter(' ');

            terminal.setCursorPosition(m.getMx(),m.getMy());
            terminal.putCharacter(m.getMonsterSymbol('\u123c'));

            terminal.flush();

        }


        for (Monster m : monster) {
            if (m.getMx() == player.getX() && m.getMy() == player.getY()) {
                terminal.bell();

                return false;
            }

        }return true;

    }
    public static void blockMonster(List <Monster> monstersList, Terminal terminal)throws Exception{

        boolean crashIntoObsticle = false;

        for (Position p : Wall.wall1) {
            for (Monster monster:monstersList) {
                if (p.x == monster.getMx() && p.y == monster.getMy()) {
                    monster.setMx(monster.getOldMX());
                    monster.setMy(monster.getOldMY());
                    terminal.setCursorPosition(monster.getMx(), monster.getMy());
                    terminal.putCharacter(monster.getMonsterSymbol('\u123c'));
                    terminal.flush();
                break;
                }
            }

        }

            for (Position p : Wall.wall2){
                for (Monster monster:monstersList) {
                    if (p.x == monster.getMx() && p.y == monster.getMy()) {
                        monster.setMx(monster.getOldMX());
                        monster.setMy(monster.getOldMY());
                        terminal.setCursorPosition(monster.getMx(), monster.getMy());
                        terminal.putCharacter(monster.getMonsterSymbol('\u123c'));
                        terminal.flush();
                        break;

                    }

            }
        }

        for (Position p : Wall.wall3) {
            for (Monster monster:monstersList) {
                if (p.x == monster.getMx() && p.y == monster.getMy()) {
                    monster.setMx(monster.getOldMX());
                    monster.setMy(monster.getOldMY());
                    terminal.setCursorPosition(monster.getMx(), monster.getMy());
                    terminal.putCharacter(monster.getMonsterSymbol('\u123c'));
                    terminal.flush();
                break;

                }
            }
        }
            for (Position p : Wall.wall4) {
                for (Monster monster:monstersList) {
                    if (p.x == monster.getMx() && p.y == monster.getMy()) {
                        monster.setMx(monster.getOldMX());
                        monster.setMy(monster.getOldMY());
                        terminal.setCursorPosition(monster.getMx(), monster.getMy());
                        terminal.putCharacter(monster.getMonsterSymbol('\u123c'));
                        terminal.flush();
                    break;
                    }
                }

             }

        /*for (Monster monster:monstersList) {
            if (crashIntoObsticle) {
                monster.setMx(monster.getOldMX());
                monster.setMy(monster.getOldMY());
                terminal.setCursorPosition(monster.getMx(), monster.getMy());
                terminal.putCharacter(monster.getMonsterSymbol());
                terminal.flush();

            }

        }

         */

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
