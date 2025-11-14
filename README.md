# Hotel-Premier 🏨

**Sistema de Gestión Hotelera** - Trabajo Práctico de Diseño de Sistemas 2025  
Facultad Regional Santa Fe - Universidad Tecnológica Nacional

## Descripción

Hotel-Premier es una aplicación web diseñada para gestionar las operaciones de un hotel. El sistema permite administrar huéspedes, habitaciones, reservas, pagos y facturas del hotel.

## Funcionalidades

- **Gestión de Huéspedes**: Registro, búsqueda y administración de datos de huéspedes
- **Gestión de Habitaciones**: Visualización del estado de disponibilidad
- **Reservas**: Sistema completo de reservas de habitaciones
- **Pagos y Facturación**: Registro de transacciones y generación de facturas

## Tecnologías Utilizadas

- **Backend**: Spring Boot 3.5.7
- **Frontend**: Thymeleaf (Motor de plantillas HTML)
- **Base de Datos**: H2 (En memoria)
- **ORM**: Spring Data JPA
- **Build Tool**: Maven
- **Java Version**: 21

## Estructura del Proyecto

```
hotel_premier/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── edu/utn/tp_disenyo/hotel_premier/
│   │   │       ├── controller/        # Controladores REST
│   │   │       ├── service/           # Lógica de negocio
│   │   │       ├── repository/        # Acceso a datos (DAO)
│   │   │       ├── model/             # Modelos de datos
│   │   │       ├── exception/         # Excepciones personalizadas
│   │   │       └── util/              # Utilidades
│   │   └── resources/
│   │       ├── templates/             # Plantillas Thymeleaf
│   │       ├── static/                # CSS, JS, imágenes
│   │       └── application.properties # Configuración
│   └── test/
│       └── java/                      # Tests unitarios
└── pom.xml                            # Dependencias Maven
```

## Inicio Rápido

### Requisitos Previos
- Java 21 o superior
- Maven 3.8.9+
- Git

### Instalación y Ejecución

1. **Clonar el repositorio**
```powershell
git clone https://github.com/warps0/Hotel-Premier.git
cd Hotel-Premier/hotel_premier
```

2. **Compilar el proyecto**
```powershell
mvn clean compile
```

3. **Ejecutar la aplicación**
```powershell
mvn spring-boot:run
```

4. **Acceder a la aplicación**
```
http://localhost:8080/
```

## Endpoints Principales

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/` | Página principal |
| GET | `/huesped/alta` | Formulario de alta de huésped |
| POST | `/huesped` | Guardar nuevo huésped |
| GET | `/huesped/exito` | Confirmación de alta exitosa |

## Modelos de Datos

### Huésped
- Datos personales del huésped (nombre, apellido)
- Tipo y número de documento
- Contacto (teléfono, email)
- Tipo de persona (Física/Jurídica)

### Habitación
- Número y tipo de habitación
- Capacidad y precio
- Estado de disponibilidad

### Reserva
- Asociación huésped-habitación
- Fechas de entrada y salida
- Estado de la reserva

### Pago
- Información de transacción
- Método de pago
- Monto e impuestos

### Factura
- Datos de la transacción
- Detalles de servicios
- Información fiscal

## 📋 Fases del TP

- **Etapa 1**: Mockups y diseño de interfaz
- **Etapa 2**: Diagrama de clases y especificación
- **Etapa 3**: Entidades, DAO y DTO
- **Etapa 4**: Implementación actualizada
- **Etapa 5**: Diagramas de secuencia
- **Etapa 6**: Implementación de 1° caso de uso

## Grupo 20 | Deadline Warriors
### Integrantes:
- **Santiago Gallardo** | s.gallardogaston@gmail.com
- **Karen Kerke** | karenkerke@gmail.com
- **Nicolás Francos** | nicofrancos72@gmail.com
- **Matías Trossero** | matias.trossero.1@gmail.com

---

# Progreso

- [x] Página home
- [ ] CU-02: Buscar huésped
- [ ] CU-04: Reservar habitación
- [ ] CU-05: Mostrar estado de habitaciones
- [ ] CU-07: Facturar
- [x] CU-09: Dar de alta huésped
- [ ] CU-11: Dar de baja huésped
- [ ] CU-15: Ocupar habitación
- [ ] CU-18: Listar ingresos