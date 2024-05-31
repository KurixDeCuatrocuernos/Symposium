package modelo;

import java.util.ArrayList;

/**
 * Esta clase permite crear ArrayList de objetos de la clase Respuesta y trabajar con ellos, es decir,
 * introducir nuevas respuestas, mostrarlas por pantalla, borrar una Respuesta concreta, vaciar la lista generada, etc.
 * @see Respuesta
 * @author Alejandro Moreno
 * @version 1.0
 * @deprecated No se ha llegado a implementar, pero está creada en caso de que en un futuro se desee hacerlo.
 */
public class ListaRespuesta {

	private ArrayList <Respuesta> respuestas;
	/**
	 * Método constructor de la clase ListaRespuesta.
	 */
	public ListaRespuesta() {
		respuestas=new ArrayList <Respuesta>();
	}
	
	/**
	 * Este método añade un objeto Respuesta al Array. 
	 * @param obra Respuesta que se insertará en el array.
	 */
	public void addRespuestaLista(Respuesta ans) {
		respuestas.add(ans);
	}
	
	/**
	 * Este método genera una lista con los objetos Respuesta que se han insertado en el array y los muestra por pantalla.
	 */
	public void listarRespuestas() {
		for(Respuesta obj:respuestas) {
			System.out.println(obj.toString());
		}
	}
	
	/**
	 * Este método permite buscar un Respuesta concreta en el array, comparando el Id_respuesta, 
	 * proporcionada con la ID_respuesta de los objetos Respuesta introducidos en el array.
	 * @param id long que recoge la ID que servirá para buscar la Respuesta concreta en el array.
	 * @return Devuelve un objeto "Respuesta".
	 */
	public Respuesta buscarRespuestaConcreta(long id) {
		Respuesta res= new Respuesta();
		boolean cell=false;
		for (Respuesta obj:respuestas) {
			if (obj.getId_respuesta()==id) {
					res=obj;
					cell=true;
				}		
			}
		if (cell==false) {
			System.out.println("No se ha podido encontrar esa respuesta, revisa la información introducida");
		}
		return res;
	}
	
	/**
	 * Este método elimina completamente los objetos Respuesta que haya dentro del array.
	 */
	public void borrarListaCompleta() {
		respuestas.clear();
	}
	
	/**
	 * Este método elimina una Respuesta concreta del array comparando la id que recibe con la Id_respuesta de los objetos Respuesta del array, 
	 * manda un mensaje por pantalla tanto si se ha podido eliminar, como si no.
	 * @param id long que recoge la Id_respuesta, que sirve para buscar la Respuesta concreta en el array. 
	 */
	public void eliminarRespuesta(long id) {
		boolean cell=false;
		for (Respuesta obj:respuestas) {
			if (obj.getId_respuesta()==id) {
					System.out.println("La respuesta: "+obj.toString()+" ha sido eliminada");
					respuestas.remove(obj);
					cell=true;
				}		
			}
		if (cell==false) {
			System.out.println("No se ha podido encontrar la respuesta con el ID: "+id+" revisa la información introducida");
		}
	}
}
