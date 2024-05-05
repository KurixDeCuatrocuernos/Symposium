/**
 * 
 */
window.addEventListener ("DOMContentLoaded", function(){
	
	
	function redir(dir){
		window.location.href = dir;
	}
	
	function llamada(){
		//esto está mejor si se hace con xml
		fetch('GestionLibroListar')
		.then(response => response.json())
		.then(data => pintar(data))
	}
	
	function llamadaBorrar(id){
		
		let cell=confirm("¿Estás seguro de que quieres borrar el libro con la id: "+id+"?");
		
		if (cell==true){
		//Si es true, borramos			
			// Mejor con xml
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
				console.error("Error: ",error);
				alert("Error al borrar el libro")
			})
			
		
		} else {
			let enfasis= document.querySelector("tr.fila"+id);
				enfasis.setAttribute("style","background-color:white");
			alert("Borrado cancelado");
		}
		
	}
	
	function llamadaModificar(id) {
		fetch('GestionLibroEditar?id='+id)
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

		let html = "<table border=1> <tr><td><strong>ISBN</strong></td></td><td><strong>Nombre del Autor</strong></td><td><strong>Titulo</strong></td><td><strong>Editorial</strong></td><td><strong>Fecha de Publicación</strong></td><td><strong>Tipo</strong></td></tr>";
		let rows = 0;/*Revisar si es necesario*/
		
		for(let i=0;i<resultados.length;i++){
				
			html += "<tr class=fila"+resultados[i].ISBN+"><td>"+resultados[i].ISBN+"</td><td>"+resultados[i].Autor+"</td><td>"+resultados[i].Titulo+"</td><td>"+resultados[i].Editorial+"</td><td>"+resultados[i].Fecha_publicacion+"</td><td>"+resultados[i].Tipo+"</td><td><button id="+resultados[i].ISBN+" type=button class=modif>Edit</button></td><td><button type=button class=borrar id="+resultados[i].ISBN+">Delete</button></td></tr>"
			rows++; /*Revisar si es necesario*/ 
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
		
		let modif=document.querySelectorAll("button.modif");
		modif.forEach(bot => {
			
			let id = bot.getAttribute("id");
			bot.addEventListener("click", function edit(){
				
				llamadaModificar(id);
				let dir="http://localhost:8080/Symposium_web/ModificarLibro.html";
				redir(dir);
				
			})
				
		})
		
		let ins=document.querySelector("button.insertarObra");
		ins.addEventListener("click", function insert(){
			let dir="http://localhost:8080/Symposium_web/SubirLibro.html"
			redir(dir);
		})
		
	} 
	
		llamada();	

})