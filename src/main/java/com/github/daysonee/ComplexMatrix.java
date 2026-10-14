package com.github.daysonee;

public final class ComplexMatrix {
    public static final double EPS = 1e-9;

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

    public ComplexMatrix add(ComplexMatrix a) {
        if (a.cols != this.cols || a.rows != this.rows) {
            throw new IllegalArgumentException("Неправильная размерность матрицы");
        }
        Complex[][] res = new Complex[rows][cols];
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                res[i][j] = data[i][j].add(a.get(i, j));
            }
        }
        return new ComplexMatrix(res);
    }

    public ComplexMatrix subtract(ComplexMatrix a) {
        if (a.cols != this.cols || a.rows != this.rows) {
            throw new IllegalArgumentException("Неправильная размерность матрицы");
        }
        Complex[][] res = new Complex[rows][cols];
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                res[i][j] = data[i][j].subtract(a.get(i, j));
            }
        }
        return new ComplexMatrix(res);
    }

    public ComplexMatrix multiply(ComplexMatrix a) {
        if (a.rows != this.cols) {
            throw new IllegalArgumentException("Неправильная размерность матрицы");
        }
        Complex[][] res = new Complex[this.rows][a.cols];
        for (int i = 0; i < this.rows; ++i) {
            for (int j = 0; j < a.cols; ++j) {
                Complex sum = Complex.ZERO;
                for (int k = 0; k < this.cols; k++) {
                    sum = sum.add(data[i][k].multiply(a.get(k, j)));
                }
                res[i][j] = sum;
            }
        }
        return new ComplexMatrix(res);
    }

    public ComplexMatrix transpose(){
        Complex[][] res = new Complex[cols][rows];
        for ( int i = 0; i < cols; ++i){
            for (int j = 0; j < rows; ++j){
                res[i][j] = data[j][i];
            }
        }
        return new ComplexMatrix(res);
    }

    private Complex[][] recreate(){
        Complex[][] res = new Complex[rows][cols];
        for(int i = 0; i < rows; ++i){
            for (int j = 0; j < cols; ++j){
                res[i][j] = new Complex(data[i][j].getRe(), data[i][j].getIm());
            }
        }
        return res;
    }

    public Complex determinant(){
        if(this.cols != this.rows){
            throw new IllegalArgumentException("Матрица не квадратная");
        }
        Complex[][] matrixCopy = this.recreate();
        Complex det = Complex.ONE;
        for(int k = 0; k < cols; ++k){
            double maxEl = 0;
            int mainRow = k;
            for (int j = k; j < cols; ++j){
                if (maxEl < matrixCopy[j][k].abs()){
                    maxEl = matrixCopy[j][k].abs();
                    mainRow = j;
                }
            }
            if (Math.abs(maxEl) < EPS){return Complex.ZERO;}
            if (mainRow != k){
                Complex[] temp = matrixCopy[mainRow];
                matrixCopy[mainRow] = matrixCopy[k];
                matrixCopy[k] = temp;
                det = det.negate();
            }
            for (int r = k+1; r < cols; ++r){
                Complex coef = matrixCopy[r][k].divide(matrixCopy[k][k]);
                for (int a = k; a < cols; ++a){
                    matrixCopy[r][a] = matrixCopy[r][a].subtract(matrixCopy[k][a].multiply(coef));
                }
            }
            det = det.multiply(matrixCopy[k][k]);
        }
        return det;
    }

}
