public class Food {
    private int fX;
    private int fY;
    final private char fSymbol = '\uF074';

    public Food(int fX, int fY) {
        this.fX = fX;
        this.fY = fY;
    }

    public int getfX() {
        return fX;
    }

    public void setfX(int fX) {
        this.fX = fX;
    }

    public int getfY() {
        return fY;
    }

    public void setfY(int fY) {
        this.fY = fY;
    }
}
