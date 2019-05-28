package game.logic;

import game.controller.Controller;

public class GameField {
    private static final int size = 12; // кол-во клеток
    public static final int LEVELS = 3; // всего уровней
    private int currentLevel; // текущий уровень

    private Hero hero;
    //private Enemy enemy[3];
    private Cell[][] cell;
    private static Map[] map = new Map[3];

    private int stars;
    private Score score;
    private Controller controller;

    private static Position heroStartPosition = new Position(1, 10);



    public static int getSize() {
        return size;
    }

    public Hero getHero() {
        return hero;
    }

    public GameField(Score score, Controller controller) {
        this.score = score;
        this.controller = controller;
        currentLevel = 0;
        cell = map[currentLevel].getArray();
        hero = new Hero(heroStartPosition);
    }

    public void newLevel() {
        score.setStars(stars);
        stars = 0;
        controller.nextLevel();
        score.nextLevel();
        cell = map[currentLevel].getArray();
        hero = new Hero(heroStartPosition);
    }

    // для рестарта
    public void reset() {
        stars = 0;
        for (int i = 1; i < size-1; i++) {
            for (int j = 1; j < size-1; j++) {
                cell[i][j].toDefault();
            }
        }
        hero = new Hero(heroStartPosition);
    }

    public void checkCell(Direction direction) {
        Position position = direction.next(new Position(hero.getX(), hero.getY()));
        int x = position.getX();
        int y = position.getY();
        int value = cell[y][x].getCurrentValue();

        if ( value == 1 ) {
            hero.move(direction);
        } else if ( value == 2 ) {
            takeStar(x, y);
            hero.move(direction);
        } else if ( value == 3 ) {
            hero.move(direction);
            currentLevel++;
            if ( currentLevel < LEVELS ) {
                newLevel();
            } else {
                controller.win();
            }
        } else if ( value < 0) {
            controller.loss();
        }
    }

    public int getCellValue(int x, int y) {
        return cell[y][x].getCurrentValue();
    }

    //после вызова обновить статус бар
    private void takeStar(int x, int y) {
        cell[y][x].setCurrentValue(1);
        stars++;
        score.setStars(stars);
    }

    static {
        Position heroStartPosition = new Position(1, 10);


        /*Position[] heroStartPosition = {
                new Position(1, 10)
        };*/


        int[][] arr = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 2, 1, 1, 1, 1, 1, 1, 1, 3, 0},
                {0, 1, 1,-1, 1, 0, 1, 0, 2,-1, 1, 0},
                {0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0},
                {0, 1, 1, 1, 1, 0, 1, 0, 2, 1, 1, 0},
                {0, 1, 0,-1, 1, 1, 1, 0,-1, 0, 1, 0},
                {0, 1, 0, 0, 0, 1, 1, 0, 1, 1, 1, 0},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0},
                {0, 2, 0, 1, 1, 0, 1,-1, 1, 0, 1, 0},
                {0, 0, 0, 0, 1, 0, 1, 1, 0, 0, 2, 0},
                {0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };
        map[0] = new Map(arr);
        arr = new int[][] {
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 1, 1, 1, 1, 1, 1, 2, 1, 3, 0},
                {0,-1, 1, 0, 2, 1, 0, 1, 0, 1, 1, 0},
                {0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 0},
                {0, 1, 0, 1,-1, 1, 1, 1, 1, 1, 1, 0},
                {0, 2, 0, 1, 0, 1, 0, 0, 1, 1, 1, 0},
                {0, 0, 0, 1, 0, 1, 2, 0, 1, 0, 1, 0},
                {0, 1, 1, 1, 0, 1, 1, 0, 1,-1, 1, 0},
                {0, 1, 1,-1, 0, 0, 0, 0, 2, 1, 1, 0},
                {0, 0, 1, 0, 1, 1, 1, 0, 0, 0, 1, 0},
                {0, 1, 1, 1, 1,-1, 1, 1, 1, 1, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };
        map[1] = new Map(arr);
        arr = new int[][] {
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 0},
                {0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0},
                {0, 1, 0, 2, 0, 1, 1, 1, 2, 0, 1, 0},
                {0, 1, 1, 1, 0, 1,-1, 1, 0, 0, 1, 0},
                {0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0},
                {0, 1, 1, 1,-1, 1, 1, 0, 2,-1, 1, 0},
                {0, 1,-1, 1, 1, 1, 1, 0, 0, 0, 1, 0},
                {0, 1, 0, 1, 1, 1, 1, 1, 1, 0, 2, 0},
                {0, 1, 0, 0, 0, 0, 0,-1, 1, 0, 1, 0},
                {0, 1, 0, 2, 1, 1, 1, 1, 1, 1, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };
        map[2] = new Map(arr);
    }

    public void tick() {
        //enemy.move();
    }

}