package edu.utn.tp_disenyo.hotel_premier.util;

public enum Piso {
    PB(0), // Individual estándar
    UNO(1), // Doble estándar
    DOS(2), // Doble superior
    TRES(3), // Superior Family Plan
    CUATRO(4); // Suite

    private final int valor;

    Piso(int i) {
        this.valor = i;
    }

    public int getValor() {return this.valor;}
}
