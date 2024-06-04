window.addEventListener ("DOMContentLoaded", function(){
			
	
		// Encabezado y navbar
	
		let home= document.getElementById("logo");
		let dir="";
		
		home.addEventListener("click", function home(){
			dir="http://localhost:8080/Symposium_web/Index.html";
			redir(dir);
		});
		
		recogerUsuario();
		avisoAdmin();
		pintarAscensoEs();
		pintarSesion();
		
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
		
		function pintarSesion() {
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
					
					html="";
					
					document.getElementById("botonInicioSesion").innerHTML=html;
				
				} else {
				
					html="<strong id=InicioSesion>Iniciar Sesión</strong>";
					
					document.getElementById("botonInicioSesion").innerHTML=html;
					
					let iniS= document.getElementById("InicioSesion");
					iniS.addEventListener("click", function redir1(){
						dir="http://localhost:8080/Symposium_web/IniciarSesion.html"
						redir(dir);
					});
					
					html="";
					
					document.getElementById("botonCerrarSesion").innerHTML=html;
					
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
	

	// contenido

	verificarAdmin();

	function verificarAdmin(){
		fetch('GestionInicioSesion?op=1', {method:'POST'})
	.then(response => response.json())
	.then (respuesta => {
		console.log(respuesta);
		if (respuesta === true) {
			llamada();
		} else {
			alert("Antes inicia sesión como Administrador")
			let dir="http://localhost:8080/Symposium_web/IniciarSesion.html"
			redir(dir);
		}
	})
	}
	
	function redir(dir){
		window.location.href = dir;
	}
	
	function llamada(){
		
		fetch('GestionObrasListar')
		.then(response => response.json())
		.then(data => pintar(data))
	}
	
	function llamadaBorrar(id){
		
		let cell=confirm("¿Estás seguro de que quieres borrar el libro con la id: "+id+"?");
		
		if (cell==true){
		//Si es true, borramos			
			fetch('GestionLibroBorrar?id='+id,{method: 'post'})
			.then(response => {
				if(response.ok){
					alert("Libro borrado con éxito");
       		 		let dir="http://localhost:8080/Symposium_web/ListarLibro.html";
					redir(dir);
				} else{
					alert("ERROR AL BORRAR EL LIBRO");
				}
			})
		} else {
			let enfasis= document.querySelector("tr.fila"+id);
				enfasis.setAttribute("style","background-color:inherit");
			alert("Borrado cancelado");
		}
		
	}
	
	function llamadaModificarL(id) {
		fetch('GestionLibroEditar?id='+id)
		.then(response => {
			if(response.ok){
				console.log("Enviado al servlet");
			} else {
				alert("ERROR AL ENVIAR EL LIBRO AL SERVLET");
			}
		})
	}
	
	function llamadaModificarA(id) {
		fetch('GestionArticuloEditar?id='+id)
		.then(response => {
			if(response.ok){
				console.log("Enviado al servlet");
			} else {
				alert("ERROR AL ENVIAR EL LIBRO AL SERVLET");
			}
		})
	}
	
	function pintar(resultados){

		let html = "<table border=1 class=tablaListado> <tr><th><strong>ISBN</strong></th><th><strong>Nombre del Autor</strong></th><th><strong>Titulo</strong></th><th><strong>Editorial / Lugar de Publicacion</strong></th><th><strong>Fecha de Publicación</strong></th><th><strong>Tipo</strong></th><th colspan=2>Acción</th></tr>";
		
		for(let i=0;i<resultados.length;i++){
			if (resultados[i].Tipo.charAt(0)=='A'){
				html += "<tr class=fila"+resultados[i].ISBN+"><td>"+resultados[i].ISBN+"</td><td>"+resultados[i].Autor+"</td><td>"+resultados[i].Titulo+"</td><td>"+resultados[i].Editorial+"</td><td>"+resultados[i].Fecha_publicacion+"</td><td>"+resultados[i].Tipo+"</td><td><button id="+resultados[i].ISBN+" type=button class=modifA>Edit</button><button type=button class=borrar id="+resultados[i].ISBN+">Delete</button></td></tr>"
			}
			else {
				html += "<tr class=fila"+resultados[i].ISBN+"><td>"+resultados[i].ISBN+"</td><td>"+resultados[i].Autor+"</td><td>"+resultados[i].Titulo+"</td><td>"+resultados[i].Editorial+"</td><td>"+resultados[i].Fecha_publicacion+"</td><td>"+resultados[i].Tipo+"</td><td><button id="+resultados[i].ISBN+" type=button class=modifL>Edit</button> <button type=button class=borrar id="+resultados[i].ISBN+">Delete</button></td></tr>"
			}
		}	
			
		html += "</table>";
			
		document.getElementById("listado").innerHTML = html;
		
		//meto el evento escuchador para borrar tras generar el objeto, y no antes
		
		let erase=document.querySelectorAll("button.borrar");
		erase.forEach(bot =>{
			
			let id = bot.getAttribute("id");	
			let enfasis = document.querySelector("tr.fila"+id);
			
			bot.addEventListener("click", function emph(){
				
				enfasis.setAttribute("style","background-color:red"); 
				// Agrega un pequeño retraso antes de llamar a llamadaBorrar() para que enfasis se ejecute antes
    			setTimeout(function() {
       		 		llamadaBorrar(id);
    			}, 100); // Espera 100 milisegundos
				
				
			})
					
		})
		
		//meto el evento escuchador para editar tras generar el objeto y no antes.
		
		let modifL=document.querySelectorAll("button.modifL");
		modifL.forEach(bot => {
			
			let id = bot.getAttribute("id");
			bot.addEventListener("click", function editL(){
				
				llamadaModificarL(id);
				let dir="http://localhost:8080/Symposium_web/ModificarLibro.html";
				redir(dir);
				
			})
				
		})
		
		let modifA=document.querySelectorAll("button.modifA");
		modifA.forEach(bot => {
			
			let id = bot.getAttribute("id");
			bot.addEventListener("click", function editA(){
				
				llamadaModificarA(id);
				let dir="http://localhost:8080/Symposium_web/ModificarArticulo.html";
				redir(dir);
				
			})
				
		})
		//boton para insertar obra
		let ins=document.getElementById("insertarLibro");
		ins.addEventListener("click", function insertL(){
			let dir="http://localhost:8080/Symposium_web/SubirObra.html"
			redir(dir);
		})
		
	} 
	
})