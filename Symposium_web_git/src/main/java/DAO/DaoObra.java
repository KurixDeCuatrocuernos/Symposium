package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.google.gson.Gson;

import modelo.Obra;
/**
 * Esta clase sirve para el acceso a la DB de cara al objeto Obra, ya sea insertarlo, modificarlo, listarlo, o borrarlo.
 * @see Obra
 * @author Alejandro Moreno
 * @version 1.0
 */
public class DaoObra {
	
	Connection con=null;
	/**
	 * Método constructor de la clase, en caso de que el parámetro con sea null iniciará el método conectar() de la clase DBConexion.
	 * @see DBonexion
	 * @throws ClassNotFoundException si no encuentra la clase DaoComentario lanzará un error.
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error.
	 */
	public DaoObra() throws SQLException, ClassNotFoundException {
		
		if (this.con==null) {
			this.con= DBConexion.conectar();
		}
		
	}
	
	/**
	 * Método que sirve para listar las obras presentes en la DB a partir de su titulo y autor.
	 * Para ello, toma un String que recoge el texto insertado en el buscador por el usuario, y lo usa para buscar Obras en la DB.
	 * El proceso se realiza mediante un PreparedStatement. 
	 * @param text String que recoge el texto que el usuario introduce en el buscador.
	 * @return obras devuelve un ArrayList que contiene Libros (que contienen los valores) recogidos de la DB.
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error.
	 */
	public ArrayList<Obra> listarObrasPorTitulo(String text) throws SQLException {
		ArrayList<Obra> obras=new ArrayList<Obra>();
		System.out.println("Estoy en DaoObra --> listarObrasPorTitulo()");
		if (!con.isClosed()) {
			
			String sql="SELECT ISBN, Titulo, Tipo FROM symposium.obras WHERE lower(Titulo) like ? ORDER BY Titulo asc";
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, "%"+text+"%");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				obras.add(new Obra(rs.getLong("ISBN"), rs.getString("Titulo"), rs.getString("Tipo")));
			}
			//REVISAR SI FUNCIONA
			sql="SELECT ISBN, Titulo, Tipo FROM symposium.obras WHERE lower(Autor) like ?";
			ps=con.prepareStatement(sql);
			ps.setString(1, "%"+text+"%");
			rs=ps.executeQuery();
			while(rs.next()) {
				obras.add(new Obra(rs.getLong("ISBN"), rs.getString("Titulo"), rs.getString("Tipo")));
			}
			
			System.out.println(obras.toString());
		} else {
			System.out.println("Conecta antes al método DBConexion.conectar()");
		}
		
		return obras;
	} 
	
	/**
	 * Método que sirve para convertir a Json el resultado del método: listarObrasPorTitulo() de la clase DaoObra.
	 * Para ello, toma un String que recoge la información proporcionada por el Usuario y lo manda al método: listarObrasPorTitulo().
	 * El proceso se realiza mediante un PreparedStatement. 
	 * @param text String que recoge la información que el Usuario introduce en el buscador.
	 * @return devuelve un String que recoge la conversión a Json del resultado del método: listarObrasPorTitulo().
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error.
	 */
	public String listarObrasJson(String text) throws SQLException {
		String json="";	
		Gson gson = new Gson();
		
		json = gson.toJson(this.listarObrasPorTitulo(text));
		
		if (!con.isClosed()) {
			con.close();
			System.out.println("Se ha cerrado la conexión con la base de datos");
		}
		
		return json;
	}
	
}
