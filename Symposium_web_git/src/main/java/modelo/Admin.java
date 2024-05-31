package modelo;

import java.sql.SQLException;

import DAO.DaoAdmin;

/**
 * Esta Clase Recoge los datos para generar un objeto Admin a partir de la clase 
 * Usuario (extiende dicha clase), para ello recoge una variable Teléfono además de las de la clase que extiende. 
 * @author Alejandro Moreno
 * @version 1.0
 * @see Usuario
 */
public class Admin extends Usuario{

	private int Telefono=0;
	/**
	 * Método constructor vacío.
	 */
	public Admin() {
		super();
	}
	/**
	 * Método constructor de la clase Admin con las propiedades de la clase Usuario y Admin (Super()+Telefono) juntas.
	 * @param id long que recoge un número entero único que se genera en el método generarId() de la clase DaoEstudiante.
	 * @see DaoEstudiante
	 * @param nivel int que recoge el nivel de privilegios del usuario (servirá de filtro para más cosas).
	 * @param nombre String que recoge el nombre del usuario.
	 * @param apellidos String que recoge los apellidos del usuario.
	 * @param edad int que recoge la edad del usuario.
	 * @param email String que recoge el correo del usuario en la aplicacion.
	 * @param password String que recoge la encriptación de la contraseña del Usuario, mediante el método encriptar() de la clase Cifrado.
	 * @see Cifrado
	 * @param telefono Int que recoge el número de teléfono del Admin.
	 */
	public Admin(long id, int nivel, String nombre, String apellidos, int edad, String email, int telefono) {
		super(id, nivel=50, nombre, apellidos, edad, email);
		this.Telefono=telefono;
	}
	/**
	 * Método Get del parámetro Telefono, muestra el valor de dicho parámetro de la clase Admin.  
	 * @return devuelve el valor del parámetro Teléfono de la clase Admin.
	 */
	public int getTelefono() {
		return Telefono;
	}
	/**
	 * Método Set del parámetro Telefono de la clase Admin. Como todo Set, sirve para establecer el valor del parámetro en cuestión, en este caso, Telefono.
	 * @param telefono parámetro que recoge el número de teléfono del objeto Admin. 
	 */
	public void setTelefono(int telefono) {
		Telefono = telefono;
	}
	
/**
 * Método para modificar a un admin ya existente. 
 * Para ello, verifica que todos los datos existan y los envía al método modificarAdmin() de la clase DaoAdmin si no hay problema.
 * @param A Admin que recoge los parámetros se utilizarán para modificar al Admin ya existente en la DB.
 * @param ID long que recoge la Id que se usará para encontrar al admin que se modificará.
 * @throws ClassNotFoundException si no encuentra la clase DaoAdmin lanzará un error.
 * @throws SQLException si no puede conectar con la base de datos SQL o si hay un error de comunicación lanzará un error.
 */
	public void modificarAdmin(Admin A, long ID) throws ClassNotFoundException, SQLException {
		boolean cell=true;
		if (A.getNombre()=="") {
			cell=false;
			System.out.println("El parámetro nombre está vacío");
		}
		if (A.getApellidos()=="") {
			cell=false;
			System.out.println("El parámetro Apellidos está vacío");
		}
		if (A.getId()<0 || A.getId()>9999999999L) {
			cell=false;
			System.out.println("El Id está vacío");
		}
		if (A.getNivel()<50) {
			cell=false;
			System.out.println("No deberías estar aquí");
		}
		if (A.getEdad()<0 || A.getEdad()>200) {
			cell=false;
			System.out.println("El campo edad no puede ser menor que 0 ni mayor que 200");
		}
		if (A.getEmail()==null) {
			cell=false;
			System.out.println("No puedes no tener correo");
		}
		if (A.getTelefono()<0 || A.getTelefono()>99999999999L) {
			cell=false;
			System.out.println("Ese número no es un teléfono");
		}
		if (cell==true) {
			DaoAdmin aux = new DaoAdmin();
			aux.modificarAdmin(A, ID);
		} else {
			System.out.println("Comprueba los valores introducidos");
		}
		
		
	}
	
	/**
	 * Metodo toString() que muestra por pantalla todos los valores del objeto Titulado correspondiente, en caso de ser necesario.
	 */
	@Override
	public String toString() {
		return "Admin [\n Id=" + Id + "\n Nivel=" + Nivel + "\n Nombre=" + Nombre + "\n Apellidos="
				+ Apellidos + "\n Edad=" + Edad + "\n Email=" + Email + "\n Telefono=" + Telefono + "]";
	}
	
	
	
	
}
