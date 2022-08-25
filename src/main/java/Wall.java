import com.googlecode.lanterna.TextColor;
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
    private final TextColor color1 = TextColor.ANSI.GREEN_BRIGHT;
    private final TextColor color2 = TextColor.ANSI.CYAN;
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

    public void drawBorder(Terminal terminal) throws IOException {
        terminal.setForegroundColor(color1);
        //Nested for-loops drawing the border,
        //where the i- and j-variables represent coordinates on the x- and y-axis respectively.
        for (int i = 1; i < this.width; i++) {
            if (i == 1) {
                //Draws left and right borders.
                for (int j = 0; j < this.height; j++) {
                    wall2.add(new Position(i, j));
                    terminal.setCursorPosition(wall2.get(j).x, wall2.get(j).y);
                    terminal.putCharacter(wallIconSide);
                    wall4.add(new Position(this.width, j));
                    terminal.setCursorPosition(wall4.get(j).x, wall4.get(j).y);
                    terminal.putCharacter(wallIconSide);
                }
            } else {
                //Draws top and bottom borders.
                for (int k = 1; k < this.width; k++) {
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
        terminal.setForegroundColor(color2);
        //mazeCell row position on y-axis.
        for (int y = 0; y < 20; y += 5) {

            //Alternates the starting mazeCell depending on even or uneven row.
            if (y % 2 == 0) {

                //mazeCell column position on x-axis.
                for (int x = 0; x < 77; x += 7) {

                    //Alternates the mazeCells in the row.
                    if (x % 2 == 0) {

                       //Nested for-loops that calls the mazeCell1 method to draw out one of the predesigned mazeCell.
                        for (int i = (3 + x); i < (10 + x); i++) {
                            for (int j = (2 + y); j < (7 + y); j++) {
                               mazeCell1(i, j, x, y, terminal);
                            }
                        }
                    } else {

                        //Nested for-loop that calls the mazeCell2 method to draw out the alternative mazeCell.
                        for (int i = (3 + x); i < (10 + x); i++) {
                            for (int j = (2 + y); j < (7 + y); j++) {
                                mazeCell2(i, j, x, y, terminal);
                            }
                       }
                   }
               }
            } else {
                //Same as above but with reversed mazeCell order.
                for (int x = 0; x < 77; x += 7) {
                    if (x % 2 == 0) {
                        for (int i = (3 + x); i < (10 + x); i++) {
                            for (int j = (2 + y); j < (7 + y); j++) {
                                mazeCell2(i, j, x, y, terminal);
                            }
                        }
                    } else {
                        for (int i = (3 + x); i < (10 + x); i++) {
                            for (int j = (2 + y); j < (7 + y); j++) {
                                mazeCell1(i, j, x, y, terminal);
                            }
                        }
                    }
                }
            }
        }
    }

    //The method that draws one of two mazeCells.
    public void mazeCell1 (int i, int j, int x, int y, Terminal terminal) throws IOException {

        //Theses if-statements draws a block-icon on the pre-defined coordinates.
        //where the i- and j-variables represent the coordinates on the x- and y-axis respectively.
        //and the x- and y-variables are the mazeCell offsets in the cell-grid.
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

    //The method that draws the alternative mazeCell.
    public void mazeCell2 (int i, int j, int x, int y, Terminal terminal) throws IOException {

        //Theses if-statements draws a block-icon on the pre-defined coordinates,
        //where i- and j-variables represent the coordinates on the x- and y-axis respectively,
        //and the x- and y-variables are the mazeCell offsets in the cell-grid.
        if ((i == 3 + x || i == 4 + x) && (j == 3 + y || j == 5 + y)) {
            maze2.add(new Position(i, j));
            terminal.setCursorPosition(i, j);
            terminal.putCharacter(wallIconSide);
        }
        if ((i == 5 + x || i == 6 + x) && (j == 2 + y || j == 3 + y || j == 5 + y)) {
            maze2.add(new Position(i, j));
            terminal.setCursorPosition(i, j);
            terminal.putCharacter(wallIconSide);
        }
        if ((i == 7 + x || i == 8 + x) && (j == 5 + y || j == 6 + y)) {
            maze2.add(new Position(i, j));
            terminal.setCursorPosition(i, j);
            terminal.putCharacter(wallIconSide);
        }
        if ((i == 9 + x) && (j == 2 + y || j == 5 + y || j == 6 + y)) {
            maze2.add(new Position(i, j));
            terminal.setCursorPosition(i, j);
            terminal.putCharacter(wallIconSide);
        }
    }
}
