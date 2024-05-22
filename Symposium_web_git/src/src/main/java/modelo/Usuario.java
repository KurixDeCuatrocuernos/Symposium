package modelo;

/**
 * Esta clase es la clase de la que heredan atributos y métodos las clases Estudiante, 
 * Titulado y Admin.
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
	 * @param id int que recoge un numero entero único (autoincremental). 
	 * @param nivel int que recoge el nivel de privilegios del usuario (servirá de filtro para más cosas).
	 * @param nombre String que recoge el nombre del usuario.
	 * @param apellidos String que recoge los apellidos del usuario.
	 * @param edad int que recoge la edad del usuario.
	 * @param email String que recoge el correo del usuario en la aplicacion.
	 * @param password String que recoge la serialización de la contraseña del usuario, aunque no debería estar así.
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
	
	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}
	
	public int getNivel() {
		return Nivel;
	}
	public void setNivel(int nivel) {
		Nivel = nivel;
	}
	
	public String getNombre() {
		return Nombre;
	}
	
	public void setNombre(String nombre) {
		Nombre = nombre;
	}
	
	public String getApellidos() {
		return Apellidos;
	}

	public void setApellidos(String apellidos) {
		Apellidos = apellidos;
	}
	
	public int getEdad() {
		return Edad;
	}

	public void setEdad(int edad) {
		Edad = edad;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	/**
	 * Método que permite registrarse a un usuario nuevo, llamando al método 
	 * constructor e insertando los valores recogidos en un formulario, en la base de 
	 * datos.
	 * @return registro Boleano que recoge si se ha realizado con éxito la inserción, 
	 * 		   si es así, devolverá true, si no, devolverá false.
	 */
	public boolean registrarse() {
		boolean registro=false;
		return registro; 
	}
	/**
	 * Método que permite iniciar sesión a un usuario existente.
	 * @return inicioSesion Boleano que recoge si el inicio de sesión se ha realizado con éxito, 
	 * 		   si es así, devolverá true, en caso contrario, devolverá false.
	 */
	
	public boolean loguearse() {
		
		boolean inicioSesion=false;
		
		return inicioSesion;
	
	}
	
	/**
	 * Metodo que permite buscar obras por nombre, llama al método gestionarBusqueda() del DAO 
	 * y devuelve la información que le emita dicho método.
	 */
	public void /*boolean*/ buscar() {
		
	}
	/**
	 * Método que permite filtrar las obras  por categorías llamando al método 
	 * gestionarFiltrado() del DAO y devuelve el resultado que ema dicho método.
	 */
	public void /*boolean*/ filtrar() {
		
	}
	/**
	 * Método que muestra toda la informacion de la clase por pantalla
	 */
	
	
	
}
