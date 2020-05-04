package Estructuras;

import Clases.Libro;
import Clases.Usuario;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Bloque {

    private int index;
    private String timeStamp;
    private int nonce;
    private JSONArray data;
    private String prevHash;
    private String hash;


    public Bloque(){

    }

    public void newCrearUsuario(Usuario arg1){
        JSONArray arregloJSON = new JSONArray();
        JSONObject objetoJSON = new JSONObject();
        objetoJSON.put("Carnet",arg1.getCarnet());
        objetoJSON.put("Nombre", arg1.getNombre());
        objetoJSON.put("Apellido", arg1.getApellido());
        objetoJSON.put("Carrera", arg1.getCarrera());
        objetoJSON.put("Password", arg1.getPassword());
        arregloJSON.add(objetoJSON);
        JSONObject objetoJSON2 = new JSONObject();
        objetoJSON2.put("CREAR_USUARIO",arregloJSON);
        data.add(objetoJSON2);
    }

    public void newEditarUsuario(Usuario arg1){
        JSONArray arregloJSON = new JSONArray();
        JSONObject objetoJSON = new JSONObject();
        objetoJSON.put("Carnet",arg1.getCarnet());
        objetoJSON.put("Nombre", arg1.getNombre());
        objetoJSON.put("Apellido", arg1.getApellido());
        objetoJSON.put("Carrera", arg1.getCarrera());
        objetoJSON.put("Password", arg1.getPassword());
        arregloJSON.add(objetoJSON);
        JSONObject objetoJSON2 = new JSONObject();
        objetoJSON2.put("EDITAR_USUARIO",arregloJSON);
        data.add(objetoJSON2);
    }

    public void newCrearLibro(Libro arg1){
        JSONArray arregloJSON = new JSONArray();
        JSONObject objetoJSON = new JSONObject();
        objetoJSON.put("ISBN",arg1.getISBN());
        objetoJSON.put("Año",arg1.getYear());
        objetoJSON.put("Idioma",arg1.getIdioma());
        objetoJSON.put("Titulo",arg1.getTitulo());
        objetoJSON.put("Editorial",arg1.getEditorial());
        objetoJSON.put("Autor",arg1.getAutor());
        objetoJSON.put("Edicion",arg1.getEdicion());
        objetoJSON.put("Categoria",arg1.getCategoria());
        arregloJSON.add(objetoJSON);
        JSONObject objetoJSON2 = new JSONObject();
        objetoJSON2.put("CREAR_LIBRO",arregloJSON);
        data.add(objetoJSON2);
    }

    public void newEliminarLibro(Libro arg1){
        JSONArray arregloJSON = new JSONArray();
        JSONObject objetoJSON = new JSONObject();
        objetoJSON.put("ISBN",arg1.getISBN());
        objetoJSON.put("Titulo",arg1.getTitulo());
        objetoJSON.put("Categoria",arg1.getCategoria());
        arregloJSON.add(objetoJSON);
        JSONObject objetoJSON2 = new JSONObject();
        objetoJSON2.put("ELIMINAR_LIBRO",arregloJSON);
        data.add(objetoJSON2);
    }

    public void newCrearCategoria(String arg1){
        JSONArray arregloJSON = new JSONArray();
        JSONObject objetoJSON = new JSONObject();
        objetoJSON.put("NOMBRE",arg1);
        arregloJSON.add(objetoJSON);
        JSONObject objetoJSON2 = new JSONObject();
        objetoJSON2.put("CREAR_CATEGORIA",arregloJSON);
        data.add(objetoJSON2);
    }

    public void newEliminarCategoria(String arg1){
        JSONArray arregloJSON = new JSONArray();
        JSONObject objetoJSON = new JSONObject();
        objetoJSON.put("NOMBRE",arg1);
        arregloJSON.add(objetoJSON);
        JSONObject objetoJSON2 = new JSONObject();
        objetoJSON2.put("ELIMINAR_CATEGORIA",arregloJSON);
        data.add(objetoJSON2);
    }





}
