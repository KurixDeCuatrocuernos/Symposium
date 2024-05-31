package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import modelo.Admin;

/**
 * Esta clase sirve para el acceso a la DB de cara al objeto Admin, de cara a su modificación y listarlo (motrar sus datos).
 * @see Admin
 * @author Alejandro Moreno
 * @version 1.0
 */
public class DaoAdmin {
	Connection con=null;
	
	/**
	 * Método constructor de la clase, en caso de que el parámetro con sea nulo iniciará el método conectar() de la clase DBConexion.
	 * @see DBonexion
	 * @throws ClassNotFoundException si no encuentra la clase DaoComentario lanzará un error.
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error. 
	 */
	public DaoAdmin() throws SQLException, ClassNotFoundException {
		
		if (this.con==null) {
			this.con= DBConexion.conectar();
		} else {
			System.out.println("Ya estabas conectado");
		}
		
	}
	/**
	 * Método que permite modificar a un Admin preexistente en la DB, para ello toma los valores de un objeto Admin y long que sirve para buscar su id en la DB.
	 * El proceso se realiza mediante un PreparedStatement.
	 * @param A objeto Admin que recoge los valores que se modificarán.
	 * @param ID long que recoge la Id del Admin cuyos valores se modificarán.
	 * @see Admin
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error.
	 */
	public void modificarAdmin(Admin A, long ID) throws SQLException {
		if (con != null) {
			System.out.println("Estoy en DaoAdmin --> modificarAdmin()");
			// Orden y tipo de las  Columnas
			// Usuarios: ID numeric(10) not null,Nivel int(3) not null,Nombre varchar(50) not null,Apellidos varchar(50) not null,Edad int(2) not null, 
			// 			 Email varchar(50) not null,Pass_word varchar(50) not null,Estudios varchar(50),Escuela varchar(50),Telefono int(9),
			// 			 Titulo_estudios varchar(50), Lugar_titulo varchar(50),Fecha_titulo int(6),Titulo_img varchar(50)
			String sql = "UPDATE symposium.usuarios SET ID=?, Nivel=?, Nombre=?, Apellidos=?, Edad=?, Email=?, Telefono=? WHERE ID=?";
			System.out.println("Voy a modificar el admin con id: "+ID+" a: "+A.toString());
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setLong(1, A.getId());
			ps.setInt(2, A.getNivel());
			ps.setString(3, A.getNombre());
			ps.setString(4, A.getApellidos());
			ps.setInt(5, A.getEdad());
			ps.setString(6, A.getEmail());
			ps.setInt(7, A.getTelefono());
			ps.setLong(8, ID);
			int filas = ps.executeUpdate();
			System.out.println(filas);
			if (con!=null) {
				con.close();
				System.out.println("Se ha cerrado la conexión con la base de datos");
			}
			System.out.println("Modificación completada, salgo de DaoLibro");
		} else {
			System.out.println("ERROR, Conecta antes con el método DBConexion.conectar()");
		}
	}
	/**
	 * Método que sirve para listar un Admin, es decir, recoger sus valores a fin de mostrarlos, para ello toma una id que usará para buscar dicho Admin en la DB.
	 * El proceso se realizará mediante un PreparedStatement. 
	 * @param id long que recoge la Id que se buscará en la DB 
	 * @return e devuelve un objeto Admin que recoge los valores que se mostrarán.
	 * @see Admin 
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error.
	 */
	public Admin listar(long id) throws SQLException {
		
		String sql = "SELECT * FROM symposium.usuarios WHERE ID="+id;
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		
		rs.next();
		
//		Usuarios(ID numeric(10) not null, Nivel int(3) not null, Nombre varchar(50) not null, Apellidos varchar(50) not null, Edad int(2) not null,
//				Email varchar(50) not null, Pass_word varchar(50) not null, Estudios varchar(50), Escuela varchar(50), Telefono int(9),
//				Titulo_estudios varchar(50), Lugar_titulo varchar(50), Fecha_titulo date, Titulo_img varchar(50));
//		Admin(long id, int nivel, String nombre, String apellidos, int edad, String email, int telefono)
			
		Admin e = new Admin(rs.getLong(1), rs.getInt(2),rs.getString(3),rs.getString(4),rs.getInt(5), rs.getString(6), rs.getInt(10));
		
		if (con!=null) {
			con.close();
			System.out.println("Se ha cerrado la conexión con la base de datos");
		}
		
		return e;
		
	}
	
}
