package game.logic;

public class Enemy {
    private int x;
    private int y;
    private Direction direction;
    private final boolean isVertical;
    private GameField field;

    public Enemy(Position position, Direction initialDirection, GameField field) {
        x = position.getX();
        y = position.getY();
        direction = initialDirection;
        if ( initialDirection == Direction.UP || initialDirection == Direction.DOWN ) {
            isVertical = true;
        } else {
            isVertical = false;
        }
        this.field = field;
    }

    public Position move() {
        Position next = direction.next(new Position(x, y)); //клетка, которая у нас на пути
        if ( field.getCellValue(next.getX(), next.getY()) == 0 ) { //если стена - меняем направление
            if (isVertical) {
                direction = ( (direction == Direction.UP)? Direction.DOWN : Direction.UP);
            } else {
                direction = ( (direction == Direction.RIGHT)? Direction.LEFT : Direction.RIGHT);
            }
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
