package modelo;

import java.time.LocalDateTime;

/**
 * Esta clase permite generar objetos Respuesta a partir de los p.
 * @author Alejandro Moreno
 * @version 1.0
 * @deprecated No se ha llegado a implementar, pero está creada en caso de que en un futuro se desee hacerlo.
 */
public class Respuesta {
	private LocalDateTime Fecha_respuesta;
	private long Id_respuesta=0;
	private String Texto="";
	private long Id_autor=0;
	private long Id_obra=0;
	/**
	 * Método constructor vacío.
	 */
	public Respuesta() {
		
	}

	/**
	 * Método constructor con todos los parámetros: fecha_respuesta, id_respuesta, texto, id_autor e id_obra.
	 * @param fecha_respuesta LocalDateTime que recoge la fecha y hora en que se crea el objeto Respuesta.
	 * @param id_respuesta long que recoge un número único para identificar la respuesta inequivocamente.
	 * @param texto String que recoge el contenido de la respuesta (su extensión puede ser considerable).
	 * @param id_autor long que recoge la id del usuario que responde.
	 * @param id_obra long que recoge la ISBN o ISSN de la obra en la que se encuentra el comentario al que responde la respuesta.
	 */
	public Respuesta(LocalDateTime fecha_respuesta, long id_respuesta, String texto, long id_autor, long id_obra) {
		super();
		Fecha_respuesta = fecha_respuesta;
		Id_respuesta = id_respuesta;
		Texto = texto;
		Id_autor = id_autor;
		Id_obra = id_obra;
	}
	
	/**
	 * Método Get del parámetro Fecha_respuesta, como todo método Get, devuelve el valor del parámetro en cuestión. 
	 * @return devuelve el valor del parámetro Fecha_respuesta.
	 */
	public LocalDateTime getFecha_respuesta() {
		return Fecha_respuesta;
	}
	
	/**
	 * Método Set del parámetro Fecha_respuesta, como todo método Set, establece el valor del parámetro en cuestión.
	 * @param fecha_respuesta LocalDateTime cuyo valor se establecerá sobre Fecha_respuesta.
	 */
	public void setFecha_respuesta(LocalDateTime fecha_respuesta) {
		Fecha_respuesta = fecha_respuesta;
	}
	
	/**
	 * @see getFecha_respuesta()
	 */
	public long getId_respuesta() {
		return Id_respuesta;
	}
	
	/**
	 * @see setFecha_respuesta()
	 */
	public void setId_respuesta(long id_respuesta) {
		Id_respuesta = id_respuesta;
	}
	
	/**
	 * @see getFecha_respuesta()
	 */
	public String getTexto() {
		return Texto;
	}
	
	/**
	 * @see setFecha_respuesta()
	 */
	public void setTexto(String texto) {
		Texto = texto;
	}
	
	/**
	 * @see getFecha_respuesta()
	 */
	public long getId_autor() {
		return Id_autor;
	}
	
	/**
	 * @see setFecha_respuesta()
	 */
	public void setId_autor(long id_autor) {
		Id_autor = id_autor;
	}
	
	/**
	 * @see getFecha_respuesta()
	 */
	public long getId_obra() {
		return Id_obra;
	}
	
	/**
	 * @see setFecha_respuesta()
	 */
	public void setId_obra(long id_obra) {
		Id_obra = id_obra;
	}

	/**
	 * Método toString() que muestra por pantalla toda la información de una Respuesta, en caso de que fuera necesario.
	 */
	@Override
	public String toString() {
		return "Respuesta [Id_respuesta= " + Id_respuesta + "\n Fecha_respuesta=" + Fecha_respuesta + "\n Texto=" + Texto
				+ "\n Id_autor=" + Id_autor + "\n Id_obra=" + Id_obra + "]";
	}

}
