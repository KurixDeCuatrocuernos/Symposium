
window.addEventListener("DOMContentLoaded", function(){
	
	// Encabezado y navbar
	let home= document.getElementById("logo");
	let dir="";
	
	home.addEventListener("click", function home(){
		dir="http://localhost:8080/Symposium_web/Index.html";
		redir(dir);
	});
	
	recogerUsuario();
	avisoAdmin();
	pintarAscensoEs();
	pintarSesion();
	
		let buscador = document.getElementById("buscar");
	
	buscador.addEventListener("keyup", function(){
		pedirSugerencias(buscador.value);
	})
	
	function peticion(inputUser){
		let solicitud = new XMLHttpRequest();
		
		let url = "./GestionObraBuscar?solic="+inputUser;
		
		solicitud.addEventListener("load", mostrar);
		solicitud.open('GET',url, true); // si es true será asíncrono, si es false, no
		solicitud.send();
		
	}
	
	function mostrar(event){
		let datos = event.target;
		let html="";
		for(let i=0; i<datos.response.length; i++){
			html+="<p>"+datos.response[i]+"</p>";
		}
		if (datos.status == 200){
			let sugerencias = JSON.parse(datos.responseText); 
			let html = "";
            for (let i = 0; i < sugerencias.length; i++) {
                html += "<p class=resultadoB id="+sugerencias[i].ISBN+" name="+sugerencias[i].Tipo+">" + sugerencias[i].Titulo + "</p>";
            }
			document.getElementById("sugerencias").innerHTML=html;
		} else {
			console.log("Fallo: "+datos.status+" en la petición XML");
		}
		let vinculos=document.querySelectorAll("p.resultadoB");
		vinculos.forEach (bot =>{
			bot.addEventListener("click", function showOpper(){
				let dir="./PaginaObra.html?viewkey="+bot.getAttribute("id")+"&viewtype="+bot.getAttribute("name");
				redir(dir);
			});
		})
	}
	
	function pedirSugerencias(str){
		if(str.length==0){
			document.getElementById("sugerencias").innerHTML="";
		} else {
			peticion(str);
		}
	}
	
	function recogerUsuario(){
		fetch('GestionInicioSesion?op=8', {method:'POST'})
		.then(response => response.json())
		.then(tipo => pintarUsuario(tipo))
	}
	
	function pintarUsuario(tipo){
		let user="";
		if(tipo<10){
			user="ANÓNIMO";
		} else if (tipo>9 && tipo<30){
			user="ESTUDIANTE";
		} else if (tipo>29&&tipo<50){
			user="TITULADO";
		}else if (tipo>49){
			user="ADMINISTRADOR";
		}
		let place=document.getElementById("usuarioType");
		place.innerHTML=user;
	}
	
	function imprimirListados(){
		
		let place= document.getElementById("botonesAdmin");
		let html="<button id=ListaUsers>Listado de Usuarios</button><button id=ListarObras>Listado de Obras</button>";
		place.innerHTML=html;
		
		let lisU= document.getElementById("ListaUsers");
		lisU.addEventListener("click", function redir2(){
			dir="http://localhost:8080/Symposium_web/ListarUsuarios.html"
			redir(dir);
		})
		let lisO= document.getElementById("ListarObras");
		lisO.addEventListener("click", function redir3(){
			dir="http://localhost:8080/Symposium_web/ListarLibro.html"
			redir(dir);
		})
	}
	
	function pintarSesion() {
		fetch('GestionInicioSesion?op=9', {method:'POST'})
		.then(response => response.json())
		.then(devuelto => {
			if(devuelto === true){
				html="<button id=CerrarSesion>Cerrar Sesión</button>";
				
				document.getElementById("botonCerrarSesion").innerHTML+=html;
				
				let cerS= document.getElementById("CerrarSesion");
				
				cerS.addEventListener("click", function closeSes(){
					cerrarSesion();
				})
				
				html="";
				
				document.getElementById("botonInicioSesion").innerHTML=html;
				
			} else {
				
				html="<strong id=InicioSesion>Iniciar Sesión</strong>";
				
				document.getElementById("botonInicioSesion").innerHTML=html;
				
				let iniS= document.getElementById("InicioSesion");
				iniS.addEventListener("click", function redir1(){
					dir="http://localhost:8080/Symposium_web/IniciarSesion.html"
					redir(dir);
				});
				
				html="";
				
				document.getElementById("botonCerrarSesion").innerHTML=html;
				
			}
		})
		
	}
	
	function cerrarSesion() {
		let cell = confirm("¿Estás seguro de que quieres cerrar la sesión?");
		console.log(cell);
		if (cell == true){
			fetch('GestionInicioSesion?op=5', {method:'POST'})
			setTimeout(function(){
				dir = "http://localhost:8080/Symposium_web/Index.html"; 
				redir(dir);
			}, 100);//esperamos 100 milisegundos
					
		} else {
			dir="http://localhost:8080/Symposium_web/Index.html"; 
			redir(dir);
		}
	}
	
	function avisoAdmin(){
		fetch('GestionInicioSesion?op=9', {method:'POST'})
		.then(response => response.json())
		.then(sesion => {
			if (sesion==true){
				console.log(sesion)
				fetch('GestionInicioSesion?op=1', {method:'POST'})
				.then(response => response.json())
				.then(admin => {
					if (admin===true){
						imprimirListados();
					}
				})
			}
		})
	}
	
	function pintarAscensoEs(){
		fetch('GestionInicioSesion?op=3', {method:'POST'})
		.then(response => response.json())
		.then(sesion => {
			if (sesion===true){
				let html="<button id=solicitarAscenso>Solicitar ascenso a Titulado</button>";
				document.getElementById("botonEstudiante").innerHTML+=html;
				
				let ascen = document.getElementById("solicitarAscenso");
				ascen.addEventListener("click", function solicitud(){
					let dir="http://localhost:8080/Symposium_web/SolicitudAscenso.html"
					redir(dir);
				})	
			}
		})
	}

	// CONTENIDO
	
	//nada más entrar pintamos el formulario
		var idOrigen=0;
		llamadaModificar();
		
	//evento escuchador para aceptar
	let acc=this.document.querySelector("button.modificar");
	acc.addEventListener("click", function subm(){
		let cell=false;
		let id=document.getElementById('IdLibro').value;
		cell=verificarFormulario();
		if (cell==true){
			//si todo está bien, verifico la id
			fetch('GestionVerificarObra?id='+id)
				.then(response => response.json())
				.then(response => {
					if(response==true){
						document.getElementById('IdLibro').style.color = "red";
						setTimeout(function() {
		       		 		alert("Ese ISBN/ISSN ya está en el listado");
		       		 		let conf=false;
		       		 		conf=confirm("¿Quieres mantener esa ISBN?");
		       		 		if(conf==true){
									modificarSinId();
									alert("modificando libro");
								}
		    			}, 100); // Espera 100 milisegundos
						
					} else {
						modificar();
						alert("modificando libro");
					}
				})	
				.catch(error => {
						console.log(error);
						alert("Error al pintar el formulario")
					});
		} else {
			alert("Introduce todos los datos para poder modificar la obra");
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
		let id=document.getElementById('IdLibro').value;
		if(id == ""|| id<10000000 || id>9999999999999){
			document.getElementById('IdLibro').style.color = "red";
			setTimeout(function() {
       		 		alert("El ISBN/ISSN ha de tener como mínimo 8 caracteres y como máximo 13");
    			}, 100); // Espera 100 milisegundos
		} else {
			fetch('GestionVerificarObra?id='+id)
				.then(response => response.json())
				.then(response => {
					if(response==true){
						document.getElementById('IdLibro').style.color = "red";
						setTimeout(function() {
		       		 		alert("Ese ISBN/ISSN ya está en el listado");
		    			}, 100); // Espera 100 milisegundos
						
					} else {
						document.getElementById('IdLibro').style.color = "green";
					}
				})	
				.catch(error => {
						console.log(error);
						alert("Error al pintar el formulario")
					});
		}
	
	});

})

function redir(dir){
		window.location.href = dir;
	}

	
function modificar(){//Me queda hacer la modificacion efectiva
	console.log(idOrigen);
	let id = document.getElementById("IdLibro").value;
	let noml = document.getElementById("NombreLibro").value;
	let noma = document.getElementById("NombreAutor").value;
	let text = document.getElementById("Texto").value;
	let date = document.getElementById("FechaLibro").value;
	let cat = document.getElementById("Categoria").value;
	let ed = document.getElementById("Edition").value;
	let tip = document.getElementById("Type").value;
	
	fetch('GestionLibroModificar?IdLibro='+id+'&NombreLibro='+noml+'&NombreAutor='+noma+'&Texto='+text+'&FechaLibro='+date+'&Categoria='+cat+'&Edition='+ed+'&Type='+tip+'&IdOrigen='+idOrigen)
		.then(response => response.json())
		.then(data => {
			if (data==true){
				
				let dir="http://localhost:8080/Symposium_web/ListarLibro.html";
				redir(dir);

			} else {
				console.log("Se produjo un error al modificar");
			}});
	
	
}
function modificarSinId(){
	document.getElementById("IdLibro").setAttribute("value",idOrigen);
	let id = document.getElementById("IdLibro").value;
	let noml = document.getElementById("NombreLibro").value;
	let noma = document.getElementById("NombreAutor").value;
	let text = document.getElementById("Texto").value;
	let date = document.getElementById("FechaLibro").value;
	let cat = document.getElementById("Categoria").value;
	let ed = document.getElementById("Edition").value;
	let tip = document.getElementById("Type").value;
	console.log(idOrigen)
	
	fetch('GestionLibroModificar?IdLibro='+id+'&NombreLibro='+noml+'&NombreAutor='+noma+'&Texto='+text+'&FechaLibro='+date+'&Categoria='+cat+'&Edition='+ed+'&Type='+tip+'&IdOrigen='+idOrigen)
		.then(response => response.json())
		.then(data => {
			if (data==true){
				
				let dir="http://localhost:8080/Symposium_web/ListarLibro.html";
				redir(dir);

			} else {
				console.log("Se produjo un error al modificar");
			}});
}
	
function verificarFormulario(){
	let id = document.getElementById("IdLibro").value;
	let noml = document.getElementById("NombreLibro").value;
	let noma = document.getElementById("NombreAutor").value;
	let text = document.getElementById("Texto").value;
	let date = document.getElementById("FechaLibro").value;
	let cat = document.getElementById("Categoria").value;
	let ed = document.getElementById("Edition").value;
	let tip = document.getElementById("Type").value;
	let mensaje="";
	let ok=true;
	
	if(id == ""|| id<10000000 || id>9999999999999){
			ok = false;
			document.getElementById('IdLibro').style.color = "red";
			mensaje+="El ISBN ha de tener 8 caracteres como mínimo y 13 como máximo. \n";
		} else {
			document.getElementById('IdLibro').style.color = "green";
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
			document.getElementById('NombreLibro').style.color = "red";
			mensaje+="Ha de tener un título. \n";
		} else {
			document.getElementById('NombreLibro').style.color = "green";
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
		
		if(ed == ""){
			ok = false;
			document.getElementById('Edition').style.color = "red";
			mensaje+="Ha de tener una editorial que lo publique. \n";
		}else {
			document.getElementById('Edition').style.color = "green";
		}
		//es un checkbox, uno como mínimo
		if(cat == ""){
			ok = false;
			document.getElementById('Categoria').style.color = "red";
			mensaje+="Ha de tener alguna categoría, pon la que se te ocurra. \n";
		}else {
			document.getElementById('Categoria').style.color = "green";
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

function pintarFormulario(resultados){
	let id = document.getElementById("IdLibro");
	let noml = document.getElementById("NombreLibro");
	let noma = document.getElementById("NombreAutor");
	let text = document.getElementById("Texto");
	let date = document.getElementById("FechaLibro");
	let cat = document.getElementById("Categoria");
	let ed = document.getElementById("Edition");
	let tip = document.getElementById("Type");
	
		
	id.value = resultados.ISBN;
    noml.value = resultados.Titulo;
    noma.value = resultados.Autor;
    text.value = resultados.Abstracto;
    date.value = resultados.Fecha_publicacion;// Corrección aquí
    cat.value = resultados.Categoria;
    ed.value = resultados.Editorial;
    tip.value = resultados.Tipo;
	idOrigen=id.value;
}

function llamadaModificar(){
	fetch('GestionLibroEditar', {method: 'post'})
		.then(response => response.json())
		.then(data => pintarFormulario(data))
		.catch(error => {
					console.log(error);
					alert("Error al pintar el formulario")
				});
}

