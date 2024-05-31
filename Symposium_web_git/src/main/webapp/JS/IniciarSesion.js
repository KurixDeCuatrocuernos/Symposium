
window.addEventListener("DOMContentLoaded", function(){
	
	// .Contenido

	let Alias= document.getElementById("alias");
	let mail= document.getElementById("correo");
	let pass = document.getElementById("passw");
	let accept = document.getElementById("subm");
	let cancel= document.getElementById("cancel");
	
	revisarSesion();
	
	accept.addEventListener("click", function subm(){
		console.log("verificando información")
		verificarDatos();	
	})
	
	
	
	cancel.addEventListener("click", function cerrar(){
		console.log("redirijo al inicio");
		let dir = "http://localhost:8080/Symposium_web/Index.html";
		redir(dir);
		
	})	
	
	function revisarSesion() {
		fetch ('GestionInicioSesion?op=9', {method:'POST'})
		.then(response => response.json())
		.then(devolver =>{
			console.log(devolver);
			if (devolver == true){
				alert("Ya has iniciado sesión");
				let dir="http://localhost:8080/Symposium_web/Index.html";
				redir(dir);
			}
		})
	}
	
	function verificarDatos() {
		
		let mensaje="";
		let verificarTexto="@symposium.com";
		let ok=true;
		if (Alias.value=="") {
			ok=false;
			mensaje+="El campo Nombre no puede quedar vacío. \n";	
		}
		if (mail.value=="") {
			ok=false;
			mensaje+="El campo Correo no puede quedar vacío. \n";	
		}
		if (pass.value=="") {
			ok=false;
			mensaje+="El campo Contraseña no puede quedar vacío. \n";	
		}
		if (!mail.value.endsWith(verificarTexto)) {
			ok=false;
			mail.setAttribute("style:color", "red");
			mensaje+="Eso no es un correo electrónico válido, el correo ha de terminar por: @symposium.com. \n";
		}
		if (ok==false) {
			alert(mensaje);
		} else {
			enviarDatos();
		}
			
	}
	
	function enviarDatos(){
		console.log("redirijo al servlet");
		fetch('GestionInicioSesion?Alias='+Alias.value+'&mail='+mail.value+'&pass='+pass.value)
		.then(response => response.json())
		.then(resultado => {
			console.log("Respuesta del servlet: "+resultado);
			if (resultado === true) {
				alert("Has iniciado sesión con exito")
				let dir = "http://localhost:8080/Symposium_web/Index.html"
				redir(dir);
			} else {
				alert("No hemos podido confirmar tu identidad, revisa la información introducida");
			}
		})
	}
	
		// Encabezado y navbar

	let home= document.getElementById("logo");
	let iniS= document.getElementById("InicioSesion");
	let dir="";

	home.addEventListener("click", function home(){
		dir="http://localhost:8080/Symposium_web/Index.html";
		redir(dir);
	});

	iniS.addEventListener("click", function redir1(){
		dir="http://localhost:8080/Symposium_web/IniciarSesion.html";
		redir(dir);
	});
	
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

	function redir(dir){
		window.location.href = dir;
	}

})
