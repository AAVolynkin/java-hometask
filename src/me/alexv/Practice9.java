package me.alexv;

import java.util.Arrays;
import java.util.Random;

public class Practice9 {

    //Все методы должны иметь проверку параметров и генерировать исключения при необходимости.
    //1. Написать метод для поиска наибольшего элемента в двумерном массиве.
    public static int maxElement(int[][] arr) throws NullPointerException,IndexOutOfBoundsException {
        if (arr == null)
            throw new NullPointerException("Массив не инициализирован");
        if (arr.length * arr[0].length <= 1)
            throw new IndexOutOfBoundsException("Массив должен содержать более 1 элемента");

        int last = arr[0][0];
        for (int[] row : arr) {
            for (int col: row) {
                if (col > last) last = col;
            }
        }
        return last;
    }

    //2. Написать метод, который проверяет, является ли двумерный массив квадратным.
    public static boolean isSquare(int[][] arr) {
        if (arr == null)
            throw new NullPointerException("Массив не инициализирован");
        if (arr.length * arr[0].length <= 1)
            throw new IndexOutOfBoundsException("Массив должен содержать более 1 элемента");

        return arr.length == arr[0].length;
    }

    //3. Написать метод, который, в двумерном массиве (матрице) ищет строку, сумма элементов которой является максимальной среди всех строк матрицы.
    public static int maxRow(int[][] arr) throws NullPointerException,IndexOutOfBoundsException {
        if (arr == null)
            throw new NullPointerException("Массив не инициализирован");
        if (arr.length * arr[0].length <= 1)
            throw new IndexOutOfBoundsException("Массив должен содержать более 1 элемента");

        int row = -1;
        int prevsum = 0;
        int sum;

        for (int i = 0; i < arr.length; i++) {
            sum = 0;
            for (int j = 0; j < arr[0].length; j++) {
                sum += arr[i][j];
            }
            if (i > 0 && sum <= prevsum) continue;

            prevsum = sum;
            row = i;
        }

        return row;
    }

    //4. Двумерный массив MxN заполнить случайными символами алфавита
    public static char[][] randArr(int row, int col) throws Exception {
        final String CHARS = "abcdefghijklmnopqrstuvwxyz";

        if (row < 1 || col < 1)
            throw new Exception("Неправильно задана размерность массива");

        char[][] chars = new char[row][col];

        try {
            Random r = new Random();
            for (int i = 0; i < chars.length; i++) {
                for (int j = 0; j < chars[0].length; j++) {
                    chars[i][j] = CHARS.charAt( r.nextInt(CHARS.length() - 1) );
                }
            }
        }
        catch (Exception ex) {
            throw new Exception("Ошибка заполнения массива значениями char");
        }

        return chars;
    }

    //5. * Дан массив чисел numbers, и дан массив weight такой же длины. Задача: написать метод, который бы случайно выбирал число из первого массива, которое есть во втором массиве.
    public static int randElem(int[] numbers, int[] weight) throws Exception {
        class notFoundElement extends RuntimeException {
            notFoundElement(int element, int[] array) {
                super("Элемент '" + element + "' не найден в массиве " + Arrays.toString(array));
            }
        }

        try {
            int elem = numbers[ (new Random()).nextInt(numbers.length) ];
            for (int j : weight) {
                if (elem == j) return elem;
            }
            throw new notFoundElement(elem,weight);
        } catch (ArrayIndexOutOfBoundsException ex) {
            throw new Exception("Неправильно задана размерность массива");
        } catch (notFoundElement ex) {
            throw new Exception(ex.getMessage());
        }
    }



    public static void main(String[] args) {
        int[][] arr;
        //1
        try {
            System.out.println("Написать метод для поиска наибольшего элемента в двумерном массиве");
            arr = new int[][]{{1,2,3},{4,5,6}};
            System.out.println("max value для массива " + Arrays.deepToString(arr)+ ": " + Practice9.maxElement(arr));
            //err
            arr = new int[][]{{},{}};
            System.out.print("max value для массива " + Arrays.deepToString(arr) + ": ");
            System.out.print(Practice9.maxElement(arr));
        } catch (Exception ex) {
            System.out.println("Ошибка: " + ex.getMessage());
        }

        //2
        try {
            System.out.println("\nНаписать метод, который проверяет, является ли двумерный массив квадратным");
            arr = new int[][]{{1, 2, 3}, {4, 5, 6}};
            System.out.println("array" + Arrays.deepToString(arr) + " is square: " + Practice9.isSquare(arr));
            //err
            arr = new int[][]{{}, {}};
            System.out.print("array" + Arrays.deepToString(arr) + " is square: ");
            Practice9.isSquare(arr);
            System.out.print(Practice9.isSquare(arr));
        } catch (Exception ex) {
            System.out.println("Ошибка: " + ex.getMessage());
        }

        //3
        try {
            System.out.println("\nНаписать метод, который, в двумерном массиве (матрице) ищет строку, сумма элементов которой является максимальной среди всех строк матрицы");
            arr = new int[][]{{1, 2, 3}, {4, 5, 6},{7,8,9}};
            System.out.println("array" + Arrays.deepToString(arr) + " maxRow: " + Practice9.maxRow(arr));
            //err
            arr = new int[][]{{}, {}};
            System.out.print("array" + Arrays.deepToString(arr) + " maxRow: ");
            Practice9.maxRow(arr);
            System.out.print(Practice9.maxRow(arr));
        } catch (Exception ex) {
            System.out.println("Ошибка: " + ex.getMessage());
        }

        //4
        try {
            System.out.println("\nДвумерный массив MxN заполнить случайными символами алфавита");
            System.out.println("array[2][9] :" + Arrays.deepToString(Practice9.randArr(2, 9)));
            //err
            System.out.print("array[-1][9]:" + Arrays.deepToString(Practice9.randArr(-1, 9)));
        } catch (Exception ex) {
            System.out.println("Ошибка: " + ex.getMessage());
        }

        //5
        try {
            System.out.println("\nДан массив чисел numbers, и дан массив weight такой же длины. Задача: написать метод, который бы случайно выбирал число из первого массива, которое есть во втором массиве");
            System.out.println("array numbers: " + Arrays.toString(new int[]{1,2,3,4,5}) + ", array weight: " + Arrays.toString(new int[]{1,2,3,4,5}) + ", element found:" + Practice9.randElem(new int[]{1,2,3,4,5}, new int[]{1,2,3,4,5}));
            //err
            System.out.println("array numbers: " + Arrays.toString(new int[]{1,2,3,4,5}) + ", array weight: " + Arrays.toString(new int[]{6,7,8,9,0}) + ", element found:" + Practice9.randElem(new int[]{1,2,3,4,5}, new int[]{6,7,8,9,0}));
        } catch (Exception ex) {
            System.out.println("Ошибка: " + ex.getMessage());
        }
    }
}
