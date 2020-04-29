package Interfaces;

import Clases.CategoriaLibro;
import Clases.Data;
import Estructuras.LinkedList;
import Estructuras.NodoBinario;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceDialog;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class Reportes implements Initializable {



    @FXML
    public void graficarUsuarios(){

    }

    @FXML
    public void graficarLibros(){
        Data.getCategoriasStructure().graphArbol();
        //Data.getCategoriasStructure().getValue(Data.getCategoriasStructure().getRoot(),"Consulta").getValue().getBookList().graphArbol();
    }

    @FXML
    public void graficarCategoria(){
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
        dialog.setHeaderText("Graficar Arbol B");
        dialog.setContentText("Elige la categoria a graficar:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            Data.getCategoriasStructure().getValue(
                    Data.getCategoriasStructure().getRoot(),
                    result.get()).getValue().getBookList().graphArbol();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
