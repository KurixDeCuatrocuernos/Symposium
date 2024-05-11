package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import modelo.Titulado;

public class DaoTitulado {
	
	Connection con=null;
		
	public DaoTitulado() throws SQLException, ClassNotFoundException {
			
		this.con= DBConexion.conectar();
		
	}
	
	public void modificarTitulado(Titulado T, long ID) throws SQLException {
		if (con!= null) {
			System.out.println("Estoy en DaoTitulado --> modificarTitulado()");
//Usuarios(ID numeric(10) not null,Nivel int(3) not null,Nombre varchar(50) not null,Apellidos varchar(50) not null,Edad int(2) not null, 
//Email varchar(50) not null,Pass_word varchar(50) not null,Estudios varchar(50),Escuela varchar(50),Telefono int(9),
//Titulo_estudios varchar(50), Lugar_titulo varchar(50),Fecha_titulo int(6),Titulo_img varchar(50),
			
			String sql = "UPDATE symposium.usuarios SET ID= ?, Nivel=?, Nombre=?, Apellidos=?, Edad=?, Email=?, Titulo_estudios=?, Lugar_titulo=?, Fecha_titulo=? WHERE ID=?";
			System.out.println("Voy a modificar el Usuario a Titulado: "+T.toString());
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setLong(1, T.getId());
			ps.setInt(2, T.getNivel());
			ps.setString(3, T.getNombre());
			ps.setString(4, T.getApellidos());
			ps.setInt(5, T.getEdad());
			ps.setString(6, T.getEmail());
			ps.setString(7, T.getTitulo_estudios());
			ps.setString(8, T.getLugar_titulo());
			ps.setInt(9, T.getFecha_titulo());
			ps.setLong(10, ID);
			System.out.println("Se va a ejecutar el siguiente Statement: "+ps);
			int filas=ps.executeUpdate();
			if (con!=null) {
				con.close();
				System.out.println("Se ha cerrado la conexión con la base de datos");
			}
				System.out.println("Modificación completada, salgo de DaoLibro");
		} else {
			System.out.println("ERROR, Conecta antes con el método DBConexion.conectar()");
		}
	}
	
	public Titulado listar(long id) throws SQLException{
		
		String sql = "SELECT * FROM symposium.usuarios WHERE ID="+id;
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		
		rs.next();
//		Usuarios(ID numeric(10) not null, Nivel int(3) not null, Nombre varchar(50) not null, Apellidos varchar(50) not null, Edad int(2) not null,
//				Email varchar(50) not null, Pass_word varchar(50) not null, Estudios varchar(50), Escuela varchar(50), Telefono int(9),
//				Titulo_estudios varchar(50), Lugar_titulo varchar(50), Fecha_titulo date, Titulo_img varchar(50));
//		Titulado(int id, int nivel, String nombre, String apellidos, int edad, String email, String tituloEstudios, String lugarTitulo, int fechaTitulo, String tituloImg)
			
		Titulado e = new Titulado(rs.getLong(1), rs.getInt(2),rs.getString(3),rs.getString(4),rs.getInt(5), rs.getString(6), rs.getString(11), rs.getString(12), rs.getInt(13), rs.getString(14));
		
		if (con!=null) {
			con.close();
			System.out.println("Se ha cerrado la conexión con la base de datos");
		}
		
		return e;
	}
}
