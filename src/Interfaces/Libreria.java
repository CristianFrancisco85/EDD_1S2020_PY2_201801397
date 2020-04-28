package Interfaces;

import Clases.Data;
import Clases.Usuario;
import Dialogos.EditarPerfil;
import Estructuras.LinkedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class Libreria implements Initializable{

    public static int User;

    @FXML
    AnchorPane pane;


    @FXML
    public void EditarPerfil(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../Dialogos/EditarPerfil.fxml"));
            Parent root = fxmlLoader.load();
            EditarPerfil controller = fxmlLoader.<EditarPerfil>getController();
            controller.setUsuario(User);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Editar Perfil");
            stage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    public void AgregarUsuario(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../Dialogos/CrearPerfil.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Crear Perfil");
            stage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    public void EliminarUsuario(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Usuario");
        alert.setHeaderText("Eliminar Usuario");
        alert.setContentText("¿Esta seguro de querer eliminar su usuario? Este cambio es irreversible");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            LinkedList<Usuario> AuxList= Data.getUsuariosStructure().getValue(User);
            //SE BUSCA USARIO
            for(int i=0;i<AuxList.getSize();i++){
                try {
                    if (AuxList.getValue(i).getCarnet()==User) {
                        AuxList.removeByPosition(i);
                        break;
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
            alert.setTitle("Eliminacion");
            alert.setHeaderText(null);
            alert.setContentText("El usuario ha sido eliminado exitosamente");
            alert.showAndWait();
            Stage stage = (Stage) pane.getScene().getWindow();
            stage.close();
        }


    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
