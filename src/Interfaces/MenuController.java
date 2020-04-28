package Interfaces;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable{

    @FXML
    Pane MyPane;

    @FXML
    public void showCargaMasiva() throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader =  new FXMLLoader();
        loader.setLocation(getClass().getResource("CargaMasiva.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Carga Masiva");
        stage.show();
        Stage auxStage = (Stage)MyPane.getScene().getWindow();
        auxStage.setIconified(true);
    }

    @FXML
    public void showLibreria() throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader =  new FXMLLoader();
        loader.setLocation(getClass().getResource("Login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
        Stage auxStage = (Stage)MyPane.getScene().getWindow();
        auxStage.setIconified(true);
    }

    @FXML
    public void showReportes() throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader =  new FXMLLoader();
        loader.setLocation(getClass().getResource("Reportes.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Reportes");
        stage.show();
        Stage auxStage = (Stage)MyPane.getScene().getWindow();
        auxStage.setIconified(true);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
