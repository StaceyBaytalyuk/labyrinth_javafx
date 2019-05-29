package game.controller;

import game.logic.Direction;
import game.logic.GameField;
import game.logic.Score;
import game.view.GameView;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import java.util.Optional;

public class Controller {
    @FXML private Text starsText;
    @FXML private Text timeText;
    @FXML private Pane pane;
    @FXML private Canvas canvas;

    private GameView view;
    private GameField field;
    private Score score = new Score(GameField.LEVELS);

    private Timeline timeline;
    private Timeline enemyTimeline;
    private Timeline heroTimeline;
    private final int[] seconds = {0};

    @FXML
    public void initialize() {
        canvas.widthProperty().bind(pane.widthProperty());
        canvas.heightProperty().bind(pane.heightProperty());
        canvas.widthProperty().addListener(e->draw());
        canvas.heightProperty().addListener(e->draw());

        field = new GameField(score, this);
        view = new GameView(field, canvas);

        initializeTimelines();
        start();
    }

    public void processKey(KeyEvent keyEvent) {
        System.out.println("pressed");
        switch (keyEvent.getCode()) {
            case LEFT: field.checkCell(Direction.LEFT);
                break;
            case RIGHT: field.checkCell(Direction.RIGHT);
                break;
            case UP: field.checkCell(Direction.UP);
                break;
            case DOWN: field.checkCell(Direction.DOWN);
                break;
        }
    }

    public void exit() {
        Platform.exit();
    }

    public void nextLevel() {
        stop();
        score.setTime(seconds[0]);
        seconds[0] = 0;
        start();
    }

    public void restartLevel() {
        stop();
        score.setStars(0);
        score.setTime(0);
        seconds[0] = 0;
        field.reset();
        start();
    }

    private void restartGame() {
        score = new Score(GameField.LEVELS);
        field = new GameField(score, this);
        view = new GameView(field, canvas);
        seconds[0] = 0;
        start();
    }

    public void win() {
        stop();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("You win!");
        alert.setHeaderText("Score: "+score.calculateScore()+"\nCollected stars: "+score.allStars()+"\nTime: "+score.generalTime()+" seconds");
        alert.setResizable(false);
        alert.setContentText("Play again?");
        Optional<ButtonType> result = alert.showAndWait();
        ButtonType button = result.orElse(ButtonType.CANCEL);
        if (button == ButtonType.OK) {
            restartGame();
        } else {
            exit();
        }
    }

    public void loss() {
        stop();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("You lost!");
        alert.setHeaderText("Play again?");
        alert.setResizable(false);
        Platform.runLater( () -> {
                    Optional<ButtonType> result = alert.showAndWait();
                    ButtonType button = result.orElse(ButtonType.CANCEL);
                    if (button == ButtonType.OK) {
                        restartLevel();
                    } else {
                        exit();
                    }
                }
        );

    }

    private void start() {
        timeline.play();
        heroTimeline.play();
        enemyTimeline.play();
    }

    private void stop() {
        timeline.stop();
        heroTimeline.stop();
        enemyTimeline.stop();
    }

    private void initializeTimelines() {
        timeline = new Timeline(new KeyFrame(Duration.millis(1000), e->timerProcessTick()));
        heroTimeline = new Timeline(new KeyFrame(Duration.millis(100), e->heroProcessTick()));
        enemyTimeline = new Timeline(new KeyFrame(Duration.millis(300), e->enemyProcessTick()));
        timeline.setCycleCount(Animation.INDEFINITE);
        heroTimeline.setCycleCount(Animation.INDEFINITE);
        enemyTimeline.setCycleCount(Animation.INDEFINITE);
    }

    private void timerProcessTick() {
        timeText.setText(String.format("%02d:%02d", (seconds[0])/60, (seconds[0])%60));
        seconds[0]++;
    }

    private void enemyProcessTick() {
        field.tick();
        draw();
    }

    private void heroProcessTick() {
        draw();
    }

    private void draw() {
        if (view!=null) {
            view.draw();
        }
        starsText.setText("☆  "+ score.getStars());
    }

}