package modelo;

import java.sql.SQLException;

import DAO.DaoEstudiante;
import DAO.DaoUsuario;

/**
 * Esta Clase Recoge los datos para generar un objeto Estudiante a partir de la clase 
 * Usuario (extiende dicha clase)
 * @author Alejandro Moreno
 * @version 1.0
 */
public class Estudiante extends Usuario{
	
	private String Estudios="";
	private String Escuela="";
	/**
	 * Método constructor vacío
	 */
	public Estudiante() {
		super();
	}
	/**
	 * Método constructor sólo con los elementos de la clase Usuario (ya que los 
	 * elementos de la clase Estudiante son opcionales).
	 * @param id int que recoge un número entero único.
	 * @param nivel int que determina el nivel de privilegios del Usuario.
	 * @param nombre String que recoge el nombre del Usuario.
	 * @param apellidos String que recoge los apellidos del Usuario.
	 * @param edad int que recoge la edad del Usuario.
	 * @param email String que recoge el correo del Usuario en la aplicacion.
	 * @param password String que recoge la serialización de la contraseña del Usuario, aunque no debería estar así.
	 */
	public Estudiante(long id, int nivel, String nombre, String apellidos, int edad, String email) {
		super(id, nivel=10, nombre, apellidos, edad, email);
	}
	/**
	 * Método constructor con los atributos de la clase Usuario y la clase Estudiante combinados (super()+Estudios y Escuela)
	 * @param id int que recoge un número entero único.
	 * @param nivel int que determina el nivel de privilegios del Usuario.
	 * @param nombre String que recoge el nombre del Usuario.
	 * @param apellidos String que recoge los apellidos del Usuario.
	 * @param edad int que recoge la edad del Usuario.
	 * @param email String que recoge el correo del Usuario en la aplicacion.
	 * @param password String que recoge la serialización de la contraseña del Usuario, aunque no debería estar así.
	 * @param estudios String que recoge el tipo de estudios del Estudiante.
	 * @param escuela String que recoge el lugar donde estudia o ha estudiado el Estudiante.
	 */
	public Estudiante(long id, int nivel, String nombre, String apellidos, int edad, String email, String estudios, String escuela) {
		super(id, nivel=10, nombre, apellidos, edad, email);
		this.Estudios=estudios;
		this.Escuela=escuela;
	}

	public String getEstudios() {
		return Estudios;
	}

	public void setEstudios(String estudios) {
		Estudios = estudios;
	}

	public String getEscuela() {
		return Escuela;
	}

	public void setEscuela(String escuela) {
		Escuela = escuela;
	}
	
	public void registrarEstudiante(Estudiante e, String Con) throws ClassNotFoundException, SQLException {
		
		DaoEstudiante aux = new DaoEstudiante();
		aux.insertarEstudiante(e, Con);
		
	}
	
	public void modificarEstudiante(Estudiante e, long id) throws ClassNotFoundException, SQLException {
		
		DaoEstudiante aux = new DaoEstudiante();
		aux.modificarEstudiante(e, id);
		
	}
	/**
	 * Método que permite al Estudiante cerrar sesión, es decir, no aparecer con su nombre de usuario ni poder hacer lo correspondiente.
	 * @return close boleano que recoge si el cierre de sesión se ha efectuado o no, en caso de que se haya efectuado el cierre devolverá true, 
	 * 		   en caso contrario devolverá false.
	 */
	public boolean cerrarSesion() {
		boolean close=false;
		
		return close;
	}
	/**
	 * Método que permite al Estudiante eliminar su cuenta llamando al método correspondiente del DAO.
	 * @return baja boleano que recoge si la eliminación de la cuenta (los datos en la base de datos) se ha efectuado corresctamente, 
	 * 		   si es así devolverá true, en caso contrario, devolverá false.
	 */
	public boolean solicitarBaja() {
		boolean baja=false;

		return baja;
	}
	/**
	 * Método que permite al estudiante generar una solicitud de ascenso al status Titulado (llamando al método constructor correspondiente de dicha 
	 * clase), introduciendo la información necesaria para dicho ascenso (los atributos de la clase Titulado) y a la espera de la confirmación 
	 * de dicho ascenso por un administrador.  
	 * @return ascenso Boleano que confirma si se ha introducido toda la información necesaria para generar la solicitud, si se ha podido generar 
	 * 		   dicha solicitud devolverá true, en caso contrario devolverá false.
	 */
	public boolean solicitarAscenso() {
		boolean ascenso=false;
		
		return ascenso;
	}
	/**
	 * Método que genera un comentario en la obra correspondiente llamando al método constructor de la clase Comentario.
	 * @return coment Boleano que recoge si la creación del comentario se ha realizado con éxito, si así ha sido, devolverá true, 
	 * 		   en caso contrario devolverá false.
	 */
	public boolean comentar() {
		boolean coment=false;
		
		return coment;
	}
	/**
	 * Método que genera una respuesta, a un comentario o respuesta preexistente, llamando al método constructor de la clase Respuesta.
	 * @return answer Boleano que recoge si la creacion de la respuesta se ha realizado con éxito, si así ha sido, devolverá true, 
	 * 		   en caso contrario devolverá false.
	 */
	public boolean responder() {
		boolean answer=false;
		
		return answer;
	}
	/**
	 * Método que permite modificar un comentario ya existente y que ha sido generado por el mismo Estudiante que lo modifica. 
	 * @return modif oleano que recoge si la modificacion del comentario se ha realizado con éxito, si así ha sido devolverá true, 
	 * 		   en caso de que no se haya podido modificar, sea por la razón que fuere, devolverá false.
	 */
	public boolean modificarComentario() {
		boolean modif=false;
		
		return modif;
	}
	
	/**
	 * Método toString() que muestra por pantalla todos los valores de los atributos de la clase Estudiante (en este caso), por si fuera necesario.
	 */
	@Override
	public String toString() {
		return "Estudiante [Id=" + Id + "\n Nivel=" + Nivel + "\n Nombre=" + Nombre 
				+ "\n Apellidos=" + Apellidos + "\n Edad=" + Edad + "\n Email=" + Email
				+ "\n Estudios=" + Estudios + "\n Escuela=" + Escuela + "]";
		
	}
	
	
	
}
