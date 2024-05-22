package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import DAO.DaoLibro;

/**
 * Servlet implementation class GestionLibroListar
 */
public class GestionLibroListar extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HttpSession sesion;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestionLibroListar() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		sesion = request.getSession();
		
		try {
			
			PrintWriter out = response.getWriter();
			
			DaoLibro dao = new DaoLibro();
			
			String resultados = dao.listarLibrosJson();
			
			System.out.println(resultados);
			
			
			out.print(resultados);
			
		} catch (SQLException ex) {
			
			ex.printStackTrace();
		} catch (ClassNotFoundException ex2) {
			
			ex2.printStackTrace();
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
