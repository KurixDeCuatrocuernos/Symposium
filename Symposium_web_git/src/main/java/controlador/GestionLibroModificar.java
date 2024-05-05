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
 * Servlet implementation class GestionLibroModificar
 */
public class GestionLibroModificar extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestionLibroModificar() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Estoy en GestionLibroModificar --> doGet()");
		boolean cell=false;
		PrintWriter out=response.getWriter();
		long idLibro=0;	String nombre=""; String nomAutor=""; String texto=""; Date date; String cate=""; String editor=""; String typeof=""; long IdOrigen=0;
		try {
			cell=true;
			idLibro=Long.parseLong(request.getParameter("IdLibro"));//isbn
			nombre=request.getParameter("NombreLibro");//titulo
			nomAutor=request.getParameter("NombreAutor");//autor
			texto=request.getParameter("Texto");//abstract
			date=Date.valueOf(request.getParameter("FechaLibro"));//fecha de publicacion
			cate=request.getParameter("Categoria");//categoria
			typeof=request.getParameter("Type");
			editor=request.getParameter("Edition");//editorial
			IdOrigen=Long.parseLong(request.getParameter("IdOrigen"));
			
			System.out.println(idLibro+", "+nombre+", "+nomAutor+", "+texto+", "+date+", "+cate+", "+typeof+", "+editor+", "+IdOrigen);
			//Libro(int iSBN, String abstracto, String autor, String titulo, String tipo, Date fecha_publicacion, String editorial, String categoria)
			Libro l1 = new Libro(idLibro, texto, nomAutor, nombre, typeof, date, editor, cate);
			System.out.println("Procedo a modificar la id: "+IdOrigen+" en la id: "+idLibro);
			try {
				//Una vez recogidos los valores llamo al método modificar de la clase Libro
				l1.modificarLibro(l1, IdOrigen);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (Exception ex) {
			cell=false;
		}
		
		out.print(cell);
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
