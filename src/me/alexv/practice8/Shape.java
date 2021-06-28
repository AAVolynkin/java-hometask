package me.alexv.practice8;

import java.util.ArrayList;
import java.util.List;

interface Shape {
    double getVolume();
}

abstract class SolidOfRevolution implements Shape {
    private double radius;

    public double getRadius() {
        return radius;
    }

    public SolidOfRevolution(double radius) {
        this.radius = radius;
    }
}

class Cylinder extends SolidOfRevolution {
    private double height;

    public Cylinder (double radius, double height) {
        super(radius);
        this.height = height;
    }

    @Override
    public double getVolume() {
        return Math.PI * this.height * Math.pow(super.getRadius(), 2);
    }
}

class Ball extends SolidOfRevolution {
    public Ball (double radius) {
        super(radius);
    }

    @Override
    public double getVolume() {
        return 4/3D * Math.PI * Math.pow(super.getRadius(), 3);
    }
}

class Pyramid implements Shape {
    private double s;
    private double h;

    public Pyramid(double square, double height) {
        this.s = square;
        this.h = height;
    }

    @Override
    public double getVolume() {
        return this.s * this.h;
    }
}

class Box {
    private double volume;
    private List<Shape> list = new ArrayList<>();

    public Box(double volume) {
        this.volume = volume;
    }

    public boolean add(Shape shape) {
        if (volume > shape.getVolume()) {
            list.add(shape);
            volume -= shape.getVolume();
            return true;
        }
        return false;
    }
}

class Main {
    public static void main(String[] args) {
        Box box = new Box(151);
        System.out.println("Box volume: " + 151);

        Cylinder cylinder = new Cylinder(2, 3);
        System.out.println("Cylinder volume: " + cylinder.getVolume());
        System.out.println(box.add(cylinder));

        Ball ball = new Ball(3);
        System.out.println("Ball volume: " + ball.getVolume());
        System.out.println(box.add(ball));

        Pyramid pyramid = new Pyramid(2, 4);
        System.out.println("Pyramid volume: " + pyramid.getVolume());
        System.out.println(box.add(pyramid));
    }
}
