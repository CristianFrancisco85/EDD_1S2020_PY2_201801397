package Estructuras;

public class BTree<T> {

    private NodoMultiple<T> Root;
    int Grade;

    public BTree(int arg1){
        this.Grade=arg1;
    }

    public int getGrade(){
        return this.Grade;
    }

    public void add(NodoMultiple<T> Nodo,T Value,int InsertionParam) throws Exception{

        //SI EL ARBOL ESTA VACIO
        if (Nodo == null) {
            Nodo = new NodoMultiple<>(Value,InsertionParam);
            //return new NodoMultiple<>(Value,InsertionParam);
        }
        else{
            //SI EL NODO ES HOJA
            if(Nodo.getNodosList().getSize()==0){
                Nodo.addValue(Value,InsertionParam);
                //SPLIT SI ES NECESARIO
                Nodo.split(Grade);
            }
            else{
                //SE OBTIENE NODO SEGUN EL RANGO
                NodoMultiple<T> auxNodo=Nodo.getNodosList().getValue(Nodo.getRange(InsertionParam));

                //SE HACE LLAMADA RECURSIVA CON EL SIGUIENTE NODO
                add(auxNodo,Value,InsertionParam);
            }

        }

    }

}
