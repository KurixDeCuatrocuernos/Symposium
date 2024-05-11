window.addEventListener("DOMContentLoaded", function() {
	
	recogerFormulario();
	accionEstudio();
	let select = document.getElementById("Nivel");
	var html="";
	var id=0;

	select.addEventListener("change", function() {
	  let opcionSeleccionada = parseInt(select.options[select.selectedIndex].value);

		   if (opcionSeleccionada === 10) {
		    accionEstudio();
		  } else if (opcionSeleccionada === 30) {
		    accionTitulo();
		  } else if (opcionSeleccionada === 50) {
		    accionAdmin();
		  }
	 
	});
	
	let accept= document.getElementById("Accept");
	accept.addEventListener("click", function acc(){
		
		verificarFormulario();
		
	});

	function accionEstudio(){
		
		html = "<ul type=none><li><h2 alt=Escuela del estudiante>Estudios del estudiante: </h2><input type=text id=Studies class=Studies name=Studies></li> <li><h2 alt=Escuela donde estudia actualmente o ha estudiado el estudiante>Escuela del estudiante: </h2><input type=text id=School class=School name=School></li></ul>";
		document.getElementById("valoresConcretos").innerHTML = html;
		console.log("Has seleccionado: Estudiante");
	
	}
	
	function accionTitulo() {
		//falta añadir el archivo, si me da tiempo lo implementaré
		html="<ul type=none><li><h2 alt=Nombre del título que tiene el usuario titulado (por ejemplo, Licenciado en ciencias sociales y humanidades, o Titulado en Desarrollo de aplicaciones web)>Nombre del Titulo: </h2><input type=text id=Titulo class=Titulo name=Titulo></li><li><h2 alt=Nombre del lugar donde se expidió el título, puede ser un Instituto o una Universidad> Lugar de obtención del titulo: </h2><input type=text id=Lugar class=Lugar name=Lugar></li><li><h2 alt=Año de expedición del título>Año de obtención del Titulo: </h2><input type=int id=Year class=Year name=Year></li></ul>";
		document.getElementById("valoresConcretos").innerHTML = html;
		console.log("Has seleccionado: Titulado");
		
	}
	
	function accionAdmin() {
		
		html="<ul type=none><li><h2 alt=Número de teléfono para contactar con el administrador en cuestión>Número de Telefono de contacto: </h2><input type=number id=Telf class=Telf name=Telf></li></ul>";
		document.getElementById("valoresConcretos").innerHTML = html;
		console.log("Has seleccionado: Administrador");
		
	}
	
	function verificarFormulario(){
		let Name = document.getElementById("Nombre");
		let SurN = document.getElementById("Apellidos");
		let Age = document.getElementById("Age");
		let fechaActual = new Date();
		let añoActual = fechaActual.getFullYear();
		let Mail = document.getElementById("Email");
		let ok=true;
		let mensaje="";
		let select = document.getElementById("Nivel");
		let opcionSeleccionada = parseInt(select.options[select.selectedIndex].value);
		//no verifico estudios porque esa información es opcional
		if (opcionSeleccionada === 30) {
		    let Titulo = document.getElementById("Titulo");
		    let Lugar = document.getElementById("Lugar");
		    let Year = document.getElementById("Year");
		    if (Titulo === ""){
				ok=false;
				mensaje += "Es necesario introducir un titulo para que el usuario pueda ser Titulado. \n";
				Titulo.setAttribute("Style=color", "red");
			}
			if (Lugar === ""){
				ok=false;
				mensaje += "Es necesario introducir un Lugar de expedición del título para que el usuario pueda ser Titulado. \n";
				Lugar.setAttribute("Style=color", "red");
			}
			if (Year === ""){
				ok=false;
				mensaje += "Es necesario introducir un Año de titulación para que el usuario pueda ser Titulado. \n";
				Year.setAttribute("Style=color", "red");
			} else if (Year<1800 && Year>añoActual) {
				ok=false;
				mensaje += "No es posible que hayas obtenido el titulo en un año que todavía no ha llegado ni que tengas más de 2 siglos de edad. \n";
				Year.setAttribute("Style=color", "red");
			}
		} else if (opcionSeleccionada === 50) {
		    let Telf = document.getElementById("Telf");
			if (Telf === ""){
				ok=false;
				mensaje += "Es necesario un número de contacto para poder ser Administrador. \n";
				Telf.setAttribute("style=color", "red");
			}
		}
		if (Name === ""){
			ok=false;
			mensaje+="Es necesario introducir un Nombre de usuario. \n";
			Name.setAttribute("style=color", "red");
		}
		if (SurN === ""){
			ok=false;
			mensaje+="Es necesario introducir un Apellido. \n";
			SurN.setAttribute("style=color", "red");
		}
		if (Age === ""){
			ok=false;
			mensaje+="Es necesario determinar tu edad. \n";
			Age.setAttribute("style=color", "red");
		}
		if (Mail.value === ""){
			ok=false;
			mensaje+="Es necesario proporcionar un correo electrónico. \n";
			Mail.setAttribute("style=color", "red");
		}
		if (ok==true) {
			ModificarU();
		} else {
			alert(mensaje);
		}
	}
	
	function recogerFormulario(){
	
	fetch ('GestionUsuarioModificar?op=0', {method:'Post'})
	.then(response => response.json())
	.then(data => pintarFormulario(data))
	.catch(error => console.log("Error: "+error));
	
}

function pintarFormulario(resultados){
	id = parseInt(resultados.ID);
	let Name = document.getElementById("Nombre");
	let SurN = document.getElementById("Apellidos");
	let Age = document.getElementById("Age");
	let Mail = document.getElementById("Email");
	Name.setAttribute("value", resultados.Nombre);
	SurN.setAttribute("value", resultados.Apellidos);
	Age.setAttribute("value", resultados.Edad);
	Mail.setAttribute("value", resultados.Email);
	
}

function ModificarU(){
	
		let Name = document.getElementById("Nombre");
		let SurN = document.getElementById("Apellidos");
		let Age = document.getElementById("Age");
		let Mail = document.getElementById("Email");
		let select = document.getElementById("Nivel");
		let opcionSeleccionada = parseInt(select.options[select.selectedIndex].value);
		if (opcionSeleccionada === 10) {
			let Studies = document.getElementById("Studies");
			let School = document.getElementById("School");
			if (Studies === "" || School === ""){
				
				fetch('GestionUsuarioModificar?id='+id+'&op=1&Name='+Name.value+'&Nivel=10&Surname='+SurN.value+'&Age='+Age.value+'&Mail='+Mail.value, {method:'Post'})
				.then(response => response.json())
				.then(result => {
					if (result===true){
						alert("Usuario Modificado con éxito")
						let dir="http://localhost:8080/Symposium_web/ListarUsuarios.html";
						redir(dir);
					} else {
						alert("HUBO UN PROBLEMA AL MODIFICAR AL USUARIO");
					}
				})
			} else {
				
				fetch('GestionUsuarioModificar?id='+id+'&op=2&Name='+Name.value+'&Nivel=10'+'&Surname='+SurN.value+'&Age='+Age.value+'&Mail='+Mail.value+'&Studies='+Studies.value+'&School='+School.value, {method:'Post'})
				.then(response => response.json())
				.then(result => {
					if (result===true){
						alert("Usuario Modificado con éxito")
						let dir="http://localhost:8080/Symposium_web/ListarUsuarios.html";
						redir(dir);
					} else {
						alert("HUBO UN PROBLEMA AL MODIFICAR AL USUARIO");
					}
				})
				
			}
		} else if (opcionSeleccionada === 30) {
			let Titulo = document.getElementById("Titulo");
		    let Lugar = document.getElementById("Lugar");
		    let Year = document.getElementById("Year");
		    
		    fetch('GestionUsuarioModificar?id='+id+'&op=3&Name='+Name.value+'&Nivel=30&Surname='+SurN.value+'&Age='+Age.value+'&Mail='+Mail.value+'&Titulo='+Titulo.value+'&Lugar='+Lugar.value+'&Year='+Year.value, {method:'Post'})
				.then(response => response.json())
				.then(result => {
					if (result === true){
						alert("Usuario Modificado con éxito")
						let dir="http://localhost:8080/Symposium_web/ListarUsuarios.html";
						redir(dir);
					} else {
						alert("HUBO UN PROBLEMA AL MODIFICAR AL USUARIO");
					}
				})
		} else if (opcionSeleccionada===50) {
			let Telf = document.getElementById("Telf");
			
			 fetch('GestionUsuarioModificar?id='+id+'&op=4&Name='+Name.value+'&Nivel=50&Surname='+SurN.value+'&Age='+Age.value+'&Mail='+Mail.value+'&Telf='+Telf.value, {method:'Post'})
				.then(response => response.json())
				.then(result => {
					if (result===true){
						alert("Usuario Modificado con éxito")
						let dir="http://localhost:8080/Symposium_web/ListarUsuarios.html";
						redir(dir);
					} else {
						alert("HUBO UN PROBLEMA AL MODIFICAR AL USUARIO");
					}
				})
		}
		
		
	}
	
	function redir(dir){
		
		window.location.href = dir;
		
	}
});
