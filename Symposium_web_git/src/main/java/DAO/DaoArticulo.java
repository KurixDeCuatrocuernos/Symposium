package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.gson.Gson;

import modelo.Articulo;


public class DaoArticulo {

	Connection con=null;
	
	public DaoArticulo() throws SQLException, ClassNotFoundException {
		this.con= DBConexion.conectar();
	}
	
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
		

			con.close();
			System.out.println("Se ha cerrado la conexión con la base de datos");
			
		} else {
			System.out.println("Conecta antes al método DBConexion.conectar()");
		}
	}
	
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
			System.out.println("Modificación completada, salgo de DaoArticulo");
			con.close();
			System.out.println("Se ha cerrado la conexión con la base de datos");
			
		}
		else {
			System.out.println("ERROR, Conecta antes con el método DBConexion.conectar()");
		}
		
		
	}
	
	public Articulo listar(long id) throws SQLException{
		Articulo a= new Articulo();
		if (con!=null) {
			String sql = "SELECT * FROM symposium.obras WHERE ISBN="+id;
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				a = new Articulo(rs.getLong(1), rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5), rs.getDate(6), rs.getString(9), rs.getString(10), rs.getString(11));
				System.out.println("articulo insertado");
			}
			/*Articulo(long iSBN, String abstracto, String autor, String titulo, String tipo, Date fecha_publicacion, 
						String lugarPublicacion, String volumenPublicacion, String temas)*/
			/*BD: [1] ISBN int(13), [2] Abstracto text, [3] Autor varchar(50), [4] Titulo varchar(50), [5] Tipo varchar(15), 
			 * [6] Fecha_publicacion date, [7] Bibliografia text, [8] Categoria varchar(50), [9] Lugar_publicacion varchar(50), 
			 * [10] Volumen_publicacion int(5), [11] Temas text, [12] Editorial varchar(50), [13] Valoracion_global int(3)*/
			
			con.close();
			System.out.println("Se ha cerrado la conexión con la base de datos");
		
		}else {
			
		}
		return a;
	}
	
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
