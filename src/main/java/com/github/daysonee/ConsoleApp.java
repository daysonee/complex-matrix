package com.github.daysonee;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ConsoleApp {

    private final Scanner scanner = new Scanner(System.in);
    private final Map<String, ComplexMatrix> matrices = new LinkedHashMap<>();

    public void run() {
        try {
            while (true) {
                printMenu();
                String line = readLine("Выбор: ").trim();
                int choice;
                try {
                    choice = Integer.parseInt(line);
                } catch (NumberFormatException e) {
                    System.out.println("Введите номер пункта меню");
                    continue;
                }
                if (choice == 0) {
                    System.out.println("Выход");
                    return;
                }
                try {
                    handle(choice);
                } catch (IllegalArgumentException | ArithmeticException e) {
                    System.out.println("Ошибка: " + e.getMessage());
                }
            }
        } catch (IllegalStateException e) {
            System.out.println();
            System.out.println(e.getMessage() + ", выход");
        }
    }

    private void printMenu() {
        System.out.println();
        System.out.println("1. Создать матрицу");
        System.out.println("2. Показать матрицу");
        System.out.println("3. Список матриц");
        System.out.println("4. Сложить");
        System.out.println("5. Вычесть");
        System.out.println("6. Умножить");
        System.out.println("7. Разделить (A / B = A * B^-1)");
        System.out.println("8. Транспонировать");
        System.out.println("9. Обратная матрица");
        System.out.println("10. Определитель");
        System.out.println("11. Удалить матрицу");
        System.out.println("0. Выход");
    }

    private void handle(int choice) {
        switch (choice) {
            case 1:
                createMatrix();
                break;
            case 2:
                showMatrix();
                break;
            case 3:
                listMatrices();
                break;
            case 4:
                binaryOperation('+');
                break;
            case 5:
                binaryOperation('-');
                break;
            case 6:
                binaryOperation('*');
                break;
            case 7:
                binaryOperation('/');
                break;
            case 8:
                unaryOperation('t');
                break;
            case 9:
                unaryOperation('i');
                break;
            case 10:
                determinant();
                break;
            case 11:
                deleteMatrix();
                break;
            default:
                System.out.println("Нет такого пункта меню");
        }
    }

    private void createMatrix() {
        String name = readName("Имя матрицы: ", null);
        int rows = readSize("Число строк: ");
        int cols = readSize("Число столбцов: ");
        System.out.println("Вводите строки, элементы через пробел, например: 1+2i 3 -i");
        System.out.println("Внутри числа пробелов быть не должно: 3+4i, а не 3 + 4i");

        List<Complex[]> rowList = new ArrayList<>();
        for (int i = 0; i < rows; ++i) {
            while (true) {
                String line = readLine("Строка " + (i + 1) + ": ");
                try {
                    rowList.add(parseRow(line, cols));
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println("Ошибка: " + e.getMessage() + ". Введите эту строку заново");
                }
            }
        }
        Complex[][] data = rowList.toArray(new Complex[0][]);
        save(name, new ComplexMatrix(data));
    }

    private void showMatrix() {
        String name = readLine("Имя матрицы: ").trim();
        ComplexMatrix m = getMatrix(name);
        System.out.println(name + " =");
        System.out.println(m);
    }

    private void listMatrices() {
        if (matrices.isEmpty()) {
            System.out.println("Матриц пока нет");
            return;
        }
        for (Map.Entry<String, ComplexMatrix> entry : matrices.entrySet()) {
            ComplexMatrix m = entry.getValue();
            System.out.println(entry.getKey() + " (" + m.getRows() + "x" + m.getCols() + "):");
            System.out.println(m);
        }
    }

    private void binaryOperation(char op) {
        ComplexMatrix a = getMatrix(readLine("Первая матрица: ").trim());
        ComplexMatrix b = getMatrix(readLine("Вторая матрица: ").trim());
        ComplexMatrix result;
        if (op == '+') {
            result = a.add(b);
        } else if (op == '-') {
            result = a.subtract(b);
        } else if (op == '*') {
            result = a.multiply(b);
        } else {
            result = a.divide(b);
        }
        showAndSaveResult(result);
    }

    private void unaryOperation(char op) {
        ComplexMatrix a = getMatrix(readLine("Матрица: ").trim());
        ComplexMatrix result;
        if (op == 't') {
            result = a.transpose();
        } else {
            result = a.inverse();
        }
        showAndSaveResult(result);
    }

    private void determinant() {
        ComplexMatrix a = getMatrix(readLine("Матрица: ").trim());
        System.out.println("Определитель = " + a.determinant());
    }

    private void deleteMatrix() {
        String name = readLine("Имя матрицы: ").trim();
        if (matrices.remove(name) == null) {
            throw new IllegalArgumentException("Матрицы " + name + " нет");
        }
        System.out.println("Матрица " + name + " удалена");
    }

    private void showAndSaveResult(ComplexMatrix result) {
        System.out.println("Результат:");
        System.out.println(result);
        String name = readName("Сохранить как (Enter = ans): ", "ans");
        save(name, result);
    }

    private void save(String name, ComplexMatrix matrix) {
        boolean existed = matrices.containsKey(name);
        matrices.put(name, matrix);
        if (existed) {
            System.out.println("Матрица " + name + " перезаписана");
        } else {
            System.out.println("Матрица " + name + " сохранена");
        }
    }

    private ComplexMatrix getMatrix(String name) {
        ComplexMatrix m = matrices.get(name);
        if (m == null) {
            throw new IllegalArgumentException("Матрицы " + name + " нет");
        }
        return m;
    }

    private String readLine(String prompt) {
        System.out.print(prompt);
        if (!scanner.hasNextLine()) {
            throw new IllegalStateException("Ввод закончился");
        }
        return scanner.nextLine();
    }

    private String readName(String prompt, String defaultName) {
        while (true) {
            String line = readLine(prompt).trim();
            if (line.isEmpty() && defaultName != null) {
                return defaultName;
            }
            if (line.isEmpty()) {
                System.out.println("Имя не может быть пустым");
            } else if (line.contains(" ")) {
                System.out.println("В имени не должно быть пробелов");
            } else {
                return line;
            }
        }
    }

    private int readSize(String prompt) {
        while (true) {
            String line = readLine(prompt).trim();
            try {
                int n = Integer.parseInt(line);
                if (n >= 1) {
                    return n;
                }
                System.out.println("Размер должен быть не меньше 1");
            } catch (NumberFormatException e) {
                System.out.println("Нужно целое число");
            }
        }
    }

    static Complex[] parseRow(String line, int cols) {
        if (line == null || line.trim().isEmpty()) {
            throw new IllegalArgumentException("Пустая строка");
        }
        String[] parts = line.trim().split("\\s+");
        if (parts.length != cols) {
            throw new IllegalArgumentException("Ожидалось элементов: " + cols + ", введено: " + parts.length);
        }
        Complex[] row = new Complex[cols];
        for (int j = 0; j < cols; ++j) {
            try {
                row[j] = Complex.parse(parts[j]);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Не удалось разобрать \"" + parts[j] + "\" (элемент " + (j + 1) + ")");
            }
        }
        return row;
    }
}