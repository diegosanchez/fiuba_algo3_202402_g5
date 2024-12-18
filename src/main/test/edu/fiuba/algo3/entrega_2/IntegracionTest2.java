package edu.fiuba.algo3.entrega_2;

import edu.fiuba.algo3.*;
import edu.fiuba.algo3.comodines.*;
import edu.fiuba.algo3.jugadas.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class IntegracionTest2 {
    @Test
    public void test01SeVerificaQueSeApliqueComodinAEscaleraCorrectamente () {
        //Arrange
        Mazo mazoMock = mock(Mazo.class);
        when(mazoMock.tieneCartas()).thenReturn(true);
        
        Ronda ronda = new Ronda(1,10000,3,3);
        CartaPoker carta1 = new CartaPoker(Valor.DOS, Palo.PICAS);
        CartaPoker carta2 = new CartaPoker(Valor.TRES, Palo.DIAMANTES);
        CartaPoker carta3 = new CartaPoker(Valor.CUATRO, Palo.DIAMANTES);
        CartaPoker carta4 = new CartaPoker(Valor.CINCO, Palo.CORAZONES);
        CartaPoker carta5 = new CartaPoker(Valor.SEIS, Palo.PICAS);

        ArrayList<CartaPoker> cartas = new ArrayList<>(List.of(carta1, carta2, carta3, carta4, carta5));
        Mano mano = new Mano(cartas);
        Puntaje puntaje = new Puntaje(0, 3);
        EfectoJugada unComodin = new EfectoJugada(Escalera.class, puntaje, "Suben los puntos" ,"x3 de multiplicador si se juega escalera");
        Jugador jugador = new Jugador("Pepe", mazoMock) {
            @Override
            public void iniciarRonda(Ronda rondaActual){
                this.rondaActual = ronda;
                this.mazo = mazoMock;
                this.manoActual = mano;
            }
        };
        int puntajeEsperado1 = (2+3+4+5+6+30)*12;
        jugador.aniadirComodin(unComodin);
        jugador.iniciarRonda(ronda);
        jugador.seleccionarCarta(carta1);
        jugador.seleccionarCarta(carta2);
        jugador.seleccionarCarta(carta3);
        jugador.seleccionarCarta(carta4);
        jugador.seleccionarCarta(carta5);        
        //Act
        jugador.jugar();
        int puntajeObtenido = ronda.calcularTotalRonda();
        //Assert
        assertEquals(puntajeEsperado1, puntajeObtenido);
    }

    @Test
    public void test02SeVerificaQueSeAplicaComodinPuntajeCorrectamente() {
        //Arrange
        Mazo mazoMock = mock(Mazo.class);
        when(mazoMock.tieneCartas()).thenReturn(true);
        Ronda ronda = new Ronda(1,10000,3,3);
        CartaPoker carta1 = new CartaPoker(Valor.DOS, Palo.PICAS);
        CartaPoker carta2 = new CartaPoker(Valor.DOS, Palo.DIAMANTES);
        CartaPoker carta3 = new CartaPoker(Valor.DOS, Palo.DIAMANTES);
        CartaPoker carta4 = new CartaPoker(Valor.DOS, Palo.CORAZONES);
        CartaPoker carta5 = new CartaPoker(Valor.SEIS, Palo.PICAS);
        ArrayList<CartaPoker> cartas = new ArrayList<>(List.of(carta1, carta2, carta3, carta4, carta5));
        Mano mano = new Mano(cartas);
        Puntaje puntaje = new Puntaje(0, 8);
        Comodin unComodin = new EfectoPuntaje( puntaje, "Lluvia de puntos" ,"x8 de multiplicador");
        Jugador jugador = new Jugador("Pepe", mazoMock) {
            @Override
            public void iniciarRonda(Ronda rondaActual){
                this.rondaActual = ronda;
                this.mazo = mazoMock;
                this.manoActual = mano;
            }
        };
        jugador.iniciarRonda(ronda);
        int puntajeEsperado = (2*4+60)*7*8;

        jugador.seleccionarCarta(carta1);
        jugador.seleccionarCarta(carta2);
        jugador.seleccionarCarta(carta3);
        jugador.seleccionarCarta(carta4);
        jugador.seleccionarCarta(carta5);
        jugador.aniadirComodin(unComodin);
        //Act
        jugador.jugar();
        int puntajeObtenido = ronda.calcularTotalRonda();
        //Assert
        Assertions.assertEquals(puntajeEsperado, puntajeObtenido);
    }

    @Test
    public void test03SeVerificaQueSeAplicaComodinDescarteCorrectamente () {
        //Arrange
        Mazo mazoMock = mock(Mazo.class);
        when(mazoMock.tieneCartas()).thenReturn(true);
        Ronda ronda = new Ronda(1,10000,3,3);
        CartaPoker carta1 = new CartaPoker(Valor.DOS, Palo.PICAS);
        CartaPoker carta2 = new CartaPoker(Valor.DOS, Palo.DIAMANTES);
        CartaPoker carta3 = new CartaPoker(Valor.DOS, Palo.DIAMANTES);
        CartaPoker carta4 = new CartaPoker(Valor.DOS, Palo.CORAZONES);
        CartaPoker carta5 = new CartaPoker(Valor.SEIS, Palo.PICAS);
        ArrayList<CartaPoker> cartas = new ArrayList<>(List.of(carta1, carta2, carta3, carta4, carta5));
        Mano mano = new Mano(cartas);
        Puntaje puntaje = new Puntaje(10, 1);
        Comodin unComodin = new EfectoDescarte(Descarte.class, puntaje, "Descartar suma" ,"+10 si realiza un descarte");
        Jugador jugador = new Jugador("Pepe", mazoMock) {
            @Override
            public void iniciarRonda(Ronda rondaActual){
                this.rondaActual = ronda;
                this.mazo = mazoMock;
                this.manoActual = mano;
            }
        };
        jugador.aniadirComodin(unComodin);
        jugador.iniciarRonda(ronda);
        int puntajeEsperado = 10;

        jugador.seleccionarCarta(carta1);
        jugador.seleccionarCarta(carta2);
        jugador.seleccionarCarta(carta3);
        jugador.seleccionarCarta(carta4);
        jugador.seleccionarCarta(carta5);
        //Act
        jugador.descartar();
        int puntajeObtenido = ronda.calcularTotalRonda();
        //Assert
        assertEquals(puntajeEsperado, puntajeObtenido);
    }

    @Test
    public void test04SeVerificaQueSeAplicaComodinAleatorioCorrectamente () {
        //Arrange
        Mazo mazoMock = mock(Mazo.class);
        when(mazoMock.tieneCartas()).thenReturn(true);
        Ronda ronda = new Ronda(1,10000,3,3);
        CartaPoker carta1 = new CartaPoker(Valor.DOS, Palo.PICAS);
        CartaPoker carta2 = new CartaPoker(Valor.DOS, Palo.DIAMANTES);
        CartaPoker carta3 = new CartaPoker(Valor.DOS, Palo.DIAMANTES);
        CartaPoker carta4 = new CartaPoker(Valor.DOS, Palo.CORAZONES);
        CartaPoker carta5 = new CartaPoker(Valor.SEIS, Palo.PICAS);
        ArrayList<CartaPoker> cartas = new ArrayList<>(List.of(carta1, carta2, carta3, carta4, carta5));
        Mano mano = new Mano(cartas);
        Jugador jugador = new Jugador("Pepe", mazoMock) {
            @Override
            public void iniciarRonda(Ronda rondaActual){
                this.rondaActual = ronda;
                this.mazo = mazoMock;
                this.manoActual = mano;
            }
        };
        int puntajeEsperado = (2*4+60+10)*7;
        Puntaje puntaje = new Puntaje(10, 1);
        Comodin unComodin = new EfectoAleatorio(1000, puntaje, "RayoMcqueen" ,"1 en 1000 de chances") {
            @Override
            public boolean seAplica() {
                return true;
            }
        };
        jugador.iniciarRonda(ronda);
        jugador.aniadirComodin(unComodin);
        jugador.seleccionarCarta(carta1);
        jugador.seleccionarCarta(carta2);
        jugador.seleccionarCarta(carta3);
        jugador.seleccionarCarta(carta4);
        jugador.seleccionarCarta(carta5);

        //Act
        jugador.jugar();
        int puntajeObtenido = ronda.calcularTotalRonda();
        //Assert
        assertEquals(puntajeEsperado, puntajeObtenido);
    }

    @Test
    public void test05SeVerificaQueSeAplicaComodinCombinadoCorrectamente() {
        //Arrange
        Mazo mazoMock = mock(Mazo.class);
        when(mazoMock.tieneCartas()).thenReturn(true);
        Ronda ronda = new Ronda(1,10000,3,3);
        CartaPoker carta1 = new CartaPoker(Valor.DOS, Palo.PICAS);
        CartaPoker carta2 = new CartaPoker(Valor.DOS, Palo.DIAMANTES);
        CartaPoker carta3 = new CartaPoker(Valor.DOS, Palo.DIAMANTES);
        CartaPoker carta4 = new CartaPoker(Valor.DOS, Palo.CORAZONES);
        CartaPoker carta5 = new CartaPoker(Valor.SEIS, Palo.PICAS);
        ArrayList<CartaPoker> cartas = new ArrayList<>(List.of(carta1, carta2, carta3, carta4, carta5));
        Mano mano = new Mano(cartas);
        Puntaje puntaje1 = new Puntaje(10, 1);
        Comodin comodin1 = new EfectoAleatorio(1000, puntaje1, "El dibu" ,"1 de cada 1000") {
            @Override
            public boolean seAplica() {
                return true;
            }
        };
        Puntaje puntaje2 = new Puntaje(0, 3);
        Comodin comodin2 = new EfectoPuntaje( puntaje2, "Triplete" ,"x3 a la jugada");
        Comodin comodin3 = new EfectoJugada(Poker.class, puntaje2, "Pokerface" ,"x3 multiplica si juego Poker");
        Comodin comodinCombinado = new EfectoCombinado(List.of(comodin1, comodin2, comodin3), "Combina3", "Tiene probabilidad 1 de cada 1000," +
                "x3 al multiplicador y x3 si se juegaPoker");
        Jugador jugador = new Jugador("Pepe", mazoMock) {
            @Override
            public void iniciarRonda(Ronda rondaActual){
                this.rondaActual = ronda;
                this.mazo = mazoMock;
                this.manoActual = mano;
            }
        };
        int puntajeEsperado = (2*4+60+10)*7*3*3;

        jugador.iniciarRonda(ronda);
        jugador.aniadirComodin(comodinCombinado);
        jugador.seleccionarCarta(carta1);
        jugador.seleccionarCarta(carta2);
        jugador.seleccionarCarta(carta3);
        jugador.seleccionarCarta(carta4);
        jugador.seleccionarCarta(carta5);

        //Act
        jugador.jugar();
        int puntajeObtenido = ronda.calcularTotalRonda();
        //Assert
        assertEquals(puntajeEsperado, puntajeObtenido);

    }
}
