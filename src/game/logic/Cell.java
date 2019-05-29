package game.logic;

public class Cell {
    private int currentValue;
    private int defaultValue;

    public Cell(int value) {
        currentValue = value;
        defaultValue = value;
    }

    public void toDefault() { currentValue = defaultValue; }
    public void setCurrentValue(int value) { currentValue = value; }
    public int getCurrentValue(){ return currentValue; }
}