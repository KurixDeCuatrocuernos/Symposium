/**
 * 
 */
window.addEventListener("DOMContentLoaded", function(){
	
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

	verificarSesion();
	
	let but = document.querySelector("button.Aceptar");
	but.addEventListener("click", function accept(){
		verificar();
	});
	let cancel = document.querySelector("button.cancel");
	cancel.addEventListener("click", function cancel(){
		let dir="http://localhost:8080/Symposium_web/Index.html";
		redir(dir);
	});
	let Mail=document.getElementById("Mail");
	let test = document.querySelector("button.Comp");
	test.addEventListener("click", function trial(){
		if(Mail.value!=""){
			let ending="@symposium.com";
			fetch('GestionEstudianteInsertar?mail='+Mail.value, {method:'POST'})
			.then(response => response.json())
			.then(respuesta => {
				if (respuesta===true){
					if(Mail.value.endsWith(ending)){
						alert("Ese correo sirve");
					} else {
						alert("El correo debe terminar por: @symposium.com");	
					}
				} else {
					alert("Ese correo ya está en uso, elige otro");
				}
			})
			
		} else {
			alert("Introduce algún correo para comprobar");
		}
		
	});
	
	function enviarFormulario(){
		let Name = document.getElementById("Name").value;
		let SurN = document.getElementById("Surname").value;
		let Age = document.getElementById("Age").value;
		let Mail = document.getElementById("Mail").value;
		let Pass = document.getElementById("Contra").value;
		//cambiar a XML
		fetch ('GestionEstudianteInsertar?&Name='+Name+'&Surname='+SurN+'&Age='+Age+'&Mail='+Mail+'&Pass='+Pass)
		.then (response => response.json())
		.then (data => {
			if (data===true){
				fetch('GestionInicioSesion?Alias='+Name+'&mail='+Mail+'&pass='+Pass)
				.then(response => response.json())
				.then(respuesta => {
					if (respuesta === true){
						alert("Has iniciado sesión");
						let dir="http://localhost:8080/Symposium_web/Index.html";
						redir(dir);
					} else {
						alert("Error al iniciar sesión, vuelve a intentarlo más tarde");
						let dir="http://localhost:8080/Symposium_web/Index.html";
						redir(dir);
					}
				})
			} else {
				alert("Error al registrar un nuevo usuario, no es culpa tuya, inténtalo de nuevo más tarde, si persiste ponte en contacto con un administrador");
				console.log("Error al registrar al usuario revisa el fetch y/o el servlet")
			}
		})
		
	}
	
	function verificar(){
		
		let Name = document.getElementById("Name");
		let SurN = document.getElementById("Surname");
		let Age = document.getElementById("Age");
		let Mail = document.getElementById("Mail");
		let Pass = document.getElementById("Contra");
		let RePass = document.getElementById("Repeat");
		let ok = true;
		let mensaje= "";
		
		if (Name.value == "") {
			
			ok=false;
			mensaje += "Introduce un nombre para poder registrarte. \n";
			
		}
		if (SurN.value == "") {
			
			ok=false;
			mensaje += "Introduce un apellido para poder registrarte. \n";
			
		}
		if (Age.value == "") {
			
			ok=false;
			mensaje += "Introduce Tu edad para poder registrarte. \n";
			
		}
		if (Mail.value == "") {
			
			ok=false;
			mensaje += "Introduce un correo para poder registrarte. \n";
			
		}
		if (Pass.value == "") {
			
			ok=false;
			mensaje += "Introduce una contraseña para poder registrarte. \n";
			
		} else if (Pass.value != RePass.value){
			
			ok=false;
			mensaje += "Las contraseñas no coinciden \n";
			Pass.setAttribute("Background-color", "red");
			RePass.setAttribute("Background-color", "red");
			
		} else if (Pass.value)
		
		if (ok==true){
			enviarFormulario();
		} else {
			alert(mensaje);
		}
		
	}
	
	function verificarSesion(){
		fetch('GestionInicioSesion?op=9', {method:'POST'})
		.then(response => response.json())
		.then(respuesta => {
			if(respuesta===true){
				alert("Ya dispones de una cuenta");
				let dir="http://localhost:8080/Symposium_web/Index.html";
				redir(dir);
			}
		})
	}
	
	function redir(dir){
		window.location.href=dir;
	}
	
});