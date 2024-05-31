package modelo;

import java.time.LocalDate;

/**
	 * Esta clase permite generar una solicitud que recogerá los valores (parámetros) necesarios para que un estudiante pueda ser ascendido a Titulado,
	 * que un Admin debe confirmar o denegar.  
	 * @see Titulado
	 * @author Alejandro Moreno
	 * @version 1.0
	 */
public class SolicitudAscenso {
	
	private long IdUsuario=0;
	private String Titulo_estudios="";
	private String Lugar_estudios="";
	private LocalDate Fecha_titulo;
	private String Titulo_img;
	/**
	 * Método constructor vacío.
	 */
	public SolicitudAscenso() {
		
	}
	/**
	 * Método constructor con todos los atributos.
	 * @param idUsuario long que recoge la id de usuario del solicitante.
	 * @param titulo_estudios String que recoge el nombre del título de los estudios del solicitante. 
	 * @param lugar_estudios String que recoge el nombre del lugar donde se expidió el título del solicitante. 
	 * @param fecha_titulo LocalDate que recoge la fecha de expedición del título del solicitante.
	 * @param titulo_img String que recoge la ruta de la imagen del título, que sirve al Admin 
	 * 		  correspondiente para cotejar los datos introducidos.
	 */
	public SolicitudAscenso(long idUsuario, String titulo_estudios, String lugar_estudios, LocalDate fecha_titulo,
			String titulo_img) {
		super();
		IdUsuario = idUsuario;
		Titulo_estudios = titulo_estudios;
		Lugar_estudios = lugar_estudios;
		Fecha_titulo = fecha_titulo;
		Titulo_img = titulo_img;
	}
	
	/**
	 * Método Constructor sin el parámetro titulo_img.
	 * @param idUsuario long que recoge la id de usuario del solicitante.
	 * @param titulo_estudios String que recoge el nombre del título de los estudios del solicitante. 
	 * @param lugar_estudios String que recoge el nombre del lugar donde se expidió el título del solicitante. 
	 * @param fecha_titulo LocalDate que recoge la fecha de expedición del título del solicitante.
	 */

	public SolicitudAscenso(long idUsuario, String titulo_estudios, String lugar_estudios, LocalDate fecha_titulo) {
		super();
		IdUsuario = idUsuario;
		Titulo_estudios = titulo_estudios;
		Lugar_estudios = lugar_estudios;
		Fecha_titulo = fecha_titulo;
	}
	/**
	 * Método Get del parámetro IdUsuario, como todo método Get, devuelve el valor del parámetro en cuestión. 
	 * @return devuelve el valor del parámetro IdUsuario.
	 */
	public long getIdUsuario() {
		return IdUsuario;
	}
	/**
	 * Método Set del parámetro IdUsuario, como todo método Set, establece el valor del parámetro en cuestión.
	 * @param idUsuario long cuyo valor se establecerá sobre IdUsuario.
	 */
	public void setIdUsuario(long idUsuario) {
		IdUsuario = idUsuario;
	}
	/**
	 * @see getIdUsuario()
	 */
	public String getTitulo_estudios() {
		return Titulo_estudios;
	}
	/**
	 * @see setIdUsuario()
	 */
	public void setTitulo_estudios(String titulo_estudios) {
		Titulo_estudios = titulo_estudios;
	}
	 /**
	  * @see getIdUsuario()
	  */
	public String getLugar_estudios() {
		return Lugar_estudios;
	}
	/**
	 * @see setIdUsuario()
	 */
	public void setLugar_estudios(String lugar_estudios) {
		Lugar_estudios = lugar_estudios;
	}
	/**
	 * @see getIdUsuario()
	 */
	public LocalDate getFecha_titulo() {
		return Fecha_titulo;
	}
	/**
	 * @see setIdUsuario()
	 */
	public void setFecha_titulo(LocalDate fecha_titulo) {
		Fecha_titulo = fecha_titulo;
	}
	/**
	 * @see getIdUsuario()
	 */
	public String getTitulo_img() {
		return Titulo_img;
	}
	/**
	 * @see setIdUsuario()
	 */
	public void setTitulo_img(String titulo_img) {
		Titulo_img = titulo_img;
	}
	
	
	/**
	 * Método toString() que muestra por pantalla toda la informacion de la SolicitudAscenso, en caso de que fuese necesario. 
	 */
	@Override
	public String toString() {
		return "SolicitudAscenso [IdUsuario=" + IdUsuario + "\n Titulo_estudios=" + Titulo_estudios 
				+ "\n Lugar_estudios=" + Lugar_estudios + "\n Fecha_titulo=" + Fecha_titulo 
				+ "\n Titulo_img=" + Titulo_img + "]";
	}
	
}
