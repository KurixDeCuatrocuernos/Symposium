package DAO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.google.gson.Gson;

import modelo.Libro;

import java.sql.PreparedStatement;
/**
 * Esta clase sirve para el acceso a la DB de cara al objeto Libro, ya sea insertarlo, modificarlo, listarlo, o borrarlo.
 * @see Libro
 * @author Alejandro Moreno
 * @version 1.1
 */
public class DaoLibro {
	
	Connection con=null;
	/**
	 * Método constructor de la clase, en caso de que el parámetro con sea null iniciará el método conectar() de la clase DBConexion.
	 * @see DBonexion
	 * @throws ClassNotFoundException si no encuentra la clase DaoComentario lanzará un error.
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error.
	 */
	public DaoLibro() throws SQLException, ClassNotFoundException {
		
		if (this.con==null) {
			this.con= DBConexion.conectar();
		} else {
			System.out.println("Ya estabas conectado");
		}
		
	}
	
	/**
	 * Método que sirve para buscar el tipo de Obra de un Libro o Artículo.
	 * Para ello toma un long que recoge la ISBN/ISSN de un Libro o Artículo cuyo tipo se buscará en la DB.
	 * El proceso se realiza mediante un PreparedStatement. 
	 * @param id long que recoge el ISBN/ISSN de la obra cuyo tipo se mostrará.
	 * @return devuelve el tipo de obra que se haya obtenido, si es un libro devolverá: Libro, si es un artículo, devolverá: Artículo.
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error.
	 * @deprecated Tras la unificación de los HTML: InsertarLibro e InsertarArtículo, en InsertarObra, dejó de usarse.
	 */
	public String comprobarTipo(long id) throws SQLException {
		System.out.println("Estoy en DaoLibro --> comprobarTipo()");
		String Type="";
		if (con != null) {
			System.out.println("Voy a comprobar el tipo de la id: "+id);
			String sql= "SELECT * FROM symposium.obras WHERE ISBN="+id;
			PreparedStatement ps= con.prepareStatement(sql);
			System.out.println("Voy a ejecutar el Statement: "+ps);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				System.out.println(rs.getString("Tipo"));
				Type=rs.getString("Tipo");
			} 
			
			
		} else {
			System.out.println("ERROR, Conecta antes con el método DBConexion.conectar()");
		}
		
		return Type; 
	}
	
	/**
	 * Método que sirve para obtener el titulo de una Obra a partir de su ISBN/ISSN.
	 * El proceso se realiza mediante un PreparedStatement. 
	 * @param id long que recoge la ISBN/ISSN de la Obra cuyo titulo se buscará.
	 * @return devuelve el titulo de la Obra si la ha encontrado en la DB. 
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error.
	 * @deprecated Tras la unificación de los HTML: InsertarLibro e InsertarArtículo, en InsertarObra, dejó de usarse.
	 */
	public String obtenerTituloPorId(long id) throws SQLException {
		System.out.println("Buscando titulo...");
		String titulo="";
		boolean find=false; 
		System.out.println("Estoy en DaoLibro --> obtenerTituloPorId()");
		if (con!=null) {
			
			String sql = "SELECT Titulo FROM symposium.obras WHERE ISBN=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				find=true;
				titulo=rs.getString("Titulo");
			} 
			if (find==true) {
				System.out.println("Se ha encontrado el libro: "+titulo);
			} else {
				System.out.println("No se ha encontrado ningún libro");
			}
			
			con.close();
			System.out.println("Se ha cerrado la conexión con la base de datos");
		
		} else {
			System.out.println("ERROR, Conecta antes con el método DBConexion.conectar()");
		}
		
		
		return titulo;
	}
	
	/**
	 * Método que sirve para buscar titulos de obras que se mostrarán como sugerencias en un buscador.
	 * Para ello, recibe un String que recoge un texto con el que buscará en la DB.
	 * El proceso se realiza mediante un PreparedStatement. 
	 * @param text String que recoge la información que se usará para buscar en la DB.
	 * @return devuelve un ArrayList con las ISBN/ISSN que haya encontrado.
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error.
	 * @deprecated Se usaba antes de generar el buscador asíncrono xml en el header de los html, pero ya no se usa.
	 */
	public ArrayList<Long> comprobarObraPorNombre(String text) throws SQLException {
		System.out.println("Estoy en DaoLibro --> comprobarObraPorNombre()");
		ArrayList <Long> ides = new ArrayList <Long>();
		System.out.println("Se va a buscar la obra con el título: "+text);
		if (con!= null) {
			
			String sql= "SELECT ISBN FROM symposium.obras WHERE Titulo like UPPER('%"+text+"%');";
			
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				ides.add(rs.getLong("ISBN"));
			}
			
				
			con.close();
			System.out.println("Se ha cerrado la conexión con la base de datos");
		} else {
			System.out.println("ERROR, Conecta antes con el método DBConexion.conectar()");
		}
		
		return ides;
	}
	
	/**
	 * Método que sirve para modificar un Libro preexistente en la DB.
	 * Para ello toma un Libro que contiene los valores que se modificarán y un long que recoge la ISBN/ISSN que se usará para buscar el Libro que se modificará.
	 * El proceso se realiza mediante un PreparedStatement. 
	 * @param l1 Libro que contiene los valores que se modificarán.
	 * @param IdOrigen long que recoge la ISBN/ISSN original del libro, que se usará para buscar el libro que se modificará.
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error.
	 */
	public void modificarLibro(Libro l1, long IdOrigen) throws SQLException {
		if (con!=null) {
			String sql="UPDATE symposium.obras SET ISBN=?, Abstracto=?, Autor=?, Titulo=?, Fecha_publicacion=?, Categoria=?, Editorial=?, Tipo=? WHERE ISBN=?;";//QUERY
			PreparedStatement ps=con.prepareStatement(sql);
			System.out.println("Statement modificar preparado");
			ps.setLong(1, l1.getISBN());
			ps.setString(2, l1.getAbstracto());
			ps.setString(3, l1.getAutor());
			ps.setString(4, l1.getTitulo());
			ps.setDate(5, l1.getFecha_publicacion());
			ps.setString(6, l1.getCategoria());
			ps.setString(7, l1.getEditorial());
			ps.setString(8, l1.getTipo());
			ps.setLong(9, IdOrigen);
			
			System.out.println("Se va a ejecutar el siguiente Statement: "+ps);
			
			int filas=ps.executeUpdate();
			System.out.println(filas);
			con.close();
			System.out.println("Se ha cerrado la conexión con la base de datos");
			System.out.println("Modificación completada, salgo de DaoLibro");
		}
		else {
			System.out.println("ERROR, Conecta antes con el método DBConexion.conectar()");
		}
		
		
	}
	
	/**
	 * Método que sirve para insertar un nuevo Libro en la DB.
	 * Para ello, toma un Libro que contiene los valores que se insertarán en la DB.
	 * El proceso se realiza mediante un PreparedStatement. 
	 * @param l Libro que contiene los valores que se procederá a insertar en la DB.
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error.
	 */
	public void insertarLibro(Libro l) throws SQLException {
		if(con!=null) {
			String sql = "INSERT INTO symposium.obras (ISBN,Abstracto,Autor,Titulo,Fecha_publicacion,Categoria,Editorial,Tipo) VALUES (?,?,?,?,?,?,?,?)";
			System.out.println("Se ha establecido la conexión: "+con);
			PreparedStatement ps= con.prepareStatement(sql);
			System.out.println("Statement preparado");
			ps.setLong(1, l.getISBN());
			ps.setString(2, l.getAbstracto());
			ps.setString(3, l.getAutor());
			ps.setString(4, l.getTitulo());
			ps.setDate(5, l.getFecha_publicacion());
			ps.setString(6, l.getCategoria());
			ps.setString(7, l.getEditorial());
			ps.setString(8, "Libro");
			System.out.println("Se va a ejecutar el comando : "+ps);

			int filas=ps.executeUpdate();
			System.out.println(filas);
			con.close();
			System.out.println("Se ha cerrado la conexión con la base de datos");
			System.out.println("Conexión: "+con+". Cerrada");
			
		} else {
			System.out.println("Conecta antes al método DBConexion.conectar()");
		}
		
		
	}
	
	/**
	 * Método que sirve para verificar si una ISBN/ISSN existe o no debtro de la DB.
	 * Para ello, toma un long que recoge la ISBN/ISSN que se procederá a buscar.
	 * El proceso se realiza mediante un PreparedStatement. 
	 * @param id long que recoge la ISBN/ISSN que se buscará
	 * @return devuelve un boleano en función de si se ha encontrado o no la Obra, si se ha encontrado devolverá true, si no, devolverá false.
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error.
	 */
	public boolean comprobarIdLibro(long id) throws SQLException {
		boolean cell=false;
		if (con!=null) {
			String sql="SELECT ISBN FROM symposium.obras WHERE ISBN = "+id+";";
			Statement st=con.createStatement();
			System.out.println("Statement: "+st+" preparado");
			ResultSet rs=st.executeQuery(sql);
			System.out.println("Statement ejecutado con éxito");
			
			if (rs.next()!=false) {
				cell=true;
				System.out.println("Se ha encontrado el Libro con el ISBN: "+id);
			} else {
				System.out.println("Se ha buscado, pero no se ha encontrado el libro con el ISBN: "+id);
			}
		
			con.close();
			System.out.println("Se ha cerrado la conexión con la base de datos");
		} else {
			System.out.println("Conecta antes al método DBConexion.conectar()");
		}
		System.out.println("Se ha cerrado la conexión: "+con);
		return cell;
	}
	
	/**
	 * Método que sirve para listar un libro <em>a partir de su ISBN/ISSN</em>.
	 * Para ello, toma un long que recoge la ISBN/ISSN del Libro en cuestión.
	 * El proceso se realiza mediante un PreparedStatement.
	 * @param id long que recoge la ISBN/ISSN del libro que se procederá a listar.
	 * @return devuelve un Libro que contiene los valores que se listarán.
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error.
	 * @deprecated Antes de implementar el buscador asíncrono se usaba, pero ya no.
	 */
	public Libro listarLibroPorId(long id) throws SQLException {
		Libro l1=new Libro();
		if (con!=null) {
			String sql="SELECT * FROM symposium.obras WHERE ISBN = ?;";
			System.out.println("Se ha establecido la conexión: "+con);
			PreparedStatement ps= con.prepareStatement(sql);
			System.out.println("Statement preparado");
			ps.setLong(1, id);
			System.out.println("Se va a ejecutar el comando : "+ps);
			
			ResultSet rs=ps.executeQuery();
			rs.next();
			l1.setISBN(rs.getLong(1));
			l1.setAbstracto(rs.getString(2));
			l1.setTitulo(rs.getString(4));
			l1.setAutor(rs.getString(3));
			l1.setTipo(rs.getString(5));
			l1.setFecha_publicacion(rs.getDate(6));
			l1.setCategoria(rs.getString(8));
			l1.setEditorial(rs.getString(12));
			
			con.close();
			System.out.println("Se ha cerrado la conexión con la base de datos");
		} else {
			System.out.println("Conecta antes al método DBConexion.conectar()");
		}
		return l1;
	}
	
	/**
	 * Método que sirve para borrar un libro preexistente en la DB.
	 * Para ello, toma un long que recoge la ISBN/ISSN del Libro que se procederá a borrar.
	 * El proceso se realiza mediante un PreparedStatement.
	 * @param id long que recoge la ISBN/ISSN que se usará para buscar el libro que se procederá a borrar.
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error.
	 */
	public void borrarLibro(long id) throws SQLException  {
		if (con!=null) {
			String sql="DELETE FROM symposium.obras WHERE ISBN=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setLong(1, id);
			System.out.println(ps);
			ps.executeUpdate();
			System.out.println("BORRADO EJECUTADO CON ÉXITO");
		
		
			con.close();
			System.out.println("Se ha cerrado la conexión con la base de datos");
		} else {
			System.out.println("Conecta antes al método DBConexion.conectar()");
		}
	}
	
	/**
	 * Método que sirve para listar un libro preexistente en la DB.
	 * Para ello, toma un long que recoge la ISBN/ISSN del libro que se listará.
	 * El proceso se realiza mediante un PreparedStatement.
	 * @param id long que quecoge la ISBN/ISSN del libro que se procederá a listar.
	 * @return devuelve un Libro que contiene los valores que se listarán.
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error.
	 */
	public Libro listar(long id) throws SQLException{
		System.out.println("Estoy en DaoLibro --> listar()");
		Libro u=new Libro();
		if (con!=null) {
			String sql = "SELECT * FROM symposium.obras WHERE ISBN="+id;
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				u = new Libro(rs.getLong(1), rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5), rs.getDate(6), rs.getString(12), rs.getString(8));
				System.out.println("libro insertado");
			} else {
				System.out.println("No hay objetos con esa id");
			}
			
			// Orden y tipo de las columnas de la DB:
			// BD: [1] ISBN int(13), [2] Abstracto text, [3] Autor varchar(50), [4] Titulo varchar(50), [5] Tipo varchar(15), 
			// 	   [6] Fecha_publicacion date, [7] Bibliografia text, [8] Categoria varchar(50), [9] Lugar_publicacion varchar(50), 
			//     [10] Volumen_publicacion int(5), [11] Temas text, [12] Editorial varchar(50), [13] Valoracion_global int(3)
				
			con.close();
			System.out.println("Se ha cerrado la conexión con la base de datos");
		} else {
			System.out.println("Conecta antes al método DBConexion.conectar()");
		}
				
		return u;
	}
	
	/**
	 * Método que sirve para listar todos los libros que hay en la DB.
	 * Para ello, inicializa un ArrayList al que se añaden los libros que se van generando con los valores recogidos de la DB.
	 * El proceso se realiza mediante un PreparedStatement.
	 * @return devuelve un ArrayList que contiene Libros (que contienen los valores) que se recogen de la DB.
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error.
	 */
	public ArrayList <Libro> listarLibros() throws SQLException {
		ArrayList <Libro> libros=new ArrayList<Libro>();
		if (con!=null ) {
			String sql = "SELECT ISBN, Autor, Titulo, Tipo, Fecha_publicacion, Editorial, Lugar_publicacion FROM symposium.obras;";
			
			PreparedStatement ps = con.prepareStatement(sql);
			
			ResultSet result = ps.executeQuery();
			
			while(result.next()) {
				
				// Orden y Tipo de las columnas de la DB:
				// BD: [1] ISBN int(13), [2] Abstracto text, [3] Autor varchar(50), [4] Titulo varchar(50), [5] Tipo varchar(15), 
				//     [6] Fecha_publicacion date, [7] Bibliografia text, [8] Categoria varchar(50), [9] Lugar_publicacion varchar(50), 
				//     [10] Volumen_publicacion int(5), [11] Temas text, [12] Editorial varchar(50), [13] Valoracion_global int(3)
				
				if (result.getString("Tipo").charAt(0)=='A') { //en caso de que fuera un artículo listamos su lugar de publicacion
					libros.add(new Libro(result.getLong("ISBN"), "", result.getString("Autor"),result.getString("Titulo"), result.getString("Tipo"),result.getDate("Fecha_publicacion"),result.getString("Lugar_publicacion"),""));
					
				} else {
				
					libros.add(new Libro(result.getLong("ISBN"), "", result.getString("Autor"),result.getString("Titulo"), result.getString("Tipo"),result.getDate("Fecha_publicacion"),result.getString("Editorial"),""));
				
				}
			}
		
			con.close();
			System.out.println("Se ha cerrado la conexión: "+con);
			
		} else {
			System.out.println("Conecta antes al método DBConexion.conectar()");
		}
		
		return libros;
	}
	
	/**
	 * Este método permite generar un ArrayList de Libros a partir de los datos de otro ArrayList de longs que recogen la ISBN/ISSN de una Obra.
	 * Para ello, toma un ArrayList de longs, los cuales sirven para buscar cada obra en la DB y recoger los datos con los que se generan los libros del ArrayList final.
	 * El proceso se realiza mediante un PreparedStatement (que se ejecuta en bucle).
	 * @param ides ArrayList que contiene longs que recogen la ISBN/ISSN de un libro que servirá para buscar los datos que se insertarán en los libros del ArrayList de Libros.
	 * @return devuelve un ArrayList que contiene Libros (que contienen los datos) recogidos de la DB.
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error.
	 * @deprecated Se usaba antes de implementar el buscador asíncrono con XML, pero ya no (además daba problemas con la conexión a la DB).
	 */
	public ArrayList<Libro> listarObrasPorIdes(ArrayList<Long> ides) throws SQLException{
		ArrayList <Libro> libros = new ArrayList<Libro>();
		if (!con.isClosed()) {
			String sql = "SELECT ISBN, Titulo, Autor FROM symposium.obras WHERE ISBN=?";
			PreparedStatement ps = con.prepareStatement(sql);
			for (int i=0; i<ides.size(); i++) {
				ps.setLong(1, ides.get(i));
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					Libro l = new Libro();
					l.setISBN(rs.getLong("ISBN"));
					l.setTitulo(rs.getString("Titulo"));
					l.setAutor(rs.getString("Autor"));
					libros.add(l);
				}
			}
		} else {
			System.out.println("Conecta antes al método DBConexion.conectar()");
		}
		
		return libros; 
	} 
	
	/**
	 * Método que sirve para convertir a Json el resultado que devuelve el método: listarLibros() de la clase DaoLibro.
	 * El proceso se realiza mediante un PreparedStatement.
	 * @return devuelve un String que recoge la conversión a Json del resultado del método: listarLibros().
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error.
	 */
	public String listarLibrosJson() throws SQLException {
		
		String json = "";	
		Gson gson = new Gson();
		
		json = gson.toJson(this.listarLibros());
		
		if (con!=null) {
			con.close();
			System.out.println("Se ha cerrado la conexión con la base de datos");
		}
		return json;
	
	}
	
	/**
	 * Método que sirve para convertir aJson el resultado del método: listarObrasPorIdes() de la clase DaoLibro.
	 * Para ello, toma un ArrayList de longs que envía al método: listarObrasPorIdes()
	 * El proceso se realiza mediante un PreparedStatement.
	 * @param id ArrayList de longs que recogen la ISBN/ISSN de una obra concreta.
	 * @return devuelve un String que recoge la conversión a Json del resultado del método: listarObrasPorIdes().
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error.
	 * @deprecated Se usaba antes de implemenentar el buscador asíncrono con XML, pero ya no.
	 */
	public String listarJsonConcatenado(ArrayList<Long> id) throws SQLException {
		String json = "";	
		Gson gson = new Gson();
		
		json += gson.toJson(this.listarObrasPorIdes(id));
		
		if (con!=null) {
			con.close();
			System.out.println("Se ha cerrado la conexión con la base de datos");
		}
		return json;
	}
	
	/**
	 * Método que sirve para convertir a Json el resultado del método: listar() de la clase DaoLibro. 
	 * Para ello, toma un long que recoge un ISSN/ISSN y lo envvía al método: listar().
	 * @param id long que recoge la ISBN/ISSN de un Libro.
	 * @return Devuelve un String que recoge la conversión a Json del resultado del método: listar().
	 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error.
	 */
	public String listarJson(long id) throws SQLException {
			
			String json = "";	
			Gson gson = new Gson();
			
			json = gson.toJson(this.listar(id));
			
			if (con!=null) {
				con.close();
				System.out.println("Se ha cerrado la conexión con la base de datos");
			}
			return json;
		
		}
}
