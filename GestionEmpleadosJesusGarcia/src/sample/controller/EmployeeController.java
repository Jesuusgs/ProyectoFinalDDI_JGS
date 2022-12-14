package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.model.Employee;
import sample.model.EmployeeDAO;

import java.sql.Date;
import java.sql.SQLException;

public class EmployeeController {

    @FXML
    private TextField empIdText;
    @FXML
    private TextArea resultArea;
    @FXML
    private TextField newEmailText;
    @FXML
    private TextField nameText;
    @FXML
    private TextField surnameText;
    @FXML
    private TextField emailText;
    @FXML
    private TableView employeeTable;
    @FXML
    private TableColumn<Employee, Integer>  empIdColumn;
    @FXML
    private TableColumn<Employee, String>  empNameColumn;
    @FXML
    private TableColumn<Employee, String> empLastNameColumn;
    @FXML
    private TableColumn<Employee, String> empEmailColumn;
    @FXML
    private TableColumn<Employee, String> empPhoneNumberColumn;
    @FXML
    private TableColumn<Employee, Date> empHireDateColumn;

    //Búsqueda de un empleado
    @FXML
    private void searchEmployee (ActionEvent actionEvent) throws ClassNotFoundException, SQLException {
        try {
            //Obtener la información necesaria
            Employee emp = EmployeeDAO.searchEmployee(empIdText.getText());
            //Cargar el empleado en la tabla
            populateAndShowEmployee(emp);
            cleanComponents();
        } catch (SQLException e) {
            resultArea.setText("Se ha producido un error al recuperar el empleado de la base de datos.\n" + e);
        }
    }

    //Buscar todos los empleados
    @FXML
    private void searchEmployees(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            //Obtener los datos necesarios de los empleados
            ObservableList<Employee> empData = EmployeeDAO.searchEmployees();
            //Mostrarlos en la tabla
            populateEmployees(empData);
            cleanComponents();
        } catch (SQLException e){
            System.out.println("Se ha producido un error al recuperar los empleados de la base de datos.\n" +e);
        }
    }

    @FXML
    private void initialize () {

        empIdColumn.setCellValueFactory(cellData -> cellData.getValue().employeeIdProperty().asObject());
        empNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        empLastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        empEmailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        empPhoneNumberColumn.setCellValueFactory(cellData -> cellData.getValue().phoneNumberProperty());
        empHireDateColumn.setCellValueFactory(cellData -> cellData.getValue().hireDateProperty());
    }

    //Mostrar empleado
    @FXML
    private void populateEmployee (Employee emp) throws ClassNotFoundException {
        //Declarar un onbservablelist para la tabla
        ObservableList<Employee> empData = FXCollections.observableArrayList();
        //Añado el empleado
        empData.add(emp);
        //envío los datos a la tabla
        employeeTable.setItems(empData);
    }

    //Escribir en el textarea la información del empleado
    @FXML
    private void setEmpInfoToTextArea (Employee emp) {
        resultArea.setText("Nombre: " + emp.getFirstName() + "\n" +
                "Apellido: " + emp.getLastName());
    }

    //enviar el empleado a la tabla y mostrar su información en el textarea
    @FXML
    private void populateAndShowEmployee(Employee emp) throws ClassNotFoundException {
        if (emp != null) {
            populateEmployee(emp);
            setEmpInfoToTextArea(emp);
            cleanComponents();
        } else {
            resultArea.setText("Este empleado no existe\n");
        }
    }

    private void cleanComponents() {
        empIdText.setText("");
        newEmailText.setText("");
        nameText.setText("");
        emailText.setText("");
        surnameText.setText("");
    }

    @FXML
    private void populateEmployees (ObservableList<Employee> empData) throws ClassNotFoundException {
        //Enviar datos a la tabla
        employeeTable.setItems(empData);
    }

    //actualizar el email del empleado
    @FXML
    private void updateEmployeeEmail (ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            EmployeeDAO.updateEmpEmail(empIdText.getText(),newEmailText.getText());
            cleanComponents();
            resultArea.setText("El email del empleado con ID " + empIdText.getText() + " ha sido actualizado correctamente.\n");
        } catch (SQLException e) {
            resultArea.setText("Ha ocurrido un problema al actualizar el email: EMP ID y NUEVO EMAIL deben contener datos.\n" + e);
        }
    }

    //Insertar empleado a la base de datos
    @FXML
    private void insertEmployee (ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            EmployeeDAO.insertEmp(nameText.getText(),surnameText.getText(),emailText.getText());
            cleanComponents();
            resultArea.setText("El empleado ha sido insertado correctamente \n");
        } catch (SQLException e) {
            resultArea.setText("Ha ocurrido un problema en la inserción del empleado: Todos los campos deben estar rellenos y el email no puede coincidir con uno ya existente.\n" + e);
        }
    }

    //Borrar un empleado con el id escrito en empIdText
    @FXML
    private void deleteEmployee (ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            EmployeeDAO.deleteEmpWithId(empIdText.getText());
            resultArea.setText("El empleado con id : " + empIdText.getText() + " ha sido eliminado correctamente.\n");
            cleanComponents();
        } catch (SQLException e) {
            resultArea.setText("Ha ocurrido un problema al eliminar al empleado. El ID debe existir en la base de datos y debe estar escrito en el campo EMP ID.\n" + e);
        }
    }
}