package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PayrollCalculator {
    public static void main(String[] args) {
        try {
            BufferedReader bufReader = new BufferedReader(new FileReader("employees.csv"));
            String input;
            bufReader.readLine();
            while((input = bufReader.readLine()) != null) {
                Employee employee = getEmployee(input);

                System.out.printf(
                        "ID: %s%n" +
                        "Name: %s%n" +
                        "Hours-worked: %.2fhrs%n" +
                        "Pay-rate: $%.2f%n" +
                        "Gross pay: $%.2f%n%n",
                        employee.getEmployeeId(),
                        employee.getName(),
                        employee.getHoursWorked(),
                        employee.getPayRate(),
                        employee.getGrossPay()
                );
            }
            bufReader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Employee getEmployee(String input) {
        String[] employeeInfo = input.split("\\|");

        return new Employee(
                employeeInfo[0].trim(),
                employeeInfo[1].trim(),
                Double.parseDouble(employeeInfo[2].trim()),
                Double.parseDouble(employeeInfo[3].trim())
        );
    }
}
