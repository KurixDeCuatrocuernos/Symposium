package DAO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import modelo.Libro;
import java.sql.Date;
import java.sql.PreparedStatement;

public class DaoLibro {
	
	Connection con=null;
	
	public DaoLibro() throws SQLException, ClassNotFoundException {
		this.con= DBConexion.conectar();
	}
	
	public void insertarLibro(Libro l) throws SQLException {
		if(con!=null) {
			String sql = "INSERT INTO symposium.obras (ISBN,Abstracto,Autor,Titulo,Fecha_publicacion,Categoria,Editorial,Tipo) VALUES (?,?,?,?,?,?,?,?)";
			System.out.println("Se ha establecido la conexión: "+con);
			PreparedStatement ps= con.prepareStatement(sql);
			System.out.println("Statement preparado");
			ps.setInt(1, l.getISBN());
			ps.setString(2, l.getAbstracto());
			ps.setString(3, l.getAutor());
			ps.setString(4, l.getTitulo());
			ps.setDate(5, l.getFecha_publicacion());
			ps.setString(6, l.getCategoria());
			ps.setString(7, l.getEditorial());
			ps.setString(8, "Libro");
			System.out.println("Se va a ejecutar el comando : "+ps);

			int filas=ps.executeUpdate();
		
			ps.close();
			System.out.println("Conexión: "+con+". Cerrada");
			
		} else {
			System.out.println("Conecta antes al método DBConexion.conectar()");
		}
		
		
	}
}
