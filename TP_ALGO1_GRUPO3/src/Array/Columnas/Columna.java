package Array.Columnas;

import Celda.*;
import Array.ArrayCelda;

public class Columna extends ArrayCelda{
    private String nombre;
    private Class<? extends Celda<?>> tipoDeDato;

    public Columna(){
        super();
    }

    public Columna(String nombre) {
        this.nombre = nombre;
    }

    public void asignarNombre(String nombre){
        this.nombre = nombre;
    }

    public String obtenerNombre(){
        return nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
    
}
