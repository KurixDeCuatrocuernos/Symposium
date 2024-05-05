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
 * Servlet implementation class GestionLibroBorrar
 */
public class GestionLibroBorrar extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestionLibroBorrar() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		long idLibro=0;
		idLibro=Long.parseLong(request.getParameter("id"));
		System.out.println(idLibro);
		try {
			System.out.println("Estoy en GestionLibroBorrar");
			DaoLibro l1=new DaoLibro();
			l1.borrarLibro(idLibro);
			
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		/*
		int idLibro=0;
		PrintWriter out = response.getWriter();
		String libro="";
		boolean comprobar=false; 
		idLibro=Integer.parseInt(request.getParameter("IdLibro"));
		Libro l1=new Libro();
		try {
			comprobar=l1.comprobarLibro(idLibro);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		if(comprobar==true) {
			boolean cell=false;
			//listamos el libro
			try {
				l1.obtenerPorId(idLibro);
				DaoLibro dao = null;
				dao = new DaoLibro();
				libro=dao.listarJson(idLibro);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("¡Se ha producido una Excepción!");
			}
			out.print(l1.dameJson());
			//response.sendRedirect("http://localhost:8080/Symposium_web/BorrarLibro.html");
			// si el usuario confirma modificamos el boleano y borramos
						cell=Boolean.parseBoolean(request.getParameter("confirm"));
						if (cell==true) {
							DaoLibro dao=null;
							dao.borrarLibro(idLibro);
						}
						else {
							System.out.println("Borrado cancelado");
						}
		} else {
			System.out.println("No se ha encontrado el Libro con el ISBN: "+idLibro);
		}*/
	
	}

}
