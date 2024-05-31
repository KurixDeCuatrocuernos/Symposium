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
	
	/*BUSCADOR*/ 
	
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
	
	function pedirSugerencias(str){
		if(str.length==0){
			document.getElementById("sugerencias").innerHTML="";
		} else {
			peticion(str);
		}
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
						imprimirAvisoSolicitud();
					}
				})
			}
		})
	}
	
	function imprimirAvisoSolicitud(){
		fetch('GestionSolicitud?op=2', {method:'POST'})
		.then(response => response.json())
		.then(solicitud => {
			if (solicitud===true){
				let red = confirm("Hay nuevas solicitudes de titulado sin revisar, \n ¿Quieres ir a revisarlas?")
				if (red===true){
					let dir = "http://localhost:8080/Symposium_web/ListarSolicitudes.html";
					redir(dir);
				}
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
		window.location.href=dir;
	}

	//contenido

	pintarComenT();

	function pintarComenT(){
		let caja1= document.getElementById("comentariosTitulados");
		let html="";
		fetch('GestionComentarioInsertar?op=9')
		.then(response => response.json())
		.then(resultado => {
			if (resultado) {
				for(let i=0; i<resultado.length; i++){
					html+="<div class=comentario>"
					html+="<div class=obraCom><img src=Icons/libro-cerrado.png alt=icono de libro class=IconoLibro><div class=datosObra><strong class=tituloObra>"+resultado[i].TituloObra+"</strong><p class=autorObra>"+resultado[i].AutorObra+"</p></div></div><div class=comentarioDeObra><img src=Icons/usuario.png alt=icono de usuario class=IconoUser><div class=datosComentario><strong class=tituloComentario>"+resultado[i].Titulo+"</strong><p class=textoComentario>"+resultado[i].Texto+"</p><p class=autorComentario>"+resultado[i].Alias+" - Titulado</p><p class=fechaComentario>"+resultado[i].Fecha_comentario+"</div></div></div>";
					html+="</div>";
				}
				caja1.innerHTML=html;
				pintarComenES();
			} else {
				console.log("No hay datos, revisa el servlet")
			}
		})
	}

	function pintarComenES(){
		let caja2=document.getElementById("comentariosEstudiantes")
		let html="";
		fetch('GestionComentarioInsertar?op=10')
		.then(response => response.json())
		.then(resultado => {
			if (resultado) {
				for(let i=0; i<resultado.length; i++){
					html+="<div class=comentario>"
					html+="<div class=obraCom><img src=Icons/libro-cerrado.png alt=icono de libro class=IconoLibro><div class=datosObra><strong class=tituloObra>"+resultado[i].TituloObra+"</strong><p class=autorObra>"+resultado[i].AutorObra+"</p></div></div><div class=comentarioDeObra><img src=Icons/usuario.png alt=icono de usuario class=IconoUser><div class=datosComentario><strong class=tituloComentario>"+resultado[i].Titulo+"</strong><p class=textoComentario>"+resultado[i].Texto+"</p><p class=autorComentario>"+resultado[i].Alias+" - Estudiante</p><p class=fechaComentario>"+resultado[i].Fecha_comentario+"</div></div></div>";
					html+="</div>";
				}
				caja2.innerHTML=html;
				pintarNewComenT();
			} else {
				console.log("No hay datos, revisa el servlet")
			}
		})
	}

	function pintarNewComenT(){
		let caja3=document.getElementById("comentariosNuevosT")
		let html="";
		fetch('GestionComentarioInsertar?op=11')
		.then(response => response.json())
		.then(resultado => {
			if (resultado) {
				for(let i=0; i<resultado.length; i++){
					html+="<div class=comentario>"
					html+="<div class=obraCom><img src=Icons/libro-cerrado.png alt=icono de libro class=IconoLibro><div class=datosObra><strong class=tituloObra>"+resultado[i].TituloObra+"</strong><p class=autorObra>"+resultado[i].AutorObra+"</p></div></div><div class=comentarioDeObra><img src=Icons/usuario.png alt=icono de usuario class=IconoUser><div class=datosComentario><strong class=tituloComentario>"+resultado[i].Titulo+"</strong><p class=textoComentario>"+resultado[i].Texto+"</p><p class=autorComentario>"+resultado[i].Alias+" - Titulado</p><p class=fechaComentario>"+resultado[i].Fecha_comentario+"</div></div></div>";
					html+="</div>";
				}
				caja3.innerHTML=html;
				pintarNewComenEs();
			} else {
				console.log("No hay datos, revisa el servlet")
			}
		})
	}

	function pintarNewComenEs(){
		let caja4=document.getElementById("comentariosNuevosE");
		let html="";
		fetch('GestionComentarioInsertar?op=12')
		.then(response => response.json())
		.then(resultado => {
			if (resultado) {
				for(let i=0; i<resultado.length; i++){
					html+="<div class=comentario>"
					html+="<div class=obraCom><img src=Icons/libro-cerrado.png alt=icono de libro class=IconoLibro><div class=datosObra><strong class=tituloObra>"+resultado[i].TituloObra+"</strong><p class=autorObra>"+resultado[i].AutorObra+"</p></div></div><div class=comentarioDeObra><img src=Icons/usuario.png alt=icono de usuario class=IconoUser><div class=datosComentario><strong class=tituloComentario>"+resultado[i].Titulo+"</strong><p class=textoComentario>"+resultado[i].Texto+"</p><p class=autorComentario>"+resultado[i].Alias+" - Estudiante</p><p class=fechaComentario>"+resultado[i].Fecha_comentario+"</div></div></div>";
					html+="</div>";
				}
				caja4.innerHTML=html;
			} else {
				console.log("No hay datos, revisa el servlet")
			}
		})
	}

	
})
