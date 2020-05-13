package Dialogos;

import Clases.Data;
import Estructuras.NodoRed;
import Interfaces.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextInputDialog;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class Config implements Initializable {


    @FXML
    public void setUDP(){
        TextInputDialog dialog = new TextInputDialog(Integer.toString(Main.UDP));
        dialog.setTitle("Puero UDP");
        dialog.setHeaderText(null);
        dialog.setContentText("Ingresa el puerto UDP");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            Main.setPuertoUDP(Integer.parseInt(result.get()));
        }

    }

    @FXML
    public void setTCP(){

        TextInputDialog dialog = new TextInputDialog(Integer.toString(Main.TCP));
        dialog.setTitle("Puero TCP");
        dialog.setHeaderText(null);
        dialog.setContentText("Ingresa el puerto TCP");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            Main.setPuertoTCP(Integer.parseInt(result.get()));
        }

    }

    @FXML
    public void setIP(){

        TextInputDialog dialog = new TextInputDialog(Main.BroadcastIP);
        dialog.setTitle("Broadcast IP");
        dialog.setHeaderText(null);
        dialog.setContentText("Ingresa la IP de Broadcast");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            Main.setBroadcastIP(result.get());
        }

    }

    @FXML
    public void addIP(){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Agregar IP");
        dialog.setHeaderText(null);
        dialog.setContentText("Ingresa la IP");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            //Se verfica que no exista
            for(int i = 0; i< Data.getListaNodos().getSize(); i++){
                try {
                    if (Data.getListaNodos().getValue(i).getIP().equals(result.get())) {
                        return;
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
            //Se agrega solo si no es la propia IP
            Data.getListaNodos().addEnd(new NodoRed(result.get()));
            System.out.println("IP " + result.get() + " agregada.");
            Main.sendAddIP(result.get());
        }

    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
