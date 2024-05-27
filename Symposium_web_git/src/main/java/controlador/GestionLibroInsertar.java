package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.Libro;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;

/**
 * Servlet implementation class GestionLibroInsertar
 */
public class GestionLibroInsertar extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestionLibroInsertar() {
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
		boolean cell=false;
		PrintWriter out=response.getWriter();
		long idLibro=0;	String nombre=""; String nomAutor=""; String texto=""; Date date; String cate=""; String editor=""; String typeof="";		
		idLibro=Long.parseLong(request.getParameter("IdLibro"));//isbn
		nombre=request.getParameter("NombreLibro");//titulo
		nomAutor=request.getParameter("NombreAutor");//autor
		texto=request.getParameter("Texto");//abstract
		date=Date.valueOf(request.getParameter("FechaLibro"));//fecha de publicacion
		cate=request.getParameter("Categoria");//categoria
		editor=request.getParameter("Edition");//editorial
		typeof="Libro";//tipo (automático en el servlet para libros)
		System.out.println(idLibro+nombre+nomAutor+texto+date+cate+editor+typeof);
		//Libro(            iSBN, abstracto, autor, titulo, tipo, fecha_publicacion, editorial, categoria)
		Libro l1=new Libro(idLibro,texto,nomAutor, nombre, typeof, date, editor, cate);
		System.out.println(l1.toString());

			try {
				cell=true;
				l1.insertarLibro();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				cell=false;
				e.printStackTrace();
			} catch (SQLException e) {
				cell=false;
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		out.print(cell);
			
		System.out.println("Libro insertado con éxito");
	}

}
