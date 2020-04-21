package Clases;

import Estructuras.HashTable;

public abstract class Data {

    private static HashTable<Usuario> UsuariosHashTable;

    public static void initializeStructures(){
        UsuariosHashTable = new HashTable<Usuario>(45);

    }
    public static HashTable getUsuariosStructure(){
        return  UsuariosHashTable;
    }


}
