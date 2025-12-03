package edu.utn.tp_disenyo.hotel_premier.exception;

public class EntityNotSavedException extends Exception {
    public EntityNotSavedException() {
        super("Error al guardar entidad en la base de datos: ");
        printStackTrace();
    }
}
