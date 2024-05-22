package modelo;

import java.util.ArrayList;
/**
 * Esta clase permite crear ArrayList de objetos de la clase "Usuario" y trabajar con ellos,
 * introducirlos, mostrarlos por pantalla, borrar un Articulo concreto o vaciar la lista generada.
 * @author Alejandro Moreno
 * @version 1.0
 * @deprecated No se ha llegado a implementar, pero está creada en caso de que en un futuro se desee implementar.
 */
public class ListaUsuario {
	
	private ArrayList <Usuario> usuarios;
	
	/**
	 * Método constructor de la clase ListaUsuario.
	 */
	public ListaUsuario() {
		usuarios=new ArrayList <Usuario>();
	}
	/**
	 * Este método añade objetos "Usuario" a un array "usuarios" (y una vez añadido amplia el tamaño de dicho array). 
	 * @param user "Usuario" que recibe los parámetros que necesita dicha clase para crear un objeto y 
	 * 		  los inserta como un objeto de la clase "Usuario" en el array "usuarios". 
	 */
	public void addUsuarioLista(Usuario user) {
		usuarios.add(user);
	}
	
	/**
	 * Este método genera una lista con los objetos "Usuario" que se han añadido al array "usuarios" 
	 * y los muestra por pantalla.
	 */
	public void listarUsuarios() {
		for(Usuario obj:usuarios) {
			if (obj.getNivel()>9 && obj.getNivel()<30) {
				System.out.println("Usuario con id="+obj.getId()+"\n Nombre="+obj.getNombre()+"\n Apellidos="+obj.getApellidos()+"\n Edad="+obj.getEdad()+"\n Nivel= Estudiante");
			} else if (obj.getNivel()>30 && obj.getNivel()<50) {
				System.out.println("Usuario con id="+obj.getId()+"\n Nombre="+obj.getNombre()+"\n Apellidos="+obj.getApellidos()+"\n Edad="+obj.getEdad()+"\n Nivel= Titulado");
			} else if (obj.getNivel()>50) {
				System.out.println("Usuario con id="+obj.getId()+"\n Nombre="+obj.getNombre()+"\n Apellidos="+obj.getApellidos()+"\n Edad="+obj.getEdad()+"\n Nivel= Admin");
			} else {
				System.out.println("Usuario eliminado o Baneado");
			}
		}
	}
	
	/**
	 * Este método permite buscar un "Usuario" concreto en el array, comparando el Id, 
	 * proporcionado con la Id de los objetos "Usuario" introducidos en el array "usuarios".
	 * @param id Int que que sirve para buscar el "Usuario" concreto en el array comparándola con
	 * 		  la Id de los objetos "Usuario" insertados en el array.
	 * @return user Devuelve un objeto "Usuario".
	 */
	public Usuario buscarUsuarioConcreto(int id) {
		Usuario user=new Usuario();
		boolean cell=false;
		for (Usuario obj:usuarios) {
			if (obj.getId()==id) {
				user=obj;
					cell=true;
				}		
			}
		if (cell==false) {
			System.out.println("No se ha podido encontrar ese usuario, revisa la información introducida");
		}
		return user;
	}
	
	/**
	 * Este método elimina completamente los objetos "Usuario" que hay dentro del array "usuarios".
	 */
	public void borrarListaCompleta() {
		usuarios.clear();
	}
	
	/**
	 * Este método elimina una "Respuesta" concreta del array "respuestas" buscando la Id_respuesta, 
	 * manda un mensaje por pantalla tanto si se ha podido eliminar, como si no.
	 * @param id Int que recoge la Id_respuesta de la "Respuesta", que sirve para buscar la "Respuesta" 
	 * 		  concreta en el array comparándolo con el Id_respuesta de los objetos "Respuesta" insertados 
	 * 		  en el array.
	 */
	public void eliminarUsuario(int id) {
		boolean cell=false;
		for (Usuario obj:usuarios) {
			if (obj.getId()==id) {
					System.out.println("El Usuario: "+obj.toString()+" ha sido eliminado");
					usuarios.remove(obj);
					cell=true;
				}		
			}
		if (cell==false) {
			System.out.println("No se ha podido encontrar al Usuario con la ID: "+id+" revisa la información introducida");
		}
	}
	
}
