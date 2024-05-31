package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modelo.Cifrado;
import modelo.Usuario;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import com.google.gson.Gson;

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
		String name="";String correo="";String contra="";String respuesta="";
		
		try {
			name=request.getParameter("Alias");
			correo= request.getParameter("mail");
			contra= Cifrado.encriptar(request.getParameter("pass"));
		} catch (Exception ex) {
			conectado=false;
			respuesta="Error a recoger los parámetros de javaScript, revisa el fetch y/o la solicitud";
			ex.printStackTrace();
		}
		
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
						//Puede que necesite más atributos, por ahora lo dejo así
						sesion.setAttribute("ide", U.getId());
						sesion.setAttribute("Nivel", U.getNivel());
						respuesta+= "HAS INICIADO SESIÓN";
					}
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			} else {
				System.out.println("No se encontró a ese usuario");
				conectado=false;
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
		boolean cell=false;
		
		switch (choice) {
			case 1: {
				//verificar Admin
				cell=false;
				if (sesion==null) {
					//si sesión es null no se ha iniciado sesión
					cell=false;
				} else {
					//no funciona si empleo el parseo largo: Integer.parseInt(), por lo que empleo el simple.
					int lvl = (int) sesion.getAttribute("Nivel");
					if (lvl > 49) {
						//Si el nivel es mayor de 49 es admin.
						cell=true;
					}
					
				}
				out.print(cell);
				break;
			}
			case 2: {
				//verificar Titulado
				cell=false;
				if (sesion==null) {
					//si sesión es no se ha iniciado sesión, por lo que no modifico
					cell=false;
				} else {
					//no funciona si empleo el parseo largo: Integer.parseInt(), por lo que empleo el simple.
					int lvl = (int) sesion.getAttribute("Nivel");
					if (lvl>29&&lvl<50) {
						//si lvl es mayor de 29 y menor que 50 es titulado
						cell=true;
					}
				}
				out.print(cell);
				break;
			}
			case 3: {
				//verificar Estudiante
				if (sesion==null) {
					//si sesión es igual a null no se ha iniciado sesión, por lo que modifico a false.
					cell=false;
				} else {
					//no funciona si empleo el parseo largo: Integer.parseInt(), por lo que empleo el simple.
					int lvl = (int) sesion.getAttribute("Nivel");
					if (lvl>9 && lvl<30) {
						//si es mayor de 9 y menor que 30 es estudiante.
						cell=true;
					}
				}
				out.print(cell);
				break;
			}
			case 4: {
				//verificar Estudiante o Titulado
				cell=false;
				if (sesion==null) {
					//si sesión es no se ha iniciado sesión, por lo que no modifico
					cell=false;
				} else {
					//no funciona si empleo el parseo largo: Integer.parseInt(), por lo que empleo el simple.
					int lvl = (int) sesion.getAttribute("Nivel");
					if (lvl > 9 && lvl < 49) {
						//Si el nivel es mayor que 9 y menor de 49 o bien es Estudiante o bien es Titulado y, por tanto, tiene acceso
						cell=true;
					} 
						
				}
				out.print(cell);
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
			case 6: {
				//verificar Admin o Titulado
				cell=false;
				if (sesion==null) {
					//si sesión es no se ha iniciado sesión, por lo que no modifico
					cell=false;
				} else {
					//no funciona si empleo el parseo largo: Integer.parseInt(), por lo que empleo el simple.
					int lvl = (int) sesion.getAttribute("Nivel");
					if (lvl > 29) {
						//Si el nivel es mayor que 29, o bien es Administrador, o bien es Titulado y, por tanto, tiene acceso
						cell=true;
					} 
				}
				out.print(cell);
				break;
			}
			case 7: {
				//verificar Admin o Estudiante
				cell=false;
				if (sesion==null) {
					//si sesión es no se ha iniciado sesión, por lo que no modifico
					cell=false;
				} else {
					//no funciona si empleo el parseo largo: Integer.parseInt(), por lo que empleo el simple.
					int lvl = (int) sesion.getAttribute("Nivel");
					if ((lvl > 9 && lvl < 30) || lvl<49) {
						//Si el nivel es mayor que 9 y menor que 30, es Estudiante y si es mayor que 49 es Administrador y, por tanto, tiene acceso
						cell=true;
					} 
				}
				out.print(cell);
				break;
			}
			case 8: {
				//este apartado devuelve el nivel del usuario
				int lvl= 0;
				if (sesion != null) {
					lvl=(int) sesion.getAttribute("Nivel");
				}
				out.print(lvl);
				break;
			}
			case 9: {
				//verificar sesión
				cell=false;
				if (sesion != null) {
					cell=true;
				}
				out.print(cell);
				break;
			}
			case 10: { //sacar nombre del usuario 
				String json="";
				if (sesion != null) {
					
					Gson gson=new Gson();
					try {
						json = gson.toJson(sesion.getAttribute("name"));
					} catch (Exception  ex) {
						ex.printStackTrace();
					}
				}
				System.out.println("Nombre recogido: "+json);
				out.print(json);
				break;
			}
			default: {
				System.out.println("ERROR AL ELEGIR LA OPCIÓN: op, en JavaScript, revisa el Fetch");
				break;
			}
		}
		
		
		
		
	}

}
