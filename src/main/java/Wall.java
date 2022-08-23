import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.List;
public class Wall {

    private final char wallIcon = '\u2588';
    private final int width;
    private final int height;
    private final List<Position> wall1;
    private final List<Position> wall2;
    private final List<Position> wall3;
    private final List<Position> wall4;

    public Wall(int width, int height, List<Position> wall1, List<Position> wall2, List<Position> wall3, List<Position> wall4) {
        this.width = width;
        this.height = height;
        this.wall1 = wall1;
        this.wall2 = wall2;
        this.wall3 = wall3;
        this.wall4 = wall4;
    }

    public void drawMap(Terminal terminal) throws IOException {
        for (int i = 0; i < this.width; i++) {
            if (i == 0) {
                for (int j = 0; j < this.height; j++) {
                    wall2.add(new Position(i, j));
                    terminal.setCursorPosition(wall2.get(j).x, wall2.get(j).y);
                    terminal.putCharacter(wallIcon);
                    wall4.add(new Position(this.width, j));
                    terminal.setCursorPosition(wall4.get(j).x, wall4.get(j).y);
                    terminal.putCharacter(wallIcon);
                }
            } else {
                for (int k = 0; k <= this.width; k++) {
                    wall1.add(new Position(k, 0));
                    terminal.setCursorPosition(wall1.get(k).x, wall1.get(k).y);
                    terminal.putCharacter(wallIcon);
                    wall3.add(new Position(k, this.height));
                    terminal.setCursorPosition(wall3.get(k).x, wall3.get(k).y);
                    terminal.putCharacter(wallIcon);
                }
            }
        }
    }

    public void drawObstacle() {

    }
}
