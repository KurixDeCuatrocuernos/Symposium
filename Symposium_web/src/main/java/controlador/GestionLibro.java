package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.Libro;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

/**
 * Servlet implementation class GestionLibro
 */
public class GestionLibro extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestionLibro() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int idLibro=Integer.parseInt(request.getParameter("IdLibro"));//isbn
		String nombre=request.getParameter("NombreLibro");//titulo
		String nomAutor=request.getParameter("NombreAutor");//autor
		String texto=request.getParameter("Texto");//abstract
		Date date=Date.valueOf(request.getParameter("FechaLibro"));//fecha de publicacion
		String cate=request.getParameter("Categoria");//categoria
		String editor=request.getParameter("Edition");//editorial
		String typeof="Libro";//tipo (automático en el servlet para libros)
		System.out.println(idLibro+nombre+nomAutor+texto+date+cate+editor+typeof);
		//Libro(            iSBN, abstracto, autor, titulo, tipo, fecha_publicacion, editorial, categoria)
		Libro l1=new Libro(idLibro,texto,nomAutor, nombre, typeof, date, editor, cate);
		System.out.println(l1.toString());
		
		try {
			l1.insertarLibro();
		} catch (ClassNotFoundException classEx) {
			// TODO Auto-generated catch block
			classEx.printStackTrace();
		} catch (SQLException sqlEx) {
			sqlEx.printStackTrace();
		}
		System.out.println("Libro insertado con éxito");
	}

}
