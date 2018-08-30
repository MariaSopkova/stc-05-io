package ru.innopolis.io.Employee.FileWorker;

import ru.innopolis.io.Employee.Employee;
import ru.innopolis.io.Employee.Exceptions.EmployeeNotFound;
import ru.innopolis.io.Employee.JobType;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.List;


/**
 * Класс, для работы с данными по сотрудникам.
 */
public class EmployeeFileWorker implements Externalizable {

    protected boolean dataHasChanged; // изменялись ли данные в коллекции
    private List<Employee> employees; // данные о сотрудникам
    private int allEmployeeSalary;    // общая сумма зарплат

    public EmployeeFileWorker() {
        employees = new ArrayList<>();
        dataHasChanged = false;
        allEmployeeSalary = 0;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public boolean isDataHasChanged() {
        return dataHasChanged;
    }

    public int getAllEmployeeSalary() {
        return allEmployeeSalary;
    }

    public void setAllEmployeeSalary(int allEmployeeSalary) {
        this.allEmployeeSalary = allEmployeeSalary;
    }

    /**
     * Найти всех сотрудников с конкретной работой
     *
     * @param currentJob текущая работа
     * @return список сотрудников
     */
    public List<Employee> getByJob(JobType currentJob) {
        List<Employee> currentJobEmployee = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee.getJob() == currentJob) {
                currentJobEmployee.add(employee);
            }
        }
        return currentJobEmployee;
    }

    /**
     * Изменить профессию для всех сотрудников данной профессии
     *
     * @param oldJob старая профессия
     * @param newJob новая профессия
     * @return изменилась ли работа для хотя бы одного сотрудника
     */
    public boolean changeAllWork(JobType oldJob, JobType newJob) {
        boolean result = false;
        for (Employee employee : employees) {
            if (employee.getJob() == oldJob) {
                result = true;
                dataHasChanged = true;
                employee.setJob(newJob);
            }
        }
        return result;
    }

    /**
     * Найти сотрудника по полному совпадению имени. Если таких сотрудников несколько, возвращается первый найденный.
     * Если сотрудник не найден, выбрасывается исключение
     *
     * @param employeeName имя сотрудника
     * @return найденный сотрудник
     * @throws EmployeeNotFound выбрасывается, когда сотрудник не найден
     */
    public Employee getByName(String employeeName) throws EmployeeNotFound {
        Employee employeeByName = employees.stream().filter(employee -> employeeName.equals(employee.getName()))
                .findAny().orElse(null);
        if (employeeByName != null) {
            return employeeByName;
        } else {
            throw new EmployeeNotFound(employeeName);
        }
    }

    /**
     * Добавить сотрудника в список. Проверка на уникальность не производится.
     * Если такой сотрудник уже есть, запись будет дублироваться
     *
     * @param employee сотрудник, которого необходимо добавить
     * @return результат добавления
     */
    public boolean add(Employee employee) {
        if (employee == null) {
            return false;
        }
        if (employees.add(employee)) {
            dataHasChanged = true;
            calculateAllSalary();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Удалить сотрудника из списка
     *
     * @param employee сотрудник, которого необходимо удалить.
     * @return результат удаления
     */
    public boolean delete(Employee employee) {
        if (employee == null) {
            return false;
        }
        if (employees.remove(employee)) {
            dataHasChanged = true;
            calculateAllSalary();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Обновляет данные по сотруднику. Если такого сотрудника нет, он будет создан
     *
     * @param employee
     * @return результат выполнения действия
     */
    public boolean saveOrUpdate(Employee employee) {
        if (employee == null) {
            return false;
        }
        int employeeIndex = employees.indexOf(employee);
        return employeeIndex == -1 ? add(employee) : updateEmpoyee(employeeIndex, employee);
    }

    /**
     * Обновить данные по сотруднику
     *
     * @param positionInCollection
     * @param newEmployeeData      новые данные по сотруднику
     * @return ркзультат обновления
     */
    private boolean updateEmpoyee(int positionInCollection, Employee newEmployeeData) {
        if (positionInCollection == -1 || positionInCollection > employees.size() - 1) {
            return false;
        }
        Employee oldEmployeeData = employees.get(positionInCollection);
        oldEmployeeData.setJob(newEmployeeData.getJob());
        oldEmployeeData.setSalary(newEmployeeData.getSalary());
        oldEmployeeData.setName(newEmployeeData.getName());
        oldEmployeeData.setAge(newEmployeeData.getAge());
        calculateAllSalary();
        dataHasChanged = true;
        return true;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(employees);
        calculateAllSalary();
        out.writeObject(allEmployeeSalary);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        employees = new ArrayList<>();
        employees = (ArrayList<Employee>) in.readObject();
        allEmployeeSalary = (int) in.readObject();
    }

    /**
     * Посчитать сумму зарплат по всем сотрудникам
     * @return сумма зарплат по всем
     */
    private int calculateAllSalary() {
        allEmployeeSalary = 0;
        for (Employee employee : employees) {
            allEmployeeSalary += employee.getSalary();
        }
        return allEmployeeSalary;
    }

    @Override
    public String toString() {
        return "employees=" + employees + "\n" +
                "allEmployeeSalary=" + allEmployeeSalary;
    }
}
