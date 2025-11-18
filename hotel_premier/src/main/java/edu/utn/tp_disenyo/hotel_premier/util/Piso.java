package edu.utn.tp_disenyo.hotel_premier.util;

public enum Piso {
    PB(0),
    UNO(1),
    DOS(2),
    TRES(3);

    private final int valor;
    
    Piso(int i) {
        this.valor = i;
    }

    public int getValor() {return this.valor;}
}
