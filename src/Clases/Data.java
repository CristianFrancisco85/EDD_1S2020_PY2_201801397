package Clases;

import Estructuras.AVLTree;
import Estructuras.Bloque;
import Estructuras.DoubleLinkedList;
import Estructuras.HashTable;

public abstract class Data {

    private static HashTable<Usuario> UsuariosHashTable;
    private static AVLTree<CategoriaLibro,Libro> ArbolCategorias;
    private static DoubleLinkedList<Bloque> BlockChain;
    public  static int blockIndex;

    public static void initializeStructures(){
        UsuariosHashTable = new HashTable<Usuario>(45);
        ArbolCategorias = new AVLTree<CategoriaLibro,Libro>();
        BlockChain = new DoubleLinkedList<Bloque>();
        blockIndex=0;
    }
    public static HashTable<Usuario> getUsuariosStructure(){
        return  UsuariosHashTable;
    }
    public static AVLTree<CategoriaLibro,Libro> getCategoriasStructure(){
        return  ArbolCategorias;
    }
    public static DoubleLinkedList<Bloque> getBlockChain(){ return BlockChain;}
    public static int getBlockIndex(){blockIndex++; return blockIndex-1;}


}
