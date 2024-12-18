package edu.fiuba.algo3.jugadas;

import edu.fiuba.algo3.CartaPoker;
import edu.fiuba.algo3.Jugada;
import edu.fiuba.algo3.Puntaje;
import java.util.List;

public class Descarte extends Jugada {
    protected Puntaje puntajeComodin;

    public Descarte(List<CartaPoker> cartas) {
        super(cartas, new Puntaje(0, 1));
        this.puntajeComodin = new Puntaje(0,1);
    }

    @Override
    public boolean esJugada(List<CartaPoker> cartas) {
        return false;
    }

    @Override
    protected List<CartaPoker> seleccionarCartasValidas(List<CartaPoker> cartas) {
        return cartas;
    }
}
