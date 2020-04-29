package Interfaces;

import Clases.CategoriaLibro;
import Clases.Data;
import Clases.Libro;
import Estructuras.BTree;
import Estructuras.LinkedList;
import Estructuras.NodoBinario;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class Catalogo implements Initializable {

    @FXML
    TextField isbnTxtS;
    @FXML
    TextField tituloTxtS;
    @FXML
    ComboBox categoriaCMB;
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
    TextField categoriaTxt;
    @FXML
    TextField idiomaTxt;
    @FXML
    TextField carnetTxt;
    @FXML
    Button sigBtn;
    @FXML
    Button antBtn;

    int Apuntador;
    LinkedList<Libro> LibrosList;
    int User;

    @FXML
    public void siguienteLibro(){
        if(Apuntador<LibrosList.getSize()-1){
            Apuntador++;
            Libro auxLibro = new Libro();
            try {
                auxLibro = LibrosList.getValue(Apuntador);
            }
            catch (Exception e){
                e.printStackTrace();
            }
            isbnTxt.setText(Integer.toString(auxLibro.getISBN()));
            tituloTxt.setText(auxLibro.getTitulo());
            autorTxt.setText(auxLibro.getAutor());
            editorialTxt.setText(auxLibro.getEditorial());
            yearTxt.setText(Integer.toString(auxLibro.getYear()));
            edicionTxt.setText(Integer.toString(auxLibro.getEdicion()));
            categoriaTxt.setText(auxLibro.getCategoria());
            idiomaTxt.setText(auxLibro.getIdioma());
            carnetTxt.setText(Integer.toString(auxLibro.getCreador()));
        }

    }
    @FXML
    public void anteriorLibro(){
        if(Apuntador>0){
            Apuntador--;
            Libro auxLibro = new Libro();
            try {
                auxLibro = LibrosList.getValue(Apuntador);
            }
            catch (Exception e){
                e.printStackTrace();
            }
            isbnTxt.setText(Integer.toString(auxLibro.getISBN()));
            tituloTxt.setText(auxLibro.getTitulo());
            autorTxt.setText(auxLibro.getAutor());
            editorialTxt.setText(auxLibro.getEditorial());
            yearTxt.setText(Integer.toString(auxLibro.getYear()));
            edicionTxt.setText(Integer.toString(auxLibro.getEdicion()));
            categoriaTxt.setText(auxLibro.getCategoria());
            idiomaTxt.setText(auxLibro.getIdioma());
            carnetTxt.setText(Integer.toString(auxLibro.getCreador()));
        }

    }

    @FXML
    public void getCatalogo(){
        sigBtn.setDisable(false);
        antBtn.setDisable(false);
        if(categoriaCMB.getValue()==categoriaCMB.getItems().get(0)){
            LinkedList<NodoBinario<CategoriaLibro>> NodosList=new LinkedList();
            Data.getCategoriasStructure().getPreOrdenList(Data.getCategoriasStructure().getRoot(),NodosList);
            LibrosList =new LinkedList();
            categoriaCMB.getItems().add("Todas las categorias");
            for(int i=0;i<NodosList.getSize();i++){
                try {
                    NodosList.getValue(i).getValue().getBookList().getAllValues(LibrosList);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
            Apuntador=0;
            setPrimerLibro();
        }
        else{
            LibrosList =new LinkedList();
            Data.getCategoriasStructure().getValue(
                    Data.getCategoriasStructure().getRoot(),
                    (String)categoriaCMB.getValue()).getValue().getBookList().getAllValues(LibrosList);
            Apuntador=0;
            setPrimerLibro();
        }
    }

    @FXML
    public void eliminarLibro() throws Exception{
        Libro auxLibro;
        auxLibro = LibrosList.getValue(Apuntador);
        if(User==auxLibro.getCreador() || auxLibro.getCreador()==0){
            BTree<Libro> auxArbol=
                Data.getCategoriasStructure().getValue(Data.getCategoriasStructure().getRoot(), auxLibro.getCategoria())
                .getValue().getBookList();
            auxArbol.delete(auxArbol.getRoot(),auxLibro.getISBN());
            LibrosList.removeByPosition(Apuntador);
            setPrimerLibro();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Eliminar Libro");
            alert.setHeaderText(null);
            alert.setContentText("El libro ha sido elimnado exitosamente");
            alert.showAndWait();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Eliminar Libro");
            alert.setHeaderText(null);
            alert.setContentText("Usted no es propietario del libro, no lo puede eliminar");
            alert.showAndWait();
        }
    }

    @FXML
    public void buscarISBN(){
        LinkedList<NodoBinario<CategoriaLibro>> NodosList=new LinkedList();
        Data.getCategoriasStructure().getPreOrdenList(Data.getCategoriasStructure().getRoot(),NodosList);
        LibrosList =new LinkedList();
        for(int i=0;i<NodosList.getSize();i++){
            try {
                NodosList.getValue(i).getValue().getBookList().getAllValues(LibrosList);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        for(int i=0;i<LibrosList.getSize();i++){
            try {
                if (LibrosList.getValue(i).getISBN() == Integer.parseInt(isbnTxtS.getText())) {
                    Libro auxLibro;
                    auxLibro = LibrosList.getValue(i);
                    isbnTxt.setText(Integer.toString(auxLibro.getISBN()));
                    tituloTxt.setText(auxLibro.getTitulo());
                    autorTxt.setText(auxLibro.getAutor());
                    editorialTxt.setText(auxLibro.getEditorial());
                    yearTxt.setText(Integer.toString(auxLibro.getYear()));
                    edicionTxt.setText(Integer.toString(auxLibro.getEdicion()));
                    categoriaTxt.setText(auxLibro.getCategoria());
                    idiomaTxt.setText(auxLibro.getIdioma());
                    carnetTxt.setText(Integer.toString(auxLibro.getCreador()));
                    sigBtn.setDisable(true);
                    antBtn.setDisable(true);
                    return;
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Busquedad");
        alert.setHeaderText(null);
        alert.setContentText("No se contro libro");
        alert.showAndWait();

    }

    @FXML
    public void buscarNombre(){
        LinkedList<NodoBinario<CategoriaLibro>> NodosList=new LinkedList();
        Data.getCategoriasStructure().getPreOrdenList(Data.getCategoriasStructure().getRoot(),NodosList);
        LibrosList =new LinkedList();
        LinkedList<Libro> auxLibrosList =new LinkedList();
        for(int i=0;i<NodosList.getSize();i++){
            try {
                NodosList.getValue(i).getValue().getBookList().getAllValues(LibrosList);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        for(int i=0;i<LibrosList.getSize();i++){
            try {
                if (LibrosList.getValue(i).getTitulo().contains(tituloTxtS.getText())) {
                    auxLibrosList.addBegin(LibrosList.getValue(i));
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        if(auxLibrosList.getSize()==0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Busquedad");
            alert.setHeaderText(null);
            alert.setContentText("No se encontraron coincidencias");
            alert.showAndWait();
        }
        else{
            LibrosList=auxLibrosList;
            setPrimerLibro();
        }


    }


    public void setUser(int arg1){
        this.User=arg1;
    }

    public void setPrimerLibro(){
        try {
            if (LibrosList.getValue(0) != null) {
                Libro auxLibro;
                auxLibro = LibrosList.getValue(0);
                isbnTxt.setText(Integer.toString(auxLibro.getISBN()));
                tituloTxt.setText(auxLibro.getTitulo());
                autorTxt.setText(auxLibro.getAutor());
                editorialTxt.setText(auxLibro.getEditorial());
                yearTxt.setText(Integer.toString(auxLibro.getYear()));
                edicionTxt.setText(Integer.toString(auxLibro.getEdicion()));
                categoriaTxt.setText(auxLibro.getCategoria());
                idiomaTxt.setText(auxLibro.getIdioma());
                carnetTxt.setText(Integer.toString(auxLibro.getCreador()));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        LinkedList<NodoBinario<CategoriaLibro>> NodosList=new LinkedList();
        Data.getCategoriasStructure().getPreOrdenList(Data.getCategoriasStructure().getRoot(),NodosList);
        LibrosList =new LinkedList();
        categoriaCMB.getItems().add("Todas las categorias");
        for(int i=0;i<NodosList.getSize();i++){
            try {
                categoriaCMB.getItems().add(NodosList.getValue(i).getValue().getCategoria());
                NodosList.getValue(i).getValue().getBookList().getAllValues(LibrosList);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        setPrimerLibro();
        Apuntador=0;


    }
}
