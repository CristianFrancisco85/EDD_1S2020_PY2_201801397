package Estructuras;

import Clases.Libro;
import javafx.stage.FileChooser;

import java.io.*;

public class BTree<T> {

    private NodoMultiple<T> Root;
    int Grade;

    public BTree(int arg1){
        this.Grade=arg1;
    }

    public NodoMultiple<T> getRoot(){
        return Root;
    }

    public void setRoot(NodoMultiple<T> arg1){
        Root=arg1;
    }

    public boolean searchByIndex(NodoMultiple<T> Nodo,int SearchParam)throws Exception{

        if (Nodo==null){
            return false;
        }
        //SI EL NODO CONTIENE EL VALOR
        if (Nodo.getIndices().search(SearchParam)){
            return true;
        }
        else if(Nodo.getNodosList().getSize()!=0){

            //SE OBTIENE NODO SEGUN EL RANGO
            NodoMultiple<T> auxNodo=Nodo.getNodosList().getValue(Nodo.getRange(SearchParam));

            //SE HACE LLAMADA RECURSIVA CON EL SIGUIENTE NODO
            return searchByIndex(auxNodo,SearchParam);
        }
        else{
            return false;
        }
    }

    public T getValuebyIndex(NodoMultiple<T> Nodo,int SearchParam)throws Exception{
        //SI EL NODO CONTIENE EL VALOR
        if (Nodo.getIndices().search(SearchParam)){
            int aux=Nodo.getIndices().getPosition(SearchParam);
            return Nodo.getValues().getValue(aux);
        }
        else{

            //SE OBTIENE NODO SEGUN EL RANGO
            NodoMultiple<T> auxNodo=Nodo.getNodosList().getValue(Nodo.getRange(SearchParam));

            //SE HACE LLAMADA RECURSIVA CON EL SIGUIENTE NODO
            return getValuebyIndex(auxNodo,SearchParam);
        }
    }

    public NodoMultiple<T> add(NodoMultiple<T> Nodo,T Value,int InsertionParam) throws Exception{

        //SI EL ARBOL ESTA VACIO
        if (Nodo == null) {
            //Nodo = new NodoMultiple<>(Value,InsertionParam);
            //System.out.println("Se creo Arbol B con ISBN "+InsertionParam);
            return new NodoMultiple<>(Value,InsertionParam);
        }
        else{
            //SI EL NODO ES HOJA
            if(Nodo.getNodosList().getSize()==0){
                Nodo.addValue(Value,InsertionParam);
                //System.out.println("Insercion en Nodo Existente Arbol B con ISBN: "+InsertionParam);
                //SPLIT SI ES NECESARIO
                Nodo.split(Grade);
                return Nodo;
            }
            else{
                //SE OBTIENE NODO SEGUN EL RANGO
                NodoMultiple<T> auxNodo=Nodo.getNodosList().getValue(Nodo.getRange(InsertionParam));

                //SE HACE LLAMADA RECURSIVA CON EL SIGUIENTE NODO
                auxNodo = add(auxNodo,Value,InsertionParam);
                return Nodo;
            }
        }


    }

    public void delete(NodoMultiple<T> Nodo,int DeleteParam)throws Exception{

        //SI EL NODO CONTIENE EL VALOR
        if (Nodo.getIndices().search(DeleteParam)){
            Nodo.deleteValue(Nodo.getIndices().getPosition(DeleteParam),Grade);
            return;
        }
        else{

            //SE OBTIENE NODO SEGUN EL RANGO
            NodoMultiple<T> auxNodo=Nodo.getNodosList().getValue(Nodo.getRange(DeleteParam));

            //SE HACE LLAMADA RECURSIVA CON EL SIGUIENTE NODO
            delete(auxNodo,DeleteParam);
            return;
        }

    }

    public void getAllValues(LinkedList<Libro> ListaLibros){
        this.getRoot().getAllValues(ListaLibros);
    }

    public void graphArbol(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar Arbol B");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Archivo DOT","*.dot"));
        File selectedFile = fileChooser.showSaveDialog(null);
        if(selectedFile!=null){
            try{
                FileWriter w = new FileWriter(selectedFile);
                BufferedWriter bw = new BufferedWriter(w);
                PrintWriter wr = new PrintWriter(bw);
                wr.write("digraph{\n");
                wr.write("node [shape=record];\n");
                wr.write(this.getRoot().toDot());
                wr.append("}");
                wr.close();
                bw.close();
                ProcessBuilder pbuilder;
                String Ruta = selectedFile.getAbsolutePath().replace(".dot","");
                pbuilder = new ProcessBuilder("dot", "-Tpng", "-o", Ruta+".png ",Ruta+".dot");
                pbuilder.redirectErrorStream(true);
                pbuilder.start();

            }
            catch (IOException e){
                e.printStackTrace();
            }
            catch (Exception e){
                e.printStackTrace();
            }

        }

    }


}
