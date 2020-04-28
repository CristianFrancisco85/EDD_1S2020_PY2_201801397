package Interfaces;

import Clases.Data;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class Reportes implements Initializable {

    @FXML
    public void graficarUsuarios(){

    }

    @FXML
    public void graficarLibros(){
        Data.getCategoriasStructure().graphArbol();
        Data.getCategoriasStructure().search(Data.getCategoriasStructure().getRoot(),"Consulta").getValue().getBookList().graphArbol();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
