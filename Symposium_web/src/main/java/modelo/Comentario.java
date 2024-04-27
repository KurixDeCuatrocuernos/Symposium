package modelo;
/**
 * Esta clase permite generar objetos Comentario, aunque necesite del Id del usuario que escribe el comentario y el ISBN de la obra que comenta.
 * @author Alejandro Moreno
 * @version 1.0
 */
public class Comentario {
	private int Fecha_comentario=0;
	private String Titulo="";
	private String Texto="";
	private int Valoracion_obra=0;
	private int Valoracion_comentario=0;
	private int IdAutorComentario=0;
	private int ISBN_obra=0;
	/**
	 * Método constructor vacío.
	 */
	public Comentario() {
		
	}
	/**
	 * Método constructor con todos los atributos necesarios para generar el objeto. 
	 * El atributo Valoracion_comentario se inicializará en 0 porque depende de la votacion 
	 * del resto de usuarios del comentario generado. 
	 * @param fecha_comentario Int que recoge la fecha (y hora) en que se publica un Comentario.
	 * @param titulo String que recoge el titulo que el usuario desee poner al Comentario.
	 * @param texto String que recoge el contenido del Comentario (su extensión puede ser considerable)
	 * @param valoracion_obra Int que recoge la valoración (1 al 100) que el usuario da a la obra que comenta.
	 * @param valoracion_comentario Int que recoge la valoración del comentario, se inicializa en 0,
	 * 		  porque depende de la votación que hagan el resto de usuarios.
	 * @param idAutorComentario Int que recoge la Id del usuario que redacta el comentario.
	 * @param isbn_obra Int que recoge el ISBN de la obra a la que pertenece ese comentario.
	 */
	public Comentario(int fecha_comentario, String titulo, String texto, int valoracion_obra, int valoracion_comentario,
			int idAutorComentario, int isbn_obra) {
		Fecha_comentario = fecha_comentario;
		Titulo = titulo;
		Texto = texto;
		Valoracion_obra = valoracion_obra;
		Valoracion_comentario = valoracion_comentario;
		IdAutorComentario = idAutorComentario;
		ISBN_obra=isbn_obra;
	}

	public int getFecha_comentario() {
		return Fecha_comentario;
	}

	public void setFecha_comentario(int fecha_comentario) {
		Fecha_comentario = fecha_comentario;
	}

	public String getTitulo() {
		return Titulo;
	}

	public void setTitulo(String titulo) {
		Titulo = titulo;
	}

	public String getTexto() {
		return Texto;
	}

	public void setTexto(String texto) {
		Texto = texto;
	}

	public int getValoracion_obra() {
		return Valoracion_obra;
	}

	public void setValoracion_obra(int valoracion_obra) {
		Valoracion_obra = valoracion_obra;
	}

	public int getValoracion_comentario() {
		return Valoracion_comentario;
	}

	public void setValoracion_comentario(int valoracion_comentario) {
		Valoracion_comentario = valoracion_comentario;
	}

	public int getIdAutorComentario() {
		return IdAutorComentario;
	}

	public void setIdAutorComentario(int idAutorComentario) {
		IdAutorComentario = idAutorComentario;
	}
	
	public int getISBN_obra() {
		return ISBN_obra;
	}
	public void setISBN_obra(int iSBN_obra) {
		ISBN_obra = iSBN_obra;
	}
	
	/**
	 * Método que permite introducir un comentario en la Base de datos, 
	 * llamando al método ----- del DAO.
	 * @return insert Boleano que recoge si se ha insertado el comentario en la Base de datos con éxito, 
	 * 		   de ser así, devolverá true, en caso contrario, devolverá false.
	 */
	public boolean insertarComentario() {
		boolean insert=false;
		
		return insert;
	}
	/**
	 * Método que permite modificar un comentario preexistente en la Base de datos, 
	 * llamando al método ----- del DAO.
	 * @return modif Boleano que recoge si se ha modificado el comentario en la Base de datos con éxito, 
	 * 		   de ser así, devolverá true, en caso contrario, devolverá false.
	 */
	public boolean modificarComentario() {
		boolean modif=false;
		
		return modif;
	}
	/**
	 * Método que permite eliminar un comentario preexistente en la Base de datos, 
	 * llamando al método ----- del DAO.
	 * @return delete Boleano que recoge si se ha eliminado el comentario de la Base de datos con éxito, 
	 * 		   de ser así, devolverá true, en caso contrario (por la razón que fuere), devolverá false.
	 */
	public boolean borrarComentario() {
		boolean delete=false;
		
		return delete;
	}
//	/**
//	 * Método que sirve para establecer la valoración de una obra. Cada vez que se la valora modifica el valor total.
//	 * Era un lío y por eso quedó borrado.
//	 */
//	public void valorarObra (int valoraciones, int valoracion_global) {	
//		valoraciones=
//		valoracion_global=(valoracion_global+valoracion_global)/valoraciones;
//	}
	
	/**
	 * Método toString() que muestra por pantalla toda la informacion de un Comentario, en caso de que fuese necesario.
	 */
	@Override
	public String toString() {
		return "Comentario [Fecha_comentario=" + Fecha_comentario + "\n Titulo=" + Titulo + "\n Texto=" + Texto
				+ "\n Valoracion_obra=" + Valoracion_obra + "\n Valoracion_comentario=" + Valoracion_comentario
				+ "\n IdAutorComentario=" + IdAutorComentario + "\n ISBN_obra=" + ISBN_obra + "]";
	}
	
	
	
	
}