package Clases;

import Estructuras.AVLTree;
import Estructuras.HashTable;

public abstract class Data {

    private static HashTable<Usuario> UsuariosHashTable;
    private static AVLTree<CategoriaLibro,Libro> ArbolCategorias;
    public  static int blockIndex;

    public static void initializeStructures(){
        UsuariosHashTable = new HashTable<Usuario>(45);
        ArbolCategorias = new AVLTree<CategoriaLibro,Libro>();
        blockIndex=0;
    }
    public static HashTable<Usuario> getUsuariosStructure(){
        return  UsuariosHashTable;
    }
    public static AVLTree<CategoriaLibro,Libro> getCategoriasStructure(){
        return  ArbolCategorias;
    }
    public static int getBlockIndex(){return blockIndex;}


}
