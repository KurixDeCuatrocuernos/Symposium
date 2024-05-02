package modelo;

import java.util.ArrayList;

/**
 * Esta clase permite crear ArrayList de objetos de la clase "Articulo" y trabajar con ellos,
 * introducirlos, mostrarlos por pantalla, borrar un Articulo concreto o vaciar la lista generada.
 * @author Alejandro Moreno
 * @version 1.0
 */
public class ListaArticulo {
	
	private  ArrayList <Articulo> articulos;
	
	/**
	 * Método constructor de la clase ListaArticulo.
	 */
	public ListaArticulo() {
		articulos=new ArrayList <Articulo>();
	}
	/**
	 * Este método añade objetos "Articulo" a un array "articulos" (y una vez añadido amplia el tamaño de dicho array). 
	 * @param obra "Articulo" que recibe los parámetros que necesita dicha clase para crear un objeto y 
	 * 		  los inserta como un objeto de la clase "Articulo" en el array "articulos". 
	 */
	public void addArticuloLista(Articulo obra) {
		articulos.add(obra);
	}
	/**
	 * Este método genera una lista con los objetos "Articulo" que se han añadido al array "articulos" 
	 * y los muestra por pantalla.
	 */
	public void listarArticulos() {
		for(Articulo obj:articulos) {
			System.out.println(obj.toString());
		}
	}
	/**
	 * Este método permite buscar un libro concreto en el array, comparando el ISBN 
	 * proporcionado con los ISBN de los objetos "Libro" introucidos en el array "libros".
	 * @param id Int que que sirve para buscar el Libro concreto en el array comparándolo con
	 * 		  el ISBN de los objetos "Libro" insertados en la lista.
	 * @return art Devuelve un objeto "Articulo". 
	 */
	public Articulo buscarArticulo(int id) {
		Articulo art=new Articulo();
		boolean cell=false;
		for (Articulo obj:articulos) {
			if (obj.getISBN()==id) {
					art=obj;
					cell=true;
				}		
			}
		if (cell==false) {
			System.out.println("No se ha podido encontrar el Articulo con el ISBN: "+id+" revisa la información introducida");
		}
		return art;
	}
	
	/**
	 * Este método elimina completamente los objetos "Articulo" que hay dentro del array "articulos"
	 */
	public void borrarListaCompleta() {
			articulos.clear();
	}
	
	/**
	 * Este método elimina un "Articulo" concreto del array "articulos" buscando su ISBN, 
	 * manda un mensaje por pantalla tanto si se ha podido eliminar, como si no.
	 * @param id Int que sirve para buscar el "Articulo" concreto en el array comparándolo con
	 * 		  el ISBN de los objetos "Articulo" insertados en la lista.
	 */
	public void eliminarArticulo(int id) {
		boolean cell=false;
		for (Articulo obj:articulos) {
			if (obj.getISBN()==id) {
					System.out.println("El articulo: "+obj.toString()+" ha sido eliminado");
					articulos.remove(obj);
					cell=true;
				}		
			}
		if (cell==false) {
			System.out.println("No se ha podido encontrar el Articulo con el ISBN: "+id+" revisa la información introducida");
		}
	}
}
