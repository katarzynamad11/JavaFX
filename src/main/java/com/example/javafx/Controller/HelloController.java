package com.example.javafx.Controller;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import com.example.javafx.Model.*;
import java.time.LocalDate;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.chart.PieChart;
import javafx.scene.input.MouseEvent;
import java.util.ArrayList;
import java.util.List;
public class HelloController {

    @FXML
    private TableView<Employee> employeeTableView;  // Tabela do wyświetlania danych

    @FXML
    private TableColumn<Employee, String> firstNameColumn;  // Kolumna dla imienia

    @FXML
    private TableColumn<Employee, String> lastNameColumn;  // Kolumna dla nazwiska

    @FXML
    private TableColumn<Employee, LocalDate> birthDateColumn; // Kolumna dla daty urodzenia

    @FXML
    private TableColumn<Employee, Double> salaryColumn;  // Kolumna dla wynagrodzenia

    @FXML
    private TableColumn<Employee, String> conditionColumn; // Kolumna dla stanu pracownika

    @FXML
    private TextField employeeNameField;  // Pole tekstowe dla imienia

    @FXML
    private TextField employeeNameField1; // Pole tekstowe dla nazwiska

    @FXML
    private DatePicker employeeBirthDatePicker; // Wybór daty urodzenia

    @FXML
    private TextField employeeSalaryField; // Pole tekstowe dla wynagrodzenia

    @FXML
    private ComboBox<EmployeeCondition> conditionComboBox; // ComboBox do wyboru stanu pracownika

    @FXML
    private TextField groupNameField;  // Pole tekstowe do wprowadzania nazwy grupy

    @FXML
    private TextField maxEmployeesField;  // Pole tekstowe do wprowadzania maksymalnej liczby pracowników

    @FXML
    private ListView<String> groupListView;  // ListView do wyświetlania listy grup

    @FXML
    private TextField textFilterField;



    @FXML
    private PieChart groupPieChart;  // Wykres kołowy do wizualizacji zapełnienia grupy

    // Observable list do wyświetlania nazw grup w ListView
    private ObservableList<String> groupDisplayList = FXCollections.observableArrayList();

    // Przechowuje wszystkie grupy
    private ClassContainer classContainer = new ClassContainer();

    // Lista pracowników, która będzie wyświetlana w tabeli
    private ObservableList<Employee> employees = FXCollections.observableArrayList();


    // Metoda inicjalizująca
    @FXML
    public void initialize() {
        // Ustawienie tabeli, kolumn oraz ich powiązań z danymi
        firstNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirst_name()));
        lastNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLast_name()));
        birthDateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getBirth_year()));
        salaryColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getSalary()).asObject());
        conditionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCondition().toString()));

        DataInitializer dataInitializer = new DataInitializer();
        employees.setAll(dataInitializer.initializeEmployees());

        // Ustawienie listy danych w tabeli
        employeeTableView.setItems(employees);

        // Inicjalizacja ComboBox
        conditionComboBox.setItems(FXCollections.observableArrayList(EmployeeCondition.values()));
        conditionComboBox.setValue(EmployeeCondition.obecny); // Domyślny stan

        // Inicjalizacja grup
        groupListView.setItems(groupDisplayList);

    }

    // Metoda dodawania nowego pracownika
    @FXML
    public void addEmployee(MouseEvent event) {
        String firstName = employeeNameField.getText();
        String lastName = employeeNameField1.getText();
        String salaryText = employeeSalaryField.getText();
        LocalDate birthDate = employeeBirthDatePicker.getValue();
        EmployeeCondition condition = conditionComboBox.getValue();

        // Sprawdzanie poprawności danych
        if (!firstName.isEmpty() && !lastName.isEmpty() && !salaryText.isEmpty() && condition != null) {
            try {
                double salary = Double.parseDouble(salaryText);
                // Tworzenie nowego pracownika
                Employee newEmployee = new Employee(firstName, lastName, condition, birthDate, salary);

                // Dodanie pracownika do listy
                employees.add(newEmployee);

                // Czyszczenie pól po dodaniu
                employeeNameField.clear();
                employeeNameField1.clear();
                employeeSalaryField.clear();
                conditionComboBox.getSelectionModel().clearSelection();
                employeeBirthDatePicker.setValue(null);
            } catch (NumberFormatException e) {
                // Obsługa błędnego formatu wynagrodzenia
                System.out.println("Invalid salary input.");
            }
        } else {
            System.out.println("Please fill in all fields.");
        }
    }

    // Metoda usuwania pracownika
    @FXML
    public void removeEmployee(MouseEvent event) {
        Employee selectedEmployee = employeeTableView.getSelectionModel().getSelectedItem();

        if (selectedEmployee != null) {
            // Sprawdzamy, do jakiej grupy należy pracownik
            String groupName = selectedEmployee.getGroupName();

            if (groupName != null) {
                // Usuwamy pracownika z grupy
                ClassEmployee classEmployee = classContainer.getClassByName(groupName);

                if (classEmployee != null) {
                    classEmployee.removeEmployee(selectedEmployee);
                    System.out.println("Usunięto pracownika z grupy: " + groupName);

                }
            }

            // Usuwamy pracownika z ogólnej listy pracowników
            employees.remove(selectedEmployee);
            System.out.println("Usunięto pracownika: " + selectedEmployee.getFirst_name() + " " + selectedEmployee.getLast_name());

            // Odświeżamy tabelę
            employeeTableView.refresh();
        } else {
            System.out.println("Nie wybrano pracownika do usunięcia.");
        }
    }



    @FXML
    public void addGroup(MouseEvent event) {
        String groupName = groupNameField.getText();
        String maxEmployeesText = maxEmployeesField.getText();

        if (!groupName.isEmpty() && !maxEmployeesText.isEmpty()) {
            try {
                int maxEmployees = Integer.parseInt(maxEmployeesText);

                // Dodajemy grupę do ClassContainer
                classContainer.addClass(groupName, maxEmployees);

                String groupDisplayText = groupName + " (Max: " + maxEmployees + ")";
                groupDisplayList.add(groupDisplayText);

                // Czyszczenie pól tekstowych po dodaniu grupy
                groupNameField.clear();
                maxEmployeesField.clear();

                // Zaktualizuj wykres kołowy, jeśli wybrano grupę
                if (classContainer.getClass(groupName) != null) {
                    updatePieChart(groupName);
                }
            } catch (NumberFormatException e) {
                System.out.println("Nieprawidłowa liczba pracowników.");
            }
        }
    }

    // Metoda do usuwania grupy
    @FXML
    public void removeGroup(MouseEvent event) {
        // Pobieramy nazwę wybranej grupy z ListView
        String selectedGroupName = groupListView.getSelectionModel().getSelectedItem();

        if (selectedGroupName != null) {
            // Wyodrębniamy samą nazwę grupy przed "(Max: X)" i usuwamy zbędne spacje
            String groupName = selectedGroupName.split(" \\(Max")[0].trim();

            // Usuwamy grupę z klasy, używając wyodrębnionej nazwy
            classContainer.removeClass(groupName);

            // Usuwamy pełną nazwę grupy z ListView
            groupDisplayList.remove(selectedGroupName);
        } else {
            System.out.println("Nie wybrano grupy do usunięcia.");
        }
    }


    private void updatePieChart(String groupName) {
        // Pobranie grupy na podstawie jej nazwy
        ClassEmployee group = classContainer.getClass(groupName);

        if (group != null) {
            // Obliczenie liczby pracowników w grupie
            int totalEmployees = group.getEmployeeCount();
            // Pobranie maksymalnej liczby pracowników w grupie
            int maxEmployees = group.getMaxEmployees();

            // Obliczenie danych do wykresu kołowego
            PieChart.Data filled = new PieChart.Data("Filled", totalEmployees);
            PieChart.Data available = new PieChart.Data("Available", maxEmployees - totalEmployees);

            // Czyszczenie poprzednich danych wykresu
            groupPieChart.getData().clear();
            // Dodanie nowych danych do wykresu
            groupPieChart.getData().addAll(filled, available);
        }
    }

    @FXML
    private void addEmployeeToGroup() {
        // Pobieramy wybraną grupę z ListView
        String groupInfo = groupListView.getSelectionModel().getSelectedItem();
        if (groupInfo == null) {
            System.out.println("Wybierz grupę!");
            return;
        }

        // Wyodrębnienie samej nazwy grupy (np. "IT" z "IT (max 5)")
        String groupName = groupInfo.split(" \\(Max")[0].trim(); // Dzielimy tekst na nazwę grupy

        // Pobieramy pracownika z TableView
        Employee selectedEmployee = employeeTableView.getSelectionModel().getSelectedItem();
        if (selectedEmployee == null) {
            System.out.println("Wybierz pracownika!");
            return;
        }

        // Pobieramy grupę z ClassContainer
        ClassEmployee group = classContainer.getClass(groupName);
        if (group != null) {
            // Dodajemy pracownika do grupy
            group.addEmployee(selectedEmployee);

            // Po dodaniu pracownika, odświeżamy wykres kołowy
            updatePieChart(groupName);  // Zaktualizowanie wykresu kołowego dla tej grupy
        } else {
            System.out.println("Grupa o nazwie " + groupName + " nie istnieje.");
        }
    }

    @FXML
    private ListView<Employee> groupEmployeeListView;  // ListView dla pracowników grupy

    // Metoda, która jest wywoływana, gdy użytkownik wybiera grupę
    @FXML
    private void onGroupSelected() {
        // Pobieramy wybraną grupę
        String selectedGroupName = groupListView.getSelectionModel().getSelectedItem();

        if (selectedGroupName != null) {
            // Pobieramy grupę z ClassContainer
            ClassEmployee group = classContainer.getClass(selectedGroupName.split(" ")[0]);

            if (group != null) {
                // Ustawiamy ListView, aby wyświetlał pracowników tej grupy
                groupEmployeeListView.setItems(FXCollections.observableArrayList(group.getEmployees()));
            }
        }
    }



    @FXML
    private void modifyEmployeeData() {
        // Pobieramy wybranego pracownika z ListView
        Employee selectedEmployee = employeeTableView.getSelectionModel().getSelectedItem();

        // Jeśli pracownik został wybrany, wyświetlamy jego dane w formularzu
        if (selectedEmployee != null) {
            employeeNameField.setText(selectedEmployee.getFirst_name());
            employeeNameField1.setText(selectedEmployee.getLast_name());
            employeeSalaryField.setText(String.valueOf(selectedEmployee.getSalary()));
            employeeBirthDatePicker.setValue(selectedEmployee.getBirth_year());
            conditionComboBox.setValue(selectedEmployee.getCondition());
        } else {
            System.out.println("Wybierz pracownika!");
        }
    }

    @FXML
    private void saveModifiedEmployeeData() {
        Employee selectedEmployee = employeeTableView.getSelectionModel().getSelectedItem();

        if (selectedEmployee != null) {
            try {
                String firstName = employeeNameField.getText().trim();
                String lastName = employeeNameField1.getText().trim();
                String salaryText = employeeSalaryField.getText().trim();
                LocalDate birthYear = employeeBirthDatePicker.getValue();
                EmployeeCondition condition = conditionComboBox.getValue(); // Poprawne pobranie wartości enum

                if (firstName.isEmpty() || lastName.isEmpty() || salaryText.isEmpty() || birthYear == null || condition == null) {
                    throw new IllegalArgumentException("Wszystkie pola muszą być uzupełnione.");
                }

                double salary = Double.parseDouble(salaryText);
                if (salary < 0) throw new NumberFormatException();

                selectedEmployee.setFirst_name(firstName);
                selectedEmployee.setLast_name(lastName);
                selectedEmployee.setSalary(salary);
                selectedEmployee.setBirth_year(birthYear);
                selectedEmployee.setCondition(condition); // Ustawienie wartości enum

                employeeTableView.refresh();
                clearFormFields();

            } catch (IllegalArgumentException e) {

            }
        }
    }



    private void clearFormFields() {
        employeeNameField.clear();
        employeeNameField1.clear();
        employeeSalaryField.clear();
        employeeBirthDatePicker.setValue(null);  // Ustawienie na null lub domyślną datę
        conditionComboBox.setValue(null);  // Ustawienie na null lub domyślną wartość
    }


    @FXML
    private void modifyGroupData() {
        String selectedGroupInfo = groupListView.getSelectionModel().getSelectedItem();

        if (selectedGroupInfo != null) {
            // Zakładając, że format w `groupDisplayList` to "Nazwa Grupy (Max: x)"
            String[] parts = selectedGroupInfo.split(" \\(Max: ");
            String groupName = parts[0]; // Pobieramy nazwę grupy
            String maxEmployees = parts[1].replace(")", ""); // Pobieramy maksymalną liczbę pracowników, usuwamy nawias

            groupNameField.setText(groupName); // Ustawiamy nazwę grupy w polu tekstowym
            maxEmployeesField.setText(maxEmployees); // Ustawiamy liczbę pracowników w polu tekstowym
        }
    }


    @FXML
    private void saveModifiedGroupData() {
        String selectedGroupInfo = groupListView.getSelectionModel().getSelectedItem();

        if (selectedGroupInfo != null) {
            // Rozdziel dane grupy: nazwa i maksymalna liczba pracowników
            String[] parts = selectedGroupInfo.split(" \\(Max: ");
            String oldGroupName = parts[0]; // Stara nazwa grupy
            int oldMaxEmployees = Integer.parseInt(parts[1].replace(")", "")); // Stara liczba maksymalnych pracowników

            // Pobieramy nowe wartości z pól tekstowych
            String newGroupName = groupNameField.getText();
            int newMaxEmployees = Integer.parseInt(maxEmployeesField.getText());

            // Znajdź grupę w `classContainer` i zaktualizuj jej dane
            ClassEmployee groupToModify = classContainer.getClass(oldGroupName);
            if (groupToModify != null) {
                groupToModify.setGroupName(newGroupName); // Aktualizuj nazwę grupy
                groupToModify.setMaxEmployees(newMaxEmployees); // Aktualizuj maksymalną liczbę pracowników

                // Aktualizuj dane w `groupDisplayList`
                int index = groupDisplayList.indexOf(selectedGroupInfo);
                if (index != -1) {
                    String updatedGroupInfo = newGroupName + " (Max: " + newMaxEmployees + ")";
                    groupDisplayList.set(index, updatedGroupInfo);
                }
            } else {
                System.out.println("Grupa nie została znaleziona w classContainer.");
            }

            // Wyczyszczenie pól tekstowych
            groupNameField.clear();
            maxEmployeesField.clear();
        }
    }

    @FXML
    private void onFilterTextChanged() {
        String filterText = textFilterField.getText().toLowerCase(); // Pobierz wprowadzony tekst (na małe litery)

        // Filtrowanie danych
        FilteredList<Employee> filteredData = new FilteredList<>(employees, employee ->
                employee.getLast_name().toLowerCase().contains(filterText) // Filtrujemy po nazwisku
        );

        // Przypisz przefiltrowane dane do TableView
        SortedList<Employee> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(employeeTableView.comparatorProperty());
        employeeTableView.setItems(sortedData);
    }




    @FXML
    private void refreshTable() {
        // Odświeżamy dane w tabeli. Można tu także zaktualizować dane lub ponownie załadować listę.
        employeeTableView.setItems(null);  // Najpierw wyczyszczono tabelę
        employeeTableView.setItems(employees);  // Ponownie przypisujemy dane
        textFilterField.clear();
    }

    @FXML
    public void searchGroupEmployees() {
        String selectedGroupName = groupListView.getSelectionModel().getSelectedItem();

        if (selectedGroupName != null) {
            // Usuń część "(Max: X)" z nazwy grupy
            String cleanedGroupName = selectedGroupName.split(" \\(Max:")[0].trim();

            // Znajdź grupę w kontenerze
            ClassEmployee selectedGroup = classContainer.getClass(cleanedGroupName);

            if (selectedGroup != null) {
                List<Employee> updatedGroupEmployees = new ArrayList<>(selectedGroup.getEmployees());

                updatedGroupEmployees.removeIf(emp -> !employees.contains(emp));

                // Aktualizujemy dane w tabeli
                employeeTableView.setItems(FXCollections.observableArrayList(updatedGroupEmployees));
            } else {
                System.out.println("Nie znaleziono grupy: " + selectedGroupName);
            }
        } else {
            System.out.println("Nie wybrano grupy.");
        }
    }


}
