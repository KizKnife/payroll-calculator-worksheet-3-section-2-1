package com.pluralsight;

import java.io.*;
import java.util.Scanner;

public class PayrollCalculator {
    public static void main(String[] args) {
        try {
            Scanner input = new Scanner(System.in);
            System.out.print("Enter the name of the file employee file to process: ");
            String employeeFileName = input.nextLine();

            System.out.print("Enter the name of the payroll file to create: ");
            String payrollFileName = input.nextLine();

            Scanner fileScanner = new Scanner(new File(employeeFileName));
            BufferedWriter writer = new BufferedWriter(new FileWriter(payrollFileName));

            fileScanner.nextLine();
            if (payrollFileName.endsWith(".csv")) {
                writer.write("id|name|gross pay\n");
            } else if (payrollFileName.endsWith(".json")) {
                writer.write("[\n");
            }

            boolean first = true;
            while (fileScanner.hasNextLine()) {

                if (payrollFileName.endsWith(".csv")) {
                    String line = fileScanner.nextLine();

                    String[] parts = line.split("\\|");

                    String id = parts[0];
                    String name = parts[1];
                    double payRate = Double.parseDouble(parts[2]);
                    double hours = Double.parseDouble(parts[3]);

                    double grossPay = payRate * hours;

                    String output = String.format("%s|%s|%.2f\n", id, name, grossPay);

                    writer.write(output);
                } else if (payrollFileName.endsWith(".json")) {
                    String line = fileScanner.nextLine();

                    String[] parts = line.split("\\|");

                    String id = parts[0];
                    String name = parts[1];
                    double payRate = Double.parseDouble(parts[2]);
                    double hours = Double.parseDouble(parts[3]);

                    double grossPay = payRate * hours;

                    if (!first) {
                        writer.write(",\n");
                    }

                    String output = String.format(
                            "     { \"id\": %s, \"name\" : \"%s\", \"grossPay\" : %.2f }",
                            id, name, grossPay);

                    writer.write(output);

                    first = false;
                }
            }

            if (payrollFileName.endsWith(".json")) {
                writer.write("\n]");
            }

            writer.close();
            fileScanner.close();
        }
        catch (IOException e) {
            System.out.println("ERROR: An unexpected error occurred");
            e.printStackTrace();
        }

        //showEmployees();
    }

    public static void showEmployees() {
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
