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
	
	let acc = document.getElementById("accept");
	let deny= document.getElementById("cancel");
	let title= document.getElementById("titulo");
	let lug= document.getElementById("LugarTitulo");
	let date=document.getElementById("fechaTitulo");
	
	acc.addEventListener("click", function subm(){
		verificarFormulario();
	})
	
	deny.addEventListener("click", function exit(){
		let dir="http://localhost:8080/Symposium_web/Index.html";
		redir(dir);
	})
	
	function verificarSesion(){
		fetch('GestionInicioSesion?op=9', {method:'POST'})
		.then(response => response.json())
		.then(verificar => {
			if (verificar===true){
				fetch('GestionInicioSesion?op=3', {method:'POST'})
				.then(response => response.json())
				.then(estudiante => {
					if(estudiante===true){
						//es estudiante y puede solicitar el ascenso
					} else {
						alert("No puedes solicitar el ascenso a Titulado");
						let dir="http://localhost:8080/Symposium_web/Index.html";
						redir(dir);
					}
				})
			}
		})
	}
	
	function verificarFormulario(){
		
		let fechaActual=new Date();
		let ok=true;
		let mensaje="";

		if(title.value==""){
			ok=false;
			mensaje+="El titulo ha de tener un nombre para poder solicitar el ascenso. \n";
		}
		if(lug.value==""){
			ok=false;
			mensaje+="El Titulo ha de haber sido expedido en algún sitio. \n";
		}
		if(date.value==""){
			ok=false;
			mensaje+="El titulo ha de haber sido expedido en algún momento. \n";
		} else if (date.value > fechaActual){
			ok=false;
			mensaje+="La fecha no puede ser posterior a la actual. \n";
		}
		
		if(ok==true){
			enviarSolicitud(title.value, lug.value, date.value);
		} else {
			alert(mensaje);
		}

	}
	
	function enviarSolicitud(title, lug, date) {
		let fecha=new Date(date);
		console.log()
		let year= fecha.getFullYear();
		let mes=fecha.getMonth()+1;
		let day=fecha.getDate();
		console.log(year+"/"+mes+"/"+day)
		fetch('GestionSolicitud?op=1&Title='+title+'&lugar='+lug+'&year='+year+'&mes='+mes+'&dia='+day)
		.then(response => response.json())
		.then(respuesta => {
			if (respuesta==true) {
				alert("Solicitud Enviada");
				let dir="http://localhost:8080/Symposium_web/Index.html";
				redir(dir);
			} else {
				alert("Error al enviar la solicitud, inténtalo de nuevo más tarde)");
			}
		})
	}
	
	function redir(dir){
		window.location.href=dir;
	}
})