package com.example.javafx.Model;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassContainer {
    private Map<String, ClassEmployee> classMap;

    public ClassContainer() {
        this.classMap = new HashMap<>();
    }
    public ClassEmployee getClassByName(String groupName) {
        return classMap.get(groupName);
    }



    public List<Employee> getEmployeesByGroup(String groupName) {
        ClassEmployee classEmployee = classMap.get(groupName);
        if (classEmployee != null) {
            return classEmployee.getEmployees();  // Zwróć listę pracowników przypisaną do grupy
        }
        return new ArrayList<>();  // Jeśli grupa nie istnieje, zwróć pustą listę
    }


    // a) Dodaj nową grupę pracowniczą o podanej nazwie i pojemności
    public void addClass(String groupName, int maxEmployees) {
        if (!classMap.containsKey(groupName)) {
            classMap.put(groupName, new ClassEmployee(groupName, maxEmployees));
            System.out.println("Dodano grupę: " + groupName);
        } else {
            System.out.println("Grupa o nazwie " + groupName + " już istnieje.");
        }
    }

    // b) Usuń grupę o podanej nazwie
    public void removeClass(String groupName) {
        if (classMap.remove(groupName) != null) {
            System.out.println("Usunięto grupę: " + groupName);
        } else {
            System.out.println("Grupa o nazwie " + groupName + " nie została znaleziona.");
        }
    }

    // c) Zwraca listę pustych grup/ zwraca liste kluczy(nazw grup)
    public List<String> findEmpty() {
        List<String> emptyGroups = new ArrayList<>();
        for (Map.Entry<String, ClassEmployee> entry : classMap.entrySet()) {
            if (entry.getValue().getEmployeeCount() == 0) {
                emptyGroups.add(entry.getKey());
            }
        }
        return emptyGroups;
    }



    // d) Wypisuje informacje o grupach i ich procentowym zapełnieniu
    public void summary() {
        System.out.println("Podsumowanie grup:");
        for (Map.Entry<String, ClassEmployee> entry : classMap.entrySet()) {
            String groupName = entry.getKey();
            ClassEmployee group = entry.getValue();
            double occupancyPercentage = (double) group.getEmployeeCount() / group.getMaxEmployees() * 100;
            System.out.printf("Grupa: %s, Procentowe zapełnienie: %.2f%%\n", groupName, occupancyPercentage);
        }
    }

    public void updateClass(String groupName, String newName, int maxEmployees) {
        ClassEmployee group = classMap.get(groupName);
        if (group != null) {
            group.setGroupName(newName);  // Zmiana nazwy grupy
            group.setMaxEmployees(maxEmployees);  // Zmiana maksymalnej liczby pracowników
        }
    }

    // Dodanie metody do uzyskania grupy na podstawie nazwy
    public ClassEmployee getClass(String groupName) {
        return classMap.get(groupName);
    }

    public List<ClassEmployee> getAllClasses() {
        return new ArrayList<>(classMap.values());
    }
}