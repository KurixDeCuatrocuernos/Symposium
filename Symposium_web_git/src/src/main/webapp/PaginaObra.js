window.addEventListener("DOMContentLoaded", function(){
	
	llamada();
	verificarSesion();
	var idlibro=0;
	
	function verificarSesion(){
		fetch('GestionInicioSesion?op=9', {method:'POST'})
		.then(response => response.json())
		.then(respuesta => {
			if(respuesta===true){
				comprobarComentario();
			}
		})
	}
	
	function comprobarComentario(){
		fetch('GestionComentarioInsertar?op=8')
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
				let dir= "http://localhost:8080/Symposium_web/IniciarSesion.html";
				redir(dir);
			} else {
				generarComentario();
			}
		})
		
	}
	
	function llamada(){
		
		fetch('GestionObraBuscar', {method:'post'})
		.then(response => response.json())
		.then(datos => {
			pintar(datos);
		})	
	
	}

	function pintar(resultado){
			
			idlibro=resultado.ISBN;
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
		
		fetch('GestionComentarioInsertar?op=4&obra='+idlibro)
		.then(resultados => resultados.json())
		.then(datos => pintarComentariosEs(datos, idlibro))
		
		
	}
	
	function pintarComentariosEs(datos, idlibro) {
		if (datos && datos.value !== null){
				let html
				for (let i=0; i<datos.length; i++){
					html= "<img src=Icons/usuario.png alt=Imagen de Usuario tomada e flaticon: https://www.flaticon.es/resultados?word=libro><div class=grueso><strong>"+datos[i].Titulo+"</strong><p>"+datos[i].Texto+"</p><p id=dateE>"+datos[i].Fecha_comentario+"</p></div><p class=name>"+datos[i].Alias+"</p><p class=lvl>Estudiante</p><p class=valor>"+datos[i].Valoracion_obra+"</p>";
					document.querySelector("div.textoE").innerHTML+=html;
				}
				
		}
		
		fetch('GestionComentarioInsertar?op=5&obra='+idlibro)
		.then(resultados => resultados.json())
		.then(datos => pintarComentariosTit(datos))
	}
	
	function pintarComentariosTit(datos){
		if (datos && datos.value !== null){
				let html
				for (let i=0; i<datos.length; i++){
					html= "<img src=Icons/usuario.png alt=Imagen de Usuario tomada e flaticon: https://www.flaticon.es/resultados?word=libro><div class=grueso><strong>"+datos[i].Titulo+"</strong><p>"+datos[i].Texto+"</p><p id=dateE>"+datos[i].Fecha_comentario+"</p></div><p class=name>"+datos[i].Alias+"</p><p class=lvl>Titulado</p><p class=valor>"+datos[i].Valoracion_obra+"</p>";
					document.querySelector("div.textoT").innerHTML+=html;
				}
				
		}
		pintarValoraciones();
	}
	
	function pintarValoraciones() {
		
		let valorGlobal = document.getElementById("valorGlobal");
		let valorT = document.getElementById("valorTitulados");
		let valorE = document.getElementById("valorEstudiantes");
		
		fetch('GestionValoraciones?op=1&idLibro='+idlibro)
		.then(response=> response.json())
		.then(valores => {
			if (valores != null) {
				valorGlobal.innerHTML=valores;
			}
		})
		
		fetch('GestionValoraciones?op=2&idLibro='+idlibro)
		.then(response => response.json())
		.then(valores => {
			if (valores != null){
				valorT.innerHTML=valores;
			}
		})
		
		fetch('GestionValoraciones?op=3&idLibro='+idlibro)
		.then(response=> response.json())
		.then(valores => {
			if (valores != null) {
				valorE.innerHTML=valores;
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
			
			
			fetch('GestionComentarioInsertar?op=1&obra='+idlibro+'&Titulo='+TI.value+'&Text='+TE.value+'&Valor='+VC.value+'&Year='+year+'&mes='+month+'&dia='+day+'&hora='+hour+'&min='+min+'&sec='+seconds)
			.then(response => response.json())
			.then(res => alert(res))
			
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
			
			
			fetch('GestionComentarioInsertar?op=3&obra='+idlibro+'&Titulo='+TI.value+'&Text='+TE.value+'&Valor='+VC.value+'&Year='+year+'&mes='+month+'&dia='+day+'&hora='+hour+'&min='+min+'&sec='+seconds)
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