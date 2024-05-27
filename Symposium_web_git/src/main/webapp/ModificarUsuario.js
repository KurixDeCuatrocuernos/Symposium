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

	recogerFormulario();
	accionEstudio();
	let select = document.getElementById("Nivel");
	var html="";
	var id=0;

	select.addEventListener("change", function() {
	  let opcionSeleccionada = parseInt(select.options[select.selectedIndex].value);

		   if (opcionSeleccionada === 10) {
		    accionEstudio();
		  } else if (opcionSeleccionada === 30) {
		    accionTitulo();
		  } else if (opcionSeleccionada === 50) {
		    accionAdmin();
		  }
	 
	});
	
	let accept= document.getElementById("Accept");
	accept.addEventListener("click", function acc(){
		
		verificarFormulario();
		
	});

	let canc=this.document.querySelector("button.abort");
	canc.addEventListener("click", function cancel(){
		let dir="http://localhost:8080/Symposium_web/ListarLibro.html";
		redir(dir);
	});

	function accionEstudio(){
		
		html = "<form><label><h1 alt=Escuela del estudiante>Estudios del estudiante: </h1><input type=text id=Studies class=Studies name=Studies></label> <label><h1 alt=Escuela donde estudia actualmente o ha estudiado el estudiante>Escuela del estudiante: </h1><input type=text id=School class=School name=School></label></form>";
		document.getElementById("valoresConcretos").innerHTML = html;
		console.log("Has seleccionado: Estudiante");
	
	}
	
	function accionTitulo() {
		//falta añadir el archivo, si me da tiempo lo implementaré
		html="<Form><label><h1 alt=Nombre del título que tiene el usuario titulado (por ejemplo, Licenciado en ciencias sociales y humanidades, o Titulado en Desarrollo de aplicaciones web)>Nombre del Titulo: </h1><input type=text id=Titulo class=Titulo name=Titulo></label><label><h1 alt=Nombre del lugar donde se expidió el título, puede ser un Instituto o una Universidad> Lugar de obtención del titulo: </h1><input type=text id=Lugar class=Lugar name=Lugar></label><label><h1 alt=Año de expedición del título>Año de obtención del Titulo: </h1><input type=int id=Year class=Year name=Year></label></form>";
		document.getElementById("valoresConcretos").innerHTML = html;
		console.log("Has seleccionado: Titulado");
		
	}
	
	function accionAdmin() {
		
		html="<form><label><h1 alt=Número de teléfono para contactar con el administrador en cuestión>Número de Telefono de contacto: </h1><input type=number id=Telf class=Telf name=Telf></label></form>";
		document.getElementById("valoresConcretos").innerHTML = html;
		console.log("Has seleccionado: Administrador");
		
	}
	
	function verificarFormulario(){
		let Name = document.getElementById("Nombre");
		let SurN = document.getElementById("Apellidos");
		let Age = document.getElementById("Age");
		let fechaActual = new Date();
		let añoActual = fechaActual.getFullYear();
		let Mail = document.getElementById("Email");
		let ok=true;
		let mensaje="";
		let select = document.getElementById("Nivel");
		let opcionSeleccionada = parseInt(select.options[select.selectedIndex].value);
		//no verifico estudios porque esa información es opcional
		if (opcionSeleccionada === 30) {
		    let Titulo = document.getElementById("Titulo");
		    let Lugar = document.getElementById("Lugar");
		    let Year = document.getElementById("Year");
		    if (Titulo === ""){
				ok=false;
				mensaje += "Es necesario introducir un titulo para que el usuario pueda ser Titulado. \n";
				Titulo.setAttribute("Style=color", "red");
			}
			if (Lugar === ""){
				ok=false;
				mensaje += "Es necesario introducir un Lugar de expedición del título para que el usuario pueda ser Titulado. \n";
				Lugar.setAttribute("Style=color", "red");
			}
			if (Year === ""){
				ok=false;
				mensaje += "Es necesario introducir un Año de titulación para que el usuario pueda ser Titulado. \n";
				Year.setAttribute("Style=color", "red");
			} else if (Year<1800 && Year>añoActual) {
				ok=false;
				mensaje += "No es posible que hayas obtenido el titulo en un año que todavía no ha llegado ni que tengas más de 2 siglos de edad. \n";
				Year.setAttribute("Style=color", "red");
			}
		} else if (opcionSeleccionada === 50) {
		    let Telf = document.getElementById("Telf");
			if (Telf === ""){
				ok=false;
				mensaje += "Es necesario un número de contacto para poder ser Administrador. \n";
				Telf.setAttribute("style=color", "red");
			}
		}
		if (Name === ""){
			ok=false;
			mensaje+="Es necesario introducir un Nombre de usuario. \n";
			Name.setAttribute("style=color", "red");
		}
		if (SurN === ""){
			ok=false;
			mensaje+="Es necesario introducir un Apellido. \n";
			SurN.setAttribute("style=color", "red");
		}
		if (Age === ""){
			ok=false;
			mensaje+="Es necesario determinar tu edad. \n";
			Age.setAttribute("style=color", "red");
		}
		if (Mail.value === ""){
			ok=false;
			mensaje+="Es necesario proporcionar un correo electrónico. \n";
			Mail.setAttribute("style=color", "red");
		}
		if (ok==true) {
			ModificarU();
		} else {
			alert(mensaje);
		}
	}
	
	function recogerFormulario(){
	
	fetch ('GestionUsuarioModificar?op=0', {method:'Post'})
	.then(response => response.json())
	.then(data => pintarFormulario(data))
	.catch(error => console.log("Error: "+error));
	
}

function pintarFormulario(resultados){
	id = parseInt(resultados.ID);
	let Name = document.getElementById("Nombre");
	let SurN = document.getElementById("Apellidos");
	let Age = document.getElementById("Age");
	let Mail = document.getElementById("Email");
	Name.setAttribute("value", resultados.Nombre);
	SurN.setAttribute("value", resultados.Apellidos);
	Age.setAttribute("value", resultados.Edad);
	Mail.setAttribute("value", resultados.Email);
	
}

function ModificarU(){
	
		let Name = document.getElementById("Nombre");
		let SurN = document.getElementById("Apellidos");
		let Age = document.getElementById("Age");
		let Mail = document.getElementById("Email");
		let select = document.getElementById("Nivel");
		let opcionSeleccionada = parseInt(select.options[select.selectedIndex].value);
		if (opcionSeleccionada === 10) {
			let Studies = document.getElementById("Studies");
			let School = document.getElementById("School");
			if (Studies === "" || School === ""){
				
				fetch('GestionUsuarioModificar?id='+id+'&op=1&Name='+Name.value+'&Nivel=10&Surname='+SurN.value+'&Age='+Age.value+'&Mail='+Mail.value, {method:'Post'})
				.then(response => response.json())
				.then(result => {
					if (result===true){
						alert("Usuario Modificado con éxito")
						let dir="http://localhost:8080/Symposium_web/ListarUsuarios.html";
						redir(dir);
					} else {
						alert("HUBO UN PROBLEMA AL MODIFICAR AL USUARIO");
					}
				})
			} else {
				
				fetch('GestionUsuarioModificar?id='+id+'&op=2&Name='+Name.value+'&Nivel=10'+'&Surname='+SurN.value+'&Age='+Age.value+'&Mail='+Mail.value+'&Studies='+Studies.value+'&School='+School.value, {method:'Post'})
				.then(response => response.json())
				.then(result => {
					if (result===true){
						alert("Usuario Modificado con éxito")
						let dir="http://localhost:8080/Symposium_web/ListarUsuarios.html";
						redir(dir);
					} else {
						alert("HUBO UN PROBLEMA AL MODIFICAR AL USUARIO");
					}
				})
				
			}
		} else if (opcionSeleccionada === 30) {
			let Titulo = document.getElementById("Titulo");
		    let Lugar = document.getElementById("Lugar");
		    let Year = document.getElementById("Year");
		    
		    fetch('GestionUsuarioModificar?id='+id+'&op=3&Name='+Name.value+'&Nivel=30&Surname='+SurN.value+'&Age='+Age.value+'&Mail='+Mail.value+'&Titulo='+Titulo.value+'&Lugar='+Lugar.value+'&Year='+Year.value, {method:'Post'})
				.then(response => response.json())
				.then(result => {
					if (result === true){
						alert("Usuario Modificado con éxito")
						let dir="http://localhost:8080/Symposium_web/ListarUsuarios.html";
						redir(dir);
					} else {
						alert("HUBO UN PROBLEMA AL MODIFICAR AL USUARIO");
					}
				})
		} else if (opcionSeleccionada===50) {
			let Telf = document.getElementById("Telf");
			
			 fetch('GestionUsuarioModificar?id='+id+'&op=4&Name='+Name.value+'&Nivel=50&Surname='+SurN.value+'&Age='+Age.value+'&Mail='+Mail.value+'&Telf='+Telf.value, {method:'Post'})
				.then(response => response.json())
				.then(result => {
					if (result===true){
						alert("Usuario Modificado con éxito")
						let dir="http://localhost:8080/Symposium_web/ListarUsuarios.html";
						redir(dir);
					} else {
						alert("HUBO UN PROBLEMA AL MODIFICAR AL USUARIO");
					}
				})
		}
		
		
	}
	
	function redir(dir){
		
		window.location.href = dir;
		
	}
});
