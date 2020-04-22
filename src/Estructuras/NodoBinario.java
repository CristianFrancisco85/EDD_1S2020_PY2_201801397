package Estructuras;

public class NodoBinario<T> {
    private T Value;
    private NodoBinario<T> RightNodo;
    private NodoBinario<T> LeftNodo;
    private int Height;

    NodoBinario(){
        this.Value=null;
        this.RightNodo=null;
        this.LeftNodo=null;
        Height=1;
    }
    NodoBinario(T arg1){
        this.Value=arg1;
        this.RightNodo=null;
        this.LeftNodo=null;
        Height=1;
    }

    public String getValueParam(){
        return this.Value.toString();
    }

    public T getValue(){
        return this.Value;
    }
    public void setValue(T arg1){
        this.Value=arg1;
    }

    public int getHeight(){
        return this.Height;
    }
    public void setHeight(int arg1){
        this.Height=arg1;
    }

    public NodoBinario getRightNodo(){
        return this.RightNodo;
    }
    public NodoBinario getLeftNodo(){
        return this.LeftNodo;
    }

    public void setRightNodo(NodoBinario arg1){
        this.RightNodo=arg1;
    }
    public void setLeftNodo(NodoBinario arg1){
        this.LeftNodo=arg1;
    }

}
