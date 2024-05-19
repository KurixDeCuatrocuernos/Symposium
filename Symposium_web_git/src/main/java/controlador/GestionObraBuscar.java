package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.Articulo;
import modelo.Libro;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import DAO.DaoArticulo;
import DAO.DaoLibro;

/**
 * Servlet implementation class GestionObraBuscar
 */
public class GestionObraBuscar extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static long ID;
    private static ArrayList <Long> IDES= new ArrayList <Long>();
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
		DaoLibro aux;
		int choice = Integer.parseInt(request.getParameter("op"));
		Libro l = new Libro();
		Articulo a = new Articulo();
		PrintWriter out=response.getWriter();
		switch (choice) {
			case 1: {//recoger busqueda
				ID=0;
				IDES.removeAll(IDES);
				System.out.println("Estoy en doGet() --> Case 1");
				//aquí se buca la isbn y se genera el objeto libro/articulo que se mostrará en case 2 
				ArrayList <Long> idesObra = new ArrayList<Long>();
				String Titulo=request.getParameter("title"); 
				boolean cell=false;
				try {
					aux = new DaoLibro();
					idesObra=aux.comprobarObraPorNombre(Titulo);
					System.out.println("ID devuelta= "+ID);
					System.out.println("Tamaño del Array: "+idesObra.size());
					if (idesObra.size()<1) {
						cell=false;
						System.out.println("No se encontró Id");
					} else if (idesObra.size() == 1) {
						cell=true;
						System.out.println("Se encontró una Id");
						
						ID=idesObra.get(0);
						System.out.println(ID);
					} else {
						cell=true;
						System.out.println("Se encontró más de una Id");
						IDES = idesObra;
					}
					out.print(cell);
					
				} catch (ClassNotFoundException | SQLException e) {
					
					e.printStackTrace();
				}
				
				break;
			}
			case 2: {//recoger numero de resultados
				System.out.println("Estoy en doGet() --> Case 2");
				boolean cell=false;
				System.out.println(ID);
				if (ID == 0) {
					// si ID es 0 significa que hay más de una obra 
					cell=false;
				} else {
					cell=true;
				}
				out.print(cell);
				break;
			}
			case 3: { // devolver varios resultados
				System.out.println("Estoy en doGet() --> Case 3");
				String titulos = "";
				try {
					//En este caso inicializo el Dao y luego lo creo para no tener problemas al cerrar y abrir la conexión con la base de datos 
					aux = new DaoLibro();
					
					titulos = aux.listarJsonConcatenado(IDES);
					
					System.out.println(titulos);
				} catch (ClassNotFoundException | SQLException e) {
					
					e.printStackTrace();
				}
				out.print(titulos);
				break;
			}
			case 4: { //recoger un resultado
				ID= Long.parseLong(request.getParameter("id"));
				break;
			}
			case 5: {
				boolean cell=false;
				long ide= Long.parseLong(request.getParameter("id"));
				try {
					aux= new DaoLibro();
					if (aux.comprobarTipo(ide).charAt(0)=='L'||aux.comprobarTipo(ide).charAt(0)=='l') {
						cell=true;
					} else {
						cell=false;
					}

				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				out.print(cell);
				
				break;
			}
			default: {
				System.out.println("No se encontró esa opción");
				break;
			}
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Estoy en GestionObraBuscar --> doPost()");
		System.out.println("La ID es: "+ID);
		String devolver="";
		String tipo="";
		PrintWriter out = response.getWriter();
		try {
			DaoLibro aux = new DaoLibro();
			DaoArticulo aax = new DaoArticulo();
			tipo = aux.comprobarTipo(ID);
			System.out.println("Se ha recogido: "+tipo);
			try {
				if (tipo.charAt(0)=='L'||tipo.charAt(0)=='l') {
				devolver = aux.listarJson(ID);
				} else {
					devolver = aax.listarJson(ID);
				}
			} catch (StringIndexOutOfBoundsException stringBoundsEx) {
				devolver="La búsqueda está vacía, vuelve a buscar";
				stringBoundsEx.printStackTrace();
			}
			
			System.out.println(devolver.toString());
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ID=0;
		out.print(devolver);
		
	}

}
