package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import DAO.DaoLibro;

/**
 * Servlet implementation class GestionVerificarObra
 */
public class GestionVerificarObra extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestionVerificarObra() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		System.out.println("Estoy en GestionVerificarObra --> doGet()");
		boolean verificar=false;
		PrintWriter out= response.getWriter();
		verificar=false;
		long idLibro=Long.parseLong(request.getParameter("id"));
		System.out.println("Procedo a verificar el Id: "+idLibro);
		try {
			DaoLibro aux=new DaoLibro();
			verificar=aux.comprobarIdLibro(idLibro);//si encuentra el libro devolverá true, si no, false
			System.out.println("El DAO ha devuelto: "+verificar);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Salgo de GestionVerificarObra -->doGet()");
		System.out.println(verificar);
		out.print(verificar);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

}
