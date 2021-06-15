package me.alexv;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Practice3 implements Practice {

    //1.	Напишите программу, которая проверяет отсортирован ли массив по возрастанию. Если он отсортирован по возрастанию то выводится “OK”, если нет, то будет выводиться текст “Please, try again”
    private void task1() {
        int[] arr = getRandArr(10);
        prnIntArr("Array:", arr);

        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < arr[i-1]) {
                System.out.println("Please, try again");
                return;
            }
        }
        System.out.println("OK");
    }

    //2.	Напишите программу, которая считывает с клавиатуры длину массива (например, пользователь вводит цифру 4), затем пользователь вводит 4 числа и на новой строке выводится массив из 4 элементов
    private void task2() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Укажите длину массива: ");
        int length = scanner.nextInt();

        int[] arr = new int[length];
        System.out.println("Укажите значение элементов массива:");
        for (int i = 0; i < arr.length; i++) {
            arr[i] = scanner.nextInt();
        }

        prnIntArr("Result:", arr);
    }

    //3.	Напишите метод, который меняет местами первый и последний элемент массива
    private void task3() {
        int[] arr = getRandArr(10);
        prnIntArr("Array1:",arr);

        int lastval = arr[0];
        arr[0] = arr[arr.length - 1];
        arr[arr.length - 1] = lastval;
        prnIntArr("Array2:",arr);
    }

    //4.	Дан массив чисел. Найдите первое уникальное в этом массиве число
    private void task4() {
        int[] arr = getRandArr(30);
        prnIntArr("Array:", arr);

        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] == arr[j])
                    break;
                if (j == arr.length - 1) {
                    System.out.println("unique value:" + arr[i]);
                    return;
                }
            }
        }
        System.out.println("no unique values");
    }

    //5.	Вычислить N-е число Фибоначчи. Не использовать рекурсию и массивы, только циклы.
    private void task5() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Укажите элемент Фибоначи: ");
        int n = scanner.nextInt();

        int[] arr = {0,1,1};

        for (int i = 2; i < n; i++) {
            arr[2] = arr[0] + arr[1];
            arr[0] = arr[1];
            arr[1] = arr[2];
        }
        System.out.println("Значение элемента: " + arr[2]);
    }

    //6.	Заполните массив случайным числами и отсортируйте его. Используйте сортировку слиянием.
    private void task6() {
        int[] arr = getRandArr(10);
        prnIntArr("Array:", arr);

        arr = sort(arr); //рекурсивный метод

        prnIntArr("Sorted:", arr);
    }
    //6 дробление
    private int[] sort(int[] arr) {
        if (arr.length < 2) return arr;

        //left
        int[] arrLeft = new int[arr.length / 2];
        arrLeft = Arrays.copyOfRange(arr, 0, arr.length / 2);

        //right
        int[] arrRight = new int[arr.length - arr.length / 2];
        arrRight = Arrays.copyOfRange(arr, arr.length / 2, arr.length);

        arrLeft = sort(arrLeft);
        arrRight = sort(arrRight);

        int posArrL = 0;
        int posArrR = 0;

        for (int i = 0; i < arr.length; i++) {
            if (arrLeft.length > posArrL && arrRight.length > posArrR) {
                if (arrLeft[posArrL] < arrRight[posArrR]) {
                    arr[i] = arrLeft[posArrL];
                    posArrL++;
                } else {
                    arr[i] = arrRight[posArrR];
                    posArrR++;
                }
            } else if (arrLeft.length > posArrL && arrRight.length <= posArrR) { //правый подмассив закончился, выводим левый
                arr[i] = arrLeft[posArrL];
                posArrL++;
            } else if (arrLeft.length <= posArrL && arrRight.length > posArrR) { //левый подмассив закончился, выводим правый
                arr[i] = arrRight[posArrR];
                posArrR++;
            }
        }

        return arr;
    }

    //7.	***Дан массив и число K. Найдите первые K самых часто встречающихся элементов.
    private void task7() {
        //условия
        int[] arr = getRandArr(30);
        int k = new Random().nextInt(10);

        prnIntArr("Array:", arr);
        System.out.println("k: " + k);

        //через двумерный массив считаем
        int[][] dubl = new int[2][arr.length]; // [ключ][вес]
        int lastpos = -1;
        boolean flag;

        for (int i = 0; i < arr.length; i++) {
            flag = false;

            //элемент повторяется, увеличиваем вес
            for (int j = 0; j < lastpos; j++) {
                if (dubl[1][j] == arr[i]) {
                    dubl[0][j]++;
                    flag = true;
                }
            }

            //впервые встречаем элемент
            if (!flag) {
                lastpos++;
                dubl[0][lastpos] = 1;
                dubl[1][lastpos] = arr[i];
            }
        }

        int maxrepeats;
        int maxpos = 0;

        System.out.println("Часто встречающиеся значения:");

        //печатаем по весу
        for (; k > 0; k--) {
            maxrepeats = 0;
            for (int j = 0; j < dubl[0].length; j++) {
                if (dubl[0][j] >= maxrepeats) {
                    maxrepeats = dubl[0][j];
                    maxpos = j;
                }
            }
            if (maxrepeats <= 1) break;

            System.out.println("Число '" + dubl[1][maxpos] + "' повторялось " + dubl[0][maxpos] + " раз(-a)");

            dubl[0][maxpos] = 0;
        }
    }

    private void prnIntArr(String prefix, int[] arr) {
        System.out.println(prefix + " " + Arrays.toString(arr));
    }

    private int[] getRandArr(int length) {
        Random r = new Random();
        int[] arr = new int[length];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = r.nextInt(100);
        }
        return arr;
    }


    @Override
    public void run() {
        System.out.println("Практика 3:");

        System.out.println("задание 1:");
        this.task1();

        System.out.println("\nзадание 2:");
        this.task2();

        System.out.println("\nзадание 3:");
        this.task3();

        System.out.println("\nзадание 4:");
        this.task4();

        System.out.println("\nзадание 5:");
        this.task5();

        System.out.println("\nзадание 6:");
        this.task6();

        System.out.println("\nзадание 7:");
        this.task7();
    }
}
