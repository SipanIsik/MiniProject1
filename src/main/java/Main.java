import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.Random;


public class Main {

    public static void main(String[] args) throws IOException {
        //start-deklaration
        boolean continueReadingInput= true;
        KeyStroke keyStroke= null;

       Terminal terminal= createTerminal();
       KeyType type;
       Character c;

        while (continueReadingInput){
            getKeyStroke(terminal);

            c=keyStroke.getCharacter();
            type=keyStroke.getKeyType();

            if(c==Character.valueOf('q')) {
               continueReadingInput=false;

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

    private static KeyStroke getKeyStroke(Terminal terminal) throws Exception {
        KeyStroke keyStroke;
        do {
            Thread.sleep(5);
            keyStroke = terminal.pollInput();
        } while (keyStroke == null);
        return keyStroke;
    }

    //create monsters --> after merging Monster Class


    //create players --> after merging Player class

    /*private static boolean checkRequestToQuit(){
        boolean continueReadingInput;

        if

        return continueReadingInput;
    }*/
}
