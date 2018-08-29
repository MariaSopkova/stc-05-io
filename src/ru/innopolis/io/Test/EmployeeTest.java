package ru.innopolis.io.Test;

import ru.innopolis.io.Employee.Employee;
import ru.innopolis.io.Employee.EmployeeFileWorker;
import ru.innopolis.io.Employee.Exceptions.EmployeeNotFound;
import ru.innopolis.io.Employee.JobType;

import java.util.List;

public class EmployeeTest {

    EmployeeFileWorker employeeFileWorker;

    public EmployeeTest() {
        System.out.println("Init employeeFileWorker...");
        try {
            employeeFileWorker = new EmployeeFileWorker();
            System.out.println("Init ok");
            printEmployees();
        } catch (Exception ex) {
            System.out.println();
        }
    }

    public void addOrUpdate() {
        System.out.println("/////////////");
        employeeFileWorker.saveOrUpdate(new Employee("DOCTOR_2", 31, 40000, JobType.DOCTOR));
        employeeFileWorker.saveOrUpdate(new Employee("PROGRAMMER", 23, 20000, JobType.PROGRAMMER));
        employeeFileWorker.saveOrUpdate(new Employee("PROGRAMMER_2", 24, 30000, JobType.PROGRAMMER));
        employeeFileWorker.saveOrUpdate(new Employee("DOCTOR", 31, 40000, JobType.DOCTOR));
        employeeFileWorker.saveOrUpdate(new Employee("FIREMAN", 33, 35000, JobType.FIREMAN));
        printEmployees();
        System.out.println("/////////////");
    }

    public void changeAllWork() {
        System.out.println("/////////////");
        printEmployees();
        employeeFileWorker.changeAllWork(JobType.PROGRAMMER, JobType.POLICEMAN);
        printEmployees();
        System.out.println("/////////////");
    }

    public void getByName() {
        System.out.println("/////////////");
        printEmployees();
        try {
            System.out.println(employeeFileWorker.getByName("PROGRAMMER"));
            System.out.println(employeeFileWorker.getByName("Firstttt"));
        } catch (EmployeeNotFound ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("/////////////");
    }

    public void getByJob() {
        System.out.println("/////////////");
        printEmployees();
        List<Employee> allProgrammer = employeeFileWorker.getByJob(JobType.PROGRAMMER);
        for (Employee programmer : allProgrammer) {
            System.out.println(programmer);
        }
        System.out.println("/////////////");
    }

    public void delete() {
        System.out.println("/////////////");
        employeeFileWorker.delete(new Employee("Second", 31, 10000, JobType.DOCTOR));
        employeeFileWorker.delete(new Employee("PROGRAMMER_2", 24, 30000, JobType.POLICEMAN));
    }

    public void close() {
        try {
            employeeFileWorker.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void printEmployees() {
        List<Employee> employees = employeeFileWorker.getEmployees();
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }
}
