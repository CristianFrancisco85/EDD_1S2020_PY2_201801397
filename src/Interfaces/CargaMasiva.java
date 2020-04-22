package Interfaces;

import Clases.CategoriaLibro;
import Clases.Data;
import Clases.Libro;
import Clases.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.ResourceBundle;

public class CargaMasiva implements Initializable{

    @FXML
    public void cargarArchivoUsuarios(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Cargar Usuarios");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Archivo JSON","*.json"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if(selectedFile!=null){
            String FileName = selectedFile.getAbsolutePath();

             try {
                 Reader reader = new FileReader(FileName);
                 JSONParser parser = new JSONParser();
                 JSONObject jsonObject = (JSONObject) parser.parse(reader);
                 System.out.println(jsonObject);

                 JSONArray UsuariosList = (JSONArray) jsonObject.get("Usuarios");
                 Iterator<JSONObject> iterator = UsuariosList.iterator();
                 JSONObject auxJSONObject;
                 Usuario tempUsuario;
                 while (iterator.hasNext()) {
                     auxJSONObject = iterator.next();
                     tempUsuario = new Usuario();
                     tempUsuario.setCarnet((int)((long) auxJSONObject.get("Carnet")));
                     tempUsuario.setNombre((String) auxJSONObject.get("Nombre"));
                     tempUsuario.setApellido((String) auxJSONObject.get("Apellido"));
                     tempUsuario.setCarrera((String) auxJSONObject.get("Carrera"));
                     tempUsuario.setPassword((String) auxJSONObject.get("Password"));
                     Data.getUsuariosStructure().add(tempUsuario,tempUsuario.getCarnet());
                 }

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
             catch (NoSuchAlgorithmException e) {
                 e.printStackTrace();
             }
        }
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
                    TempCategoria=new CategoriaLibro<>();
                    TempCategoria.setCategoria((String)auxJSONObject.get("Categoria"));
                    Data.getCategoriasStructure().setRoot(
                    Data.getCategoriasStructure().add(Data.getCategoriasStructure().getRoot(),tempLibro,TempCategoria.getCategoria()));
                }

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


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
