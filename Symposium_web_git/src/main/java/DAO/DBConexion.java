package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConexion {
	
	private static String JDBC_URL="jdbc:mysql://localhost:3306/symposium";
	private static String user="root";
	private static String password ="";
	private static Connection con= null;
	
	public DBConexion() {
		
	}
	
	public static Connection conectar() throws SQLException, ClassNotFoundException {
		System.out.println("Estoy en getConexion() y voy a conectar con "+con);
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con=DriverManager.getConnection(JDBC_URL, user, password);
			if (con!=null) {
				System.out.println("Se ha conectado correctamente a la base de datos.");
			}
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		
		System.out.println("He conectado a: "+con);
		return con;
	}
	
	public static void desconectar() throws SQLException{
		if (con!=null) {
			con.close();
			System.out.println("Se ha cerrado la conexión");
		}
		else {
			System.out.println("La conexión no estaba abierta");
		}
	}
	
//	public void insertarEnBD() throws SQLException{
//		System.out.println("Voy a insertar");
//		DBConexion.Conectar().prepareStatement("INSERT INTO ...");
//	}
	
}
