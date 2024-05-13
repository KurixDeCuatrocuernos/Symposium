/**
 * 
 */
window.addEventListener("DOMContentLoaded", function() {
	
	let iniS= document.getElementById("InicioSesion");
	let lisU= document.getElementById("ListaUsers");
	let cerS= document.getElementById("CerrarSesion");
	let lisO= document.getElementById("ListarObras");
	let busO= document.getElementById("BuscarObra");
	let dir="";
	
	iniS.addEventListener("click", function redir1(){
		dir="http://localhost:8080/Symposium_web/IniciarSesion.html"
		redir(dir);
	})
	
	lisU.addEventListener("click", function redir2(){
		dir="http://localhost:8080/Symposium_web/ListarUsuarios.html"
		redir(dir);
	})
	
	lisO.addEventListener("click", function redir3(){
		dir="http://localhost:8080/Symposium_web/ListarLibro.html"
		redir(dir);
	})
	
	cerS.addEventListener("click", function closeSes(){
		let cell = confirm("¿Estás seguro de que quieres cerrar la sesión?");
		if (cell == true){
			fetch('GestionInicioSesion?op=5', {method:'POST'})
			setTimeout(function(){
				dir = "http://localhost:8080/Symposium_web/Index.html"; 
				redir(dir);
			});
		
		}
		
	})
	
	busO.addEventListener("click", function buscar(){
		dir = "http://localhost:8080/Symposium_web/BuscarObra.html"; 
		redir(dir);
	});
	
	function redir(dir){
		window.location.href=dir;
	}
})
