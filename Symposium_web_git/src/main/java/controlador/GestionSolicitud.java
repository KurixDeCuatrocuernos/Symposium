package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modelo.SolicitudAscenso;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDate;

import DAO.DaoSolicitud;
import DAO.DaoTitulado;

/**
 * Servlet implementation class GestionSolicitud
 */
public class GestionSolicitud extends HttpServlet {
	private static final long serialVersionUID = 1L;
    HttpSession sesion;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestionSolicitud() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		sesion=request.getSession();
		int choice=0;
		PrintWriter out= response.getWriter();
		DaoSolicitud aux;
		try {
			choice=Integer.parseInt(request.getParameter("op"));
		} catch (NumberFormatException numEx) {
			System.out.println("No se ha podido recuperar la informacion, revisa el fetch y/o la solicitud");
		}
		switch (choice) {
			case 1: { //insertar Solicitud
				boolean insert= false;
				if(sesion!=null) {
					String titulo=""; String Lugar=""; int Year=0; int Month=0; int Day=0;
					long ID=0;
					try {
						titulo=request.getParameter("Title");
						Lugar=request.getParameter("lugar");
						Year=Integer.parseInt(request.getParameter("year"));
						Month=Integer.parseInt(request.getParameter("mes"));
						Day=Integer.parseInt(request.getParameter("dia"));
						//utilizo el parseo corto porque no funciona
						ID=(long)sesion.getAttribute("ide");
					} catch(NumberFormatException numEx) {
						System.out.println("Revisa la solicitud o el fetch de Javascript");
					}
					LocalDate fecha= LocalDate.of(Year, Month, Day);
					
					SolicitudAscenso S = new SolicitudAscenso();
					S.setFecha_titulo(fecha);
					S.setLugar_estudios(Lugar);
					S.setTitulo_estudios(titulo);
					S.setIdUsuario(ID);
					try {
						insert=true;
						aux=new DaoSolicitud();
						insert=aux.insertarSolicitud(S);
					} catch(SQLException sqlEx) {
						insert=false;
						sqlEx.printStackTrace();
					} catch(ClassNotFoundException CNFEx) {
						insert=false;
						CNFEx.printStackTrace();
					}
					
				} else {
					System.out.println("Inicia Sesión antes de solicitar el ascenso");
					insert=false;
				}
				
				out.print(insert);

				break;
			}
			case 2: { //revisarsolicitudes
				boolean solicitudes=false;
				try {
					aux=new DaoSolicitud();
					solicitudes=aux.buscarSolicitudes();
				} catch (ClassNotFoundException e) {
					
					e.printStackTrace();
				} catch (SQLException e) {

					e.printStackTrace();
				}
				
				
				out.print(solicitudes);
				break;
			}
			case 3: { //listar Solicitudes
				long idUser=0;
				String respuesta="";
				try {
					idUser=Long.parseLong(request.getParameter("id"));
				} catch (NumberFormatException NFEx) {
					System.out.println("No se ha podido recoger la id, revisa el fetch y/o el tpo de información");
				}
						
				try {
					aux= new DaoSolicitud();
					respuesta=aux.listarJson(idUser);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Datos trasformados: "+respuesta);
				out.print(respuesta);
				break;
			}
			case 4: { //aceptar solicitud
				long id=0;
				SolicitudAscenso s=new SolicitudAscenso();
				DaoTitulado aut;
				boolean cell=true;
				try {
					id=Long.parseLong(request.getParameter("id"));
				} catch (NumberFormatException numEx) {
					cell=false;
					numEx.printStackTrace();
					System.out.println("No se ha podido recuperar la información de id, revisa el fetch y/o el modo de parsear la información.");
				}
				try {
					aux = new DaoSolicitud();
					s=aux.aceptarSolicitud(id);
				} catch(ClassNotFoundException | SQLException ex) {
					ex.printStackTrace();
				}
				
				try {
					aut = new DaoTitulado();
					aut.acenderEstudianteATitulado(s);
				} catch (ClassNotFoundException | SQLException ex2) {
					cell=false;
					ex2.printStackTrace();
				}
				try {
					aux=new DaoSolicitud();
					aux.borrarSolicitud(id);
				} catch (ClassNotFoundException | SQLException ex3) {
					cell=false;
					ex3.printStackTrace();
				}
				if (cell==false) {
					System.out.println("Error al aceptar la solicitud");
				}
				out.print(cell);
				
 				break;
			}
			case 5: { //denegar Solicitud
				boolean cell=true;
				long id=0;
				try {
					id=Long.parseLong(request.getParameter("id"));
				} catch (NumberFormatException numEx) {
					cell=false;
					numEx.printStackTrace();
					System.out.println("No se ha podido recuperar la información de id, revisa el fetch y/o el modo de parsear la información.");
				}
				try {
					aux=new DaoSolicitud();
					aux.borrarSolicitud(id);
				} catch (ClassNotFoundException | SQLException ex3) {
					cell=false;
					ex3.printStackTrace();
				}
				
				out.print(cell);
				break;
			}
			default: {
				System.out.println("Error al recoger la eleccion choice, revisa el fetch");
				break;
			}
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
