window.addEventListener("DOMContentLoaded", function(){
	
	let ini= document.querySelector("button.IrAInicio");
	verificarAdmin();
	
	ini.addEventListener( "click", function redir3(){
		let dir="http://localhost:8080/Symposium_web/Index.html";
		redir(dir);
	})
	


function redir(dir){
	
	window.location.href = dir;

}

function verificarAdmin(){
	fetch('GestionInicioSesion?op=1', {method:'POST'})
	.then(response => response.json())
	.then (respuesta => {
		if (respuesta === true) {
			llamada();
		} else {
			alert("Antes inicia sesión como Administrador")
			let dir="http://localhost:8080/Symposium_web/IniciarSesion.html"
			redir(dir);
		}
	})
}

function llamada(){
	
	fetch('GestionUsuariosListar')
	.then(response => response.json())
	.then(data => pintar(data))
	
}

function pintar(resultados){

	let html = "<table border=1 class=tablaListado> <tr><th><strong>IDUsuario</strong></th><th><strong>Nombre de Usuario</strong></th><th><strong>Nivel</strong></th><th colspan=2>Acción</th></tr>";
	let lvl="";
	for(let i=0;i<resultados.length;i++){
		
		if (resultados[i].Nivel>49){
			lvl="Administrador";
		} else if (resultados[i].Nivel<50 && resultados[i].Nivel>29){
			lvl="Titulado";
		} else if(resultados[i].Nivel<30 && resultados[i].Nivel>9){
			lvl="Estudiante"
		} else {
			lvl="Usuario baneado"
		}
			
		html += "<tr class=fila"+resultados[i].Id+"><td>"+resultados[i].Id+"</td><td>"+resultados[i].Nombre+" "+resultados[i].Apellidos+"</td><td id=Nivel>"+lvl+"</td><td><button id="+resultados[i].Id+"  type=button class=modifU>Modificar</button><button type=button class=borrarU id="+resultados[i].Id+">Eliminar Usuario</button></td></tr>"

	}	
			
	html += "</table>";
			
	document.getElementById("listado").innerHTML = html;
		
		//meto el evento escuchador para borrar tras generar el objeto, y no antes
		
	let erase=document.querySelectorAll("button.borrarU");
	erase.forEach(bot =>{
			
		let id = bot.getAttribute("id");	
		let enfasis = document.querySelector("tr.fila"+id);
			
		bot.addEventListener("click", function emph(){
				
			enfasis.setAttribute("style","background-color:red"); 
				// Agrega un pequeño retraso antes de llamar a llamadaBorrar() para que enfasis se ejecute antes
    		setTimeout(function() {
       		 	llamadaBorrarU(id);
    		}, 100); // Espera 100 milisegundos
				
				
		});
					
	});
		
		//meto el evento escuchador para editar tras generar el objeto y no antes.
		
	let modifL=document.querySelectorAll("button.modifU");
	modifL.forEach(bot => {
			
		let id = bot.getAttribute("id"); 
		bot.addEventListener("click", function editU(){
			
			llamadaModificarU(id);
			setTimeout( function(){
				let dir="http://localhost:8080/Symposium_web/ModificarUsuario.html";
				redir(dir);
			}, 200);//espera 200 milisegundos
			
				
		})
				
	})
	
}

function llamadaBorrarU(id){
		
	let cell=confirm("¿Estás seguro de que quieres borrar el Usuario con la id: "+id+"?");
		
	if (cell==true){
		//Si es true, borramos			
		fetch('GestionUsuarioBorrar?id='+id,{method: 'post'})
		.then(response => {
			if(response.ok){
				alert("Usuario borrado con éxito");
       		 	let dir="http://localhost:8080/Symposium_web/ListarUsuarios.html";
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
//Este es ligeramente diferente
function llamadaModificarU(id) {
	
	fetch('GestionUsuarioModificar?id='+id, {method:'GET'})
	.then(response => {
		if(response.ok){
			console.log("Enviado al servlet");
		} else {
			alert("ERROR AL ENVIAR EL LIBRO AL SERVLET");
		}
	})
	.catch(error =>{
		console.log("Se ha producido el error: "+error);
		alert("Error al enviar el Usuario al servlet");
	});
}

});
