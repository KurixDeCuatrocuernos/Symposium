package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.Articulo;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;

/**
 * Servlet implementation class GestionArticuloInsertar
 */
public class GestionArticuloInsertar extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestionArticuloInsertar() {
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
		
		System.out.println("Estoy en GestionArticuloInsertar --> doPost()");
		int option=Integer.parseInt(request.getParameter("op"));
		PrintWriter out=response.getWriter();
		boolean cell=false;
		
		if (option==1) {
			
			long idArticulo=Long.parseLong(request.getParameter("IdLibro"));
			String nombre=request.getParameter("NombreArticulo");//titulo
			String nomAutor=request.getParameter("NombreAutor");//autor
			String texto=request.getParameter("Texto");//abstract
			Date date=Date.valueOf(request.getParameter("FechaLibro"));//fecha de publicacion
			String temas=request.getParameter("Temas");//temas
			String lugar=request.getParameter("LugarPublicacion");//lugar de publicacion
			String typeof="Articulo";//tipo (automático en el servlet para articulos)
			System.out.println(idArticulo+nombre+nomAutor+texto+date+temas+lugar);
			Articulo a1= new Articulo(idArticulo, texto, nomAutor, nombre, typeof, date, lugar, temas);
			System.out.println(a1.toString());
			
		} else if(option==2) {
			
			long idArticulo=Long.parseLong(request.getParameter("IdLibro"));
			String nombre=request.getParameter("NombreArticulo");//titulo
			String nomAutor=request.getParameter("NombreAutor");//autor
			String texto=request.getParameter("Texto");//abstract
			Date date=Date.valueOf(request.getParameter("FechaLibro"));//fecha de publicacion
			String temas=request.getParameter("Temas");//temas
			String lugar=request.getParameter("LugarPublicacion");//lugar de publicacion
			String volumen=request.getParameter("VolumenPublicacion");//volumen de publicacion
			String typeof="Articulo";//tipo (automático en el servlet para articulos)
			System.out.println(idArticulo+nombre+nomAutor+texto+date+temas+lugar+volumen);
			Articulo a1= new Articulo(idArticulo, texto, nomAutor, nombre, typeof, date, lugar, volumen, temas);
			System.out.println(a1.toString());
			
			try {
				cell=true;
				a1.insertarArticulo(a1);
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
			
		System.out.println("Articulo insertado con éxito");
		
		}
		
		
	}

}
