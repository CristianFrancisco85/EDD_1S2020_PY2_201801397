package Estructuras;

public class NodoMultiple<T> {
    private LinkedList<Integer> IndicesList;
    private LinkedList<T> ValueList;
    private LinkedList<NodoMultiple<T>> NodosList;
    private int Grade;
    private  NodoMultiple<T> Parent;

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

    public NodoMultiple(T Value,int Index,NodoMultiple<T> Padre){
        Grade=0;
        IndicesList = new LinkedList<>();
        NodosList = new LinkedList<>();
        ValueList = new LinkedList<>();
        Parent=Padre;
        this.addValue(Value,Index);
    }


    public int getGrade(){
        return this.Grade;
    }

    public void setGrade(int arg1){
        this.Grade=arg1;
    }

    public NodoMultiple<T> getParent(){
        return Parent;
    }

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
            else {
                try {
                    int PosPivote = Math.round(Grade / 2) - 1;
                    int Pivote = this.IndicesList.getValue(PosPivote);
                    T ValuePivote = this.ValueList.getValue(Pivote);
                    //Se sube elemento Pivote
                    this.getParent().addValue(ValuePivote, Pivote);
                    //Se separan las dos mitades
                    NodoMultiple<T> NewNodo = new NodoMultiple<>();
                    for (int i = 0; i < PosPivote; i++) {
                        NewNodo.addValue(this.ValueList.getValue(0), this.IndicesList.getValue(0));
                        NewNodo.getNodosList().addEnd(this.getNodosList().getValue(0));
                        this.ValueList.removeByPosition(0);
                        this.IndicesList.removeByPosition(0);
                        this.NodosList.removeByPosition(0);
                    }
                    this.getParent().getNodosList().addIn(NewNodo, this.getParent().IndicesList.getPosition(Pivote));
                    this.Parent.split(Grade);
                } catch (Exception e) {

                }
            }
        }
    }
    //SPLIT CUANDO PADRE ES NULL Y TIENE HIJOS
    public void split1(int Grade){

        try{
            int PosPivote = Math.round(Grade/2)-1;
            //Se separan las dos mitades
            NodoMultiple<T> NewNodo1=new NodoMultiple<>();
            NodoMultiple<T> NewNodo2=new NodoMultiple<>();
            //SE AGREGA DESDE INICIO HACIA PIVOTE
            for(int i=0;i<PosPivote;i++){
                NewNodo1.addValue(this.ValueList.getValue(0),this.IndicesList.getValue(0));
                NewNodo1.getNodosList().addEnd(this.getNodosList().getValue(0));
                this.ValueList.removeByPosition(0);
                this.IndicesList.removeByPosition(0);
                this.NodosList.removeByPosition(0);
            }
            NewNodo1.getNodosList().addEnd(this.getNodosList().getValue(0));
            this.NodosList.removeByPosition(0);
            //SE AGREAGA DESDE FINAL HACIA PIVOTE
            for(int i=PosPivote+1;i<Grade;i++){
                NewNodo2.addValue(this.ValueList.getValue(i),this.IndicesList.getValue(this.IndicesList.getSize()-1));
                NewNodo2.getNodosList().addEnd(this.getNodosList().getValue(this.IndicesList.getSize()-1));
                this.ValueList.removeByPosition(this.IndicesList.getSize()-1);
                this.IndicesList.removeByPosition(this.IndicesList.getSize()-1);
                this.NodosList.removeByPosition(this.IndicesList.getSize()-1);
            }
            NewNodo2.getNodosList().addEnd(this.getNodosList().getValue(this.IndicesList.getSize()-1));
            this.NodosList.removeByPosition(this.IndicesList.getSize()-1);

            this.getNodosList().addEnd(NewNodo1);
            this.getNodosList().addEnd(NewNodo2);
        }
        catch (Exception e){

        }

    }

    //SPLIT CUANDO PADRE ES NULL Y NO TIENE HIJOS
    public void split2(int Grade){
        try{
            int PosPivote = Math.round(Grade/2)-1;
            //Se separan las dos mitades
            NodoMultiple<T> NewNodo1=new NodoMultiple<>();
            NodoMultiple<T> NewNodo2=new NodoMultiple<>();
            //SE AGREGA DESDE INICIO HACIA PIVOTE
            for(int i=0;i<PosPivote;i++){
                NewNodo1.addValue(this.ValueList.getValue(0),this.IndicesList.getValue(0));
                this.ValueList.removeByPosition(0);
                this.IndicesList.removeByPosition(0);
            }
            //SE AGREAGA DESDE FINAL HACIA PIVOTE
            for(int i=PosPivote+1;i<Grade;i++){
                NewNodo2.addValue(this.ValueList.getValue(i),this.IndicesList.getValue(this.IndicesList.getSize()-1));
                this.ValueList.removeByPosition(this.IndicesList.getSize()-1);
                this.IndicesList.removeByPosition(this.IndicesList.getSize()-1);
            }
            this.getNodosList().addEnd(NewNodo1);
            this.getNodosList().addEnd(NewNodo2);
        }
        catch (Exception e){

        }

    }

    //SPLIT CUANDO PADRE NO ES NULL Y TIENE HIJOS
    public void split3(int Grade){
        try {
            int PosPivote = Math.round(Grade / 2) - 1;
            int Pivote = this.IndicesList.getValue(PosPivote);
            T ValuePivote = this.ValueList.getValue(Pivote);
            //Se sube elemento Pivote
            this.getParent().addValue(ValuePivote, Pivote);
            //Se separan las dos mitades
            NodoMultiple<T> NewNodo = new NodoMultiple<>();
            for (int i = 0; i < PosPivote; i++) {
                NewNodo.addValue(this.ValueList.getValue(0), this.IndicesList.getValue(0));
                NewNodo.getNodosList().addEnd(this.getNodosList().getValue(0));
                this.ValueList.removeByPosition(0);
                this.IndicesList.removeByPosition(0);
                this.NodosList.removeByPosition(0);
            }
            this.getNodosList().addEnd(new NodoMultiple<>());
            this.getParent().getNodosList().addIn(NewNodo, this.getParent().IndicesList.getPosition(Pivote));
            this.Parent.split(Grade);
        } catch (Exception e) {

        }

    }

    //SPLIT CUANDO PADRE NO ES NULL Y NO TIENE HIJOS
    public void split4(int Grade){
        try {
            int PosPivote = Math.round(Grade / 2) - 1;
            int Pivote = this.IndicesList.getValue(PosPivote);
            T ValuePivote = this.ValueList.getValue(Pivote);
            //Se sube elemento Pivote
            this.getParent().addValue(ValuePivote, Pivote);
            //Se separan las dos mitades
            NodoMultiple<T> NewNodo = new NodoMultiple<>();
            for (int i = 0; i < PosPivote; i++) {
                NewNodo.addValue(this.ValueList.getValue(0), this.IndicesList.getValue(0));
                this.ValueList.removeByPosition(0);
                this.IndicesList.removeByPosition(0);
            }
            this.getParent().getNodosList().addIn(NewNodo, this.getParent().IndicesList.getPosition(Pivote));
            this.Parent.split(Grade);
        } catch (Exception e) {

        }

    }


    public LinkedList<T> getValues(){
        return this.ValueList;
    }

    public void addValue(T arg1,int Index){

        int auxPosition=0;

        for(int i=0;i<IndicesList.getSize();i++){
            try {
                if(IndicesList.getValue(i)>Index){
                    auxPosition=i;
                    break;
                }
            }
            catch (Exception e){

            }
            if(IndicesList.getSize()-1==i){
                auxPosition=i+1;
                break;
            }
        }

        IndicesList.addIn(Index,auxPosition);
        ValueList.addIn(arg1,auxPosition);
        Grade++;

    }

    public LinkedList<NodoMultiple<T>> getNodosList(){
        return NodosList;
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
