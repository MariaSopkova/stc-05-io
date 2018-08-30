package ru.innopolis.io.Employee.FileWorker;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Класс для чтания файла о сотрудниках. Считаем, что данные хранятся внутри в формате json
 * Формат файла: версия, массив данных о сотрудниках, общая сумма зарплаты
 */
public class EmployeeTXTFileParser extends EmployeeFileParser {
    public static final String employeeJSONFileName = "employee.txt";
    public static final int fileVersion = 1;
    public int currentFileVersion = 0;

    @Override
    public EmployeeFileWorker readFromFile() throws IOException, ClassNotFoundException {
        return super.readFromFile();
        /*EmployeeFileWorker fileWorker = new EmployeeFileWorker();
        try (BufferedReader employeeFileReader = new BufferedReader(new FileReader(employeeFileName))) {
            System.out.println("Read from file...");
            if(parseFileVersion(employeeFileReader.readLine())){

                parseAllSalary(employeeFileReader.readLine(), fileWorker);
            } else{
                throw new IOException();
            }

            System.out.println("OK");
        } catch (FileNotFoundException ex) {
            // если файл не найден, будем считать, что он пустой
            System.out.println("File " + employeeFileName + " not found");
        }
        return fileWorker;*/
    }

    private boolean parseFileVersion(String versionString) throws IOException {
        currentFileVersion = getIntValue(versionString);
        return currentFileVersion == fileVersion;
    }

    private void parseAllSalary(String salaryString, EmployeeFileWorker fileWorker) throws IOException {
        fileWorker.setAllEmployeeSalary(getIntValue(salaryString));
    }

    private int getIntValue(String value) throws IOException {
        // формат строки название=значение
        String[] splitedString = value.split("=");
        if (splitedString.length != 2) {
            throw new IOException();
        }
        return Integer.valueOf(splitedString[1]);
    }

    @Override
    public void writeToFile(EmployeeFileWorker fileWorker) throws Exception {
        try (BufferedWriter emploeeBufferedWriter = new BufferedWriter(new FileWriter(employeeJSONFileName))) {
            System.out.println("Write to file...");
            String fileWorkerAsString = parseoutFileWorker(fileWorker);
            emploeeBufferedWriter.write(fileWorkerAsString);
            System.out.println("OK");
        }
    }

    private String parseoutFileWorker(EmployeeFileWorker fileWorker) {
        String result = "version=" + fileVersion + "\n" + fileWorker.toString();
        return result;
    }


}
