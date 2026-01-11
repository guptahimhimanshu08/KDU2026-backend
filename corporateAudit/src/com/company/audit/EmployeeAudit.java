package com.company.audit;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class EmployeeAudit {
    public static List<Employee> filterEmployees(
            List<Employee> employees,
            Predicate<Employee> condition
    ) {
        if(employees == null || condition == null)return List.of();
        
        return employees.stream()
                        .filter(condition)
                        .collect(Collectors.toList());
    }


    public static List<String> filterEmployeesByNames(
            List<Employee> employees
    ) {
        
        if(employees == null) return List.of();
        
        return employees.stream()
                        .map(Employee::getName)
                        .map(String::toUpperCase)
                        .toList();
    }

    public static double filterEmployeesBySalary(
            List<Employee> employees
    ) {
        
        if(employees == null) return List.of();
        
        return employees.stream()
                        .mapToDouble(Employee::getSalary)
                        .sum();
    }

    public static void main(String[] args){
        List<Employee> employees = Employee.getSampleData();
        
        List<Employee> highEarningEngineers = filterEmployees(
            employees,
            e -> e.getDepartment().equals("ENGINEERING") && e.getSalary() > 70000
        );

        highEarningEngineers.forEach(System.out::println);

        List<String> standardizedNames = filterEmployeesByNames(employees);
        standardizedNames.forEach(System.out::println);

        double totalSalaryBudget = filterEmployeesBySalary(employees);
        System.out.printf("Total Salary Budget: $%.2f%n", totalSalaryBudget);
    }
}
