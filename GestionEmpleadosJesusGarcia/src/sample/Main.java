package sample;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.IOException;
public class Main extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) {
        //1) Declaración del primarystage
        this.primaryStage = primaryStage;
        //Título del primarystage
        this.primaryStage.setTitle("Gestión de empleados");
        //2) Inicialización del rootlayout mediante un método
        initRootLayout();
        //3) Mostrar la vista de EmployeeOperations
        showEmployeeView();
    }

    public void initRootLayout() {
        try {
            //Carga del rootlayout desde el FXML
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            //Mostrar la escena
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            //mostrat el primarystage
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void showEmployeeView() {
        try {
            //Carga del EmployeeView desde EmployeeView.fxml
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/EmployeeView.fxml"));
            AnchorPane employeeOperationsView = (AnchorPane) loader.load();
            // Colocarlo en el centro
            rootLayout.setCenter(employeeOperationsView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String args) {
        launch(args);
    }
}
