package modelo;

import java.util.ArrayList;

/**
 * Esta clase permite crear ArrayList de objetos de la clase "Respuesta" y trabajar con ellos, es decir,
 * introducir nuevas respuestas, mostrarlas por pantalla, borrar una "Respuesta" concreta o vaciar la lista generada.
 * @author Alejandro Moreno
 * @version 1.0
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
	 * Este método añade objetos "Respuesta" a un array "respuestas" (y una vez añadido amplia el tamaño de dicho array). 
	 * @param obra "Respuesta" que recibe los parámetros que necesita dicha clase para crear un objeto y 
	 * 		  los inserta como un objeto de la clase "Respuesta" en el array "respuestas". 
	 */
	public void addRespuestaLista(Respuesta ans) {
		respuestas.add(ans);
	}
	
	/**
	 * Este método genera una lista con los objetos "Respuesta" que se han añadido al array "respuestas" 
	 * y los muestra por pantalla.
	 */
	public void listarRespuestas() {
		for(Respuesta obj:respuestas) {
			System.out.println(obj.toString());
		}
	}
	
	/**
	 * Este método permite buscar un "Respuesta" concreta en el array, comparando el Id_respuesta, 
	 * proporcionada con la ID_respuesta de los objetos "Respuesta" introducidos en el array "respuestas".
	 * @param id Int que que sirve para buscar la "Respuesta" concreta en el array comparándola con
	 * 		  el Id_respuesta de los objetos "Respuesta" insertados en el array.
	 * @return res Devuelve un objeto "Respuesta".
	 */
	public Respuesta buscarRespuestaConcreta(int id) {
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
	 * Este método elimina completamente los objetos "Respuesta" que hay dentro del array "respuestas".
	 */
	public void borrarListaCompleta() {
		respuestas.clear();
	}
	
	/**
	 * Este método elimina una "Respuesta" concreta del array "respuestas" buscando la Id_respuesta, 
	 * manda un mensaje por pantalla tanto si se ha podido eliminar, como si no.
	 * @param id Int que recoge la Id_respuesta de la "Respuesta", que sirve para buscar la "Respuesta" 
	 * 		  concreta en el array comparándolo con el Id_respuesta de los objetos "Respuesta" insertados 
	 * 		  en el array.
	 */
	public void eliminarRespuesta(int id) {
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
