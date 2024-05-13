package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modelo.Estudiante;
import modelo.Usuario;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import DAO.DaoEstudiante;
import DAO.DaoUsuario;

/**
 * Servlet implementation class GestionInicioSesion
 */
public class GestionInicioSesion extends HttpServlet {
	private static final long serialVersionUID = 1L;
    HttpSession sesion;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestionInicioSesion() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		PrintWriter out=response.getWriter();
		DaoUsuario aux;
		sesion=request.getSession();
		boolean conectado=true;
		String name=request.getParameter("Alias");
		String correo= request.getParameter("mail");
		String contra= Cifrado.encriptar(request.getParameter("pass"));
		String respuesta="";
		
		if (name=="") {
			conectado=false;
			respuesta += "El campo nombre está vacío";
		} else if (correo == "") {
			conectado=false;
			respuesta += "El campo correo está vacío";
		} else if (contra=="") {
			conectado=false;
			respuesta += "el campo password está vacío";
		} else {
			//comprobamos que existan esos datos
			boolean cell=false;
			try {
				aux=new DaoUsuario();
				cell=aux.comprobarCredenciales(name, correo, contra);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (cell==true) {
				cell=false;
				int Type=0;
				try {
					aux=new DaoUsuario();
					Type=aux.recogerTipo(name, correo, contra);
					if (Type < 10) {
						conectado=false;
					} else {
						aux=new DaoUsuario();
						Usuario U=new Usuario();
						U=aux.recogerCredenciales(name, correo, contra);
						//¡¡¡¡¡HAY QUE BORRAR ESTE TO STRING!!!!!:
						System.out.println(U.toString());
						//Puede que necesite más atributos, por ahora lo dejo así
						sesion.setAttribute("ide", U.getId());
						sesion.setAttribute("Nivel", U.getNivel());
						respuesta+= "HAS INICIADO SESIÓN";
					}
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		}
		System.out.println("Resultado: "+respuesta);
		
		out.print(conectado);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		int choice = Integer.parseInt(request.getParameter("op"));
		
		
		switch (choice) {
			case 1: {
				//verificar Admin
				boolean cell=false;
				if (sesion==null) {
					//si sesión es null no se ha iniciado sesión
					out.print(cell);
				} else {
					//no funciona si empleo el parseo lago: Integer.parseInt(), por lo que empleo el simple.
					int lvl = (int) sesion.getAttribute("Nivel");
					if (lvl < 49) {
						//Si el nivel es menor de 49 no es admin y por tanto no tiene acceso
						cell=false;
						out.print(cell);
					} else {
						//Si el nivel es mayor de 49 es admin y tiene acceso
						cell=true;
						out.print(cell);
					}
					
				}
				break;
			}
			case 2: {
				//verificar Titulado
				break;
			}
			case 3: {
				//verificar Estudiante
				break;
			}
			case 4: {
				//verificar Estudiante o Titulado
				break;
			}
			case 5: {
				// cerrar sesión
				if (sesion != null) {
					sesion.invalidate();
					sesion= null;
					System.out.println("Sesión cerrada");
				} else {
					System.out.println("No se ha iniciado sesión");
				}
				break;
			}
			default: {
				
				break;
			}
		}
		
		
		
		
	}

}
