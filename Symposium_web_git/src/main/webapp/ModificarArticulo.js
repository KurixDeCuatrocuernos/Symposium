/**
 * 
 */
window.addEventListener("DOMContentLoaded", function(){
	
	//nada más entrar pintamos el formulario
	var idOrigen=0;
	llamadaModificar();
		
	//evento escuchador para aceptar
	let acc=this.document.querySelector("button.modificar");
	acc.addEventListener("click", function subm(){
		let cell=false;
		let id=document.getElementById('IdArticulo').value;
		cell=verificarFormulario();
		if (cell==true){
			//si todo está bien, verifico la id
			fetch('GestionVerificarObra?id='+id)
				.then(response => response.json())
				.then(response => {
					if(response==true){
						document.getElementById('IdArticulo').style.color = "red";
						setTimeout(function() {
		       		 		alert("Ese ISBN/ISSN ya está en el listado (pero puedes usarla si se trata de la misma obra");
		       		 		let conf=false;
		       		 		conf=confirm("¿Quieres mantener esa ISBN?");
		       		 		if(conf==true){
									modificarSinId();
									alert("Modificando Articulo");
								}
		    			}, 100); // Espera 100 milisegundos
						
					} else {
						modificar();
						alert("Modificando Articulo");
					}
				})	
				.catch(error => {
						console.log(error);
						alert("Error al pintar el formulario")
					});
		} else {
			alert("Introduce todos los datos para poder modificar el Articulo");
		}
	})
	
	//evento escuchador para cancelar
	let den=this.document.querySelector("button.abort");
	den.addEventListener("click", function deny(){
		let dir="http://localhost:8080/Symposium_web/ListarLibro.html";
		redir(dir);
	})	
	
	//evento escuchador para comprobar el ID.
	let ver=this.document.querySelector("button.verify");
	ver.addEventListener("click", function comprobarID(){
		let id=document.getElementById('IdArticulo').value;
		if(id == ""|| id<10000000 || id>9999999999999){
			document.getElementById('IdArticulo').style.color = "red";
			setTimeout(function() {
       		 		alert("El ISBN/ISSN ha de tener como mínimo 8 caracteres y como máximo 13");
    			}, 100); // Espera 100 milisegundos
		} else {
			fetch('GestionVerificarObra?id='+id)
				.then(response => response.json())
				.then(response => {
					if(response==true){
						document.getElementById('IdArticulo').style.color = "red";
						setTimeout(function() {
		       		 		alert("Ese ISBN/ISSN ya está en el listado");
		    			}, 100); // Espera 100 milisegundos
						
					} else {
						document.getElementById('IdArticulo').style.color = "green";
					}
				})	
				.catch(error => {
						console.log(error);
						alert("Error al pintar el formulario")
					});
		}
	
	});

});
	
	function redir(dir){
		window.location.href = dir;
	}
		
		
	function llamadaModificar(){
	//Con XML estaría mejor
	fetch('GestionArticuloEditar', {method: 'post'})
		.then(response => response.json())
		.then(data => pintarFormulario(data))
		.catch(error => {
					console.log(error);
					alert("Error al pintar el formulario")
				});
	}
	
	function pintarFormulario(resultados){
		let id = document.getElementById("IdArticulo");
		let noml = document.getElementById("NombreArticulo");
		let noma = document.getElementById("NombreAutor");
		let text = document.getElementById("Texto");
		let date = document.getElementById("FechaLibro");
		let temas = document.getElementById("Temas");
		let lugar = document.getElementById("LugarPublicacion");
		let volumen = document.getElementById("VolumenPublicacion");
		let tip = document.getElementById("Type");
		
		if(resultados.Volumen_publicacion==""){
			id.value = resultados.ISBN;
		    noml.value = resultados.Titulo;
		    noma.value = resultados.Autor;
		    text.value = resultados.Abstracto;
		    date.value = resultados.Fecha_publicacion;// Corrección aquí
		    temas.value = resultados.Temas;
		    lugar.value = resultados.Lugar_publicacion;
		    tip.value = resultados.Tipo;
		} else {
			id.value = resultados.ISBN;
		    noml.value = resultados.Titulo;
		    noma.value = resultados.Autor;
		    text.value = resultados.Abstracto;
		    date.value = resultados.Fecha_publicacion;// Corrección aquí
		    temas.value = resultados.Temas;
		    lugar.value = resultados.Lugar_publicacion;
		    volumen.value = resultados.Volumen_publicacion
		    tip.value = resultados.Tipo;
		}
		idOrigen=id.value;
		
	
	}
	
	function verificarFormulario(){
		let id = document.getElementById("IdArticulo").value;
		let noml = document.getElementById("NombreArticulo").value;
		let noma = document.getElementById("NombreAutor").value;
		let text = document.getElementById("Texto").value;
		let date = document.getElementById("FechaLibro").value;
		let temas = document.getElementById("Temas").value;
		let lugar = document.getElementById("LugarPublicacion").value;
		let volumen = document.getElementById("VolumenPublicacion").value;
		let tip = document.getElementById("Type").value;
		let mensaje="";
		let ok=true;
		
		if (volumen==""){
			ok=confirm("No has insertado volumen, ¿Estás seguro?");
			if(ok==false){
				mensaje+="Introduce algún volumen \n";
			}
		} 
		
		if(temas==""){
			ok=confirm("No has insertado ningún tema, ¿Estás seguro?");
			if(ok==false){
				mensaje+="Introduce algún tema \n";
			}
		}
		
		if(id == ""|| id<10000000 || id>9999999999999){
			ok = false;
			document.getElementById('IdArticulo').style.color = "red";
			mensaje+="El ISBN ha de tener 8 caracteres como mínimo y 13 como máximo. \n";
		} else {
			document.getElementById('IdArticulo').style.color = "green";
		}
			
		if(noma == ""){
			ok = false;
			document.getElementById('NombreAutor').style.color = "red";
			mensaje+="Ha de tener un autor. \n";
		} else {
			document.getElementById('NombreAutor').style.color = "green";
		}
			
		if(noml == ""){
			ok = false;
			document.getElementById('NombreArticulo').style.color = "red";
			mensaje+="Ha de tener un título. \n";
		} else {
			document.getElementById('NombreArticulo').style.color = "green";
		}
		
		if(text == ""){
			ok = false;
			document.getElementById('Texto').style.color = "red";
			mensaje+="Ha de tener un abstracto, si no, haz el resumen que se te ocurra. \n";
		}else {
			document.getElementById('Texto').style.color = "green";
		}
		
		if(date == ""){
			ok = false;
			document.getElementById('FechaLibro').style.color = "red";
			mensaje+="Ha de tener una fecha de publicación. \n";
		} else {
			document.getElementById('FechaLibro').style.color = "green";
		}
		
		if(lugar == ""){
			ok = false;
			document.getElementById('LugarPublicacion').style.color = "red";
			mensaje+="Ha de haber un lugar donde se publique. \n";
		}else {
			document.getElementById('LugarPublicacion').style.color = "green";
		}
		
		//Es un checkbox, uno como mínimo
		if(tip== ""){
			ok = false;
			document.getElementById('Type').style.color = "red";
			mensaje+="O bien es un libro o un Artículo \n";
		} else {
			document.getElementById('Type').style.color = "green";
		}
			
		if(ok == true){
			alert("Se puede modificar");
		} else {
			alert(mensaje);
		}
		return ok;
	}		
	//Modifica el artículo compleatamente
	function modificar(){
		console.log(idOrigen);
		let id = document.getElementById("IdArticulo").value;
		let noml = document.getElementById("NombreArticulo").value;
		let noma = document.getElementById("NombreAutor").value;
		let text = document.getElementById("Texto").value;
		let date = document.getElementById("FechaLibro").value;
		let temas = document.getElementById("Temas").value;
		let lugar = document.getElementById("LugarPublicacion").value;
		let volumen = document.getElementById("VolumenPublicacion").value;
		let tip = document.getElementById("Type").value;
		
		fetch('GestionArticuloModificar?IdArticulo='+id+'&NombreArticulo='+noml+'&NombreAutor='+noma+'&Texto='+text+'&FechaLibro='+date+'&Temas='+temas+'&LugarPublicacion='+lugar+'&VolumenPublicacion='+volumen+'&Type='+tip+'&IdOrigen='+idOrigen)
			.then(response => response.json())
			.then(data => {
				if (data==true){
					
					let dir="http://localhost:8080/Symposium_web/ListarLibro.html";
					redir(dir);
	
				} else {
					console.log("Se produjo un error al modificar");
				}});
	}
	
	//Modifica el artículo a excepción de la Id
	function modificarSinId(){
		document.getElementById("IdArticulo").setAttribute("value",idOrigen);
		let id = document.getElementById("IdArticulo").value;
		let noml = document.getElementById("NombreArticulo").value;
		let noma = document.getElementById("NombreAutor").value;
		let text = document.getElementById("Texto").value;
		let date = document.getElementById("FechaLibro").value;
		let temas = document.getElementById("Temas").value;
		let lugar = document.getElementById("LugarPublicacion").value;
		let volumen = document.getElementById("VolumenPublicacion").value;
		let tip = document.getElementById("Type").value;
		
		fetch('GestionArticuloModificar?IdArticulo='+id+'&NombreArticulo='+noml+'&NombreAutor='+noma+'&Texto='+text+'&FechaLibro='+date+'&Temas='+temas+'&LugarPublicacion='+lugar+'&VolumenPublicacion='+volumen+'&Type='+tip+'&IdOrigen='+idOrigen)
			.then(response => response.json())
			.then(data => {
				if (data==true){
					
					let dir="http://localhost:8080/Symposium_web/ListarLibro.html";
					redir(dir);
	
				} else {
					console.log("Se produjo un error al modificar");
				}});
	}
	
