package modelo;

import java.sql.Date;
import java.sql.SQLException;

import DAO.DaoArticulo;
import DAO.DaoLibro;

/**
 * Esta Clase genera objetos Articulo mediante los atributos de la clase Obra (que extiende).
 * @author Alejandro Moreno
 * @version 1.0
 */
public class Articulo extends Obra{
	private String Lugar_publicacion="";
	private String Volumen_publicacion="";
	private String Temas="";
	/**
	 * Métoo constructor vacío
	 */
	public Articulo() {
		super();
	}
	/**
	 * Método Constructor con todos los atributos de la clase Obra y Artículo.
	 * @param iSBN Int que recoge el núnero de serie (Identificador numérico único) de una obra escrita (sea física o digital).
	 * @param abstracto String que recoge el resumen de una Obra (su extension es considerable).
	 * @param autor String que recoge el nombre y apellidos del autor de la obra.
	 * @param titulo String que recoge el título de la obra.
	 * @param fecha_publicacion Int que recoge la fecha de publicación original de la obra.
	 * @param valoracion_global Int que se inicializa a 0, porque depende de las valoraciones que hagan los usuarios en sus comentarios (@see Comentario)
	 * @param valoraciones Int que se inicializa en 0, porque depende de las valoraciones que hagan los usurios en sus comentarios (@see Comentario). 
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
	 * @param iSBN Int que recoge el núnero de serie (Identificador numérico único) de una obra escrita (sea física o digital).
	 * @param abstracto String que recoge el resumen de una Obra (su extension es considerable).
	 * @param autor String que recoge el nombre y apellidos del autor de la obra.
	 * @param titulo String que recoge el título de la obra.
	 * @param fecha_publicacion Int que recoge la fecha de publicación original de la obra.
	 * @param valoracion_global Int que se inicializa a 0, porque depende de las valoraciones que hagan los usuarios en sus comentarios (@see Comentario)
	 * @param valoraciones Int que se inicializa en 0, porque depende de las valoraciones que hagan los usurios en sus comentarios (@see Comentario). 
	 * @param lugarPublicacion String que recoge el lugar donde se publicó el artículo (v.g. revista, universidad, dialnet, o lo que fuere).
	 * @param temas String que recoge los temas principales o ideas clave de un artículo si las hubiera.
	 */
	public Articulo(long iSBN, String abstracto, String autor, String titulo, String tipo, Date fecha_publicacion,
					String lugar_publicacion, String temas) {
		super(iSBN, abstracto, autor, titulo, tipo, fecha_publicacion);
		this.Lugar_publicacion=lugar_publicacion;
		this.Temas=temas;
	}
	
	public String getLugar_publicacion() {
		return Lugar_publicacion;
	}

	public void setLugar_publicacion(String lugar_publicacion) {
		Lugar_publicacion = lugar_publicacion;
	}

	public String getVolumen_publicacion() {
		return Volumen_publicacion;
	}

	public void setVolumen_publicacion(String volumen_publicacion) {
		Volumen_publicacion = volumen_publicacion;
	}

	public String getTemas() {
		return Temas;
	}

	public void setTemas(String temas) {
		Temas = temas;
	}
	
	/**
	 * Método que permite insertar un nuevo Articulo en la Base de datos, llamando al método ----- del DAO).
	 * @return insertar Boleano que recoge si la inserción se ha realizado con éxito, de ser así, devolverá true, 
	 * 		   en caso contrario, devolverá false.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
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
	 * Método que permite modificar un Articulo preexistente en la base de datos llamando al método ---- del DAO. 
	 * @return modif Boleano que recoge si la modificacion del Libro se ha realizado con éxito, si así ha sido, devolverá true, 
	 * 		   en caso de que no se haya podido modificar, sea por la razón que fuere, devolverá false.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public void modificarArticulo(Articulo a, long id) throws ClassNotFoundException, SQLException {
		System.out.println("Estoy en la clase Articulo, procedo a llamar a DAOArticulo");
		//Modificamos el libro llamando al Dao
		DaoArticulo aux =new DaoArticulo();
		aux.modificarArticulo(a, id);
		System.out.println("Salgo de la clase Articulo, vuelvo a GestionArticuloModificar");
	}
	
	/**
	 * Método que permite eliminar un Articulo preexistente en la base de datos llamando al método ----- del DAO. 
	 * @return delete Boleano que recoge si el borrado se ha realizado con éxito, de ser así, devolverá true, 
	 * 		   en caso contrario (sea por la razón que fuere) devolverá false. 
	 */
	public boolean eliminarArticulo() {
		boolean delete=false;
		
		return delete;
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
