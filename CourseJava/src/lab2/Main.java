package lab2;

//Дана матрица. В каждой строке найти наибольший элемент.
//Из этих элементов найти наименьший и удалить ту строку, которой он принадлежит. Вывести исходную и получившуюся матрицы в консоль.

import java.util.Arrays;

public class Main {
    static final int MATRIX_SIZE = 7;

    public static <T> void arrayOutput(T[][] array) {
        arrayOutput(array, -1);
    }

    public static <T> void arrayOutput(T[][] array, Integer wasDeleted) {
        for (int i = 0; i < array.length; ++i) {
            for (int j = 0; j < array[i].length; ++j) {
                //System.out.printf((Integer.compare(wasDeleted, i) == 0) ? ConsoleColors.RED : ConsoleColors.RESET);
                //System.out.printf("%6.4s", array[i][j]);
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
            //System.out.println(ConsoleColors.RESET);
        }
    }

    public static void main(String[] args) {
        Integer[][] matrix = new Integer[MATRIX_SIZE][MATRIX_SIZE];
        Integer[][] matrix2 = new Integer[MATRIX_SIZE - 1][MATRIX_SIZE];
        Integer[] maxRows = new Integer[MATRIX_SIZE];
        //initialize matrix
        for (int i = 0; i < matrix.length; ++i) {
            matrix[i] = new Integer[matrix[i].length];
            for (int j = 0; j < matrix[i].length; ++j)
                matrix[i][j] = (int)((Math.random() + 1.1 / 10) * 89);//range[10, 99]
        }

        Integer theSmallestMax = Integer.MAX_VALUE, theSmallestMaxIndex = 0, wasDeleted = 0;
        for (int i = 0; i < matrix.length; ++i) {
            maxRows[i] = matrix[i][0];
            //max element of row
            for (int j = 0; j < matrix[i].length; ++j)
                if (matrix[i][j] > maxRows[i])
                    maxRows[i] = matrix[i][j];

            //min element of max
            if (maxRows[i] < theSmallestMax) {
                theSmallestMax = maxRows[i];
                theSmallestMaxIndex = i;
            }
        }

        System.out.println("Initial matrix:");
        arrayOutput(matrix, theSmallestMaxIndex);
        System.out.println("\n" + (theSmallestMaxIndex + 1) + " row was deleted, because of element = " + theSmallestMax + "\n");
        //initialize matrix2
        for (int i = 0; i < matrix.length; ++i) {
            if (Integer.compare(theSmallestMaxIndex, i) == 0) {
                wasDeleted = 1;
                continue;
            }
            matrix2[i - wasDeleted] = new Integer[matrix2[i - wasDeleted].length];
            for (int j = 0; j < matrix[i].length; ++j)
                matrix2[i - wasDeleted][j] = matrix[i][j];
        }

        System.out.println("Result's matrix:");
        arrayOutput(matrix2);
    }
}