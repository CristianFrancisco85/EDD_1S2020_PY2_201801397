package Estructuras;

import Clases.CategoriaLibro;
import Clases.Libro;
import javafx.stage.FileChooser;

import java.io.*;

public class AVLTree<T,B> {

    private NodoBinario<T> Root;
    private int Height;
    private int Data;

    public AVLTree(){
        Root=null;
        Height=0;
        Data=0;
    }


    /**
     * Inserta un valor al arbol
     * @param Nodo Nodo sobre el cual se ejecuta el metodo
     * @param Value Objeto a Insertar
     * @param InsertionParam Valor por el cual se insertara en arbol
     */
    public NodoBinario<T> add(NodoBinario<T> Nodo, B Value,String InsertionParam){

        //SE HACE INSERCION
        if (Nodo == null) {
            CategoriaLibro NewCategoria=new CategoriaLibro(InsertionParam);
            NewCategoria.getBookList().addEnd(Value);
            return new NodoBinario(NewCategoria);
        }

        //SE BUSCA POSICION DE INSERCION
        if (InsertionParam.compareTo(Nodo.getValueParam())>0) {
            Nodo = add(Nodo.getRightNodo(),Value,InsertionParam);
        }
        else if (InsertionParam.compareTo(Nodo.getValueParam())<0) {
            Nodo = add(Nodo.getLeftNodo(),Value,InsertionParam);
        }
        else{
            //NODO YA EXISTE
            System.out.println("Valor repetido en AVL: "+InsertionParam);
            CategoriaLibro aux = (CategoriaLibro) Nodo.getValue();

            aux.getBookList().addEnd(Value);
            return Nodo;
        }

        //SE CALCULA DE NUEVO LA ALTURA
        if(Nodo.getRightNodo()==null && Nodo.getLeftNodo()==null){
            Nodo.setHeight(1);
        }
        else if(Nodo.getRightNodo()==null && Nodo.getLeftNodo()!=null){
            Nodo.setHeight( 1 +Nodo.getLeftNodo().getHeight());
        }
        else if(Nodo.getLeftNodo()==null && Nodo.getRightNodo()!=null){
            Nodo.setHeight( 1 +Nodo.getRightNodo().getHeight());
        }
        else{
            Nodo.setHeight( 1 + Math.max(Nodo.getRightNodo().getHeight(),Nodo.getLeftNodo().getHeight()) );
        }


        //SE BALANCEA ARBOL

        int factorBalance = getFactorBalance(Nodo);

        // Caso Left Left
        if (factorBalance > 1 && InsertionParam.compareTo(Nodo.getLeftNodo().getValueParam())<0){
            return rightRotate(Nodo);
        }
        // Caso Left Right
        if (factorBalance > 1 && InsertionParam.compareTo(Nodo.getLeftNodo().getValueParam())>0) {
            Nodo.setLeftNodo(leftRotate(Nodo.getLeftNodo()));
            return rightRotate(Nodo);
        }

        // Caso Right Right
        if (factorBalance < -1 && InsertionParam.compareTo(Nodo.getRightNodo().getValueParam())>0){
            return leftRotate(Nodo);
        }

        // Caso Right Left
        if (factorBalance < -1 && InsertionParam.compareTo(Nodo.getRightNodo().getValueParam())<0) {
            Nodo.setRightNodo(rightRotate(Nodo.getRightNodo()));
            return leftRotate(Nodo);
        }

        return Nodo;
    }

    /**
     * Inserta un valor al arbol
     * @param Nodo Nodo sobre el cual se ejecuta el metodo
     * @param DeleteParam Valor por el cual se eliminara en arbol
     */
    public NodoBinario<T> delete(NodoBinario<T> Nodo,String DeleteParam){

        if (Nodo == null){
            return Nodo;
        }

        //Se busca la posicion de insercion
        if ((DeleteParam.compareTo(Nodo.getValueParam())<0)){
            Nodo.setLeftNodo(delete(Nodo.getLeftNodo(),DeleteParam));
        }

        else if ((DeleteParam.compareTo(Nodo.getValueParam())>0)){
            Nodo.setRightNodo(delete(Nodo.getRightNodo(),DeleteParam));
        }

        //Se encuentra posicion de insercion
        else{

            //SI SOLO HAY SUB ARBOL IZQUIERDO
            if (Nodo.getLeftNodo() != null && Nodo.getRightNodo() == null ){
                Nodo = Nodo.getLeftNodo();
            }
            //SI SOLO HAY SUB ARBOL DERECHO
            else if (Nodo.getRightNodo() != null && Nodo.getLeftNodo() == null ){
                Nodo = Nodo.getRightNodo();
            }
            //SI NO HAY SUB ARBOL DERECHO NI IZQUIERDO
            else if (Nodo.getRightNodo() == null && Nodo.getLeftNodo() == null ){
                Nodo = null;
            }
            //SI HAY SUB ARBOL DERECHO E IZQUIERDO
            else {
                NodoBinario<T> auxNodo = Nodo.getRightNodo();
                //SE BUSCA HOJA MAS A LA IZQUIERDA DEL SUB ARBOL DERECHO
                while (auxNodo.getLeftNodo() != null) {
                    auxNodo = auxNodo.getLeftNodo();
                }
                Nodo.setValue(auxNodo.getValue());
                Nodo.setRightNodo(delete(Nodo.getRightNodo(),auxNodo.getValueParam()));

            }

        }

        //SI EL NODO QUE SE ELIMINO NO TIENE SUB ARBOL NO HAY QUE BALANCEAR
        if (Nodo == null){
            return Nodo;
        }

        //SE CALCULA DE NUEVO LA ALTURA
        if(Nodo.getRightNodo()==null && Nodo.getLeftNodo()==null){
            Nodo.setHeight(1);
        }
        else if(Nodo.getRightNodo()==null && Nodo.getLeftNodo()!=null){
            Nodo.setHeight( 1 +Nodo.getLeftNodo().getHeight());
        }
        else if(Nodo.getLeftNodo()==null && Nodo.getRightNodo()!=null){
            Nodo.setHeight( 1 +Nodo.getRightNodo().getHeight());
        }
        else{
            Nodo.setHeight( 1 + Math.max(Nodo.getRightNodo().getHeight(),Nodo.getLeftNodo().getHeight()) );
        }

        //SE BALANCEA ARBOL

        int factorBalance = getFactorBalance(Nodo);

        // Caso Left Left
        if (factorBalance > 1 && DeleteParam.compareTo(Nodo.getLeftNodo().getValueParam())<0){
            return rightRotate(Nodo);
        }
        // Caso Left Right
        if (factorBalance > 1 && DeleteParam.compareTo(Nodo.getLeftNodo().getValueParam())>0) {
            Nodo.setLeftNodo(leftRotate(Nodo.getLeftNodo()));
            return rightRotate(Nodo);
        }

        // Caso Right Right
        if (factorBalance < -1 && DeleteParam.compareTo(Nodo.getRightNodo().getValueParam())>0){
            return leftRotate(Nodo);
        }

        // Caso Right Left
        if (factorBalance < -1 && DeleteParam.compareTo(Nodo.getRightNodo().getValueParam())<0) {
            Nodo.setRightNodo(rightRotate(Nodo.getRightNodo()));
            return leftRotate(Nodo);
        }

        return Nodo;

    }

    /**
     * Busca si existe un elemento
     * @param SearchParam Parametro sobre el que se hara la bisquedad
     */
    public boolean exist(NodoBinario<T> Nodo,String SearchParam){

        if (Nodo == null) {
            return false;
        }
        //SE BUSCA POSICION DE INSERCION
        if (SearchParam.compareTo(Nodo.getValueParam())>0) {
            return  exist(Nodo.getRightNodo(),SearchParam);
        }
        else if (SearchParam.compareTo(Nodo.getValueParam())<0) {
            return  exist(Nodo.getRightNodo(),SearchParam);
        }
        else{
            return true;
        }
    }


    /**
     * Regresa el factor de balance de un nodo
     * @param Nodo Nodo spbre el cual se calcula el factor de balance
     * @return Factor de Balance
     */
    private int getFactorBalance(NodoBinario<T> Nodo) {
        if (Nodo == null){
            return 0;
        }
        //SE RETORNA LA RESTA DE LAS ALTURAS
        if(Nodo.getRightNodo()==null && Nodo.getLeftNodo()==null){
            return 0;
        }
        else if(Nodo.getRightNodo()==null && Nodo.getLeftNodo()!=null){
            return Nodo.getLeftNodo().getHeight() - 0;
        }
        else if(Nodo.getLeftNodo()==null && Nodo.getRightNodo()!=null){
            return 0 - Nodo.getRightNodo().getHeight();
        }
        else{
            return Nodo.getLeftNodo().getHeight() - Nodo.getRightNodo().getHeight();
        }

    }

    /**
     *
     * @return Regresa Raiz de Arbol
     */
    public NodoBinario<T> getRoot(){
        return Root;
    }

    public void setRoot(NodoBinario<T> arg1){
        this.Root=arg1;
    }

    /**
     * Grafica Arbol
     */
    public void graphArbol(){
        LinkedList<NodoBinario<T>> NodosList=new LinkedList();
        this.getPreOrdenList(this.Root,NodosList);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar Arbol AVL");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Archivo DOT","*.dot"));
        File selectedFile = fileChooser.showSaveDialog(null);
        if(selectedFile!=null){
            try{
                FileWriter w = new FileWriter(selectedFile);
                BufferedWriter bw = new BufferedWriter(w);
                PrintWriter wr = new PrintWriter(bw);
                wr.write("digraph{\n");
                wr.write("node [shape=circle];\n");

                for(int i=0;i<NodosList.getSize();i++){
                    if(NodosList.getValue(i).getLeftNodo()!=null){
                        wr.write("\""+NodosList.getValue(i).toString()+"\"");
                        wr.write("->");
                        wr.write("\""+NodosList.getValue(i).getLeftNodo().toString()+"\"\n");
                    }
                    if(NodosList.getValue(i).getRightNodo()!=null){
                        wr.write("\""+NodosList.getValue(i).toString()+"\"");
                        wr.write("->");
                        wr.write("\""+NodosList.getValue(i).getRightNodo().toString()+"\"\n");
                    }
                }
                wr.append("}");
                wr.close();
                bw.close();
                ProcessBuilder pbuilder = new ProcessBuilder();
                String Ruta = selectedFile.getAbsolutePath().replace(".dot","");
                pbuilder.command(" dot -Tpng -o "+Ruta+".png "+Ruta+".dot");
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

    public void getPreOrdenList(NodoBinario<T> Raiz,LinkedList<NodoBinario<T>> Lista){
        if(Raiz!=null){
            Lista.addEnd(Raiz);
            getPreOrdenList(Raiz.getLeftNodo(),Lista);
            getPreOrdenList(Raiz.getRightNodo(),Lista);
        }
    }



    /**
     * Realiza Rotacion hacia la derecha sobre un nodo
     * @param Nodo Nodo donde se realiza la rotacion
     * @return
     */
    private NodoBinario<T> rightRotate(NodoBinario<T> Nodo) {
        NodoBinario<T> nodoLeft = Nodo.getLeftNodo();
        NodoBinario<T> nodoLeftRight = nodoLeft.getRightNodo();

        nodoLeft.setRightNodo(Nodo);
        Nodo.setLeftNodo(nodoLeftRight);

        Nodo.setHeight(Math.max(Nodo.getLeftNodo().getHeight(),Nodo.getRightNodo().getHeight())+1);
        nodoLeft.setHeight(Math.max(nodoLeft.getLeftNodo().getHeight(),nodoLeft.getRightNodo().getHeight())+1);

        return nodoLeft;
    }

    /**
     * Realiza Rotacion hacia la izquierda sobre un nodo
     * @param Nodo Nodo donde se realiza la rotacion
     * @return
     */
    private NodoBinario<T> leftRotate(NodoBinario<T> Nodo) {
        NodoBinario<T> nodoRight = Nodo.getRightNodo();
        NodoBinario<T> nodoRightLeft = nodoRight.getLeftNodo();

        nodoRight.setLeftNodo(Nodo);
        Nodo.setRightNodo(nodoRightLeft);

        Nodo.setHeight(Math.max(Nodo.getLeftNodo().getHeight(),Nodo.getRightNodo().getHeight())+1);
        nodoRight.setHeight(Math.max(nodoRightLeft.getLeftNodo().getHeight(),nodoRightLeft.getRightNodo().getHeight())+1);

        return nodoRight;

    }

}
