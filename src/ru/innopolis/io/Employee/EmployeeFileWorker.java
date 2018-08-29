package ru.innopolis.io.Employee;

import ru.innopolis.io.Employee.Exceptions.EmployeeNotFound;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Класс, для работы с файлом с данными по сотрудникам.
 */
public class EmployeeFileWorker implements AutoCloseable {

    public static final String employeeFileName = "employee.dat";
    protected List<Employee> employees;
    protected boolean updateFile = false;

    public EmployeeFileWorker() throws IOException, ClassNotFoundException {
        ReadFromFile();
    }

    public List<Employee> getEmployees() {
        return employees;
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
                updateFile = true;
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
        return employees.add(employee);
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
        return employees.remove(employee);
    }

    @Override
    public void close() throws Exception {
        WriteToFile();
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
        return true;
    }

    /**
     * Прочитать данные из файла
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void ReadFromFile() throws IOException, ClassNotFoundException {
        employees = new ArrayList<>();
        try (ObjectInputStream employeeInputStream = new ObjectInputStream(new FileInputStream(employeeFileName))) {
            employees = (ArrayList<Employee>) employeeInputStream.readObject();
            System.out.println("Read from file...");
        } catch (FileNotFoundException ex) {
            // если файл не найден, будем считать, что он пустой
            System.out.println("File " + employeeFileName + " not found");
        }
    }

    /**
     * Записать данные в файл
     *
     * @throws Exception
     */
    private void WriteToFile() throws Exception {
        try (ObjectOutputStream employeeOutputStream = new ObjectOutputStream(new FileOutputStream(employeeFileName))) {
            System.out.println("Write to file...");
            employeeOutputStream.writeObject(employees);
            System.out.println("OK");
        } catch (FileNotFoundException ex) {
            // если файл не найден, будем считать, что он пустой и при закрытии создадим новый
            System.out.println("File " + employeeFileName + " not found");
        }
    }

}
