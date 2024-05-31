package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.Cifrado;
import modelo.Estudiante;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import DAO.DaoUsuario;

/**
 * Servlet implementation class GestionEstudianteInsertar
 */
public class GestionEstudianteInsertar extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestionEstudianteInsertar() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		System.out.println("Estoy en GestionEstudianteInsertar --> doGet()");
		PrintWriter out = response.getWriter();
		
		boolean cell=false;
		String Nom = "";
		String Apell = "";
		int Ed= 0;
		String Mal= "";
		String Con= "";
		//Name='+Name+'&Surname='+SurN+'&Age='+Age+'&Mail='+Mail+'&Pass='+Pass
		
		Nom = request.getParameter("Name");
		Apell = request.getParameter("Surname");
		Ed = Integer.parseInt(request.getParameter("Age"));
		Mal = request.getParameter("Mail");
		Con = Cifrado.encriptar(request.getParameter("Pass"));
		
		if (Nom == "" || Apell=="" || Ed<0 || Ed>200 || Ed==0 || Mal=="" || Con == "") {
			cell=false;
		} else {
			cell=true;
			//Estudiante(id, nivel, nombre, apellidos, edad, email) & password
			Estudiante e = new Estudiante(0, 10, Nom, Apell, Ed, Mal);
			try {
				e.registrarEstudiante(e, Con);
			} catch (ClassNotFoundException | SQLException e1) {
				cell=false;
				e1.printStackTrace();
			}
		}
		out.print(cell);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//doGet(request, response);
		String email="";
		PrintWriter out=response.getWriter();
		DaoUsuario aux;
		boolean cell=false;
		try {
			email=request.getParameter("mail");
		} catch (Exception ex) {
			
			ex.printStackTrace();
		}
		if (email!="") {
			try {
				aux=new DaoUsuario();
				cell=aux.verificarCorreo(email);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			
		}else {
			System.out.println("El parámetro no se ha podido recoger, error: ");
		}
		
		out.print(cell);
		
	}

}
