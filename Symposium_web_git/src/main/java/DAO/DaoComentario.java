package DAO;

import java.lang.reflect.InaccessibleObjectException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import modelo.Comentario;
import modelo.LocalDateTimeAdapter;
/**
 * Esta clase sirve para el acceso a la DB de cara al objeto Comentario, ya sea insertarlo, modificarlo, listarlo, o borrarlo.
 * @see Comentario
 * @author Alejandro Moreno
 * @version 1.0
 */
public class DaoComentario {
	
	Connection con=null;
	
	/**
	 * Método constructor de la clase, en caso de que el parámetro con sea null iniciará el método conectar() de la clase DBConexion.
	 * @see DBonexion
	 * @throws ClassNotFoundException si no encuentra la clase DaoComentario lanzará un error.
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error. 
	 */
	public DaoComentario() throws SQLException, ClassNotFoundException {
		
		if (this.con==null) {
			this.con = DBConexion.conectar();
		} else {
			System.out.println("Ya estabas conectado");
		}
		
	}
	
	/**
	 * Método que sirve para listar los comentarios insertados <em>más nuevos</em> de la DB. 
	 * Para ello recoge un int máximo y otro mínimo que sirve para filtrar el tipo de usuario.
	 * El proceso se realiza mediante un PreparedStatement. 
	 * @param tipomin int que recoge el valor minimo que se filtrará. 
	 * @param tipomax int que recoge el valor máximo que se filtrará. 
	 * @return devuelve un ArrayList que contiene los comentarios (que a su vez contienen los valores) que se mostrarán.
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error. 
	 */
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
	
	/**
	 * Método que sirve para mostrar los comentarios que haya en la DB filtrando el tipo de Usuario que los ha creado a partir de un valor mínimo y máximo.
	 * El proceso se realiza mediante un PreparedStatement. 
	 * @param tipomin int que recoge el valor minimo que se filtrará. 
	 * @param tipomax int que recoge el valor máximo que se filtrará. 
	 * @return devuelve un ArrayList que contiene los comentarios (que a su vez contienen los valores) que se mostrarán.
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error.
	 */
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
	
	/**
	 * Método que sirve para modificar un comentario preexistente en la DB. 
	 * Para ello toma un objeto Comentario que contiene los valores que se modificarán.
	 * El proceso se realiza mediante un PreparedStatement. 
	 * @param c Comentario que recoge los valores que se modificarán.
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error.
	 */
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
	
	/**
	 * Método que comprueba si existe o no un comentario, es decir, si da la convergencia de la Id de un usuario y el ISBN/ISSN de una Obra en la DB.
	 * El proceso se realiza mediante un PreparedStatement. 
	 * @param id long que recoge la Id de un Usuario que se usará para buscar el comentario.
	 * @param isbn long que recoge el ISBN/ISSN de una Obra, que se usará para buscar el comentario
	 * @return devuelve un boleano en función de si se encuentra o no el comentario en la DB, si lo encuentra devolverá false, si no lo encuentra, devolverá true. 
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error.
	 */
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
	
	/**
	 * Método que sirve para insertar un comentario nuevo en la DB. 
	 * Para ello toma un objeto Comentario que contiene los valores que se insertarán en la DB.
	 * El proceso se realiza mediante un PreparedStatement.
	 * @param C Comentario que contiene los valores que se insertarán.
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error.
	 */
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
			System.out.println("Se ha ejecutado el Statement para comentario correctamente: "+filas);
			con.close();
			System.out.println("Se ha cerrado la conexión");
			
		} else {
			System.out.println("Error al conectar, antes conecta con DBConexion");
		}
	}
	
	/**
	 * Método que sirve para borrar un Comentario preexistente en la DB. 
	 * Para ello toma un long que recoge la Id del Usuario que redacta el Comentario y un long que recoge la ISBN/ISSN de la Obra que copmenta el Comentario.
	 * El proceso se realiza mediante un PreparedStatement.
	 * @param id long que recoge la id del Usuario que ha redactado el Comentario que se procederá a borrar.
	 * @param isbn long que recoge la ISBN/ISSN de la Obra que comenta el Comentario que sprocederá a borrar.
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error.
	 */
	public void borrarComentario(long id, long isbn) throws SQLException {
		System.out.println("Estoy en DaoComentario --> borrarComentario()");
		if (con!= null) {
			String sql = "DELETE FROM symposium.comentarios WHERE ID=? AND ISBN=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1,"");
			ps.setLong(1, id);
			ps.setLong(2, isbn);
			int filas= ps.executeUpdate();
			System.out.println("comentario borrado: "+filas);
			
			con.close();
			System.out.println("Se ha cerrado la conexión");
		} else {
			System.out.println("Error al conectar, antes conecta con DBConexion");
		}
	}
	
	/**
	 * Método que sirve para borrar todos los copmentarios de un mismo usuario de la DB. 
	 * Para ello, toma un long que recoge la id del usuario cuyos comentarios se eliminarán.
	 * El proceso se realiza mediante un PreparedStatement.
	 * @param ide long que recoge la Id del Usuario cuyos comentarios se procederá a borrar.
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error.
	 */
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
	
	/**
	 * Método que sirve para listar los Comentarios que pertenecen a una misma Obra y tipo de Usuario. 
	 * Para ello, toma un long que recoge la ISBN/ISSN de una Obra y un long que recoge el nivel de los Usuarios que tengan un comentario de dicha obra.
	 * El proceso se realiza mediante un PreparedStatement.
	 * @param id long que recoge la ISBN/ISSN de la Obra cuyos comentarios se listarán.
	 * @param tipo int que recoge el nivel de Usuario, cuyos comentarios se listarán
	 * @return devuelve un ArrayList que contiene los comentarios (que a su vez contienen los valores) que se mostrarán.
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error.
	 */
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
				
				// Orden y tipo de las  Columnas en DB
				// comentarios(ID numeric(10) not null,ISBN numeric(13) not null,
				//			   Fecha_comentario datetime not null,Texto text not null,
				//			   Titulo varchar(80),
				
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
	
	/**
	 * Método que sirve para listar todos los comentarios de un mismo Usuario.
	 * Para ello toma un long que recoge la Id del Usuario cuyos comentarios se listarán.
	 * El proceso se realiza mediante un PreparedStatement.
	 * @param id long que recoge la Id que se usará para buscar los comentarios en la DB.
	 * @return devuelve un ArrayList que contiene los comentarios que se hayan encontrado.
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error.
	 */
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
				
				// Orden y tipo de las  Columnas en DB
				// comentarios(ID numeric(10) not null,ISBN numeric(13) not null,
				//			   Fecha_comentario datetime not null,Texto text not null,
				//			   Titulo varchar(80),
		
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
	
	/**
	 * Método que sirve para convertir el resultado que devuelve el método listarComentariosPorISBN() de la clase DaoComentario a Json.
	 * Para ello, toma un long que recoge la ISBN/ISSN de una Obra y un long que recoge el nivel de Usuario que se usarán en el método listarComentariosPorIsbn().
	 * @param id long que recoge la ISBN/ISSN que se mandará al método: listarComentariosPorIsbn() de la clase DaoComentario.
	 * @param tipo int que recoge el nivel de Usuario que se mandará al método: listarComentariosPorIsbn() de la clase DaoComentario.
	 * @return devuelve un String que recoge la conversión a Json del resultado del método: listarComentariosPorIsbn() de la clase DaoComentario.
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error.
	 */
	public String listarJson(long id, int tipo) throws SQLException {
		
		String json = "";	
		// Inicializo el Gson con el Adaptador para la clase LocalDateTime que está en el modelo (básicamente, esto le permite a gson leer los parámetros de dicha clase).
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
	
	/**
	 * Método que sirve para convertir el resultado del método: listarComentariosUser() de la clase DaoComentario, a Json.
	 * Para ello, toma un long que recoge la Id del usuario que se mandará al método listarComentariosUser(). 
	 * @param id long que recoge la Id de Usuario que se mandará al método: listarComentariosUser() de la clase DaoComentario.
	 * @return devuelve un String que recoge la conversión del resultado del método: listarComentariosUser() de la clase DaoComentario.
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error.
	 */
	public String listarJsonUser(long id) throws SQLException {
		
		String json = "";	
		// Inicializo el Gson con el Adaptador para la clase LocalDateTime que está en el modelo (básicamente, esto le permite a gson leer los parámetros de dicha clase).
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
	
	/**
	 * Método que sirve para convertir el resultado del método: listarComentariosPorTiempo() de la clase DaoComentario, a Json.
	 * Para ello, toma un int que recoge el nivel mínimo y otro int con el nivel máximo de usuario que mandará al método: listarComentariosPorTiempo().
	 * @param tipomin int que recoge el nivel mínimo que se usará en el método: listarComentariosPorTiempo().
	 * @param tipomax int que recoge el nivel máximo que se usará en el método: listarComentariosPorTiempo().
	 * @return devuelve un String que recoge la conversión a Json del resultado que devuelve el método: listarComentariosPorTiempo().
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error.
	 */
	public String listarJsonPorTiempo(int tipomin, int tipomax) throws SQLException {
		String json = "";	
		// Inicializo el Gson con el Adaptador para la clase LocalDateTime que está en el modelo (básicamente, esto le permite a gson leer los parámetros de dicha clase).
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
	
	/**
	 * Método que sirve para convertir el resultado del método: listarComentariosPorTipo() de la clase DaoComentario, a Json.
	 * Para ello, toma un int que recoge el nivel mínimo y otro int con el nivel máximo de usuario que mandará al método: listarComentariosPorTipo().
	 * @param tipomin int que recoge el nivel mínimo que se usará en el método: listarComentariosPorTipo().
	 * @param tipomax int que recoge el nivel máximo que se usará en el método: listarComentariosPorTipo().
	 * @return devuelve un String que recoge la conversión a Json del resultado que devuelve el método: listarComentariosPorTipo().
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error.
	 */
	public String listarJsonPorTipo(int tipomin, int tipomax) throws SQLException {
		String json = "";	
		// Inicializo el Gson con el Adaptador para la clase LocalDateTime que está en el modelo (básicamente, esto le permite a gson leer los parámetros de dicha clase).
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
