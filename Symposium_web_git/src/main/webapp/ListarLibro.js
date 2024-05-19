window.addEventListener ("DOMContentLoaded", function(){
			
	
			
	verificarAdmin();
	
	let ini= document.querySelector("button.IrAInicio");
			
	ini.addEventListener( "click", function redir3(){
		let dir="http://localhost:8080/Symposium_web/Index.html";
		redir(dir);
	})

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
				enfasis.setAttribute("style","background-color:white");
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
		.catch(error =>{
			console.error("Error: ", error);
			alert("Error al enviar el libro al servlet");
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