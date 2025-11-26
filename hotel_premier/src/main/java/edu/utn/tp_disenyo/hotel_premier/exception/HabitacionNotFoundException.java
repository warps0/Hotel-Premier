package edu.utn.tp_disenyo.hotel_premier.exception;

public class HabitacionNotFoundException extends Exception {
    public HabitacionNotFoundException() {
        super("La habitación especificada no existe.");
    }
}
