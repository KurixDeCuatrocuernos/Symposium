package modelo;

import java.sql.Date;
import java.sql.SQLException;

import DAO.DaoArticulo;

/**
 * Esta Clase genera objetos Articulo con los parámetros: Lugar_publicacion, Volumen_publicacion, Temas y con los atributos de la clase Obra (que extiende).
 * @author Alejandro Moreno
 * @version 1.0
 * @see Obra
 */
public class Articulo extends Obra{
	private String Lugar_publicacion="";
	private String Volumen_publicacion="";
	private String Temas="";
	
	/**
	 * Método constructor vacío.
	 */
	public Articulo() {
		super();
	}
	
	/**
	 * Método Constructor con todos los atributos de la clase Obra y Artículo.
	 * @param iSBN long que recoge el número de serie (Identificador numérico único) de una obra escrita (sea física o digital).
	 * @param abstracto String que recoge el resumen de una Obra (su extensión es considerable).
	 * @param autor String que recoge el nombre y apellidos del autor de la obra.
	 * @param titulo String que recoge el título de la obra.
	 * @param fecha_publicacion int que recoge la fecha de publicación original de la obra.
	 * @param valoracion_global int que se inicializa a 0, porque depende de las valoraciones que hagan los usuarios en sus comentarios (@see Comentario)
	 * @param valoraciones int que se inicializa en 0, porque depende de las valoraciones que hagan los usurios en sus comentarios (@see Comentario). 
	 * @param lugarPublicacion String que recoge el lugar donde se publicó el artículo (v.g. revista, universidad, dialnet, o lo que fuere).
	 * @param volumenPublicacion String que recoge el lugar concreto del lugar donde se publicó el artículo (v.g. volumen, página, etc.).
	 * @param temas String que recoge los temas principales o ideas clave de un artículo si las hubiera.
	 */
	public Articulo(long iSBN, String abstracto, String autor, String titulo, String tipo, Date fecha_publicacion, 
					String lugarPublicacion, String volumenPublicacion, String temas) {
		super(iSBN, abstracto, autor, titulo, tipo, fecha_publicacion);
		this.Lugar_publicacion=lugarPublicacion;
		this.Volumen_publicacion=volumenPublicacion;
		this.Temas=temas;
	}
	
	/**
	 * Método Constructor con todos los atributos de la clase Obra y todos de la clase Artículo salvo volumen_publicacion.
	 * @param iSBN long que recoge el número de serie (Identificador numérico único) de una obra escrita (sea física o digital).
	 * @param abstracto String que recoge el resumen de una Obra (su extensión es considerable).
	 * @param autor String que recoge el nombre y apellidos del autor de la obra.
	 * @param titulo String que recoge el título de la obra.
	 * @param fecha_publicacion int que recoge la fecha de publicación original de la obra.
	 * @param valoracion_global int que se inicializa a 0, porque depende de las valoraciones que hagan los usuarios en sus comentarios (@see Comentario)
	 * @param valoraciones int que se inicializa en 0, porque depende de las valoraciones que hagan los usurios en sus comentarios (@see Comentario). 
	 * @param lugarPublicacion String que recoge el lugar donde se publicó el artículo (v.g. revista, universidad, dialnet, o lo que fuere).
	 * @param temas String que recoge los temas principales o ideas clave de un artículo si las hubiera.
	 */
	public Articulo(long iSBN, String abstracto, String autor, String titulo, String tipo, Date fecha_publicacion,
					String lugar_publicacion, String temas) {
		super(iSBN, abstracto, autor, titulo, tipo, fecha_publicacion);
		this.Lugar_publicacion=lugar_publicacion;
		this.Temas=temas;
	}
	
	/**
	 * Método Get del parámetro Lugar_publicacion de la clase Articulo. 
	 * Como todo método Get, sirve para recoger el valor del parámetro del objeto en cuestión.
	 * @return String que devuelve el valor del parámetro Lugar_publicacion.
	 */
	public String getLugar_publicacion() {
		return Lugar_publicacion;
	}
	
	/**
	 * Método Set del parámetro Lugar_publicacion de la clase Articulo.
	 * Como todo método Set, sirve para establecer el valor del parámetro de la clase en cuestión, en este caso, Lugar_publicacion. 
	 * @param lugar_publicacion String que recoge el lugar de publicación del artículo.
	 */
	public void setLugar_publicacion(String lugar_publicacion) {
		Lugar_publicacion = lugar_publicacion;
	}
	
	/**
	 * @see getLugar_publicacion().
	 */
	public String getVolumen_publicacion() {
		return Volumen_publicacion;
	}
	
	/**
	 * @see setLugar_publicacion
	 */
	public void setVolumen_publicacion(String volumen_publicacion) {
		Volumen_publicacion = volumen_publicacion;
	}
	
	/**
	 * @see getLugar_publicacion().
	 */
	public String getTemas() {
		return Temas;
	}
	
	/**
	 * @see setLugar_publicacion
	 */
	public void setTemas(String temas) {
		Temas = temas;
	}
	
	/**
	 * Método que permite insertar un nuevo Articulo en la Base de datos, llamando al método insertarArticulo() del DAO).
	 * @return Boleano que recoge si la inserción se ha realizado con éxito, de ser así, devolverá true, 
	 * 		   en caso contrario, devolverá false.
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error. 
	 * @throws ClassNotFoundException si no encuentra la clase DaoArticulo lanzará un error. 
	 */
	public void insertarArticulo(Articulo a) throws ClassNotFoundException, SQLException {
		System.out.println("Estoy en Articulo --> insertarArticulo()");
		DaoArticulo aux= new DaoArticulo();
		try {
		aux.insertarArticulo(a);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Método que permite modificar un Articulo preexistente en la base de datos llamando al método modificarArticulo() del DAO. 
	 * @return modif, Boleano que recoge si la modificacion del Libro se ha realizado con éxito, si así ha sido, devolverá true, 
	 * 		   en caso de que no se haya podido modificar, sea por la razón que fuere, devolverá false.
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error. 
	 * @throws ClassNotFoundException si no encuentra la clase DaoArticulo lanzará un error.  
	 */
	public void modificarArticulo(Articulo a, long id) throws ClassNotFoundException, SQLException {
		System.out.println("Estoy en la clase Articulo, procedo a llamar a DAOArticulo");
		//Modificamos el libro llamando al Dao
		DaoArticulo aux =new DaoArticulo();
		aux.modificarArticulo(a, id);
		System.out.println("Salgo de la clase Articulo, vuelvo a GestionArticuloModificar");
	}
	
	/**
	 * Método toString() que muestra por pantalla toda la información de un objeto Articulo en caso de que fuera necesario.
	 */
	@Override
	public String toString() {
		return "Articulo [ISBN=" + ISBN + "\n Abstracto=" + Abstracto + "\n Autor=" + Autor + "\n Titulo=" + Titulo 
				+ "\n Fecha_publicacion=" + Fecha_publicacion + "\n Lugar_publicacion=" + Lugar_publicacion 
				+ "\n Volumen_publicacion=" + Volumen_publicacion + "\n Temas=" + Temas + "\n Valoracion_global="+ Valoracion_global 
				+ "\n Tipo="+ Tipo+"]";
	}
	
}
