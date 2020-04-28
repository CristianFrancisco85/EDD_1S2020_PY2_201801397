package Clases;

import Estructuras.BTree;

public class CategoriaLibro<T> {
    BTree<T> BooksList;
    String Categoria;
    int CarneCreador;

    public CategoriaLibro(){
        BooksList=new BTree<>(5);
        Categoria = null;
        CarneCreador=0;
    }
    public CategoriaLibro(String arg1){
        BooksList=new BTree<>(5);
        Categoria = arg1;
        CarneCreador=0;
    }

    public BTree<T> getBookList(){
        return BooksList;
    }
    public void setCategoria(String arg1){
        this.Categoria=arg1;
    }
    public String getCategoria(){
        return Categoria;
    }

    public void setCreador(int arg1){
        this.CarneCreador=arg1;
    }
    public int getCreador(){
        return  this.CarneCreador;
    }

    @Override
    public String toString(){
        return Categoria;
    }

}
