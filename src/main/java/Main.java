import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
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
        wall.drawMap(terminal);
        wall.drawObstacle(terminal);
       //MONSTER
        Monster monster = createMonster(terminal);
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
                if (index % 50 == 0) {
                    if (latestKeyStroke != null) {
                        movePlayer(latestKeyStroke,player,terminal);
                        if (checkGameOver(terminal, player, monster)){
                            continueReadingInput = false;
                        }
                        if(countPoints==5) {
                            continueReadingInput = playerWon(terminal);
                        }
                        //POINTS
                        point= getPoint(terminal, player, food);
                        countPoints= countPoints + point;

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

            System.out.println("count points " + countPoints);

            if(c==Character.valueOf('q')) {
                continueReadingInput= checkRequestToQuit(terminal);
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

    public static Monster createMonster(Terminal terminal) throws Exception {
        Monster monster= new Monster(5, 5, '¤');
        terminal.setCursorPosition(monster.getMx(), monster.getMy());
        terminal.putCharacter(monster.getMonsterSymbol());

        return monster;
    }

    public static Food createFood (Terminal terminal) throws Exception {
        /*for (int i = 1; i <= numberOfFood; i++) {
            Random placeFoodX = new Random();
            Random placeFoodY = new Random();*/
        Food food = new Food((random.nextInt(10,70)), (random.nextInt(10,30)));
        System.out.println("new food x " + food.getfX());
        System.out.println("new food y " + food.getfY());

        terminal.setCursorPosition(food.getfX(), food.getfY());
        terminal.putCharacter(food.getfSymbol());

        return food;
    }

    private static boolean checkRequestToQuit(Terminal terminal) throws Exception {
        boolean continueReadingInput =false;
        terminal.setCursorPosition(20,10);
        terminal.putString("Exiting the Game!");
        terminal.putCharacter('\u2639');
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
            terminal.putString("GAME OVER!");
            terminal.putCharacter('\u2639');
            terminal.flush();
            Thread.sleep(500);
            terminal.close();
        }
        return continueReadingInput;
    }

    private static boolean playerWon(Terminal terminal) throws Exception {

        boolean continueReadingInput = false;
        terminal.setCursorPosition(20, 10);
        terminal.putString("YOU WON!! CONGRATULATIONS!");
        terminal.putCharacter('\uF04A');
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
}
