package ru.innopolis.io.Employee.Exceptions;

/**
 * Исключение "Сотрудник не найден в файле"
 */
public class EmployeeNotFound extends Exception {

    String employeeName; // имя сотрудника

    public EmployeeNotFound(String employeeName) {
        this.employeeName = employeeName;
    }

    @Override
    public String getMessage() {
        return "\"" + employeeName + "\" not found in file.";
    }
}
