package modelo;

import java.sql.SQLException;
import java.time.LocalDateTime;

import DAO.DaoComentario;

/**
 * Esta clase permite generar objetos Comentario, a partir de los parámetros: 
 * Fecha_comentario, Titulo, Texto, IdAutorComentario, ISBN_obra, Alias, TituloObra, AutorObra, Valoracion_obra y Valoracion_comentario.
 * @author Alejandro Moreno
 * @version 1.0
 */
public class Comentario {
	private LocalDateTime Fecha_comentario;
	private String Titulo="";
	private String Texto="";
	private int Valoracion_obra=0;
	private int Valoracion_comentario=0;
	private long IdAutorComentario=0;
	private long ISBN_obra=0;
	private String Alias="";
	private String TituloObra="";
	private String AutorObra="";
	
	/**
	 * Método constructor vacío.
	 */
	public Comentario() {
		
	}
	
	/**
	 * Método constructor con todos los atributos necesarios para generar el objeto. 
	 * El atributo Valoracion_comentario se inicializará en 0 porque depende de la votacion 
	 * del resto de usuarios del comentario generado: 
	 * @param fecha_comentario int que recoge la fecha (y hora) en que se publica un Comentario.
	 * @param titulo String que recoge el titulo que el usuario desee poner al Comentario.
	 * @param texto String que recoge el contenido del Comentario (su extensión puede ser considerable)
	 * @param valoracion_obra int que recoge la valoración (1 al 100) que el usuario da a la obra que comenta.
	 * @param valoracion_comentario int que recoge la valoración del comentario, se inicializa en 0,
	 * 		  porque depende de la votación que hagan el resto de usuarios.
	 * @param idAutorComentario int que recoge la Id del usuario que redacta el comentario.
	 * @param isbn_obra long que recoge el ISBN de la obra a la que pertenece ese comentario.
	 */
	public Comentario(LocalDateTime fecha_comentario, String titulo, String texto, int valoracion_obra, int valoracion_comentario,
			long idAutorComentario, long isbn_obra) {
		Fecha_comentario = fecha_comentario;
		Titulo = titulo;
		Texto = texto;
		IdAutorComentario = idAutorComentario;
		ISBN_obra=isbn_obra;
	}
	
	/**
	 * Método constructor sin el parámetro Valoración_comentario:
	 * @param fecha_comentario int que recoge la fecha (y hora) en que se publica un Comentario.
	 * @param titulo String que recoge el titulo que el usuario desee poner al Comentario.
	 * @param texto String que recoge el contenido del Comentario (su extensión puede ser considerable)
	 * @param valoracion_comentario int que recoge la valoración del comentario, se inicializa en 0,
	 * 		  porque depende de la votación que hagan el resto de usuarios.
	 * @param idAutorComentario int que recoge la Id del usuario que redacta el comentario.
	 * @param isbn_obra long que recoge el ISBN de la obra a la que pertenece ese comentario.
	 */
	public Comentario(LocalDateTime fecha_comentario, String titulo, String texto, int valoracion_obra, long idAutorComentario,
			long iSBN_obra) {
		super();
		Fecha_comentario = fecha_comentario;
		Titulo = titulo;
		Texto = texto;
		Valoracion_obra = valoracion_obra;
		IdAutorComentario = idAutorComentario;
		ISBN_obra = iSBN_obra;
	}
	
	/**
	 * Método con todos los parámetros necesarios para listar completamente un comentario:
	 * @param fecha_comentario int que recoge la fecha (y hora) en que se publica un Comentario.
	 * @param titulo String que recoge el titulo que el usuario desee poner al Comentario.
	 * @param texto String que recoge el contenido del Comentario (su extensión puede ser considerable)
	 * @param valoracion_comentario int que recoge la valoración del comentario, se inicializa en 0,
	 * 		  porque depende de la votación que hagan el resto de usuarios.
	 * @param idAutorComentario int que recoge la Id del usuario que redacta el comentario.
	 * @param isbn_obra long que recoge el ISBN de la obra a la que pertenece ese comentario.
	 * @param alias String que recoge el nombre del usuario que genera el comentario.
	 */
	public Comentario(LocalDateTime fecha_comentario, String titulo, String texto, int valoracion_obra, long idAutorComentario, long iSBN_obra, String alias) {
		super();
		Fecha_comentario = fecha_comentario;
		Titulo = titulo;
		Texto = texto;
		Valoracion_obra = valoracion_obra;
		IdAutorComentario = idAutorComentario;
		ISBN_obra = iSBN_obra;
		Alias = alias;
	}
	
	/**
	 * Metodo constructor para la clase comentario, para cuando es necesario listar toda la información de un comentario (titulo de la obra y del comentador):
	 * 
	 * @param fecha_comentario int que recoge la fecha (y hora) en que se publica un Comentario.
	 * @param titulo String que recoge el titulo que el usuario desee poner al Comentario.
	 * @param texto String que recoge el contenido del Comentario (su extensión puede ser considerable)
	 * @param valoracion_comentario int que recoge la valoración del comentario, se inicializa en 0,
	 * 		  porque depende de la votación que hagan el resto de usuarios.
	 * @param idAutorComentario int que recoge la Id del usuario que redacta el comentario.
	 * @param isbn_obra long que recoge el ISBN de la obra a la que pertenece ese comentario.
	 * @param alias String que recoge el nombre del usuario.
	 * @param tituloObra String que recoge el nombre de la obra a la que hace referencia el comentario.
	 * @param autorObra String que recoge el nombre del autor de la obra.
	 */
	public Comentario(LocalDateTime fecha_comentario, String titulo, String texto, int valoracion_obra, long idAutorComentario, 
			long iSBN_obra, String alias, String tituloObra, String autorObra) {
		super();
		Fecha_comentario = fecha_comentario;
		Titulo = titulo;
		Texto = texto;
		Valoracion_obra = valoracion_obra;
		IdAutorComentario = idAutorComentario;
		ISBN_obra = iSBN_obra;
		Alias = alias;
		TituloObra = tituloObra;
		AutorObra=autorObra;
	}
	
	/**
	 * Método Get del parámetro Fecha_comentario, como todo método Get, recoge y devuelve el valor del parámetro fecha_comentario.
	 * @return devuelve el valor del parámetro Fecha_comentario de la clase Comentario.
	 */
	public LocalDateTime getFecha_comentario() {
		return Fecha_comentario;
	}
	
	/**
	 * Método Set del parámetro Fecha_comentario. Como todo método Set, sirve para recoger el valor del parámetro del objeto en cuestión. 
	 * @param fecha_comentario String que recoge la fecha de publicación del comentario.
	 */
	public void setFecha_comentario(LocalDateTime fecha_comentario) {
		Fecha_comentario = fecha_comentario;
	}
	
	/**
	 * @see getFecha_comentario()
	 */
	public String getTitulo() {
		return Titulo;
	}
	
	/**
	 * @see setFecha_comentario()
	 */
	public void setTitulo(String titulo) {
		Titulo = titulo;
	}
	
	/**
	 * @see getFecha_comentario()
	 */
	public String getTexto() {
		return Texto;
	}
	
	/**
	 * @see setFecha_comentario()
	 */
	public void setTexto(String texto) {
		Texto = texto;
	}
	
	/**
	 * @see getFecha_comentario()
	 */
	public int getValoracion_obra() {
		return Valoracion_obra;
	}
	
	/**
	 * @see setFecha_comentario()
	 */
	public void setValoracion_obra(int valoracion_obra) {
		Valoracion_obra = valoracion_obra;
	}
	
	/**
	 * @see getFecha_comentario()
	 */
	public int getValoracion_comentario() {
		return Valoracion_comentario;
	}
	
	/**
	 * @see setFecha_comentario()
	 */
	public void setValoracion_comentario(int valoracion_comentario) {
		Valoracion_comentario = valoracion_comentario;
	}
	
	/**
	 * @see getFecha_comentario()
	 */
	public long getIdAutorComentario() {
		return IdAutorComentario;
	}
	
	/**
	 * @see setFecha_comentario()
	 */
	public void setIdAutorComentario(long idAutorComentario) {
		IdAutorComentario = idAutorComentario;
	}
	
	/**
	 * @see getFecha_comentario()
	 */
	public long getISBN_obra() {
		return ISBN_obra;
	}
	
	/**
	 * @see setFecha_comentario()
	 */
	public void setISBN_obra(long iSBN_obra) {
		ISBN_obra = iSBN_obra;
	}
	
	/**
	 * @see getFecha_comentario()
	 */
	public String getAlias() {
		return Alias;
	}
	
	/**
	 * @see setFecha_comentario()
	 */
	public void setAlias(String alias) {
		Alias = alias;
	}
	
	/**
	 * @see getFecha_comentario()
	 */
	public String getTituloObra() {
		return TituloObra;
	}
	
	/**
	 * @see setFecha_comentario()
	 */
	public void setTituloObra(String tituloObra) {
		TituloObra = tituloObra;
	}
	
	/**
	 * @see getFecha_comentario()
	 */
	public String getAutorObra() {
		return AutorObra;
	}
	
	/**
	 * @see setFecha_comentario()
	 */
	public void setAutorObra(String autorObra) {
		AutorObra = autorObra;
	}
	
	/**
	 * Método que sirve para listar los comentarios <em>más recientes</em> de una obra según el parámetro Nivel del usuario que lo inserta @see Usuario, 
	 * para ello recibe un valor mínimo y máximo (ambos proceden del servlet) y los envía al método listarJsonPorTiempo() de la clase DaoComentario @see DaoComentario.
	 * @param tipomin int que recoge el valor mínimo con el que se filtrará el parámetro Nivel.
	 * @param tipomax int que recoge el valor máximo con el que se filtrará el parámetro Nivel.
	 * @return String que recoge los datos que devuelve el método listarJsonPorTiempo() de la clase DaoComentario @see DaoComentario.
	 * @throws ClassNotFoundException si no encuentra la clase DaoComentario lanzará un error.
	 */
	public String listarComentariosConObraPorIntervaloYTiempo(int tipomin, int tipomax) throws ClassNotFoundException {
		String datos="";
		try {
			DaoComentario aux=new DaoComentario();
			datos=aux.listarJsonPorTiempo(tipomin, tipomax);
		} catch(SQLException SQLex) {
			SQLex.printStackTrace();
		}
		return datos;
	}
	
	/**
	 * Método que sirve para listar los comentarios de una obra <em>únicamente según el parámetro Nivel</em> del usuario que lo inserta @see Usuario, 
	 * para ello recibe un valor mínimo y máximo (ambos proceden del servlet) y los envía al método listarJsonPorTiempo() de la clase DaoComentario @see DaoComentario.
	 * @param tipomin int que recoge el valor mínimo con el que se filtrará el parámetro Nivel.
	 * @param tipomax int que recoge el valor máximo con el que se filtrará el parámetro Nivel.
	 * @return String que recoge los datos que devuelve el método listarJsonPorTipo() de la clase DaoComentario @see DaoComentario.
	 * @throws ClassNotFoundException si no encuentra la clase DaoComentario lanzará un error.
	 */
	public String listarComentariosConObraPorIntervalo(int tipomin, int tipomax) throws ClassNotFoundException {
		String datos="";
		try {
			DaoComentario aux=new DaoComentario();
			datos=aux.listarJsonPorTipo(tipomin, tipomax);
		} catch(SQLException SQLex) {
			SQLex.printStackTrace();
		}
		return datos;
	}
	
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