package Clases;

import Estructuras.BTree;

public class CategoriaLibro<T> {
    BTree<T> BooksList;
    String Categoria;

    public CategoriaLibro(){
        BooksList=new BTree<>(5);
        Categoria = null;
    }
    public CategoriaLibro(String arg1){
        BooksList=new BTree<>(5);
        Categoria = arg1;
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

    @Override
    public String toString(){
        return Categoria;
    }

}
