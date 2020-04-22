package Clases;

import Estructuras.LinkedList;

public class CategoriaLibro<T> {
    LinkedList<T> BooksList;
    String Categoria;

    public CategoriaLibro(){
        BooksList=new LinkedList<>();
        Categoria = null;
    }
    public CategoriaLibro(String arg1){
        BooksList=new LinkedList<>();
        Categoria = arg1;
    }

    public LinkedList<T> getBookList(){
        return BooksList;
    }
    public void setCategoria(String arg1){
        this.Categoria=arg1;
    }
    public String getCategoria(){
        return Categoria;
    }

    @Override
    public String toString(){
        return Categoria;
    }

}
