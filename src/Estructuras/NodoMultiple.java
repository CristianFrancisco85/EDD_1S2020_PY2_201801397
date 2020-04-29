package Estructuras;

import Clases.Libro;

public class NodoMultiple<T> {
    private LinkedList<Integer> IndicesList;
    private LinkedList<T> ValueList;
    private LinkedList<NodoMultiple<T>> NodosList;
    private int Grade;
    private  NodoMultiple<T> Parent;

    //CONSTRUCTORES

    public NodoMultiple(){
        Grade=0;
        IndicesList = new LinkedList<>();
        NodosList = new LinkedList<>();
        ValueList = new LinkedList<>();
    }

    public NodoMultiple(T Value,int Index){
        Grade=0;
        IndicesList = new LinkedList<>();
        NodosList = new LinkedList<>();
        ValueList = new LinkedList<>();
        Parent=null;
        this.addValue(Value,Index);
    }

    public NodoMultiple(NodoMultiple<T> Padre){
        Grade=0;
        IndicesList = new LinkedList<>();
        NodosList = new LinkedList<>();
        ValueList = new LinkedList<>();
        Parent=Padre;
    }



    public int getGrade(){
        return this.Grade;
    }
    public void setGrade(int arg1){
        this.Grade=arg1;
    }

    /**
     * Obtiene Nodo Padre
     * @return Nodo Padre
     */
    public NodoMultiple<T> getParent(){
        return Parent;
    }

    /**
     * Redefine Nodo Padre
     * @param arg1 Nuevo Nodo Padre
     */
    public void setParent(NodoMultiple<T> arg1){
        Parent=arg1;
    }

    /**
     * Redefine el padre de todos los Nodos como this.
     */
    public void redifineParent(){

        for(int i=0;i<this.getNodosList().getSize();i++){
            try {
                this.getNodosList().getValue(i).setParent(this);
            }
            catch (Exception e){

            }
        }

    }

    /**
     * Genera representacio en lenguaje Dot del nodo y sus hijos recursivamente
     * @return Cadena en lenguaje Dot de el arbol completo
     */
    public String toDot(){

        StringBuilder dotString = new StringBuilder();

        dotString.append(this.hashCode());
        dotString.append("[label=\"<P0>");
        for(int i=0;i<this.getIndices().getSize();i++){
            try {
                Libro auxLibro = (Libro)this.getValues().getValue(i);
                dotString.append("|" + this.getIndices().getValue(i).toString()+"\\nTitulo: "+auxLibro.getTitulo()+
                "\\nISBN: "+auxLibro.toString());
                dotString.append("|<P"+(i+1)+">");
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        dotString.append("\"];\n");

        for(int i=0;i<this.getNodosList().getSize();i++){
            try {
                dotString.append(this.getNodosList().getValue(i).toDot());
                dotString.append(this.hashCode()+":P"+i+"->"+this.getNodosList().getValue(i).hashCode()+";\n");
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        return dotString.toString();
    }

    /**
     * Obtiene todos los valores del arbol en la lista
     * @param ListaLibros Lista donde se almacenaran los libros
     */
    public void getAllValues(LinkedList<Libro> ListaLibros){

        for(int i=0;i<this.getIndices().getSize();i++){
            try {
                Libro auxLibro = (Libro)this.getValues().getValue(i);
                ListaLibros.addBegin(auxLibro);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        for(int i=0;i<this.getNodosList().getSize();i++){
            try {
                this.getNodosList().getValue(i).getAllValues(ListaLibros);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Obtiene lista de Valores
     * @return Lista de Valores
     */
    public LinkedList<T> getValues(){
        return this.ValueList;
    }

    /**
     * Obtiene Lista Enlazada de las llaves.
     * @return Lista de Indices
     */
    public LinkedList<Integer> getIndices(){return this.IndicesList; }

    /**
     * Obtiene Lista de Nodos
     * @return Lista de Nodos
     */
    public LinkedList<NodoMultiple<T>> getNodosList(){
        return NodosList;
    }


    //OPERACIONES DE SPLIT

    public void split(int Grade){
        //Se verifica si se necesita split
        if(this.Grade==Grade){
            if(this.getParent()==null&&this.getNodosList().getSize()!=0){
                this.split1(Grade);
            }
            else if(this.getParent()==null&&this.getNodosList().getSize()==0){
                this.split2(Grade);
            }
            else if(this.getParent()!=null&&this.getNodosList().getSize()!=0){
                this.split3(Grade);
            }
            else if(this.getParent()!=null&&this.getNodosList().getSize()==0){
                this.split4(Grade);
            }
        }
    }

    //SPLIT CUANDO PADRE ES NULL Y TIENE HIJOS
    public void split1(int Grade){

        try{
            int PosPivote = Math.round(Grade/2);
            //Se separan las dos mitades
            NodoMultiple<T> NewNodo1=new NodoMultiple<>(this);
            NodoMultiple<T> NewNodo2=new NodoMultiple<>(this);
            //SE AGREGA DESDE INICIO HACIA PIVOTE
            for(int i=0;i<PosPivote;i++){
                NewNodo1.addValue(this.ValueList.getValue(0),this.IndicesList.getValue(0));
                NewNodo1.getNodosList().addEnd(this.getNodosList().getValue(0));
                this.ValueList.removeByPosition(0);
                this.IndicesList.removeByPosition(0);
                this.NodosList.removeByPosition(0);
                this.Grade--;
            }
            NewNodo1.getNodosList().addEnd(this.getNodosList().getValue(0));
            this.NodosList.removeByPosition(0);
            NewNodo1.redifineParent();
            //SE AGREGA DESDE FINAL HACIA PIVOTE
            for(int i=PosPivote+1;i<Grade;i++){
                NewNodo2.addValue(this.ValueList.getValue(this.IndicesList.getSize()-1),this.IndicesList.getValue(this.IndicesList.getSize()-1));
                NewNodo2.getNodosList().addBegin(this.getNodosList().getValue(this.NodosList.getSize()-1));
                this.ValueList.removeByPosition(this.IndicesList.getSize()-1);
                this.IndicesList.removeByPosition(this.IndicesList.getSize()-1);
                this.NodosList.removeByPosition(this.NodosList.getSize()-1);
                this.Grade--;
            }
            NewNodo2.getNodosList().addBegin(this.getNodosList().getValue(this.IndicesList.getSize()-1));
            this.NodosList.removeByPosition(this.NodosList.getSize()-1);
            NewNodo2.redifineParent();

            this.getNodosList().addEnd(NewNodo1);
            this.getNodosList().addEnd(NewNodo2);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    //SPLIT CUANDO PADRE ES NULL Y NO TIENE HIJOS
    public void split2(int Grade){
        try{
            int PosPivote = Math.round(Grade/2);
            //Se separan las dos mitades
            NodoMultiple<T> NewNodo1=new NodoMultiple<>(this);
            NodoMultiple<T> NewNodo2=new NodoMultiple<>(this);
            //SE AGREGA DESDE INICIO HASTA PIVOTE-1
            for(int i=0;i<PosPivote;i++){
                NewNodo1.addValue(this.ValueList.getValue(0),this.IndicesList.getValue(0));
                this.ValueList.removeByPosition(0);
                this.IndicesList.removeByPosition(0);
                this.Grade--;
            }
            //SE AGREGA DESDE FINAL HASTA PIVOTE+1
            for(int i=PosPivote+1;i<Grade;i++){
                NewNodo2.addValue(this.ValueList.getValue(this.IndicesList.getSize()-1),this.IndicesList.getValue(this.IndicesList.getSize()-1));
                this.ValueList.removeByPosition(this.IndicesList.getSize()-1);
                this.IndicesList.removeByPosition(this.IndicesList.getSize()-1);
                this.Grade--;
            }
            this.getNodosList().addEnd(NewNodo1);
            this.getNodosList().addEnd(NewNodo2);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    //SPLIT CUANDO PADRE NO ES NULL Y TIENE HIJOS
    public void split3(int Grade){
        try {
            int PosPivote = Math.round(Grade / 2);
            int Pivote = this.IndicesList.getValue(PosPivote);
            T ValuePivote = this.ValueList.getValue(PosPivote);
            //Se sube elemento Pivote
            this.getParent().addValue(ValuePivote, Pivote);
            //Se separan las dos mitades
            NodoMultiple<T> NewNodo = new NodoMultiple<>(this.getParent());
            for (int i = 0; i < PosPivote; i++) {
                NewNodo.addValue(this.ValueList.getValue(0), this.IndicesList.getValue(0));
                NewNodo.getNodosList().addEnd(this.getNodosList().getValue(0));
                this.ValueList.removeByPosition(0);
                this.IndicesList.removeByPosition(0);
                this.NodosList.removeByPosition(0);
                this.Grade--;
            }
            NewNodo.getNodosList().addEnd(this.getNodosList().getValue(0));
            NewNodo.redifineParent();
            this.ValueList.removeByPosition(0);
            this.IndicesList.removeByPosition(0);
            this.NodosList.removeByPosition(0);
            this.Grade--;
            //this.getNodosList().addEnd(new NodoMultiple<>(this));

            this.getParent().getNodosList().addIn(NewNodo, this.getParent().IndicesList.getPosition(Pivote));
            this.Parent.split(Grade);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //SPLIT CUANDO PADRE NO ES NULL Y NO TIENE HIJOS
    public void split4(int Grade){
        try {
            int PosPivote = Math.round(Grade / 2);
            int Pivote = this.IndicesList.getValue(PosPivote);
            T ValuePivote = this.ValueList.getValue(PosPivote);
            //Se sube elemento Pivote
            this.getParent().addValue(ValuePivote, Pivote);
            //Se separan las dos mitades
            NodoMultiple<T> NewNodo = new NodoMultiple<>(this.getParent());
            for (int i = 0; i < PosPivote; i++) {
                NewNodo.addValue(this.ValueList.getValue(0), this.IndicesList.getValue(0));
                this.ValueList.removeByPosition(0);
                this.IndicesList.removeByPosition(0);
                this.Grade--;
            }
            this.ValueList.removeByPosition(0);
            this.IndicesList.removeByPosition(0);
            this.Grade--;
            this.getParent().getNodosList().addIn(NewNodo, this.getParent().IndicesList.getPosition(Pivote));
            this.Parent.split(Grade);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Agrea un valor en el arreglo del nodo
     * @param arg1 Objeto a insertar
     * @param Index Indice de referencia para insercion
     */
    public void addValue(T arg1,int Index){

        int auxPosition=0;

        for(int i=0;i<this.IndicesList.getSize();i++){
            try {
                if(this.IndicesList.getValue(i)>Index){
                    auxPosition=i;
                    break;
                }
            }
            catch (Exception e){

            }
            if(this.IndicesList.getSize()-1==i){
                auxPosition=i+1;
                break;
            }
        }

        this.IndicesList.addIn(Index,auxPosition);
        this.ValueList.addIn(arg1,auxPosition);
        this.Grade++;

    }


    public void deleteValue(int Index,int Grade){
        this.getIndices().removeByPosition(Index);
        this.getValues().removeByPosition(Index);
        this.Grade--;

        if(this.getNodosList().getSize()!=0 && this.getParent()!=null){
            try {
                //SE OBTIENE NODO MAS A DERECHA DEL SUB ARBOL IZQUIERDO
                NodoMultiple auxNodo = this.getNodosList().getValue(Index);
                auxNodo=auxNodo.getLeafRight(auxNodo);

                //SE OBTIENE VALOR E INDICE DE ESE NODO
                T auxValue=(T) auxNodo.getValues().getValue(auxNodo.ValueList.getSize()-1);
                int auxIndice=(Integer)auxNodo.ValueList.getValue(auxNodo.ValueList.getSize()-1);

                //SE HACE LE INTERCAMBIO DEL ELEMENTO ELIMINADO POR auxValue
                this.addValue(auxValue,auxIndice);
                auxNodo.ValueList.removeByPosition(this.ValueList.getSize()-1);
                auxNodo.IndicesList.removeByPosition(this.IndicesList.getSize()-1);
                auxNodo.Grade--;

                //SI EL NODO TIENE MENOS de 2n , se produce subOcupacion
                if(auxNodo.Grade<(Grade-1)/2){
                    auxNodo.subOcupacion(auxNodo,Grade);
                }

            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        else{
            //SI EL NODO TIENE MENOS de 2n , se produce subOcupacion
            if(this.Grade<(Grade-1)/2){
                this.subOcupacion(this,Grade);
            }
        }


    }

    public void subOcupacion(NodoMultiple<T> Nodo,int Grade){

        //PARA NODOS QUE NO SON LA RAIZ
        if(Nodo.getParent()!=null) {
            NodoMultiple<T> auxParent = Nodo.getParent();
            LinkedList<NodoMultiple<T>> auxNodosList = Nodo.getParent().getNodosList();
            LinkedList<T> auxValueList = Nodo.getParent().getValues();
            LinkedList<Integer> auxIndicesList = Nodo.getParent().getIndices();
            int IndexNodo = auxNodosList.getPosition(Nodo);

            try {
                //SE OBTIENE NODOS HOJA HERMANOS
                if (IndexNodo + 1 < auxNodosList.getSize()) {
                    NodoMultiple<T> auxRightNodo = getLeafLeft(auxNodosList.getValue(IndexNodo + 1));
                    if (auxRightNodo.getGrade() > (Grade - 1) / 2) {
                        //SE BAJA UN VALOR DE PADRE
                        Nodo.addValue(auxValueList.getValue(IndexNodo), auxIndicesList.getValue(IndexNodo));
                        auxValueList.removeByPosition(IndexNodo);
                        auxIndicesList.removeByPosition(IndexNodo);
                        auxParent.Grade--;
                        auxParent.addValue(auxRightNodo.getValues().getValue(0), (Integer) auxRightNodo.getIndices().getValue(0));
                        auxRightNodo.ValueList.removeByPosition(0);
                        auxRightNodo.IndicesList.removeByPosition(0);
                        auxRightNodo.Grade--;
                        return;
                    }


                }
                else {
                    NodoMultiple<T> auxLeftNodo = getLeafRight(auxNodosList.getValue(IndexNodo - 1));
                    if (auxLeftNodo.getGrade() > (Grade - 1) / 2) {
                        //SE BAJA UN VALOR DE PADRE
                        Nodo.addValue(auxValueList.getValue(IndexNodo), auxIndicesList.getPosition(IndexNodo));
                        auxValueList.removeByPosition(IndexNodo);
                        auxIndicesList.removeByPosition(IndexNodo);
                        auxParent.Grade--;
                        auxParent.addValue(auxLeftNodo.getValues().getValue(this.ValueList.getSize()-1),
                                (Integer) auxLeftNodo.getIndices().getValue(this.IndicesList.getSize()-1));
                        auxLeftNodo.ValueList.removeByPosition(this.ValueList.getSize()-1);
                        auxLeftNodo.IndicesList.removeByPosition(this.IndicesList.getSize()-1);
                        auxLeftNodo.Grade--;
                        return;
                    }

                }


                //SI NO HAY NODOS HERMANOS PARA JUNTAR
                if (IndexNodo + 1 < auxNodosList.getSize()) {
                    NodoMultiple<T> auxRightNodo = auxNodosList.getValue(IndexNodo + 1);
                    if (auxRightNodo.getGrade() > (Grade - 1) / 2) {
                        //SE BAJA UN VALOR DE PADRE
                        Nodo.addValue(auxValueList.getValue(IndexNodo), auxIndicesList.getValue(IndexNodo));
                        auxValueList.removeByPosition(IndexNodo);
                        auxIndicesList.removeByPosition(IndexNodo);
                        auxParent.Grade--;
                        auxParent.addValue(auxRightNodo.getValues().getValue(0), (Integer) auxRightNodo.getIndices().getValue(0));
                        auxRightNodo.ValueList.removeByPosition(0);
                        auxRightNodo.IndicesList.removeByPosition(0);
                        auxRightNodo.Grade--;

                        Nodo.NodosList.addEnd(auxRightNodo.NodosList.getValue(0));
                        auxRightNodo.NodosList.removeByPosition(0);
                        Nodo.redifineParent();
                        return;
                    }
                }
                else{
                    NodoMultiple<T> auxLeftNodo = auxNodosList.getValue(IndexNodo + 1);
                    if (auxLeftNodo.getGrade() > (Grade - 1) / 2) {
                        //SE BAJA UN VALOR DE PADRE
                        Nodo.addValue(auxValueList.getValue(IndexNodo), auxIndicesList.getValue(IndexNodo));
                        auxValueList.removeByPosition(IndexNodo);
                        auxIndicesList.removeByPosition(IndexNodo);
                        auxParent.Grade--;
                        auxParent.addValue(auxLeftNodo.getValues().getValue(this.ValueList.getSize()-1),
                                (Integer) auxLeftNodo.getIndices().getValue(this.IndicesList.getSize()-1));
                        auxLeftNodo.ValueList.removeByPosition(this.ValueList.getSize()-1);
                        auxLeftNodo.IndicesList.removeByPosition(this.IndicesList.getSize()-1);
                        auxLeftNodo.Grade--;

                        Nodo.NodosList.addEnd(auxLeftNodo.NodosList.getValue(this.NodosList.getSize()-1));
                        auxLeftNodo.NodosList.removeByPosition(this.NodosList.getSize()-1);
                        Nodo.redifineParent();
                        return;
                    }

                }


                //SI NO HAY NODOS HERMANOS QUE PUEDAN PRESTAR UN VALOR ENTONCES SE UNEN DOS HOJAS
                if (IndexNodo + 1 < auxNodosList.getSize()) {
                    NodoMultiple<T> auxRightNodo = auxNodosList.getValue(IndexNodo + 1);
                    //SE BAJA UN VALOR DE PADRE
                    Nodo.addValue(auxValueList.getValue(IndexNodo), auxIndicesList.getValue(IndexNodo));
                    auxValueList.removeByPosition(IndexNodo);
                    auxIndicesList.removeByPosition(IndexNodo);
                    auxParent.Grade--;
                    for (int i = 0; i < auxRightNodo.IndicesList.getSize(); i++) {
                        Nodo.addValue(auxRightNodo.ValueList.getValue(i), auxRightNodo.IndicesList.getValue(i));
                        if (auxRightNodo.getNodosList().getSize() != 0) {
                            Nodo.NodosList.addEnd(auxRightNodo.getNodosList().getValue(0));
                            auxRightNodo.NodosList.removeByPosition(0);
                        }
                    }
                    if (auxRightNodo.getNodosList().getSize() != 0) {
                        Nodo.NodosList.addEnd(auxRightNodo.getNodosList().getValue(0));
                        Nodo.redifineParent();
                    }
                    auxNodosList.removeByPosition(IndexNodo + 1);
                }
                else {
                    NodoMultiple<T> auxLeftNodo = auxNodosList.getValue(IndexNodo - 1);
                    //SE BAJA UN VALOR DE PADRE
                    Nodo.addValue(auxValueList.getValue(IndexNodo - 1), auxIndicesList.getValue(IndexNodo - 1));
                    auxValueList.removeByPosition(IndexNodo - 1);
                    auxIndicesList.removeByPosition(IndexNodo - 1);
                    auxParent.Grade--;
                    for (int i = 0; i < auxLeftNodo.IndicesList.getSize(); i++) {
                        Nodo.addValue(auxLeftNodo.ValueList.getValue(i), auxLeftNodo.getIndices().getValue(i));
                        if (auxLeftNodo.getNodosList().getSize() != 0) {
                            Nodo.NodosList.addBegin(auxLeftNodo.getNodosList().getValue(auxLeftNodo.IndicesList.getSize() - 1));
                            auxLeftNodo.getNodosList().removeByPosition(auxLeftNodo.IndicesList.getSize() - 1);
                        }
                    }
                    if (auxLeftNodo.getNodosList().getSize() != 0) {
                        Nodo.NodosList.addBegin(auxLeftNodo.getNodosList().getValue(0));
                        Nodo.redifineParent();
                    }
                    auxNodosList.removeByPosition(IndexNodo - 1);
                }

                if (auxParent.Grade < (Grade - 1) / 2) {
                    auxParent.subOcupacion(auxParent, Grade);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //PARA NODO RAIZ
        else  if (Nodo.Grade ==0 && Nodo.NodosList.getSize()!=1) {
            int IndexNodo = 0;
            try {

                NodoMultiple<T> auxRightNodo = getLeafLeft(Nodo.NodosList.getValue(IndexNodo + 1));
                Nodo.addValue(auxRightNodo.ValueList.getValue(0), auxRightNodo.IndicesList.getValue(0));
                auxRightNodo.ValueList.removeByPosition(0);
                auxRightNodo.IndicesList.removeByPosition(0);
                auxRightNodo.Grade--;

                if (auxRightNodo.Grade < (Grade - 1) / 2) {
                    auxRightNodo.subOcupacion(auxRightNodo, Grade);
                }
                return;

            }
            catch (Exception e){
                e.printStackTrace();
            }

        }

    }

    /**
     * Obtiene Nodo Hoja mas a la derecha
     * @param Nodo Nodo de partida
     * @return Nodo Hoja
     * @throws Exception
     */

    public NodoMultiple<T> getLeafRight(NodoMultiple<T> Nodo) throws Exception{

        //SI EL NODO ES HOJA
        if(Nodo.getNodosList().getSize()==0){
            return Nodo;
        }
        else{
            //SE OBTIENE NODO SEGUN EL RANGO
            NodoMultiple<T> auxNodo=Nodo.getNodosList().getValue(Nodo.getNodosList().getSize()-1);

            //SE HACE LLAMADA RECURSIVA CON EL SIGUIENTE NODO
            return auxNodo.getLeafRight(auxNodo);
        }

    }

    /**
     * Obtiene Nodo Hoja mas a la izquierda
     * @param Nodo Nodo de partida
     * @return Nodo Hoja
     * @throws Exception
     */

    public NodoMultiple<T> getLeafLeft(NodoMultiple<T> Nodo) throws Exception{

        //SI EL NODO ES HOJA
        if(Nodo.getNodosList().getSize()==0){
            return Nodo;
        }
        else{
            //SE OBTIENE NODO SEGUN EL RANGO
            NodoMultiple<T> auxNodo=Nodo.getNodosList().getValue(0);

            //SE HACE LLAMADA RECURSIVA CON EL SIGUIENTE NODO
            return auxNodo.getLeafLeft(auxNodo);
        }

    }


    /**
     * Devuelve el numero de Nodo para seguir con insercion
     */
    public int getRange(int Index){
        int numNodo=0;

        for(int i=0;i<IndicesList.getSize();i++){
            try {
                if(IndicesList.getValue(i)>Index){
                    numNodo=i;
                    break;
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
            if(IndicesList.getSize()-1==i){
                numNodo=i+1;
                break;
            }
        }

        return numNodo;

    }


}
