package modelo;

import java.util.ArrayList;
/**
 * Esta clase permite crear ArrayList de objetos de la clase Usuario y trabajar con ellos, es decir,
 * introducirlos, mostrarlos por pantalla, borrar un Usario concreto, vaciar la lista generada, etc.
 * @see Usuario
 * @author Alejandro Moreno
 * @version 1.0
 * @deprecated No se ha llegado a implementar, pero está creada en caso de que en un futuro se desee hacerlo.
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
	 * Este método inserta un objeto Usuario que recibe, en el array. 
	 * @param user Usuario que se insertará en el array. 
	 */
	public void addUsuarioLista(Usuario user) {
		usuarios.add(user);
	}
	
	/**
	 * Este método genera una lista con los objetos Usuario que se han añadido al array, 
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
	 * Este método permite buscar un Usuario concreto en el array, mediante una Id, comparándola con las presentes en el array.
	 * @param id long que recoge el número que sirve para buscar el Usuario concreto en el array. 
	 * @return devuelve un objeto Usuario (si lo encuentra, tendrá valores en sus parámetros si no, estarán vacíos).
	 */
	public Usuario buscarUsuarioConcreto(long id) {
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
	 * Este método elimina completamente los objetos que hay dentro del array.
	 */
	public void borrarListaCompleta() {
		usuarios.clear();
	}
	
	/**
	 * Este método elimina una Respuesta concreta del array buscando la Id_respuesta mediante la id que recibe, 
	 * manda un mensaje por pantalla tanto si se ha podido eliminar, como si no.
	 * @param id long que recoge la Id_respuesta que sirve para buscar la Respuesta concreta en el array.
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
