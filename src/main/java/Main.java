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

       Terminal terminal= createTerminal();

       final char play= '\u263A';

        while (continueReadingInput){
            keyStroke= getKeyStroke(terminal);

            KeyType type=keyStroke.getKeyType();
            Character c=keyStroke.getCharacter();

           // moveCharacter(type, player, terminal);

            //terminal.setCursorPosition(20,20);
            //terminal.putCharacter(c);

            if(c==Character.valueOf('q')) {
                continueReadingInput= checkRequestToQuit(terminal);

            }
            //moveCharacter(type, 20, 20);

            terminal.flush();
        }
    }



    private static Terminal createTerminal() throws IOException {
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
        Terminal terminal = terminalFactory.createTerminal();
        terminal.setCursorVisible(false);

        return terminal;
    }

    private static KeyStroke getKeyStroke(Terminal terminal) throws Exception {
        KeyStroke keyStroke;
        do {
            Thread.sleep(5);
            keyStroke = terminal.pollInput();
        } while (keyStroke == null);
        return keyStroke;
    }
    public static void movePlayer(KeyStroke type , Position player, Terminal terminal) throws Exception{
        Position oldPosition= new Position(player.x , player.y);
        switch (type.getKeyType()){
            case ArrowUp:
                player.y--;
                break;
            case ArrowDown:
                player.y++;
                break;
            case ArrowLeft:
                player.x--;
                break;
            case ArrowRight:
                player.x++;
                break;
        }
        //Clean old position
        terminal.setCursorPosition(oldPosition.x, oldPosition.y);
        terminal.putCharacter(' ');

        terminal.setCursorPosition(player.x, player.y);
        terminal.putCharacter('X');

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
