package DAO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.google.gson.Gson;

import modelo.Libro;

import java.sql.PreparedStatement;

public class DaoLibro {
	
	Connection con=null;
	
	public DaoLibro() throws SQLException, ClassNotFoundException {
		this.con= DBConexion.conectar();
	}
	
	public String comprobarTipo(Long id) throws SQLException {
		System.out.println("Estoy en DaoLibro --> comprobarTipo()");
		String Type="";
		if (con != null) {
			System.out.println("Voy a comprobar el tipo de la id: "+id);
			String sql= "SELECT * FROM symposium.obras WHERE ISBN="+id;
			PreparedStatement ps= con.prepareStatement(sql);
			System.out.println("Voy a ejecutar el Statement: "+ps);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				System.out.println(rs.getString("Tipo"));
				Type=rs.getString("Tipo");
			} 
			
			
		} else {
			System.out.println("ERROR, Conecta antes con el método DBConexion.conectar()");
		}
		
		return Type; 
	}
	
	public String obtenerTituloPorId(Long id) throws SQLException {
		String titulo="";
		System.out.println("Estoy en DaoLibro --> obtenerTituloPorId()");
		if (con!=null) {
			
			String sql = "SELECT Titulo FROM symposium.obras WHERE ISBN=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			//no sé si result set empieza en 0 o en 1
			if (rs.next()) {
				titulo=rs.getString("Titulo");
			} else {
				System.out.println("NO SE ENCONTRÓ NINGÚN TÍTULO CON ESA ID");
			}
			
			con.close();
			System.out.println("Se ha cerrado la conexión con la base de datos");
		
		} else {
			System.out.println("ERROR, Conecta antes con el método DBConexion.conectar()");
		}
		
		
		return titulo;
	}
	
	public ArrayList<Long> comprobarObraPorNombre(String text) throws SQLException {
		System.out.println("Estoy en DaoLibro --> comprobarObraPorNombre()");
		ArrayList <Long> ides = new ArrayList <Long>();
		System.out.println("Se va a buscar la obra con el título: "+text);
		if (con!= null) {
			
			String sql= "SELECT ISBN FROM symposium.obras WHERE Titulo like UPPER('%"+text+"%');";
			
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				ides.add(rs.getLong("ISBN"));
			}
			
				
			con.close();
			System.out.println("Se ha cerrado la conexión con la base de datos");
		} else {
			System.out.println("ERROR, Conecta antes con el método DBConexion.conectar()");
		}
		
		return ides;
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
			
			con.close();
			System.out.println("Se ha cerrado la conexión con la base de datos");
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
		
			con.close();
			System.out.println("Se ha cerrado la conexión con la base de datos");
			System.out.println("Conexión: "+con+". Cerrada");
			
		} else {
			System.out.println("Conecta antes al método DBConexion.conectar()");
		}
		
		
	}
	
	public boolean comprobarIdLibro(long id) throws SQLException {
		boolean cell=false;
		if (con!=null) {
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
		
			con.close();
			System.out.println("Se ha cerrado la conexión con la base de datos");
		} else {
			System.out.println("Conecta antes al método DBConexion.conectar()");
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
			
			con.close();
			System.out.println("Se ha cerrado la conexión con la base de datos");
		} else {
			System.out.println("Conecta antes al método DBConexion.conectar()");
		}
		return l1;
	}
	public void borrarLibro(long id) throws SQLException  {
		if (con!=null) {
			String sql="DELETE FROM symposium.obras WHERE ISBN=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setLong(1, id);
			System.out.println(ps);
			ps.executeUpdate();
			System.out.println("BORRADO EJECUTADO CON ÉXITO");
		
		
			con.close();
			System.out.println("Se ha cerrado la conexión con la base de datos");
		} else {
			System.out.println("Conecta antes al método DBConexion.conectar()");
		}
	}
	public Libro listar(long id) throws SQLException{
		Libro u =new Libro();
		if (con!=null) {
			String sql = "SELECT * FROM symposium.obras WHERE ISBN="+id;
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			rs.next();
			/*Libro int iSBN, String abstracto, String autor, String titulo, String tipo, Date fecha_publicacion, String editorial, 
			 * String categoria*/
			/*BD: [1] ISBN int(13), [2] Abstracto text, [3] Autor varchar(50), [4] Titulo varchar(50), [5] Tipo varchar(15), 
			 * [6] Fecha_publicacion date, [7] Bibliografia text, [8] Categoria varchar(50), [9] Lugar_publicacion varchar(50), 
			 * [10] Volumen_publicacion int(5), [11] Temas text, [12] Editorial varchar(50), [13] Valoracion_global int(3)*/
			u = new Libro(rs.getLong(1), rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5), rs.getDate(6), rs.getString(12), rs.getString(8));
				
			con.close();
			System.out.println("Se ha cerrado la conexión con la base de datos");
		} else {
			System.out.println("Conecta antes al método DBConexion.conectar()");
		}
				
			return u;
		}
	
	public ArrayList <Libro> listarLibros() throws SQLException {
		ArrayList <Libro> libros=new ArrayList<Libro>();
		if (con!=null ) {
			String sql = "SELECT ISBN, Autor, Titulo, Tipo, Fecha_publicacion, Editorial, Lugar_publicacion FROM symposium.obras;";
			
			PreparedStatement ps = con.prepareStatement(sql);
			
			ResultSet result = ps.executeQuery();
			
			while(result.next()) {
				
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
		
			con.close();
			System.out.println("Se ha cerrado la conexión: "+con);
			
		} else {
			System.out.println("Conecta antes al método DBConexion.conectar()");
		}
		
		return libros;
	}
	/**
	 * Este método permite sacar la información de todas las obras a partir de su isbn, que estén en un array, para ello, no se cierra la conexión hasta que el método listarJsonConcatenado haya terminado 
	 * (porque llama a este método en bucle) 
	 * @param ides
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Libro> listarObrasPorIdes(ArrayList<Long> ides) throws SQLException{
		ArrayList <Libro> libros = new ArrayList<Libro>();
		if (con!=null) {
			String sql = "SELECT ISBN, Titulo, Autor FROM symposium.obras WHERE ISBN=?";
			PreparedStatement ps = con.prepareStatement(sql);
			for (int i=0; i<ides.size(); i++) {
				ps.setLong(1, ides.get(i));
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					Libro l = new Libro();
					l.setISBN(rs.getLong("ISBN"));
					l.setTitulo(rs.getString("Titulo"));
					l.setAutor(rs.getString("Autor"));
					libros.add(l);
				}
			}
			System.out.println(libros.toString());
		} else {
			System.out.println("Conecta antes al método DBConexion.conectar()");
		}
		
		return libros; 
	} 
	
	public String listarLibrosJson() throws SQLException {
		
		String json = "";	
		Gson gson = new Gson();
		
		json = gson.toJson(this.listarLibros());
		
		if (con!=null) {
			con.close();
			System.out.println("Se ha cerrado la conexión con la base de datos");
		}
		return json;
	
	}
	
	public String listarJsonConcatenado(ArrayList<Long> id) throws SQLException {
		String json = "";	
		Gson gson = new Gson();
		
		json += gson.toJson(this.listarObrasPorIdes(id));
		
		if (con!=null) {
			con.close();
			System.out.println("Se ha cerrado la conexión con la base de datos");
		}
		return json;
	}
		
	public String listarJson(long id) throws SQLException {
			
			String json = "";	
			Gson gson = new Gson();
			
			json = gson.toJson(this.listar(id));
			
			if (con!=null) {
				con.close();
				System.out.println("Se ha cerrado la conexión con la base de datos");
			}
			return json;
		
		}
}
