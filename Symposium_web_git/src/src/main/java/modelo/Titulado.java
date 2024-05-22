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
	 * @param id int que recoge un numero entero único (autoincremental). 
	 * @param nivel int que recoge el nivel de privilegios del Usuario (servirá de filtro para más cosas).
	 * @param nombre String que recoge el nombre del Usuario.
	 * @param apellidos String que recoge los apellidos del Usuario.
	 * @param edad int que recoge la edad del Usuario.
	 * @param email String que recoge el correo del Usuario en la aplicacion.
	 * @param password String que recoge la serialización de la contraseña del Usuario, aunque no debería estar así.
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
	 * @param id int que recoge un numero entero único (autoincremental). 
	 * @param nivel int que recoge el nivel de privilegios del Usuario (servirá de filtro para más cosas).
	 * @param nombre String que recoge el nombre del Usuario.
	 * @param apellidos String que recoge los apellidos del Usuario.
	 * @param edad int que recoge la edad del Usuario.
	 * @param email String que recoge el correo del Usuario en la aplicacion.
	 * @param password String que recoge la serialización de la contraseña del Usuario, aunque no debería estar así.
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

	public String getTitulo_estudios() {
		return Titulo_estudios;
	}

	public void setTitulo_estudios(String titulo_estudios) {
		Titulo_estudios = titulo_estudios;
	}

	public String getLugar_titulo() {
		return Lugar_titulo;
	}

	public void setLugar_titulo(String lugar_titulo) {
		Lugar_titulo = lugar_titulo;
	}

	public int getFecha_titulo() {
		return Fecha_titulo;
	}

	public void setFecha_titulo(int fecha_titulo) {
		Fecha_titulo = fecha_titulo;
	}

	public String getTitulo_img() {
		return Titulo_img;
	}

	public void setTitulo_img(String titulo_img) {
		Titulo_img = titulo_img;
	}
	
	public void modificarTitulado(Titulado T, long ID) throws ClassNotFoundException, SQLException {
		
		DaoTitulado aux = new DaoTitulado();
		aux.modificarTitulado(T, ID);
		
	}
	/**
	 * Método que permite al Titulado eliminar su cuenta (es decir, 
	 * los datos almacenados en la base de datos)
	 * @return baja Boleano que recoge si la eliminación de los datos se ha realizado con éxito,
	 * 		   si así ha sido devolverá true, si no, devolverá false.
	 */
	public boolean solicitarBaja() {
		boolean baja=false;
		
		return baja;
	}
	/**
	 * Método que permite al Titulado cerrar sesión, es decir, no aparecer con su nombre de usuario ni poder hacer lo correspondiente.
	 * @return close Boleano que recoge si el cierre de sesión se ha efectuado o no, en caso de que se haya efectuado el cierre, devolverá true, 
	 * 		   en caso contrario devolverá false.
	 */
	public boolean cerrarSesion() {
		boolean close=false;
		
		return close;
	}
	/**
	 * Método que genera un comentario en la obra correspondiente llamando al método constructor de la clase Comentario.
	 * @return coment Boleano que recoge si la creación del comentario se ha realizado con éxito, 
	 * 		   si así ha sido, devolverá true, en caso contrario devolverá false.
	 */
	public boolean comentar() {
		boolean coment=false;
		
		return coment;
	}
	/**
	 * Método que permite modificar un comentario ya existente y que ha sido generado por el mismo Titulado que lo modifica. 
	 * @return modif Boleano que recoge si la modificacion del comentario se ha realizado con éxito, si así ha sido devolverá true, 
	 * 		   en caso de que no se haya podido modificar, sea por la razón que fuere, devolverá false.
	 */
	public boolean modificarComentario() {
		boolean modif=false;
		
		return modif;
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
	 * Método toString() que muestra por pantalla todos los valores del objeto Titulado correspondiente, en caso de ser necesario.
	 */
	@Override
	public String toString() {
		return "Titulado [Id=" + Id + "\n Nivel=" + Nivel + "\n Nombre=" + Nombre + "\n Apellidos=" + Apellidos + "\n Edad=" + Edad + "\n Email=" + Email 
				+ "\n Titulo_estudios=" + Titulo_estudios + "\n Lugar_titulo=" + Lugar_titulo + "\n Fecha_titulo=" + Fecha_titulo 
				+ "\n Titulo_img=" + Titulo_img + "]";
	}
	
	
	
}
