package com.example.javafx.Model;

import java.util.ArrayList;
import java.util.Collections; // do operacji takich jak sortowane
import java.util.Comparator; //tworzenie wlasnych komparatoroów, które definiją reguły sortowania
import java.util.List;



public class ClassEmployee {
    private String groupName;
    private int maxEmployees;

    private List<Employee> employees;

    public ClassEmployee(String groupName, int maxEmployees) {
        this.groupName = groupName;
        this.maxEmployees = maxEmployees;
        this.employees = new ArrayList<>();
    }

    public String getGroupName() {
        return groupName;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setMaxEmployees(int maxEmployees) {
        this.maxEmployees = maxEmployees;
    }



    public int getMaxEmployees() {
        return maxEmployees;
    }

    public int getEmployeeCount() {
        return employees.size();
    }



    public void addEmployee(Employee employee) {

        if (employees.size() >= maxEmployees) {
            System.out.println("Nie można dodać pracownika! Grupa jest już pełna!.");
            return;
        }


        for (Employee emp : employees) {
            if (emp.getFirst_name().equals(employee.getFirst_name()) &&
                    emp.getLast_name().equals(employee.getLast_name())) {
                System.out.println("Employee już istnieje: " + emp.getFirst_name() + " " + emp.getLast_name());
                return;
            }
        }

        employees.add(employee);
        System.out.println("Employee dodany: " + employee.getFirst_name() + " " + employee.getLast_name());
    }



    public void addSalary(Employee employee, double amount) {
        if (employees.contains(employee)) {
            employee.salary += amount;
            System.out.println("Wypłata zmieniona dla: " + employee.getFirst_name() + " " + employee.getLast_name());
        } else {
            System.out.println("Employee nie znaleziony.");
        }
    }

    public void removeEmployee(Employee employee) {
        if (employees.remove(employee)) {
            System.out.println("Employee usunięty: " + employee.getFirst_name() + " " + employee.getLast_name());
            // Zaktualizowanie liczby dostępnych miejsc
            maxEmployees++;
            System.out.println("Wolne miejsca w grupie: " + maxEmployees);
        } else {
            System.out.println("Employee nie znaleziony");
        }
    }




    public void changeCondition(Employee employee, EmployeeCondition condition) {
        if (employees.contains(employee)) {
            employee.condition = condition;  // Update the condition
            System.out.println("Condition zmieniony na: " + employee.getFirst_name() + " " + employee.getLast_name());
        } else {
            System.out.println("Employee nie znaleziony.");
        }
    }
/*
    public Employee search(String lastName) {
        //  Comparator do porównywania nazwisk - comparator używany żeby znalezc indeks w posortowanej liscie
        Comparator<Employee> lastNameComparator = (e1, e2) -> e1.getLast_name().compareTo(e2.getLast_name());

        // Sortujemy listę przed wyszukiwaniem
        List<Employee> sortedList = new ArrayList<>(employees);
        Collections.sort(sortedList, lastNameComparator);

        // Tworzymy obiekt `Employee` tylko z nazwiskiem do porównania
        Employee dummy = new Employee();
        dummy.last_name = lastName;

        // Używamy `Collections.binarySearch()` do wyszukiwania
        int index = Collections.binarySearch(sortedList, dummy, lastNameComparator);

        if (index >= 0) {
            Employee foundEmployee = sortedList.get(index);
            System.out.println("Employee znaleziony: ");
            foundEmployee.printing();  // Wyświetlamy dane pracownika
            return foundEmployee;
        } else {
            System.out.println("Employee nie znaleziony.");
            return null;
        }
    }
*/
/*
    public Employee search(String lastName) {
        // Sortujemy listę przed wyszukiwaniem
        List<Employee> sortedList = new ArrayList<>(employees);
        Collections.sort(sortedList); // Używa metody compareTo() z klasy Employee do sortowania listy.

        // Tworzymy obiekt `Employee` tylko z nazwiskiem do porównania
        Employee dummy = new Employee();
        dummy.last_name = lastName;

        // Wyszukuje element w posortowanej liście za pomocą compareTo().
        int index = Collections.binarySearch(sortedList, dummy);

        if (index >= 0) {
            Employee foundEmployee = sortedList.get(index);
            System.out.println("Employee znaleziony: ");
            foundEmployee.printing(); // Wyświetlamy dane pracownika
            return foundEmployee;
        } else {
            System.out.println("Employee nie znaleziony.");
            return null;
        }
    }

*/
    public List<Employee> searchPartial(String partial) {
        List<Employee> foundEmployees = new ArrayList<>();
        for (Employee emp : employees) {
            if (emp.getFirst_name().contains(partial) || emp.getLast_name().contains(partial)) {
                foundEmployees.add(emp);
            }
        }
        return foundEmployees;
    }

    public long countByCondition(EmployeeCondition condition) {
        return employees.stream().filter(emp -> emp.condition == condition).count();
    }

    public void summary() {
        for (Employee emp : employees) {
            emp.printing();
            System.out.println("--------------------");
        }
    }

    public List<Employee> sortByName() {
        List<Employee> sortedList = new ArrayList<>(employees);
        Collections.sort(sortedList);
        return sortedList;
    }
//new Comparator<Employee>() - sposob tworzenia anonimowej klasy implementujacej interfejs
   /* public List<Employee> sortBySalary() {
        List<Employee> sortedList = new ArrayList<>(employees);

        Collections.sort(sortedList, new Comparator<Employee>() {
            @Override
            public int compare(Employee e1, Employee e2) {
                //wartosc ujemna gdy e1 jest mniejsze od e2, 0 jak oba obiekty są równe
                return Double.compare(e2.getSalary(), e1.getSalary());
            }
        });
        return sortedList;
    }*/

    public List<Employee> sortBySalary() {
        List<Employee> sortedList = new ArrayList<>(employees);
        // Używamy wyrażenia lambda do porównania pracowników na podstawie wynagrodzenia
        Collections.sort(sortedList, (e1, e2) -> Double.compare(e2.getSalary(), e1.getSalary()));
        return sortedList;
    }



    public Employee max() {
        return Collections.max(employees, Comparator.comparingDouble(Employee::getSalary));
    }



}
