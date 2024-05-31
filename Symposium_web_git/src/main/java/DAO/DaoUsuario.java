package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.google.gson.Gson;

import modelo.Usuario;
/**
 * Esta clase sirve para el acceso a la DB de cara al objeto Usuario, ya sea insertarlo, modificarlo, listarlo, o borrarlo.
 * @see Usuario
 * @author Alejandro Moreno
 * @version 1.0
 */
public class DaoUsuario {
	
	Connection con=null;
	/**
	 * Método constructor de la clase, en caso de que el parámetro con sea null iniciará el método conectar() de la clase DBConexion.
	 * @see DBonexion
	 * @throws ClassNotFoundException si no encuentra la clase DaoComentario lanzará un error.
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error.
	 */
	public DaoUsuario() throws SQLException, ClassNotFoundException {
		
		if(this.con==null) {
			this.con= DBConexion.conectar();
		} else {
			System.out.println("Error al conectar, antes conecta con DBConexion");
		}
		
	
	}
	/**
	 * Método que sirve para verificar si se puede borrar a un Usuario o antes hay que borrar sus Comentarios y/o Solicitudes de la DB.
	 * Para ello, toma un long que recoge la ID del Usuario en cuestión y comprueba si existe algún Comentario o Solicitud con esa ID en la DB.
	 * El proceso se realiza mediante un PreparedStatement. 
	 * @param id long que recoge la ID del Usuario que se procederá a borrar
	 * @return devuelve true si no encuentra ni comentarios ni solicitudes con esa ID en la DB y false si encuentra alguno.
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error.
	 */
	public boolean comprobarBorrado(long id) throws SQLException {
		System.out.println("Comprobando borrado");
		boolean borrar=true;
		String sql="SELECT * FROM symposium.comentarios WHERE ID=?";
		PreparedStatement ps=con.prepareStatement(sql);
		ps.setLong(1, id);
		ResultSet rs=ps.executeQuery();
		if(rs.next()) {
			borrar=false;
		}
		sql="SELECT * FROM symposium.solicitudes WHERE ID=?";
		ps=con.prepareStatement(sql);
		ps.setLong(1, id);
		rs=ps.executeQuery();
		if(rs.next()) {
			borrar=false;
		}
		if (borrar==true) {
			System.out.println("se puede borrar al usuario");
		} else {
			System.out.println("para borrar al usuario antes hay que borrar su informión, procedo a borrarla");
		}
		return borrar;
	}
	/**
	 * Método que sirve verifica si un Email existe ya en la DB.
	 * Para ello, toma un String que recoge un correo insertado por el usuario y lo busca en la DB.
	 * El proceso se realiza mediante un PreparedStatement. 
	 * @param mail String que recoge en correo que se procederá a buscar.
	 * @return devuelve true si el email no está ya en la DB y false si lo encuentra.
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error.
	 */
	public boolean verificarCorreo(String mail) throws SQLException {
		System.out.println("Estoy en DaoUsuario --> verificarCorreo()");
		boolean cell=false;
		ArrayList<String> correos = new ArrayList<String>();
		if (con!=null) {
			String sql="SELECT Email FROM symposium.usuarios";
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				correos.add(rs.getString(1));
			}
			if (correos.size()>1) {
				cell=true;
				for(int i=0;i<correos.size();i++) {
					if(correos.get(i).equals(mail)) {
						cell=false;
					}
				}
			} else {
				System.out.println("No se han encontrado correos en la base de datos");
			}
			con.close();
			System.out.println("Se ha cerrado la conexión con la base de datos");
		} else {
			System.out.println("ERROR, conecta antes con la base de datos y asegurate de haber introducido correctamente los datos");
		}
		
		return cell;
	}
	/**
	 * Método que sirve para recoger <em> todos los parámetros de un Usuario</em> a partir de un nombre, email y contraseña.
	 * Para ello, Toma un String que recoge el nombre del usuario, otro String que recoge el Email y otro String que recoge la encriptación de la contraseña.
	 * @see Cifrado
	 * El proceso se realiza mediante un PreparedStatement. 
	 * @param nombre String que recoge el nombre del Usuario.
	 * @param Correo String que recoge el correo electrónico del Usuario.
	 * @param Contra String que recoge la encriptación de la contraseña del Usuario.
	 * @return devuelve un Usuario que contiene los parámetros que se hayan encontrado en la DB.
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error.
	 */
	public Usuario recogerCredenciales(String nombre, String Correo, String Contra) throws SQLException {
		Usuario U=new Usuario();
		if(con!= null && nombre!= "" && Correo!= "" && Contra!="") {
			System.out.println("Recogiendo credenciales...");
			String sql ="SELECT ID, Nivel, Nombre, Apellidos, Edad, Email FROM symposium.usuarios WHERE Nombre=? and Email=? and Pass_word=?";
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, nombre);
			ps.setString(2, Correo);
			ps.setString(3, Contra);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				U.setId(rs.getLong("ID"));
				U.setNombre(rs.getString("Nombre"));
				U.setApellidos(rs.getString("Apellidos"));
				U.setEdad(rs.getInt("Edad"));
				U.setEmail(rs.getString("Email"));
				U.setNivel(rs.getInt("Nivel"));
			}
			con.close();
			System.out.println("Se ha cerrado la conexión con la base de datos");
		} else {
			System.out.println("ERROR, conecta antes con la base de datos y asegurate de haber introducido correctamente los datos");
		}
		return U;
	}
	/**
	 * Método que sirve para recoger el tipo de Usuario a partir de un nombre, correo y contraseña.
	 * Para ello, toma un String que recoge el nombre, otro que recoge un correo electrónico y otro que recoge la encriptación de una contraseña, 
	 * y con ellos busca en la DB a un Usuario, si existe.
	 * El proceso se realiza mediante un PreparedStatement. 
	 * @param nom String que recoge el nombre del Usuario.
	 * @param mail String que recoge el correo electrónico del Usuario.
	 * @param pass String que recoge la encriptación de la contraseña del Usuario.
	 * @return devuelve el tipo de Usuario, es decir, un número que representa si se trata de un Estudiante, Titulado o Admin. 
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error.
	 */
	public int recogerTipo(String nom, String mail, String pass) throws SQLException {
		int tipo=0;
		if (con != null && nom!= "" && mail!= "" && pass!="") {
			System.out.println("Verificando credenciales...");
			String sql ="SELECT Nivel from symposium.usuarios where Nombre=? and Email=? and Pass_word=?";
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, nom);
			ps.setString(2, mail);
			ps.setString(3, pass);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				tipo=rs.getInt("Nivel");
			}
			
			con.close();
			System.out.println("Se ha cerrado la conexión con la base de datos");
		} else {
			System.out.println("ERROR, conecta antes con la base de datos y asegurate de haber introducido correctamente los datos");
		}
		
		return tipo;
	}
	/**
	 * Método que sirve para comprobar si existe en la DB un Usuario o no.
	 * Para ello, toma un String que recoge el nombre del Usuario, otro que recoge el correo electrónico y otro que recoge la encriptación de la contraseña que servirán para buscar en la DB.
	 * El proceso se realiza mediante un PreparedStatement. 
	 * @param nombre String que recoge el nombre del Usuario.
	 * @param email String que recoge el correo electrónico del Usuario.
	 * @param Pass String que recoge la encriptación de la contraseña del Usuario.
	 * @return devuelve un boleano en función de si existe un usuario con esos parámetros, si existe devolverá true, si no, devolverá false.
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error.
	 */
	public boolean comprobarCredenciales(String nombre, String email, String Pass) throws SQLException {
		boolean cell=false;
		if (con!=null && nombre!= "" && email!= "" && Pass!="") {
			System.out.println("Comprobando credenciales");
			System.out.println("Estoy en DaoUsuario --> comprobarCredenciales()");
			String sql="SELECT Nombre, Email, Pass_word FROM symposium.usuarios where Nombre=? and Email=? and Pass_word=?;";
			PreparedStatement ps= con.prepareStatement(sql);
			ps.setString(1, nombre);
			ps.setString(2, email);
			ps.setString(3, Pass);
			ResultSet rs= ps.executeQuery();
			while(rs.next()) {
				cell=true;
			}
			con.close();
			System.out.println("Se ha cerrado la conexión con la base de datos");
		} else {
			System.out.println("ERROR, conecta antes con la base de datos o verifica la información");
		}
		return cell;
	}
	/**
	 * Método que sirve para borrar a un Usuario preexistente en la DB.
	 * Para ello, toma un long que recoge la Id del usuario que se procederá a borrar.
	 * El proceso se realiza mediante un PreparedStatement. 
	 * @param Id long que recoge la Id del usuario.
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error.
	 */
	public void borrarUsuario(long Id) throws SQLException {
		if (con!=null) {
			System.out.println("Estoy en DaoUsuario --> borrarUsuario()");
			String sql="DELETE FROM symposium.usuarios WHERE ID=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setLong(1, Id);
			System.out.println(ps);
			ps.executeUpdate();
			System.out.println("BORRADO EJECUTADO CON ÉXITO");
			
			con.close();
			System.out.println("Se ha cerrado la conexión con la base de datos");
		} else {
			System.out.println("ERROR, conecta antes con la base de datos");
		}
		
	}
	/**
	 * Método que sirve para listar todos los Usuarios que hay en la DB.
	 * El proceso se realiza mediante un PreparedStatement.
	 * @return devuelve un ArrayList que contiene los Usuarios (que contienen los valores) que se han recogido de la DB.
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error.
	 */
	public ArrayList <Usuario> listarUsuarios() throws SQLException {
		ArrayList <Usuario> usuarios=null;
		System.out.println("Estoy en DaoUsuario --> listarUsuarios()");
		if (con!=null) {
			
			String sql = "SELECT ID, Nivel, Nombre, Apellidos, Edad, Email FROM symposium.usuarios;";
			PreparedStatement ps = con.prepareStatement(sql);

			ResultSet result = ps.executeQuery();
			
			while(result.next()) {
				
				if(usuarios == null) {
					
					usuarios = new ArrayList<Usuario>();
				}
				
				// Orden y Tipo de las columnas de la DB:
				// Usuarios( ID numeric(10) not null, Nivel int(3) not null, Nombre varchar(50) not null, Apellidos varchar(50) not null,
				
					usuarios.add(new Usuario(result.getLong("ID"), result.getInt("Nivel"),result.getString("Nombre"), result.getString("Apellidos"), result.getInt("Edad"), result.getString("Email")));

			}
			
			con.close();
			System.out.println("Se ha cerrado la conexión con la base de datos");
		} else {
			System.out.println("ERROR, conecta antes con la base de datos");
		}
		
		return usuarios;
	}
	/**
	 * Método que sirve para recoger el nivel de Usuario y llamar a los métodos listar() correspondientes de cada tipo.
	 * Para ello, toma un long que recoge la Id del Usuario, y con ella busca el nivel del usuario correspondiente y redirige al método listar() de cada clase.
	 * Adicionalmente, convierte el resultado de cada método listar() a Json.
	 * El proceso se realiza mediante un PreparedStatement. 
	 * @see DaoAdmin 
	 * @see DaoEstudiante
	 * @see DaoTitulado
	 * @param id long que recoge la id del Usuario.
	 * @return devuelve un String que recoge la conversión a Json del resultado de cada método listar
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error.
	 * @throws ClassNotFoundException si no encuentra la clase DaoComentario lanzará un error.
	 */
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
		// Orden y Tipo de la DB:
		// Usuarios(ID numeric(10) not null, Nivel int(3) not null, Nombre varchar(50) not null, Apellidos varchar(50) not null, Edad int(2) not null,
		//			Email varchar(50) not null, Pass_word varchar(50) not null, Estudios varchar(50), Escuela varchar(50), Telefono int(9),
		//			Titulo_estudios varchar(50), Lugar_titulo varchar(50), Fecha_titulo date, Titulo_img varchar(50));

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
	/**
	 * Método que sirve para convertir a Json el resultado del método listarUsuario() de la clase DaoUsuario.
	 * El proceso se realiza mediante un PreparedStatement.
	 * @return devuelve un String que recoge la conversión a Json del resultado del método listarUsuario().
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error.
	 */
	public String listarUsuariosJson() throws SQLException {
		System.out.println("Estoy en DaoUsuario --> listarUsuariosJson()");

			String json = "";	
			Gson gson = new Gson();
			
			json = gson.toJson(this.listarUsuarios());
			
			return json;
	
	}
}
