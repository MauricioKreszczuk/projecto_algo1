package Array.Columnas;

import Celda.*;
import Array.ArrayCelda;

public abstract class Columna<T> extends ArrayCelda {
    private Class<T> tipoDeDato;

    public Columna(String nombre, Class<T> tipoDeDato) {
        super(nombre);
        this.tipoDeDato = tipoDeDato;
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



    public abstract void agregarValor(T valor);

    public void agregarValor() {
        super.agregarValor();
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
