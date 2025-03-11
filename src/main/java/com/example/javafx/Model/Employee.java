package com.example.javafx.Model;
import java.lang.Comparable;
import java.time.LocalDate;
public class Employee implements Comparable<Employee> {

    String first_name;
    String last_name;
    EmployeeCondition condition;
    LocalDate birth_year;
    double salary;
    private String groupName;
    /*
    public Employee() {
        this.first_name = "";
        this.last_name = "";
        this.condition = null;
        this.birth_year = 0;
        this.salary = 0.0;
    }
*/
   public Employee(String first_name, String last_name,EmployeeCondition condition, LocalDate birth_year, double salary) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.condition = condition;
        this.birth_year = birth_year;
        this.salary = salary;

    }
    public String getFirst_name() {
        return this.first_name;
    }

    public String getLast_name() {
        return this.last_name;
    }
    //public EmployeeCondition getCondition(EmployeeCondition condition) {
   //     return this.condition;
   // }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }


    public EmployeeCondition getCondition() {
        return condition;
    }
    public LocalDate getBirth_year() {
        return birth_year;
    }
    public double getSalary() {
        return this.salary;
    }

    // Setter methods
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setCondition(EmployeeCondition condition) {
        this.condition = condition;
    }

    public void setBirth_year(LocalDate birth_year) {
        this.birth_year = birth_year;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }





    public void printing () {
        System.out.println(first_name + " " + last_name);
        System.out.println(condition);
        System.out.println("Rok urodzenia: " + birth_year);
        System.out.println("Wynagrodzenie: "+ salary);
    }
    @Override
    public int compareTo(Employee employee) {
        return this.last_name.compareTo(employee.last_name);
    }
}
