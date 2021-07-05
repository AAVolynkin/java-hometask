package me.alexv;

import java.util.*;

public class Practice10 {

    //1.	Написать метод, который на входе получает коллекцию объектов, а возвращает коллекцию уже без дубликатов. Для решения этой задачи пригодится Set.
    public static <E> Collection<E> removeDublicates(Collection<E> collection) {
        return new HashSet<>(collection);
    }

    //2.	Напишите метод, который добавляет 1млн элементов в ArrayList и LinkedList.
    // Напишите метод, который выбирает из заполненного списка элемент наугад 100000 раз.
    // Замерьте время, которое потрачено на это. Сравните результаты, предположите, почему они именно такие.
    public static void method2() {
        ArrayList<Double> arrayList = new ArrayList<>();
        LinkedList<Double> linkedList = new LinkedList<>();

        for (int i = 0; i < 1000000; i++) {
            Double random = Math.random();
            arrayList.add(random);
            linkedList.add(random);
        }

        Random r = new Random();

        long arrayTime = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            arrayList.get(r.nextInt(100000));
        }
        arrayTime = System.currentTimeMillis() - arrayTime;


        long listTime = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            linkedList.get(r.nextInt(100000));
        }
        listTime = System.currentTimeMillis() - listTime;

        System.out.println("Время поиска по ArrayList: " + arrayTime);
        System.out.println("Время поиска по LinkedList: " + listTime);
    }

    //3.	Опишите класс User с одним полем name. Добавьте конструктор, сеттер и геттер.
    //Создайте Map, в котором для каждого пользователя хранится количество очков, заработанных в какой-то игре (Map<User, Integer>)
    //Напишите программу, которая считывает с консоли имя и показывает, сколько очков у такого пользователя. Сами данные можно добавить в Map при создании или сгенерировать случайно.
    public static void method3() {
        class User {
            private String name;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public User (String name) {
                this.name = name;
            }
        }

        HashMap<User, Integer> map = new HashMap<>() {{put(new User("Ivan"), 10);put(new User("Petr"), 20);}};

        System.out.print("Укажите имя игрока:");
        Scanner in = new Scanner(System.in);

        String playerName = in.nextLine();

        for (Map.Entry<User, Integer> rec: map.entrySet()) {
            if (rec.getKey().getName().equals(playerName)) {
                Integer scope = rec.getValue();
                System.out.printf("Игрок %s набрал %d очков",playerName,scope);
                return;
            }
        }

        System.out.printf("Игрок %s не найден",playerName);
    }

    //4.	Метод получает на вход массив элементов типа К. Вернуть нужно объект Map<K, Integer>, где K — Значение из массива, а Integer количество вхождений в массив:
    //<K> Map<K, Integer> arrayToMap(K[] ks);
    public static <K> Map<K, Integer> arrayToMap(K[] ks) {
        Map<K, Integer> map = new HashMap<>();
        for (K item: ks) {
            map.put(item, map.containsKey(item) ? map.get(item) + 1 : 1);
        }
        return map;
    }
}
