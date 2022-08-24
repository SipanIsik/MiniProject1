import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class Wall {

    private final char wallIconSide = '\u2588';
    private final char wallIconBottom = '\u2580';
    private final char wallIconTop = '\u2584';
    private final int width;
    private final int height;
    public static final List<Position> wall1 = new ArrayList<>();
    public static final List<Position> wall2 = new ArrayList<>();
    public static final List<Position> wall3 = new ArrayList<>();
    public static final List<Position> wall4 = new ArrayList<>();
    public static final List<Position> maze1 = new ArrayList<>();
    public static final List<Position> maze2 = new ArrayList<>();

    public Wall(int width, int height) {
        this.width = width;
        this.height = height;

    }

    public void drawMap(Terminal terminal) throws IOException {
        for (int i = 1; i < this.width; i++) {
           //Ritar ut höger- och vänstervägg
            if (i == 1) {
                for (int j = 0; j < this.height; j++) {
                    wall2.add(new Position(i, j));
                    terminal.setCursorPosition(wall2.get(j).x, wall2.get(j).y);
                    terminal.putCharacter(wallIconSide);
                    wall4.add(new Position(this.width, j));
                    terminal.setCursorPosition(wall4.get(j).x, wall4.get(j).y);
                    terminal.putCharacter(wallIconSide);
                }
            } else {
                //Ritar ut över- och undervägg
                for (int k = 1; k <= this.width; k++) {
                    wall1.add(new Position(k, 0));
                    terminal.setCursorPosition(wall1.get(k - 1).x, wall1.get(k - 1).y);
                    terminal.putCharacter(wallIconTop);
                    wall3.add(new Position(k, this.height));
                    terminal.setCursorPosition(wall3.get(k - 1).x, wall3.get(k - 1).y);
                    terminal.putCharacter(wallIconBottom);
                }
            }
        }
    }

    public void drawObstacle(Terminal terminal) throws IOException {
        //Cellposition i y-led
       for (int y = 0; y < 20; y += 5) {
           //Byter ordning på cellerna beroende på om det är jämn eller ojämn rad
           if (y % 2 == 0) {
               //Cellposition i x-led
               for (int x = 0; x < 77; x += 7) {
                   //Placerar cellerna på varannan plats
                   if (x % 2 == 0) {
                       //Nästlad for-loop som ritar ut en fördesignad cell
                       //där i och j representerar x-led respektive y-led för placering av block-character
                       for (int i = (3 + x); i < (10 + x); i++) {
                           for (int j = (2 + y); j < (7 + y); j++) {
                               if ((i == (3 + x) || i == 4 + x) && (j == 2 + y || j == 5 + y)) {
                                   maze1.add(new Position(i, j));
                                   terminal.setCursorPosition(i, j);
                                   terminal.putCharacter(wallIconSide);
                               }
                               if ((i == 5 + x || i == 6 + x) && (j == 4 + y || j == 5 + y)) {
                                   maze1.add(new Position(i, j));
                                   terminal.setCursorPosition(i, j);
                                   terminal.putCharacter(wallIconSide);
                               }
                           }
                       }
                   } else {
                       //Nästlad for-loop som ritar ut en alternativ fördesignad cell
                       for (int i = (3 + x); i < (10 + x); i++) {
                           for (int j = (2 + y); j < (7 + y); j++) {
                               if ((i == 3 + x || i == 4 + x) && (j == 3 + y || j == 5 + y)) {
                                   maze1.add(new Position(i, j));
                                   terminal.setCursorPosition(i, j);
                                   terminal.putCharacter(wallIconSide);
                               }
                               if ((i == 5 + x || i == 6 + x) && (j == 2 + y || j == 3 + y || j == 5 + y)) {
                                   maze1.add(new Position(i, j));
                                   terminal.setCursorPosition(i, j);
                                   terminal.putCharacter(wallIconSide);
                               }
                               if ((i == 7 + x || i == 8 + x) && (j == 5 + y || j == 6 + y)) {
                                   maze1.add(new Position(i, j));
                                   terminal.setCursorPosition(i, j);
                                   terminal.putCharacter(wallIconSide);
                               }
                               if ((i == 9 + x) && (j == 2 + y || j == 5 + y || j == 6 + y)) {
                                   maze1.add(new Position(i, j));
                                   terminal.setCursorPosition(i, j);
                                   terminal.putCharacter(wallIconSide);
                               }
                           }
                       }
                   }
               }
           } else {
               //Likadant som ovan fast i omvänd cellordning
               for (int x = 0; x < 77; x += 7) {
                   if (x % 2 == 0) {
                       for (int i = (3 + x); i < (10 + x); i++) {
                           for (int j = (2 + y); j < (7 + y); j++) {
                               if ((i == 3 + x || i == 4 + x) && (j == 3 + y || j == 5 + y)) {
                                   maze1.add(new Position(i, j));
                                   terminal.setCursorPosition(i, j);
                                   terminal.putCharacter(wallIconSide);
                               }
                               if ((i == 5 + x || i == 6 + x) && (j == 2 + y || j == 3 + y || j == 5 + y)) {
                                   maze1.add(new Position(i, j));
                                   terminal.setCursorPosition(i, j);
                                   terminal.putCharacter(wallIconSide);
                               }
                               if ((i == 7 + x || i == 8 + x) && (j == 5 + y || j == 6 + y)) {
                                   maze1.add(new Position(i, j));
                                   terminal.setCursorPosition(i, j);
                                   terminal.putCharacter(wallIconSide);
                               }
                               if ((i == 9 + x) && (j == 2 + y || j == 5 + y || j == 6 + y)) {
                                   maze1.add(new Position(i, j));
                                   terminal.setCursorPosition(i, j);
                                   terminal.putCharacter(wallIconSide);
                               }
                           }
                       }
                   } else {
                       for (int i = (3 + x); i < (10 + x); i++) {
                           for (int j = (2 + y); j < (7 + y); j++) {
                               if ((i == (3 + x) || i == 4 + x) && (j == 2 + y || j == 5 + y)) {
                                   maze1.add(new Position(i, j));
                                   terminal.setCursorPosition(i, j);
                                   terminal.putCharacter(wallIconSide);
                               }
                               if ((i == 5 + x || i == 6 + x) && (j == 4 + y || j == 5 + y)) {
                                   maze1.add(new Position(i, j));
                                   terminal.setCursorPosition(i, j);
                                   terminal.putCharacter(wallIconSide);
                               }
                           }
                       }
                   }
               }
           }
       }
    }
}
