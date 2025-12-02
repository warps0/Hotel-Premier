// ==========================================
// CONFIGURACIÓN DEL HOTEL
// ==========================================

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

// ==========================================
// UTILIDADES DE FECHAS
// ==========================================

const UtilidadesFechas = {
    /**
     * Formatea una fecha al formato dd/mm/aaaa
     */
    formatearFecha(fecha) {
        return fecha.toLocaleDateString('es-ES', { 
            day: '2-digit', 
            month: '2-digit', 
            year: 'numeric' 
        });
    },

    /**
     * Calcula los días entre dos fechas (incluyendo ambas)
     */
    calcularDiasEntre(fechaInicio, fechaFin) {
        // Agregar T00:00:00 para evitar problemas de zona horaria
        const inicio = new Date(fechaInicio + 'T00:00:00');
        const fin = new Date(fechaFin + 'T00:00:00');
        const diferenciaMilisegundos = fin - inicio;
        const dias = Math.ceil(diferenciaMilisegundos / (1000 * 60 * 60 * 24)) + 1;
        
        return Math.max(1, Math.min(dias, 6)); // Entre 1 y 6 días máximo
    },

    /**
     * Genera un array de fechas entre dos fechas
     */
    generarRangoFechas(fechaInicio, cantidadDias) {
        const fechas = [];
        // Agregar T00:00:00 para evitar problemas de zona horaria
        const fechaActual = new Date(fechaInicio + 'T00:00:00');
        
        for (let i = 0; i < cantidadDias; i++) {
            fechas.push(new Date(fechaActual));
            fechaActual.setDate(fechaActual.getDate() + 1);
        }
        
        return fechas;
    },

    /**
     * Verifica si una fecha está dentro de un rango
     */
    estaEnRango(fecha, fechaInicio, fechaFin) {
        const f = new Date(fecha + 'T00:00:00');
        const inicio = new Date(fechaInicio + 'T00:00:00');
        const fin = new Date(fechaFin + 'T00:00:00');
        
        return f >= inicio && f <= fin;
    }
};

// ==========================================
// SERVICIO DE API
// ==========================================

const ServicioHabitaciones = {
    /**
     * Obtiene el estado de las habitaciones del backend
     */
    async obtenerEstadoHabitaciones(fechaInicio, fechaFin) {
        try {
            // Convertir las fechas al formato requerido: YYYY-MM-DDTHH:mm:ss
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
            return this.procesarRespuestaBackend(habitaciones, fechaInicio, fechaFin);
            
        } catch (error) {
            console.error('Error al obtener habitaciones:', error);
            throw error;
        }
    },

    /**
     * Procesa la respuesta del backend y la convierte en un formato útil
     * Estructura: { numeroHabitacion: { fecha: estado } }
     */
    procesarRespuestaBackend(habitaciones, fechaInicioRango, fechaFinRango) {
        const estadoPorHabitacionYFecha = {};
        
        habitaciones.forEach(habitacion => {
            const numeroHabitacion = habitacion.id;
            estadoPorHabitacionYFecha[numeroHabitacion] = {};
            
            // Obtener todas las fechas del rango solicitado
            const fechas = this.generarTodasLasFechasDelRango(fechaInicioRango, fechaFinRango);
            
            // Para cada fecha, buscar qué estado tiene la habitación
            fechas.forEach(fecha => {
                const estado = this.obtenerEstadoEnFecha(habitacion.historialEstado, fecha);
                estadoPorHabitacionYFecha[numeroHabitacion][fecha] = estado;
            });
        });
        
        return estadoPorHabitacionYFecha;
    },

    /**
     * Genera todas las fechas dentro del rango
     */
    generarTodasLasFechasDelRango(fechaInicio, fechaFin) {
        const fechas = [];
        // Agregar T00:00:00 para evitar problemas de zona horaria
        const fechaActual = new Date(fechaInicio + 'T00:00:00');
        const fechaFinal = new Date(fechaFin + 'T00:00:00');
        
        while (fechaActual <= fechaFinal) {
            fechas.push(fechaActual.toISOString().split('T')[0]);
            fechaActual.setDate(fechaActual.getDate() + 1);
        }
        
        return fechas;
    },

    /**
     * Obtiene el estado de una habitación en una fecha específica
     */
    obtenerEstadoEnFecha(historialEstado, fechaBuscada) {
        // Agregar T00:00:00 para evitar problemas de zona horaria
        const fecha = new Date(fechaBuscada + 'T00:00:00');
        
        // Buscar en el historial qué estado corresponde a esta fecha
        for (const periodo of historialEstado) {
            const inicio = new Date(periodo.fechaInicio);
            const fin = new Date(periodo.fechaFin);
            
            if (fecha >= inicio && fecha <= fin) {
                return CONFIGURACION_HOTEL.mapeoEstados[periodo.estado] || 'disponible';
            }
        }
        
        // Si no se encuentra, se asume disponible
        return 'disponible';
    }
};

// ==========================================
// GENERADOR DE TABLA
// ==========================================

const GeneradorTabla = {
    /**
     * Genera la tabla completa con encabezados y filas
     */
    generarTabla(fechaInicio, cantidadDias, estadoHabitaciones) {
        this.limpiarTabla();
        
        const fechas = UtilidadesFechas.generarRangoFechas(fechaInicio, cantidadDias);
        
        this.generarEncabezados(fechas);
        this.generarFilasHabitaciones(fechas, estadoHabitaciones);
    },

    /**
     * Limpia el contenido actual de la tabla
     */
    limpiarTabla() {
        const thead = document.querySelector('#roomTable thead tr');
        const tbody = document.getElementById('tableBody');
        
        thead.innerHTML = '<th class="room-type-cell">Tipo de Habitación</th>';
        tbody.innerHTML = '';
    },

    /**
     * Genera los encabezados de la tabla con las fechas
     */
    generarEncabezados(fechas) {
        const thead = document.querySelector('#roomTable thead tr');
        
        fechas.forEach(fecha => {
            const th = document.createElement('th');
            th.textContent = UtilidadesFechas.formatearFecha(fecha);
            thead.appendChild(th);
        });
    },

    /**
     * Genera todas las filas de habitaciones
     */
    generarFilasHabitaciones(fechas, estadoHabitaciones) {
        const tbody = document.getElementById('tableBody');
        
        Object.entries(CONFIGURACION_HOTEL.tiposHabitacion).forEach(([tipo, config]) => {
            const fila = this.crearFilaTipoHabitacion(tipo, config, fechas, estadoHabitaciones);
            tbody.appendChild(fila);
        });
    },

    /**
     * Crea una fila para un tipo de habitación específico
     */
    crearFilaTipoHabitacion(tipoHabitacion, configuracion, fechas, estadoHabitaciones) {
        const fila = document.createElement('tr');
        
        // Celda con el nombre del tipo de habitación
        const celdaTipo = document.createElement('td');
        celdaTipo.className = 'room-type-cell';
        celdaTipo.textContent = configuracion.nombre;
        fila.appendChild(celdaTipo);
        
        // Generar los números de habitación para este tipo
        const numerosHabitacion = this.generarNumerosHabitacion(
            configuracion.pisoInicial, 
            configuracion.cantidad
        );
        
        // Una celda por cada fecha
        fechas.forEach(fecha => {
            const celda = this.crearCeldaFecha(numerosHabitacion, fecha, estadoHabitaciones);
            fila.appendChild(celda);
        });
        
        return fila;
    },

    /**
     * Genera los números de habitación basados en el piso inicial y cantidad
     */
    generarNumerosHabitacion(pisoInicial, cantidad) {
        const numeros = [];
        for (let i = 1; i <= cantidad; i++) {
            numeros.push(pisoInicial + i);
        }
        return numeros;
    },

    /**
     * Crea una celda con todos los números de habitación para una fecha específica
     */
    crearCeldaFecha(numerosHabitacion, fecha, estadoHabitaciones) {
        const celda = document.createElement('td');
        const fechaStr = fecha.toISOString().split('T')[0];
        
        numerosHabitacion.forEach(numeroHabitacion => {
            const estado = estadoHabitaciones[numeroHabitacion]?.[fechaStr] || 'disponible';
            const divHabitacion = this.crearDivHabitacion(numeroHabitacion, estado);
            celda.appendChild(divHabitacion);
        });
        
        return celda;
    },

    /**
     * Crea el div visual de una habitación con su estado
     */
    crearDivHabitacion(numero, estado) {
        const div = document.createElement('div');
        div.className = `room-number room-${estado}`;
        div.textContent = numero;
        div.style.margin = '3px 0';
        
        // Agregar tooltip con información adicional
        div.title = `Habitación ${numero} - ${this.obtenerTextoEstado(estado)}`;
        
        return div;
    },

    /**
     * Convierte el código de estado en texto legible
     */
    obtenerTextoEstado(estado) {
        const textos = {
            'disponible': 'Disponible',
            'ocupada': 'Ocupada',
            'fuera-servicio': 'Fuera de servicio'
        };
        return textos[estado] || 'Desconocido';
    }
};

// ==========================================
// VALIDACIONES
// ==========================================

const Validaciones = {
    /**
     * Valida que las fechas sean correctas
     */
    validarFechas(fechaInicio, fechaFin) {
        if (!fechaInicio || !fechaFin) {
            throw new Error('Por favor, complete ambas fechas');
        }
        
        if (new Date(fechaInicio) > new Date(fechaFin)) {
            throw new Error('La fecha de inicio debe ser anterior a la fecha de fin');
        }
        
        return true;
    }
};

// ==========================================
// CONTROLADOR PRINCIPAL
// ==========================================

const ControladorBusqueda = {
    /**
     * Estado actual de la búsqueda
     */
    estado: {
        cargando: false,
        fechaInicio: null,
        fechaFin: null,
        datosHabitaciones: null
    },

    /**
     * Realiza la búsqueda de habitaciones
     */
    async buscarHabitaciones() {
        try {
            // Obtener fechas de los inputs
            const fechaInicio = document.getElementById('startDate').value;
            const fechaFin = document.getElementById('endDate').value;
            
            // Validar fechas
            Validaciones.validarFechas(fechaInicio, fechaFin);
            
            // Mostrar estado de carga
            this.mostrarCargando(true);
            
            // Obtener datos del backend
            const estadoHabitaciones = await ServicioHabitaciones.obtenerEstadoHabitaciones(
                fechaInicio, 
                fechaFin
            );
            
            // Guardar en el estado
            this.estado.fechaInicio = fechaInicio;
            this.estado.fechaFin = fechaFin;
            this.estado.datosHabitaciones = estadoHabitaciones;
            
            // Calcular días a mostrar
            const cantidadDias = UtilidadesFechas.calcularDiasEntre(fechaInicio, fechaFin);
            
            // Generar la tabla
            GeneradorTabla.generarTabla(fechaInicio, cantidadDias, estadoHabitaciones);
            
            // Mostrar resultados
            this.mostrarResultados();
            
        } catch (error) {
            this.manejarError(error);
        } finally {
            this.mostrarCargando(false);
        }
    },

    /**
     * Muestra u oculta el indicador de carga
     */
    mostrarCargando(cargando) {
        this.estado.cargando = cargando;
        
        const botonBuscar = document.querySelector('.btn-search');
        if (botonBuscar) {
            botonBuscar.disabled = cargando;
            botonBuscar.textContent = cargando ? '⏳ Cargando...' : '🔍 Buscar';
        }
    },

    /**
     * Muestra la sección de resultados
     */
    mostrarResultados() {
        document.getElementById('searchSection').style.display = 'none';
        document.getElementById('resultsSection').classList.add('active');
    },

    /**
     * Vuelve a la pantalla de búsqueda
     */
    volverABusqueda() {
        document.getElementById('resultsSection').classList.remove('active');
        document.getElementById('searchSection').style.display = 'block';
    },

    /**
     * Cancela y limpia la búsqueda
     */
    cancelarBusqueda() {
        document.getElementById('startDate').value = '';
        document.getElementById('endDate').value = '';
        this.estado.datosHabitaciones = null;
    },

    /**
     * Maneja los errores mostrando un mensaje al usuario
     */
    manejarError(error) {
        console.error('Error en la búsqueda:', error);
        alert(error.message || 'Ocurrió un error al buscar las habitaciones');
    }
};

// ==========================================
// FUNCIONES GLOBALES (llamadas desde HTML)
// ==========================================

function searchRooms() {
    ControladorBusqueda.buscarHabitaciones();
}

function backToSearch() {
    ControladorBusqueda.volverABusqueda();
}

function cancelSearch() {
    ControladorBusqueda.cancelarBusqueda();
}

// ==========================================
// INICIALIZACIÓN
// ==========================================

// Establecer fecha mínima como hoy
document.addEventListener('DOMContentLoaded', () => {
    const hoy = new Date().toISOString().split('T')[0];
    const inputFechaInicio = document.getElementById('startDate');
    const inputFechaFin = document.getElementById('endDate');
    
    if (inputFechaInicio) {
        inputFechaInicio.min = hoy;
    }
    
    if (inputFechaFin) {
        inputFechaFin.min = hoy;
    }
});