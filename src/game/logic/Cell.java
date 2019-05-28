package game.logic;

public class Cell {
    private int currentValue; // меняется, когда на клетку становится персонаж
    private int defaultValue;

    public Cell(int value) {
        currentValue = value;
        defaultValue = value;
    }
    public void toDefault() { currentValue = defaultValue; } // очистка клетки
    public void setCurrentValue(int value) { currentValue = value; }
    public void setDefaultValue(int value) { defaultValue = value; } //нужно чтобы забрать звезду
    public int getCurrentValue(){ return currentValue; }
}