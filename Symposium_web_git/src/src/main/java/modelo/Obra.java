package modelo;

import java.sql.Date;

/**
 * Clase que genera las propiedades comunes para las clases Libro y Artículo.
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
	 * Método constructor con las variables que heredarán las subclases Libro y Artículo, a excepción del atributo "Tipo".
	 * @param iSBN Int que recoge el núnero de serie (Identificador numérico único) de una obra escrita (sea física o digital).
	 * @param abstracto String que recoge el resumen de una Obra (su extension es considerable).
	 * @param autor String que recoge el nombre y apellidos del autor de la obra.
	 * @param titulo String que recoge el título de la obra.
	 * @param fecha_publicacion Int que recoge la fecha de publicación original de la obra.
	 * @param valoracion_global Int que se inicializa a 0, porque depende de las valoraciones que hagan los usuarios en sus comentarios (@see Comentario)
	 * @param valoraciones Int que se inicializa en 0, porque depende de las valoraciones que hagan los usurios en sus comentarios (@see Comentario) 
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
	 * Método constructor con todas las variables que heredarán las subclases Libro y Artículo.
	* @param iSBN Int que recoge el núnero de serie (Identificador numérico único) de una obra escrita (sea física o digital).
	 * @param abstracto String que recoge el resumen de una Obra (su extension es considerable).
	 * @param autor String que recoge el nombre y apellidos del autor de la obra.
	 * @param titulo String que recoge el título de la obra.
	 * @param tipo String que recoge de qué tipo de obra se trata (Libro o Articulo).
	 * @param fecha_publicacion Int que recoge la fecha de publicación original de la obra.
	 * @param valoracion_global Int que se inicializa a 0, porque depende de las valoraciones que hagan los usuarios en sus comentarios (@see Comentario)
	 * @param valoraciones Int que se inicializa en 0, porque depende de las valoraciones que hagan los usurios en sus comentarios (@see Comentario) 
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
	
	public long getISBN() {
		return ISBN;
	}

	public void setISBN(long iSBN) {
		this.ISBN = iSBN;
	}

	public String getAbstracto() {
		return Abstracto;
	}

	public void setAbstracto(String abstracto) {
		this.Abstracto = abstracto;
	}

	public String getAutor() {
		return Autor;
	}

	public void setAutor(String autor) {
		this.Autor = autor;
	}

	public String getTitulo() {
		return Titulo;
	}

	public void setTitulo(String titulo) {
		this.Titulo = titulo;
	}
	
	public String getTipo() {
		return Tipo;
	}
	
	public void setTipo(String tipo) {
		Tipo = tipo;
	}
	
	public Date getFecha_publicacion() {
		return Fecha_publicacion;
	}

	public void setFecha_publicacion(Date fecha_publicacion) {
		this.Fecha_publicacion = fecha_publicacion;
	}
	
	public int getValoracion_global() {
		return Valoracion_global;
	}
	public void setValoracion_global(int valoracion_global) {
		Valoracion_global = valoracion_global;
	}
	public int getValoraciones() {
		return Valoraciones;
	}
	public void setValoraciones(int valoraciones) {
		Valoraciones = valoraciones;
	}
	
}
