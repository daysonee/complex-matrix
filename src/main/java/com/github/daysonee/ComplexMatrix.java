package com.github.daysonee;

public final class ComplexMatrix {

    private final int rows;
    private final int cols;

    private final Complex[][] data;

    public ComplexMatrix(Complex[][] source) {
        if (source == null) {
            throw new IllegalArgumentException("матрицы нет");
        }
        if (source.length == 0) {
            throw new IllegalArgumentException("нет строк в матрице");
        }
        if (source[0] == null || source[0].length == 0) {
            throw new IllegalArgumentException("пустая 1 строка матрицы");
        }
        int colsCount = source[0].length;
        int rowsCount = source.length;
        Complex[][] arr = new Complex[rowsCount][colsCount];
        for (int i = 0; i < rowsCount; ++i) {
            if (source[i] == null) {
                throw new IllegalArgumentException("пустая строка " + i);
            }
            if (source[i].length != colsCount) {
                throw new IllegalArgumentException("не матрица");
            }
            for (int j = 0; j < colsCount; ++j) {
                if (source[i][j] == null) {
                    throw new IllegalArgumentException("пустая ячейка");
                }
                arr[i][j] = source[i][j];
            }
        }
        data = arr;
        cols = colsCount;
        rows = rowsCount;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public Complex get(int i, int j) {
        if (i >= rows || i < 0 || j >= cols || j < 0) {
            throw new IndexOutOfBoundsException("вышли за пределы матрицы");
        }
        return data[i][j];
    }
@Override 
    public String toString() {
        String matrix = "";
        for (int i = 0; i < rows; ++i) {
            String row = "";
            for (int j = 0; j < cols; ++j) {
                row = row + data[i][j].toString();
                if (j != cols - 1) {
                    row = row + " ";
                }
            }
            matrix = matrix + row;
            if (i != rows - 1) {
                matrix = matrix + "\n";
            }
        }
        return matrix;
    }
}
