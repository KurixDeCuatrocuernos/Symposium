package modelo;
/**
 * Esta Clase Recoge los datos para generar un objeto Admin a partir de la clase 
 * Usuario (extiende dicha clase)
 * @author Alejandro Moreno
 * @version 1.0
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
	 * @param id int que recoge un numero entero único (autoincremental). 
	 * @param nivel int que recoge el nivel de privilegios del usuario (servirá de filtro para más cosas).
	 * @param nombre String que recoge el nombre del usuario.
	 * @param apellidos String que recoge los apellidos del usuario.
	 * @param edad int que recoge la edad del usuario.
	 * @param email String que recoge el correo del usuario en la aplicacion.
	 * @param password String que recoge la serialización de la contraseña del usuario, aunque no debería estar así.
	 * @param telefono Int que recoge el número de teléfono del Admin.
	 */
	public Admin(int id, int nivel, String nombre, String apellidos, int edad, String email, String password, int telefono) {
		super(id, nivel=50, nombre, apellidos, edad, email, password);
		this.Telefono=telefono;
	}

	public int getTelefono() {
		return Telefono;
	}

	public void setTelefono(int telefono) {
		Telefono = telefono;
	}
	
	/**
	 * Método que genera un comentario en la obra correspondiente llamando al método constructor de la clase Comentario.
	 * @return coment Boleano que recoge si la creación del comentario se ha realizado con éxito, 
	 * 		   si así ha sido, devolverá true, en caso contrario devolverá false.
	 */
	public boolean comentar() {
		boolean coment=false;
		
		return coment;
	}
	
	/**
	 * Método que permite modificar un comentario ya existente y que ha sido generado por el mismo Admin que lo modifica. 
	 * @return modif Boleano que recoge si la modificacion del comentario se ha realizado con éxito, si así ha sido devolverá true, 
	 * 		   en caso de que no se haya podido modificar, sea por la razón que fuere, devolverá false.
	 */
	public boolean modificarComentario() {
		boolean modif=false;
		
		return modif;
	}
	
	/**
	 * Método que permite al Admin borrar un Comentario de cualquier tipo de Usuario, llamando al método ----- del DAO.  
	 * @return delete Boleano que recoge si la eliminación se ha realizado con éxito, en caso de que así sea, devolverá true,
	 * 		   en caso contrario, devolverá false.
	 */
	public boolean eliminarComentario() {
		boolean delete=false;
		
		return delete;
	}
	
	/**
	 * Método que genera una respuesta, a un comentario o respuesta preexistente, llamando al método constructor de la clase Respuesta.
	 * @return answer Boleano que recoge si la creacion de la respuesta se ha realizado con éxito, si así ha sido, devolverá true, 
	 * 		   en caso contrario devolverá false.
	 */
	public boolean responder() {
		boolean answer=false;
		
		return answer;
	}
	
	/**
	 * Método que permite al Admin eliminar una respuesta de cualquier tipo de Usuario, llamando al método ----- del DAO.
	 * @return delete Boleano que recoge si la eliminación se ha realizado con éxito, en caso de que así sea, devolverá true,
	 * 		   en caso contrario, devolverá false.
	 */
	public boolean eliminarRespuesta() {
		boolean delete=false;
		
		return delete;
	}
	
	/**
	 * Método que permite al Admin suspender la cuenta de un Usuario eliminando cualquier acceso a la app.  
	 * @return susp Boleano que recoge si la suspensión se ha realizado con éxito, de ser así, devolverá true, 
	 * 		   en caso contrario, devolverá false.
	 */
	public boolean suspenderUsuario() {
		boolean susp=false;
		
		return susp;
	}
	
	/**
	 * Método que permite un Admin dar de baja a cualquier Usuario, él mismo incluido, llamando al método ----- del DAO.
	 * @return baja Boleano que recoje si la baja se ha realizado con éxito, de ser así, 
	 * 		   devolverá true, en caso contrario, devolverá false.
	 * 
	 */
	public boolean bajaUsuario() {
		boolean baja=false;
		
		return baja;
	}
	
	/**
	 * Método que permite a un Admin aceptar o denegar la solicitud de ascenso de un Estudiante a Titulado.
	 * @return confirm en caso de que la confirmación o denegación se haya realizado con éxito devolverá true, 
	 * 		   en caso contrario, devolverá false.
	 */
	public boolean titularEstudiante() {
		boolean confirm=false;
		
		return confirm;
	}
	
	/**
	  * Método que permite al Admin cerrar sesión, es decir, no aparecer con su nombre de usuario ni poder hacer lo correspondiente.
	 * @return close Boleano que recoge si el cierre de sesión se ha efectuado o no, en caso de que se haya efectuado el cierre, devolverá true, 
	 * 		   en caso contrario devolverá false.
	 */
	public boolean cerrarSesion() {
		boolean close=false;
		
		return close;
	}
	
	/**
	 * Metodo toString() que muestra por pantalla todos los valores del objeto Titulado correspondiente, en caso de ser necesario.
	 */
	@Override
	public String toString() {
		return "Admin [, Id=" + Id + ", Nivel=" + Nivel + ", Nombre=" + Nombre + ", Apellidos="
				+ Apellidos + ", Edad=" + Edad + ", Email=" + Email + ", Password=" + Password 
				+ "Telefono=" + Telefono + "]";
	}
	
	
	
	
}
