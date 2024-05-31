package DAO;

import java.lang.reflect.InaccessibleObjectException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import modelo.LocalDateAdapter;
import modelo.SolicitudAscenso;
/**
 * Esta clase sirve para el acceso a la DB de cara al objeto SolicitudAscenso, ya sea insertarlo, modificarlo, listarlo, o borrarlo.
 * @see SolicitudAscenso
 * @author Alejandro Moreno
 * @version 1.0
 */
public class DaoSolicitud {
	
	Connection con=null;
	
	/**
	 * Método constructor de la clase, en caso de que el parámetro con sea null iniciará el método conectar() de la clase DBConexion.
	 * @see DBonexion
	 * @throws ClassNotFoundException si no encuentra la clase DaoComentario lanzará un error.
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error.
	 */

	public DaoSolicitud() throws SQLException, ClassNotFoundException {
		
		if (this.con==null) {
			this.con= DBConexion.conectar();
		}
		
	}
	
	/**
	 * Método que sirve para insertar una nueva Solicitud en la DB.
	 * Para ello, toma una SolicitudAscenso que contiene los datos que se insertarán.
	 * El proceso se realiza mediante un PreparedStatement. 
	 * @param s SolicitudAscenso que recoge los datos que se insertarán en la DB.
	 * @return devuelve false si no ha podido insertar la Solicitud, o True si ha podido. 
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error.
	 */
	public boolean insertarSolicitud(SolicitudAscenso s) throws SQLException {
		System.out.println("Estoy en DaoSolicitud --> insertarSolicitud()");
		boolean cell=true;
		if (con!=null) {
			Date fecha = null;
			try {
				fecha = Date.valueOf(s.getFecha_titulo());
			} catch (NumberFormatException numEx) {
				cell=false;
				System.out.println("No se ha podido convertir la fecha a formato Date de SQL");
			}
			if (fecha!=null) {
				String sql="INSERT INTO symposium.solicitudes (ID, Titulo_estudios, Lugar_Titulo, Fecha_titulo) VALUES (?,?,?,?)";
				try {
				PreparedStatement ps=con.prepareStatement(sql);
				ps.setLong(1, s.getIdUsuario());
				ps.setString(2, s.getTitulo_estudios());
				ps.setString(3, s.getLugar_estudios());
				ps.setDate(4, fecha);
				int filas=ps.executeUpdate();
				System.out.println(filas);
				} catch (SQLException sqlEx) {
					cell=false;
				}
			}
			
			con.close();
			System.out.println("conexión con la base de datos cerrada");
		} else {
			cell=false;
			System.out.println("ERROR, conecta antes con la base de datos y asegurate de haber introducido correctamente los datos");
		}
		return cell;
	}
	
	/**
	 * Método que sirve para verificar si hay o no solicitudes en la DB.
	 * El proceso se realiza mediante un PreparedStatement. 
	 * @return devuelve false si no hay solicitudes en la DB o true si hay, como mínimo 1.
	 * @throws SQLException, si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error.
	 */
	public boolean buscarSolicitudes() throws SQLException {
		boolean cell=false;
		System.out.println("Estoy en DaoSolicitud --> buscarSolicitudes()");
		String sql = "SELECT * FROM symposium.solicitudes";
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs= ps.executeQuery();
		while (rs.next()) {
			cell=true;
		}
		System.out.println(cell);
		return cell;
	}
	
	/**
	 * Método que sirve para borrar una solicitud preexistente en la DB.
	 * Para ello, toma un long que recoge la ID de la solicitud y la usa para eliminar dicha solicitud.
	 * El proceso se realiza mediante un PreparedStatement. 
	 * @param id, long que recoge la ID de la solicitud que se eliminará.
	 * @throws SQLException, si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error.
	 */
	public void borrarSolicitud(long id) throws SQLException {
		System.out.println("Estoy en DaoSolicitud --> borrarSolicitud()");
		if (con!=null) {
			String sql="DELETE FROM symposium.solicitudes WHERE ID=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setLong(1, id);
			int filas=ps.executeUpdate();
			System.out.println(filas);
			con.close();
			System.out.println("Se ha cerrado la conexión");
		} else {
			System.out.println("Error al conectar, antes conecta con DBConexion");
		}
	}
	
	/**
	 * Método que sirve para aceptar una solicitud de ascenso, es decir, que recoge la información de dicha solicitud y la aplica al usuario que solicita el ascenso.
	 * Para ello, toma un long que recoge la ID de la solicitud que sirve para buscar la solicitud en cuestión en la DB. 
	 * El proceso se realiza mediante un PreparedStatement. 
	 * @param id long que recoge la ID de la solicitud que se procederá a aceptar.
	 * @return devuelve una SolicitudAscenso que recoge los datos que se insertarán en el Estudiante que solicita el ascenso.
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error.
	 */
	public SolicitudAscenso aceptarSolicitud(long id) throws SQLException {
		SolicitudAscenso s=new SolicitudAscenso();
		System.out.println("Estoy en DaoSolicitud --> aceptarSolicitud()");
		if (con!= null) {
			String sql="SELECT ID, Titulo_estudios, Lugar_titulo, Fecha_titulo FROM symposium.solicitudes WHERE ID=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setLong(1, id);
			ResultSet rs= ps.executeQuery();
			while(rs.next()) {
				Date fechaOrigen=rs.getDate("Fecha_titulo");
				LocalDate Fecha= fechaOrigen.toLocalDate();
				s.setIdUsuario(rs.getLong("ID"));
				s.setTitulo_estudios(rs.getString("Titulo_estudios"));
				s.setLugar_estudios("Lugar_titulo");
				s.setFecha_titulo(Fecha);
				System.out.println("Se ha recogido toda la información con éxito");
			}
			con.close();
			System.out.println("Se ha cerrado la conexión");
		} else {
			System.out.println("Error al conectar, antes conecta con DBConexion");
		}
		return s;
	}
	
	/**
	 * Método que sirve para listar todas las solicitudes que haya insertadas en la DB.
	 * El proceso se realiza mediante un PreparedStatement. 
	 * @return devuelve un ArrayList que contiene las SolicitudAscenso (que contienen los valores) que se tomas de la DB.
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error.
	 */
	public ArrayList<SolicitudAscenso> listarSolicitudes() throws SQLException{
		System.out.println("Estoy en DaoSolicitud --> listarSolicitudes()");
		ArrayList<SolicitudAscenso> solicitudes= new ArrayList<SolicitudAscenso>();
		
		if (con!=null) {
			String sql="SELECT DISTINCT ID, Titulo_estudios, Lugar_titulo, Fecha_Titulo  FROM symposium.solicitudes";
			PreparedStatement ps= con.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				
				Date fechaOrigen = rs.getDate("Fecha_titulo");
		        // Convierto de Instant a LocalDate
		        LocalDate fecha = fechaOrigen.toLocalDate();
				solicitudes.add(new SolicitudAscenso(rs.getLong("ID"), rs.getString("Titulo_estudios"), rs.getString("Lugar_Titulo"), fecha));
			}
			con.close();
			System.out.println("Se ha cerrado la conexión");
		} else {
			System.out.println("Error al conectar, antes conecta con DBConexion");
		}
		
		return solicitudes;
	}
	
	/**
	 * Método que sirve para convertir a Json el rersultado del método: listarSolicitudes() de la clase DaoSolicitud.
	 * El proceso se realiza mediante un PreparedStatement. 
	 * @return json devuelve la conversión a Json del resultado del método: listarSolicitudes().
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error.
	 */
	public String listarJson() throws SQLException {
		
		String json = "";	
		// Inicializo el Gson con el Adaptador para la clase LocalDate que está en el modelo (básicamente, esto le permite a gson leer los parámetros de dicha clase).
		Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
		
		try {
			json = gson.toJson(this.listarSolicitudes());
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
