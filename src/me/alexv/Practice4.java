package me.alexv;

import java.util.Arrays;

public class Practice4 implements Practice {
    private String[] arrStr = {"набор","тестовых","строк","ака","ого","вот такая бяка будет"};

    //1.	Написать метод для поиска самой длинной строки.
    private void task1() {
        int pos = 0;
        int length = 0;

        for (int i = 0; i < arrStr.length; i++) {
            if (arrStr[i].length() > length) {
                pos = i;
                length = arrStr[i].length();
            }
        }
        System.out.println("Max length: " + length + " for string: '" + arrStr[pos] + "'");
    }

    //2.	Написать метод, который проверяет является ли слово палиндромом
    private void task2() {
        for (int i = 0; i < arrStr.length; i++) {
            if (arrStr[i].equals( (new StringBuilder(arrStr[i])).reverse().toString() ))
                System.out.println("Palindrome: " + arrStr[i]);
        }
    }

    //3.	Напишите метод, заменяющий в тексте все вхождения слова «бяка» на «[вырезано цензурой]»
    private void task3() {
        String ban = "бяка";
        String censure = "[вырезано цензурой]";

        System.out.println(arrStr.toString().replaceAll(ban,censure));

        /*
        StringBuilder s;

        for (int i = 0; i < arrStr.length; i++) {
            s = new StringBuilder(arrStr[i]);
            while (s.indexOf(ban) >= 0) {
                s.replace( s.indexOf(ban), s.indexOf(ban) + ban.length(), censure );
            }
            System.out.println( s.toString() );
        }
        */
    }

    //4.	Имеются две строки. Найти количество вхождений одной (являющейся подстрокой) в другую.
    private void task4() {
        String str = "Многие не любят перемен, но мы должны научиться принимать их. Особенно если альтернатива переменам – это катастрофа.";
        String substr = "перемен";
        StringBuilder s = new StringBuilder(str);

        int count = 0;
        int idx = 0;

        while (true) {
            idx = s.indexOf(substr,idx);
            if (idx < 0) break;
            count++;
            idx++;
        }

        System.out.println("String: '" + str + "'");
        System.out.println("Substring: '" + substr + "'");
        System.out.println("Count: " + count);
    }

    //5.	Напишите метод, который инвертирует слова в строке. Предполагается, что в строке нет знаков препинания, и слова разделены пробелами.
    private void task5() {
        String str = "Просто тестовая строка";

        String[] strArr = str.split(" ");

        for (int i = 0; i < strArr.length; i++) {
            strArr[i] = (new StringBuilder(strArr[i])).reverse().toString();
        }

        System.out.println("String: '" + str + "'");
        System.out.println("Reverse: '" + String.join(" ", strArr) + "'");
    }

    @Override
    public void run() {
        System.out.println("Практика 4:");

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
    }
}
