package modelo;

import java.util.ArrayList;

/**
 * Esta clase permite generar ArrayList de objetos "Libro" y trabajar con ellos como un ArrayList, 
 * es decir, insertarlos, mostrarlos por pantalla, borrar un "Libro" concreto o vaciar la lista generada.
 * @author Alejandro Moreno
 * @version 1.0
 * @deprecated No se ha llegado a implementar, pero está creada en caso de que en un futuro se desee implementar.
 */
public class ListaLibro {

	private ArrayList <Libro> libros;
	
	/**
	 * Método constructor del ArrayList.
	 */
	public ListaLibro() {
		libros=new ArrayList <Libro>();
		
	}
	/**
	 * Este método añade objetos Libro a un array (y una vez añadido amplia el tamaño de dicho array). 
	 * @param obra "Libro" que recibe los parámetros que necesita dicha clase para crear un objeto y 
	 * 		  los inserta como un objeto de la clase "Libro" en el array "libros". 
	 */
	public void addLibroLista(Libro obra) {
		libros.add(obra);
	}
	/**
	 * Este método genera una lista con los objetos "Libro" que se han añadido al array "libros" 
	 * y los muestra por pantalla.
	 */
	public void listarLibros() {
		for(Libro obj:libros) {
			System.out.println(obj.toString());
		}
	}
	/**
	 * Este método permite buscar un libro concreto en el array, comparando el ISBN 
	 * proporcionado con los ISBN de los objetos "Libro" introucidos en el array "libros".
	 * @param id Int que que sirve para buscar el Libro concreto en el array comparándolo con
	 * 		  el ISBN de los objetos "Libro" insertados en la lista.
	 * @return lib Devuelve el objeto "Libro" si lo encuentra en el array. 
	 */
	public Libro buscarLibro(int id) {
		Libro lib=new Libro();
		boolean cell=false;
		for (Libro obj:libros) {
			if (obj.getISBN()==id) {
					lib=obj;
					cell=true;
				}		
			}
		if (cell==false) {
			System.out.println("No se ha podido encontrar el Libro con el ISBN: "+id+" revisa la información introducida");
		}
		return lib;
	}
	/**
	 * Este método elimina completamente los objetos "Libro" que hay dentro del array "libros"
	 */
	public void borrarListaCompleta() {
		libros.clear();
	}
	/**
	 * Este método elimina un "Libro" concreto del array "libros" buscando su ISBN, 
	 * manda un mensaje por pantalla tanto si se ha podido eliminar, como si no.
	 * @param id Int que sirve para buscar el Libro concreto en el array comparándolo con
	 * 		  el ISBN de los objetos "Libro" insertados en la lista.
	 */
	public void eliminarLibro(int id) {
		boolean cell=false;
		for (Libro obj:libros) {
			if (obj.getISBN()==id) {
					System.out.println("El libro: "+obj.toString()+" ha sido eliminado");
					libros.remove(obj);
					cell=true;
				}		
			}
		if (cell==false) {
			System.out.println("No se ha podido encontrar el Libro con el ISBN: "+id+" revisa la información introducida");
		}
	}
}
