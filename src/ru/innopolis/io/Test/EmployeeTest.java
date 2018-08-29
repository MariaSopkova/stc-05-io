package ru.innopolis.io.Test;

import ru.innopolis.io.Employee.Employee;
import ru.innopolis.io.Employee.Exceptions.EmployeeNotFound;
import ru.innopolis.io.Employee.FileWorker.EmployeeFile;
import ru.innopolis.io.Employee.JobType;

import java.util.List;

public class EmployeeTest {

    EmployeeFile employeeFile;

    public EmployeeTest() {
        System.out.println("Init employeeFileWorker...");
        try {
            employeeFile = new EmployeeFile();
            System.out.println("Init ok");
            printEmployees();
        } catch (Exception ex) {
            System.out.println();
        }
    }

    public void addOrUpdate() {
        System.out.println("/////////////");
        employeeFile.saveOrUpdate(new Employee("DOCTOR_2", 31, 10000, JobType.DOCTOR));
        employeeFile.saveOrUpdate(new Employee("PROGRAMMER", 23, 10000, JobType.PROGRAMMER));
        employeeFile.saveOrUpdate(new Employee("PROGRAMMER_2", 24, 20000, JobType.PROGRAMMER));
        employeeFile.saveOrUpdate(new Employee("DOCTOR", 31, 20000, JobType.DOCTOR));
        employeeFile.saveOrUpdate(new Employee("FIREMAN", 33, 20000, JobType.FIREMAN));
        printEmployees();
        System.out.println("/////////////");
    }

    public void changeAllWork() {
        System.out.println("/////////////");
        printEmployees();
        employeeFile.changeAllWork(JobType.PROGRAMMER, JobType.POLICEMAN);
        printEmployees();
        System.out.println("/////////////");
    }

    public void getByName() {
        System.out.println("/////////////");
        printEmployees();
        try {
            System.out.println(employeeFile.getByName("PROGRAMMER"));
            System.out.println(employeeFile.getByName("Firstttt"));
        } catch (EmployeeNotFound ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("/////////////");
    }

    public void getByJob() {
        System.out.println("/////////////");
        printEmployees();
        List<Employee> allProgrammer = employeeFile.getByJob(JobType.PROGRAMMER);
        for (Employee programmer : allProgrammer) {
            System.out.println(programmer);
        }
        System.out.println("/////////////");
    }

    public void delete() {
        System.out.println("/////////////");
        employeeFile.delete(new Employee("Second", 31, 10000, JobType.DOCTOR));
        employeeFile.delete(new Employee("PROGRAMMER_2", 24, 30000, JobType.POLICEMAN));
    }

    public void close() {
        try {
            employeeFile.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void printEmployees() {
        System.out.println(employeeFile);
    }
}
