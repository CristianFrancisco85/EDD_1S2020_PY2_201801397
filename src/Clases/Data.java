package Clases;

import Estructuras.*;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.Iterator;


public abstract class Data {

    private static HashTable<Usuario> UsuariosHashTable;
    private static AVLTree<CategoriaLibro,Libro> ArbolCategorias;
    private static DoubleLinkedList<Bloque> BlockChain;
    private static LinkedList<NodoRed> ListaNodos;
    public  static int blockIndex;

    public static void initializeStructures(){
        UsuariosHashTable = new HashTable<>(45);
        ArbolCategorias = new AVLTree<>();
        BlockChain = new DoubleLinkedList<>();
        ListaNodos=new LinkedList<>();
        blockIndex=0;
    }
    public static HashTable<Usuario> getUsuariosStructure(){
        return  UsuariosHashTable;
    }
    public static AVLTree<CategoriaLibro,Libro> getCategoriasStructure(){
        return  ArbolCategorias;
    }
    public static DoubleLinkedList<Bloque> getBlockChain(){ return BlockChain;}
    public static LinkedList<NodoRed> getListaNodos(){return ListaNodos;}
    public static int getBlockIndex(){blockIndex++; return blockIndex-1;}

    public static void showImage(String arg1){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Imagen");
        alert.setContentText(null);
        Image image = new Image("file:///"+arg1);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(1000);
        imageView.setFitWidth(1000);
        imageView.setPreserveRatio(true);
        alert.setGraphic(imageView);
        alert.showAndWait();


    }
    public static void saveBlockChain(){
        String desktopPath=System.getProperty("user.home") + "/BlockChain";
        new File(desktopPath).mkdirs();

        try {
            FileWriter w = new FileWriter(desktopPath + "/BlockChain.json");
            BufferedWriter bw = new BufferedWriter(w);
            PrintWriter wr = new PrintWriter(bw);

            JSONArray Arreglo = new JSONArray();

            for(int i=0;i<BlockChain.getSize();i++){
                Arreglo.add(BlockChain.getByPosition(i).getJSONObject());
            }

            wr.write(Arreglo.toJSONString());
            wr.close();
            bw.close();

        }
        catch (IOException e){
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }


    }
    public static void readBlockChain(){
        String desktopPath=System.getProperty("user.home") + "/BlockChain";
        try {
            Reader reader = new FileReader(desktopPath + "/BlockChain.json");
            JSONParser parser = new JSONParser();

            JSONArray BlockList = (JSONArray) parser.parse(reader);
            Iterator<JSONObject> iterator = BlockList.iterator();
            Bloque auxBloque;
            JSONObject auxJSONObject;
            while (iterator.hasNext()){
                auxJSONObject = iterator.next();
                auxBloque = new Bloque(auxJSONObject.toJSONString());
                Data.getBlockChain().addEnd(auxBloque);
            }
        }
        catch (FileNotFoundException e){
            System.out.println("No hay BlockChain Existente");
        }
        catch (IOException e){
            e.printStackTrace();
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }



}
