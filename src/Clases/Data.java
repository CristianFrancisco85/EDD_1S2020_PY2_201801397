package Clases;

import Estructuras.*;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


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


}
