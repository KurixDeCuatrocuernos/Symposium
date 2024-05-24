package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.Articulo;
import modelo.Libro;
import modelo.Obra;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import com.google.gson.Gson;

import DAO.DaoArticulo;
import DAO.DaoLibro;

/**
 * Servlet implementation class GestionObraBuscar
 */
public class GestionObraBuscar extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestionObraBuscar() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		System.out.println("Estoy en GestionObraBuscar --> doGet()");
		PrintWriter out=response.getWriter();
		Obra o = new Obra();
		String petic = request.getParameter("solic");
		String obras="";
		obras=o.buscarObras(petic);
		out.print(obras);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Estoy en GestionObraBuscar --> doPost()");
		long idelibro=0;
		String tipo="";
		DaoLibro dll;
		DaoArticulo dla;
		String resultado="";
		PrintWriter out = response.getWriter();
		boolean recoger=true;
		try {
			idelibro=Long.parseLong(request.getParameter("viewkey"));
			tipo=request.getParameter("viewtype");
		} catch (NumberFormatException NumForEx) {
			recoger=false;
			NumForEx.printStackTrace();
		}
		if (recoger==true) {
			if (tipo.charAt(0)=='L'||tipo.charAt(0)=='l') {
				try {
					dll=new DaoLibro();
					resultado=dll.listarJson(idelibro);
					System.out.println(resultado);
				} catch (ClassNotFoundException | SQLException e) {
					recoger=false;
					System.out.println("Error al listar el libro");
					e.printStackTrace();
				}
			} else {
				try {
					dla= new DaoArticulo();
					resultado=dla.listarJson(idelibro);
				} catch (ClassNotFoundException | SQLException e) {
					recoger=false;
					System.out.println("Error al listar el libro");
					e.printStackTrace();
				}
			}
		} else {
			System.out.println("la Información no es válida revisa la url");
		}
		if(recoger==true) {
			out.print(resultado);
		}else {
			Gson gson= new Gson();
			resultado=gson.toJson("emptyness");
			System.out.println("Modificado a vacío");
			out.print(resultado);
		}
		
	}

}
