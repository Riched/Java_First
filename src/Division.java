import java.security.spec.ECParameterSpec;
import java.util.*;

public class Division {

    public String name;
    public int code;
    public Employee manager;
    public List<Employee> Employees = new ArrayList<>();

    public Division(String line) {
        this.name = line;
    }

    public Division(String name, int code, String employeeName, float salary, int employeeCode, boolean manager) {
        this.code = code;

        if (manager) {
            addNewManager(employeeName, salary, name);
        } else {
            addNewEmployee(employeeName, employeeCode, salary);
        }

    }

    public String getStringForOutput(String sortType, boolean sortDesc) {
        float avSalary = 0;
        int countEmployee = 1;
        String result = "";
        result += this.name + "\n";
        result += "Manager, " + this.manager.getStringForOutput() + "\n";
        avSalary += this.manager.getSalary();

        switch (sortType) {
            case "bySalary":
                if (sortDesc) {
                    Employees.sort(Comparator.comparing(Employee::getSalary).reversed());
                } else {
                    Employees.sort(Comparator.comparing(Employee::getSalary));
                }
            case "byName":
                if (sortDesc) {
                    Employees.sort(Comparator.comparing(Employee::getName).reversed());
                } else {
                    Employees.sort(Comparator.comparing(Employee::getName));
                }

        }

        for (Employee employee : Employees) {
            result += "Employee, " + employee.getStringForOutput() + "\n";
            avSalary += employee.getSalary();
        }

        countEmployee += Employees.stream().count();
        avSalary /= countEmployee;
        result += countEmployee + ", " + avSalary + "\n";

        return result;
    }

    public void addNewManager(String name, float salary, String divisionName) {

        this.manager = new Employee(this.code, name, salary);
        this.name = divisionName;

    }

    public void addNewEmployee(String name, int code, float salary) {
        Employee newEmployee = new Employee(code, name, salary);
        this.Employees.add(newEmployee);
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
