package game.logic;

public class Enemy {
    private int x;
    private int y;
    private Direction direction;
    private final boolean isVertical; // направление движения
    private GameField field;

    public Enemy(Position position, Direction initialDirection, GameField field) {
        x = position.getX();
        y = position.getY();
        direction = initialDirection;
        isVertical = initialDirection == Direction.UP || initialDirection == Direction.DOWN;
        this.field = field;
    }

    public Position move() {
        Position next = direction.next(new Position(x, y));
        int value = field.getCellValue(next.getX(), next.getY());
        if ( value == 0 || value == 3 ) { //стена или дверь - меняем направление
            if (isVertical) {
                direction = ( (direction == Direction.UP)? Direction.DOWN : Direction.UP);
            } else {
                direction = ( (direction == Direction.RIGHT)? Direction.LEFT : Direction.RIGHT);
            }
            next = direction.next(new Position(x, y));
        }
        x = next.getX();
        y = next.getY();
        return new Position(x, y);
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