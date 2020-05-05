package Dialogos;

import Clases.Data;
import Clases.Usuario;
import Estructuras.LinkedList;
import Interfaces.Libreria;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

public class CrearPerfil implements Initializable {

    @FXML
    TextField carnetTxt;
    @FXML
    TextField nombreTxt;
    @FXML
    TextField apellidoTxt;
    @FXML
    TextField carreraTxt;
    @FXML
    PasswordField passwordTxt;

    @FXML
    public void crearUsuario(){
        Usuario TempUsuario = new Usuario();
        if(checkUser(Integer.parseInt(carnetTxt.getText()))){
            TempUsuario.setCarnet(Integer.parseInt(carnetTxt.getText()));
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Usuario");
            alert.setHeaderText(null);
            alert.setContentText("Usuario ya existe");
            alert.showAndWait();
            return;
        }

        TempUsuario.setNombre(nombreTxt.getText());
        TempUsuario.setApellido(apellidoTxt.getText());
        TempUsuario.setCarrera(carreraTxt.getText());
        try {
            if(passwordTxt.getText().length()>0){
                TempUsuario.setPassword(passwordTxt.getText());
                Data.getUsuariosStructure().add(TempUsuario,TempUsuario.getCarnet());
                Libreria.tempBloque.newCrearUsuario(TempUsuario);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Usuario");
                alert.setHeaderText(null);
                alert.setContentText("Usuario Creado");
                alert.showAndWait();
                Stage stage = (Stage) passwordTxt.getScene().getWindow();
                stage.close();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Usuario");
                alert.setHeaderText(null);
                alert.setContentText("Ingrese una Contraseña");
                alert.showAndWait();
                return;
            }
        }
        catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }

    }

    public boolean checkUser(int carnet){
        LinkedList<Usuario> AuxList= Data.getUsuariosStructure().getValue(carnet);
        if(AuxList==null){
            return true;
        }
        //SE BUSCA USARIO
        for(int i=0;i<AuxList.getSize();i++){
            try {
                if (AuxList.getValue(i).getCarnet()==carnet) {
                    return false;
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        return true;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
