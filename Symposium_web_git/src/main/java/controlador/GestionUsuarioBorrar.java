package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import DAO.DaoComentario;
import DAO.DaoSolicitud;
import DAO.DaoUsuario;
import DAO.DaoValoracion;

/**
 * Servlet implementation class GestionUsuarioBorrar
 */
public class GestionUsuarioBorrar extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestionUsuarioBorrar() {
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
		// TODO Auto-generated method stub
		System.out.println("Estoy en GestionUsuarioBorrar --> doPost()");
		long ide=Long.parseLong(request.getParameter("id"));
		System.out.println(ide);
		boolean borrar=true;
		try {
			
			DaoUsuario u=new DaoUsuario();
			borrar=u.comprobarBorrado(ide);
			if (borrar==true) {
				u.borrarUsuario(ide);
			} else {
				//borramos los comentarios, solicitudes y valoraciones de ese usuario.
				try {
					borrar=true;
					DaoComentario auxc=new DaoComentario();
					auxc.borrarComentariosUser(ide);
					DaoValoracion auxv=new DaoValoracion();
					auxv.borrarValoracionesUser(ide);
					DaoSolicitud auxs=new DaoSolicitud();
					auxs.borrarSolicitud(ide);
				} catch (Exception ex) {
					borrar=false;
					ex.printStackTrace();
				}
				if (borrar==true) {
					u.borrarUsuario(ide);
				}
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
