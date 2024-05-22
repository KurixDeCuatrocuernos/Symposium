/**
 * 
 */
window.addEventListener("DOMContentLoaded", function(){
	
	verificarSesion();
	
	let but = document.querySelector("button.Aceptar");
	but.addEventListener("click", function accept(){
		verificar();
	});
	let cancel = document.querySelector("button.cancel");
	cancel.addEventListener("click", function cancel(){
		let dir="http://localhost:8080/Symposium_web/Index.html";
		redir(dir);
	});
	let Mail=document.getElementById("Mail");
	let test = document.querySelector("button.Comp");
	test.addEventListener("click", function trial(){
		if(Mail.value!=""){
			let ending="@symposium.com";
			fetch('GestionEstudianteInsertar?mail='+Mail.value, {method:'POST'})
			.then(response => response.json())
			.then(respuesta => {
				if (respuesta===true){
					if(Mail.value.endsWith(ending)){
						alert("Ese correo sirve");
					} else {
						alert("El correo debe terminar por: @symposium.com");	
					}
				} else {
					alert("Ese correo ya está en uso, elige otro");
				}
			})
			
		} else {
			alert("Introduce algún correo para comprobar");
		}
		
	});
	
	function enviarFormulario(){
		let Name = document.getElementById("Name").value;
		let SurN = document.getElementById("Surname").value;
		let Age = document.getElementById("Age").value;
		let Mail = document.getElementById("Mail").value;
		let Pass = document.getElementById("Contra").value;
		//cambiar a XML
		fetch ('GestionEstudianteInsertar?&Name='+Name+'&Surname='+SurN+'&Age='+Age+'&Mail='+Mail+'&Pass='+Pass)
		.then (response => response.json())
		.then (data => {
			if (data===true){
				fetch('GestionInicioSesion?Alias='+Name+'&mail='+Mail+'&pass='+Pass)
				.then(response => response.json())
				.then(respuesta => {
					if (respuesta === true){
						alert("Has iniciado sesión");
						let dir="http://localhost:8080/Symposium_web/Index.html";
						redir(dir);
					} else {
						alert("Error al iniciar sesión, vuelve a intentarlo más tarde");
						let dir="http://localhost:8080/Symposium_web/Index.html";
						redir(dir);
					}
				})
			} else {
				alert("Error al registrar un nuevo usuario, no es culpa tuya, inténtalo de nuevo más tarde, si persiste ponte en contacto con un administrador");
				console.log("Error al registrar al usuario revisa el fetch y/o el servlet")
			}
		})
		
	}
	
	function verificar(){
		
		let Name = document.getElementById("Name");
		let SurN = document.getElementById("Surname");
		let Age = document.getElementById("Age");
		let Mail = document.getElementById("Mail");
		let Pass = document.getElementById("Contra");
		let RePass = document.getElementById("Repeat");
		let ok = true;
		let mensaje= "";
		
		if (Name.value == "") {
			
			ok=false;
			mensaje += "Introduce un nombre para poder registrarte. \n";
			
		}
		if (SurN.value == "") {
			
			ok=false;
			mensaje += "Introduce un apellido para poder registrarte. \n";
			
		}
		if (Age.value == "") {
			
			ok=false;
			mensaje += "Introduce Tu edad para poder registrarte. \n";
			
		}
		if (Mail.value == "") {
			
			ok=false;
			mensaje += "Introduce un correo para poder registrarte. \n";
			
		}
		if (Pass.value == "") {
			
			ok=false;
			mensaje += "Introduce una contraseña para poder registrarte. \n";
			
		} else if (Pass.value != RePass.value){
			
			ok=false;
			mensaje += "Las contraseñas no coinciden \n";
			Pass.setAttribute("Background-color", "red");
			RePass.setAttribute("Background-color", "red");
			
		} else if (Pass.value)
		
		if (ok==true){
			enviarFormulario();
		} else {
			alert(mensaje);
		}
		
	}
	
	function verificarSesion(){
		fetch('GestionInicioSesion?op=9', {method:'POST'})
		.then(response => response.json())
		.then(respuesta => {
			if(respuesta===true){
				alert("Ya dispones de una cuenta");
				let dir="http://localhost:8080/Symposium_web/Index.html";
				redir(dir);
			}
		})
	}
	
	function redir(dir){
		window.location.href=dir;
	}
	
});