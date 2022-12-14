package sample.controller;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
public class RootLayoutController {
    //Para salir del programa al pulsar Close
    public void handleExit(ActionEvent actionEvent) {
        System.exit(0);
    }
    //Botón "About"
    public void handleHelp(ActionEvent actionEvent) {
        Alert a = new Alert (Alert.AlertType.INFORMATION);
        a.setTitle("Ayuda");
        a.setHeaderText("Gestión de empleados");
        a.setContentText("Este proyeto se basa en SWTestAcademy projects y ha sido realizado por Jesús García Segovia");
        a.show();
    }
}