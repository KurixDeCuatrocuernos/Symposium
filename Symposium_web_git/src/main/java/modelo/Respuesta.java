package modelo;
/**
 * Esta clase permite generar objetos Respuesta, aunque necesite del Id del usuario que escribe el comentario y dependa del comentario 
 * o respuesta a la que responde.
 * @author Alejandro Moreno
 * @version 1.0
 */
public class Respuesta {
	private int Fecha_respuesta=0;
	private int Id_respuesta=0;
	private String Texto="";
	/**
	 * Método constructor vacío.
	 */
	public Respuesta() {
		
	}
	/**
	 * Método constructor con todos los atributos.
	 * @param fecha_respuesta Int que recoge la fecha en que se crea el objeto Respuesta.
	 * @param id_respuesta Int que recoge un número único (autoincremental) para identificar la respuesta inequivocamente.
	 * @param valor_respuesta Int que se inicializa a 0, porque depende de las valoraciones posteriores (aunque es un elemento que forma parte del objeto).
	 * @param texto String que recoge el contenido de la respuesta (su extensión puede ser considerable).
	 */
	public Respuesta(int fecha_respuesta, int id_respuesta, String texto) {
		super();
		Fecha_respuesta = fecha_respuesta;
		Id_respuesta = id_respuesta;
		Texto = texto;
	}

	public int getFecha_respuesta() {
		return Fecha_respuesta;
	}

	public void setFecha_respuesta(int fecha_respuesta) {
		Fecha_respuesta = fecha_respuesta;
	}

	public int getId_respuesta() {
		return Id_respuesta;
	}

	public void setId_respuesta(int id_respuesta) {
		Id_respuesta = id_respuesta;
	}

	public String getTexto() {
		return Texto;
	}

	public void setTexto(String texto) {
		Texto = texto;
	}
	
	/**
	 * Método que permite introducir una Respuesta en la Base de datos, 
	 * llamando al método ----- del DAO.
	 * @return insert Boleano que recoge si se ha insertado la Respuesta en la Base de datos con éxito, 
	 * 		   de ser así, devolverá true, en caso contrario, devolverá false.
	 */
	public boolean insertarRespuesta() {
		boolean insert=false;
		
		return insert;
	}
	/**
	 * Método que permite  modificar un comentario preexistente en la Base de datos, 
	 * llamando al método ----- del DAO. Este método NO SE USA, pero lo he dejado, 
	 * en caso de que se quiera usar en un futuro.
	 * @return modif Boleano que recoge si se ha modificado la Respuesta en la Base de datos con éxito, 
	 * 		   de ser así, devolverá true, en caso contrario, devolverá false.
	 */
	public boolean modificarRespuesta() {
		boolean modif=false;
		
		return modif;
	}
	/**
	 * Método que permite eliminar una Respuesta preexistente en la Base de datos, 
	 * llamando al método ----- del DAO.
	 * @return delete Boleano que recoge si se ha eliminado la Respuesta de la Base de datos con éxito, 
	 * 		   de ser así, devolverá true, en caso contrario (por la razón que fuere), devolverá false.
	 */
	public boolean borrarRespuesta() {
		boolean delete=false;
		
		return delete;
	}
	
	/**
	 * Método toString() que muestra por pantalla toda la información de una Respuesta, en caso de que fuera necesario.
	 */
	@Override
	public String toString() {
		return "Respuesta [Fecha_respuesta=" + Fecha_respuesta + "\n Id_respuesta=" 
	+ Id_respuesta + "\n Texto=" + Texto + "]";
	}
	
	
}
