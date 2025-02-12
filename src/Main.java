import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        List<Division> divisions = new ArrayList<>();
        List<String> wrongEmployeeInfo = new ArrayList<>();


        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите путь к файлу: ");
        String filePath = scanner.nextLine();

        scanner.close();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {

                newEmployee(line, divisions, wrongEmployeeInfo);

            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
            return;
        }

        String sortType = "default";
        boolean sortOrderSet = false;
        boolean sortDesc = false;
        boolean writeInFile = false;
        String outputFilePath = "";


        for (String arg : args) {

            if (arg.startsWith("--path=")) {
                outputFilePath = arg.substring("--path=".length());
                continue;
            }

            switch (arg) {
                case "-s":
                    sortType = "bySalary";
                    break;
                case "--s":
                    sortType = "byName";
                    break;
                case "-a":
                    sortOrderSet = true;
                    break;
                case "-d":
                    sortOrderSet = true;
                    sortDesc = true;
                    break;
                case "-o":
                    writeInFile = true;
                    break;
                default:
                    System.out.println("Неизвестный параметр: " + arg);
                    return;
            }
        }

        String result = "";
        if ((sortOrderSet) && (sortType == "default")) {
            System.out.println("Порядок сортировки без метода сортировки не может быть указан.");
            return;
        }

        if (outputFilePath.isEmpty() && writeInFile) {
            System.out.println("Не задан путь для сохранения в файл");
            return;
        } else if (!outputFilePath.isEmpty() && !writeInFile) {
            System.out.println("Не задана запись в файл.");
            return;
        }

        for (Division division : divisions) {
           result += division.getStringForOutput(sortType, sortDesc);
        }

        if (wrongEmployeeInfo.stream().count() != 0) {
            result += "Некорректные данные:\n";

            for (String line : wrongEmployeeInfo) {
                result += line +"\n";
            }
        }

        if (writeInFile) {
            try (FileWriter writer = new FileWriter(outputFilePath)) {
                writer.write(result);
                System.out.println("Файл успешно записан.");
            } catch (IOException e) {
                System.out.println("Ошибка при записи в файл: " + e.getMessage());
            }
        } else {
            System.out.println(result);
        }


    }

    protected static void newEmployee(String line, List<Division> divisions, List<String> wrongEmployeeInfo) {

        String[] employeeInfo = line.split(",");

        String typeOfEmployee = employeeInfo[0].strip();
        boolean manager = false;

        String divisionName = "";
        if (typeOfEmployee.equalsIgnoreCase("manager")) {
            manager = true;
            divisionName = employeeInfo[4];
        }

        int divisionCode;
        float salary;
        int employeeCode = 0;

        try {
            if (manager) {
                divisionCode = Integer.parseInt(employeeInfo[1].strip());
            } else {
                divisionCode = Integer.parseInt(employeeInfo[4].strip());
                employeeCode = Integer.parseInt(employeeInfo[1].strip());
            }
            salary = Integer.parseInt(employeeInfo[3].strip());
            if (salary < 0) {
                wrongEmployeeInfo.add(line);
                return;
            }
        } catch (NumberFormatException e) {
            wrongEmployeeInfo.add(line);
            return;
        }

        for (Division division : divisions) {

            if (division.getCode() == divisionCode) {
                if (manager) {
                    division.addNewManager(employeeInfo[2], salary, divisionName);
                } else {
                    division.addNewEmployee(employeeInfo[2], employeeCode, salary);
                }
                return;
            }

        }

        divisions.add(new Division(divisionName, divisionCode, employeeInfo[2], salary, divisionCode, manager));

    }
}