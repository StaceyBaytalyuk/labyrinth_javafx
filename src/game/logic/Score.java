package game.logic;

public class Score {
    private final int levels;
    private int currentLevel;
    private int[] time; // секунд на каждом уровне
    private int[] stars; // звёзд на каждом уровне

    public Score(int levels) {
        this.levels = levels;
        time = new int[levels];
        stars = new int[levels];
    }

    public void nextLevel() {
        currentLevel++;
    }

    public void setStars(int n) {
        stars[currentLevel] = n;
    }

    public int getStars() {
        return stars[currentLevel];
    }

    public int allStars() {
        int sum = 0;
        for (int i = 0; i < levels; i++) {
            sum += stars[i];
        }
        return sum;
    }

    public int generalTime() {
        int sum = 0;
        for (int i = 0; i < currentLevel; i++) {
            sum += time[i];
        }
        return sum;
    }

    public void setTime(int n) {
        time[currentLevel] = n;
    }

    public int calculateScore() {
        int score = 0;
        for (int i=0; i<levels; i++) {
            if ( stars[i] < 5 ) {
                score += 10*stars[i];
            } else {
                int timeBonus = 0;
                if ( time[i] <= 15 ) timeBonus = 50;
                else if ( time[i] <= 25 ) timeBonus = 25;
                score += 10*stars[i] + timeBonus;
            }
        }
        return score;
    }
}