package modelo;
	/**
	 * Esta clase permite a un Estudiante generar una solicitud para ser "ascendido" a Titulado, 
	 * que un Admin debe confirmar o denegar.  
	 * @author Alejandro Moreno
	 * @version 1.0
	 */
public class SolicitudAscenso {
	
	private int IdUsuario=0;
	private String Titulo_estudios="";
	private String Lugar_estudios="";
	private int Fecha_titulo=0;
	private String Titulo_img;
	/**
	 * Método constructor vacío.
	 */
	public SolicitudAscenso() {
		
	}
	/**
	 * Método constructor con todos los atributos.
	 * @param idUsuario Int que recoge la id de usuario del solicitante.
	 * @param titulo_estudios String que recoge el nombre del título de los estudios del solicitante. 
	 * @param lugar_estudios String que recoge el nombre del lugar donde se expidió el título del solicitante. 
	 * @param fecha_titulo Int que recoge la fecha de expedición del título del solicitante.
	 * @param titulo_img String que recoge la ruta de la imagen del título, que sirve al Admin 
	 * 		  correspondiente para cotejar los datos introducidos.
	 */
	public SolicitudAscenso(int idUsuario, String titulo_estudios, String lugar_estudios, int fecha_titulo,
			String titulo_img) {
		super();
		IdUsuario = idUsuario;
		Titulo_estudios = titulo_estudios;
		Lugar_estudios = lugar_estudios;
		Fecha_titulo = fecha_titulo;
		Titulo_img = titulo_img;
	}

	public int getIdUsuario() {
		return IdUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		IdUsuario = idUsuario;
	}

	public String getTitulo_estudios() {
		return Titulo_estudios;
	}

	public void setTitulo_estudios(String titulo_estudios) {
		Titulo_estudios = titulo_estudios;
	}

	public String getLugar_estudios() {
		return Lugar_estudios;
	}

	public void setLugar_estudios(String lugar_estudios) {
		Lugar_estudios = lugar_estudios;
	}

	public int getFecha_titulo() {
		return Fecha_titulo;
	}

	public void setFecha_titulo(int fecha_titulo) {
		Fecha_titulo = fecha_titulo;
	}

	public String getTitulo_img() {
		return Titulo_img;
	}

	public void setTitulo_img(String titulo_img) {
		Titulo_img = titulo_img;
	}
	
	/**
	 * Método que verifica si el admin concede o no el ascenso al solicitante. 
	 * @return confirm Boleano que verifica si la confirmación ha tenido éxito, 
	 * 		   si es así, devolverá true, en caso contrario, devolverá false.
	 */
	public boolean confirmarAscenso() {
		boolean confirm=false;
		//revisa la decision del admin
		//si dice que sí
			//conecta con la bd
			//busca al usuario con esa id
			//genera un usuario-Titulado nuevo con la información del usuario antiguo + la proporcionada por la solicitud
		//si dice que no
			//genera un mensaje con el motivo de la denegación
		return confirm;
	}
	
	/**
	 * Método toString() que muestra por pantalla toda la informacion de la SolicitudAscenso, en caso de que fuese necesario. 
	 */
	@Override
	public String toString() {
		return "SolicitudAscenso [IdUsuario=" + IdUsuario + "\n Titulo_estudios=" + Titulo_estudios 
				+ "\n Lugar_estudios=" + Lugar_estudios + "\n Fecha_titulo=" + Fecha_titulo 
				+ "\n Titulo_img=" + Titulo_img + "]";
	}
	
}
