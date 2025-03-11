package com.example.javafx.Model;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DataInitializer {

    private List<Employee> employees;


    public DataInitializer() {
        this.employees = new ArrayList<>();
        initializeEmployees();

    }

    public List<Employee> initializeEmployees() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("John", "Doe", EmployeeCondition.obecny, LocalDate.of(1990, 5, 10), 4500));
        employees.add(new Employee("Jane", "Smith", EmployeeCondition.nieobecny, LocalDate.of(1985, 3, 20), 4800));
        employees.add(new Employee("Michael", "Johnson", EmployeeCondition.delegacja, LocalDate.of(1992, 8, 15), 5200));
        employees.add(new Employee("Emily", "Davis", EmployeeCondition.nieobecny, LocalDate.of(1988, 11, 5), 4700));
        employees.add(new Employee("Chris", "Brown", EmployeeCondition.nieobecny, LocalDate.of(1995, 6, 25), 4000));
        employees.add(new Employee("Sarah", "Wilson", EmployeeCondition.nieobecny, LocalDate.of(1993, 9, 12), 5000));
        employees.add(new Employee("David", "Lee", EmployeeCondition.nieobecny, LocalDate.of(1987, 4, 18), 5300));
        employees.add(new Employee("Emma", "Moore", EmployeeCondition.nieobecny, LocalDate.of(1991, 7, 22), 4600));
        employees.add(new Employee("Daniel", "Taylor", EmployeeCondition.nieobecny, LocalDate.of(1984, 2, 8), 4900));
        employees.add(new Employee("Sophia", "Anderson", EmployeeCondition.nieobecny, LocalDate.of(1996, 10, 30), 4200));
    return employees;
    }




    public List<Employee> getEmployees() {
        return employees;
    }

}