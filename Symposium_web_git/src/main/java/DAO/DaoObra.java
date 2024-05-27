package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.google.gson.Gson;

import modelo.Obra;

public class DaoObra {
	
	Connection con=null;
	
	public DaoObra() throws SQLException, ClassNotFoundException {
		this.con= DBConexion.conectar();
	}
	
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
			System.out.println(obras.toString());
		} else {
			System.out.println("Conecta antes al método DBConexion.conectar()");
		}
		
		return obras;
	} 

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
