const CONFIGURACION_HOTEL = {
    tiposHabitacion: {
        'INDIVIDUAL_ESTANDAR': { nombre: 'Individual Estándar' },
        'DOBLE_ESTANDAR': { nombre: 'Doble Estándar' },
        'DOBLE_SUPERIOR': { nombre: 'Doble Superior' },
        'SUPERIOR_FAMILY_PLAN': { nombre: 'Superior Family Plan' },
        'SUITE_DOBLE': { nombre: 'Suite Doble' }
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

// Nueva función: Obtener habitaciones por tipo desde el backend
async function obtenerHabitacionesPorTipo(tipo) {
    try {
        const url = `/api/habitacion?tipo=${tipo}`;
        console.log('Obteniendo habitaciones del tipo:', tipo);
        
        const respuesta = await fetch(url);
        
        if (!respuesta.ok) {
            const errorText = await respuesta.text();
            console.error('Error del servidor:', errorText);
            throw new Error(`Error HTTP: ${respuesta.status} - ${errorText}`);
        }
        
        const habitaciones = await respuesta.json();
        return habitaciones;
        
    } catch (error) {
        console.error(`Error al obtener habitaciones del tipo ${tipo}:`, error);
        throw error;
    }
}

// Nueva función: Obtener todas las habitaciones de todos los tipos
async function obtenerTodasLasHabitaciones() {
    try {
        const tipos = Object.keys(CONFIGURACION_HOTEL.tiposHabitacion);
        
        // Hacer todas las peticiones en paralelo
        const promesas = tipos.map(tipo => obtenerHabitacionesPorTipo(tipo));
        const resultados = await Promise.all(promesas);
        
        // Organizar las habitaciones por tipo
        const habitacionesPorTipo = {};
        tipos.forEach((tipo, index) => {
            habitacionesPorTipo[tipo] = resultados[index];
        });
        
        return habitacionesPorTipo;
        
    } catch (error) {
        console.error('Error al obtener todas las habitaciones:', error);
        throw error;
    }
}

// Procesar habitaciones para crear el mapa de estados por fecha
function procesarHabitaciones(habitacionesPorTipo, fechaInicio, fechaFin) {
    const estadoPorHabitacionYFecha = {};
    const fechas = generarTodasLasFechasDelRango(fechaInicio, fechaFin);
    
    // Procesar cada tipo de habitación
    Object.values(habitacionesPorTipo).forEach(habitaciones => {
        habitaciones.forEach(habitacion => {
            const numeroHabitacion = habitacion.numeroHabitacion;
            estadoPorHabitacionYFecha[numeroHabitacion] = {};
            
            // Para cada fecha del rango, determinar el estado
            fechas.forEach(fecha => {
                const estado = obtenerEstadoEnFecha(habitacion.historialEstado, fecha);
                estadoPorHabitacionYFecha[numeroHabitacion][fecha] = estado;
            });
        });
    });
    
    return estadoPorHabitacionYFecha;
}

function generarTabla(fechaInicio, cantidadDias, habitacionesPorTipo, estadoHabitaciones) {
    limpiarTabla();
    
    const fechas = generarRangoFechas(fechaInicio, cantidadDias);
    
    generarEncabezados(fechas);
    generarFilasHabitaciones(fechas, habitacionesPorTipo, estadoHabitaciones);
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

function generarFilasHabitaciones(fechas, habitacionesPorTipo, estadoHabitaciones) {
    const tbody = document.getElementById('tableBody');
    
    Object.entries(CONFIGURACION_HOTEL.tiposHabitacion).forEach(([tipo, config]) => {
        const habitaciones = habitacionesPorTipo[tipo] || [];
        
        if (habitaciones.length > 0) {
            const fila = crearFilaTipoHabitacion(tipo, config, fechas, habitaciones, estadoHabitaciones);
            tbody.appendChild(fila);
        }
    });
}

function crearFilaTipoHabitacion(tipoHabitacion, configuracion, fechas, habitaciones, estadoHabitaciones) {
    const fila = document.createElement('tr');
    
    // Celda con el nombre del tipo de habitación
    const celdaTipo = document.createElement('td');
    celdaTipo.className = 'room-type-cell';
    celdaTipo.textContent = configuracion.nombre;
    fila.appendChild(celdaTipo);
    
    // Ordenar habitaciones por número
    const habitacionesOrdenadas = habitaciones.sort((a, b) => a.numeroHabitacion - b.numeroHabitacion);
    
    // Una celda por cada fecha
    fechas.forEach(fecha => {
        const celda = crearCeldaFecha(habitacionesOrdenadas, fecha, estadoHabitaciones);
        fila.appendChild(celda);
    });
    
    return fila;
}

function crearCeldaFecha(habitaciones, fecha, estadoHabitaciones) {
    const celda = document.createElement('td');
    const fechaStr = fecha.toISOString().split('T')[0];
    
    habitaciones.forEach(habitacion => {
        const numeroHabitacion = habitacion.numeroHabitacion;
        const id = habitacion.id;
        const estado = estadoHabitaciones[numeroHabitacion]?.[fechaStr] || 'disponible';
        const divHabitacion = crearDivHabitacion(numeroHabitacion, estado, id);
        celda.appendChild(divHabitacion);
    });
    
    return celda;
}

function crearDivHabitacion(numero, estado, id) {
    const div = document.createElement('div');
    div.className = `room-number room-${estado}`;
    div.textContent = numero;
    div.style.margin = '3px 0';
    
    return div;
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
    habitacionesPorTipo: null,
    datosHabitaciones: null
};

async function buscarHabitaciones() {
    try {
        const fechaInicio = document.getElementById('startDate').value;
        const fechaFin = document.getElementById('endDate').value;
        
        validarFechas(fechaInicio, fechaFin);
        
        // mostrarCargando(true);
        
        // Obtener todas las habitaciones del backend
        const habitacionesPorTipo = await obtenerTodasLasHabitaciones();
        
        // Procesar los estados por fecha
        const estadoHabitaciones = procesarHabitaciones(
            habitacionesPorTipo,
            fechaInicio, 
            fechaFin
        );
        
        estadoApp.fechaInicio = fechaInicio;
        estadoApp.fechaFin = fechaFin;
        estadoApp.habitacionesPorTipo = habitacionesPorTipo;
        estadoApp.datosHabitaciones = estadoHabitaciones;
        
        const cantidadDias = calcularDiasEntre(fechaInicio, fechaFin);
 
        generarTabla(fechaInicio, cantidadDias, habitacionesPorTipo, estadoHabitaciones);
        
        mostrarResultados();
        
    } catch (error) {
        manejarError(error);
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

function backToSearch() {
    document.getElementById('resultsSection').classList.remove('active');
    document.getElementById('searchSection').style.display = 'block';
}

function cancelSearch() {
    document.getElementById('startDate').value = '';
    document.getElementById('endDate').value = '';
}