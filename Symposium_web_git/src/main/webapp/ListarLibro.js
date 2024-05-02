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
		
		let cell=confirm("Estás seguro de que quieres borrar el libro con la id: "+id);
		
		if (cell==true){
		//Si es true, borramos			
			// Mejor con xml
			fetch('GestionLibroBorrar?id='+id,{
				method: 'post'
			})
			.then(response => {
				if(response.ok){
					alert("Borrado");
				} else{
					alert("ERROR AL BORRAR EL LIBRO");
				}
			})
			.catch(error => {
				console.error('Error: ',error);
				alert("Error al borrar el libro")
			})
		
		} else {
			let enfasis= document.querySelector("tr.fila"+id);
				enfasis.setAttribute("style","background-color:white");
			alert("Borrado cancelado");
		}
		
	}
	
	
	function pintar(resultados){

		let html = "<table border=1> <tr><td><strong>ISBN</strong></td></td><td><strong>Nombre del Autor</strong></td><td><strong>Titulo</strong></td><td><strong>Editorial</strong></td><td><strong>Fecha de Publicación</strong></td></tr>";
		let rows = 0;
		
		for(let i=0;i<resultados.length;i++){
				
			html += "<tr class=fila"+resultados[i].ISBN+"><td>"+resultados[i].ISBN+"</td><td>"+resultados[i].Autor+"</td><td>"+resultados[i].Titulo+"</td><td>"+resultados[i].Editorial+"</td><td>"+resultados[i].Fecha_publicacion+"</td><td><button type=button id=modif>Edit</button></td><td><button type=button class=borrar id="+resultados[i].ISBN+">Delete</button></td></tr>"
			rows++;
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
				console.log("borrar");
				// Agrega un pequeño retraso antes de llamar a llamadaBorrar() para que emph() se ejecute antes
    			setTimeout(function() {
       		 		llamadaBorrar(id);
    			}, 100); // Espera 100 milisegundos
				
				
			})
					
		})
		
	} 
	
		llamada();	

})