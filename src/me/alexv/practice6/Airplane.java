package me.alexv.practice6;

public class Airplane {

    public static void main(String[] args) {
        Airplane airplane = new Airplane();

        Airplane.Wing wing1 = airplane.new Wing();
        Airplane.Wing wing2 = airplane.new Wing();

        wing1.weight = 340.29F;
        wing2.weight = 341.29F;

        System.out.println("Wing 1. Weight: " + wing1.getWeight());
        System.out.println("Wing 2. Weight: " + wing2.getWeight());
    }

    public class Wing {
        private float weight;

        public float getWeight() {
            return weight;
        }
    }
}
