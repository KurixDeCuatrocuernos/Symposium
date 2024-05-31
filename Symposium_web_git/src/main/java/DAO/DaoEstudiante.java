package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import modelo.Estudiante;

/**
 * Esta clase sirve para el acceso a la DB de cara al objeto Estudiante, ya sea insertarlo, modificarlo, listarlo, o borrarlo.
 * @see Estudiante
 * @author Alejandro Moreno
 * @version 1.0
 */
public class DaoEstudiante {
	
	private Connection con = null;
	/**
	 * Método constructor de la clase, en caso de que el parámetro con sea null iniciará el método conectar() de la clase DBConexion.
	 * @see DBonexion
	 * @throws ClassNotFoundException si no encuentra la clase DaoComentario lanzará un error.
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error.
	 */
	public DaoEstudiante() throws SQLException, ClassNotFoundException {
		
		if (this.con==null) {
			this.con= DBConexion.conectar();
		} else {
			System.out.println("Ya estabas conectado");
		}
		
	}
	/**
	 * Método que sirve para modificar un estudiante preexistente en la DB.
	 * Para ello, toma un Estudiante que contiene los valores que se insertarán en la DB y un long que recoge la Id del Estudiuante cuyos valores se modificarán.
	 * El proceso se realiza mediante un PreparedStatement. 
	 * @param E Estudiante que contiene los valores que se modificarán.
	 * @param ID long que recoge la Id de Usuario que se usará para buscar al Estudiante que se procederá a modificar.
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error.
	 */
	public void modificarEstudiante(Estudiante E, long ID) throws SQLException {
		if (con != null) {
			System.out.println("Estoy en DaoEstudiante --> modificarEstudiante()");
			// Orden y tipo de las columnas de la DB
			// Usuarios(ID numeric(10) not null,Nivel int(3) not null,Nombre varchar(50) not null,Apellidos varchar(50) not null,Edad int(2) not null, 
			//          Email varchar(50) not null,Pass_word varchar(50) not null,Estudios varchar(50),Escuela varchar(50),Telefono int(9),
			//          Titulo_estudios varchar(50), Lugar_titulo varchar(50),Fecha_titulo int(6),Titulo_img varchar(50),
			String sql="UPDATE symposium.usuarios SET ID=?, Nivel=?, Nombre=?, Apellidos=?, Edad=?, Email=?, Estudios=?, Escuela=? WHERE ID=?";
			System.out.println("Voy a modificar al estudiante con la ID: "+ID+" a: "+E.toString());
			PreparedStatement ps= con.prepareStatement(sql);
			ps.setLong(1, E.getId());
			ps.setInt(2, E.getNivel());
			ps.setString(3, E.getNombre());
			ps.setString(4, E.getApellidos());
			ps.setInt(5, E.getEdad());
			ps.setString(6, E.getEmail());
			if (E.getEstudios() == null || E.getEscuela() == null) {
				;
			} else {
				ps.setString(7, E.getEstudios());
				ps.setString(8, E.getEscuela());
			}
			ps.setLong(9, ID);
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
	 * Método que sirve para listar los datos de un Estudiante preexistente en la DB.
	 * Para ello, toma un log que recoge la Id de Usuario que se utilizará para buscar al estudiante cuyos datos se listarán. 
	 * El proceso se realiza mediante un PreparedStatement. 
	 * @param id long que recoge la Id de Usuario que se usará para buscar al Usuario en la DB.
	 * @return devuelve un Estudiante que contiene los valores que se han recogido de la DB.
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error.
	 */
	public Estudiante listar(long id) throws SQLException {
			
		String sql = "SELECT * FROM symposium.usuarios WHERE ID="+id;
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		
		rs.next();
		
		// Orden y tipo de las columnas de la DB.
		// Usuarios(ID numeric(10) not null, Nivel int(3) not null, Nombre varchar(50) not null, Apellidos varchar(50) not null, Edad int(2) not null,
		//          Email varchar(50) not null, Pass_word varchar(50) not null, Estudios varchar(50), Escuela varchar(50), Telefono int(9),
		//			Titulo_estudios varchar(50), Lugar_titulo varchar(50), Fecha_titulo date, Titulo_img varchar(50));
			
		Estudiante e = new Estudiante(rs.getLong(1), rs.getInt(2),rs.getString(3),rs.getString(4),rs.getInt(5), rs.getString(6), rs.getString(8), rs.getString(9));
		
		if (con!=null) {
			con.close();
			System.out.println("Se ha cerrado la conexión con la base de datos");
		}
		
		return e;
	}
	
	/**
	 * Método que sirve para recoge todas las ID's que hay en la DB. Este método se usa únicamente en el método generarId() de la clase DaoEstudiante.
	 * El proceso se realiza mediante un PreparedStatement. 
	 * @return devuelve un ArrayList con las ID's que haya recogido de la DB.
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error.
	 */
	private ArrayList<Long> seleccionarIdes() throws SQLException {
		System.out.println("Estoy en DaoEstudiante --> seleccionarIdes()");
		ArrayList <Long> ides = new ArrayList <Long>();
		if (con != null) {
			String sql = "SELECT ID FROM symposium.usuarios;";	
			System.out.println("Se ha establecido la conexión: "+con);
			PreparedStatement ps= con.prepareStatement(sql);
			System.out.println("Statement preparado");
			ResultSet rs = ps.executeQuery();
			System.out.println(rs);
			while (rs.next()) {
				ides.add(rs.getLong("ID"));
			};
			
		} else {
			System.out.println("Conecta antes al método DBConexion.conectar()");
		}
		return ides;
	}
	
	/**
	 * Método que genera una Id para un nuevo Usuario, que siempre ha de ser Estudiante. 
	 * Para ello, hace uso del método seleccionarIdes() de la clase DaoEstudiante, 
	 * y genera un número del 0 al 10000000000 (no inclusive) evitando que se repita con alguno ya presente en la DB, mediante el método antes mencionado.
	 * @return devuelve un long con una Id única y que no se repite en la DB. 
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error.
	 */
	private long generarId() throws SQLException {
			
		System.out.println("Estoy en DaoEstudiante --> generarId()");
		long id=0; boolean cell=false; int lim=0;
		ArrayList <Long> ides = seleccionarIdes();
		System.out.println("las ides recogidas son: "+ides.toString());
		do {
				
			id = (long) Math.floor(Math.random()*10000000000L);
			System.out.println("se ha generado la id: "+id);
			cell=true;
			lim=0;
			if (id<0 || id>10000000000L) {
					
				cell=false;
				
			} else {
					
				do {
					System.out.println(ides.get(lim)+" vs "+id);	
					if (id==ides.get(lim)) {
						
						cell=false;
					}
					lim++;
						
				} while (lim<(ides.size()-1));
					
			}
				
		} while (cell==false);
		System.out.println("se va a usar la ide: "+id);
		return id;
	}
	
	/**
	 * Método que sirve para insertar un nuevo estudiante en la DB.
	 * Para ello toma un Estudiante que contiene los valores que se insertarán y un String con la encriptación de la contraseña que tendrá.
	 * Además, genera una Id única para ese usuario llamando al método privado: generarId() de la clase DaoEstudiante
	 * El proceso se realiza mediante un PreparedStatement. 
	 * @param e Estudiante que contiene los valores que se insertarán en la DB.
	 * @param Pass String que recoge la encriptación, hecha mediante el método encriptar() de la clase Cifrado, de la contraseña que se insertará en la DB.
	 * @see Cifrado
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error.
	 */
	public void insertarEstudiante(Estudiante e, String Pass) throws SQLException {
		System.out.println("Estoy en DaoEstudiante --> insertarEstudiante()");
		e.setId(generarId());
		String Cont =(Pass);
	
		if (con != null) {
			String sql = "INSERT INTO symposium.usuarios (ID, Nivel, Nombre, Apellidos, Edad, Email, Pass_word) values(?,?,?,?,?,?,?)";	
			System.out.println("Se ha establecido la conexión: "+con);
			PreparedStatement ps= con.prepareStatement(sql);
			System.out.println("Statement preparado");
			ps.setLong(1, e.getId());
			ps.setInt(2, e.getNivel());
			ps.setString(3, e.getNombre());
			ps.setString(4, e.getApellidos());
			ps.setInt(5, e.getEdad());
			ps.setString(6, e.getEmail());
			ps.setString(7, Cont);
			
			System.out.println("Se va a ejecutar el comando : "+ps);

			int filas=ps.executeUpdate();
			System.out.println(filas);
			if (con!=null) {
				con.close();
				System.out.println("Se ha cerrado la conexión con la base de datos");
			}
			
		} else {
			System.out.println("Conecta antes al método DBConexion.conectar()");
		}
	}
}
