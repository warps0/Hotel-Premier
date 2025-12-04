// Agregar esta función en tu archivo reservarHabitacion.js o reservas.js

function siguiente() {
    // Verificar que haya una habitación seleccionada
    if (!seleccionReserva.habitacionNumero) {
        alert('Por favor, seleccione una habitación antes de continuar');
        return;
    }

    // Verificar que haya fechas seleccionadas
    const fechaInicio = document.getElementById('startDate').value;
    const fechaFin = document.getElementById('endDate').value;
    
    if (!fechaInicio || !fechaFin) {
        alert('Por favor, seleccione las fechas de la reserva');
        return;
    }

    // Formatear las fechas para la URL
    const fechaIng = formatearFecha(fechaInicio);
    const fechaEgr = formatearFecha(fechaFin);
    
    // Construir URL con parámetros
    const params = new URLSearchParams({
        habitacion: seleccionReserva.habitacionNumero,
        tipo: seleccionReserva.tipoHabitacion,
        fechaIngreso: fechaIng,
        fechaEgreso: fechaEgr,
    });
    
    // Redirigir a la página de confirmación
    window.location.href = `/confirmar/reserva?${params.toString()}`;
}


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
    const datos = obtenerDatosURL();
    
    // Aquí envías los datos al backend
    fetch('/api/reservas', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            habitacionId: datos.habitacion,
            fechaIngreso: datos.fechaIngreso,
            horaIngreso: datos.horaIngreso,
            fechaEgreso: datos.fechaEgreso,
            horaEgreso: datos.horaEgreso
        })
    })
    .then(response => response.json())
    .then(data => {
        alert('Reserva confirmada exitosamente');
        window.location.href = '/home'; // Volver a la pantalla principal
    })
    .catch(error => {
        console.error('Error:', error);
        alert('Error al confirmar la reserva');
    });
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

        // Cargar datos al iniciar la página
        window.addEventListener('DOMContentLoaded', () => {
            reservasPendientes = obtenerReservasURL();
            console.log('Reservas cargadas:', reservasPendientes);
            renderizarReservas();
        });

        function rechazar() {
            if (confirm('¿Desea cancelar todas las reservas y volver?')) {
                window.history.back();
            }
        }

        function aceptar() {
            if (reservasPendientes.length === 0) {
                alert('No hay reservas para confirmar');
                return;
            }

            // Enviar todas las reservas al backend
            fetch('/api/reservas/multiple', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    reservas: reservasPendientes
                })
            })
            .then(response => response.json())
            .then(data => {
                alert(`${reservasPendientes.length} reserva(s) confirmada(s) exitosamente`);
                window.location.href = '/home'; // Volver a la pantalla principal
            })
            .catch(error => {
                console.error('Error:', error);
                alert('Error al confirmar las reservas');
            });
        }
    