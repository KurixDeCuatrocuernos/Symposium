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
 * Servlet implementation class GestionArticuloModificar
 */
public class GestionArticuloModificar extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestionArticuloModificar() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Estoy en GestionArticuloModificar --> doGet()");
		boolean cell=false;
		PrintWriter out=response.getWriter();
		long id=0;	String nombrea=""; String nomAutor=""; String texto=""; Date date; String temas=""; String lugar=""; String typeof=""; String volumen=""; long IdOrigen=0;
		try {
			cell=true;
			id=Long.parseLong(request.getParameter("IdArticulo"));//isbn
			nombrea=request.getParameter("NombreArticulo");//titulo
			nomAutor=request.getParameter("NombreAutor");//autor
			texto=request.getParameter("Texto");//abstract
			date=Date.valueOf(request.getParameter("FechaLibro"));//fecha de publicacion
			temas=request.getParameter("Temas");//temas
			typeof=request.getParameter("Type");//tipo
			lugar=request.getParameter("LugarPublicacion");//lugar de Publicacion
			volumen=request.getParameter("VolumenPublicacion");// volumen de publicacion
			IdOrigen=Long.parseLong(request.getParameter("IdOrigen"));
			
			System.out.println(id+", "+nombrea+", "+nomAutor+", "+texto+", "+date+", "+temas+", "+lugar+", "+typeof+", "+volumen+", "+IdOrigen);
			/*Articulo(iSBN, abstracto, autor, titulo, tipo, fecha_publicacion, 
			 * lugarPublicacion, volumenPublicacion, temas)*/
			/*Articulo(iSBN, abstracto, autor, titulo, tipo, fecha_publicacion,
					lugar_publicacion, temas)*/
			Articulo a= new Articulo();
			if (volumen=="" && temas==""){
				a.setISBN(id);
				a.setAbstracto(texto);
				a.setAutor(nomAutor);
				a.setFecha_publicacion(date);
				a.setTitulo(nombrea);
				a.setLugar_publicacion(lugar);
				a.setTipo(typeof);
			} else if (volumen =="") {
				a.setISBN(id);
				a.setAbstracto(texto);
				a.setAutor(nomAutor);
				a.setFecha_publicacion(date);
				a.setTitulo(nombrea);
				a.setLugar_publicacion(lugar);
				a.setTipo(typeof);
				a.setTemas(temas);
			} else if (temas==""){
				a.setISBN(id);
				a.setAbstracto(texto);
				a.setAutor(nomAutor);
				a.setFecha_publicacion(date);
				a.setTitulo(nombrea);
				a.setLugar_publicacion(lugar);
				a.setTipo(typeof);
				a.setVolumen_publicacion(volumen);
			} else {
				a.setISBN(id);
				a.setAbstracto(texto);
				a.setAutor(nomAutor);
				a.setFecha_publicacion(date);
				a.setTitulo(nombrea);
				a.setLugar_publicacion(lugar);
				a.setTipo(typeof);
				a.setVolumen_publicacion(volumen);
				a.setTemas(temas);
			}
			
			System.out.println("Procedo a modificar la id: "+IdOrigen+" en la id: "+id);
			try {
				//Una vez recogidos los valores llamo al método modificar de la clase Libro
				a.modificarArticulo(a, IdOrigen);
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
