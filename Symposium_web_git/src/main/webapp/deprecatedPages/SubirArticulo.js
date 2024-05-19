/**
 * 
 */
window.addEventListener("DOMContentLoaded", function(){
	
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
	
	function validarId(){
		let id = document.getElementById('IdArticulo').value;
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
						let dir = "http://localhost:8080/Symposium_web/ModificarArticulo.html";
						redir(dir);
					}
				} else {
					validarFormulario();
				}
			});
		}
		
	}
	
	function llamadaModificar(id) {
		fetch('GestionArticuloEditar?id='+id)
		.then(response => {
			if(response.ok){
				console.log("Enviado al servlet");
			} else {
				alert("ERROR AL ENVIAR EL LIBRO AL SERVLET");
			}
		})
	}
	
	function validarFormulario(){
		let mensaje="";
		let id = document.getElementById('IdArticulo').value;
		let noma = document.getElementById('NombreAutor').value;
   		let nomar = document.getElementById('NombreArticulo').value;
   		let text=document.getElementById('Texto').value;
   		let temas=document.getElementById('Temas').value;
   		let fechal=document.getElementById('FechaLibro').value;
   		let lugar = document.getElementById('LugarPublicacion').value;
   		let volumen = document.getElementById('VolumenPublicacion').value;
		let ok = true;
		
		if(id == ""|| id<10000000 || id>9999999999999){
			ok = false;
			document.getElementById('IdArticulo').style.color = "red";
			mensaje+="La ISBN/ISSN debe tener como mínimo 8 caracteres y como máximo 13 \n";
			
		} else {
			document.getElementById('IdArticulo').style.color = "green";
		}
			
		if(noma == ""){
			ok = false;
			document.getElementById('NombreAutor').style.color = "red";
			mensaje+="Introduce el nombre del autor.\n";
		} else {
			document.getElementById('NombreAutor').style.color = "green";
		}
			
		if(nomar == ""){
			ok = false;
			document.getElementById('NombreArticulo').style.color = "red";
			mensaje+="Introduce el Titulo de la obra. \n";
		} else {
			document.getElementById('NombreArticulo').style.color = "green";
		}
		
		if(text == ""){
			ok = false;
			document.getElementById('Texto').style.color = "red";
			mensaje+="Introduce el Abstracto del articulo, de no tenerlo, poroporciona una breve descripción\n"
		}else {
			document.getElementById('Texto').style.color = "green";
		}
		
		if(fechal == ""){
			ok = false;
			document.getElementById('FechaLibro').style.color = "red";
			mensaje+="El articulo ha de tener una fecha de publicación \n";
		} else {
			document.getElementById('FechaLibro').style.color = "green";
		}
		
		if(lugar == ""){
			ok = false;
			document.getElementById('LugarPublicacion').style.color = "red";
			mensaje+="Introduce el Lugar de publicacion del articulo \n";
		}else {
			document.getElementById('LugarPublicacion').style.color = "green";
		}
		
		if(temas == ""){
			ok = false;
			document.getElementById('Temas').style.color = "red";
			mensaje+="Introduce alguna categoría a la que pertenezca el articulo \n";
		}else {
			document.getElementById('Temas').style.color = "green";
		}
			
		if(ok == true){
			//si no hay nada en volumen inserto todo lo demás
			if(volumen==""){
				
				insertarArticulo(id, noma, nomar, text, temas, fechal, lugar);
				
			//si hay algo en volumen, lo inserto junto a lo demás
			} else {
				
				insertarArticuloV(id, noma, nomar, text, temas, fechal, lugar, volumen);
				
			}
					
		} else {
			
			alert(mensaje);
		
		}
				   
	}
	
	function insertarArticuloV(id, noma, nomar, text, temas, fechal, lugar, volumen){
		
		fetch('GestionArticuloInsertar?op=2&IdLibro='+id+'&NombreArticulo='+nomar+'&NombreAutor='+noma+'&Texto='+text+'&FechaLibro='+fechal+'&Temas='+temas+'&LugarPublicacion='+lugar+'&VolumenPublicacion='+volumen, {method:'post'})
		.then(response=>response.json())
		.then(cell=> {
			if(cell==false) {
				alert("ERROR AL INSERTAR EL LIBRO");
			} else {
				alert("Articulo insertado con éxito");
				let dir = "http://localhost:8080/Symposium_web/ListarLibro.html";
				redir(dir);
			}
		})
	}

	function insertarArticulo(id, noma, nomar, text, temas, fechal, lugar){
		
		fetch('GestionArticuloInsertar?op=1&IdLibro='+id+'&NombreArticulo='+nomar+'&NombreAutor='+noma+'&Texto='+text+'&FechaLibro='+fechal+'&Temas='+temas+'&LugarPublicacion='+lugar, {method:'post'})
		.then(response=>response.json())
		.then(cell=> {
			if(cell==false) {
				alert("ERROR AL INSERTAR EL LIBRO");
			} else {
				alert("Articulo insertado con éxito");
				let dir = "http://localhost:8080/Symposium_web/ListarLibro.html";
				redir(dir);
			}
		})
	}
 