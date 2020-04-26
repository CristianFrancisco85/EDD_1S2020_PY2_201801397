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
        //Data.getCategoriasStructure().graphArbol();
        Data.getCategoriasStructure().search(Data.getCategoriasStructure().getRoot(),"Consulta").getValue().getBookList().graphArbol();
        try {
            Data.getCategoriasStructure().search(Data.getCategoriasStructure().getRoot(), "Consulta").getValue().getBookList()
                    .delete(Data.getCategoriasStructure().search(Data.getCategoriasStructure().getRoot(), "Consulta").getValue().getBookList()
                            .getRoot(), 10795);
            Data.getCategoriasStructure().search(Data.getCategoriasStructure().getRoot(),"Consulta").getValue().getBookList().graphArbol();
            Data.getCategoriasStructure().search(Data.getCategoriasStructure().getRoot(), "Consulta").getValue().getBookList()
                    .delete(Data.getCategoriasStructure().search(Data.getCategoriasStructure().getRoot(), "Consulta").getValue().getBookList()
                            .getRoot(), 10763);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        Data.getCategoriasStructure().search(Data.getCategoriasStructure().getRoot(),"Consulta").getValue().getBookList().graphArbol();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
