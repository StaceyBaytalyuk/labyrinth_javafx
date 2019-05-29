package game.view;

import game.logic.GameField;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class GameView {
    private GameField field;
    private Canvas canvas;
    private Image heroImage = new Image(getClass().getResourceAsStream("/images/hero.png"));
    private Image enemyImage = new Image(getClass().getResourceAsStream("/images/enemy.png"));
    private Image starImage = new Image(getClass().getResourceAsStream("/images/star.png"));
    private Image doorImage = new Image(getClass().getResourceAsStream("/images/door.png"));


    public GameView(GameField field, Canvas canvas) {
        this.field = field;
        this.canvas = canvas;
    }

    public void draw() {
        GraphicsContext g2 = canvas.getGraphicsContext2D();
        g2.setFill(Color.WHITESMOKE);
        g2.fillRect(0,0, canvas.getWidth(), canvas.getHeight());
        g2.setStroke(Color.GRAY);

        int n = GameField.SIZE;
        double cellWidth = canvas.getWidth() / (double)n;
        double cellHeight = canvas.getHeight() / (double)n;
        double cellSize = Double.min(cellHeight, cellWidth);

        for (int i = 0; i <= n; i++) {
            g2.strokeLine(i*cellSize, 0, i*cellSize, n*cellSize);
        }
        for (int i = 0; i <=n; i++) {
            g2.strokeLine(0, i*cellSize, n*cellSize, i*cellSize);
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int value = field.getCellValue(j, i);
                if ( value == 0 ) {
                    g2.setFill(Color.BLACK);
                    g2.fillRect(j*cellSize, i*cellSize, cellSize, cellSize);
                } else if ( value == -1 ) {
                    g2.setFill(Color.GRAY);
                    g2.fillOval(j*cellSize+0.15*cellSize, i*cellSize+0.3*cellSize, cellSize*0.7, cellSize*0.4);
                } else if ( value == 2 ) {
                    g2.drawImage(starImage, j*cellSize+0.15*cellSize, i*cellSize+0.15*cellSize, cellSize*0.7, cellSize*0.7);
                } else if ( value == 3 ) {
                    g2.drawImage(doorImage, j*cellSize, i*cellSize, cellSize, cellSize);
                }
            }
        }

        int x = field.getHero().getX();
        int y = field.getHero().getY();
        g2.drawImage(heroImage, x*cellSize, y*cellSize, cellSize, cellSize);

        for (int i = 0; i < GameField.ENEMIES; i++) {
            x = field.getEnemy(i).getX();
            y = field.getEnemy(i).getY();
            g2.drawImage(enemyImage, x*cellSize, y*cellSize, cellSize, cellSize);
        }
    }
}