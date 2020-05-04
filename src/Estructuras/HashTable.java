package Estructuras;

import Clases.Usuario;
import javafx.stage.FileChooser;

import java.io.*;

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
        if(Table[hash(HashParam)]==null){
            return null;
        }
        if(Table[hash(HashParam)].getSize()!=0){
            return Table[hash(HashParam)];
        }
        else {
            return null;
        }
    }

    /**
     * Grafica la tabla hash
     */
    public void graphTable(){

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar Hash-Table");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Archivo DOT","*.dot"));
        File selectedFile = fileChooser.showSaveDialog(null);
        if(selectedFile!=null){
            try{
                FileWriter w = new FileWriter(selectedFile);
                BufferedWriter bw = new BufferedWriter(w);
                PrintWriter wr = new PrintWriter(bw);
                wr.write("digraph{\n");
                wr.write("node [shape=box] rankdir=TB;");

                for(int i=0;i<Table.length;i++){

                    if(Table[i]!=null){
                        LinkedList<T> auxList = Table[i];
                        Usuario auxUsuario;
                        Usuario auxUsuario2;
                        wr.write("\""+i+"\";\n");

                        for(int j=0;j<auxList.getSize();j++){
                            auxUsuario=(Usuario) auxList.getValue(j);
                            wr.write("\""+auxUsuario.getCarnet()+"\""+"[label ="+"\""+auxUsuario.toString()+"\"];\n");
                        }
                        for(int j=0;j<auxList.getSize()-1;j++){
                            auxUsuario=(Usuario) auxList.getValue(j);
                            auxUsuario2=(Usuario) auxList.getValue(j+1);
                            wr.write("\""+auxUsuario.getCarnet()+"->"+"\""+auxUsuario2.getCarnet()+";\n");
                        }

                        auxUsuario=(Usuario) auxList.getValue(0);
                        wr.write(i+"->"+"\""+auxUsuario.getCarnet()+"\";\n");

                    }

                }

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
