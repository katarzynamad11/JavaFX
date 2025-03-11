package com.example.javafx.Model;

import com.example.javafx.Model.Employee;
import com.example.javafx.Model.EmployeeCondition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class EmployeeTableView {

    private TableView<Employee> tableView;
    private ObservableList<Employee> employees;

    public EmployeeTableView() {
        tableView = new TableView<>();
        employees = FXCollections.observableArrayList();

        initializeColumns();
        tableView.setItems(employees);
    }

    private void initializeColumns() {
        TableColumn<Employee, String> firstNameColumn = new TableColumn<>("First Name");
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("first_name"));

        TableColumn<Employee, String> lastNameColumn = new TableColumn<>("Last Name");
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("last_name"));

        TableColumn<Employee, String> birthDateColumn = new TableColumn<>("Birth Date");
        birthDateColumn.setCellValueFactory(new PropertyValueFactory<>("birth_year"));

        TableColumn<Employee, Double> salaryColumn = new TableColumn<>("Salary");
        salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));

        TableColumn<Employee, EmployeeCondition> conditionColumn = new TableColumn<>("Condition");
        conditionColumn.setCellValueFactory(new PropertyValueFactory<>("condition"));

        // Dodanie kolumn do tabeli
        tableView.getColumns().addAll(firstNameColumn, lastNameColumn, birthDateColumn, salaryColumn, conditionColumn);
    }

    public TableView<Employee> getTableView() {
        return tableView;
    }

    public ObservableList<Employee> getEmployees() {
        return employees;
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public void removeEmployee(Employee employee) {
        employees.remove(employee);
    }
}