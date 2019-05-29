package game.logic;

import game.controller.Controller;

import java.util.ArrayList;
import java.util.List;

public class GameField {
    public static final int SIZE = 12; // кол-во клеток
    public static final int LEVELS = 3; // всего уровней
    public static final int ENEMIES = 3;

    private Hero hero;
    private Enemy[] enemy = new Enemy[ENEMIES];
    private List<Position> currentEnemyPosition = new ArrayList<>(ENEMIES);
    private Cell[][] cell;

    private int currentLevel;
    private int stars;
    private Score score;
    private Controller controller;

    public GameField(Score score, Controller controller) {
        this.score = score;
        this.controller = controller;
        currentLevel = 0;
        cell = map[currentLevel].getArray();
        hero = new Hero(heroStartPosition);
        initializeEnemies();
    }

    public void tick() { // движение врагов
        for (int i = 0; i < ENEMIES; i++) {
            currentEnemyPosition.set(i, enemy[i].move());
            if ( hero.getPosition().equals(currentEnemyPosition.get(i)) ) {
                controller.loss();
            }
        }
    }

    private void nextLevel() {
        score.setStars(stars); // сохранить звёзды текущего уровня
        stars = 0;
        controller.nextLevel();
        score.nextLevel();
        cell = map[currentLevel].getArray(); // загрузить новую карту
        hero = new Hero(heroStartPosition);
        initializeEnemies();
    }

    // для рестарта
    public void reset() {
        stars = 0;
        for (int i = 1; i < SIZE -1; i++) {
            for (int j = 1; j < SIZE -1; j++) {
                cell[i][j].toDefault();
            }
        }
        hero = new Hero(heroStartPosition);
        initializeEnemies();
    }

    public void checkCell(Direction direction) {
        Position position = direction.next(new Position(hero.getX(), hero.getY()));
        int x = position.getX();
        int y = position.getY();
        int value = cell[y][x].getCurrentValue();

        for (int i = 0; i < ENEMIES; i++) { // если там враг
            if ( position.equals(enemy[i].getPosition()) ) {
                controller.loss();
            }
        }

        if ( value == 1 ) {
            hero.move(direction);
        } else if ( value == 2 ) {
            takeStar(x, y);
            hero.move(direction);
        } else if ( value == 3 ) {
            hero.move(direction);
            controller.draw();
            currentLevel++;
            if ( currentLevel < LEVELS ) {
                nextLevel();
            } else {
                controller.win();
            }
        } else if ( value == -1 ) {
            hero.move(direction);
            controller.draw();
            controller.loss();
        }
    }

    public int getCellValue(int x, int y) {
        return cell[y][x].getCurrentValue();
    }

    public Hero getHero() {
        return hero;
    }

    public Enemy getEnemy(int number) {
        return enemy[number];
    }

    private void takeStar(int x, int y) {
        cell[y][x].setCurrentValue(1);
        stars++;
        score.setStars(stars);
    }

    private void initializeEnemies() {
        if ( currentLevel > 0 ) {
            for (int i = 0; i < ENEMIES; i++) {
                enemy[i] = new Enemy(enemyStartPosition[i], enemyStartDirection[i], this);
            }
        } else {
            enemy[0] = new Enemy(enemyStartPosition[0], enemyStartDirection[0], this);
            enemy[1] = new Enemy(new Position(6, 1), enemyStartDirection[1], this);
            enemy[2] = new Enemy(enemyStartPosition[2], enemyStartDirection[2], this);
        }

        for (int i = 0; i < ENEMIES; i++) {
            currentEnemyPosition.add(enemy[i].getPosition());
        }
    }

    private static Position heroStartPosition = new Position(1, 10);
    private static Position[] enemyStartPosition = { new Position(1, 1), new Position(5, 1), new Position(10, 10) };
    private static Direction[] enemyStartDirection = { Direction.RIGHT, Direction.DOWN, Direction.UP };
    private static Map[] map = new Map[LEVELS];

    static {
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
}