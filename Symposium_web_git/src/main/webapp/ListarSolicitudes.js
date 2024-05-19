/**
 * 
 */
window.addEventListener("DOMContentLoaded", function() {
	
	let tabla=document.getElementById("listaSolicitudes");
	
	buscarSolicitudes();
	
	function buscarSolicitudes(){
		fetch('GestionSolicitud?op=3')
		.then(response => response.json())
		.then(datos => pintarSolicitudes(datos))
	}
	
	function pintarSolicitudes(datos){
		let html="<table border=1 class=tablaListado> <tr><th>ID Usuario</th><th>Nombre del Titulo</th><th>Lugar de expedición del titulo</th><th>Fecha de expedición del titulo</th><th colspan=2>Acción</th></tr>";
		for(let i=0; i<datos.length;i++){
			html+="<tr class=fila"+datos[i].IdUsuario+"><td>"+datos[i].IdUsuario+"</td><td>"+datos[i].Titulo_estudios+"</td><td>"+datos[i].Lugar_estudios+"</td><td>"+datos[i].Fecha_titulo+"</td> <td><button id="+datos[i].IdUsuario+" class=accept name=accept>Aceptar</button><button id="+datos[i].IdUsuario+" class=deny name=deny>Denegar</button></td></tr>"
		}
		html += "</table>"
		tabla.innerHTML=html;
		
		let acc= document.querySelectorAll("button.accept");
		acc.forEach(bot => {
			let id= bot.getAttribute("id");
			let enfasis = document.querySelector("tr.fila"+id);
			
			bot.addEventListener("click", function emph(){
				enfasis.setAttribute("style","background-color:green"); 
				//Agrega un pequeño retraso antes de llamar a llamadaBorrar() para que enfasis se ejecute antes
	    		setTimeout(function() {
	       		 	aceptarSolicitud(id);
	    		}, 100); // Espera 100 milisegundos
			})
			
		})
		let den=document.querySelectorAll("button.deny");
		den.forEach(bot => {
			let id= bot.getAttribute("id");
			let enfasis=document.querySelector("tr.fila"+id);
			
			bot.addEventListener("click", function emph(){
				enfasis.setAttribute("style","background-color:red"); 
				//Agrega un pequeño retraso antes de llamar a llamadaBorrar() para que enfasis se ejecute antes
    			setTimeout(function() {
       		 	denegarSolicitud(id);
    			}, 100); // Espera 100 milisegundos
			})
			
		})
		
	}
	
	function aceptarSolicitud(id){
		fetch('GestionSolicitud?op=4&id='+id)
		.then(response => response.json())
		.then(respuesta =>{
			if (respuesta === true){
				alert("Solicitud aceptada")
				let dir= "http://localhost:8080/Symposium_web/ListarSolicitudes.html"
				redir(dir);
			} else {
				alert("Fallo al aceptar la solicitud, revisa el fetch y/o el servlet");
			}
		})
		
	}
	
	function denegarSolicitud(id){
		fetch('GestionSolicitud?op=5&id='+id)
		.then(response => response.json())
		.then(respuesta =>{
			if (respuesta === true){
				alert("Solicitud Denegada")
				let dir= "http://localhost:8080/Symposium_web/ListarSolicitudes.html"
				redir(dir);
			} else {
				alert("Fallo al denegar la solicitud, revisa el fetch y/o el servlet");
			}
		})
	}
	
	function redir(dir){
		window.location.href=dir;
	}
	
})