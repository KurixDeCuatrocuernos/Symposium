package modelo;

import java.util.ArrayList;

/**
 * Esta clase permite generar ArrayList de objetos Libro y trabajar con ellos como un ArrayList, 
 * es decir, insertarlos, mostrarlos por pantalla, borrar un Libro concreto, vaciar la lista generada, etc.
 * @see Libro
 * @author Alejandro Moreno
 * @version 1.0
 * @deprecated No se ha llegado a implementar, pero está creada en caso de que en un futuro se desee hacerlo.
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
	 * Este método añade un Libro nuevo al array,  
	 * @param obra Libro que se insertará en el array libros. 
	 */
	public void addLibroLista(Libro obra) {
		libros.add(obra);
	}
	/**
	 * Este método genera una lista con los objetos Libro que se han añadido al array y los muestra por pantalla.
	 */
	public void listarLibros() {
		for(Libro obj:libros) {
			System.out.println(obj.toString());
		}
	}
	/**
	 * Este método permite buscar un libro concreto en el array, comparando el ISBN 
	 * proporcionado con los ISBN de los libros introucidos en el array.
	 * @param id long que recoge la ISBN mediante la cual se buscará el Libro concreto en el array.
	 * @return devuelve un objeto Libro si lo encuentra en el array (si no lo encuentra devolverá un objeto con valores vacíos). 
	 */
	public Libro buscarLibro(long id) {
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
	 * Este método elimina completamente los objetos que hay dentro del array.
	 */
	public void borrarListaCompleta() {
		libros.clear();
	}
	/**
	 * Este método elimina un Libro concreto del array buscando su ISBN, manda un mensaje por pantalla tanto si se ha podido eliminar, como si no.
	 * @param id long que recoge la ISBN que se buscará en el array.
	 */
	public void eliminarLibro(long id) {
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
