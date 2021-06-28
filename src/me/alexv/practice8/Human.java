package me.alexv.practice8;

public abstract class Human {
    private String name;
    private String surname;

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public abstract String getFullInfo();

    public Human(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public static void main(String[] args) {
        Client client = new Client("Ivan", "Petrov", "RUS Bank");
        Employee employee = new Employee("Petr", "Ivanov", "USA Bank");

        System.out.println(client.getFullInfo());
        System.out.println(employee.getFullInfo());
    }
}

class Client extends Human {
    private String bankName;

    public Client(String name, String surname, String bankName) {
        super(name, surname);
        this.bankName = bankName;
    }

    @Override
    public String getFullInfo() {
        return getSurname() + " " + getName() + " client of " + this.bankName;
    }
}

class Employee extends Human {
    private String bankName;

    public Employee(String name, String surname, String bankName) {
        super(name, surname);
        this.bankName = bankName;
    }

    @Override
    public String getFullInfo() {
        return getSurname() + " " + getName() + " works in " + this.bankName;
    }
}