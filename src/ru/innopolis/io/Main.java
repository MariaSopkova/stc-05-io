package ru.innopolis.io;

import ru.innopolis.io.Test.EmployeeTest;

public class Main {
    public static void main(String[] args) {
        EmployeeTest employeeTest = new EmployeeTest();
        employeeTest.addOrUpdate();
        employeeTest.close();

    }
}
