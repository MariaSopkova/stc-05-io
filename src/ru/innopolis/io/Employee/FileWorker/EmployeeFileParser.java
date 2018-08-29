package ru.innopolis.io.Employee.FileWorker;

import java.io.*;

/**
 * Класс для чтения данных о сотрудника из файла
 */
public class EmployeeFileParser {
    public static final String employeeFileName = "employee.dat";

    public void EmployeeFileParser() {
    }

    public EmployeeFileWorker readFromFile() throws IOException, ClassNotFoundException {
        EmployeeFileWorker fileWorker = new EmployeeFileWorker();
        try (ObjectInputStream employeeInputStream = new ObjectInputStream(new FileInputStream(employeeFileName))) {
            System.out.println("Read from file...");
            fileWorker = (EmployeeFileWorker) employeeInputStream.readObject();
            System.out.println("OK");
        } catch (FileNotFoundException ex) {
            // если файл не найден, будем считать, что он пустой
            System.out.println("File " + employeeFileName + " not found");
        }
        return fileWorker;
    }

    public void writeToFile(EmployeeFileWorker fileWorker) throws Exception {
        try (ObjectOutputStream employeeOutputStream = new ObjectOutputStream(new FileOutputStream(employeeFileName))) {
            System.out.println("Write to file...");
            employeeOutputStream.writeObject(fileWorker);
            System.out.println("OK");
        }
    }
}
