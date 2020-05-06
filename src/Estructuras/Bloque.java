package Estructuras;

import Clases.CategoriaLibro;
import Clases.Data;
import Clases.Libro;
import Clases.Usuario;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Iterator;


public class Bloque {

    private int index;
    private String timeStamp;
    private int nonce;
    private JSONArray data;
    private String prevHash;
    private String hash;


    /**
     * Constructor vacio
     */
    public Bloque(){
        data = new JSONArray();
        nonce=-1;
    }

    /**
     * Construye el Bloque a partir de un string en formato JSON
     * @param arg1 String en formato JSON
     */
    public Bloque(String arg1){
        JSONParser parser = new JSONParser();

        try {

            JSONObject jsonObject = (JSONObject) parser.parse(arg1);
            System.out.println("Bloque Nuevo apartir de:\n"+jsonObject);

            this.index= (int)((long)jsonObject.get("INDEX"));
            this.timeStamp= (String) jsonObject.get("TIMESTAMP");
            this.nonce= (int)((long)jsonObject.get("NONCE"));
            this.data = (JSONArray) jsonObject.get("DATA");
            this.prevHash= (String)jsonObject.get("PREVIOUSHASH");
            this.hash= (String)jsonObject.get("HASH");
            if(this.validateBlock()){
                System.out.println("BLOQUE VALIDO");
                this.executeData();
            }
            else{
                System.out.println("BLOQUE NO VALIDO");
            }

        }
        catch (ParseException e) {
            e.printStackTrace();
        }

    }

    //METODOS PARA ESCRIBIR LA DATA DE UN BLOQUE

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

    public void newEditarUsuario(Usuario arg1, int oldCarnet){
        JSONArray arregloJSON = new JSONArray();
        JSONObject objetoJSON = new JSONObject();
        objetoJSON.put("oldCarnet",oldCarnet);
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

    public void newEliminarUsuario(int arg1){
        JSONArray arregloJSON = new JSONArray();
        JSONObject objetoJSON = new JSONObject();
        objetoJSON.put("Carnet",arg1);
        arregloJSON.add(objetoJSON);
        JSONObject objetoJSON2 = new JSONObject();
        objetoJSON2.put("ELIMINAR_USUARIO",arregloJSON);
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
        objetoJSON.put("Creador",arg1.getCreador());
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

    public void newCrearCategoria(CategoriaLibro<Libro> arg1){
        JSONArray arregloJSON = new JSONArray();
        JSONObject objetoJSON = new JSONObject();
        objetoJSON.put("Nombre",arg1.getCategoria());
        objetoJSON.put("Creador",arg1.getCreador());
        arregloJSON.add(objetoJSON);
        JSONObject objetoJSON2 = new JSONObject();
        objetoJSON2.put("CREAR_CATEGORIA",arregloJSON);
        data.add(objetoJSON2);
    }

    public void newEliminarCategoria(String arg1){
        JSONArray arregloJSON = new JSONArray();
        JSONObject objetoJSON = new JSONObject();
        objetoJSON.put("Nombre",arg1);
        arregloJSON.add(objetoJSON);
        JSONObject objetoJSON2 = new JSONObject();
        objetoJSON2.put("ELIMINAR_CATEGORIA",arregloJSON);
        data.add(objetoJSON2);
    }

    //METODOS PARA EJECUTAR LA DATA DE UN BLOQUE

    public void executeCrearUsuario(JSONObject objJSON){

        Usuario tempUsuario = new Usuario();
        tempUsuario.setCarnet((int)((long) objJSON.get("Carnet")));
        tempUsuario.setNombre((String) objJSON.get("Nombre"));
        tempUsuario.setApellido((String) objJSON.get("Apellido"));
        tempUsuario.setCarrera((String) objJSON.get("Carrera"));
        tempUsuario.setPasswordDirect((String) objJSON.get("Password"));
        Data.getUsuariosStructure().add(tempUsuario,tempUsuario.getCarnet());
        System.out.println("OPERACION CREAR USUARIO DESDE BLOQUE:"+tempUsuario.getCarnet());

    }

    public void executeEditarUsuario(JSONObject objJSON){


        int oldcarnet = (int)((long) objJSON.get("oldCarnet"));
        LinkedList<Usuario> AuxList= Data.getUsuariosStructure().getValue(oldcarnet);
        //SE BUSCA USUARIO
        for(int i=0;i<AuxList.getSize();i++){
            try {
                if (AuxList.getValue(i).getCarnet()== oldcarnet) {
                    AuxList.removeByPosition(i);
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        Usuario tempUsuario = new Usuario();
        tempUsuario.setCarnet((int)((long) objJSON.get("Carnet")));
        tempUsuario.setNombre((String) objJSON.get("Nombre"));
        tempUsuario.setApellido((String) objJSON.get("Apellido"));
        tempUsuario.setCarrera((String) objJSON.get("Carrera"));
        tempUsuario.setPasswordDirect((String) objJSON.get("Password"));
        Data.getUsuariosStructure().add(tempUsuario,tempUsuario.getCarnet());
        System.out.println("OPERACION EDITAR USUARIO DESDE BLOQUE:"+tempUsuario.getCarnet());

    }

    public void executeEliminarUsuario(JSONObject objJSON){

        int carnet = (int)((long) objJSON.get("Carnet"));
        LinkedList<Usuario> AuxList= Data.getUsuariosStructure().getValue(carnet);
        //SE BUSCA USUARIO
        for(int i=0;i<AuxList.getSize();i++){
            try {
                if (AuxList.getValue(i).getCarnet()==carnet) {
                    AuxList.removeByPosition(i);
                    break;
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        System.out.println("OPERACION ELIMINAR USUARIO DESDE BLOQUE:"+carnet);

    }

    public void executeCrearLibro(JSONObject objJSON){

        Libro tempLibro = new Libro();
        tempLibro.setISBN((int)((long) objJSON.get("ISBN")));
        tempLibro.setYear((int)((long) objJSON.get("Año")));
        tempLibro.setIdioma((String) objJSON.get("Idioma"));
        tempLibro.setTitulo((String) objJSON.get("Titulo"));
        tempLibro.setEditorial((String) objJSON.get("Editorial"));
        tempLibro.setAutor((String) objJSON.get("Autor"));
        tempLibro.setEdicion((int)((long) objJSON.get("Edicion")));
        tempLibro.setCategoria((String) objJSON.get("Categoria"));
        tempLibro.setCreador((int)((long) objJSON.get("Creador")));
        CategoriaLibro tempCategoria=new CategoriaLibro<>();
        tempCategoria.setCategoria((String)objJSON.get("Categoria"));
        Data.getCategoriasStructure().setRoot(
                Data.getCategoriasStructure().add(Data.getCategoriasStructure().getRoot(),tempLibro,tempCategoria.getCategoria()));
        System.out.println("OPERACION CREAR LIBRO DESDE BLOQUE:"+tempLibro.getISBN());
    }

    public void executeEliminarLibro(JSONObject objJSON){
        int ISBN =(int)((long) objJSON.get("ISBN"));
        String categoria = (String) objJSON.get("Categoria");

        try {
            BTree<Libro> auxArbol =
                    Data.getCategoriasStructure().getValue(Data.getCategoriasStructure().getRoot(), categoria)
                            .getValue().getBookList();
            auxArbol.delete(auxArbol.getRoot(), ISBN);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("OPERACION ELIMINAR LIBRO DESDE BLOQUE:"+ISBN);

    }

    public void executeCrearCategoria(JSONObject objJSON){
        CategoriaLibro<Libro> AuxCategoria = new CategoriaLibro((String) objJSON.get("Nombre"));
        AuxCategoria.setCreador((int)((long) objJSON.get("Creador")));
        Data.getCategoriasStructure().setRoot(
                Data.getCategoriasStructure().add(Data.getCategoriasStructure().getRoot(),AuxCategoria,AuxCategoria.getCategoria())
        );
        System.out.println("OPERACION CREAR CATEGORIA DESDE BLOQUE:"+(String) objJSON.get("Nombre"));
    }

    public void executeEliminarCategoria(JSONObject objJSON){
        String categoria=(String) objJSON.get("Nombre");
        Data.getCategoriasStructure().delete(Data.getCategoriasStructure().getRoot(),categoria);
        System.out.println("OPERACION ELIMINAR CATEGORIA DESDE BLOQUE:"+categoria);
    }



    /**
     * Ejecuta todas las instrucciones dentro del arreglo Data
     */
    public void executeData(){

        Iterator<JSONObject> iterator = data.iterator();
        JSONObject jsonObject;
        JSONArray jsonArray;

        while (iterator.hasNext()) {
            jsonObject=iterator.next();
            String key = (String)jsonObject.keySet().toArray()[0];

            jsonArray = (JSONArray)jsonObject.get(key);
            Iterator<JSONObject> iterator2 = jsonArray.iterator();

            switch (key){
                case "CREAR_USUARIO":
                    executeCrearUsuario(iterator2.next());
                    break;
                case "EDITAR_USUARIO":
                    executeEditarUsuario(iterator2.next());
                    break;
                case "ELIMINAR_USUARIO":
                    executeEliminarUsuario(iterator2.next());
                    break;
                case "CREAR_LIBRO":
                    executeCrearLibro(iterator2.next());
                    break;
                case "ELIMINAR_LIBRO":
                    executeEliminarLibro(iterator2.next());
                    break;
                case "CREAR_CATEGORIA":
                    executeCrearCategoria(iterator2.next());
                    break;
                case "ELIMINAR_CATEGORIA":
                    executeEliminarCategoria(iterator2.next());
                    break;
            }
        }



    }

    public  void showData(){
        System.out.println(this.data.toJSONString());
    }

    /**
     * Convierte bloque en JSON
     * @return  Cadena en formato JSON
     */
    public JSONObject getJSONObject(){
        JSONObject objetoJSON = new JSONObject();
        objetoJSON.put("INDEX", index);
        objetoJSON.put("TIMESTAMP", timeStamp);
        objetoJSON.put("NONCE", nonce);
        objetoJSON.put("DATA", data);
        objetoJSON.put("PREVIOUSHASH", prevHash);
        objetoJSON.put("HASH", hash);

        return objetoJSON;
    }

    /**
     * Se crea bloque y se añade a
     */
    public void createBlock(){
        index= Data.getBlockIndex();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy::HH:mm:ss");
        timeStamp = sdf.format(new Timestamp(System.currentTimeMillis()));
        try {
            prevHash = Data.getBlockChain().getLast().hash;
        }
        catch (Exception e){
            prevHash = "0000";
        }

        String auxString;

        do{
            nonce++;
            auxString=index+timeStamp+prevHash+data.toJSONString()+nonce;
            try {
                MessageDigest mdHash = MessageDigest.getInstance("SHA-256");
                byte[] hashBytes = mdHash.digest(auxString.getBytes(StandardCharsets.UTF_8));
                StringBuilder sb = new StringBuilder();
                for (byte b : hashBytes) {
                    sb.append(String.format("%02x", b));
                }
                hash = sb.toString();
            }
            catch (NoSuchAlgorithmException e){
                e.printStackTrace();
            }

        }while(!hash.substring(0,4).equals("0000"));

        Data.getBlockChain().addEnd(this);
        System.out.println("BLOQUE RESULTANTE");
        System.out.println(this.getJSONObject().toJSONString());

        for(int i=0;i<Data.getListaNodos().getSize();i++){
            try {
                Data.getListaNodos().getValue(i).sendData(this.getJSONObject().toJSONString());
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    /**
     * Valida un bloque recibido
     */
    public boolean validateBlock(){

        String auxString=index+timeStamp+prevHash+data.toJSONString()+nonce;
        try {
            MessageDigest mdHash = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = mdHash.digest(auxString.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            if(hash.equals(sb.toString())) {
                return true;
            }
            else{
                return false;
            }
        }
        catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return false;
    }








}
