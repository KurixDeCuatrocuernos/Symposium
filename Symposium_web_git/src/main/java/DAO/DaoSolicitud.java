package DAO;

import java.lang.reflect.InaccessibleObjectException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import modelo.LocalDateAdapter;
import modelo.LocalDateTimeAdapter;
import modelo.SolicitudAscenso;

public class DaoSolicitud {
	Connection con=null;
	
	public DaoSolicitud() throws SQLException, ClassNotFoundException {
		
		this.con= DBConexion.conectar();
	
	}
	
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
	
	public void borrarSolicitud(long id) throws SQLException {
		System.out.println("Estoy en DaoSolicitud --> borrarSolicitud()");
		if (con!=null) {
			String sql="DELETE FROM symposium.solicitudes WHERE ID=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setLong(1, id);
			int filas=ps.executeUpdate();
			
			con.close();
			System.out.println("Se ha cerrado la conexión");
		} else {
			System.out.println("Error al conectar, antes conecta con DBConexion");
		}
	}
	
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
	
	public ArrayList<SolicitudAscenso> listarSolicitudes(long id) throws SQLException{
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
	
public String listarJson(long id) throws SQLException {
		
		String json = "";	
		//Genero el gson con el adaptador para la clase LocalDate.
		Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
		
		try {
			json = gson.toJson(this.listarSolicitudes(id));
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
