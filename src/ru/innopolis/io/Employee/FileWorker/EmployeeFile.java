package ru.innopolis.io.Employee.FileWorker;

import ru.innopolis.io.Employee.Employee;
import ru.innopolis.io.Employee.Exceptions.EmployeeNotFound;
import ru.innopolis.io.Employee.JobType;

import java.io.IOException;
import java.util.List;

/**
 * Класс для работы с файлом с данными по сотрудникам
 * Чтение данных из файла происходит в конструкторе
 * Запись в файл происходит при вызове close()
 */
public class EmployeeFile implements AutoCloseable {
    private EmployeeFileWorker fileWorker;
    private EmployeeFileParser fileParser;

    public EmployeeFile() throws IOException, ClassNotFoundException {
        fileParser = new EmployeeFileParser();
        fileWorker = fileParser.readFromFile();
    }

    @Override
    public void close() throws Exception {
        if (fileWorker.isDataHasChanged()) {
            fileParser.writeToFile(fileWorker);
        }
    }

    public List<Employee> getByJob(JobType currentJob) {
        return fileWorker.getByJob(currentJob);
    }

    public boolean changeAllWork(JobType oldJob, JobType newJob) {
        return fileWorker.changeAllWork(oldJob, newJob);
    }

    public Employee getByName(String employeeName) throws EmployeeNotFound {
        return fileWorker.getByName(employeeName);
    }

    public boolean add(Employee employee) {
        return fileWorker.add(employee);
    }

    public boolean delete(Employee employee) {
        return fileWorker.delete(employee);
    }

    public boolean saveOrUpdate(Employee employee) {
        return fileWorker.saveOrUpdate(employee);
    }

    public int getAllSalaryCount() {
        return fileWorker.getAllEmployeeSalary();
    }

    @Override
    public String toString() {
        return fileWorker.toString();
    }
}
