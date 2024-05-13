window.addEventListener("DOMContentLoaded", function(){
	
	llamada();
	let coment = document.getElementById("comentar");
	coment.addEventListener("click", function com(){
		generarComentario();
	})
});

function llamada(){
	
	fetch('GestionObraBuscar', {method:'post'})
	.then(response => response.json())
	.then(datos => pintar(datos))	

}

function pintar(resultado){
	
		let NumL = document.getElementById("NumLibro");
		let Title = document.getElementById("Titl");
		let Name = document.getElementById("Author");
		let Text = document.getElementById("Text");
		let Dat = document.getElementById("Fecha");
		let Ed = document.getElementById("Edicion");
		
		NumL.innerHTML = resultado.ISBN;
		Title.innerHTML= resultado.Titulo;
		Name.innerHTML= resultado.Autor;
		Text.innerHTML= resultado.Abstracto;
		Dat.innerHTML= resultado.Fecha;
		Ed.innerHTML= resultado.Editorial;
		
	if(resultado.Tipo.charAt(0)=='L'|| resultado.Tipo.charAt(0)=='l'){
		//datos específicos de la clase libro
	} else {
		//datos específicos de la clase articulo
	}
}

function generarComentario() {
	let NumObra = resultado.ISBN;
	//en el servlet se revisará si el usuario está logueado y quién es
	fetch('GestionComentarioInsertar?obra='+NumObra)
	.then(response => response.json())
	.then(datos => console.log(datos))
}