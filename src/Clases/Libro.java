package Clases;

public class Libro {

    int ISBN;
    int Year;
    String Idioma;
    String Titulo;
    String Editorial;
    String Autor;
    int Edicion;
    String Categoria;

    public Libro(){

    }

    public void setISBN(int arg1){
        this.ISBN=arg1;
    }
    public int getISBN(int arg1){
        return this.ISBN;
    }


    public void setYear(int arg1){
        this.Year=arg1;
    }
    public int getYear(){
        return this.Year;
    }

    public void setIdioma(String arg1){
        this.Idioma=arg1;
    }
    public String getIdioma(){
        return this.Idioma;
    }

    public void setTitulo(String arg1){
        this.Titulo=arg1;
    }
    public String getTitulo(){
        return this.Titulo;
    }

    public void setEditorial(String arg1){
        this.Editorial=arg1;
    }
    public String getEditorial(){
        return this.Editorial;
    }

    public void setAutor(String arg1){
        this.Autor=arg1;
    }
    public String getAutor(){
        return this.Autor;
    }

    public void setEdicion(int arg1){
        this.Edicion=arg1;
    }
    public int getEdicion(){
        return this.Edicion;
    }

    public void setCategoria(String arg1){
        this.Categoria=arg1;
    }
    public String getCategoria(){
        return this.Categoria;
    }

    @Override
    public String toString(){
        return Integer.toString(ISBN);
    }

}
