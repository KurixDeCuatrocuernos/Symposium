package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.Admin;
import modelo.Estudiante;
import modelo.Titulado;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import DAO.DaoLibro;
import DAO.DaoUsuario;

/**
 * Servlet implementation class GestionUsuarioModificar
 */
public class GestionUsuarioModificar extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String gson="";
	private static long ID=0;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestionUsuarioModificar() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		System.out.println("Estoy en GestionUsuarioModificar --> doGet()");
		long ideUser=Long.parseLong(request.getParameter("id"));
		String json;
		try {
		DaoUsuario aux=new DaoUsuario();
		json=aux.listarJson(ideUser);
		gson=json;
		ID=ideUser;
		System.out.println(json);
					
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		System.out.println("Se va a devolver: "+gson);
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Estoy en GestionUsuarioModificar --> doPost()");
		boolean cell=true;
		int choice=Integer.parseInt(request.getParameter("op"));
		long ideUser=ID;
		int lvl = 0;
		String Nomb="";
		String Apell="";
		String Email="";
		int Edad = 0;
		//long ideUser=ID;
		PrintWriter out=response.getWriter();
		switch (choice) {
			case 0: {
				System.out.println("Se va a devolver: "+gson);
				out.print(gson);
				break;
			}
			case 1: {
				
				lvl = Integer.parseInt(request.getParameter("Nivel"));
				Nomb=request.getParameter("Name");
				Apell=request.getParameter("Surname");
				Email=request.getParameter("Mail");
				Edad = Integer.parseInt(request.getParameter("Age"));
				Estudiante e = new Estudiante(ideUser, lvl, Nomb, Apell, Edad, Email);
				try {
					e.modificarEstudiante(e, ideUser);
				} catch (ClassNotFoundException | SQLException ex) {
					cell=false;
					ex.printStackTrace();
				}
				out.print(cell);
				break;
			
			}
			case 2: {
				lvl = Integer.parseInt(request.getParameter("Nivel"));
				Nomb=request.getParameter("Name");
				Apell=request.getParameter("Surname");
				Email=request.getParameter("Mail");
				Edad = Integer.parseInt(request.getParameter("Age"));
				String Estudios= request.getParameter("Studies");
				String Escuela= request.getParameter("School");
				Estudiante e = new Estudiante (ideUser, lvl, Nomb, Apell, Edad, Email, Estudios, Escuela);
				try {
					e.modificarEstudiante(e, ideUser);
				} catch (ClassNotFoundException | SQLException ex) {
					cell=false;
					ex.printStackTrace();
				}
				out.print(cell);
				break;
			
			}
			case 3: {
				lvl = Integer.parseInt(request.getParameter("Nivel"));
				Nomb=request.getParameter("Name");
				Apell=request.getParameter("Surname");
				Email=request.getParameter("Mail");
				Edad = Integer.parseInt(request.getParameter("Age"));
				String Title = request.getParameter("Titulo");
				String Lug = request.getParameter("Lugar");
				int anio = Integer.parseInt(request.getParameter("Year"));
				Titulado t = new Titulado(ideUser, lvl, Nomb, Apell, Edad, Email, Title, Lug, anio);
				try {
					t.modificarTitulado(t, ideUser);
				} catch (ClassNotFoundException | SQLException e) {
					cell=false;
					e.printStackTrace();
				}
				out.print(cell);
				break;
			
			}
			case 4: {
				lvl = Integer.parseInt(request.getParameter("Nivel"));
				Nomb=request.getParameter("Name");
				Apell=request.getParameter("Surname");
				Email=request.getParameter("Mail");
				int phone = Integer.parseInt(request.getParameter("Telf"));
				Edad = Integer.parseInt(request.getParameter("Age"));
				Admin a = new Admin(ideUser, lvl, Nomb, Apell, Edad, Email, phone);
				try {
					a.modificarAdmin(a, ideUser);
				} catch (ClassNotFoundException | SQLException e) {
					cell=false;
					e.printStackTrace();
				}
				out.print(cell);
				break;
			
			}
			default: {
				
				System.out.println("No se encuentra esa opción");
				
				break;
			}
		}
		
		
	}

}
