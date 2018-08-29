package ru.innopolis.io.Employee;

import java.io.Serializable;
import java.util.Objects;

/**
 * Класс сотрудника, данные о котором содержаться в файле
 */
public class Employee implements Serializable {

    private String name;
    private int age;
    private int salary;
    private JobType job;

    public Employee() {
        name = "";
        age = 0;
        salary = 0;
        job = JobType.NO_JOB;
    }

    public Employee(String name, int age, int salary, JobType job) {
        this.name = name;
        this.age = age;
        this.salary = salary;
        this.job = job;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public JobType getJob() {
        return job;
    }

    public void setJob(JobType job) {
        this.job = job;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", salary=" + salary +
                ", job=" + job +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return age == employee.age &&
                Objects.equals(name, employee.name) &&
                job == employee.job;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, job);
    }


}
