package Estructuras;

import Clases.Usuario;

public class HashTable<T> {
    private LinkedList<T> Table[];

    public HashTable(int Size){
        Table = new LinkedList[Size];
    }

    /**
     * Reazliza el calculo de posicion mediante funcion hash
     * Funcion Hash: F(x)=x % x.Size;
     * @return Posicion calculada
     */
    public int hash(int arg1){
        return arg1 % String.valueOf(arg1).length();
    }

    /**
     * Verifica si las lista esta vacia
     * @param Value Valor a insertar
     * @param HashParam Valor a usar para calculo de posicion
     */
    public void add(T Value,int HashParam){
        if(Table[hash(HashParam)]==null){
            Table[hash(HashParam)]= new LinkedList();
        }
        Table[hash(HashParam)].addEnd(Value);
        System.out.println(HashParam+" Insertado en "+hash(HashParam));
    }


    /**
     * Devuelve el valor
     * @param HashParam Valor a usar para calculo de posicion
     */
    public LinkedList getValue(int HashParam){
        if(Table[hash(HashParam)].getSize()!=0){
            return Table[hash(HashParam)];
        }
        else {
            return null;
        }
    }



}
