/**
 * 
 */
window.addEventListener("DOMContentLoaded", function(){
	//Función para validar la información que se introduzca en el formulario

	let submit=document.querySelector("button.insertarObra");
	submit.addEventListener("click", function accept(){
		validarId();
		
	})
	
	//evento escuchador para cancelar
	let den=this.document.querySelector("button.abort");
	den.addEventListener("click", function deny(){
		let dir="http://localhost:8080/Symposium_web/ListarLibro.html";
		redir(dir);
	})
	
});

	function redir(dir){
		window.location.href = dir;
	}
	
	function insertarLibro(id, noma, noml, text, cat, fechal, edit){
		
		fetch('GestionLibroInsertar?IdLibro='+id+'&NombreLibro='+noml+'&NombreAutor='+noma+'&Texto='+text+'&FechaLibro='+fechal+'&Categoria='+cat+'&Edition='+edit, {method:'post'})
		.then(response=>response.json())
		.then(cell=> {
			if(cell==false) {
				alert("ERROR AL INSERTAR EL LIBRO");
			} else {
				alert("Libro insertado con éxito");
				let dir = "http://localhost:8080/Symposium_web/ListarLibro.html";
				redir(dir);
			}
		})
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
			console.log(error);
			alert("Error al enviar el libro al servlet");
		})
	}
	
	function validarId(){
		let id = document.getElementById('IdLibro').value;
		if (id==""){
			alert("INTRODUCE ALGUNA ID");
		} else {
			fetch('GestionVerificarObra?id='+id)
			.then(response => response.json())
			.then(response => {
				if (response==true){
					alert("La ISBN: "+id+" ya está en la base de datos y no puede introducirse de nuevo.")
					let cell2=confirm("¿Quieres Modificarla?");
					if (cell2==true){
						llamadaModificar(id);
						let dir = "http://localhost:8080/Symposium_web/ModificarLibro.html";
						redir(dir);
					}
				} else {
					validarFormulario();
				}
			});
		}
		
	}
	
	
	function validarFormulario(){
		let mensaje="";
		let id = document.getElementById('IdLibro').value;
		let noma = document.getElementById('NombreAutor').value;
   		let noml = document.getElementById('NombreLibro').value;
   		let text=document.getElementById('Texto').value;
   		let cat=document.getElementById('Categoria').value;
   		let fechal=document.getElementById('FechaLibro').value;
   		let edit = document.getElementById('Edition').value;
		let ok = true;
		
		if(id == ""|| id<10000000 || id>9999999999999){
			ok = false;
			document.getElementById('IdLibro').style.color = "red";
			mensaje+="La ISBN/ISSN debe tener como mínimo 8 caracteres y como máximo 13 \n";
			
		} else {
			document.getElementById('IdLibro').style.color = "green";
		}
			
		if(noma == ""){
			ok = false;
			document.getElementById('NombreAutor').style.color = "red";
			mensaje+="Introduce el nombre del autor.\n";
		} else {
			document.getElementById('NombreAutor').style.color = "green";
		}
			
		if(noml == ""){
			ok = false;
			document.getElementById('NombreLibro').style.color = "red";
			mensaje+="Introduce el Titulo de la obra. \n";
		} else {
			document.getElementById('NombreLibro').style.color = "green";
		}
		
		if(text == ""){
			ok = false;
			document.getElementById('Texto').style.color = "red";
			mensaje+="Introduce el Abstacto del libro, de no tenerlo, poroporciona una breve descripción\n"
		}else {
			document.getElementById('Texto').style.color = "green";
		}
		
		if(fechal == ""){
			ok = false;
			document.getElementById('FechaLibro').style.color = "red";
			mensaje+="El libro ha de tener una fecha de publicación \n";
		} else {
			document.getElementById('FechaLibro').style.color = "green";
		}
		
		if(edit == ""){
			ok = false;
			document.getElementById('Edition').style.color = "red";
			mensaje+="Introduce la editorial del libro \n";
		}else {
			document.getElementById('Edition').style.color = "green";
		}
		
		if(cat == ""){
			ok = false;
			document.getElementById('Categoria').style.color = "red";
			mensaje+="Introduce alguna categoría a la que pertenezca el libro \n";
		}else {
			document.getElementById('Categoria').style.color = "green";
		}
			
		if(ok == true){
			
			insertarLibro(id, noma, noml, text, cat, fechal, edit);
				
		} else {
			
			alert(mensaje);
		
		}
				   
	}
