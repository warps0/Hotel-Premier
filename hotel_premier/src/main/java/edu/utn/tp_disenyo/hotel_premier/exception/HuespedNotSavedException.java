package edu.utn.tp_disenyo.hotel_premier.exception;

public class HuespedNotSavedException extends Exception {
    public HuespedNotSavedException() {
        super("Error al guardar huésped: ");
        printStackTrace();
    }
}
