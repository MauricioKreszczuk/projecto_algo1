package Array.Columnas;

import Celda.*;

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

    public void establecerCeldas(List<Celda<?>> nuevasCeldas) {
        this.celdas = nuevasCeldas;
    }

    @Override
    public T obtenerValor(int indice){
        T valor = super.obtenerValor(indice);
        return valor;
    }
    @Override 
    public T obtenerValor(String etiqueta){
        T valor = super.obtenerValor(etiqueta);
        return valor;
    }

    public void agregarValor() {
        super.agregarValor();
    }


    public void cambiarCelda(int indice, Celda celda){
        this.celdas.set(indice, celda);
    }

    // Cambia el contenido de dos celdas dado sus índices
    public void intercambiarCeldas(int indice1, int indice2) {
        identificadores.indiceValido(indice1);
        identificadores.indiceValido(indice2);

        Celda<?> temp = celdas.get(indice1);
        celdas.set(indice1, celdas.get(indice2));
        celdas.set(indice2, temp);

        String etiqueta1 = identificadores.obtenerEtiqueta(indice1);
        String etiqueta2 = identificadores.obtenerEtiqueta(indice2);

        identificadores.asignarEtiqueta(indice1, etiqueta2);
        identificadores.asignarEtiqueta(indice2, etiqueta1);
    }

    // Cambia el contenido de dos celdas dado sus etiquetas
    public void intercambiarCeldas(String etiqueta1, String etiqueta2) {
        int indice1 = identificadores.indiceDeEtiqueta(etiqueta1);
        int indice2 = identificadores.indiceDeEtiqueta(etiqueta2);

        intercambiarCeldas(indice1, indice2);
    }

    protected void validarTipo(T valor) {
        if (!tipoDeDato.isInstance(valor)) {
            throw new IllegalArgumentException("El valor no es del tipo esperado: " + tipoDeDato.getSimpleName());
        }
    }

    @Override
    public String toString() {
        return nombre;
    }
}
