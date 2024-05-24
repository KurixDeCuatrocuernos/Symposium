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

public class DaoValoracion {
	
	Connection con=null;
	
	public DaoValoracion() throws SQLException, ClassNotFoundException {
		
		this.con = DBConexion.conectar();
		
	}
	
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
			
			System.out.println("Se ha ejecutado el Statement para valoracion correctamente");
			con.close();
			System.out.println("Se ha cerrado la conexión");
			
		} else {
			System.out.println("Error al conectar, antes conecta con DBConexion");
		}
	}
	
	public void borrarValoracion(long id, long isbn) throws SQLException {
		System.out.println("Estoy en DaoValoraciones --> borrarComentario()");
		if (con != null) {
			String sql = "DELETE FROM symposium.valoraciones WHERE ID=? AND ISBN=?";
			PreparedStatement ps=con.prepareStatement(sql);
			ps=con.prepareStatement(sql);
			ps.setLong(1, id);
			ps.setLong(2, isbn);
			int filas =ps.executeUpdate();
			System.out.println("valoracion asociada al comentario borrada");
			
			
			System.out.println("Se ha ejecutado el Statement para valoracion correctamente");
			con.close();
			System.out.println("Se ha cerrado la conexión");
		} else {
			System.out.println("Error al conectar, antes conecta con DBConexion");
		}
	}
	
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
	
	public String listarJsonPorTipo(long isbn, int tipomin, int tipomax) throws SQLException {
			
			String json = "";	
			
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
		
	public String listarJsonUser(long id) throws SQLException {
			
			String json = "";	
			//Gson gson = new Gson();
			Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()).create();
			try {
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
