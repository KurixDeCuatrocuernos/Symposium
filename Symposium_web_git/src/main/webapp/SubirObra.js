/**
 * 
 */
window.addEventListener("DOMContentLoaded", function(){
	
	// Encabezado y navbar
	let iniS= document.getElementById("InicioSesion");
	let home= document.getElementById("logo");
	let dir="";
	
	home.addEventListener("click", function home(){
		dir="http://localhost:8080/Symposium_web/Index.html";
		redir(dir);
	});

	iniS.addEventListener("click", function redir1(){
		dir="http://localhost:8080/Symposium_web/IniciarSesion.html"
		redir(dir);
	})
	
	recogerUsuario();
	avisoAdmin();
	pintarAscensoEs();
	pintarCierre();
	
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
	
	function pintarCierre() {
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

	//Función para validar la información que se introduzca en el formulario
	verificarAdmin();
	
	let tip = document.getElementById("tipo");
	var html="";
	accionLibro();
	
	let submit=document.getElementById("accept");
	submit.addEventListener("click", function accept(){
		validarId();
	})
	
	let cancel = document.getElementById("cancel");
	cancel.addEventListener("click", function cancel(){
		let dir = "http://localhost:8080/Symposium_web/ListarLibro.html"
		redir(dir);
	})
	
	tip.addEventListener("change", function cambiarTipo(){
		let opcionSeleccionada = parseInt(tip.options[tip.selectedIndex].value);
		
		if (opcionSeleccionada == 1){
			accionLibro();
		} else if (opcionSeleccionada ==2){
			accionArticulo();
		} else {
			alert("Selecciona un Tipo de obra");
		}
		
	});
	
	function accionLibro(){
		html = "<label><h1 alt=Categoría de la obra, es subjetiva, pero puede ser algo como Epistemología, Ética y/o Metafísica.>Categoría del Libro: </h1><input type=text id=Categoria name=Categoria></label><label><h1 alt=Empresa que Edita y publica el libro, depende en gran medida del Libro en cuestión, suele tener un icono concreto y a veces aparece en la primera página.>Editorial del Libro: </h1><input type=text id=Edition name=Edition></label>"
		document.getElementById("valoresConcretos").innerHTML=html
		console.log("Insertarás un libro");
	}
	
	function accionArticulo(){
		html="<label><h1 alt=Temas clave del artículo, sulen aparecer listados tras el abstracto o en cursiva en dicho abstracto, en caso de no ser así, no es necesario poner nada>Temas del artículo: </h1><input type=text id=Temas name=Temas></label><label><h1 alt=Se trata de poner el nombre del sitio donde está publicado el artículo, si está en una revista, el nombre de dicha revista o, si está publicado en un libro, el nombre de dicho libro, seguido de las páginas donde se encuentra (por ejemplo: Libro escrito, pag.1-5)>Lugar de publicación: </h1><input type=text id=LugarPublicacion name=LugarPublicacion></label><label><h1 alt=En caso de que el lugar de publicación sea un conjunto de obras o una revista, ha de tener un volumen para identificar dicho lugar, es decir, si el artículo está en el número 5 de la revista x, aquí pondremos un 5 o una v en función de la numeración que tenga>Volumen de publicación: </h1><input type=text id=VolumenPublicacion name=VolumenPublicacion></label>";
		document.getElementById("valoresConcretos").innerHTML=html;
		console.log("Insertarás un articulo");
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
	
	function insertarLibro(){
		let id = document.getElementById('IdLibro');
		let noma = document.getElementById('NombreAutor');
   		let noml = document.getElementById('NombreLibro');
   		let text=document.getElementById('Texto');
   		let fechal=document.getElementById('FechaLibro');
   		let edit = document.getElementById('Edition');
   		let cat=document.getElementById('Categoria');
		fetch('GestionLibroInsertar?IdLibro='+id.value+'&NombreLibro='+noml.value+'&NombreAutor='+noma.value+'&Texto='+text.value+'&FechaLibro='+fechal.value+'&Categoria='+cat.value+'&Edition='+edit.value, {method:'post'})
		.then(response=>response.json())
		.then(cell=> {
			if(cell==false) {
				alert("ERROR AL INSERTAR EL LIBRO");
			} else {
				alert("Libro insertado con éxito");
				window.location.href = "http://localhost:8080/Symposium_web/ListarLibro.html";
			}
		})
	}
	
	function insertarArticulo(){
		let id = document.getElementById('IdLibro');
		let noma = document.getElementById('NombreAutor');
   		let noml = document.getElementById('NombreLibro');
   		let text=document.getElementById('Texto');
   		let fechal=document.getElementById('FechaLibro');
   		let lug = document.getElementById("LugarPublicacion");
		let volumen = document.getElementById("VolumenPublicacion");
		let temas= document.getElementById("Temas");
		fetch('GestionArticuloInsertar?op=2&IdLibro='+id.value+'&NombreArticulo='+noml.value+'&NombreAutor='+noma.value+'&Texto='+text.value+'&FechaLibro='+fechal.value+'&Temas='+temas.value+'&LugarPublicacion='+lug.value+'&VolumenPublicacion='+volumen.value, {method:'post'})
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
	
	function llamadaModificar(id) {
		fetch('GestionVerificarObra?op=5&id='+id.value)
		.then(response => {
			if (response.ok==true){
				fetch('GestionLibroEditar?id='+id.value)
				.then(response => {
					console.log(response.ok)
					if(response.ok){
						console.log("Enviado al servlet y redireccionado a ModificarLibro.html");
						let dir = "http://localhost:8080/Symposium_web/ModificarLibro.html"
						redir(dir);
					} else {
						alert("ERROR AL ENVIAR EL LIBRO AL SERVLET");
					}
				})
			} else {
				fetch('GestionArticuloEditar?id='+id.value)
				.then(response => {
					if(response.ok){
						console.log("Enviado al servlet y redireccionado a ModificarArticulo.html");
						let dir = "http://localhost:8080/Symposium_web/ModificarArticulo.html"
						redir(dir)
					} else {
						alert("ERROR AL ENVIAR EL LIBRO AL SERVLET");
					}
				})
			}
		})

		
	}
	
	function validarId(){
		let id = document.getElementById('IdLibro');
		fetch('GestionVerificarObra?id='+id.value)
		.then(response => response.json())
		.then(response => {
			if (response==true){
				alert("La ISBN: "+id.value+" ya está en la base de datos y no puede introducirse de nuevo.")
				let cell2=confirm("¿Quieres Modificarla?");
				if (cell2==true){
					llamadaModificar(id);
				}
			} else {
				validarFormulario();
			}
		});
	}
	
	
	function validarFormulario(){
		let mensaje="";
		let id = document.getElementById('IdLibro');
		let noma = document.getElementById('NombreAutor');
   		let noml = document.getElementById('NombreLibro');
   		let text=document.getElementById('Texto');
   		let fechal=document.getElementById('FechaLibro');
   		let ok = true;
   		let opcionSeleccionada = parseInt(tip.options[tip.selectedIndex].value);
   		
   		if(id.value == ""|| id.value<10000000 || id.value>9999999999999){
			ok = false;
			document.getElementById('IdLibro').style.color = "red";
			mensaje+="La ISBN/ISSN debe tener como mínimo 8 caracteres y como máximo 13 \n";
		} else {
			document.getElementById('IdLibro').style.color = "green";
		}
				
		if(noma.value == ""){
			ok = false;
			document.getElementById('NombreAutor').style.color = "red";
			mensaje+="Introduce el nombre del autor.\n";
		} else {
			document.getElementById('NombreAutor').style.color = "green";
		}
		
		if(noml.value == ""){
			ok = false;
			document.getElementById('NombreLibro').style.color = "red";
			mensaje+="Introduce el Titulo de la obra. \n";
		} else {
			document.getElementById('NombreLibro').style.color = "green";
		}
			
		if(text.value == ""){
			ok = false;
			document.getElementById('Texto').style.color = "red";
			mensaje+="Introduce el Abstacto de la Obra, de no tenerlo, poroporciona una breve descripción\n"
		}else {
			document.getElementById('Texto').style.color = "green";
		}
			
		if(fechal.value == ""){
			ok = false;
			document.getElementById('FechaLibro').style.color = "red";
			mensaje+="La Obra ha de tener una fecha de publicación \n";
		} else {
			document.getElementById('FechaLibro').style.color = "green";
		}
		
   		if(opcionSeleccionada===1){
			let edit = document.getElementById('Edition');
   			let cat=document.getElementById('Categoria');
			
			if(edit.value == ""){
				ok = false;
				document.getElementById('Edition').style.color = "red";
				mensaje+="Introduce la editorial del libro \n";
			}else {
				document.getElementById('Edition').style.color = "green";
			}
			
			if(cat.value == ""){
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
		} else if (opcionSeleccionada===2) {
			let lug = document.getElementById("LugarPublicacion");
			
			if (lug.value==="") {
				ok=false;
				document.getElementById('LugarPublicacion').style.color = "red";
				mensaje+="El libro ha de estar publicado en algún sitio \n";
				
			}
			if (ok == true){
				insertarArticulo();
			} else {
				alert(mensaje);
			}
		} 
		   
	}
	
	function redir(dir){
		window.location.href=dir;
	}
});