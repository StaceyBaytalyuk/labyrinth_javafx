package game.logic;

public class Hero {
    private int x;
    private int y;

    public Hero(Position position) {
        x = position.getX();
        y = position.getY();
    }

    public void move(Direction direction) {
        Position next = direction.next(new Position(x, y));
        x = next.getX();
        y = next.getY();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Position getPosition() {
        return new Position(x, y);
    }
}