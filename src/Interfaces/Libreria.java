package Interfaces;

import Clases.CategoriaLibro;
import Clases.Data;
import Clases.Libro;
import Clases.Usuario;
import Dialogos.CrearLibro;
import Dialogos.EditarPerfil;
import Estructuras.*;
import Estructuras.LinkedList;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.URL;
import java.util.*;

public class Libreria implements Initializable{

    public static int User;

    public static Bloque tempBloque;

    @FXML
    AnchorPane anchorPane;


    @FXML
    public void EditarPerfil(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Dialogos/EditarPerfil.fxml"));
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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Dialogos/CrearPerfil.fxml"));
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
                        Libreria.tempBloque.newEliminarUsuario(User);
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
    public void AgregarCategoria(){
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
                tempBloque.newCrearCategoria(AuxCategoria);
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
                tempBloque.newEliminarCategoria(result.get());
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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Dialogos/CrearLibro.fxml"));
            Parent root = fxmlLoader.load();
            CrearLibro controller = fxmlLoader.<CrearLibro>getController();
            controller.setMyCarnet(User);
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

    @FXML
    public void VerCatalogo()throws IOException{

        Stage stage = new Stage();
        FXMLLoader loader =  new FXMLLoader();
        loader.setLocation(getClass().getResource("Catalogo.fxml"));
        Parent root = loader.load();
        Catalogo controller = loader.<Catalogo>getController();
        controller.setUser(User);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Catalogo de Libros");
        stage.show();
        Stage auxStage = (Stage)anchorPane.getScene().getWindow();
        auxStage.setIconified(true);
    }

    @FXML
    public void graficarUsuarios(){
        Data.getUsuariosStructure().graphTable();
    }

    @FXML
    public void graficarLibros(){
        Data.getCategoriasStructure().graphArbol();
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

    @FXML
    public void graficarCategoriaPreOrder(){
        Data.getCategoriasStructure().graphPreOrder();
    }

    @FXML
    public void graficarCategoriaInOrder(){
        Data.getCategoriasStructure().graphInOrder();
    }

    @FXML
    public void graficarCategoriaPostOrder(){
        Data.getCategoriasStructure().graphPostOrder();
    }

    @FXML
    public void cargarLibros(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Cargar Libros");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Archivo JSON","*.json"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if(selectedFile!=null){
            String FileName = selectedFile.getAbsolutePath();

            try {
                Reader reader = new FileReader(FileName);
                JSONParser parser = new JSONParser();
                JSONObject jsonObject = (JSONObject) parser.parse(reader);
                System.out.println(jsonObject);
                JSONArray LibrosList = (JSONArray) jsonObject.get("libros");
                Iterator<JSONObject> iterator = LibrosList.iterator();
                JSONObject auxJSONObject;
                Libro tempLibro;
                CategoriaLibro<Libro> TempCategoria;
                while (iterator.hasNext()) {
                    auxJSONObject = iterator.next();
                    tempLibro = new Libro();
                    tempLibro.setISBN((int)((long) auxJSONObject.get("ISBN")));
                    tempLibro.setYear((int)((long) auxJSONObject.get("Año")));
                    tempLibro.setIdioma((String) auxJSONObject.get("Idioma"));
                    tempLibro.setTitulo((String) auxJSONObject.get("Titulo"));
                    tempLibro.setEditorial((String) auxJSONObject.get("Editorial"));
                    tempLibro.setAutor((String) auxJSONObject.get("Autor"));
                    tempLibro.setEdicion((int)((long) auxJSONObject.get("Edicion")));
                    tempLibro.setCategoria((String) auxJSONObject.get("Categoria"));
                    tempLibro.setCreador(User);
                    TempCategoria=new CategoriaLibro<>();
                    TempCategoria.setCategoria((String)auxJSONObject.get("Categoria"));
                    Data.getCategoriasStructure().setRoot(
                            Data.getCategoriasStructure().add(Data.getCategoriasStructure().getRoot(),tempLibro,TempCategoria.getCategoria()));
                    tempBloque.newCrearLibro(tempLibro);
                }
                System.out.println("LIBROS CARGADOS");

            }
            catch (FileNotFoundException e){
                e.printStackTrace();
            }
            catch (IOException e){
                e.printStackTrace();
            }
            catch (ParseException e) {
                e.printStackTrace();
            }
        }

    }

    @FXML
    public void crearBloque(){
        tempBloque.createBlock();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("BlockChain");
        alert.setHeaderText(null);
        alert.setContentText("El bloque ha sido creado exitosamente \n Hash:"+tempBloque.getJSONObject().get("HASH"));
        alert.showAndWait();
        tempBloque=new Bloque();
    }

    @FXML
    public void graficarNodosRed(){
        LinkedList<NodoRed> NodosList=Data.getListaNodos();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar Nodos de la Red");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Archivo DOT","*.dot"));
        File selectedFile = fileChooser.showSaveDialog(null);
        if(selectedFile!=null){
            try{
                FileWriter w = new FileWriter(selectedFile);
                BufferedWriter bw = new BufferedWriter(w);
                PrintWriter wr = new PrintWriter(bw);
                wr.write("digraph{\n");
                wr.write("node [shape=box]");
                wr.write("rankdir=LR;");

                wr.write("\""+NodosList.getValue(0).getIP()+"\"");
                wr.write("->");
                wr.write("\""+NodosList.getValue(1).getIP()+"\"");

                wr.write("\""+NodosList.getValue(1).getIP()+"\"");
                wr.write("->");
                wr.write("\""+NodosList.getValue(0).getIP()+"\"");

                for(int i=0;i<NodosList.getSize()-1;i++){
                    wr.write("\""+NodosList.getValue(i).getIP()+"\"");
                    wr.write("->");
                    wr.write("\""+NodosList.getValue(i+1).getIP()+"\"");

                    wr.write("\""+NodosList.getValue(i+1).getIP()+"\"");
                    wr.write("->");
                    wr.write("\""+NodosList.getValue(i).getIP()+"\"");
                }
                wr.append("}");
                wr.close();
                bw.close();
                ProcessBuilder pbuilder;
                String Ruta = selectedFile.getAbsolutePath().replace(".dot","");
                pbuilder = new ProcessBuilder("dot", "-Tpng", "-o", Ruta+".png ",Ruta+".dot");
                pbuilder.redirectErrorStream(true);
                pbuilder.start();
                Data.showImage(Ruta+".png");

            }
            catch (IOException e){
                e.printStackTrace();
            }
            catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    @FXML
    public void graficarBlockChain(){
        DoubleLinkedList<Bloque> NodosList=Data.getBlockChain();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar BlockChain");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Archivo DOT","*.dot"));
        File selectedFile = fileChooser.showSaveDialog(null);
        if(selectedFile!=null){
            try{
                FileWriter w = new FileWriter(selectedFile);
                BufferedWriter bw = new BufferedWriter(w);
                PrintWriter wr = new PrintWriter(bw);
                wr.write("digraph{\n");
                wr.write("node [shape=box]");
                wr.write("rankdir=LR;");

                for(int i=0;i<NodosList.getSize()-1;i++){
                    wr.write("\""+"Index: "+NodosList.getByPosition(i).getJSONObject().get("INDEX")+"\n");
                    wr.write("TimeStamp: "+NodosList.getByPosition(i).getJSONObject().get("TIMESTAMP")+"\n");
                    wr.write("NONCE: "+NodosList.getByPosition(i).getJSONObject().get("NONCE")+"\n");
                    wr.write("PrevHash: "+NodosList.getByPosition(i).getJSONObject().get("PREVIOUSHASH")+"\n");
                    wr.write("Hash: "+NodosList.getByPosition(i).getJSONObject().get("HASH")+"\"");
                    wr.write("->");
                    wr.write("\""+"Index: "+NodosList.getByPosition(i+1).getJSONObject().get("INDEX")+"\n");
                    wr.write("TimeStamp: "+NodosList.getByPosition(i+1).getJSONObject().get("TIMESTAMP")+"\n");
                    wr.write("NONCE: "+NodosList.getByPosition(i+1).getJSONObject().get("NONCE")+"\n");
                    wr.write("PrevHash: "+NodosList.getByPosition(i+1).getJSONObject().get("PREVIOUSHASH")+"\n");
                    wr.write("Hash: "+NodosList.getByPosition(i+1).getJSONObject().get("HASH")+"\"");

                    wr.write("\""+"Index: "+NodosList.getByPosition(i+1).getJSONObject().get("INDEX")+"\n");
                    wr.write("TimeStamp: "+NodosList.getByPosition(i+1).getJSONObject().get("TIMESTAMP")+"\n");
                    wr.write("NONCE: "+NodosList.getByPosition(i+1).getJSONObject().get("NONCE")+"\n");
                    wr.write("PrevHash: "+NodosList.getByPosition(i+1).getJSONObject().get("PREVIOUSHASH")+"\n");
                    wr.write("Hash: "+NodosList.getByPosition(i+1).getJSONObject().get("HASH")+"\"");
                    wr.write("->");
                    wr.write("\""+"Index: "+NodosList.getByPosition(i).getJSONObject().get("INDEX")+"\n");
                    wr.write("TimeStamp: "+NodosList.getByPosition(i).getJSONObject().get("TIMESTAMP")+"\n");
                    wr.write("NONCE: "+NodosList.getByPosition(i).getJSONObject().get("NONCE")+"\n");
                    wr.write("PrevHash: "+NodosList.getByPosition(i).getJSONObject().get("PREVIOUSHASH")+"\n");
                    wr.write("Hash: "+NodosList.getByPosition(i).getJSONObject().get("HASH")+"\"");
                }
                wr.append("}");
                wr.close();
                bw.close();
                ProcessBuilder pbuilder;
                String Ruta = selectedFile.getAbsolutePath().replace(".dot","");
                pbuilder = new ProcessBuilder("dot", "-Tpng", "-o", Ruta+".png ",Ruta+".dot");
                pbuilder.redirectErrorStream(true);
                Process process= pbuilder.start();
                process.waitFor();
                Data.showImage(Ruta+".png");

            }
            catch (IOException e){
                e.printStackTrace();
            }
            catch (Exception e){
                e.printStackTrace();
            }

        }



    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tempBloque= new Bloque();

    }

}
