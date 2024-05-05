package modelo;

import java.sql.Date;
import java.sql.SQLException;

import com.google.gson.Gson;

import DAO.DaoLibro;

/**
 * Esta Clase Recoge los datos para generar un objeto Libro a partir de la clase 
 * Usuario (extiende dicha clase)
 * @author Alejandro Moreno
 * @version 1.0
 */
public class Libro extends Obra{
	
	private String Editorial="";
	private String Categoria="";
	/**
	 * Método constructor vacío.
	 */
	public Libro() {
		super();
	}
	/**
	 * Método constructor con todos los atributos de la clase Obra y los atributos de la clase Libro, 
	 * nótese que el atributo valoracion_global se inicializa siempre a 0.
	 * @param iSBN Int que recoge el núnero de serie (Identificador numérico único) de un Libro.
	 * @param abstracto String que recoge el resumen de un Libro (su extension es considerable).
	 * @param autor String que recoge el nombre y apellidos del autor del Libro.
	 * @param titulo String que recoge el título del Libro.
	 * @param fecha_publicacion Int que recoge la fecha de publicación original del Libro.
	 * @param valoracion_global Int que se inicializa a 0 y se modifica al crear un Comentario de esa obra.
	 * @param editorial String que recoge el nombre de la editorial a la que pertenece el Libro.
	 * @param categoria String que recoge la/s categoría/s a la/s que pertenece un Libro concreto.
	 */
	public Libro(long iSBN, String abstracto, String autor, String titulo, String tipo, Date fecha_publicacion, String editorial, String categoria) {
		super( iSBN, abstracto, autor, titulo, tipo, fecha_publicacion);
		this.Editorial=editorial;
		this.Categoria=categoria;
	}
	
	public String getEditorial() {
		return Editorial;
	}
	
	public void setEditorial(String editorial) {
		Editorial = editorial;
	}
	public String getCategoria() {
		return Categoria;
	}
	public void setCategoria(String categoria) {
		Categoria = categoria;
	}
	/**
	 * Método que permite insertar un nuevo Libro llamando al método constructor de la clase correspondiente (y al DAO).
	 * @return insertar Boleano que recoge si la inserción del Libro en la Base de datos se ha realizado con éxito, 
	 * 		   de ser así, devolverá true, en caso contrario, devolverá false.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public boolean comprobarLibro(long Id) throws ClassNotFoundException, SQLException {
		boolean cell=false;
		DaoLibro dao=new DaoLibro();
		cell=dao.comprobarIdLibro(Id);
		return cell;
	}
	//FALTA COMENTAR!!!!!!!!
	public void insertarLibro() throws ClassNotFoundException, SQLException {
		
			DaoLibro dao=new DaoLibro();
			dao.insertarLibro(this);
			
	}
	
	/**
	 * Método que permite modificar un Libro preexistente en la base de datos llamando al método ---- del DAO. 
	 * @return modif Boleano que recoge si la modificacion del Libro se ha realizado con éxito, si así ha sido, devolverá true, 
	 * 		   en caso de que no se haya podido modificar, sea por la razón que fuere, devolverá false.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public void modificarLibro(Libro l1, long IdOrigen) throws ClassNotFoundException, SQLException {

		System.out.println("Estoy en la clase libro, procedo a llamar a DAOLibro");
		//Modificamos el libro llamando al Dao
		DaoLibro aux =new DaoLibro();
		aux.modificarLibro(l1, IdOrigen);
		System.out.println("Salgo de la clase Libro, vuelvo a GestionLibroModificar");
		
	}
	//FALTA COMENTAR !!!!!
	public void obtenerPorId(long id) throws SQLException, ClassNotFoundException {
		
		DaoLibro dao = new DaoLibro();
		Libro aux = dao.listarLibroPorId(id);
		
		this.setISBN(aux.getISBN());
		this.setAutor(aux.getAutor());
		this.setAbstracto(aux.getAbstracto());
		this.setTitulo(aux.getTitulo());
		this.setEditorial(aux.getEditorial());
		this.setCategoria(aux.getCategoria());
		this.setFecha_publicacion(aux.getFecha_publicacion());
		this.setTipo("Libro");
	}
	
	/**
	 * Método que permite eliminar un libro preexistente en la base de datos llamando al método ----- del DAO. 
	 * @return delete Boleano que recoge si el borrado del Libro se ha realizado con éxito, de ser así, devolverá true, 
	 * 		   en caso contrario (sea por la razón que fuere) devolverá false. 
	 */
	public boolean borrarLibro() {
		boolean delete=false;
		
		return delete;
	}
	//FALTA COMENTAR!!!!!
	public String dameJson() {
		String json = "";
		
		Gson gson = new Gson();
		
		json = gson.toJson(this);
		return json;
	}
	
	/**
	 * Método toString() que muestra por pantalla toda la información de un objeto Libro en caso de que fuera necesario.
	 */
	@Override
	public String toString() {
		return "Libro [ISBN=" + ISBN + "\n Abstracto=" + Abstracto + "\n Autor=" + Autor + "\n Titulo=" + Titulo
				+ "\n Fecha_publicacion=" + Fecha_publicacion + "\n Editorial=" + Editorial + "\n Categoria=" + Categoria 
				+ "\n Valoracion_global=" + Valoracion_global + "]";
	}
	
	
	
	
}
