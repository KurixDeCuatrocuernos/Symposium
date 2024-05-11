/**
 * 
 */
window.addEventListener("DOMContentLoaded", function(){
	
	let but = document.querySelector("button.Aceptar");
	but.addEventListener("click", function accept(){
		verificar();
	});

});
	
	function enviarFormulario(){
		let Name = document.getElementById("Name").value;
		let SurN = document.getElementById("Surname").value;
		let Age = document.getElementById("Age").value;
		let Mail = document.getElementById("Mail").value;
		let Pass = document.getElementById("Contra").value;
		//cambiar a XML
		fetch ('GestionEstudianteInsertar?&Name='+Name+'&Surname='+SurN+'&Age='+Age+'&Mail='+Mail+'&Pass='+Pass)
		.then (response => response.json)
		.then (data => console.log(data))
		
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
	
	
	