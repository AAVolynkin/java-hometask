package me.alexv.practice7;

public class Car {
    public int weight;
    public String model;
    public char color;
    public float speed;


    public void outPut () {
        System.out.println("Вес " + model + " составляет " + weight + "кг.");
        System.out.println("Цвет машины - " + color + " и её скорость - " + speed);
    }

    public Car (int w, String m, char c, float s) {
        weight = w;
        model = m;
        color = c;
        speed = s;
    }

    public Car () {}
}

class Truck extends Car {
    private int countWheel;
    private int weightTruck;

    public void newWheels(int countWheel) {
        this.countWheel = countWheel;
        System.out.println("Wheel's count: " + this.countWheel);
    }

    public Truck(int weight, String model, char color, float speed, int countWheel) {
        super(weight, model, color, speed);
        this.countWheel = countWheel;
        this.weightTruck = weight;
    }
}