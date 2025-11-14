function botonCancelar(mensaje) {
    let cancelar = confirm(mensaje);
    if (cancelar) {
        window.location.href = "http://localhost:8080/home";
    } 
}