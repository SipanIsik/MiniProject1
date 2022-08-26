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
        KeyStroke keyStroke;
        KeyStroke latestKeyStroke= null;
        Terminal terminal= createTerminal();

        //WALL
        Wall wall= new Wall(100, 50);
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

        terminal.flush();

        while (continueReadingInput){
            int index = 0;
            do {
                index++;
                if (index % 30 == 0) {
                    if (latestKeyStroke != null) {
                        movePlayer(latestKeyStroke,player,terminal);
                        if (index % 40 == 0) {
                            continueReadingInput = moveMonsters(monster, player, terminal);
                            terminal.flush();
                            if (!continueReadingInput) {
                                terminal.close();
                                break;
                            }
                        }
                        if (checkGameOver(terminal, player, monster){
                            continueReadingInput = false;
                        }
                        if(countPoints==5) {
                            continueReadingInput = playerWon(terminal);
                        }
                        //POINTS
                        point= getPoint(terminal, player, food);
                        countPoints+= point;
                        pointBox(terminal, countPoints);

                        if (1 == point){
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

            //System.out.println("count points " + countPoints);

            if(c==Character.valueOf('q')) {
                continueReadingInput= checkRequestToQuit(terminal);
            }
            terminal.flush();

            //terminal.setCursorPosition(monster.getMx(),monster.getMy());
            //Character.valueOf('\u2588');
        }
    }



    private static Terminal createTerminal() throws IOException {
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
        Terminal terminal = terminalFactory.createTerminal();
        terminal.setCursorVisible(false);

        return terminal;
    }

    public static void movePlayer(KeyStroke type , Player player, Terminal terminal) throws Exception{
        switch (type.getKeyType()){
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
        //Calls method to block player from going through the walls.
        blockPlayerWall(player,terminal);
        //Clean old position
        terminal.setCursorPosition(player.getPreviousX(), player.getPreviousY());
        terminal.putCharacter(' ');

        terminal.setCursorPosition(player.getX(), player.getY());
        terminal.putCharacter(player.getSymbol());

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
        player.setX(10);
        player.setY(15);
        player.setSymbol('\uF04A');

        terminal.setCursorPosition(player.getX(), player.getY());
        terminal.putCharacter(player.getSymbol());

        return player;
    }

   /* public static Monster createMonster(Terminal terminal) throws Exception {
        Monster monster= new Monster(5, 5, '¤');
        terminal.setCursorPosition(monster.getMx(), monster.getMy());
        terminal.putCharacter(monster.getMonsterSymbol());

        return monster;
    }

    */
    public static Food createFood (Terminal terminal) throws Exception {

        Food food;
        boolean hasAvoidedWalls;
        do {
            food = new Food((random.nextInt(10, 70)), (random.nextInt(5, 20)));

            terminal.setCursorPosition(food.getfX(), food.getfY());
            terminal.putCharacter(food.getfSymbol());

            hasAvoidedWalls= foodToAvoidWalls(terminal, food);

            if (!hasAvoidedWalls){
                //CLEAN old position
                terminal.setBackgroundColor(TextColor.ANSI.CYAN);
                terminal.setCursorPosition(food.getfX(), food.getfY());
                terminal.putCharacter('\u2588');
                terminal.setBackgroundColor(TextColor.ANSI.DEFAULT);
            }

        } while (!hasAvoidedWalls);
        return food;
    }

    public static boolean foodToAvoidWalls(Terminal terminal, Food food){
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
        //CHECK MAZE2
        for (Position p : Wall.maze2) {
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
        terminal.putString("Exiting the Game!");
        terminal.putCharacter('\u2639');
        terminal.setBackgroundColor(TextColor.ANSI.DEFAULT);
        terminal.setForegroundColor(TextColor.ANSI.DEFAULT);
        terminal.flush();
        Thread.sleep(500);
        terminal.close();

        return continueReadingInput;
    }

    private static boolean checkGameOver(Terminal terminal, Player player, Monster monster) throws Exception {
        boolean continueReadingInput = false;
        if(player.getX() == monster.getMx() && player.getY() == monster.getMy()){
            continueReadingInput = true;
            terminal.setCursorPosition(20,10);
            terminal.setBackgroundColor(TextColor.ANSI.YELLOW_BRIGHT);
            terminal.setForegroundColor(TextColor.ANSI.BLACK);
            terminal.putString("GAME OVER!");
            terminal.putCharacter('\u2639');
            terminal.setBackgroundColor(TextColor.ANSI.DEFAULT);
            terminal.setForegroundColor(TextColor.ANSI.DEFAULT);

            terminal.flush();
            Thread.sleep(500);
            terminal.close();
        }
        return continueReadingInput;
    }

    private static boolean playerWon(Terminal terminal) throws Exception {

        boolean continueReadingInput = false;
        terminal.setCursorPosition(20, 10);
        terminal.setBackgroundColor(TextColor.ANSI.YELLOW_BRIGHT);
        terminal.setForegroundColor(TextColor.ANSI.BLACK);
        terminal.putString("YOU WON!! CONGRATULATIONS!");
        terminal.putCharacter('\uF04A');
        terminal.setBackgroundColor(TextColor.ANSI.DEFAULT);
        terminal.setForegroundColor(TextColor.ANSI.DEFAULT);
        terminal.flush();
        Thread.sleep(500);
        terminal.close();

        return continueReadingInput;
    }


    public static int getPoint(Terminal terminal, Player player, Food food) throws Exception {
        int count=0;
        TextColor color;
        if(player.getX() == food.getfX() && player.getY() == food.getfY()) {
            terminal.bell();
            count++;
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



    public static List<Monster> createMonster(Terminal terminal) throws Exception {
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
}
