package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.Estudiante;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

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
		Cifrado con= new Cifrado();
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
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
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
