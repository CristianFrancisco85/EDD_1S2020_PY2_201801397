package Interfaces;

import Clases.Data;
import Clases.Usuario;
import Estructuras.LinkedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

public class Login implements Initializable {

    @FXML
    PasswordField passwordTxt;
    @FXML
    TextField userTxt;

    @FXML
    public void login(){

        int User = Integer.parseInt(userTxt.getText());
        LinkedList<Usuario> AuxList=Data.getUsuariosStructure().getValue(User);
        Usuario TempUsuario=null;
        //SI USUARIO NO EXISTE
        if(AuxList==null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Usuario");
            alert.setHeaderText(null);
            alert.setContentText("Usuario Invalido");
            alert.showAndWait();
            return;
        }
        //SE BUSCA USARIO
        for(int i=0;i<AuxList.getSize();i++){
            try {
                if (AuxList.getValue(i).getCarnet()==User) {
                    TempUsuario=AuxList.getValue(i);
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        //SI USUARIO NO EXISTE
        if(TempUsuario==null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Usuario");
            alert.setHeaderText(null);
            alert.setContentText("Usuario Invalido");
            alert.showAndWait();
        }
        //SI SE ENCONTRO USUARIO
        else{
            try {
                String Password = passwordTxt.getText();
                MessageDigest mdHash = MessageDigest.getInstance("MD5");
                byte[] hashBytes = mdHash.digest(Password.getBytes(StandardCharsets.UTF_8));
                StringBuilder sb = new StringBuilder();
                for (byte b : hashBytes) {
                    sb.append(String.format("%02x", b));
                }
                //SI LOS HASHES COINCIDE
                if(sb.toString().equals(TempUsuario.getPassword())){
                    Stage stage = (Stage) passwordTxt.getScene().getWindow();
                    FXMLLoader loader =  new FXMLLoader();
                    loader.setLocation(getClass().getResource("Libreria.fxml"));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setTitle("Libreria");
                    stage.show();
                    Libreria.User=User;
                }
                else{
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Contraseña");
                    alert.setHeaderText(null);
                    alert.setContentText("Contreseña Invalida");
                    alert.showAndWait();
                }
            }
            catch (NoSuchAlgorithmException e){
                e.printStackTrace();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}