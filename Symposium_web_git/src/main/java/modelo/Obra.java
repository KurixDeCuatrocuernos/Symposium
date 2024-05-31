package modelo;

import java.sql.Date;
import java.sql.SQLException;

import DAO.DaoObra;

/**
 * Clase que genera las propiedades comunes que se usarán en las clases Libro y Artículo. También sirve como medio para las situaciones en que se necesita usar ambos.
 * Esta clase se compone de los parámetros: ISBN, Abstracto, Autor, Titulo, Tipo, Fecha_publicacion, Valoracion_global y Valoraciones.
 * @author Alejandro Moreno
 * @version 1.0 
 */
public class Obra {
	
	protected long ISBN;
	protected String Abstracto="";
	protected String Autor="";
	protected String Titulo="";
	protected String Tipo="";
	protected Date Fecha_publicacion;
	protected int Valoracion_global=0;
	protected int Valoraciones;
	
	/**
	 * Método constructor vacío.
	 */
	public Obra() {
		super();
	}
	
	/**
	 * Método constructor con las variables que heredarán las clases Libro y Artículo, a excepción del parámetro Tipo.
	 * @param iSBN long que recoge el Identificador numérico único de una obra escrita (sea física o digital).
	 * @param abstracto String que recoge el resumen de una Obra (su extensión es considerable).
	 * @param autor String que recoge el nombre y apellidos del autor de la obra.
	 * @param titulo String que recoge el título de la obra.
	 * @param fecha_publicacion int que recoge la fecha de publicación original de la obra.
	 * @param valoracion_global int que se inicializa a 0, porque depende de las valoraciones que hagan los usuarios en sus comentarios.
	 * @see Comentario 
	 * @param valoraciones int que se inicializa en 0, porque depende de las valoraciones que hagan los usurios en sus comentarios.
	 */
	public Obra(long iSBN, String abstracto, String autor, String titulo, String tipo, Date fecha_publicacion) {
		super();
		this.ISBN = iSBN;
		this.Abstracto = abstracto;
		this.Autor = autor;
		this.Titulo = titulo;
		this.Fecha_publicacion = fecha_publicacion;
		this.Valoracion_global=0;
		this.Valoraciones=0;
		this.Tipo=tipo;
	}
	/**
	 * Método constructor con todas las variables que heredarán las clases Libro y Artículo.
	 * @param iSBN long que recoge el Identificador numérico único de una obra escrita (sea física o digital).
	 * @param abstracto String que recoge el resumen de una Obra (su extensión es considerable).
	 * @param autor String que recoge el nombre y apellidos del autor de la obra.
	 * @param titulo String que recoge el título de la obra.
	 * @param tipo String que recoge de qué tipo de obra se trata (Libro o Articulo).
	 * @param fecha_publicacion int que recoge la fecha de publicación original de la obra.
	 * @param valoracion_global int que se inicializa a 0, porque depende de las valoraciones que hagan los usuarios en sus comentarios 
	 * @see Comentario
	 * @param valoraciones int que se inicializa en 0, porque depende de las valoraciones que hagan los usurios en sus comentarios 
	 */
	public Obra(long iSBN, String abstracto, String autor, String titulo, String tipo, Date fecha_publicacion,
			int valoracion_global, int valoraciones) {
		super();
		ISBN = iSBN;
		Abstracto = abstracto;
		Autor = autor;
		Titulo = titulo;
		Tipo = tipo;
		Fecha_publicacion = fecha_publicacion;
		Valoracion_global = valoracion_global;
		Valoraciones = valoraciones;
	}
	
	
	/**
	 * Método constructor para el buscador de obras
	 * @param iSBN long que recoge el Identificador numérico único de una obra escrita (sea física o digital).
	 * @param titulo String que recoge el título de la obra.
	 * @param tipo String que recoge de qué tipo de obra se trata (Libro o Articulo).
	 */
	public Obra(long iSBN, String titulo, String tipo) {
		super();
		ISBN = iSBN;
		Titulo = titulo;
		Tipo = tipo;
	}
	/**
	 * Método Get del parámetro ISBN, como todo método Get, devuelve el valor del parámetro en cuestión.
	 * @return devuelve el valor del parámetro ISBN de la clase.
	 */
	public long getISBN() {
		return ISBN;
	}
	/**
	 * Método Set del parámetro ISBN, como todo método Set, sirve para establecer el valor de dicho parámetro, mediante un valor que se le proporcione.
	 * @param iSBN long que recoge el parámetro que se establecerá en ISBN.
	 */
	public void setISBN(long iSBN) {
		this.ISBN = iSBN;
	}
	/**
	 * @see getISBN()
	 */
	public String getAbstracto() {
		return Abstracto;
	}
	/**
	 * @see setISBN()
	 */
	public void setAbstracto(String abstracto) {
		this.Abstracto = abstracto;
	}
	/**
	 * @see getISBN()
	 */
	public String getAutor() {
		return Autor;
	}
	/**
	 * @see setISBN()
	 */
	public void setAutor(String autor) {
		this.Autor = autor;
	}
	/**
	 * @see getISBN()
	 */
	public String getTitulo() {
		return Titulo;
	}
	/**
	 * @see setISBN()
	 */
	public void setTitulo(String titulo) {
		this.Titulo = titulo;
	}
	/**
	 * @see getISBN()
	 */
	public String getTipo() {
		return Tipo;
	}
	/**
	 * @see setISBN()
	 */
	public void setTipo(String tipo) {
		Tipo = tipo;
	}
	/**
	 * @see getISBN()
	 */
	public Date getFecha_publicacion() {
		return Fecha_publicacion;
	}
	/**
	 * @see setISBN()
	 */
	public void setFecha_publicacion(Date fecha_publicacion) {
		this.Fecha_publicacion = fecha_publicacion;
	}
	/**
	 * @see getISBN()
	 */
	public int getValoracion_global() {
		return Valoracion_global;
	}
	/**
	 * @see setISBN()
	 */
	public void setValoracion_global(int valoracion_global) {
		Valoracion_global = valoracion_global;
	}
	/**
	 * @see getISBN()
	 */
	public int getValoraciones() {
		return Valoraciones;
	}
	/**
	 * @see setISBN()
	 */
	public void setValoraciones(int valoraciones) {
		Valoraciones = valoraciones;
	}
	/**
	 * Método para buscar obras en la DB, para ello recibe un texto con el que buscará en la DB, pasándoselo al método listarObrasJson() de la clase DaoObra.
	 * @see DaoObra
	 * @param texto String que recoge 
	 * @return String que recoge los resultados que se hayan obtenido en la búsqueda.
	 */
	public String buscarObras(String texto){
		String datos="";
		DaoObra auo;
		try {
			auo = new DaoObra();
			datos=auo.listarObrasJson(texto);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return datos;
	}
	
}
