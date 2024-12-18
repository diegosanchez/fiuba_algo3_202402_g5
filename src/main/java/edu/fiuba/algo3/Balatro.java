package edu.fiuba.algo3;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class Balatro {

    private final List<Ronda> rondas;
    private final Mazo mazo;
    private final Jugador jugador;

    public Balatro(Jugador jugador) {
        this.rondas = new ArrayList<>();
        this.mazo = new Mazo();
        cargarRondasDesdeJSON("Balatro.json");
        mazo.inicializarMazo("Balatro.json");
        this.mazo.mezclar();
        this.jugador = jugador;
    }

    public void cargarRondasDesdeJSON(String rutaArchivo) {
        ObjectMapper objectMapper = new ObjectMapper();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(rutaArchivo)) {
            if (inputStream == null) {
                throw new IOException("File not found in classpath: " + rutaArchivo);
            }
            JsonNode rootNode = objectMapper.readTree(inputStream);
            JsonNode rondasNode = rootNode.path("rondas");

            for (JsonNode rondaNode : rondasNode) {
                int numero = rondaNode.path("nro").asInt();
                int puntajeMinimo = rondaNode.path("puntajeASuperar").asInt();
                int descartesDisponibles = rondaNode.path("descartes").asInt();
                int jugadasDisponibles = rondaNode.path("manos").asInt();

                Ronda ronda = new Ronda(numero, puntajeMinimo, descartesDisponibles, jugadasDisponibles);
                rondas.add(ronda);
            }
        } catch (IOException e) {
            System.err.println("Error al cargar las rondas desde el archivo JSON: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void iniciarJuego() {
        for (Ronda ronda : this.rondas) {
            if (ronda.verificarPuntaje()) {
                this.jugador.iniciarRonda(ronda);
            }
        }
    }
}
