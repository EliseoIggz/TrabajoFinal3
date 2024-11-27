package com.example.trabajofinal3;

import java.util.Calendar;

public class Planta {
    private String nombre;
    private int tiempoRiego;
    private Calendar fechaRegistro; // Fecha en que se registró la planta

    public Planta(String nombre, int tiempoRiego) {
        this.nombre = nombre;
        this.tiempoRiego = tiempoRiego;
        this.fechaRegistro = Calendar.getInstance();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTiempoRiego() {
        return tiempoRiego;
    }

    public void setTiempoRiego(int tiempoRiego) {
        this.tiempoRiego = tiempoRiego;
    }

    public Calendar getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Calendar fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public long getDiasParaRiego() {
        // Crear un objeto Calendar para la fecha de próximo riego
        Calendar fechaProximoRiego = (Calendar) fechaRegistro.clone();
        fechaProximoRiego.add(Calendar.DAY_OF_YEAR, tiempoRiego); // Sumar los días del tiempo de riego
        // Obtener la fecha actual
        Calendar fechaActual = Calendar.getInstance();
        // Calcular la diferencia en milisegundos entre las dos fechas
        long diferenciaMilisegundos = fechaProximoRiego.getTimeInMillis() - fechaActual.getTimeInMillis();
        // Convertir la diferencia de milisegundos a días
        long diferenciaDias = diferenciaMilisegundos / (24 * 60 * 60 * 1000); // Convertir milisegundos a días
        // Comprobar si los dias que faltan para regar son menor a 0 para poner una nueva fecha de riego
        if(diferenciaDias < 0){ //
            setFechaRegistro(Calendar.getInstance());
            diferenciaDias = getDiasParaRiego();
            diferenciaDias -= 1; // Se resta 1 para que no coincida el mensaje del dia de riego "Regar hoy" con el de "Regar en x dias"
                                    // porque el dia que riegas ya cuenta como el primer dia hasta el proximo riego
        }
        return diferenciaDias;  //Valor que se usa para enseñar en el mensaje
    }
}
