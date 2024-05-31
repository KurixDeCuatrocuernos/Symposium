package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.gson.Gson;

import modelo.Articulo;

/**
 * Esta clase sirve para el acceso a la DB de cara al objeto Articulo, ya sea Insertarlo, Modificarlo o listarlo.
 * @see Articulo
 * @author Alejandro Moreno
 * @version 1.0
 */
public class DaoArticulo {

	Connection con=null;
	
	/**
	 * Método constructor de la clase, en caso de que el parámetro con sea null iniciará el método conectar() de la clase DBConexion.
	 * @see DBonexion
	 * @throws ClassNotFoundException si no encuentra la clase DaoComentario lanzará un error.
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error. 
	 */
	public DaoArticulo() throws SQLException, ClassNotFoundException {
		if (this.con==null) {
			this.con= DBConexion.conectar();
		} else {
			System.out.println("Ya estabas conectado");
		}
		
	}
	
	/**
	 * Método que sirve para insertar un nuevo Artículo en la DB, para ello toma un objeto Artículo que recoge los valores que se insertarán.
	 * El proceso se realiza mediante un PreparedStatement
	 * @param a Artículo que recoge los valores que se insertarán
	 * @see Articulo
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error. 
	 */
	public void insertarArticulo(Articulo a) throws SQLException {
		System.out.println("Estoy en DaoArticulo --> insertarArticulo()");
		if(con!=null) {
			
			String sql = "INSERT INTO symposium.obras (ISBN,Abstracto,Autor,Titulo,Fecha_publicacion,Temas,Lugar_publicacion,Volumen_publicacion,Tipo) VALUES (?,?,?,?,?,?,?,?,?)";
			System.out.println("Se ha establecido la conexión: "+con);
			PreparedStatement ps= con.prepareStatement(sql);
			System.out.println("Statement preparado");
			ps.setLong(1, a.getISBN());
			ps.setString(2, a.getAbstracto());
			ps.setString(3, a.getAutor());
			ps.setString(4, a.getTitulo());
			ps.setDate(5, a.getFecha_publicacion());
			ps.setString(6, a.getTemas());
			ps.setString(7, a.getLugar_publicacion());
			ps.setString(8, a.getVolumen_publicacion());
			ps.setString(9, a.getTipo());
			System.out.println("Se va a ejecutar el comando : "+ps);

			int filas=ps.executeUpdate();
			System.out.println(filas);

			con.close();
			System.out.println("Se ha cerrado la conexión con la base de datos");
			
		} else {
			System.out.println("Conecta antes al método DBConexion.conectar()");
		}
	}
	
	/**
	 * Método que sirve para modificar los valores de un Artículo preexistente en la DB, 
	 * para ello toma un objeto Artículo que contiene los valores que se modificarán y un long que recoge la id que se usará para buscar el Articulo en la DB
	 * El proceso se realiza mediante un PreparedStatement
	 * @param a Artículo que contiene los valores que se modificarán
	 * @param id long que recoge la ISBN/ISSN que se usará para buscar el Artículo que se modificará
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error. 
	 */
	public void modificarArticulo(Articulo a, long id) throws SQLException {
		System.out.println("Estoy en DaoArticulo --> modificarArticulo()");
		if (con!=null) {
			String sql="UPDATE symposium.obras SET ISBN=?, Abstracto=?, Autor=?, Titulo=?, Fecha_publicacion=?, Temas=?, Lugar_publicacion=?, Volumen_publicacion=?, Tipo=? WHERE ISBN=?;";//QUERY
			PreparedStatement ps=con.prepareStatement(sql);
			System.out.println("Statement modificar preparado");
			ps.setLong(1, a.getISBN());
			ps.setString(2, a.getAbstracto());
			ps.setString(3, a.getAutor());
			ps.setString(4, a.getTitulo());
			ps.setDate(5, a.getFecha_publicacion());
			ps.setString(6, a.getTemas());
			ps.setString(7, a.getLugar_publicacion());
			ps.setString(8, a.getVolumen_publicacion());
			ps.setString(9, a.getTipo());
			ps.setLong(10, id);
			
			System.out.println("Se va a ejecutar el siguiente Statement: "+ps);
			
			int filas=ps.executeUpdate();
			System.out.println("Modificación completada: "+filas+", salgo de DaoArticulo");
			con.close();
			System.out.println("Se ha cerrado la conexión con la base de datos");
			
		}
		else {
			System.out.println("ERROR, Conecta antes con el método DBConexion.conectar()");
		}
		
		
	}
	
	/**
	 * Método que sirve para listar un Articulo, es decir, recoger sus valores a fin de mostrarlos, para ello toma una id que usará para buscar dicho Articulo en la DB.
	 * El proceso se realizará mediante un PreparedStatement.
	 * @param id long que sirve para buscar el Articulo concreto en la DB.
	 * @return devuelve un Artículo que contiene los valores que se mostrarán.
	 * @see Articulo 
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error.
	 */
	public Articulo listar(long id) throws SQLException{
		Articulo a= new Articulo();
		if (con!=null) {
			String sql = "SELECT * FROM symposium.obras WHERE ISBN="+id;
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			// Orden y tipo de las  Columnas en DB
			// BD: [1] ISBN int(13), [2] Abstracto text, [3] Autor varchar(50), [4] Titulo varchar(50), [5] Tipo varchar(15), 
			//     [6] Fecha_publicacion date, [7] Bibliografia text, [8] Categoria varchar(50), [9] Lugar_publicacion varchar(50), 
			//     [10] Volumen_publicacion int(5), [11] Temas text, [12] Editorial varchar(50), [13] Valoracion_global int(3)
			if(rs.next()) {
				a = new Articulo(rs.getLong(1), rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5), rs.getDate(6), rs.getString(9), rs.getString(10), rs.getString(11));
				System.out.println("articulo insertado");
			}
			
			
			con.close();
			System.out.println("Se ha cerrado la conexión con la base de datos");
		
		}else {
			
		}
		return a;
	}
	
	/**
	 * Método que sirve para convertir un Artículo a Json, para que pueda ser leído por JavaScript, 
	 * para ello toma un long que manda al método listar() de la clase DaoArticulo y convierte dicho resultado a Json.
	 * @param id long que recoge la id que se pasará al método listar() de la clase DaoArticulo
	 * @return json devuelve un String que recoge la conversión a json del resultado que devuelva el método listar de la clase DaoArticulo
	 * @throws SQLException
	 */
	public String listarJson(long id) throws SQLException {
		
		String json = "";	
		Gson gson = new Gson();
		json = gson.toJson(this.listar(id));
		
		if (con!=null) {
			con.close();
			System.out.println("Se ha cerrado la conexión");
		}
		
		return json;
	
	}
}
