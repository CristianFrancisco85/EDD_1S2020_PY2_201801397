package Clases;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Usuario {

    private int Carnet;
    private String Nombre;
    private String Apellido;
    private String Carrera;
    private String Password;

    public Usuario(){

    }

    public Usuario(int carnet,String nombre,String apellido,String carrera,String password)throws NoSuchAlgorithmException{
        this.Carnet=carnet;
        this.Nombre=nombre;
        this.Apellido=apellido;
        this.Carrera=carrera;

        MessageDigest mdHash = MessageDigest.getInstance("MD5");
        byte[] hashBytes = mdHash.digest(password.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }
        this.Password=sb.toString();
    }

    public void setCarnet(int arg1){
        this.Carnet=arg1;
    }
    public int getCarnet(){
        return Carnet;
    }

    public void setNombre(String arg1){
        this.Nombre=arg1;
    }
    public String getNombre(){
        return Nombre;
    }

    public void setApellido(String arg1){
        this.Apellido=arg1;
    }
    public String getApellido(){
        return Apellido;
    }

    public void setCarrera(String arg1){
        this.Carrera=arg1;
    }
    public String getCarrera(){
        return Carrera;
    }

    public void setPassword(String arg1)throws NoSuchAlgorithmException{
        MessageDigest mdHash = MessageDigest.getInstance("MD5");
        byte[] hashBytes = mdHash.digest(arg1.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }
        this.Password=sb.toString();
    }
    public String getPassword(){
        return Password;
    }


}
