package DAO;

import java.lang.reflect.InaccessibleObjectException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import modelo.Comentario;
import modelo.LocalDateTimeAdapter;
/**
 * Esta clase sirve para el acceso a la DB de cara a las valoraciones (presente en las clases Comentario y Obra), ya sea insertarlo, modificarlo, listarlo, o borrarlo.
 * En este caso, se trata de un objeto presente en la DB, pero no en el modelo (al menos no por sí mismo).
 * @see Obra
 * @see Comentario
 * @author Alejandro Moreno
 * @version 1.0
 */
public class DaoValoracion {
	
	Connection con=null;
	/**
	 * Método constructor de la clase, en caso de que el parámetro con sea null iniciará el método conectar() de la clase DBConexion.
	 * @see DBonexion
	 * @throws ClassNotFoundException si no encuentra la clase DaoComentario lanzará un error.
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error.
	 */
	public DaoValoracion() throws SQLException, ClassNotFoundException {
		
		this.con = DBConexion.conectar();
		
	}
	
	/**
	 * Método que sirve para insertar una nueva valoración en la DB.
	 * Para ello, toma un Comentario del que tomará los valores que se insertarán. 
	 * @param C Comentario que contiene los valores que se insertarán.
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error.
	 */
	public void insertarValoracion(Comentario C) throws SQLException {
		System.out.println("Estoy en DaoValoraciones --> insertarValoracion()");
		if (con!=null) {
			Timestamp fechaF = new Timestamp(0);
			if (C.getFecha_comentario()!=null) {
				fechaF= Timestamp.valueOf(C.getFecha_comentario());
				System.out.println(fechaF);
			}
			String sql ="INSERT INTO symposium.valoraciones (ID,ISBN,Fecha_valoracion,Valor) VALUES(?,?,?,?)";
			PreparedStatement ps= con.prepareStatement(sql);
			ps.setLong(1, C.getIdAutorComentario());
			ps.setLong(2, C.getISBN_obra());
			ps.setTimestamp(3, fechaF);
			ps.setInt(4, C.getValoracion_obra());
			
			int filas=ps.executeUpdate();
			System.out.println(filas);
			System.out.println("Se ha ejecutado el Statement para valoracion correctamente");
			con.close();
			System.out.println("Se ha cerrado la conexión");
			
		} else {
			System.out.println("Error al conectar, antes conecta con DBConexion");
		}
	}
	
	/**
	 * Método que sirve para borrar una Valoración preexistente en la DB.
	 * Para ello, toma un long que recoge la id del Usuario que ha generado esa valoración y otro long que recoge la ISBN/ISSN de la obra a la que pertenece la valoración.
	 * El proceso se realiza mediante un PreparedStatement.  
	 * @param id long que recoge la Id del Usuario que ha generado la valoración en cuestión.
	 * @param isbn long que recoge la ISBN/ISSN de la Obra a la que pertenece la Valoración en cuestión. 
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error.
	 */
	public void borrarValoracion(long id, long isbn) throws SQLException {
		System.out.println("Estoy en DaoValoraciones --> borrarComentario()");
		if (con != null) {
			String sql = "DELETE FROM symposium.valoraciones WHERE ID=? AND ISBN=?";
			PreparedStatement ps=con.prepareStatement(sql);
			ps=con.prepareStatement(sql);
			ps.setLong(1, id);
			ps.setLong(2, isbn);
			int filas =ps.executeUpdate();
			System.out.println("valoracion asociada al comentario borrada: "+filas);
			
			
			System.out.println("Se ha ejecutado el Statement para valoracion correctamente");
			con.close();
			System.out.println("Se ha cerrado la conexión");
		} else {
			System.out.println("Error al conectar, antes conecta con DBConexion");
		}
	}
	
	/**
	 * Método que sirve para borrar todas las valoraciones generadas por un mismo Usuario.
	 * Para ello, toma un long que recoge la Id del Usuario cuyas valoraciones se procederá a borrar.
	 * El proceso se realiza mediante un PreparedStatement.
	 * @param ide long que recoge la Id del Usuario.
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error.
	 */
	public void borrarValoracionesUser(long ide) throws SQLException {
		if(!con.isClosed()) {
			System.out.print("Borrando valoraciones del usuario...");
			String sql="DELETE FROM symposium.valoraciones WHERE ID=?";
			PreparedStatement ps= con.prepareStatement(sql);
			ps.setLong(1, ide);
			int filas=ps.executeUpdate();
			if(filas>0) {
				System.out.println("Se han borrado las valoraciones con éxito");
			} else {
				System.out.println("No había valoraciones que borrar");
			}
			con.close();
			System.out.println("Se ha cerrado la conexión");
		} else {
			System.out.println("Error al conectar, antes conecta con DBConexion");
		}
	}
	
	/**
	 * Método que sirve para listar la media de todas las Valoraciones de un mismo tipo de Usuario (ya sea Admin, Titulado o Estudiante) y una misma Obra.
	 * Para ello, toma un long que recoge la ISBN/ISSN de una Obra, un int que recoge el valor mínimo y otro int que recoge el valor máximo que servirán para determinar el tipo de Usuario.
	 * El proceso se realiza mediante un PreparedStatement.
	 * @param isbn long que recoge la ISBN/ISSN de una Obra.
	 * @param tipomin int que recoge el valor mínimo que servirá para filtrar el tipo de Usuario.
	 * @param tipomax int que recoge el valor máximo que servirá para filtrar el tipo de Usuario.
	 * @return devuelve un int con la media de las valoraciones de una misma Obra y un mismo tipo de Usuarios.
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error.
	 */
	public int listarValoracionesPorTipo(long isbn, int tipomin, int tipomax) throws SQLException {
		System.out.println("Estoy en DaoValoraciones --> listarValoracionesPorTipo()");
		int resultado=0;
		if (con!=null) {
			String sql="SELECT avg(Valor) as 'result' FROM symposium.valoraciones v WHERE v.ISBN=? and v.ID in(SELECT ID FROM symposium.usuarios WHERE Nivel>? and Nivel<?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setLong(1, isbn);
			ps.setInt(2, tipomin);
			ps.setInt(3, tipomax);
			ResultSet rs=ps.executeQuery();
			while (rs.next()) {
				System.out.println(rs.getInt("result"));
				resultado=rs.getInt("result");
			}
			System.out.println(resultado);
			System.out.println("Se ha ejecutado el Statement para valoracion correctamente");
			con.close();
			System.out.println("Se ha cerrado la conexión");
		} else {
			System.out.println("Error al conectar, antes conecta con DBConexion");
		}
		
		
		return resultado;
	}
	
	/**
	 * Método que sirve para convertir a Json el resultado del método: listarValoracionesPorTipo() de la clase DaoValoracion.
	 * Para ello, toma un long que recoge la ISBN/ISSN de una Obra, un int que recoge el valor mínimo y otro int que recoge el valor máximo que servirán para determinar el tipo de Usuario.
	 * Todos esos valores se los manda al método: listarValoracionesPorTipo().
	 * El proceso se realiza mediante un PreparedStatement.
	 * @param isbn long que recoge la ISBN/ISSN de una Obra.
	 * @param tipomin int que recoge el valor mínimo que servirá para filtrar el tipo de Usuario.
	 * @param tipomax int que recoge el valor máximo que servirá para filtrar el tipo de Usuario.
	 * @return devuelve un String que recoge la conversión a Json del resultado del Método: listarValoracionesPorTipo().
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error.
	 */
	public String listarJsonPorTipo(long isbn, int tipomin, int tipomax) throws SQLException {
			
		String json = "";	
		// Inicializo el Gson con el Adaptador para la clase LocalDateTime que está en el modelo (básicamente, esto le permite a gson leer los parámetros de dicha clase).
		Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()).create();
		
		try {
			json = gson.toJson(this.listarValoracionesPorTipo(isbn, tipomin, tipomax));
		} catch (InaccessibleObjectException ex) {
			System.out.println("NO SE HA PODIDO CONVERTIR A Json");
		}
		
		if (con!=null) {
			con.close();
			System.out.println("Se ha cerrado la conexión");
		}
			
		return json;
		
	}
	
	/**
	 * Método que sirve para convertir a Json el resultado del método: listarValoracionesUser() de la clase DaoValoracion.
	 * Para ello, toma un long que recoge la Id del Usuario cuyas valoraciones se listarán en el método: listarValoracionesUser() y que se mandarán a dicho método.
	 * @param id long que recoge la Id del Usuario.
	 * @return devuelve un String que recoge la conversión a Json del resultado del métod: listarValoracionesUser().
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error.
	 * @deprecated Queda hecho, pero falta crear el método: listarValoracionesUser().
	 */
	public String listarJsonUser(long id) throws SQLException {
			
			String json = "";	
			// Inicializo el Gson con el Adaptador para la clase LocalDateTime que está en el modelo (básicamente, esto le permite a gson leer los parámetros de dicha clase).
			//Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()).create();
			try {
				// falta el método listarValoracionesUser
				//json = gson.toJson(this.listarValoracionesUser(id));
			} catch (InaccessibleObjectException ex) {
				System.out.println("NO SE HA PODIDO CONVERTIR A Json");
			}
			
			if (con!=null) {
				con.close();
				System.out.println("Se ha cerrado la conexión");
			}
			
			return json;
		
		}
}
