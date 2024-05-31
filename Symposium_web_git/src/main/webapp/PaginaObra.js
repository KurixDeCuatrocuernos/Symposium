window.addEventListener("DOMContentLoaded", function(){
	
	// Encabezado y navbar
	
	let iniS= document.getElementById("InicioSesion");
	let home= document.getElementById("logo");
	let dir="";
	
	home.addEventListener("click", function home(){
		dir="./Index.html";
		redir(dir);
	});

	iniS.addEventListener("click", function redir1(){
		dir="./IniciarSesion.html"
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
            for (let i = 0; i < sugerencias.length && i<10; i++) {
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
			dir="./ListarUsuarios.html"
			redir(dir);
		})
		let lisO= document.getElementById("ListarObras");
		lisO.addEventListener("click", function redir3(){
			dir="./ListarLibro.html"
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
				dir = "./Index.html"; 
				redir(dir);
			}, 100);//esperamos 100 milisegundos
					
		} else {
			dir="./Index.html"; 
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
					let dir="./SolicitudAscenso.html"
					redir(dir);
				})	
			}
		})
	}

	// CONTENIDO
	
	let urlparameters= new URLSearchParams(window.location.search);
	var viewkey= urlparameters.get('viewkey');
	var viewtype= urlparameters.get('viewtype');
	llamada();
	verificarSesion();
	
	
	function verificarSesion(){
		fetch('GestionInicioSesion?op=9', {method:'POST'})
		.then(response => response.json())
		.then(respuesta => {
			if(respuesta===true){
				comprobarComentario();
			} else {
				pintarOpcionComentar();
			}
		})
	}
	
	function comprobarComentario(){
		fetch('GestionComentarioInsertar?op=8&obra='+viewkey)
		.then(response => response.json())
		.then(respuesta => {
			if(respuesta===true){
				pintarOpcionComentar();
			} else {
				pintarOpcionModificar();
			}
		})
	}
	
	function pintarOpcionComentar(){
		let html="<button id=comentar class=comentar name=comentar>Comentar</button>";
		let place=document.getElementById("newComentario");
		place.innerHTML=html;
		let coment = document.getElementById("comentar");
		coment.addEventListener("click", function com(){
			comprobarSesion();
		})
	}
	
	function pintarOpcionModificar(){
		let html="<button id=modifC class=modifC name=modifC>Modificar</button>";
		let place=document.getElementById("newComentario");
		place.innerHTML=html;
		let coment = document.getElementById("modifC");
		coment.addEventListener("click", function com(){
			generarComentario2();
		})
	}
	
	function comprobarSesion(){
		//sólo los estudiantes registrados pueden comentar
		fetch('GestionInicioSesion?op=9', {method:'POST'})
		.then(response => response.json())
		.then(respuesta => {
			if (respuesta === false) {
				let dir= "./IniciarSesion.html";
				redir(dir);
			} else {
				generarComentario();
			}
		})
		
	}
	
	function llamada(){
		// aquí tiene que recoger el dato de la url
		
		/*
		if (viewkey && viewtype){
			console.log("Ambas variables existen: "+viewkey+", "+viewtype);
		} else if (viewtype){
			console.log("Sólo reconozco viewtype: "+viewtype);
		}else if (viewkey){
			console.log("Sólo reconozco viewkey: "+viewkey);
		} else{
			console.log("no reconozco ninguna variable");
		}
		*/
		fetch('./GestionObraBuscar?viewkey='+viewkey+'&viewtype='+viewtype, {method:'post'})
		.then(response => response.json())
		.then(datos => {
			if(datos && datos=="emptyness"){
				console.log("Ha habido un fallo al recoger los datos");
			}  else if (datos && datos.ISBN!=0){
				console.log("hay datos");
				pintar(datos);
			} else {
				console.log("No se han recogido los datos");
				alert("No existe esa obra")
				let dir="./Index.html"
				redir(dir);
			}
			
		})	
	
	}

	function pintar(resultado){
			
			viewkey=resultado.ISBN;
			let NumL = document.getElementById("NumLibro");
			let Title = document.getElementById("Titl");
			let Name = document.getElementById("Author");
			let Text = document.getElementById("Text");
			let Dat = document.getElementById("Fecha");
			
			NumL.innerHTML = resultado.ISBN;
			Title.innerHTML= resultado.Titulo;
			Name.innerHTML= resultado.Autor;
			Text.innerHTML= resultado.Abstracto;
			Dat.innerHTML= resultado.Fecha_publicacion;
			
		if(resultado.Tipo.charAt(0)=='L'|| resultado.Tipo.charAt(0)=='l'){
			
				let html= "<h3>Editorial: </h3><p>"+resultado.Editorial+"</p>";
				if (resultado.Categoria && resultado.Categoria.value !== ""){
					html+="<h3>Categoría: </h3><p>"+resultado.Categoria+"</p>";
				}
				document.getElementById("DatosEspecificos").innerHTML=html;
				
		} else {
			let html= "<h3>Lugar de Publicacion: </h3><p>"+resultado.Lugar_publicacion+"</p>";
			//Verifico que los atributos opcionales existan y, en caso de que existan que su valor sea distinto de null (vacío)
			if (resultado.Volumen_publicacion && resultado.Volumen_publicacion !== ""){
				html+="<h3>Volumen de publicacion: </h3><p>"+resultado.Volumen_publicacion+"</p>"
			}
			if (resultado.Temas && resultado.Temas.value !== ""){
				html+="<h3>Temas: </h3><p>"+resultado.Temas+"</p>"
			}
			document.getElementById("DatosEspecificos").innerHTML=html;
		}
		
		fetch('GestionComentarioInsertar?op=4&obra='+viewkey)
		.then(resultados => resultados.json())
		.then(datos => pintarComentariosEs(datos, viewkey))
		
		
	}
	
	function pintarComentariosEs(datos, viewkey) {
		if (datos && datos.value !== null){
				let html
				let color="";
				for (let i=0; i<datos.length; i++){
					if(datos[i].Valoracion_obra>=75){
						color= "background-color:#008C30";
					} else if (datos[i].Valoracion_obra<75 && datos[i].Valoracion_obra>=50){
						color= "background-color:yellowgreen";
					}else if (datos[i].Valoracion_obra<50 && datos[i].Valoracion_obra>=25){
						color= "background-color:orange";
					} else{
						color= "background-color:red";
					}
					html= "<img src=Icons/usuario.png alt=Imagen de Usuario tomada e flaticon: https://www.flaticon.es/resultados?word=libro><div class=grueso><strong>"+datos[i].Titulo+"</strong><p>"+datos[i].Texto+"</p><p id=dateE>"+datos[i].Fecha_comentario+"</p></div><p class=name>"+datos[i].Alias+"</p><p class=lvl>Estudiante</p><p class=valor style="+color+" >"+datos[i].Valoracion_obra+"</p>";
					document.querySelector("div.textoE").innerHTML+=html;
				}
				
		}
		
		fetch('GestionComentarioInsertar?op=5&obra='+viewkey)
		.then(resultados => resultados.json())
		.then(datos => pintarComentariosTit(datos))
	}
	
	function pintarComentariosTit(datos){
		if (datos && datos.value !== null){
				let html
				let color="";
				for (let i=0; i<datos.length; i++){
					if(datos[i].Valoracion_obra>=75){
						color= "background-color:#008C30";
					} else if (datos[i].Valoracion_obra<75 && datos[i].Valoracion_obra>=50){
						color= "background-color:yellowgreen";
					}else if (datos[i].Valoracion_obra<50 && datos[i].Valoracion_obra>=25){
						color= "background-color:orange";
					} else{
						color= "background-color:red";
					}
					html= "<img src=Icons/usuario.png alt=Imagen de Usuario tomada e flaticon: https://www.flaticon.es/resultados?word=libro><div class=grueso><strong>"+datos[i].Titulo+"</strong><p>"+datos[i].Texto+"</p><p id=dateE>"+datos[i].Fecha_comentario+"</p></div><p class=name>"+datos[i].Alias+"</p><p class=lvl>Titulado</p><p class=valor style="+color+" >"+datos[i].Valoracion_obra+"</p>";
					document.querySelector("div.textoT").innerHTML+=html;
				}
				
		}
		pintarValoraciones();
	}
	
	function pintarValoraciones() {
		
		let valorGlobal = document.getElementById("valorGlobal");
		let valorT = document.getElementById("valorTitulados");
		let valorE = document.getElementById("valorEstudiantes");
		
		fetch('GestionValoraciones?op=1&idLibro='+viewkey)
		.then(response=> response.json())
		.then(valores => {
			if (valores != null) {
				valorGlobal.innerHTML=valores;
				if(valores>=75){
					valorGlobal.setAttribute("style","background-color: #008C30");
				} else if (valores<75 && valores>=50){
					valorGlobal.setAttribute("style","background-color: yellowgreen");
				}else if (valores<50 && valores>=25){
					valorGlobal.setAttribute("style","background-color: orange");
				} else{
					valorGlobal.setAttribute("style","background-color: red");
				}
			}
		})
		
		fetch('GestionValoraciones?op=2&idLibro='+viewkey)
		.then(response => response.json())
		.then(valores => {
			if (valores != null){
				valorT.innerHTML=valores;
				if(valores>=75){
					valorT.setAttribute("style","background-color: #008C30");
				} else if (valores<75 && valores>=50){
					valorT.setAttribute("style","background-color: yellowgreen");
				}else if (valores<50 && valores>=25){
					valorT.setAttribute("style","background-color: orange");
				} else{
					valorT.setAttribute("style","background-color: red");
				}
			}
		})
		
		fetch('GestionValoraciones?op=3&idLibro='+viewkey)
		.then(response=> response.json())
		.then(valores => {
			if (valores != null) {
				valorE.innerHTML=valores;
				if(valores>=75){
					valorE.setAttribute("style","background-color: #008C30");
				} else if (valores<75 && valores>=50){
					valorE.setAttribute("style","background-color: yellowgreen");
				}else if (valores<50 && valores>=25){
					valorE.setAttribute("style","background-color: orange");
				} else{
					valorE.setAttribute("style","background-color: red");
				}
			}
		})
	}
	
	

	function generarComentario() {
		
		let html="<form><label><strong>Titulo: </strong><input type=text id=tituloComentario></label><label><strong>Valoracion: </strong><input type=number id=valorComentario></label><label><strong>Comentario: </strong><input type=text id=textoComentario></label><button id=check>Enviar</button></form>";
		document.getElementById("newComentario").innerHTML=html;
		
		let bot=document.getElementById("check");
		bot.addEventListener("click", function check(){
			comentarObra();
		})
	}
	
	function generarComentario2() {
		
		let html="<form><label><strong>Titulo: </strong><input type=text id=tituloComentario></label><label><strong>Valoracion: </strong><input type=number id=valorComentario></label><label><strong>Comentario: </strong><input type=text id=textoComentario></label><button id=check>Enviar</button></form>";
		document.getElementById("newComentario").innerHTML=html;
		
		let bot=document.getElementById("check");
		bot.addEventListener("click", function check(){
			modificarComentario();
		})
	}
	
	function comentarObra(){
		let TI = document.getElementById("tituloComentario");
		let TE = document.getElementById("textoComentario");
		let VC = document.getElementById("valorComentario");
		let ok = true;
		let mensaje="";
		if (TI.value=="") {
			ok=false;
			mensaje +="El Comentario ha de tener un título \n";
		}
		if (TE.value=="") {
			ok=false;
			mensaje +="El comentario ha de tener algún contenido \n";
		}
		if (VC.value=="") {
			ok=false;
			mensaje +="El comentario ha de tener una valoración \n";
		} else if( VC.value<0 || VC.value > 100) {
			ok=false;
			mensaje +="La valoración ha de estar entre 0 y 100 \n";
		}
		if (ok===true){
			
			let date = new Date();
			let year = date.getFullYear();
			let month = date.getMonth();
			let day = date.getDate();
			let hour= date.getHours();
			let min= date.getMinutes();
			let seconds = date.getSeconds();
			
			
			fetch('GestionComentarioInsertar?op=1&obra='+viewkey+'&Titulo='+TI.value+'&Text='+TE.value+'&Valor='+VC.value+'&Year='+year+'&mes='+month+'&dia='+day+'&hora='+hour+'&min='+min+'&sec='+seconds)
			.then(response => response.json())
			.then(res => {
				if(res==true){
					alert("Comentario subido");
				} else {
					alert("Hubo un problema al procesar tu comentario, inténtalo de nuevo más tarde. \nSi el problema persiste ponte en contacto con un administrador");
				}
				let dir="./PaginaObra.html?viewkey="+viewkey+"&viewtype="+viewtype
				redir(dir);
			})
			
		} else {
			alert(mensaje);
		}
	}
	
	function modificarComentario(){
		let TI = document.getElementById("tituloComentario");
		let TE = document.getElementById("textoComentario");
		let VC = document.getElementById("valorComentario");
		let ok = true;
		let mensaje="";
		if (TI.value=="") {
			ok=false;
			mensaje +="El Comentario ha de tener un título \n";
		}
		if (TE.value=="") {
			ok=false;
			mensaje +="El comentario ha de tener algún contenido \n";
		}
		if (VC.value=="") {
			ok=false;
			mensaje +="El comentario ha de tener una valoración \n";
		} else if( VC.value<0 || VC.value > 100) {
			ok=false;
			mensaje +="La valoración ha de estar entre 0 y 100 \n";
		}
		if (ok===true){
			
			let date = new Date();
			let year = date.getFullYear();
			let month = date.getMonth();
			let day = date.getDate();
			let hour= date.getHours();
			let min= date.getMinutes();
			let seconds = date.getSeconds();
			
			
			fetch('GestionComentarioInsertar?op=3&obra='+viewkey+'&Titulo='+TI.value+'&Text='+TE.value+'&Valor='+VC.value+'&Year='+year+'&mes='+month+'&dia='+day+'&hora='+hour+'&min='+min+'&sec='+seconds)
			.then(response => response.json())
			.then(res => alert(res))
			
		} else {
			alert(mensaje);
		}
	}
	
	
	function redir(dir){
		window.location.href=dir;
	}
});