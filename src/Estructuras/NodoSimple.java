package Estructuras;

public class NodoSimple<T> {
    private T Value;
    private NodoSimple<T> NextNodo;

    NodoSimple(){
        this.Value=null;
        this.NextNodo=null;
    }
    NodoSimple(T arg1){
        this.Value=arg1;
        this.NextNodo=null;
    }

    public T getValue(){
        return this.Value;
    }

    public void setValue(T arg1){
        this.Value=arg1;
    }

    public NodoSimple getNextNodo(){
        return this.NextNodo;
    }

    public void setNextNodo(NodoSimple arg1){
        this.NextNodo=arg1;
    }
}
