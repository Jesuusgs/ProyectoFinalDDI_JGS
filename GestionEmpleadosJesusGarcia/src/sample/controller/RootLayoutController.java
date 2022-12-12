package sample.controller;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import sample.Main;
public class RootLayoutController {
    //Exit the program
    public void handleExit(ActionEvent actionEvent) {
        System.exit(0);
    }
    //Help Menu button behavior
    public void handleHelp(ActionEvent actionEvent) {
        Alert alert = new Alert (Alert.AlertType.INFORMATION);
        alert.setTitle("Ayuda");
        alert.setHeaderText("Gestión de empleados");
        alert.setContentText("Este proyeto se basa en SWTestAcademy projects y ha sido realizado por Jesús García Segovia");
        alert.show();
    }
}