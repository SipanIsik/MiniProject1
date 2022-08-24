import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

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
        this.oldMX = oldMX;
        this.oldMY = oldMY;
    }



    public int getMx() {return Mx;}

    public int getMy() {return My;}

    public char getMonsterSymbol() {return MonsterSymbol;}

    public int getOldMX() {return oldMX;}

    public int getOldMY() {return oldMY;}

    public void setMx(int mx) {
        Mx = mx;
    }

    public void setMy(int my) {
        My = my;
    }

    public void setMonsterSymbol(char monsterSymbol) {
        MonsterSymbol = monsterSymbol;
    }

    public void moveTowards(Player player) {
        oldMX = Mx;
        oldMY = My;

        int diffX = Mx - player.getX();
        int absDiffX = Math.abs(diffX);
        int diffY = My - player.getY();
        int absDiffY = Math.abs(diffY);


        if (absDiffX > absDiffY) {
            // Move horizontal! <--->
            if (diffX < 0) {
                Mx += 1;
            } else {
                Mx -= 1;
            }
        } else if (absDiffX < absDiffY) {
            // Move vertical! v / ^
            if (diffY < 0) {
                My += 1;
            } else {
                My -= 1;
            }
        }
    }
}












