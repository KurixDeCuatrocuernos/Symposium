window.addEventListener ("DOMContentLoaded", function(){
	
			llamada();	

})
	
	
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
			.catch(error => {
				console.log("Se ha producido el error: "+error);
				alert("Error al borrar el libro")
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
		.catch(error =>{
			console.log("Se ha producido el error: "+error);
			alert("Error al enviar el libro al servlet");
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

		let html = "<table border=1> <tr><td><strong>ISBN</strong></td></td><td><strong>Nombre del Autor</strong></td><td><strong>Titulo</strong></td><td><strong>Editorial / Lugar de Publicacion</strong></td><td><strong>Fecha de Publicación</strong></td><td><strong>Tipo</strong></td></tr>";
		
		for(let i=0;i<resultados.length;i++){
			if (resultados[i].Tipo.charAt(0)=='A'){
				html += "<tr class=fila"+resultados[i].ISBN+"><td>"+resultados[i].ISBN+"</td><td>"+resultados[i].Autor+"</td><td>"+resultados[i].Titulo+"</td><td>"+resultados[i].Editorial+"</td><td>"+resultados[i].Fecha_publicacion+"</td><td>"+resultados[i].Tipo+"</td><td><button id="+resultados[i].ISBN+" type=button class=modifA>Edit</button></td><td><button type=button class=borrar id="+resultados[i].ISBN+">Delete</button></td></tr>"
			}
			else {
				html += "<tr class=fila"+resultados[i].ISBN+"><td>"+resultados[i].ISBN+"</td><td>"+resultados[i].Autor+"</td><td>"+resultados[i].Titulo+"</td><td>"+resultados[i].Editorial+"</td><td>"+resultados[i].Fecha_publicacion+"</td><td>"+resultados[i].Tipo+"</td><td><button id="+resultados[i].ISBN+" type=button class=modifL>Edit</button></td><td><button type=button class=borrar id="+resultados[i].ISBN+">Delete</button></td></tr>"
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
		
		let ins=document.querySelector("button.insertarLibro");
		ins.addEventListener("click", function insertL(){
			let dir="http://localhost:8080/Symposium_web/SubirLibro.html"
			redir(dir);
		})
		let insa=document.querySelector("button.insertarArticulo");
		insa.addEventListener("click", function insertA(){
			let dir="http://localhost:8080/Symposium_web/SubirArticulo.html"
			redir(dir);
		})
	} 
	
