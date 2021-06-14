package me.alexv;

import java.util.Arrays;
import java.util.Scanner;

public class Practice2 implements Practice {

    //Напишите программу, которая выводит на консоль нечетные числа от 1 до 99. Каждое число печатается с новой строки.
    private void task1() {
        for (int i = 1; i < 100; i++) {
            if (i % 2 > 0) System.out.println(i);
        }
    }

    //Напишите программу, которая выводит числа от 1 до 100, которые делятся на 3, 5 и на то и другое (то есть и на 3 и на 5).
    private void task2() {
        System.out.print("Делится на 3: ");
        for (int i = 1; i <= 100; i++) {
            if (i % 3 == 0) System.out.print(i + " ");
        }
        System.out.println("");

        System.out.print("Делится на 5: ");
        for (int i = 1; i <= 100; i++) {
            if (i % 5 == 0) System.out.print(i + " ");
        }
        System.out.println("");

        System.out.print("Делится на 3 и на 5: ");
        for (int i = 1; i <= 100; i++) {
            if ((i % 3 == 0) && (i % 5 == 0)) System.out.print(i + " ");
        }
        System.out.println("");
    }

    //Напишите программу, чтобы вычислить сумму двух целых чисел и вернуть true, если сумма равна третьему целому числу.
    private void task3() {
        Scanner num = new Scanner(System.in);

        System.out.print("Введите первое число: ");
        int a = num.nextInt();

        System.out.print("Введите второе число: ");
        int b = num.nextInt();

        System.out.print("Введите третье число: ");
        int c = num.nextInt();

        System.out.println("Результат: " + ((a + b) == c));
    }

    //Напишите программу, которая принимает от пользователя три целых числа и возвращает true, если второе число больше первого числа, а третье число больше второго числа.
    private void task4() {
        Scanner num = new Scanner(System.in);

        System.out.print("Введите первое число: ");
        int a = num.nextInt();

        System.out.print("Введите второе число: ");
        int b = num.nextInt();

        System.out.print("Введите третье число: ");
        int c = num.nextInt();

        System.out.println("Результат: " + ((b > a) && (c > b)));
    }

    //Напишите программу, чтобы проверить, является ли число 3 как первый или последний элемент массива целых чисел. Длина массива должна быть больше или равна двум.
    private void task5() {
        int testValue = 3;
        int[] array = new int[] {1, 2, 3, 4, 5, 6, 3};

        System.out.println("array =" + Arrays.toString(array));
        System.out.println("result: " + ((array[0] == testValue) || (array[array.length - 1] == testValue)));
    }

    //Напишите программу, чтобы проверить, что массив содержит число 1 или 3.
    private void task6() {
        int[] array = new int[] {3, -3, 7, 4, 5, 4, 3};
        boolean res = false;

        for (int i: array) {
            if ((i == 1) || i == 3) {
                res = true;
                break;
            }
        }

        System.out.println("array =" + Arrays.toString(array));
        System.out.println("result: " + res);
    }

    @Override
    public void run() {
        System.out.println("Практика 2:");

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
    }
}
