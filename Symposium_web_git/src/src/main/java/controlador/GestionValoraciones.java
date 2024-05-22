package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import DAO.DaoValoracion;

/**
 * Servlet implementation class GestionValoraciones
 */
public class GestionValoraciones extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestionValoraciones() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		int choice=0;
		long idlib=0;
		DaoValoracion auv;
		PrintWriter out= response.getWriter();
		boolean parse=false;
			
			try {
				parse=true;
				choice=Integer.parseInt(request.getParameter("op"));
				idlib=Long.parseLong(request.getParameter("idLibro"));
			} catch (NumberFormatException numEx) {
				System.out.println("Fallo al solicitar la información, revisa el fetch y la socicitud");
			}
			
			System.out.println("opción: "+choice+" isbn: "+idlib);
		if (parse==true) {
			switch(choice) {
			case 1: { // media de todas las valoraciones
				int media=0;
				try {
					auv= new DaoValoracion();
					media=auv.listarValoracionesPorTipo(idlib, 9, 50);
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Media Global: "+media);
				out.print(media);
				break;
			}
			case 2: { // media de las valoraciones de titulados
				int media=0;
				try {
					auv= new DaoValoracion();
					media=auv.listarValoracionesPorTipo(idlib, 29, 50);
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Media de Titulados: "+media);
				out.print(media);
				break;
			}
			case 3: { // media de las valoraciones de estudiantes
				int media=0;
				try {
					auv= new DaoValoracion();
					media=auv.listarValoracionesPorTipo(idlib, 9, 30);
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				System.out.println("Media de Estudiantes: "+media);
				out.print(media);
				break;
			}
			default: {
				System.out.println("Error al recoger la opción, revisa el fetch de javascript");
				break;
			}
			}
		} else {
			out.print("Error al recoger los valores");
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
