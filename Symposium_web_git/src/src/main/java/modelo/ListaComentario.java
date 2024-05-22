package modelo;

import java.util.ArrayList;

/**
 * Esta clase permite crear ArrayList de objetos de la clase "Comentario" y trabajar con ellos, es decir,
 * introducir Comentarios nuevos el el ArrayList, mostrarlos por pantalla, borrar un "Comentario" concreto o vaciar la lista generada.
 * @author Alejandro Moreno
 * @version 1.0
 * @deprecated No se ha llegado a implementar, pero está creada en caso de que en un futuro se desee implementar.
 */
public class ListaComentario {
	
	private ArrayList <Comentario> comentarios;
	
	/**
	 * Método constructor de objetos ListaComentario.
	 */
	public ListaComentario() {
		comentarios=new ArrayList<Comentario>();
	}
	/**
	 * Este método añade objetos "Comentario" a un array "comentarios" (y una vez añadido amplia el tamaño de dicho array). 
	 * @param obra "Comentario" que recibe los parámetros que necesita dicha clase para crear un objeto y 
	 * 		  los inserta como un objeto de la clase "Comentario" en el array "comentarios". 
	 */
	public void addComentarioLista(Comentario com) {
		comentarios.add(com);
	}
	
	/**
	 * Este método genera una lista con los objetos "Comentario" que se han añadido al array "comentarios" 
	 * y los muestra por pantalla.
	 */
	public void listarComentarios() {
		for(Comentario obj:comentarios) {
			System.out.println(obj.toString());
			System.out.println("");
		}
	}
	
	/**
	* Este método permite buscar un "Comentario" concreto en el array, comparando el IdAutorComentario, el ISBN_obra y la Fecha_comentario 
	 * proporcionados con los atributos correspondientes de los objetos "Comentario" introducidos en el array "comentarios".
	 * @param id Int que que sirve para buscar el "Comentario" concreto en el array comparándolo con
	 * 		  el IdAutorComentario de los objetos "Comentario" insertados en el array.
	 * @param isbn recoge el número identificativo de una obra, que tamién ha de estar en el Comentario. 
	 * @return com Devuelve el comentario si lo encuentra.
	 */
	public Comentario buscarComentarioConcreto(int id, int isbn) {
		Comentario com=new Comentario();
		boolean cell=false;
		for (Comentario obj:comentarios) {
			if (obj.getISBN_obra()==isbn && obj.getIdAutorComentario()==id) {
					com=obj;
					cell=true;
				}		
			}
		if (cell==false) {
			System.out.println("No se ha podido encontrar el Comentario de ese usuario, revisa la información introducida");
		}
		return com;
	}
	
	/**
	 * Este método elimina completamente los objetos "Comentario" que hay dentro del array "comentarios"
	 */
	public void borrarListaCompleta() {
			comentarios.clear();
	}
	
	/**
	 * Este método elimina un "Comentario" concreto del array "comentarios" buscando la IdAutorComentario y su ISBN_obra, 
	 * manda un mensaje por pantalla tanto si se ha podido eliminar, como si no.
	 * @param id Int que recoge la id del autor del "Comentario", que sirve para buscar el "Comentario" concreto en el array comparándolo con
	 * 		  el IdAutorComentario de los objetos "Comentario" insertados en el array.
	 * @param isbn Int que recoge el ISBN del "Comentario", que sirve para buscar el "Comentario" concreto en el array comparándolo con
	 * 		  el ISBN_obra de los objetos "Comentario" insertados en el array.
	 */
	public void eliminarComentario(int id, int isbn) {
		boolean cell=false;
		for (Comentario obj:comentarios) {
			if (obj.getISBN_obra()==isbn && obj.getIdAutorComentario()==id) {
					System.out.println("El comentario: "+obj.toString()+" ha sido eliminado");
					comentarios.remove(obj);
					cell=true;
				}		
			}
		if (cell==false) {
			System.out.println("No se ha podido encontrar el comentario con el ISBN: "+id+ "e ID: "+isbn+" revisa la información introducida");
		}
	}
}
