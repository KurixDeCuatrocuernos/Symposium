
window.addEventListener("DOMContentLoaded", function(){
	
	let Alias= document.getElementById("alias");
	let mail= document.getElementById("correo");
	let pass = document.getElementById("passw");
	let accept = document.getElementById("subm");
	let cancel= document.getElementById("cancel");
	
	revisarSesion();
	
	accept.addEventListener("click", function subm(){
		console.log("verificando información")
		verificarDatos();	
	})
	
	
	
	cancel.addEventListener("click", function cerrar(){
		console.log("redirijo al inicio");
		let dir = "http://localhost:8080/Symposium_web/Index.html";
		redir(dir);
		
	})	
	
	function revisarSesion() {
		fetch ('GestionInicioSesion?op=9', {method:'POST'})
		.then(response => response.json())
		.then(devolver =>{
			console.log(devolver);
			if (devolver == true){
				alert("Ya has iniciado sesión");
				let dir="http://localhost:8080/Symposium_web/Index.html";
				redir(dir);
			}
		})
	}
	
	function verificarDatos() {
		
		let mensaje="";
		let verificarTexto="@symposium.com";
		let ok=true;
		if (Alias.value=="") {
			ok=false;
			mensaje+="El campo Nombre no puede quedar vacío. \n";	
		}
		if (mail.value=="") {
			ok=false;
			mensaje+="El campo Correo no puede quedar vacío. \n";	
		}
		if (pass.value=="") {
			ok=false;
			mensaje+="El campo Contraseña no puede quedar vacío. \n";	
		}
		if (!mail.value.endsWith(verificarTexto)) {
			ok=false;
			mail.setAttribute("style:color", "red");
			mensaje+="Eso no es un correo electrónico válido. \n";
		}
		if (ok==false) {
			alert(mensaje);
		} else {
			enviarDatos();
		}
			
	}
	
	function enviarDatos(){
		console.log("redirijo al servlet");
		fetch('GestionInicioSesion?Alias='+Alias.value+'&mail='+mail.value+'&pass='+pass.value)
		.then(response => response.json())
		.then(resultado => {
			console.log("Respuesta del servlet: "+resultado);
			if (resultado === true) {
				alert("Has iniciado sesión con exito")
				let dir = "http://localhost:8080/Symposium_web/Index.html"
				redir(dir);
			} else {
				alert("No hemos podido confirmar tu identidad, revisa la información introducida");
			}
		})
	}
	
	
})



function redir(dir){
	window.location.href = dir;
}
