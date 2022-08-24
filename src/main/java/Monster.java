import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
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

    public char getMonsterSymbol() {
        return MonsterSymbol;
    }

    public int getOldMX() {
        return oldMX;
    }

    public int getOldMY() {
        return oldMY;
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


}












