package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.Usuario;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import DAO.DaoUsuario;


/**
 * Servlet implementation class GestionUsuariosListar
 */
public class GestionUsuariosListar extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestionUsuariosListar() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		System.out.println("Estoy en GestionUsuariosListar --> doGet()");
		try {
			
			PrintWriter out = response.getWriter();
			
			DaoUsuario dao = new DaoUsuario();
			
			String resultados = dao.listarUsuariosJson();
			
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
