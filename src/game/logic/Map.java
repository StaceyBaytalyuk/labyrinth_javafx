package game.logic;

public class Map { // образец, по которому заполняется поле во время (пере)загрузки уровня
    private int[][] array; // что содержит каждая клетка

    public Map(int[][] array) {
        this.array = array;
    }

    public Cell[][] getArray() { // TODO проверить не меняет ли копия содержание оригинала
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
