window.addEventListener("DOMContentLoaded", function(){
	
	let boton= document.getElementById("search");
	boton.addEventListener("click", function busq(){
		verificarBusqueda();
	})
	
})

function verificarBusqueda(){
	let text = document.getElementById("buscar");
	if (text.value == ""){
		alert("introduce algo para buscar");
	} else {
		console.log(text.value);
		fetch('GestionObraBuscar?op=1&title='+text.value)
		.then(response => {
			if (response.ok === false){
				alert("No se ha encontrado esa obra en el servidor");
			} else {
				//se ha encontrado al menos 1 obra, por lo que afinamos más
				fetch('GestionObraBuscar?op=2')
				.then(response => {
					console.log(response);
					if (response === true) {
						// hay más de una obra
						recogerObras();
					} else {
						// sólo hay una obra
						
						let dir = "http://localhost:8080/Symposium_web/PaginaObra.html"
						redir(dir);
					}
				})
				
			}
		})
	}
}

function recogerObras() {
	fetch ('GestionObraBuscar?op=3')
	.then(response => response.json())
	.then(datos => pintarObras(datos)) 
}

function pintarObras(datos){
	let html="<ul type=none> <label>Obras encontradas: </label>";
	let text = document.getElementById("buscar");
	for (let i=0; i<datos.length; i++){
		//aquí se generan los titulos encontrados
		html += "<li><button id="+datos[i].ISBN+" class=titulo name="+datos[i].Titulo+">"+datos[i].Titulo+", de: "+datos[i].Autor+"</button></li>";
	}
	html+="</ul>";
	document.getElementById("resultadosBuscador").innerHTML = html;
	
	let tit=document.querySelectorAll("button.titulo");
		tit.forEach(bot =>{
			
			let id = bot.getAttribute("id");

			
			bot.addEventListener("click", function choice(){
				console.log(id);
				fetch('GestionObraBuscar?op=4&id='+id)
				let dir= "http://localhost:8080/Symposium_web/PaginaObra.html";
				redir(dir);
			})
					
		})	
		
}

function redir(dir){
	window.location.href = dir;
}