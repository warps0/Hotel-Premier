package edu.utn.tp_disenyo.hotel_premier.exception;

public class HuespedNotFoundException extends Exception {
    public HuespedNotFoundException() {
        super("El huésped no existe en el sistema.");
    }
}
