import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;


public class Main {

    public static void main(String[] args) throws Exception {
        //start-deklaration
        boolean continueReadingInput= true;
        KeyStroke keyStroke= null;
        KeyStroke latestKeyStroke= null;

       Terminal terminal= createTerminal();

      Player player = new Player();

      player.setX(10);
      player.setY(15);
      player.setSymbol('\u263A');

       terminal.setCursorPosition(player.getX(), player.getY());
       terminal.putCharacter(player.getSymbol());
       terminal.flush();

        while (continueReadingInput){
            //keyStroke= getKeyStroke(terminal);

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

    /*private static KeyStroke getKeyStroke(Terminal terminal) throws Exception {
        KeyStroke keyStroke;

        do {
            Thread.sleep(5);
            keyStroke = terminal.pollInput();
        } while (keyStroke == null);
        return keyStroke;
    }*/
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
    public static void handlePlayer(){
        //ev att man refererar direct till Player klassen ?
    }

    //create monsters --> after merging Monster Class


    //create players --> after merging Player class

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
