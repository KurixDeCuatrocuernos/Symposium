package modelo;

import java.sql.Date;
import java.sql.SQLException;

import com.google.gson.Gson;

import DAO.DaoLibro;

/**
 * Esta Clase Recoge los datos para generar un objeto Libro con los parámetros: Editorial y Categoria, a partir de la clase Usuario (extiende dicha clase)
 * @see Usuario
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
	 * @see Obra
	 * @param iSBN long que recoge el núnero de serie (Identificador numérico único) de un Libro.
	 * @param abstracto String que recoge el resumen de un Libro (su extension es considerable).
	 * @param autor String que recoge el nombre y apellidos del autor del Libro.
	 * @param titulo String que recoge el título del Libro.
	 * @param fecha_publicacion int que recoge la fecha de publicación original del Libro.
	 * @param valoracion_global int que se inicializa a 0 y se modifica al crear un Comentario de esa obra.
	 * @param editorial String que recoge el nombre de la editorial a la que pertenece el Libro.
	 * @param categoria String que recoge la/s categoría/s a la/s que pertenece un Libro concreto.
	 */
	public Libro(long iSBN, String abstracto, String autor, String titulo, String tipo, Date fecha_publicacion, String editorial, String categoria) {
		super( iSBN, abstracto, autor, titulo, tipo, fecha_publicacion);
		this.Editorial=editorial;
		this.Categoria=categoria;
	}
	/**
	 * Método Get del parámetro Editorial, como todos los métodos Get, devuelve el valor del parámetro en cuestión
	 * @return devuelave el valor del parámetro Editorial.
	 */
	public String getEditorial() {
		return Editorial;
	}
	/**
	 * Método Set del parámetro Editorial, como todos los métodos set, establece el valor del parámetro en cuestión, en este caso, editorial.
	 * @param editorial String que recoge el parámetro que se establecerá sobre Editorial.
	 */
	public void setEditorial(String editorial) {
		Editorial = editorial;
	}
	/**
	 * @see getEditorial()
	 */
	public String getCategoria() {
		return Categoria;
	}
	/**
	 * @see setEditorial()
	 */
	public void setCategoria(String categoria) {
		Categoria = categoria;
	}
	/**
	 * Método que permite comprobar si un Libro está o no en la DB, para ello llama al método comprobarIdLibro() de la clase DaoLibro.
	 * @see DaoLibro
	 * @param Id long que recoge el valor que se usará en el método comprobarIdLibro().
	 * @return insertar boleano que recoge si se ha encontrado la id de ese libro en la DB, de ser así, devolverá true, en caso contrario, devolverá false.
	 * @throws ClassNotFoundException si no encuentra la clase DaoComentario lanzará un error.
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error. 
	 */
	public boolean comprobarLibro(long Id) throws ClassNotFoundException, SQLException {
		boolean cell=false;
		DaoLibro dao=new DaoLibro();
		cell=dao.comprobarIdLibro(Id);
		return cell;
	}
	/**
	 * Método para insertar un Libro nuevo en la DB, en este caso manda este mismo objeto al método insertarLibro() de la clase DaoLibro.
	 * @see DaoLibro
	 * @throws ClassNotFoundException si no encuentra la clase DaoComentario lanzará un error.
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error. 
	 */
	public void insertarLibro() throws ClassNotFoundException, SQLException {
		
			DaoLibro dao=new DaoLibro();
			dao.insertarLibro(this);
			
	}
	
	/**
	 * Método que permite modificar un Libro preexistente en la DB llamando al método modificarLibro() de la clase DaoLibro. 
	 * @see DaoLibro
	 * @param l1 Libro que recoge los valores que se usarán para modificar el objeto preexistente en la DB.
	 * @param IdOrigen long que se usará para buscar el objeto que se modificará en la DB.
	 * @return modif boleano que recoge si la modificacion del Libro se ha realizado con éxito, si así ha sido, devolverá true, 
	 * 		   en caso de que no se haya podido modificar, devolverá false.
	 * @throws ClassNotFoundException si no encuentra la clase DaoComentario lanzará un error.
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error. 
	 */
	public void modificarLibro(Libro l1, long IdOrigen) throws ClassNotFoundException, SQLException {

		System.out.println("Estoy en la clase libro, procedo a llamar a DAOLibro");
		//Modificamos el libro llamando al Dao
		DaoLibro aux =new DaoLibro();
		aux.modificarLibro(l1, IdOrigen);
		System.out.println("Salgo de la clase Libro, vuelvo a GestionLibroModificar");
		
	}
	/**
	 * Método que recoge los valores de un Libro por su Id, para ello llama al método listarLibroPorId() de la clase DaoLibro.
	 * @see DaoLibro
	 * @param id long que recoge la isbn/issn que se usará para buscar el libro en la DB.
	 * @throws ClassNotFoundException si no encuentra la clase DaoComentario lanzará un error.
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error.
	 * @deprecated Antes de implementar el buscador asíncrono se usaba, pero ya no. 
	 */
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
	 * Método que transforma los valores de esta clase (obtenidos por el método: obtenerPorId()) a Json.
	 * @see obtenerPorId()  
	 * @return devuelve la transformación a Json de los valores de esta clase (Libro).
	 */
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
