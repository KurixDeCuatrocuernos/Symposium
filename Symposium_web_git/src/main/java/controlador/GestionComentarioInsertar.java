package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modelo.Comentario;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDateTime;

import DAO.DaoComentario;
import DAO.DaoValoracion;

/**
 * Servlet implementation class GestionComentarioInsertar
 */
public class GestionComentarioInsertar extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HttpSession sesion2;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestionComentarioInsertar() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());

		sesion2=request.getSession();
		PrintWriter out= response.getWriter();
		boolean respuesta=true;
		if (sesion2 != null) { 
			int choice= Integer.parseInt(request.getParameter("op"));
			long ide=0;
			long idAutor=0;
			//genero aquí el dao y lo inicializo en cada case del switch para evitar problemas con la conexion
			DaoComentario auc;
			
			switch (choice) {
				case 1:{ //insertar comentario
					try {
						idAutor= (long) sesion2.getAttribute("ide");
						ide= Long.parseLong(request.getParameter("obra")); 
					} catch (NumberFormatException numEx) {
						numEx.printStackTrace();
					}
					try {
						auc=new DaoComentario();
						respuesta=auc.comprobarComentario(idAutor, ide);
					} catch (ClassNotFoundException | SQLException e) {
						e.printStackTrace();
						respuesta=false;
					}
					
					if (respuesta==true) {
						String Title="";
						String Abstra="";
						int Value=0;
						LocalDateTime fecha = LocalDateTime.now();
						try {
							Title = request.getParameter("Titulo");
							Abstra = request.getParameter("Text");
							Value= Integer.parseInt(request.getParameter("Valor"));
							int hora= Integer.parseInt(request.getParameter("hora"));
							int minutes= Integer.parseInt(request.getParameter("min"));
							int seconds= Integer.parseInt(request.getParameter("sec"));
							int anio= Integer.parseInt(request.getParameter("Year"));
							int month= (Integer.parseInt(request.getParameter("mes")))+1;
							int day= Integer.parseInt(request.getParameter("dia"));
							fecha = LocalDateTime.of(anio, month, day, hora, minutes, seconds);
						} catch (NumberFormatException numEx) {
							respuesta=false;
							numEx.printStackTrace();
						}
						
						Comentario c= new Comentario(fecha, Title, Abstra, Value, idAutor, ide);
						
						try {
							auc= new DaoComentario();
							auc.insertarComentario(c);
						} catch (SQLException e) {
							respuesta=false;
							e.printStackTrace();
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
							respuesta=false;
						}
						
						try {
							DaoValoracion auv= new DaoValoracion();
							auv.insertarValoracion(c);
						}catch (SQLException e) {
							e.printStackTrace();
							respuesta=false;
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
							respuesta=false;
						}
						System.out.println("COMENTARIO INSERTADO");
						
					} else {
						System.out.println("Ese usuario ya ha comentado esa obra");
						respuesta=false;
					}
					
					out.print(respuesta);
					break;
				}
				case 2: { //borrar comentario
					try {
						idAutor= (long) sesion2.getAttribute("ide");
						ide= Long.parseLong(request.getParameter("obra")); 
					} catch (NumberFormatException numEx) {
						numEx.printStackTrace();
					}
					try {
						auc=new DaoComentario();
						respuesta=auc.comprobarComentario(idAutor, ide);
					} catch (ClassNotFoundException | SQLException e) {
						e.printStackTrace();
						respuesta=false;
					}
					if (respuesta == true) {
						try {
							auc=new DaoComentario();
							auc.borrarComentario(idAutor, ide);
							DaoValoracion auv = new DaoValoracion();
							auv.borrarValoracion(idAutor, ide);
						} catch (SQLException e) {
							respuesta=false;
							e.printStackTrace();
						} catch (ClassNotFoundException e) {
							respuesta=false;
							e.printStackTrace();
						}
						System.out.println("Comentario Borrado");
					} else {
						System.out.println("No se ha encontrado ese comentario");
						respuesta=false;
					}
					out.print(respuesta);
					break;
				}
				case 3: {//modificar comentario
					try {
						idAutor= (long) sesion2.getAttribute("ide");
						ide= Long.parseLong(request.getParameter("obra")); 
					} catch (NumberFormatException numEx) {
						numEx.printStackTrace();
					}
					break;
				}
				case 4: { //listar comentarios de estudiantes
					
					String result="Empty";
					try {
						ide= Long.parseLong(request.getParameter("obra")); 
					} catch (NumberFormatException numEx) {
						numEx.printStackTrace();
					}
					try {
						auc=new DaoComentario();
						result = auc.listarJson(ide, 10);
					} catch (SQLException e) {
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					out.print(result);
					break;
				}
				case 5: { // listar comentarios de titulados
					
					String result="Empty";
					try {
						ide= Long.parseLong(request.getParameter("obra")); 
					} catch (NumberFormatException numEx) {
						numEx.printStackTrace();
					}
					try {
						auc=new DaoComentario();
						result = auc.listarJson(ide, 30);
					} catch (SQLException e) {
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					out.print(result);
					
					break;
				}
				case 6: { // listar comentarios de administrador
					
					String result="Empty";
					try {
						ide= Long.parseLong(request.getParameter("obra")); 
					} catch (NumberFormatException numEx) {
						numEx.printStackTrace();
					}
					try {
						auc=new DaoComentario();
						result = auc.listarJson(ide, 50);
					} catch (SQLException e) {
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					out.print(result);
					
					break;
				}
				case 7: {	//listar comentarios de un mismo usuario
					
					String result="Empty";
					try {
						idAutor= (long) sesion2.getAttribute("ide");
						ide= Long.parseLong(request.getParameter("obra")); 
					} catch (NumberFormatException numEx) {
						numEx.printStackTrace();
					}
					try {
						auc=new DaoComentario();
						result = auc.listarJsonUser(idAutor);
					} catch (SQLException e) {
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					out.print(result);
					
					break;
				}
				default: {
					System.out.println("No se ha encontrado esa opción, revisa el Fetch de javascript");
					break;
				}
			}
		
			
		} else {
			System.out.println("ERROR DE SESIÓN, CIERRA SESIÓN Y VUELVE A INICIARLA");
		}
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}