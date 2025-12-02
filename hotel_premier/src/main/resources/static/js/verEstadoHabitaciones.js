const CONFIGURACION_HOTEL = {
    tiposHabitacion: {
        'INDIVIDUAL_ESTANDAR': { nombre: 'Individual Estándar', cantidad: 10, pisoInicial: 100 },
        'DOBLE_ESTANDAR': { nombre: 'Doble Estándar', cantidad: 18, pisoInicial: 200 },
        'DOBLE_SUPERIOR': { nombre: 'Doble Superior', cantidad: 8, pisoInicial: 300 },
        'SUPERIOR_FAMILY_PLAN': { nombre: 'Superior Family Plan', cantidad: 10, pisoInicial: 400 },
        'SUITE_PLAN': { nombre: 'Suite Plan', cantidad: 2, pisoInicial: 500 }
    },
    
    mapeoEstados: {
        'LIBRE': 'disponible',
        'RESERVADO': 'ocupada',
        'MANTENIMIENTO': 'fuera-servicio',
        'OCUPADO': 'ocupada'
    }
};

function formatearFecha(fecha) {
    return fecha.toLocaleDateString('es-ES', { 
        day: '2-digit', 
        month: '2-digit', 
        year: 'numeric' 
    });
}

function calcularDiasEntre(fechaInicio, fechaFin) {
    const inicio = new Date(fechaInicio + 'T00:00:00');
    const fin = new Date(fechaFin + 'T00:00:00');
    const diferenciaMilisegundos = fin - inicio;
    const dias = Math.ceil(diferenciaMilisegundos / (1000 * 60 * 60 * 24)) + 1;
    
    return Math.max(1, dias); 
}

function generarRangoFechas(fechaInicio, cantidadDias) {
    const fechas = [];
    const fechaActual = new Date(fechaInicio + 'T00:00:00');
    
    for (let i = 0; i < cantidadDias; i++) {
        fechas.push(new Date(fechaActual));
        fechaActual.setDate(fechaActual.getDate() + 1);
    }
    return fechas;
}

function estaEnRango(fecha, fechaInicio, fechaFin) {
    const f = new Date(fecha + 'T00:00:00');
    const inicio = new Date(fechaInicio + 'T00:00:00');
    const fin = new Date(fechaFin + 'T00:00:00');
    
    return f >= inicio && f <= fin;
}

async function obtenerEstadoHabitaciones(fechaInicio, fechaFin) {
    try {
        const fechaInicioISO = `${fechaInicio}T00:00:00`;
        const fechaFinISO = `${fechaFin}T00:00:00`;
        
        const url = `/api/habitacion/buscar?fechaInicio=${fechaInicioISO}&fechaFin=${fechaFinISO}`;
        
        console.log('URL de solicitud:', url);
        
        const respuesta = await fetch(url);
        
        if (!respuesta.ok) {
            const errorText = await respuesta.text();
            console.error('Error del servidor:', errorText);
            throw new Error(`Error HTTP: ${respuesta.status} - ${errorText}`);
        }
        
        const habitaciones = await respuesta.json();
        return procesarRespuestaBackend(habitaciones, fechaInicio, fechaFin);
        
    } catch (error) {
        console.error('Error al obtener habitaciones:', error);
        throw error;
    }
}

function procesarRespuestaBackend(habitaciones, fechaInicioRango, fechaFinRango) {
    const estadoPorHabitacionYFecha = {};
    
    habitaciones.forEach(habitacion => {
        const numeroHabitacion = habitacion.id;
        estadoPorHabitacionYFecha[numeroHabitacion] = {};
        
        const fechas = generarTodasLasFechasDelRango(fechaInicioRango, fechaFinRango);
        
        fechas.forEach(fecha => {
            const estado = obtenerEstadoEnFecha(habitacion.historialEstado, fecha);
            estadoPorHabitacionYFecha[numeroHabitacion][fecha] = estado;
        });
    });
    
    return estadoPorHabitacionYFecha;
}

function generarTodasLasFechasDelRango(fechaInicio, fechaFin) {
    const fechas = [];
    const fechaActual = new Date(fechaInicio + 'T00:00:00');
    const fechaFinal = new Date(fechaFin + 'T00:00:00');
    
    while (fechaActual <= fechaFinal) {
        fechas.push(fechaActual.toISOString().split('T')[0]);
        fechaActual.setDate(fechaActual.getDate() + 1);
    }
    
    return fechas;
}

function obtenerEstadoEnFecha(historialEstado, fechaBuscada) {
    const fecha = new Date(fechaBuscada + 'T00:00:00');
    
    for (const periodo of historialEstado) {
        const inicio = new Date(periodo.fechaInicio);
        const fin = new Date(periodo.fechaFin);
        
        if (fecha >= inicio && fecha <= fin) {
            return CONFIGURACION_HOTEL.mapeoEstados[periodo.estado] || 'disponible';
        }
    }
    
    return 'disponible';
}

function generarTabla(fechaInicio, cantidadDias, estadoHabitaciones) {
    limpiarTabla();
    
    const fechas = generarRangoFechas(fechaInicio, cantidadDias);
    
    generarEncabezados(fechas);
    generarFilasHabitaciones(fechas, estadoHabitaciones);
}

function limpiarTabla() {
    const thead = document.querySelector('#roomTable thead tr');
    const tbody = document.getElementById('tableBody');
    
    thead.innerHTML = '<th class="room-type-cell">Tipo de Habitación</th>';
    tbody.innerHTML = '';
}

function generarEncabezados(fechas) {
    const thead = document.querySelector('#roomTable thead tr');
    
    fechas.forEach(fecha => {
        const th = document.createElement('th');
        th.textContent = formatearFecha(fecha);
        thead.appendChild(th);
    });
}

function generarFilasHabitaciones(fechas, estadoHabitaciones) {
    const tbody = document.getElementById('tableBody');
    
    Object.entries(CONFIGURACION_HOTEL.tiposHabitacion).forEach(([tipo, config]) => {
        const fila = crearFilaTipoHabitacion(tipo, config, fechas, estadoHabitaciones);
        tbody.appendChild(fila);
    });
}

function crearFilaTipoHabitacion(tipoHabitacion, configuracion, fechas, estadoHabitaciones) {
    const fila = document.createElement('tr');
    
    // Celda con el nombre del tipo de habitación
    const celdaTipo = document.createElement('td');
    celdaTipo.className = 'room-type-cell';
    celdaTipo.textContent = configuracion.nombre;
    fila.appendChild(celdaTipo);
    
    // Generar los números de habitación para este tipo
    const numerosHabitacion = generarNumerosHabitacion(
        configuracion.pisoInicial, 
        configuracion.cantidad
    );
    
    // Una celda por cada fecha
    fechas.forEach(fecha => {
        const celda = crearCeldaFecha(numerosHabitacion, fecha, estadoHabitaciones);
        fila.appendChild(celda);
    });
    
    return fila;
}

function generarNumerosHabitacion(pisoInicial, cantidad) {
    const numeros = [];
    for (let i = 1; i <= cantidad; i++) {
        numeros.push(pisoInicial + i);
    }
    return numeros;
}

function crearCeldaFecha(numerosHabitacion, fecha, estadoHabitaciones) {
    const celda = document.createElement('td');
    const fechaStr = fecha.toISOString().split('T')[0];
    
    numerosHabitacion.forEach(numeroHabitacion => {
        const estado = estadoHabitaciones[numeroHabitacion]?.[fechaStr] || 'disponible';
        const divHabitacion = crearDivHabitacion(numeroHabitacion, estado);
        celda.appendChild(divHabitacion);
    });
    
    return celda;
}

function crearDivHabitacion(numero, estado) {
    const div = document.createElement('div');
    div.className = `room-number room-${estado}`;
    div.textContent = numero;
    div.style.margin = '3px 0';
    div.title = `Habitación ${numero} - ${obtenerTextoEstado(estado)}`;
    
    return div;
}

function obtenerTextoEstado(estado) {
    const textos = {
        'disponible': 'Disponible',
        'ocupada': 'Ocupada',
        'fuera-servicio': 'Fuera de servicio'
    };
    return textos[estado] || 'Desconocido';
}

function validarFechas(fechaInicio, fechaFin) {
    if (!fechaInicio || !fechaFin) {
        throw new Error('Por favor, complete ambas fechas');
    }
    
    if (new Date(fechaInicio) > new Date(fechaFin)) {
        throw new Error('La fecha de inicio debe ser anterior a la fecha de fin');
    }
    
    return true;
}

const estadoApp = {
    cargando: false,
    fechaInicio: null,
    fechaFin: null,
    datosHabitaciones: null
};

async function buscarHabitaciones() {
    try {
        const fechaInicio = document.getElementById('startDate').value;
        const fechaFin = document.getElementById('endDate').value;
        
        validarFechas(fechaInicio, fechaFin);
        
        mostrarCargando(true);
        
        const estadoHabitaciones = await obtenerEstadoHabitaciones(
            fechaInicio, 
            fechaFin
        );
        
        estadoApp.fechaInicio = fechaInicio;
        estadoApp.fechaFin = fechaFin;
        estadoApp.datosHabitaciones = estadoHabitaciones;
        
        const cantidadDias = calcularDiasEntre(fechaInicio, fechaFin);
 
        generarTabla(fechaInicio, cantidadDias, estadoHabitaciones);
        
        mostrarResultados();
        
    } catch (error) {
        manejarError(error);
    } finally {
        mostrarCargando(false);
    }
}

function mostrarCargando(cargando) {
    estadoApp.cargando = cargando;
    
    const botonBuscar = document.querySelector('.btn-search');
    if (botonBuscar) {
        botonBuscar.disabled = cargando;
        botonBuscar.textContent = cargando ? '⏳ Cargando...' : '🔍 Buscar';
    }
}

function mostrarResultados() {
    document.getElementById('searchSection').style.display = 'none';
    document.getElementById('resultsSection').classList.add('active');
}

function manejarError(error) {
    console.error('Error en la búsqueda:', error);
    alert(error.message || 'Ocurrió un error al buscar las habitaciones');
}