window.addEventListener("DOMContentLoaded", function(){
	
	llamada();
	let coment = document.getElementById("comentar");
	coment.addEventListener("click", function com(){
		comprobarSesion();
	})
	var idlibro=0;
	

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
			
			NumL.innerHTML = "ISBN: "+resultado.ISBN;
			Title.innerHTML= "Titulo: "+resultado.Titulo;
			Name.innerHTML= "Autor: "+resultado.Autor;
			Text.innerHTML= "Resumen: "+resultado.Abstracto;
			Dat.innerHTML= "Fecha de Publicacion: "+resultado.Fecha_publicacion;
			
		if(resultado.Tipo.charAt(0)=='L'|| resultado.Tipo.charAt(0)=='l'){
			
				let html= "<p>Editorial: "+resultado.Editorial+"</p>";
				if (resultado.Categoria && resultado.Categoria.value !== ""){
					html+="<p>Categoría: "+resultado.Categoria+"</p>";
				}
				document.getElementById("DatosEspecificos").innerHTML=html;
				
		} else {
			let html= "<p>Lugar de Publicacion: "+resultado.Lugar_publicacion+"</p>";
			//Verifico que los atributos opcionales existan y, en caso de que existan que su valor sea distinto de null (vacío)
			if (resultado.Volumen_publicacion && resultado.Volumen_publicacion !== ""){
				html+="<p>Volumen de publicacion: "+resultado.Volumen_publicacion+"</p>"
			}
			if (resultado.Temas && resultado.Temas.value !== ""){
				html+="<p>Temas: "+resultado.Temas+"</p>"
			}
			document.getElementById("DatosEspecificos").innerHTML=html;
		}
		
		fetch('GestionComentarioInsertar?op=4&obra='+idlibro)
		.then(resultados => resultados.json())
		.then(datos => pintarComentariosEs(datos, idlibro))
		
		
	}
	
	function pintarComentariosEs(datos, idlibro) {
		if (datos && datos.value !== null){
			for (let i=0; i<datos.length; i++){
				let html= "<div>"+datos[i].Titulo+"</div><div>"+datos[i].Texto+"</div><div>Autor</div><div>Estudiante</div><div>"+datos[i].Fecha_comentario+"</div><div>"+datos[i].Valoracion_obra+"</div>";
				document.getElementById("comentariosEstudiantes").innerHTML+=html;
			}
		}
		
		fetch('GestionComentarioInsertar?op=5&obra='+idlibro)
		.then(resultados => resultados.json())
		.then(datos => pintarComentariosTit(datos))
	}
	
	function pintarComentariosTit(datos){
		if (datos && datos.value !== null){
			for (let i=0; i<datos.length; i++){
				let html= "<div>"+datos[i].Titulo+"</div><div>"+datos[i].Texto+"</div><div>Autor</div><div>Titulado</div><div>"+datos[i].Fecha_comentario+"</div><div>"+datos[i].Valoracion_obra+"</div>";
				document.getElementById("comentariosTitulados").innerHTML+=html;
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
		
		let html="<ul type=none><li><label>Titulo: <input type=text id=tituloComentario></label></li><li><label>Comentario: <input type=text id=textoComentario></label></li><li><label>Valoracion: <input type=number id=valorComentario></label></li></ul><button id=check>Enviar Comentario</button>";
		document.getElementById("newComentario").innerHTML=html;
		
		let bot=document.getElementById("check");
		bot.addEventListener("click", function check(){
			comentarObra();
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

	
	
	function redir(dir){
		window.location.href=dir;
	}
});