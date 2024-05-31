package modelo;

/**
 * Esta es la clase de la que heredan atributos y métodos las clases Estudiante, Titulado y Admin, 
 * además permite la gestión de Usuarios en general, en aquellos casos en los que no son necesarios los parámetros de cada clase en particular.
 * @see Admin 
 * @see Estudiante 
 * @see Titulado
 * @author Alejandro Moreno
 * @version 1.0
 */
public class Usuario {
	
	protected long Id=0;
	protected int Nivel=0;
	protected String Nombre="";
	protected String Apellidos="";
	protected int Edad=0;
	protected String Email="";
	
	/**
	 * Método constructor vacío, servirá para generar la clase sin inicializar valores 
	 */
	public Usuario() {
		
	}
	/**
	 * Método constructor completo, este método será el que tomen las clases hijas para 
	 * inicializar sus constructores.
	 * @param id long que recoge un número entero único que se genera en el método generarId() de la clase DaoEstudiante.
	 * @see DaoEstudiante 
	 * @param nivel int que recoge el tipo de Usuario (Estudiante, Titulado, Admin).
	 * @param nombre String que recoge el nombre del usuario.
	 * @param apellidos String que recoge los apellidos del usuario.
	 * @param edad int que recoge la edad del usuario.
	 * @param email String que recoge el correo del usuario en la aplicacion.
	 * @param password String que recoge la encriptación de la contraseña del Usuario, mediante el método encriptar() de la clase Cifrado.
	 * @see Cifrado
	 */
	public Usuario(long id, int nivel, String nombre, String apellidos, int edad, String email) {
		super();
		Id = id;
		Nivel=nivel;
		Nombre = nombre;
		Apellidos = apellidos;
		Edad = edad;
		Email = email;
		
	}
	/**
	 * Método Get del parámetro Id, como todo método Get, devuelve el valor del parámetro en cuestión. 
	 * @return devuelve el valor del parámetro Id.
	 */
	public long getId() {
		return Id;
	}
	/**
	 * Método Set del parámetro Id, como todo método Set, establece el valor del parámetro en cuestión.
	 * @param id String cuyo valor se establecerá sobre Id.
	 */
	public void setId(long id) {
		Id = id;
	}
	/**
	 * @see getId()
	 */
	public int getNivel() {
		return Nivel;
	}
	/**
	 * @see setId()
	 */
	public void setNivel(int nivel) {
		Nivel = nivel;
	}
	/**
	 * @see getId()
	 */
	public String getNombre() {
		return Nombre;
	}
	/**
	 * @see setId()
	 */
	public void setNombre(String nombre) {
		Nombre = nombre;
	}
	/**
	 * @see getId()
	 */
	public String getApellidos() {
		return Apellidos;
	}
	/**
	 * @see setId()
	 */
	public void setApellidos(String apellidos) {
		Apellidos = apellidos;
	}
	/**
	 * @see getId()
	 */
	public int getEdad() {
		return Edad;
	}
	/**
	 * @see setId()
	 */
	public void setEdad(int edad) {
		Edad = edad;
	}
	/**
	 * @see getId()
	 */
	public String getEmail() {
		return Email;
	}
	/**
	 * @see setId()
	 */
	public void setEmail(String email) {
		Email = email;
	}
	/**
	 * Método toString() que muestra por pantalla todos los valores del objeto Usuario correspondiente, en caso de ser necesario.
	 */
	@Override
	public String toString() {
		return "Usuario [Id=" + Id + "\n Nivel=" + Nivel + "\n Nombre=" + Nombre + "\n Apellidos=" + Apellidos + "\n Edad="
				+ Edad + "\n Email=" + Email + "]";
	}

	
	
	
	
}
