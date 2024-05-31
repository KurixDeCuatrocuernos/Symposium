package modelo;

import java.util.ArrayList;

/**
 * Esta clase permite crear ArrayList de objetos de la clase "Articulo" y trabajar con ellos,
 * introducirlos, mostrarlos por pantalla, borrar un Articulo concreto, vaciar la lista generada, etc.
 * @see Articulo
 * @author Alejandro Moreno
 * @version 1.0
 * @deprecated No se ha llegado a implementar, pero está creada en caso de que en un futuro se desee hacerlo.
 */
public class ListaArticulo {
	
	private ArrayList <Articulo> articulos;
	
	/**
	 * Método constructor de la clase ListaArticulo.
	 */
	public ListaArticulo() {
		articulos=new ArrayList <Articulo>();
	}
	/**
	 * Este método añade un objeto <em>Articulo</em> a un array de <em>articulos</em>. 
	 * @param obra Articulo que se insertará en el array articulos. 
	 */
	public void addArticuloLista(Articulo obra) {
		articulos.add(obra);
	}
	/**
	 * Este método genera una lista con los objetos Articulo que se han añadido al array articulos 
	 * y los muestra por pantalla.
	 */
	public void listarArticulos() {
		for(Articulo obj:articulos) {
			System.out.println(obj.toString());
		}
	}
	/**
	 * Este método permite buscar un articulo concreto en el array, comparando el ISBN 
	 * proporcionado con los ISBN de los objetos Articulo introucidos en el array articulos.
	 * @param id long que que sirve para buscar el Articulo concreto en el array comparándolo con
	 * 		  el ISBN de los objetos de la clase Articulo insertados en la lista.
	 * @return devuelve un objeto Articulo (si ningún ISBN coincide con los del array, el objeto estará vacío). 
	 */
	public Articulo buscarArticulo(long id) {
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
	 * Este método elimina un Articulo concreto del array articulos buscando su ISBN, 
	 * manda un mensaje por pantalla tanto si se ha podido eliminar, como si no.
	 * @param id long que sirve para buscar el "Articulo" concreto en el array comparándolo con
	 * 		  el ISBN de los objetos "Articulo" insertados en la lista.
	 */
	public void eliminarArticulo(long id) {
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
