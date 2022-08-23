public class Player {
    private int x;
    private int y;
    private int PreviousX;
    private int PreviousY;
    private char Symbol;

    public Player(){

    }

    public void setSymbol(char symbol) {
        Symbol = symbol;
    }

    public Player(int x, int y, char symbol) {
        this.x = x;
        this.y = y;
        this.Symbol = Symbol;
        this.PreviousX=x;
        this.PreviousY=y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getPreviousX() {
        return PreviousX;
    }


    public int getPreviousY() {
        return PreviousY;
    }


    public char getSymbol() {
        return Symbol;
    }

    void moveUp (){
        PreviousX = x;
        PreviousY=y;
        y-=1;
    } void moveDown (){
        PreviousX = x;
        PreviousY=y;
        y+=1;
    } void moveLeft (){
        PreviousX = x;
        PreviousY=y;
        x-=1;
    } void moveRight (){
        PreviousX=x;
        PreviousY=y;
        x+=1;
    }

}
