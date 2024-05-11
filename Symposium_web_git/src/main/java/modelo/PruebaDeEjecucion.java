package modelo;

import java.sql.Date;

/**
 * Esta clase sirve para controlar las excepciones que se pueden llegar a producir en el programa.
 * @author Alejandro Moreno
 * @version 1.1
 */
public class PruebaDeEjecucion {
/**
 * Esta clase sirve para probar los métdos constructores de todas las clases, así 
 * como la inserción en las listas, arrojando la excepción si se produjese.
 * @param args Método principal que permite imprimir por pantalla.
 * @throws Exception Exception que recoge la excepción que se pueda producir y la imprime por pantalla. 
 */
	public static void main(String[] args) throws Exception{
		
		Libro libro1=new Libro(111, "Qué bueno es este libro", "Escritor que Escribe", "Libro1", "Libro", Date.valueOf("01/01/1111"), "Editorial de libros", "Filosofia");
		Libro libro2=new Libro(222, "Qué bueno es este libro", "Escritor que Escribe", "Libro2", "Libro", Date.valueOf("02/02/2222"), "Editorial de libros", "Literatura");
		Libro libro3=new Libro(333, "Qué bueno es este libro", "Escritor que Escribe", "Libro3", "Libro", Date.valueOf("03/03/3333"), "Editorial de libros", "Historia");
		ListaLibro libros=new ListaLibro();
		
		libros.addLibroLista(libro1);
		libros.addLibroLista(libro2);
		libros.addLibroLista(libro3);
		
		libros.listarLibros();
		
		Articulo articulo1 = new Articulo(011, "Qué bueno es este Articulo", "Escritor que Escribe", "Articulo1", "Articulo", Date.valueOf("01/01/1111"), "", "Filosofia");
		Articulo articulo2 = new Articulo(022, "Qué bueno es este Articulo", "Escritor que Escribe", "Articulo2", "Articulo", Date.valueOf("02/02/2222"), "", "Literatura");
		Articulo articulo3 = new Articulo(033, "Qué bueno es este Articulo", "Escritor que Escribe", "Articulo3", "Articulo", Date.valueOf("03/03/3333"), "", "Historia");
		ListaArticulo articulos=new ListaArticulo();
		
		articulos.addArticuloLista(articulo1);
		articulos.addArticuloLista(articulo2);
		articulos.addArticuloLista(articulo3);
		
		articulos.listarArticulos();
		
		Comentario com1=new Comentario(11110101, "La Mejor Obra que he leído nunca", "Lorem fistrum laboris quietooor diodeno. Et al ataquerl quietooor commodo. Et torpedo llevame al sircoo la caidita occaecat a gramenawer. Magna qui consequat commodo consequat va usté muy cargadoo qui. Aute a gramenawer duis benemeritaar quis me cago en tus muelas a wan al ataquerl. Te va a hasé pupitaa irure ad no te digo trigo por no llamarte Rodrigor nostrud a gramenawer ex ut se calle ustée ullamco caballo blanco caballo negroorl. Te voy a borrar el cerito ut veniam no puedor eiusmod dolor qui.\n Sed laboris ut ut velit aute elit. Magna ut adipisicing exercitation incididunt esse ese hombree. Cillum hasta luego Lucas condemor te va a hasé pupitaa sed qué dise usteer hasta luego Lucas. Officia incididunt diodeno sed se calle ustée no puedor ullamco dolor a wan qué dise usteer aliquip. Et ahorarr voluptate ese hombree a peich está la cosa muy malar. Diodenoo se calle ustée minim officia dolore ahorarr esse cillum apetecan irure. Dolor commodo exercitation et está la cosa muy malar duis mamaar. Cillum hasta luego Lucas elit consectetur benemeritaar hasta luego Lucas se calle ustée la caidita papaar papaar irure laboris.\n", 100, 0, 001, 111);
		Comentario com2=new Comentario(22220202, "Qué mala", "Lorem fistrum laboris quietooor diodeno. Et al ataquerl quietooor commodo. Et torpedo llevame al sircoo la caidita occaecat a gramenawer. Magna qui consequat commodo consequat va usté muy cargadoo qui. Aute a gramenawer duis benemeritaar quis me cago en tus muelas a wan al ataquerl. Te va a hasé pupitaa irure ad no te digo trigo por no llamarte Rodrigor nostrud a gramenawer ex ut se calle ustée ullamco caballo blanco caballo negroorl. Te voy a borrar el cerito ut veniam no puedor eiusmod dolor qui.\n Sed laboris ut ut velit aute elit. Magna ut adipisicing exercitation incididunt esse ese hombree. Cillum hasta luego Lucas condemor te va a hasé pupitaa sed qué dise usteer hasta luego Lucas. Officia incididunt diodeno sed se calle ustée no puedor ullamco dolor a wan qué dise usteer aliquip. Et ahorarr voluptate ese hombree a peich está la cosa muy malar. Diodenoo se calle ustée minim officia dolore ahorarr esse cillum apetecan irure. Dolor commodo exercitation et está la cosa muy malar duis mamaar. Cillum hasta luego Lucas elit consectetur benemeritaar hasta luego Lucas se calle ustée la caidita papaar papaar irure laboris.\n", 0, 0, 001, 222);
		Comentario com3=new Comentario(33330303, "No está mal", "Lorem fistrum laboris quietooor diodeno. Et al ataquerl quietooor commodo. Et torpedo llevame al sircoo la caidita occaecat a gramenawer. Magna qui consequat commodo consequat va usté muy cargadoo qui. Aute a gramenawer duis benemeritaar quis me cago en tus muelas a wan al ataquerl. Te va a hasé pupitaa irure ad no te digo trigo por no llamarte Rodrigor nostrud a gramenawer ex ut se calle ustée ullamco caballo blanco caballo negroorl. Te voy a borrar el cerito ut veniam no puedor eiusmod dolor qui.\n Sed laboris ut ut velit aute elit. Magna ut adipisicing exercitation incididunt esse ese hombree. Cillum hasta luego Lucas condemor te va a hasé pupitaa sed qué dise usteer hasta luego Lucas. Officia incididunt diodeno sed se calle ustée no puedor ullamco dolor a wan qué dise usteer aliquip. Et ahorarr voluptate ese hombree a peich está la cosa muy malar. Diodenoo se calle ustée minim officia dolore ahorarr esse cillum apetecan irure. Dolor commodo exercitation et está la cosa muy malar duis mamaar. Cillum hasta luego Lucas elit consectetur benemeritaar hasta luego Lucas se calle ustée la caidita papaar papaar irure laboris.\n", 50, 0, 001, 333);
		ListaComentario comentarios= new ListaComentario();
		
		comentarios.addComentarioLista(com1);
		comentarios.addComentarioLista(com2);
		comentarios.addComentarioLista(com3);
		
		comentarios.listarComentarios();
		
		Respuesta res1=new Respuesta(11110101, 00001, 0, "No tienes ni idea");
		Respuesta res2=new Respuesta(22220202, 00002, 0, "No tienes ni idea");
		Respuesta res3=new Respuesta(33330303, 00003, 0, "No tienes ni idea");
		ListaRespuesta respuestas=new ListaRespuesta();
		
		respuestas.addRespuestaLista(res1);
		respuestas.addRespuestaLista(res2);
		respuestas.addRespuestaLista(res3);
		
		respuestas.listarRespuestas();
		
		Usuario user1= new Usuario(001, 10, "Juan", "Juanes Juanes", 11, "juanJuanes1@correo.com");
		Usuario user2= new Usuario(002, 20, "Smith", "Smith Smith", 22, "SmithTheSmith2@correo.com");
		Usuario user3= new Usuario(003, 30, "Trinidad", "Trinity Trece", 33, "Trinidad3@correo.com");
		ListaUsuario usuarios=new ListaUsuario();
		
		usuarios.addUsuarioLista(user1);
		usuarios.addUsuarioLista(user2);
		usuarios.addUsuarioLista(user3);
		
		usuarios.listarUsuarios();
		
		
	
	
	}	
}
