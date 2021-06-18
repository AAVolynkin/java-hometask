package me.alexv;

import java.util.Random;

public class Practice5 implements Practice {
    @Override
    public void run() {
        Random r = new Random();
        Vector vector1 = new Vector(r.nextInt(), r.nextInt(), r.nextInt());
        Vector vector2 = new Vector(r.nextInt(), r.nextInt(), r.nextInt());

        System.out.println("vector1: " + vector1.toString());
        System.out.println("vector2: " + vector2.toString());
        System.out.println();
        System.out.printf("vector1.length(): %f", vector1.length());

        System.out.printf("vector1.scalProd(vector2): %d", vector1.scalProd(vector2));
        System.out.println("vector1.vectProd(vector2) " + (vector1.vectProd(vector2)).toString());
        System.out.printf("vector1.angle(vector2): %f", vector1.angle(vector2));
        System.out.println("vector1.sum(vector2) " + (vector1.sum(vector2)).toString());
        System.out.println("vector1.diff(vector2) " + (vector1.diff(vector2)).toString());
        System.out.println();

        int N = 10;
        Vector[] vectorArr = Vector.vectorArray(N);
        for (int i = 0; i < N; i++) {
            System.out.println("vector " + (i + 1) + ": " + vectorArr[i].toString());
        }
    }
}

class Vector {
    private int x,y,z;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    private Vector() {
        Random r = new Random();
        this.x = r.nextInt();
        this.y = r.nextInt();
        this.z = r.nextInt();
    }

    //1.конструктор с параметрами в виде списка координат x,y,z
    public Vector(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    //2.метод, вычисляющий длину вектора
    public double length() {
        return Math.sqrt( Math.pow(this.x,2) + Math.pow(this.y,2) + Math.pow(this.z,2) );
    }

    //3.метод, вычисляющий скалярное произведение
    public int scalProd(Vector v) {
        return v.x * this.x + v.y * this.y + v.z * this.z;
    }

    //4.метод, вычисляющий векторное произведение с другим вектором
    public Vector vectProd(Vector v) {
        int x = this.y * v.z - this.z * v.y;
        int y = this.z * v.x - this.x * v.z;
        int z = this.x * v.y - this.y * v.x;

        return new Vector(x,y,z);
    }

    //5.метод, вычисляющий угол между векторами
    public double angle(Vector v) {
        return Math.acos( this.scalProd(v) / (Math.abs(this.length()) * Math.abs(v.length())) );
    }

    //6.метод для суммы
    public Vector sum(Vector v) {
        return new Vector(this.x + v.x,this.y + v.y,this.z + v.z);
    }
    //6.метод для разности
    public Vector diff(Vector v) {
        return new Vector(this.x - v.x,this.y - v.y,this.z - v.z);
    }

    //7.статический метод, который принимает целое число N и возвращает массив случайных векторов размером N
    public static Vector[] vectorArray(int count) {
        Vector[] arr = new Vector[count];
        for (count--; count >= 0; count--) {
            arr[count] = new Vector();
        }
        return arr;
    }

    @Override
    public String toString() {
        return "{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
