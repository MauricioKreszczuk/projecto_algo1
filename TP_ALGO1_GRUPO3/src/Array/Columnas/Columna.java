package Array.Columnas;

import Celda.*;
import ExcepcionTabla.ExcepcionTipoDeDatoInvalido;

import java.util.List;

import Array.ArrayCelda;

public class Columna<T> extends ArrayCelda {
    private Class<?> tipoDeDato;

    public Columna(ArrayCelda array){
        super(array.obtenerNombre());
        for (Celda<?> celda : array.obtenerCeldas()){
            this.agregarCelda(celda);
        }
    }

    public Columna(String nombre, Class<T> tipoDeDato) {
        super(nombre);
        this.tipoDeDato = tipoDeDato;
    }

    public Columna(String nombre) {
        super(nombre);
    }
    
    public Columna(String nombre, List<Celda<?>> Celdas) {
        super(nombre);
        this.celdas = Celdas;
    }

    @Override
    public T obtenerValor(int indice){
        T valor = super.obtenerValor(indice);
        return valor;
    }

    public void agregarValor() {
        super.agregarValor();
    }

    public void cambiarCelda(int indice, Celda celda){
        this.celdas.set(indice, celda);
    }

    protected void validarTipo(T valor) {
        if (!tipoDeDato.isInstance(valor)) {
            throw new ExcepcionTipoDeDatoInvalido("El valor no es del tipo esperado: " + tipoDeDato.getSimpleName());
        }
    }

    @Override
    public String toString() {
        return nombre;
    }
}
