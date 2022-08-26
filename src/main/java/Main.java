import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Main {
    public static Random random = new Random();

    public static void main(String[] args) throws Exception {

        //START-deklaration
        boolean continueReadingInput= true;
        KeyStroke keyStroke =null;
        KeyStroke latestKeyStroke= null;
        Terminal terminal= createTerminal();

        //WALL
        Wall wall= new Wall(79, 23);
        wall.drawObstacle(terminal);
        wall.drawBorder(terminal);
       //MONSTER
       List <Monster> monster = createMonster(terminal);
        //PLAYER
        Player player = createPlayer(terminal);
        //FOOD
        Food food = createFood(terminal);
        int countPoints=0;
        int point;
        int mPoint;

        terminal.flush();

        while (continueReadingInput){
            int index = 0;
            do {
                index++;
                if (index % 30 == 0) {
                    if (latestKeyStroke != null) {
                        movePlayer(latestKeyStroke,player,terminal);
                        if (index % 40 == 0) {
                            wall.drawObstacle(terminal);
                            wall.drawBorder(terminal);
                            continueReadingInput = moveMonsters(monster, player, terminal);
                            terminal.flush();
                            if (!continueReadingInput) {
                                terminal.close();
                                break;
                            }
                        }
                        if (checkGameOver(terminal, player, monster)){
                            continueReadingInput = false;
                        }

                        if(countPoints==5) {
                            continueReadingInput = playerWon(terminal);
                        }
                        //POINTS
                        point= getPoint(terminal, player, food);
                        mPoint=getPointM(monster, food);
                        countPoints+= point;
                        pointBox(terminal, countPoints);
                        if (1 == point){
                            food = createFood(terminal);
                        }
                        if (1==mPoint){
                            food = createFood(terminal);
                        }
                    }
                }
                Thread.sleep(5); // might throw InterruptedException
                keyStroke = terminal.pollInput();

            } while (keyStroke == null);
            latestKeyStroke = keyStroke;

            //KeyType type=keyStroke.getKeyType();
            Character c=keyStroke.getCharacter();

            if(c==Character.valueOf('q')) {
                continueReadingInput= checkRequestToQuit(terminal);
            }
            terminal.flush();
        }
    }

    private static Terminal createTerminal() throws IOException {
        TerminalSize ts = new TerminalSize(100, 40);
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
        terminalFactory.setInitialTerminalSize(ts);
        Terminal terminal = terminalFactory.createTerminal();
        terminal.setCursorVisible(false);

        return terminal;
    }

    public static void movePlayer(KeyStroke type , Player player, Terminal terminal) throws Exception{
        switch (type.getKeyType()) {
            case ArrowUp -> player.moveUp();
            case ArrowDown -> player.moveDown();
            case ArrowLeft -> player.moveLeft();
            case ArrowRight -> player.moveRight();
        }
        //Calls method to block player from going through the walls.
        blockPlayerWall(player,terminal);
        //Clean old Position

        terminal.setCursorPosition(player.getPreviousX(), player.getPreviousY());
        terminal.putCharacter(' ');
        //Set colour and print
        terminal.setForegroundColor(TextColor.Indexed.fromRGB(254,254,29));
        terminal.setCursorPosition(player.getX(), player.getY());
        terminal.putCharacter(player.getSymbol());
        terminal.setForegroundColor(TextColor.ANSI.DEFAULT);

        terminal.flush();
    }

    public static void blockPlayerWall(Player player, Terminal terminal) throws Exception{
        boolean crashIntoObsticle = false;

        for (Position p:Wall.wall1){
            if (p.x == player.getX() && p.y == player.getY()) {
                crashIntoObsticle = true;
                break;
            }
        }
            for (Position o :Wall.wall2) {
                if (o.x == player.getX() && o.y == player.getY()) {
                    crashIntoObsticle = true;
                    break;
                }
        }
                for (Position i :Wall.wall3) {
                    if (i.x == player.getX() && i.y == player.getY()) {
                        crashIntoObsticle = true;
                        break;
                    }
        }
                    for (Position u :Wall.wall4) {
                        if (u.x == player.getX() && u.y == player.getY()) {
                            crashIntoObsticle = true;
                            break;
                        }
        }
                        for (Position y :Wall.maze1) {
                            if (y.x == player.getX() && y.y == player.getY()) {
                                crashIntoObsticle = true;
                                break;
                            }
        }
                            for (Position t :Wall.maze2) {
                                if (t.x == player.getX() && t.y == player.getY()) {
                                    crashIntoObsticle = true;
                                    break;
                                }
        }

        if (crashIntoObsticle) {
            player.setX(player.getPreviousX());
            player.setY(player.getPreviousY());

            terminal.setCursorPosition(player.getX(), player.getY());
            terminal.putCharacter(player.getSymbol());
            terminal.flush();
        }
    }
    public static Player createPlayer(Terminal terminal) throws Exception {
        Player player = new Player();
        player.setX(37);
        player.setY(12);
        player.setSymbol('\uF04A');

        terminal.setForegroundColor(TextColor.Indexed.fromRGB(254,254,29));

        terminal.setCursorPosition(player.getX(), player.getY());
        terminal.putCharacter(player.getSymbol());
        terminal.setForegroundColor(TextColor.ANSI.DEFAULT);

        return player;
    }


        public static List<Monster> createMonster(Terminal terminal) throws Exception {
            List<Monster> monsters = new ArrayList<>();
            terminal.setForegroundColor(TextColor.ANSI.GREEN);
            monsters.add(new Monster(6, 3,'¤'));
            monsters.add(new Monster(74, 3,'¤'));
            monsters.add(new Monster(74, 19,'¤'));
            monsters.add(new Monster(6, 19,'¤'));

            return monsters;
        }

    public static Food createFood (Terminal terminal) throws Exception {

        Food food;
        boolean hasAvoidedWalls;
        do {
            food = new Food((random.nextInt(2, 78)), (random.nextInt(1, 22)));

            hasAvoidedWalls= foodToAvoidWalls(food);

            if (hasAvoidedWalls){
                //CLEAN old position
                terminal.setForegroundColor(TextColor.ANSI.MAGENTA_BRIGHT);
                terminal.setCursorPosition(food.getfX(), food.getfY());
                terminal.putCharacter(food.getfSymbol());
                terminal.setForegroundColor(TextColor.ANSI.DEFAULT);
                terminal.setBackgroundColor(TextColor.ANSI.DEFAULT);
            }
        } while (!hasAvoidedWalls);
        return food;
    }

    public static boolean foodToAvoidWalls(Food food){
        boolean hasAvoidedWalls= false;
        //CHECK MAZE1
        for (Position p : Wall.maze1) {
            if(food.getfX() == p.x && food.getfY() ==p.y){
                hasAvoidedWalls=false;
                break;
            } else {
                hasAvoidedWalls=true;
            }
        }
        return hasAvoidedWalls;
    }

    private static boolean checkRequestToQuit(Terminal terminal) throws Exception {
        boolean continueReadingInput =false;
        terminal.setCursorPosition(20,10);
        terminal.setBackgroundColor(TextColor.ANSI.YELLOW_BRIGHT);
        terminal.setForegroundColor(TextColor.ANSI.BLACK);
        terminal.putString("Exiting the Game!" + '\u2639');
        terminal.setBackgroundColor(TextColor.ANSI.DEFAULT);
        terminal.setForegroundColor(TextColor.ANSI.DEFAULT);
        terminal.flush();
        Thread.sleep(500);
        terminal.close();

        return continueReadingInput;
    }

    private static boolean checkGameOver(Terminal terminal, Player player, List<Monster> monster) throws Exception {
        boolean continueReadingInput = false;
        for (Monster monster1 : monster) {
            if (player.getX() == monster1.getMx() && player.getY() == monster1.getMy()) {
                continueReadingInput = true;
                terminal.setCursorPosition(20, 10);
                terminal.setBackgroundColor(TextColor.ANSI.YELLOW_BRIGHT);
                terminal.setForegroundColor(TextColor.ANSI.BLACK);
                terminal.putString("GAME OVER!!! " + '\u2639');
                terminal.setBackgroundColor(TextColor.ANSI.DEFAULT);
                terminal.setForegroundColor(TextColor.ANSI.DEFAULT);

                Thread.sleep(700);
                terminal.flush();

                break;
            }
        }
        return continueReadingInput;
    }

    private static boolean playerWon(Terminal terminal) throws Exception {

        boolean continueReadingInput = false;
        terminal.setCursorPosition(20, 10);
        terminal.setBackgroundColor(TextColor.ANSI.YELLOW_BRIGHT);
        terminal.setForegroundColor(TextColor.ANSI.BLACK);
        terminal.putString("YOU WON!! CONGRATULATIONS!" + '\uF04A');
        terminal.setBackgroundColor(TextColor.ANSI.DEFAULT);
        terminal.setForegroundColor(TextColor.ANSI.DEFAULT);
        terminal.flush();
        Thread.sleep(500);
        terminal.close();

        return continueReadingInput;
    }


    public static int getPoint(Terminal terminal, Player player, Food food) throws Exception {
        int count=0;

        if(player.getX() == food.getfX() && player.getY() == food.getfY()) {
            terminal.bell();
            count++;
        }
        return count;
        }

    public static int getPointM(List <Monster> monsters, Food food) {
        int count=0;

        for (Monster m: monsters) {
            if(m.getMx() == food.getfX() && m.getMy() == food.getfY()) {
                count++;
            }
        }

        return count;
    }

        public static void pointBox (Terminal terminal, int countPoints) throws Exception {

            terminal.setCursorPosition(66, 24);
            terminal.setBackgroundColor(TextColor.ANSI.YELLOW_BRIGHT);
            terminal.setForegroundColor(TextColor.ANSI.BLACK);
            terminal.putString("POINTS: " + countPoints + " of 5");
            terminal.setBackgroundColor(TextColor.ANSI.DEFAULT);
            terminal.setForegroundColor(TextColor.ANSI.DEFAULT);

        }

    public static boolean moveMonsters(List<Monster> monster, Player player, Terminal terminal) throws Exception {
        for (Monster m : monster) {
            m.setOldMX(m.getMx());
            m.setOldMY(m.getMy());
            terminal.setCursorPosition(m.getMx(), m.getMy());
            terminal.putCharacter(' ');
            int diffX = m.getMx() - player.getX();
            int absDiffX = Math.abs(diffX);
            int diffY = m.getMy() - player.getY();
            int absDiffY = Math.abs(diffY);
            if (absDiffX > absDiffY) {
                // Move horizontal! <--->
                if (diffX < 0) {
                    m.setMx(m.getMx() + 1);
                } else {
                    m.setMx(m.getMx() - 1);
                }
            } else if (absDiffX < absDiffY) {
                // Move vertical! v / ^
                if (diffY < 0) {
                    m.setMy(m.getMy() + 1);
                } else {
                    m.setMy(m.getMy() - 1);
                }
            } else {
                // Move diagonal! / or \
                if (diffX < 0) {
                    m.setMx(m.getMx() + 1);
                } else {
                    m.setMx(m.getMx() - 1);
                }
                if (diffY < 0) {
                    m.setMy(m.getMy() - 1);
                } else {
                    m.setMy(m.getMy() - 1);
                }
            }
        }

        blockMonster(monster, terminal);
        for (Monster m : monster) {
            for (Position wall : Wall.maze1) {
                if (m.getOldMX() == wall.x && m.getOldMY() == wall.y) {
                    terminal.setCursorPosition(m.getOldMX(),m.getOldMY());
                    terminal.putCharacter('\u2588');
                }else {
                    terminal.setCursorPosition(m.getOldMX(), m.getOldMY());
                    terminal.putCharacter(' ');
                }

            }

            terminal.setCursorPosition(m.getMx(), m.getMy());
            terminal.putCharacter(m.getMonsterSymbol('\u123c'));
            terminal.flush();
        }


        for (Monster m : monster) {
            if (m.getMx() == player.getX() && m.getMy() == player.getY()) {
                terminal.bell();
                return false;
            }

        }
        return true;
    }
    public static void blockMonster(List<Monster> monstersList, Terminal terminal) throws Exception {

        for (Position p : Wall.wall1) {
            for (Monster monster : monstersList) {
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

        for (Position p : Wall.wall2) {
            for (Monster monster : monstersList) {
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
            for (Monster monster : monstersList) {
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
            for (Monster monster : monstersList) {
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


       /* for (Position p : Wall.maze1) {
            for (Monster monster : monstersList) {
                if (p.x == monster.getMx() && p.y == monster.getMy()) {
                    terminal.setCursorPosition(monster.getMx(), monster.getMy());
                    terminal.setBackgroundColor(TextColor.ANSI.CYAN);
                    terminal.putCharacter(monster.getMonsterSymbol('\u123c'));
                    terminal.setBackgroundColor(TextColor.ANSI.DEFAULT);
                    terminal.flush();
                    break;
                }
            }
        }*/
    }
}
