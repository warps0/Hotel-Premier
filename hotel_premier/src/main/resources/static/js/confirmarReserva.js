// Este archivo contiene funciones para la página de confirmación.
// Evitar declarar `siguiente()` aquí para no sobrescribir la función del buscador.

// Obtener datos de la URL
function obtenerDatosURL() {
    const params = new URLSearchParams(window.location.search);
    return {
        habitacion: params.get('habitacion'),
        tipoHabitacion: params.get('tipo'),
        fechaIngreso: params.get('fechaIngreso'),
        horaIngreso: params.get('horaIngreso'),
        fechaEgreso: params.get('fechaEgreso'),
        horaEgreso: params.get('horaEgreso')
    };
}

// Cargar datos al iniciar la página
window.addEventListener('DOMContentLoaded', () => {
    const datos = obtenerDatosURL();
    
    if (datos.habitacion) {
        document.getElementById('habitacion').textContent = datos.habitacion;
    }
    if (datos.tipoHabitacion) {
        document.getElementById('tipoHabitacion').textContent = decodeURIComponent(datos.tipoHabitacion);
    }
    if (datos.fechaIngreso) {
        document.getElementById('fechaIngreso').textContent = datos.fechaIngreso;
    }
    if (datos.horaIngreso) {
        document.getElementById('horaIngreso').textContent = datos.horaIngreso;
    }
    if (datos.fechaEgreso) {
        document.getElementById('fechaEgreso').textContent = datos.fechaEgreso;
    }
    if (datos.horaEgreso) {
        document.getElementById('horaEgreso').textContent = datos.horaEgreso;
    }
});

function rechazar() {
    // Volver a la pantalla anterior
    window.history.back();
}

function aceptar() {
    // Obtener datos de la URL (posible reserva única)
    const datos = obtenerDatosURL();

    // Obtener reservas (posible multiple) cargadas desde la URL
    const reservasFromURL = reservasPendientes || [];

    // Obtener datos del responsable desde inputs
    const nombre = document.getElementById('nombre') ? document.getElementById('nombre').value.trim() : '';
    const apellido = document.getElementById('apellido') ? document.getElementById('apellido').value.trim() : '';
    const contacto = document.getElementById('contacto') ? document.getElementById('contacto').value.trim() : '';

    if (!nombre || !apellido || !contacto) {
        if (!confirm('No completó los datos del responsable (nombre/apellido/contacto). Desea continuar de todos modos?')) {
            return;
        }
    }

    // Si hay reservas múltiples (vienen en el array), enviar una petición por cada una
    if (reservasFromURL.length > 0) {
        const promises = reservasFromURL.map(reserva => {
            const payload = {
                habitacionesIds: [reserva.habitacionId || reserva.habitacionNumero],
                nombre: nombre || 'N/D',
                apellido: apellido || 'N/D',
                contacto: contacto || 'N/D',
                fechaInicio: reserva.fechaInicio + 'T00:00:00',
                fechaFin: reserva.fechaFin + 'T00:00:00'
            };

            return fetch('/api/reserva', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(payload)
            }).then(resp => {
                if (!resp.ok) throw new Error('Error en creación: ' + resp.status);
                return resp.json();
            });
        });

        Promise.all(promises)
            .then(results => {
                alert(`${results.length} reserva(s) confirmada(s) exitosamente`);
                window.location.href = '/home';
            })
            .catch(err => {
                console.error('Error al confirmar reservas múltiples:', err);
                alert('Ocurrió un error al confirmar las reservas');
            });

        return;
    }

    // Si no hay array de reservas, intentar enviar una sola reserva usando parámetros simples
    if (datos.habitacion) {
        const payload = {
            habitacionesIds: [Number(datos.habitacion)],
            nombre: nombre || 'N/D',
            apellido: apellido || 'N/D',
            contacto: contacto || 'N/D',
            fechaInicio: (datos.fechaIngreso ? datos.fechaIngreso : '') + 'T00:00:00',
            fechaFin: (datos.fechaEgreso ? datos.fechaEgreso : '') + 'T00:00:00'
        };

        fetch('/api/reserva', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        })
        .then(resp => {
            if (!resp.ok) throw new Error('Error en creación: ' + resp.status);
            return resp.json();
        })
        .then(data => {
            alert('Reserva confirmada exitosamente');
            window.location.href = '/home';
        })
        .catch(err => {
            console.error('Error al confirmar reserva:', err);
            alert('Error al confirmar la reserva');
        });
    } else {
        alert('No hay datos de reserva para enviar');
    }
}




let reservasPendientes = [];

        // Obtener datos de la URL
        function obtenerReservasURL() {
            const params = new URLSearchParams(window.location.search);
            const reservasJSON = params.get('reservas');
            
            if (reservasJSON) {
                try {
                    return JSON.parse(decodeURIComponent(reservasJSON));
                } catch (error) {
                    console.error('Error al parsear reservas:', error);
                    return [];
                }
            }
            return [];
        }

        // Formatear fecha de YYYY-MM-DD a DD/MM/YYYY
        function formatearFecha(fecha) {
            const partes = fecha.split('-');
            return `${partes[2]}/${partes[1]}/${partes[0]}`;
        }

        // Renderizar todas las reservas
        function renderizarReservas() {
            const container = document.getElementById('reservasList');
            const totalElement = document.getElementById('totalReservas');
            
            if (reservasPendientes.length === 0) {
                container.innerHTML = '<div class="no-reservas">No hay reservas pendientes</div>';
                totalElement.textContent = 'Total de habitaciones: 0';
                return;
            }

            totalElement.textContent = `Total de habitaciones: ${reservasPendientes.length}`;
            
            container.innerHTML = '';
            
            reservasPendientes.forEach((reserva, index) => {
                const card = crearTarjetaReserva(reserva, index);
                container.appendChild(card);
            });
        }

        // Crear tarjeta de reserva individual
        function crearTarjetaReserva(reserva, index) {
            const card = document.createElement('div');
            card.className = 'reserva-card';
            
            card.innerHTML = `
                <div class="reserva-header">
                    <div class="habitacion-numero">Habitación ${reserva.habitacionNumero}</div>
                    <button class="btn-eliminar" onclick="eliminarReserva(${index})">✖ Eliminar</button>
                </div>
                
                <div class="reserva-details">
                    <div class="label">Tipo:</div>
                    <div class="value">${reserva.tipoHabitacion}</div>
                    
                    <div class="label">Ingreso:</div>
                    <div class="value">${formatearFecha(reserva.fechaInicio)}</div>
                    
                    <div class="label">Egreso:</div>
                    <div class="value">${formatearFecha(reserva.fechaFin)}</div>
                </div>
            `;
            
            return card;
        }

        // Eliminar una reserva específica
        function eliminarReserva(index) {
            if (confirm('¿Está seguro de eliminar esta reserva?')) {
                reservasPendientes.splice(index, 1);
                renderizarReservas();
            }
        }

        // Cargar datos al iniciar la página (reservas múltiples)
        window.addEventListener('DOMContentLoaded', () => {
            reservasPendientes = obtenerReservasURL();
            console.log('Reservas cargadas:', reservasPendientes);
            renderizarReservas();
        });
    