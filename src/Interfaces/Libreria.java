package Interfaces;

import Clases.CategoriaLibro;
import Clases.Data;
import Clases.Libro;
import Clases.Usuario;
import Dialogos.EditarPerfil;
import Estructuras.LinkedList;
import Estructuras.NodoBinario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class Libreria implements Initializable{

    public static int User;

    @FXML
    AnchorPane anchorPane;


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
            Stage stage = (Stage) anchorPane.getScene().getWindow();
            stage.close();
        }


    }

    @FXML
    public void AgregarCatergoria(){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Categorias");
        dialog.setHeaderText("Agregar Categoria");
        dialog.setContentText("Ingresa el nombre de la nueva categoria:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            //SI LA CATEGORIA YA EXISTE
            if(Data.getCategoriasStructure().search(Data.getCategoriasStructure().getRoot(),result.get())){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Categorias");
                alert.setHeaderText(null);
                alert.setContentText("La categoria ya existe");
                alert.showAndWait();
            }
            else{
                CategoriaLibro<Libro> AuxCategoria = new CategoriaLibro(result.get());
                AuxCategoria.setCreador(User);
                Data.getCategoriasStructure().setRoot(
                        Data.getCategoriasStructure().add(Data.getCategoriasStructure().getRoot(),AuxCategoria,AuxCategoria.getCategoria())
                );
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Categorias");
                alert.setHeaderText(null);
                alert.setContentText("La categoria "+result.get()+" se ha creado exitosamente");
                alert.showAndWait();
            }

        }
    }

    @FXML
    public void  EliminarCategoria(){
        LinkedList<NodoBinario<CategoriaLibro>> NodosList=new LinkedList();
        Data.getCategoriasStructure().getPreOrdenList(Data.getCategoriasStructure().getRoot(),NodosList);

        List<String> choices = new ArrayList<>();
        for(int i=0;i<NodosList.getSize();i++){
            try {
                choices.add(NodosList.getValue(i).getValue().getCategoria());
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
        dialog.setTitle("Categorias");
        dialog.setHeaderText("Eliminar Categoria");
        dialog.setContentText("Elige la categoria a eliminar:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confrmacion");
            alert.setHeaderText("Eliminacion de Categoria: "+result.get());
            alert.setContentText("¿Esta seguro de eliminar la categoria?");

            Optional<ButtonType> result2 = alert.showAndWait();
            if (result2.get() == ButtonType.OK){
                Data.getCategoriasStructure().delete(Data.getCategoriasStructure().getRoot(),result.get());
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Informacion");
                alert.setHeaderText(null);
                alert.setContentText("La eliminacion ha sido exitosa");
                alert.showAndWait();
            }

        }
    }

    @FXML
    public void AgregarLibro(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../Dialogos/CrearLibro.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Crear Libro");
            stage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
