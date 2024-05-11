package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import modelo.Estudiante;


public class DaoEstudiante {
	
	private Connection con = null;
	
	public DaoEstudiante() throws SQLException, ClassNotFoundException {
		
		this.con= DBConexion.conectar();
		
	}
	
	public void modificarEstudiante(Estudiante E, long ID) throws SQLException {
		if (con != null) {
			System.out.println("Estoy en DaoEstudiante --> modificarEstudiante()");
			//Usuarios(ID numeric(10) not null,Nivel int(3) not null,Nombre varchar(50) not null,Apellidos varchar(50) not null,Edad int(2) not null, 
			//Email varchar(50) not null,Pass_word varchar(50) not null,Estudios varchar(50),Escuela varchar(50),Telefono int(9),
			//Titulo_estudios varchar(50), Lugar_titulo varchar(50),Fecha_titulo int(6),Titulo_img varchar(50),
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
			if (con!=null) {
				con.close();
				System.out.println("Se ha cerrado la conexión con la base de datos");
			}
			System.out.println("Modificación completada, salgo de DaoLibro");
		} else {
			System.out.println("ERROR, Conecta antes con el método DBConexion.conectar()");
		}
	}
	
	public Estudiante listar(long id) throws SQLException {
			
		String sql = "SELECT * FROM symposium.usuarios WHERE ID="+id;
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		
		rs.next();
//		Usuarios(ID numeric(10) not null, Nivel int(3) not null, Nombre varchar(50) not null, Apellidos varchar(50) not null, Edad int(2) not null,
//				Email varchar(50) not null, Pass_word varchar(50) not null, Estudios varchar(50), Escuela varchar(50), Telefono int(9),
//				Titulo_estudios varchar(50), Lugar_titulo varchar(50), Fecha_titulo date, Titulo_img varchar(50));
//		Estudiante(int id, int nivel, String nombre, String apellidos, int edad, String email, String estudios, String escuela)
			
		Estudiante e = new Estudiante(rs.getLong(1), rs.getInt(2),rs.getString(3),rs.getString(4),rs.getInt(5), rs.getString(6), rs.getString(8), rs.getString(9));
		
		if (con!=null) {
			con.close();
			System.out.println("Se ha cerrado la conexión con la base de datos");
		}
		
		return e;
	}
	
	public ArrayList<Long> seleccionarIdes() throws SQLException {
		System.out.println("Estoy en DaoEstudiante --> seleccionarIdes()");
		ArrayList <Long> ides = new ArrayList <Long>();
		int lim=0;
		if (con != null) {
			String sql = "SELECT ID FROM symposium.usuarios;";	
			System.out.println("Se ha establecido la conexión: "+con);
			PreparedStatement ps= con.prepareStatement(sql);
			System.out.println("Statement preparado");
			ResultSet rs = ps.executeQuery();
			lim=1;
			System.out.println(rs);
			while (rs.next()) {
				ides.add(rs.getLong(1));
				lim++;
			};
			
		}
		if (con!=null) {
			con.close();
			System.out.println("Se ha cerrado la conexión con la base de datos");
		}
		return ides;
	}
	
	public long generarId() throws SQLException {
			
		System.out.println("Estoy en DaoEstudiante --> generarId()");
		long id=0; boolean cell=false; int lim=0;
		ArrayList <Long> ides = seleccionarIdes();
		System.out.println(ides);
		do {
				
			id = (int) Math.floor(Math.random()*10000000000L);	
			cell=true;
			lim=0;
			if (id<0 || id>10000000000L) {
					
				cell=false;
				
			} else {
					
				do {
						
					if (id==ides.get(lim)) {
						cell=false;
					}
					lim++;
						
				} while (lim<(ides.size()-1));
					
			}
				
		} while (cell==false);
			
		if (con!=null) {
			con.close();
			System.out.println("Se ha cerrado la conexión con la base de datos");
		}			
		return id;
	}
	
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
		
			if (con!=null) {
				con.close();
				System.out.println("Se ha cerrado la conexión con la base de datos");
			}
			
		} else {
			System.out.println("Conecta antes al método DBConexion.conectar()");
		}
		
		
		
	}
}
