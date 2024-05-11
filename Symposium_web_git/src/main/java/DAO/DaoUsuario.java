package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.google.gson.Gson;

import modelo.Admin;
import modelo.Estudiante;
import modelo.Libro;
import modelo.Titulado;
import modelo.Usuario;

public class DaoUsuario {
	
	Connection con=null;
	
	public DaoUsuario() throws SQLException, ClassNotFoundException {
		
		this.con= DBConexion.conectar();
	
	}
	
	public void borrarUsuario(long Id) throws SQLException {
		System.out.println("Estoy en DaoUsuario --> borrarUsuario()");
		String sql="DELETE FROM symposium.usuarios WHERE ID=?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setLong(1, Id);
		System.out.println(ps);
		ps.executeUpdate();
		System.out.println("BORRADO EJECUTADO CON ÉXITO");
		if (con!=null) {
			con.close();
			System.out.println("Se ha cerrado la conexión con la base de datos");
		}
	}
	
	public ArrayList <Usuario> listarUsuarios() throws SQLException {
		System.out.println("Estoy en DaoUsuario --> listarUsuarios()");
		String sql = "SELECT ID, Nivel, Nombre, Apellidos, Edad, Email FROM symposium.usuarios;";
		ArrayList <Usuario> usuarios=null;
		PreparedStatement ps = con.prepareStatement(sql);

		ResultSet result = ps.executeQuery();
		
		while(result.next()) {
			
			if(usuarios == null) {
				
				usuarios = new ArrayList<Usuario>();
			}
			//Usuarios( ID numeric(10) not null, Nivel int(3) not null, Nombre varchar(50) not null, Apellidos varchar(50) not null,
			//Usuario(int id, int nivel, String nombre, String apellidos, int edad, String email)
			
			
				usuarios.add(new Usuario(result.getLong("ID"), result.getInt("Nivel"),result.getString("Nombre"), result.getString("Apellidos"), result.getInt("Edad"), result.getString("Email")));

		}
		if (con!=null) {
			con.close();
			System.out.println("Se ha cerrado la conexión con la base de datos");
		}
		return usuarios;
	}
	
	public String listarJson(long id) throws SQLException, ClassNotFoundException {
		
		DaoAdmin ad=new DaoAdmin();
		DaoEstudiante es = new DaoEstudiante();
		DaoTitulado ti= new DaoTitulado();
		String json = "";	
		Gson gson = new Gson();
		String sql = "SELECT Nivel FROM symposium.usuarios WHERE ID="+id;
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		
		rs.next();
		
//		Usuarios(ID numeric(10) not null, Nivel int(3) not null, Nombre varchar(50) not null, Apellidos varchar(50) not null, Edad int(2) not null,
//				Email varchar(50) not null, Pass_word varchar(50) not null, Estudios varchar(50), Escuela varchar(50), Telefono int(9),
//				Titulo_estudios varchar(50), Lugar_titulo varchar(50), Fecha_titulo date, Titulo_img varchar(50));
//		Usuario(int id, int nivel, String nombre, String apellidos, int edad, String email)
		if (rs.getInt("Nivel")>49) {
			
			json = gson.toJson(ad.listar(id));
			System.out.println(json);
			
		} else if (rs.getInt("Nivel")<50 && rs.getInt("Nivel")>29) {
			
			json = gson.toJson(ti.listar(id));
			System.out.println(json);
			
		} else if (rs.getInt("Nivel")<30 && rs.getInt("Nivel")>9) {
			
			json = gson.toJson(es.listar(id));
			System.out.println(json);
		}
		
		if (con!=null) {
			con.close();
			System.out.println("Se ha cerrado la conexión con la base de datos");
		}
		
		return json;
	
	}
	
	public String listarUsuariosJson() throws SQLException {
		System.out.println("Estoy en DaoUsuario --> listarUsuariosJson()");

			String json = "";	
			Gson gson = new Gson();
			
			json = gson.toJson(this.listarUsuarios());
			
			return json;
	
	}
}
