package Clases;

import Estructuras.*;

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


}
