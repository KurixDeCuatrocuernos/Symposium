package DAO;

import java.lang.reflect.InaccessibleObjectException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import modelo.Comentario;
import modelo.LocalDateTimeAdapter;

public class DaoComentario {
	
	Connection con=null;
	
	public DaoComentario() throws SQLException, ClassNotFoundException {

		this.con = DBConexion.conectar();
		
	}
	
	public ArrayList<Comentario> listarComentariosPorTiempo(int tipomin, int tipomax) throws SQLException {
		ArrayList<Comentario> comentarios=new ArrayList<Comentario>();
		System.out.println("Estoy en DaoComentario --> listarComentariosPorTipo()");
		if (con!=null) {
			String sql="SELECT c.Titulo as 'TituloC', c.Texto as 'TextoC', c.Fecha_comentario as 'Fecha', u.Nombre as 'Alias', u.Nivel as 'Level', o.Titulo as'TituloO', o.Autor as'AutorO' FROM symposium.comentarios c, symposium.obras o, symposium.usuarios u WHERE c.ISBN=o.ISBN and c.ID=u.ID and u.Nivel>? and u.Nivel<? order by Fecha_comentario";
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setInt(1, tipomin);
			ps.setInt(2, tipomax);
			ResultSet rs =ps.executeQuery();
			Comentario c;
			LocalDateTime fecha=LocalDateTime.now();
			int lim=0;
			while(rs.next()&& lim<1) {
				c=new Comentario();
				try {
					fecha= rs.getTimestamp("Fecha").toLocalDateTime();
				} catch(NumberFormatException NFex) {
					NFex.printStackTrace();
				} catch (SQLException exSQL) {
					exSQL.printStackTrace();
				}
				
				try {
					c.setAlias(rs.getString("Alias"));
					c.setTituloObra(rs.getString("TituloO"));
					c.setAutorObra(rs.getString("AutorO"));
					c.setTitulo(rs.getString("TituloC"));
					c.setTexto(rs.getString("TextoC"));
					c.setFecha_comentario(fecha);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				comentarios.add(c);
				lim++;
			}
			con.close();
			System.out.println("Se ha cerrado la conexión");
		} else {
			System.out.println("Error al conectar, antes conecta con DBConexion");
		}
		return comentarios;
	}
	
	public ArrayList<Comentario> listarComentariosPorTipo(int tipomin, int tipomax) throws SQLException{
		ArrayList<Comentario> comentarios=new ArrayList<Comentario>();
		System.out.println("Estoy en DaoComentario --> listarComentariosPorTipo()");
		if (con!=null) {
			String sql="SELECT c.Titulo as 'TituloC', c.Texto as 'TextoC', c.Fecha_comentario as 'Fecha', u.Nombre as 'Alias', u.Nivel as 'Level', o.Titulo as'TituloO', o.Autor as'AutorO' FROM symposium.comentarios c, symposium.obras o, symposium.usuarios u WHERE c.ISBN=o.ISBN and c.ID=u.ID and u.Nivel>? and u.Nivel<?";
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setInt(1, tipomin);
			ps.setInt(2, tipomax);
			ResultSet rs =ps.executeQuery();
			Comentario c;
			LocalDateTime fecha=LocalDateTime.now();
			while(rs.next()) {
				c=new Comentario();
				try {
					fecha= rs.getTimestamp("Fecha").toLocalDateTime();
				} catch(NumberFormatException NFex) {
					NFex.printStackTrace();
				} catch (SQLException exSQL) {
					exSQL.printStackTrace();
				}
				
				try {
					c.setAlias(rs.getString("Alias"));
					c.setTituloObra(rs.getString("TituloO"));
					c.setAutorObra(rs.getString("AutorO"));
					c.setTitulo(rs.getString("TituloC"));
					c.setTexto(rs.getString("TextoC"));
					c.setFecha_comentario(fecha);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				comentarios.add(c);
			}
			con.close();
			System.out.println("Se ha cerrado la conexión");
		} else {
			System.out.println("Error al conectar, antes conecta con DBConexion");
		}
		return comentarios;
	}
	
	public void modificarComentario(Comentario c) throws SQLException {
		System.out.println("Estoy en DaoComentario --> modificarComentario()");
		if (con!=null) {
			Timestamp fechaF = new Timestamp(0);
			if (c.getFecha_comentario()!=null) {
				fechaF= Timestamp.valueOf(c.getFecha_comentario());
				System.out.println(fechaF);
			}
			try {
				String sql="UPDATE symposium.comentarios SET Fecha_comentario=?, Texto=?, Titulo=? WHERE ID=? AND ISBN=?";
				PreparedStatement ps=con.prepareStatement(sql);
				ps.setTimestamp(1,fechaF);
				ps.setString(2, c.getTexto());
				ps.setString(3, c.getTitulo());
				ps.setLong(4, c.getIdAutorComentario());
				ps.setLong(5, c.getISBN_obra());
				int rs=ps.executeUpdate();
				if (rs>1) {
					System.out.println("Update Comentario ejecutado con exito");
				}
			} catch(SQLException ex) {
				ex.printStackTrace();
			}
			
			try {
				String sql="UPDATE symposium.valoraciones SET Fecha_valoracion=?, Valor=? WHERE ID=? AND ISBN=?";
				PreparedStatement ps=con.prepareStatement(sql);
				ps.setTimestamp(1,fechaF);
				ps.setInt(2, c.getValoracion_obra());
				ps.setLong(3, c.getIdAutorComentario());
				ps.setLong(4, c.getISBN_obra());
				int rs=ps.executeUpdate();
				if (rs>1) {
					System.out.println("Update Valoracion ejecutado con exito");
				}
			} catch (SQLException ex2) {
				ex2.printStackTrace();
			}
			
			con.close();
			System.out.println("Se ha cerrado la conexión");
		} else {
			System.out.println("Error al conectar, antes conecta con DBConexion");
		}
	}
	
	public boolean comprobarComentario(long id, long isbn) throws SQLException {
		System.out.println("Comprobando Comentario...");
		boolean exis =true;
		if (con!=null) {
			String sql="SELECT ID, ISBN FROM symposium.comentarios WHERE ID=? AND ISBN=?";
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setLong(1,id);
			ps.setLong(2, isbn);
			ResultSet rs= ps.executeQuery();
			if(rs.next()) {
					exis=false;
					System.out.println("Hay un resultado");
			}
			
			System.out.println("Se ha ejecutado el Statement para valoracion correctamente");
			con.close();
			System.out.println("Se ha cerrado la conexión");
			
		} else {
			System.out.println("Error al conectar, antes conecta con DBConexion");
		}
		
		
		
		return exis;
	}
	
	public void insertarComentario(Comentario C) throws SQLException {
		
		System.out.println("Estoy en DaoComentario --> insertarComentario()");
		if (con!=null) {
			
			Timestamp fechaF = new Timestamp(0);
			if (C.getFecha_comentario()!=null) {
				fechaF= Timestamp.valueOf(C.getFecha_comentario());
				System.out.println(fechaF);
			}
			String sql ="INSERT INTO symposium.comentarios (ID,ISBN,Fecha_comentario,Texto,Titulo) VALUES(?,?,?,?,?)";
			PreparedStatement ps= con.prepareStatement(sql);
			ps.setLong(1, C.getIdAutorComentario());
			ps.setLong(2, C.getISBN_obra());
			ps.setTimestamp(3, fechaF);
			ps.setString(4, C.getTexto());
			ps.setString(5, C.getTitulo());
			
			int filas = ps.executeUpdate();
			System.out.println("Se ha ejecutado el Statement para comentario correctamente");
			con.close();
			System.out.println("Se ha cerrado la conexión");
			
		} else {
			System.out.println("Error al conectar, antes conecta con DBConexion");
		}
	}
	
	
	
	public void borrarComentario(long id, long isbn) throws SQLException {
		System.out.println("Estoy en DaoComentario --> borrarComentario()");
		if (con!= null) {
			String sql = "DELETE FROM symposium.comentarios WHERE ID=? AND ISBN=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1,"");
			ps.setLong(1, id);
			ps.setLong(2, isbn);
			int filas= ps.executeUpdate();
			System.out.println("comentario borrado");
			
			con.close();
			System.out.println("Se ha cerrado la conexión");
		} else {
			System.out.println("Error al conectar, antes conecta con DBConexion");
		}
	}
	
	public void borrarComentariosUser(long ide) throws SQLException {
		if(!con.isClosed()) {
			System.out.print("Borrando comentarios del usuario...");
			String sql="DELETE FROM symposium.comentarios WHERE ID=?";
			PreparedStatement ps= con.prepareStatement(sql);
			ps.setLong(1, ide);
			int filas=ps.executeUpdate();
			if(filas>0) {
				System.out.println("Se han borrado los comentarios con éxito");
			} else {
				System.out.println("No había comentarios que borrar");
			}
			con.close();
			System.out.println("Se ha cerrado la conexión");
		} else {
			System.out.println("Error al conectar, antes conecta con DBConexion");
		}
	}
	
	public ArrayList <Comentario> listarComentariosPorIsbn(long id, int tipo) throws SQLException{
		System.out.println("Estoy en DaoComentario --> listarComentarios()");
		ArrayList <Comentario> comentarios= new ArrayList<Comentario>();
		if (con!=null) {
			System.out.println(tipo);
			String sql = "SELECT c.ID, c.ISBN, c.Fecha_comentario, c.Texto, c.Titulo, v.valor, u.nombre FROM symposium.comentarios c, symposium.valoraciones v, symposium.usuarios u WHERE v.ISBN=c.ISBN AND c.ISBN=? AND v.ID=c.ID and c.ID=u.ID and u.ID IN (SELECT ID FROM symposium.usuarios WHERE Nivel=?);";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setLong(1, id);
			ps.setInt(2, tipo);
			ResultSet result = ps.executeQuery();

			while(result.next()) {
				//comentarios(ID numeric(10) not null,ISBN numeric(13) not null,
				//			  Fecha_comentario datetime not null,Texto text not null,
				//			  Titulo varchar(80),
				//Comentario(LocalDateTime fecha_comentario, String titulo, 
				//           String texto, int valoracion_obra, long idAutorComentario,
				//			 long iSBN_obra)
				
				Timestamp fechaOrigen = result.getTimestamp("Fecha_comentario");
				LocalDateTime fecha= fechaOrigen.toLocalDateTime();
				System.out.println(fecha);
				
				comentarios.add(new Comentario(fecha, result.getString("Titulo"), result.getString("Texto"), result.getInt("valor"), result.getLong("ID"), result.getLong("ISBN"), result.getString("nombre")));
			}
			
			con.close();
			System.out.println("Se ha cerrado la conexión");
		} else {
			System.out.println("Error al conectar, antes conecta con DBConexion");
		}
		
		return comentarios;
	}
	
	public ArrayList <Comentario> listarComentariosUser(long id) throws SQLException{
		System.out.println("Estoy en DaoComentario --> listarComentariosUser()");
		ArrayList <Comentario> comentarios=null;
		if (con!=null) {
			
			String sql = "SELECT DISTINCT c.ID, c.ISBN, c.Fecha_comentario, c.Texto, c.Titulo, v.valor FROM symposium.comentarios c, symposium.valoraciones v WHERE v.ID=c.ID and c.ID ="+id;
			PreparedStatement ps = con.prepareStatement(sql);
			
			ResultSet result = ps.executeQuery();
			
			while(result.next()) {
				
				if(comentarios == null) {
					
					comentarios = new ArrayList<Comentario>();
				}
				//comentarios(ID numeric(10) not null,ISBN numeric(13) not null,
				//			  Fecha_comentario datetime not null,Texto text not null,
				//			  Titulo varchar(80),
				//Comentario(LocalDateTime fecha_comentario, String titulo, 
				//           String texto, int valoracion_obra, long idAutorComentario,
				//			 long iSBN_obra)
				
				Timestamp fechaOrigen = result.getTimestamp("Fecha_comentario");
				LocalDateTime fecha = fechaOrigen.toLocalDateTime();
				System.out.println(fecha);
				
				Comentario c = new Comentario();
				c.setFecha_comentario(fecha);
				c.setIdAutorComentario(result.getLong("ID"));
				c.setISBN_obra(result.getLong("ISBN"));
				c.setTexto(result.getString("Texto"));
				c.setTitulo(result.getString("Titulo"));
				c.setValoracion_obra(result.getInt("valor"));
				
				comentarios.add(c);

			}
			
			con.close();
			System.out.println("Se ha cerrado la conexión");
		} else {
			System.out.println("Error al conectar, antes conecta con DBConexion");
		}
		
		return comentarios;
	}
	
	public String listarJson(long id, int tipo) throws SQLException {
		
		String json = "";	
		
		Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()).create();
		
		try {
			json = gson.toJson(this.listarComentariosPorIsbn(id, tipo));
		} catch (InaccessibleObjectException ex) {
			System.out.println("NO SE HA PODIDO CONVERTIR A Json");
		}
		
		
		if (con!=null) {
			con.close();
			System.out.println("Se ha cerrado la conexión en gson");
		}
		
		return json;
	
	}
	
	public String listarJsonUser(long id) throws SQLException {
		
		String json = "";	
		//Inicializo el Gson con el Adaptador para la clase LocalDateTime que está en el modelo
		Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()).create();
		try {
			json = gson.toJson(this.listarComentariosUser(id));
		} catch (InaccessibleObjectException ex) {
			System.out.println("NO SE HA PODIDO CONVERTIR A Json");
		}
		
		if (con!=null) {
			con.close();
			System.out.println("Se ha cerrado la conexión en gson");
		}
		
		return json;
	
	}
	
	public String listarJsonPorTiempo(int tipomin, int tipomax) throws SQLException {
		String json = "";	
		//Inicializo el Gson con el Adaptador para la clase LocalDateTime que está en el modelo
		Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()).create();
		try {
			json = gson.toJson(this.listarComentariosPorTiempo(tipomin, tipomax));
		} catch (InaccessibleObjectException ex) {
			System.out.println("NO SE HA PODIDO CONVERTIR A Json");
		}
		if (con!=null) {
			con.close();
			System.out.println("Se ha cerrado la conexión en gson");
		}
		return json;
	}
	
	public String listarJsonPorTipo(int tipomin, int tipomax) throws SQLException {
		String json = "";	
		//Inicializo el Gson con el Adaptador para la clase LocalDateTime que está en el modelo
		Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()).create();
		try {
			json = gson.toJson(this.listarComentariosPorTipo(tipomin, tipomax));
		} catch (InaccessibleObjectException ex) {
			System.out.println("NO SE HA PODIDO CONVERTIR A Json");
		}
		if (con!=null) {
			con.close();
			System.out.println("Se ha cerrado la conexión en gson");
		}
		return json;
	}
	
}
