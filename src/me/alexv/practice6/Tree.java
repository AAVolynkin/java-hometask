package me.alexv.practice6;

//Создайте класс Tree.
public class Tree {

    //Добавьте в него поля: возраст, живое ли дерево и название дерева
    private int age;
    private boolean alive;
    private String name;

    //Создайте три конструктора:
    //Конструктор, который устанавливает только возраст и название;
    public Tree(int age, String name) {
        this.age = age;
        this.name = name;
    }

    //Конструктор, который устанавливает все переменные в классе;
    public Tree(int age, boolean alive, String name) {
        this.age = age;
        this.alive = alive;
        this.name = name;
    }

    //Конструктор, который ничего не устанавливает, но выводит сообщение «Пустой конструктор без параметров сработал».
    public Tree() {
        System.out.println("Пустой конструктор без параметров сработал");
    }

    @Override
    public String toString() {
        return "Tree{" +
                "age=" + age +
                ", alive=" + alive +
                ", name='" + name + '\'' +
                '}';
    }

    public static void main(String[] args) {
        //Создайте три объекта на основе класса и используйте по одному конструктору на каждый.
        Tree pine = new Tree(1, "Сосна");
        Tree birch = new Tree(2,true,"Берёза");
        Tree aspen = new Tree();

        System.out.println(pine.toString());
        System.out.println(birch.toString());
        System.out.println(aspen.toString());
    }
}
