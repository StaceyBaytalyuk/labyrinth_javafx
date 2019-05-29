package game.logic;

public class Map { // образец, по которому заполняется поле при загрузке уровня
    private int[][] array;

    public Map(int[][] array) {
        this.array = array;
    }

    public Cell[][] getArray() {
        int size = array.length;
        Cell[][] result = new Cell[size][size];
        int[] row;
        for (int i = 0; i < array.length; i++) {
            row = array[i].clone();
            for (int j = 0; j < size; j++) {
                result[i][j] = new Cell(row[j]);
            }
        }
        return result;
    }
}