package Dialogos;

import Clases.CategoriaLibro;
import Clases.Data;
import Clases.Libro;
import Estructuras.BTree;
import Estructuras.LinkedList;
import Estructuras.NodoBinario;
import Interfaces.Libreria;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CrearLibro implements Initializable {

    @FXML
    TextField isbnTxt;
    @FXML
    TextField tituloTxt;
    @FXML
    TextField autorTxt;
    @FXML
    TextField editorialTxt;
    @FXML
    TextField yearTxt;
    @FXML
    TextField edicionTxt;
    @FXML
    ComboBox categoriaCMB;
    @FXML
    TextField idiomaTxt;

    public int MyCarnet;


    @FXML
    public void crearLibro(){
        BTree<Libro> auxArbol;

        //SE VERIFICA QUE EL ISBN NO EXISTA
        LinkedList<NodoBinario<CategoriaLibro>> NodosList=new LinkedList();
        Data.getCategoriasStructure().getPreOrdenList(Data.getCategoriasStructure().getRoot(),NodosList);
        for(int i=0;i<NodosList.getSize();i++){
            try {
                auxArbol = Data.getCategoriasStructure().
                getValue(Data.getCategoriasStructure().getRoot(),(String)NodosList.getValue(i).getValue().getCategoria()).getValue().getBookList();
                if(auxArbol.searchByIndex(auxArbol.getRoot(), Integer.parseInt(isbnTxt.getText()))){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Crear Libro");
                    alert.setHeaderText(null);
                    alert.setContentText("El ISBN del libro ya existe");
                    alert.showAndWait();
                    return;
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }


        try {
            //SI EL ISBN AUN NO EXISTE
            if(categoriaCMB.getValue()!=null) {
                //Se obtiene arbol B de categoria
                auxArbol = Data.getCategoriasStructure().
                getValue(Data.getCategoriasStructure().getRoot(),(String) categoriaCMB.getValue()).getValue().getBookList();
                //SE CREA LIBRO
                Libro auxLibro = new Libro();
                auxLibro.setISBN(Integer.parseInt(isbnTxt.getText()));
                auxLibro.setTitulo(tituloTxt.getText());
                auxLibro.setAutor(autorTxt.getText());
                auxLibro.setEditorial(editorialTxt.getText());
                auxLibro.setYear(Integer.parseInt(yearTxt.getText()));
                auxLibro.setEdicion(Integer.parseInt(edicionTxt.getText()));
                auxLibro.setCategoria((String) categoriaCMB.getValue());
                auxLibro.setIdioma(idiomaTxt.getText());
                auxLibro.setCreador(MyCarnet);
                //SE AGREGA LIBRO
                auxArbol.setRoot(auxArbol.add(auxArbol.getRoot(), auxLibro, auxLibro.getISBN()));
                Libreria.tempBloque.newCrearLibro(auxLibro);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Creacion de Libro");
                alert.setHeaderText(null);
                alert.setContentText("El libro nuevo se ha registrado exitosamente");
                alert.showAndWait();
                Stage stage = (Stage) categoriaCMB.getScene().getWindow();
                stage.close();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Creacion de Libro");
                alert.setHeaderText(null);
                alert.setContentText("Seleccione una categoria");
                alert.showAndWait();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }


    }

    public void setMyCarnet(int arg1){
        MyCarnet=arg1;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LinkedList<NodoBinario<CategoriaLibro>> NodosList=new LinkedList();
        Data.getCategoriasStructure().getPreOrdenList(Data.getCategoriasStructure().getRoot(),NodosList);
        for(int i=0;i<NodosList.getSize();i++){
            try {
                categoriaCMB.getItems().add(NodosList.getValue(i).getValue().getCategoria());
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
