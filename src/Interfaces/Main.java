package Interfaces;

import Clases.Data;
import Estructuras.Bloque;
import Estructuras.NodoRed;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.*;

public class Main extends Application {


    static Thread thread,thread2;
    static boolean flag1,flag2;
    static DatagramSocket datagramSocket;
    static ServerSocket serverSocket;
    public static int UDP,TCP;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Data.initializeStructures();
        Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
        primaryStage.setTitle("Menu");
        primaryStage.setScene(new Scene(root, 725, 575));
        primaryStage.setResizable(false);
        primaryStage.show();
        UDP=55557;
        TCP=9999;
        flag1=true;
        flag2=true;
        thread = new Thread(EscucharIP);
        thread.start();
        thread2 = new Thread(EscucharDatos);
        thread2.start();
        sendAddIP();
    }

    @Override
    public void stop(){
        flag1=false;
        flag2=false;
        sendRemoveIP();
        datagramSocket.close();
        try{
            serverSocket.close();
        }
        catch (IOException e){
            System.out.println("Stopped listening TCP");
        }

    }

    public static void analizeData(String data){

        String[] newData = data.split(";");

        switch (newData[0]){
            case "ADD":
                try {
                    InetAddress inetAddress = InetAddress.getLocalHost();
                    String myIP = inetAddress.getHostAddress();
                    //Se verfica que no exista
                    for(int i=0;i<Data.getListaNodos().getSize();i++){
                        if(Data.getListaNodos().getValue(i).getIP().equals(newData[1])){
                            return;
                        }
                    }
                    //Se agrega solo si no es la propia IP
                    if(!newData[1].equals(myIP)){
                        Data.getListaNodos().addEnd(new NodoRed(newData[1]));
                        System.out.println("IP " + newData[1] + " agregada.");
                        sendAddIP();
                    }

                }
                catch (IOException e){
                    e.printStackTrace();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case "REMOVE":
                try{
                    for(int i=0;i<Data.getListaNodos().getSize();i++){
                        if(Data.getListaNodos().getValue(i).getIP().equals(newData[1])){
                            Data.getListaNodos().removeByPosition(i);
                            System.out.println("IP " + newData[1] + " eliminada.");
                        }
                    }

                }
                catch (Exception e){
                    e.printStackTrace();
                }
                break;

        }


    }

    static Runnable EscucharIP= new Runnable(){
        public void run () {
            while (flag1) {
                try {
                    datagramSocket = new DatagramSocket(UDP);
                    byte[] dato = new byte[1024];
                    DatagramPacket dgp = new DatagramPacket(dato, dato.length);
                    System.out.println("Listen TCP in port "+UDP+" ...");
                    datagramSocket.receive(dgp);
                    byte[] datos = dgp.getData();
                    String answer = new String(datos).replace("\0", "");
                    System.out.println("Respuesta en puerto "+UDP+"..."+answer);
                    analizeData(answer);
                    datagramSocket.close();

                } catch (SocketException e) {
                    System.out.println("Stopped listening UDP");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    static Runnable EscucharDatos= new Runnable(){
        public void run () {
            while (flag2) {
                try {
                    serverSocket = new ServerSocket(TCP);
                    System.out.println("Listen TCP in port "+TCP+" ...");
                    Socket mySocket= serverSocket.accept();
                    DataInputStream flujo_entrada=new DataInputStream(mySocket.getInputStream());
                    String answer = flujo_entrada.readUTF();
                    flujo_entrada.close();
                    mySocket.close();
                    serverSocket.close();
                    System.out.println("Respuesta en puerto "+TCP+"..."+answer);
                    Bloque auxBloque = new Bloque(answer);
                    Data.getBlockChain().addEnd(auxBloque);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    };

    public static void sendAddIP(){

        try {
            String mydata="ADD;";
            InetAddress inetAddress = InetAddress.getLocalHost();
            mydata = mydata+inetAddress.getHostAddress();
            DatagramSocket enviador = new DatagramSocket();
            byte[] dato = mydata.getBytes();
            DatagramPacket dgp = new DatagramPacket(dato, dato.length, InetAddress.getByName("192.168.1.255"), UDP);
            enviador.send(dgp);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void sendRemoveIP(){

        try {
            String mydata="REMOVE;";
            InetAddress inetAddress = InetAddress.getLocalHost();
            mydata = mydata+inetAddress.getHostAddress();
            DatagramSocket enviador = new DatagramSocket();
            byte[] dato = mydata.getBytes();
            DatagramPacket dgp = new DatagramPacket(dato, dato.length, InetAddress.getByName("192.168.1.255"), UDP);
            enviador.send(dgp);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void setPuertoUDP(int newUDP){
        flag1=false;
        datagramSocket.close();
        UDP=newUDP;
        try {
            thread.join();
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
        flag1=true;
        thread = new Thread(EscucharIP);
        thread.start();
        sendAddIP();
    }

    public static void setPuertoTCP(int newTCP){
        flag2=false;
        try{
            serverSocket.close();
        }
        catch (IOException e){
            System.out.println("Stopped listening TCP");
        }
        try {
            thread2.join();
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
        TCP=newTCP;
        flag2=true;
        thread2 = new Thread(EscucharDatos);
        thread2.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
