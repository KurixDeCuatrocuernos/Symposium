package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Esta clase sirve para Conectar con la DB. 
 * @author Alejandro Moreno
 * @version 1.0
 */
public class DBConexion {
	
	private static String JDBC_URL="jdbc:mysql://localhost:3306/symposium";
	private static String user="root";
	private static String password ="";
	private static Connection con= null;
	/**
	 * Método constructor vacío de la clase.
	 */
	public DBConexion() {
		
	}
	/**
	 * Método que sirve para establecer conexión con la DB en cuestión.
	 * @return devuelve la Connection ya iniciada.
	 * @throws ClassNotFoundException si no encuentra la clase DaoComentario lanzará un error.
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error.
	 */
	public static Connection conectar() throws SQLException, ClassNotFoundException {
		System.out.println("Estoy en conectar() y voy a conectar con "+con);
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
	
}
