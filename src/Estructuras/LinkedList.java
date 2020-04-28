package Estructuras;

import com.sun.xml.internal.ws.handler.HandlerException;

public class LinkedList<T> {

    private NodoSimple<T> Head;
    private NodoSimple<T> Iterador;
    private int Size;

    public LinkedList(){
        this.Size=0;
        this.Head=null;
        this.Iterador=null;
    }

    /**
     * Verifica si las lista esta vacia
     * @return true si el primer nodo es null
     */
    public boolean isEmpty(){
        return this.Head == null;
    }

    /**
     * Retorna cuantos elementos existen dentro de la lista
     * @return Numero de elementos que contiene
     */
    public int getSize(){
        return Size;
    }


    /**
     * Busca si existe un valor en la lista.
     * @param Value Valor a buscar.
     * @return true si existe el valor.
     */
    public boolean search(T Value){

        Iterador= Head;
        for(int i=0;i<Size;i++){
            if(Iterador.getValue().equals(Value)){
                return true;
            }
            Iterador=Iterador.getNextNodo();
        }
        return false;
    }

    public int getPosition(T Value){
        Iterador=Head;
        for(int i=0;i<Size;i++){
            if(Iterador.getValue().equals(Value)){
                return i;
            }
            Iterador=Iterador.getNextNodo();
        }
        return -1;
    }

    /**
     * Elimina la lista
     */
    public void deleteList(){
        Head = null;
        Size = 0;
    }

    /**
     * Agrega un nodo al final de la lista
     * @param Value Valor a agregar.
     */
    public void addEnd(T Value){
        NodoSimple<T> NewNode = new NodoSimple();
        NewNode.setValue(Value);
        if (isEmpty()) {
            Head = NewNode;
        }
        else{
            Iterador = Head;
            while(Iterador.getNextNodo() != null){
                Iterador = Iterador.getNextNodo();
            }
            Iterador.setNextNodo(NewNode);
        }
        Size++;
    }

    /**
     * Agrega un nodo en la posicion indicada
     * @param Value Valor a agregar.
     * @param Position Posicion donde se insertara
     */
    public void addIn(T Value,int Position){
        if(Position==0){
            this.addBegin(Value);
        }
        else if(Position>=Size){
            this.addEnd(Value);
        }
        else if(Position<Size) {
            NodoSimple<T> NewNode = new NodoSimple();
            NewNode.setValue(Value);
            if (isEmpty()) {
                Head = NewNode;
            }
            else {
                Iterador = Head;
                for (int i = 0; i < Position-1; i++) {
                    Iterador = Iterador.getNextNodo();
                }
                NewNode.setNextNodo(Iterador.getNextNodo());
                Iterador.setNextNodo(NewNode);
            }
            Size++;
        }
        else{
            System.out.println("Posicion no valida");
        }
    }

    /**
     * Agrega un nodo al inicio de la lista.
     * @param Value Valor a agregar.
     */
    public void addBegin(T Value){
        NodoSimple<T> NewNode = new NodoSimple();
        NewNode.setValue(Value);
        if (isEmpty()) {
            Head = NewNode;
        }
        else{
            NewNode.setNextNodo(Head);
            Head = NewNode;
        }
        Size++;
    }

    /**
     * Obtiene el valor de un nodo en la posición indicada.
     * @param Position del nodo que se desea obtener su valor.
     * @return El valor del nodo en la posicion indicada
     * @throws Exception
     */
    public T getValue(int Position) throws Exception{
        if(Position>=Size){
            Position=Size-1;
        }
        if(Position>=0 && Position<Size){
            if (Position == 0) {
                return Head.getValue();
            }else{
                Iterador=Head;
                for (int i = 0; i < Position; i++) {
                    Iterador = Iterador.getNextNodo();
                }
                return Iterador.getValue();
            }
        } else {
            throw new Exception("¡POSICION NO EXISTE!");
        }
    }

    /**
     * Actualiza el valor del nodo en la posicion indicada
     * @param Position en la cual se encuentra el nodo a actualizar.
     * @param Value nuevo valor para el nodo.
     */
    public void editByPosition(int Position , T Value){
        if(Position>=0 && Position<Size){
            if(Position == 0){
                Head.setValue(Value);
            }
            else{
                Iterador=Head;
                for (int i = 0; i < Position; i++) {
                    Iterador = Iterador.getNextNodo();
                }
                Iterador.setValue(Value);
            }
        }
    }

    /**
     * Elimina un nodo que se encuentre en la posicion indicada
     * @param Position en la cual se encuentra el nodo a eliminar.
     */
    public void removeByPosition(int Position){
        if(Position>=0 && Position<Size){
            if(Position == 0){
                Head = Head.getNextNodo();
            }
            else{
                Iterador=Head;
                for (int i = 0; i < Position-1; i++) {
                    Iterador = Iterador.getNextNodo();
                }
                Iterador.setNextNodo(Iterador.getNextNodo().getNextNodo());
            }
            Size--;
        }
    }



}
