/**
 * 
 */
window.addEventListener("DOMContentLoaded", function() {
	
	// Encabezado y navbar
	let iniS= document.getElementById("InicioSesion");
	let home= document.getElementById("logo");
	let dir="";
	
	home.addEventListener("click", function home(){
		dir="http://localhost:8080/Symposium_web/Index.html";
		redir(dir);
	});

	iniS.addEventListener("click", function redir1(){
		dir="http://localhost:8080/Symposium_web/IniciarSesion.html"
		redir(dir);
	})
	
	recogerUsuario();
	avisoAdmin();
	pintarAscensoEs();
	pintarCierre();
	
		let buscador = document.getElementById("buscar");
	
	buscador.addEventListener("keyup", function(){
		pedirSugerencias(buscador.value);
	})
	
	function peticion(inputUser){
		let solicitud = new XMLHttpRequest();
		
		let url = "./GestionObraBuscar?solic="+inputUser;
		
		solicitud.addEventListener("load", mostrar);
		solicitud.open('GET',url, true); // si es true será asíncrono, si es false, no
		solicitud.send();
		
	}
	
	function mostrar(event){
		let datos = event.target;
		let html="";
		for(let i=0; i<datos.response.length; i++){
			html+="<p>"+datos.response[i]+"</p>";
		}
		if (datos.status == 200){
			let sugerencias = JSON.parse(datos.responseText); 
			let html = "";
            for (let i = 0; i < sugerencias.length; i++) {
                html += "<p class=resultadoB id="+sugerencias[i].ISBN+" name="+sugerencias[i].Tipo+">" + sugerencias[i].Titulo + "</p>";
            }
			document.getElementById("sugerencias").innerHTML=html;
		} else {
			console.log("Fallo: "+datos.status+" en la petición XML");
		}
		let vinculos=document.querySelectorAll("p.resultadoB");
		vinculos.forEach (bot =>{
			bot.addEventListener("click", function showOpper(){
				let dir="./PaginaObra.html?viewkey="+bot.getAttribute("id")+"&viewtype="+bot.getAttribute("name");
				redir(dir);
			});
		})
	}
	
	function pedirSugerencias(str){
		if(str.length==0){
			document.getElementById("sugerencias").innerHTML="";
		} else {
			peticion(str);
		}
	}
	

	function recogerUsuario(){
		fetch('GestionInicioSesion?op=8', {method:'POST'})
		.then(response => response.json())
		.then(tipo => pintarUsuario(tipo))
	}
	
	function pintarUsuario(tipo){
		let user="";
		if(tipo<10){
			user="ANÓNIMO";
		} else if (tipo>9 && tipo<30){
			user="ESTUDIANTE";
		} else if (tipo>29&&tipo<50){
			user="TITULADO";
		}else if (tipo>49){
			user="ADMINISTRADOR";
		}
		let place=document.getElementById("usuarioType");
		place.innerHTML=user;
	}
	
	function imprimirListados(){
		
		let place= document.getElementById("botonesAdmin");
		let html="<button id=ListaUsers>Listado de Usuarios</button><button id=ListarObras>Listado de Obras</button>";
		place.innerHTML=html;
		
		let lisU= document.getElementById("ListaUsers");
		lisU.addEventListener("click", function redir2(){
			dir="http://localhost:8080/Symposium_web/ListarUsuarios.html"
			redir(dir);
		})
		let lisO= document.getElementById("ListarObras");
		lisO.addEventListener("click", function redir3(){
			dir="http://localhost:8080/Symposium_web/ListarLibro.html"
			redir(dir);
		})
	}
	
	function pintarCierre() {
		fetch('GestionInicioSesion?op=9', {method:'POST'})
		.then(response => response.json())
		.then(devuelto => {
			if(devuelto === true){
				html="<button id=CerrarSesion>Cerrar Sesión</button>";
				
				document.getElementById("botonCerrarSesion").innerHTML+=html;
				
				let cerS= document.getElementById("CerrarSesion");
				
				cerS.addEventListener("click", function closeSes(){
					cerrarSesion();
				})
			}
		})
		
	}
	
	function cerrarSesion() {
		let cell = confirm("¿Estás seguro de que quieres cerrar la sesión?");
		console.log(cell);
		if (cell == true){
			fetch('GestionInicioSesion?op=5', {method:'POST'})
			setTimeout(function(){
				dir = "http://localhost:8080/Symposium_web/Index.html"; 
				redir(dir);
			}, 100);//esperamos 100 milisegundos
					
		} else {
			dir="http://localhost:8080/Symposium_web/Index.html"; 
			redir(dir);
		}
	}
	
	function avisoAdmin(){
		fetch('GestionInicioSesion?op=9', {method:'POST'})
		.then(response => response.json())
		.then(sesion => {
			if (sesion==true){
				console.log(sesion)
				fetch('GestionInicioSesion?op=1', {method:'POST'})
				.then(response => response.json())
				.then(admin => {
					if (admin===true){
						imprimirListados();
					}
				})
			}
		})
	}
	
	function pintarAscensoEs(){
		fetch('GestionInicioSesion?op=3', {method:'POST'})
		.then(response => response.json())
		.then(sesion => {
			if (sesion===true){
				let html="<button id=solicitarAscenso>Solicitar ascenso a Titulado</button>";
				document.getElementById("botonEstudiante").innerHTML+=html;
				
				let ascen = document.getElementById("solicitarAscenso");
				ascen.addEventListener("click", function solicitud(){
					let dir="http://localhost:8080/Symposium_web/SolicitudAscenso.html"
					redir(dir);
				})	
			}
		})
	}


	// CONTENIDO

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