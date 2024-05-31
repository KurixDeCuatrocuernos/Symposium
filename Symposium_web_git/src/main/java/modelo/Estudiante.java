package modelo;

import java.sql.SQLException;

import DAO.DaoEstudiante;

/**
 * Esta Clase Recoge los datos para generar un objeto Estudiante mediante los parámetros: Estudios y Escuela, a partir de la clase 
 * Usuario (extiende dicha clase).
 * @author Alejandro Moreno
 * @version 1.0
 * @see Usuario
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
	 * @param id long que recoge un número entero único que se genera en el método generarId() de la clase DaoEstudiante. 
	 * @see DaoEstudiante
	 * @param nivel int que determina el nivel de privilegios del Usuario (el 10 en este caso).
	 * @param nombre String que recoge el nombre del Usuario.
	 * @param apellidos String que recoge los apellidos del Usuario.
	 * @param edad int que recoge la edad del Usuario.
	 * @param email String que recoge el correo del Usuario en la aplicacion.
	 * @param password String que recoge la encriptación de la contraseña del Usuario, mediante el método encriptar() de la clase Cifrado.
	 * @see Cifrado
	 */
	public Estudiante(long id, int nivel, String nombre, String apellidos, int edad, String email) {
		super(id, nivel=10, nombre, apellidos, edad, email);
	}
	
	/**
	 * Método constructor con los atributos de la clase Usuario y la clase Estudiante combinados (super()+Estudios y Escuela)
	 * @param id long que recoge un número entero único que se genera en el método generarId() de la clase DaoEstudiante. 
	 * @see DaoEstudiante
	 * @param nivel int que determina el nivel de privilegios del Usuario (10 en este caso).
	 * @param nombre String que recoge el nombre del Usuario.
	 * @param apellidos String que recoge los apellidos del Usuario.
	 * @param edad int que recoge la edad del Usuario.
	 * @param email String que recoge el correo del Usuario en la aplicacion.
	 * @param password String que recoge la encriptación de la contraseña del Usuario, mediante el método encriptar() de la clase Cifrado.
	 * @see Cifrado
	 * @param estudios String que recoge el tipo de estudios del Estudiante.
	 * @param escuela String que recoge el lugar donde estudia o ha estudiado el Estudiante.
	 */
	public Estudiante(long id, int nivel, String nombre, String apellidos, int edad, String email, String estudios, String escuela) {
		super(id, nivel=10, nombre, apellidos, edad, email);
		this.Estudios=estudios;
		this.Escuela=escuela;
	}
	
	/**
	 * Método Get del parámetro Estudios. Como en todo método Get, devuelve el valor del parámetro en cuestión de la clase Estudiante.
	 * @return devuelve el valor del parámetro Estudios de esta clase.
	 */
	public String getEstudios() {
		return Estudios;
	}
	
	/**
	 * Método Set del parámetro Estudios. Como todo método Set, toma un valor y lo introduce en el parámetro en cuestión de la clase Estudiante.
	 * @param estudios String que recoge el tipo de estudios de un estudiante.
	 */
	public void setEstudios(String estudios) {
		Estudios = estudios;
	}
	
	/**
	 * @see getEstudios()
	 */
	public String getEscuela() {
		return Escuela;
	}
	
	/**
	 * @see setEstudios()
	 */
	public void setEscuela(String escuela) {
		Escuela = escuela;
	}
	
	/**
	 * Método que sirve para insertar un objeto Estudiante en la DB, para ello llama al método insertarEstudiante() de la clase DaoEstudiante, a partir de un objeto Estudiante .
	 * @see DaoEstudiante
	 * @param e Estudiante que se enviará al Dao para insertarlo en la DB.
	 * @param Con String que recoge la encriptación (mediante el método encriptar() de la clase Cifrado) del estudiante que se procederá a insertar.
	 * @see Cifrado
	 * @throws ClassNotFoundException si no encuentra la clase DaoComentario lanzará un error.
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error. 
	 */
	public void registrarEstudiante(Estudiante e, String Con) throws ClassNotFoundException, SQLException {
		
		DaoEstudiante aux = new DaoEstudiante();
		aux.insertarEstudiante(e, Con);
		
	}
	
	/**
	 * Método que sirve para modificar un objeto estudiante preexistente en la DB. 
	 * Para ello llama al método modificarEstudiante() de la clase DaoEstudiante y le pasa los parámetros id y e.
	 * @see DaoEstudiante
	 * @param e Estudiante que recoge la información que se introducirá en el objeto preexistente.
	 * @param id long que recoge la id que se usará para buscar al Estudiante en la DB.
	 * @throws ClassNotFoundException si no encuentra la clase DaoComentario lanzará un error.
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error. 
	 */
	public void modificarEstudiante(Estudiante e, long id) throws ClassNotFoundException, SQLException {
		
		DaoEstudiante aux = new DaoEstudiante();
		aux.modificarEstudiante(e, id);
		
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
