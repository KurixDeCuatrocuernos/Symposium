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

import DAO.DaoArticulo;
import DAO.DaoLibro;

/**
 * Servlet implementation class GestionArticuloEditar
 */
public class GestionArticuloEditar extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String gson="";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestionArticuloEditar() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		System.out.println("Estoy en GestionArticuloEditar --> doPost()");
		String json="";
		long idArticulo=Long.parseLong(request.getParameter("id"));
		System.out.println("He recogido la variable id: "+idArticulo);
		try {
			DaoArticulo aux=new DaoArticulo();
			json=aux.listarJson(idArticulo);
			gson=json;
			System.out.println(json);
			doPost(request, response);
					
		} catch (ClassNotFoundException | SQLException e) {
			
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter outside = response.getWriter();
		String json=gson;
		outside.print(json);
	}

}
