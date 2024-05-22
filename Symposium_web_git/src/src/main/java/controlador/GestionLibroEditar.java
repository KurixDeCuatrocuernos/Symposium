package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.Libro;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import DAO.DaoLibro;

/**
 * Servlet implementation class GestionLibroEditar
 */
public class GestionLibroEditar extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String gson="";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestionLibroEditar() {
        super();
        
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Aquí debería recibir la id que me envía el método llamadaModificar(id) del documento: ListarLibro.js
		
		System.out.println("Estoy en GestionLibroEditar --> doGet()");
		String json="";
		
		long idLibro=Long.parseLong(request.getParameter("id"));
		
		try {
			DaoLibro aux=new DaoLibro();
			json=aux.listarJson(idLibro);
			gson=json;
			
			System.out.println(json);
			
			doPost(request, response);
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter outside = response.getWriter();
		String json=gson;
		outside.print(json);
	}

}
