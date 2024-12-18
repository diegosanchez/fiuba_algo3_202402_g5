package edu.fiuba.algo3;

import java.util.ArrayList;
import java.util.List;
import edu.fiuba.algo3.jugadas.*;

public abstract class Jugada {
    private final Puntaje sumaValores;
    private Puntaje puntaje;
    protected List<CartaPoker> cartas;
    protected List<CartaPoker> cartasValidas;
    private Puntaje puntajeComodin;

    public Jugada(List<CartaPoker> cartas, Puntaje puntaje) {
        this.cartas = cartas;
        this.sumaValores = new Puntaje (0,1);
        this.puntaje = puntaje;
        this.cartasValidas = new ArrayList<>();
        this.puntajeComodin = new Puntaje(0,1);
    }

    public abstract boolean esJugada(List<CartaPoker> cartas);

    protected abstract List<CartaPoker> seleccionarCartasValidas(List<CartaPoker> cartas);

    public static Jugada crearJugada(List<CartaPoker> cartas) {
        List<Jugada> posiblesJugadas = List.of(
            new EscaleraReal(cartas),
            new EscaleraColor(cartas),
            new Poker(cartas),
            new FullHouse(cartas),
            new Color(cartas),
            new Escalera(cartas),
            new Trio(cartas),
            new DoblePar(cartas),
            new Par(cartas),
            new CartaAlta(cartas)
        );
        for (Jugada jugada : posiblesJugadas) {
            if (jugada.esJugada(cartas)) {
                return jugada;
            }
        }
        return new CartaAlta(cartas);
    }

    public void sumarValores() {
        int sumaPuntajes = 0;
        for (CartaPoker carta : cartasValidas) {
            sumaPuntajes += carta.calcularPuntaje();
        }
        this.sumaValores.incrementarPuntos(sumaPuntajes);
    }


    public int calcularValor() {
        sumarValores();
        this.puntaje = this.puntaje.sumarPuntaje(this.sumaValores);
        return puntaje.calcularPuntaje();
    }

    public int calcularPuntaje() {
        sumarValores();
        this.puntaje = this.puntaje.sumarPuntaje(this.sumaValores);
        this.puntaje = this.puntaje.sumarPuntaje(this.puntajeComodin);
        return this.puntaje.calcularPuntaje();
    }

    public void aplicarComodin(Puntaje unPuntaje) {
        this.puntajeComodin = this.puntajeComodin.sumarPuntaje(unPuntaje);
    }
}