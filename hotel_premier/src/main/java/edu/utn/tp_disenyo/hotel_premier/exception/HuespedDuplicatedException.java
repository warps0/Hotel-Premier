package edu.utn.tp_disenyo.hotel_premier.exception;

public class HuespedDuplicatedException extends RuntimeException {
    public HuespedDuplicatedException() {

        super("El huesped con el dni ingresado ya se encuentra en la base de datos.");
    }
}
