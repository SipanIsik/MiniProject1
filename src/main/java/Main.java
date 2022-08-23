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
        boolean continueReadingInput= true;
        KeyStroke keyStroke= null;
        KeyStroke latestKeyStroke= null;
        Terminal terminal= createTerminal();

        //WALL
       Wall wall= new Wall(100, 50);
       wall.drawMap(terminal);
       //MONSTER
        Monster monster = createMonster(terminal);
        //PLAYER
        Player player = createPlayer(terminal);

        terminal.flush();

        while (continueReadingInput){

            int index = 0;
            do {
                index++;
                if (index % 50 == 0) {
                    if (latestKeyStroke != null) {
                        movePlayer(latestKeyStroke,player,terminal);
                    }
                }
                Thread.sleep(5); // might throw InterruptedException
                keyStroke = terminal.pollInput();

            } while (keyStroke == null);
            latestKeyStroke = keyStroke;

            KeyType type=keyStroke.getKeyType();
            Character c=keyStroke.getCharacter();

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
        Monster monster= new Monster(5, 5, 'X');
        terminal.setCursorPosition(monster.getMx(), monster.getMy());
        terminal.putCharacter(monster.getMonsterSymbol());

        return monster;
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
}
