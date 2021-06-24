package me.alexv.practice6;

import java.time.Year;

public class House {
    private int floors;
    private int builtYear;
    private String name;

    //метод для установки всех значений;
    public void setFields (int floors, int builtYear, String name)
    {
        this.floors = floors;
        this.builtYear = builtYear;
        this.name = name;
    }

    //метод для вывода всех значений;
    public String getFields() {return "floors:" + floors + "\nbuilt in: " + builtYear + "\nname:" + name;}

    //метод, возвращающий количество лет с момента постройки
    public int age () {
        return Math.round(Year.now().getValue() - builtYear);
    }

    public static void main(String[] args) {
        //На основе класса создайте два объекта и пропишите для каждого характеристики.
        House whiteHouse = new House();
        House redHouse = new House();

        //Добавление характеристик реализуйте через метод класса.
        whiteHouse.setFields(2, 1812, "Ампир");
        redHouse.setFields(3, 1900, "Эклектика");

        //Выведите информацию про каждый объект.
        System.out.println( whiteHouse.getFields() );
        System.out.println( redHouse.getFields() );
    }

}
