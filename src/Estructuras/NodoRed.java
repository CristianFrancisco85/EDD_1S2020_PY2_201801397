package Estructuras;

import Interfaces.Main;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class NodoRed {

    String IP;

    public NodoRed(String arg1){
        IP=arg1;
    }

    public void sendData(String arg1){
        try {
            Socket mySocket = new Socket(this.IP, Main.TCP);
            DataOutputStream flujo_salida = new DataOutputStream(mySocket.getOutputStream());
            flujo_salida.writeUTF(arg1);
            flujo_salida.close();
            mySocket.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public String getIP(){
        return IP;
    }
}
