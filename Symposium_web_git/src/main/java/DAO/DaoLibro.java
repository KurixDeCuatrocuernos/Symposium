package DAO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;

import com.google.gson.Gson;

import modelo.Libro;
import modelo.Usuario;

import java.sql.Date;
import java.sql.PreparedStatement;

public class DaoLibro {
	
	Connection con=null;
	
	public DaoLibro() throws SQLException, ClassNotFoundException {
		this.con= DBConexion.conectar();
	}
	
	public void modificarLibro(Libro l1, long IdOrigen) throws SQLException {
		if (con!=null) {
			String sql="UPDATE symposium.obras SET ISBN=?, Abstracto=?, Autor=?, Titulo=?, Fecha_publicacion=?, Categoria=?, Editorial=?, Tipo=? WHERE ISBN=?;";//QUERY
			PreparedStatement ps=con.prepareStatement(sql);
			System.out.println("Statement modificar preparado");
			ps.setLong(1, l1.getISBN());
			ps.setString(2, l1.getAbstracto());
			ps.setString(3, l1.getAutor());
			ps.setString(4, l1.getTitulo());
			ps.setDate(5, l1.getFecha_publicacion());
			ps.setString(6, l1.getCategoria());
			ps.setString(7, l1.getEditorial());
			ps.setString(8, l1.getTipo());
			ps.setLong(9, IdOrigen);
			
			System.out.println("Se va a ejecutar el siguiente Statement: "+ps);
			
			int filas=ps.executeUpdate();
			if (con!=null) {
				con.close();
				System.out.println("Se ha cerrado la conexión con la base de datos");
			}
			System.out.println("Modificación completada, salgo de DaoLibro");
		}
		else {
			System.out.println("ERROR, Conecta antes con el método DBConexion.conectar()");
		}
		
		
	}
	
	public void insertarLibro(Libro l) throws SQLException {
		if(con!=null) {
			String sql = "INSERT INTO symposium.obras (ISBN,Abstracto,Autor,Titulo,Fecha_publicacion,Categoria,Editorial,Tipo) VALUES (?,?,?,?,?,?,?,?)";
			System.out.println("Se ha establecido la conexión: "+con);
			PreparedStatement ps= con.prepareStatement(sql);
			System.out.println("Statement preparado");
			ps.setLong(1, l.getISBN());
			ps.setString(2, l.getAbstracto());
			ps.setString(3, l.getAutor());
			ps.setString(4, l.getTitulo());
			ps.setDate(5, l.getFecha_publicacion());
			ps.setString(6, l.getCategoria());
			ps.setString(7, l.getEditorial());
			ps.setString(8, "Libro");
			System.out.println("Se va a ejecutar el comando : "+ps);

			int filas=ps.executeUpdate();
		
			if (con!=null) {
				con.close();
				System.out.println("Se ha cerrado la conexión con la base de datos");
			}
			System.out.println("Conexión: "+con+". Cerrada");
			
		} else {
			System.out.println("Conecta antes al método DBConexion.conectar()");
		}
		
		
	}
	
	public boolean comprobarIdLibro(long id) throws SQLException {
		boolean cell=false;
		String sql="SELECT ISBN FROM symposium.obras WHERE ISBN = "+id+";";
		Statement st=con.createStatement();
		System.out.println("Statement: "+st+" preparado");
		ResultSet rs=st.executeQuery(sql);
		System.out.println("Statement ejecutado con éxito");
		
		if (rs.next()!=false) {
			cell=true;
			System.out.println("Se ha encontrado el Libro con el ISBN: "+id);
		} else {
			System.out.println("Se ha buscado, pero no se ha encontrado el libro con el ISBN: "+id);
		}
		if (con!=null) {
			con.close();
			System.out.println("Se ha cerrado la conexión con la base de datos");
		}
		System.out.println("Se ha cerrado la conexión: "+con);
		return cell;
	}
	
	public Libro listarLibroPorId(long id) throws SQLException {
		Libro l1=new Libro();
		if (con!=null) {
			String sql="SELECT * FROM symposium.obras WHERE ISBN = ?;";
			System.out.println("Se ha establecido la conexión: "+con);
			PreparedStatement ps= con.prepareStatement(sql);
			System.out.println("Statement preparado");
			ps.setLong(1, id);
			System.out.println("Se va a ejecutar el comando : "+ps);
			
			ResultSet rs=ps.executeQuery();
			rs.next();
			l1.setISBN(rs.getLong(1));
			l1.setAbstracto(rs.getString(2));
			l1.setTitulo(rs.getString(4));
			l1.setAutor(rs.getString(3));
			l1.setTipo(rs.getString(5));
			l1.setFecha_publicacion(rs.getDate(6));
			l1.setCategoria(rs.getString(8));
			l1.setEditorial(rs.getString(12));
			
			if (con!=null) {
				con.close();
				System.out.println("Se ha cerrado la conexión con la base de datos");
			}
		}
		else {
			System.out.println("Conecta antes al método DBConexion.conectar()");
		}
		return l1;
	}
	public void borrarLibro(long id) throws SQLException  {
		String sql="DELETE FROM symposium.obras WHERE ISBN=?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setLong(1, id);
		System.out.println(ps);
		ps.executeUpdate();
		System.out.println("BORRADO EJECUTADO CON ÉXITO");
		
		if (con!=null) {
			con.close();
			System.out.println("Se ha cerrado la conexión con la base de datos");
		}
	}
	public Libro listar(long id) throws SQLException{
			
			String sql = "SELECT * FROM symposium.obras WHERE ISBN="+id;
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			rs.next();
			/*Libro int iSBN, String abstracto, String autor, String titulo, String tipo, Date fecha_publicacion, String editorial, 
			 * String categoria*/
			/*BD: [1] ISBN int(13), [2] Abstracto text, [3] Autor varchar(50), [4] Titulo varchar(50), [5] Tipo varchar(15), 
			 * [6] Fecha_publicacion date, [7] Bibliografia text, [8] Categoria varchar(50), [9] Lugar_publicacion varchar(50), 
			 * [10] Volumen_publicacion int(5), [11] Temas text, [12] Editorial varchar(50), [13] Valoracion_global int(3)*/
			Libro u = new Libro(rs.getLong(1), rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5), rs.getDate(6), rs.getString(12), rs.getString(8));
			if (con!=null) {
				con.close();
				System.out.println("Se ha cerrado la conexión con la base de datos");
			}
			return u;
		}
	public ArrayList <Libro> listarLibros() throws SQLException {
		String sql = "SELECT ISBN, Autor, Titulo, Tipo, Fecha_publicacion, Editorial, Lugar_publicacion FROM symposium.obras;";
		ArrayList <Libro> libros=null;
		PreparedStatement ps = con.prepareStatement(sql);
		
		ResultSet result = ps.executeQuery();
		
		while(result.next()) {
			
			if(libros == null) {
				
				libros = new ArrayList<Libro>();
			}
			/*Libro int iSBN, String abstracto, String autor, String titulo, String tipo, Date fecha_publicacion, String editorial, 
			 * String categoria*/
			/*BD: [1] ISBN int(13), [2] Abstracto text, [3] Autor varchar(50), [4] Titulo varchar(50), [5] Tipo varchar(15), 
			 * [6] Fecha_publicacion date, [7] Bibliografia text, [8] Categoria varchar(50), [9] Lugar_publicacion varchar(50), 
			 * [10] Volumen_publicacion int(5), [11] Temas text, [12] Editorial varchar(50), [13] Valoracion_global int(3)*/
			
			if (result.getString("Tipo").charAt(0)=='A') { //en caso de que fuera un artículo listamos su lugar de publicacion
				libros.add(new Libro(result.getLong("ISBN"), "", result.getString("Autor"),result.getString("Titulo"), result.getString("Tipo"),result.getDate("Fecha_publicacion"),result.getString("Lugar_publicacion"),""));
				
			} else {
			
				libros.add(new Libro(result.getLong("ISBN"), "", result.getString("Autor"),result.getString("Titulo"), result.getString("Tipo"),result.getDate("Fecha_publicacion"),result.getString("Editorial"),""));
			
			}
			
			
		}
		if (con!=null) {
			con.close();
			System.out.println("Se ha cerrado la conexión con la base de datos");
		}
		return libros;
	}
	
	public String listarLibrosJson() throws SQLException {
		
		String json = "";	
		Gson gson = new Gson();
		
		json = gson.toJson(this.listarLibros());
		
		return json;
	
	}
		
	public String listarJson(long id) throws SQLException {
			
			String json = "";	
			Gson gson = new Gson();
			
			json = gson.toJson(this.listar(id));
			
			return json;
		
		}
}
