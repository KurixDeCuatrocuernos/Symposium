package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.Date;

import modelo.SolicitudAscenso;
import modelo.Titulado;
/**
 * Esta clase sirve para el acceso a la DB de cara al objeto Titulado, ya sea insertarlo, modificarlo, listarlo, o borrarlo.
 * @see Titulado
 * @author Alejandro Moreno
 * @version 1.0
 */
public class DaoTitulado {
	
	Connection con=null;
	
	/**
	 * Método constructor de la clase, en caso de que el parámetro con sea null iniciará el método conectar() de la clase DBConexion.
	 * @see DBonexion
	 * @throws ClassNotFoundException si no encuentra la clase DaoComentario lanzará un error.
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error.
	 */
	public DaoTitulado() throws SQLException, ClassNotFoundException {
		
		if(this.con==null) {
			this.con= DBConexion.conectar();
		} else {
			System.out.println("Error al conectar, antes conecta con DBConexion");
		}
		
	}
	
	/**
	 * Método que sirve para insertar un Titulado en la DB a partir de los datos de una SolicitudAscenso.
	 * Para ello, toma una SolicitudAscenso que recoge los datos necesarios para generar un Titulado a partir de un Estudiante.
	 * El proceso se realiza mediante un PreparedStatement. 
	 * @param s SolicitudAscenso que recoge los datos necesarios para convertir a un Estudiante en Titulado.
	 * @see DaoSolicitud
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error.
	 */
	public void acenderEstudianteATitulado(SolicitudAscenso s) throws SQLException {
		System.out.println("Estoy en DaoTitulado --> acenderEstudianteATitulado()");
		if (con!=null) {
			LocalDate fechaOrigen= s.getFecha_titulo();
			Date fecha= Date.valueOf(fechaOrigen);
			String sql="UPDATE symposium.usuarios SET Titulo_estudios=?,Lugar_titulo=?, Fecha_titulo=?, Nivel=30 WHERE ID=?";
			PreparedStatement ps= con.prepareStatement(sql);
			ps.setString(1, s.getTitulo_estudios());
			ps.setString(2, s.getLugar_estudios());
			ps.setDate(3, fecha);
			ps.setLong(4, s.getIdUsuario());
			int filas=ps.executeUpdate();
			System.out.println("filas: "+filas);
			con.close();
			System.out.println("Se ha cerrado la conexión con la base de datos");
		} else {
			System.out.println("ERROR, Conecta antes con el método DBConexion.conectar()");
		}
	}
	
	/**
	 * Método que sirve para modificar a un titulado preexistente en la DB.
	 * Para ello, toma un Titulado que recoge los datosque se modificarán y un long que recoge la ID que se usaá para buscar en la DB al Titulado que se modificará.
	 * El proceso se realiza mediante un PreparedStatement. 
	 * @param T Titulado que recoge los valores que se modificarán.
	 * @param ID long que recoge la ID que se usará para buscar al Titulado que se modificará.
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error.
	 */
	public void modificarTitulado(Titulado T, long ID) throws SQLException {
		if (con!= null) {
			System.out.println("Estoy en DaoTitulado --> modificarTitulado()");
			// Orden y Tipo de las columnas de la DB: 
			// Usuarios(ID numeric(10) not null,Nivel int(3) not null,Nombre varchar(50) not null,Apellidos varchar(50) not null,Edad int(2) not null, 
			//			Email varchar(50) not null,Pass_word varchar(50) not null,Estudios varchar(50),Escuela varchar(50),Telefono int(9),
			//			Titulo_estudios varchar(50), Lugar_titulo varchar(50),Fecha_titulo int(6),Titulo_img varchar(50),
			
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
			System.out.println("filas: "+filas);
			con.close();
			System.out.println("Se ha cerrado la conexión con la base de datos");
		} else {
			System.out.println("ERROR, Conecta antes con el método DBConexion.conectar()");
		}
	}
	/**
	 * Método que se usa para listar los datos de un Titulado preexistente en la DB.
	 * Para ello, toma un long que recoge la ID del Usuario que se listará.
	 * El proceso se realiza mediante un PreparedStatement. 
	 * @param id long que recoge la Id del Usuario que se listará
	 * @return devuelve un Titulado que contiene los valores que se listarán.
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error.
	 */
	public Titulado listar(long id) throws SQLException{
		
		String sql = "SELECT * FROM symposium.usuarios WHERE ID="+id;
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		
		rs.next();
		
		// Orden y Tipo de las columnas de la DB.
		// Usuarios(ID numeric(10) not null, Nivel int(3) not null, Nombre varchar(50) not null, Apellidos varchar(50) not null, Edad int(2) not null,
		//			Email varchar(50) not null, Pass_word varchar(50) not null, Estudios varchar(50), Escuela varchar(50), Telefono int(9),
		//			Titulo_estudios varchar(50), Lugar_titulo varchar(50), Fecha_titulo date, Titulo_img varchar(50));
			
		Titulado e = new Titulado(rs.getLong(1), rs.getInt(2),rs.getString(3),rs.getString(4),rs.getInt(5), rs.getString(6), rs.getString(11), rs.getString(12), rs.getInt(13), rs.getString(14));
		
		if (con!=null) {
			con.close();
			System.out.println("Se ha cerrado la conexión con la base de datos");
		}
		
		return e;
	}
}
