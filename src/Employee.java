import java.util.Arrays;

public class Employee {

    public int code;
    public String name;
    public float salary;

    public Employee(int code, String name, float salary) {
        this.code = code;
        this.name = name;
        this.salary = salary;
    }

    public String getStringForOutput() {
        String result = "";
        result += this.code + ", " + this.name + ", " + this.salary;
        return  result;
    }

    public float getSalary(){
        return this.salary;
    }

    public String getName() {
        return name;
    }
}
