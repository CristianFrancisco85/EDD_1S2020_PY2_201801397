package Estructuras;

public class DoubleLinkedList<T> {

    private NodoBinario<T> Head;
    private NodoBinario<T> Iterador;
    private int Size;

    public DoubleLinkedList(){
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
        NodoBinario<T> NewNode = new NodoBinario();
        NewNode.setValue(Value);
        if (isEmpty()) {
            Head = NewNode;
        }
        else{
            Iterador = Head;
            while(Iterador.getRightNodo() != null){
                Iterador = Iterador.getRightNodo();
            }
            Iterador.setRightNodo(NewNode);
            NewNode.setLeftNodo(Iterador);
        }
        Size++;
    }


    /**
     * Agrega un nodo al inicio de la lista.
     * @param Value Valor a agregar.
     */
    public void addBegin(T Value){
        NodoBinario<T> NewNode = new NodoBinario();
        NewNode.setValue(Value);
        if (isEmpty()) {
            Head = NewNode;
        }
        else{
            Head.setLeftNodo(NewNode);
            NewNode.setRightNodo(Head);
            Head = NewNode;
        }
        Size++;
    }

    /**
     * Obtiene el valor de un nodo en la ultima posición.
     * @return El valor del nodo en la posicion indicada
     * @throws Exception
     */
    public T getLast() throws  Exception{

        if (Head == null) {
            throw new Exception("Arbol Vacio");
        } else {
            Iterador = Head;
            while (Iterador.getRightNodo() != null) {
                Iterador = Iterador.getRightNodo();
            }
            return Iterador.getValue();
        }

    }

    /**
     * Obtiene un valor en cierta posicion de la lista
     * @param arg1 Posicion
     * @return  Valor en la posicion indicada
     */
    public T getByPosition(int arg1) throws Exception{

        if (Head == null) {
            throw new Exception("Arbol Vacio");
        }
        else {
            Iterador = Head;
            for (int i = 0; i < arg1; i++) {
                if (Iterador.getRightNodo() == null) {
                    throw new Exception("Posicion no existe");
                } else {
                    Iterador = Iterador.getRightNodo();
                }
            }
            return Iterador.getValue();

        }
    }




}
