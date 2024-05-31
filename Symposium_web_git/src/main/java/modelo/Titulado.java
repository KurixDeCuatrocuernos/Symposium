package modelo;

import java.sql.SQLException;

import DAO.DaoTitulado;

/**
 * Esta clase recoge los datos para generar un objeto Titulado a partir de la 
 * clase Usuario (extiende dicha clase).
 * @author Alejandro Moreno
 * @version 1.0
 */
public class Titulado extends Usuario{
	
	private String Titulo_estudios="";
	private String Lugar_titulo="";
	private int Fecha_titulo;
	private String Titulo_img="";
	
	/**
	 * Método constructor vacío.
	 */
	public Titulado() {
		super();
	}
	
	/**
	 * Método constructor de la clase Titulado, a excepción de la variable imagen.
	 * @param id long que recoge un número entero único que se genera en el método generarId() de la clase DaoEstudiante.
	 * @see DaoEstudiante
	 * @param nivel int que recoge el nivel de privilegios del Usuario (servirá de filtro para más cosas).
	 * @param nombre String que recoge el nombre del Usuario.
	 * @param apellidos String que recoge los apellidos del Usuario.
	 * @param edad int que recoge la edad del Usuario.
	 * @param email String que recoge el correo del Usuario en la aplicacion.
	 * @param password String que recoge la encriptación de la contraseña del Usuario, mediante el método encriptar() de la clase Cifrado.
	 * @see Cifrado
	 * @param tituloEstudios String que recoge el tipo de título que tiene un Usuario Titulado, v.g. Licenciado en Bellas Artes.
	 * @param lugarTitulo String que recoge lugar donde se expidió el titulo del Usuario Titulado.
	 * @param fechaTitulo int que recoge la fecha de expedición del título del Usuario Titulado.
	 */
	public Titulado(long id, int nivel, String nombre, String apellidos, int edad, String email, String tituloEstudios, String lugarTitulo, int fechaTitulo) {
		super(id, nivel, nombre, apellidos, edad, email);
		this.Titulo_estudios=tituloEstudios;
		this.Lugar_titulo=lugarTitulo;
		this.Fecha_titulo=fechaTitulo;
	}
	
	/**
	 * Método constructor de la clase 
	 * @param id long que recoge un número entero único que se genera en el método generarId() de la clase DaoEstudiante.
	 * @see DaoEstudiante 
	 * @param nivel int que recoge el nivel de privilegios del Usuario (servirá de filtro para más cosas).
	 * @param nombre String que recoge el nombre del Usuario.
	 * @param apellidos String que recoge los apellidos del Usuario.
	 * @param edad int que recoge la edad del Usuario.
	 * @param email String que recoge el correo del Usuario en la aplicacion.
	 * @param password String que recoge la encriptación de la contraseña del Usuario, mediante el método encriptar() de la clase Cifrado.
	 * @see Cifrado
	 * @param tituloEstudios String que recoge el tipo de título que tiene un Usuario Titulado, v.g. Licenciado en Bellas Artes.
	 * @param lugarTitulo String que recoge lugar donde se expidió el titulo del Usuario Titulado.
	 * @param fechaTitulo int que recoge la fecha de expedición del título del Usuario Titulado.
	 * @param tituloImg int que recoge la ruta de la imagen del título que proporciona el Usuario Titulado para que un Admin pueda cotejar los datos.
	 */
	public Titulado(long id, int nivel, String nombre, String apellidos, int edad, String email, String tituloEstudios, String lugarTitulo, int fechaTitulo, String tituloImg) {
		super(id, nivel=30, nombre, apellidos, edad, email);
		this.Titulo_estudios=tituloEstudios;
		this.Lugar_titulo=lugarTitulo;
		this.Fecha_titulo=fechaTitulo;
		this.Titulo_img=tituloImg;
	}
	
	/**
	 * Método Get del parámetro Titulo_estudios, como todo método Get, devuelve el valor del parámetro en cuestión. 
	 * @return devuelve el valor del parámetro Titulo_estudios.
	 */
	public String getTitulo_estudios() {
		return Titulo_estudios;
	}
	
	/**
	 * Método Set del parámetro Titulo_estudios, como todo método Set, establece el valor del parámetro en cuestión.
	 * @param titulo_estudios String cuyo valor se establecerá sobre Titulo_estudios.
	 */
	public void setTitulo_estudios(String titulo_estudios) {
		Titulo_estudios = titulo_estudios;
	}
	
	/**
	 * @see getTitulo_estudios()
	 */
	public String getLugar_titulo() {
		return Lugar_titulo;
	}
	
	/**
	 * @see setTitulo_Estudios()
	 */
	public void setLugar_titulo(String lugar_titulo) {
		Lugar_titulo = lugar_titulo;
	}
	
	/**
	 * @see getTitulo_estudios()
	 */
	public int getFecha_titulo() {
		return Fecha_titulo;
	}
	
	/**
	 * @see setTitulo_estudios()
	 */
	public void setFecha_titulo(int fecha_titulo) {
		Fecha_titulo = fecha_titulo;
	}
	
	/**
	 * @see getTitulo_estudios()
	 */
	public String getTitulo_img() {
		return Titulo_img;
	}
	
	/**
	 * @see setTitulo_estudios()
	 */
	public void setTitulo_img(String titulo_img) {
		Titulo_img = titulo_img;
	}
	
	/**
	 * Método que permite Modificar los valores de los parámetros de un titulado, 
	 * para ello llama al métoo modificarTitulado() de la clase DaoTitulado y le pasará un objeto Titulado con los valores y un long con la ID del titulado a modificar que se usará para buscarlo  
	 * @param T Titulado que contiene los valores que se modificarán.
	 * @param ID long que servirá para buscar al titulado que se modificará. 
	 * @throws ClassNotFoundException si no encuentra la clase DaoComentario lanzará un error.
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error. 
	 */
	public void modificarTitulado(Titulado T, long ID) throws ClassNotFoundException, SQLException {
		
		DaoTitulado aux = new DaoTitulado();
		aux.modificarTitulado(T, ID);
		
	}
	
	/**
	 * Método toString() que muestra por pantalla todos los valores del objeto Titulado correspondiente, en caso de ser necesario.
	 */
	@Override
	public String toString() {
		return "Titulado [Id=" + Id + "\n Nivel=" + Nivel + "\n Nombre=" + Nombre + "\n Apellidos=" + Apellidos + "\n Edad=" + Edad + "\n Email=" + Email 
				+ "\n Titulo_estudios=" + Titulo_estudios + "\n Lugar_titulo=" + Lugar_titulo + "\n Fecha_titulo=" + Fecha_titulo 
				+ "\n Titulo_img=" + Titulo_img + "]";
	}
	
	
	
}
