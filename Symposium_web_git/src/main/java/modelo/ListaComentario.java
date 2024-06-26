package modelo;

import java.util.ArrayList;

/**
 * Esta clase permite crear ArrayList de objetos de la clase Comentario y trabajar con ellos, es decir,
 * introducir Comentarios nuevos el el ArrayList, mostrarlos por pantalla, borrar un "Comentario" concreto o vaciar la lista generada, etc.
 * @see Comentario
 * @author Alejandro Moreno
 * @version 1.0
 * @deprecated No se ha llegado a implementar, pero está creada en caso de que en un futuro se desee hacerlo.
 */
public class ListaComentario {
	
	private ArrayList <Comentario> comentarios;
	
	/**
	 * Método constructor de la clase ListaComentario.
	 */
	public ListaComentario() {
		comentarios=new ArrayList<Comentario>();
	}
	
	/**
	 * Este método añade objetos Comentario al array comentarios. 
	 * @param obra Comentario que se insertará en el array comentarios. 
	 */
	public void addComentarioLista(Comentario com) {
		comentarios.add(com);
	}
	
	/**
	 * Este método genera una lista con los objetos Comentario que se han añadido al array comentarios 
	 * y los muestra por pantalla.
	 */
	public void listarComentarios() {
		for(Comentario obj:comentarios) {
			System.out.println(obj.toString());
			System.out.println("");
		}
	}
	
	/**
	* Este método permite buscar un Comentario concreto en el array comentarios. Para ello, compara el IdAutorComentario y el ISBN_obra 
	 * proporcionados con los atributos correspondientes de los objetos presentes en el array comentarios.
	 * @see Obra
	 * @see Usuario
	 * @param id long que recoge la Id que se comparará con el IdAutorComentario de los objetos Comentario insertados en el array.
	 * @param isbn long que recoge el número identificativo de una obra, que tamibén ha de estar en el Comentario. 
	 * @return devuelve el comentario si lo encuentra.
	 */
	public Comentario buscarComentarioConcreto(long id, long isbn) {
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
	 * Este método elimina completamente los objetos Comentario que haya dentro del array comentarios.
	 */
	public void borrarListaCompleta() {
			comentarios.clear();
	}
	
	/**
	 * Este método elimina un Comentario concreto del array comentarios buscando la IdAutorComentario y su ISBN_obra. 
	 * Manda un mensaje por pantalla tanto si se ha podido eliminar, como si no.
	 * @param id long que recoge la id que se comparará con la IdAutorComentario de los comentarios insertados en el array.
	 * @param isbn long que recoge el ISBN del Comentario, que se comparará con la ISBN  de los comentarios insertados en el array.
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
