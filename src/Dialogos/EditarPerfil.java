package Dialogos;

import Clases.Data;
import Clases.Usuario;
import Estructuras.LinkedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

public class EditarPerfil implements Initializable {

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

    public Usuario TempUsuario;

    @FXML
    public void ActualizarInformacion(){
        TempUsuario.setCarnet(Integer.parseInt(carnetTxt.getText()));
        TempUsuario.setNombre(nombreTxt.getText());
        TempUsuario.setApellido(apellidoTxt.getText());
        TempUsuario.setCarrera(carreraTxt.getText());
        try {
            if(passwordTxt.getText().length()>0){
                TempUsuario.setPassword(passwordTxt.getText());
            }
        }
        catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Usuario");
        alert.setHeaderText(null);
        alert.setContentText("Informacion Actualizada");
        alert.showAndWait();
        Stage stage = (Stage) passwordTxt.getScene().getWindow();
        stage.close();

    }

    public void setUsuario(int carnet){
        LinkedList<Usuario> AuxList= Data.getUsuariosStructure().getValue(carnet);
        //SE BUSCA USARIO
        for(int i=0;i<AuxList.getSize();i++){
            try {
                if (AuxList.getValue(i).getCarnet()==carnet) {
                    TempUsuario=AuxList.getValue(i);
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        carnetTxt.setText(Integer.toString(TempUsuario.getCarnet()));
        nombreTxt.setText(TempUsuario.getNombre());
        apellidoTxt.setText(TempUsuario.getApellido());
        carreraTxt.setText(TempUsuario.getCarrera());
        passwordTxt.setText("");

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
