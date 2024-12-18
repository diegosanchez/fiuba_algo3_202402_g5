package edu.fiuba.algo3;

import java.util.ArrayList;
import java.util.List;

public class CartaPoker implements Comparable<CartaPoker>{

    private static final int contadorId = 1;

    private final Valor valor;
    private final Palo palo;
    private Puntaje puntaje;
    private final Puntaje puntajeComodin;
    private Puntaje puntajeTarot;

    public CartaPoker(Valor valor, Palo palo){
        this.valor = valor;
        this.palo = palo;
        this.puntaje = new Puntaje( this.valor.valor(), 1);
        this.puntajeTarot = new Puntaje(0,1);
        this.puntajeComodin = new Puntaje(0,1);
    }

    public CartaPoker comprar() {
        return this;
    }

    public void modificarPuntaje(Puntaje unPuntaje) {
        this.puntaje = this.puntaje.sumarPuntaje(unPuntaje);
    }

    public int calcularPuntaje() {
        modificarPuntaje(this.puntajeTarot);
        return this.puntaje.calcularPuntaje();
    }

    public int sumarValorCon(int otroValor) { return this.valor.valor() + otroValor; }

    public boolean esMismoValor(CartaPoker otraCarta) { return this.valor == otraCarta.valor; }

    public boolean esMismoPalo(CartaPoker otraCarta) { return this.palo.equals(otraCarta.palo); }

    public boolean esConsecutiva(CartaPoker otraCarta) { return Math.abs(this.valor.valor() - otraCarta.valor.valor()) == 1; }

    public boolean compararCartaCon(CartaPoker otraCarta) {
        return (this.valor == otraCarta.valor && this.palo.equals(otraCarta.palo));
    }

    public void activarTarot(Tarot tarot) {
        this.puntajeTarot = tarot.modificarPuntaje(puntajeTarot);
    }

    @Override
    public int compareTo(CartaPoker otraCarta) { return Integer.compare(this.valor.valor(), otraCarta.valor.valor()); }

    public ConjuntoCartas obtenerEscalera() {
        ConjuntoCartas escalera = new ConjuntoCartas();
        escalera.agregarCarta(this);

        Valor[] valores = Valor.values();
        int indexActual = this.valor.ordinal(); // Posición actual del valor en el enum

        for (int i = 1; i <= 4; i++) {
            int nuevoIndex = indexActual + i;
            if (nuevoIndex >= valores.length) {
                break; // Si supera el rango de valores, se frena
            }

            Valor nuevoValor = valores[nuevoIndex];
            CartaPoker nuevaCarta = new CartaPoker(nuevoValor, this.palo);
            escalera.agregarCarta(nuevaCarta);
        }

        return escalera;
    }

    public void aplicarComodin(Puntaje unPuntaje) {
        this.puntajeComodin.sumarPuntaje(unPuntaje);
    }

}

