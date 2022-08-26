import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.terminal.Terminal;

import java.util.ArrayList;
import java.util.List;

public class Monster {

    private int Mx;
    private int My;
    private char MonsterSymbol;
    private int oldMX;
    private int oldMY;


    public Monster(int mx, int my, char monsterSymbol) {
        Mx = mx;
        My = my;
        MonsterSymbol = monsterSymbol;
    }

      public int getMx() {
        return Mx;
    }

    public int getMy() {
        return My;
    }

    public char getMonsterSymbol(char c) {
        return MonsterSymbol;
    }

    public int getOldMX() {
        return oldMX;
    }

    public int getOldMY() {
        return oldMY;
    }

    public void setOldMX(int oldMX) {
        this.oldMX = oldMX;
    }

    public void setOldMY(int oldMY) {
        this.oldMY = oldMY;
    }

    public void setMx(int mx) {
        Mx = mx;
    }

    public void setMy(int my) {
        My = my;
    }

    public void setMonsterSymbol(char monsterSymbol) {
        MonsterSymbol = monsterSymbol;
    }

    /*// flytta över nedan till Main
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
    public static void blockMonster(List<Monster> monstersList, Terminal terminal)throws Exception{

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


    }
    */

}












