/**
 * 
 */
window.addEventListener("DOMContentLoaded", function(){
	
	verificarSesion();
	
	let acc = document.getElementById("accept");
	let deny= document.getElementById("cancel");
	let title= document.getElementById("titulo");
	let lug= document.getElementById("LugarTitulo");
	let date=document.getElementById("fechaTitulo");
	
	acc.addEventListener("click", function subm(){
		verificarFormulario();
	})
	
	deny.addEventListener("click", function exit(){
		let dir="http://localhost:8080/Symposium_web/Index.html";
		redir(dir);
	})
	
	function verificarSesion(){
		fetch('GestionInicioSesion?op=9', {method:'POST'})
		.then(response => response.json())
		.then(verificar => {
			if (verificar===true){
				fetch('GestionInicioSesion?op=3', {method:'POST'})
				.then(response => response.json())
				.then(estudiante => {
					if(estudiante===true){
						//es estudiante y puede solicitar el ascenso
					} else {
						alert("No puedes solicitar el ascenso a Titulado");
						let dir="http://localhost:8080/Symposium_web/Index.html";
						redir(dir);
					}
				})
			}
		})
	}
	
	function verificarFormulario(){
		
		let fechaActual=new Date();
		let ok=true;
		let mensaje="";

		if(title.value==""){
			ok=false;
			mensaje+="El titulo ha de tener un nombre para poder solicitar el ascenso. \n";
		}
		if(lug.value==""){
			ok=false;
			mensaje+="El Titulo ha de haber sido expedido en algún sitio. \n";
		}
		if(date.value==""){
			ok=false;
			mensaje+="El titulo ha de haber sido expedido en algún momento. \n";
		} else if (date.value > fechaActual){
			ok=false;
			mensaje+="La fecha no puede ser posterior a la actual. \n";
		}
		
		if(ok==true){
			enviarSolicitud(title.value, lug.value, date.value);
		} else {
			alert(mensaje);
		}

	}
	
	function enviarSolicitud(title, lug, date) {
		let fecha=new Date(date);
		console.log()
		let year= fecha.getFullYear();
		let mes=fecha.getMonth()+1;
		let day=fecha.getDate();
		console.log(year+"/"+mes+"/"+day)
		fetch('GestionSolicitud?op=1&Title='+title+'&lugar='+lug+'&year='+year+'&mes='+mes+'&dia='+day)
		.then(response => response.json())
		.then(respuesta => {
			if (respuesta==true) {
				alert("Solicitud Enviada");
				let dir="http://localhost:8080/Symposium_web/Index.html";
				redir(dir);
			} else {
				alert("Error al enviar la solicitud, inténtalo de nuevo más tarde)");
			}
		})
	}
	
	function redir(dir){
		window.location.href=dir;
	}
})