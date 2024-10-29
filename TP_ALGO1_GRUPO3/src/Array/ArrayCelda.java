package Array;

import Celda.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ArrayCelda implements util.ImputarFaltantes{
    private List<Celda<?>> celdas;
    private HashMap<Integer, String> etiquetas;

    public ArrayCelda() {
        this.celdas = new ArrayList<>(); 
        this.etiquetas = new HashMap<>(); 
    }

    public Celda<?> obtenerCelda(String etiqueta) {
        Integer index = indiceDeEtiqueta(etiqueta); 
        return obtenerCelda(index); 
    }

    // Método genérico para obtener el valor de una celda por índice
    public <T> T obtenerValor(int indice) {
        Celda<?> celda = celdas.get(indice); // Obtiene la celda en el índice

        if (celda instanceof CeldaNA) {
            return null; // Si la celda es NA, retorna null
        }

        return (T) celda.obtenerValor(); // Retorna el valor de la celda
    }

    // Método genérico para obtener el valor de una celda por etiqueta
    public <T> T obtenerValor(String etiqueta) {
        int indice = indiceDeEtiqueta(etiqueta); // Obtiene el índice a partir de la etiqueta
        return obtenerValor(indice); // Retorna el valor de la celda en el índice
    }

    // Cambia el contenido de dos celdas dado sus índices
    public void cambiarCeldas(int indice1, int indice2) {
        indiceValido(indice1); // Verifica que el índice1 sea válido
        indiceValido(indice2); // Verifica que el índice2 sea válido

        // Realiza el intercambio
        Celda<?> temp = celdas.get(indice1); // Almacena la celda de indice1 en temp
        celdas.set(indice1, celdas.get(indice2)); // Cambia la celda de indice1 por la de indice2
        celdas.set(indice2, temp); // Cambia la celda de indice2 por la de temp
    }

    // Cambia el contenido de dos celdas dado sus etiquetas
    public void cambiarCeldas(String etiqueta1, String etiqueta2) {
        int indice1 = indiceDeEtiqueta(etiqueta1); // Obtiene el índice de la etiqueta1
        int indice2 = indiceDeEtiqueta(etiqueta2); // Obtiene el índice de la etiqueta2

        cambiarCeldas(indice1, indice2); // Cambia las celdas usando los índices
    }

    // Cambia el contenido de una celda a NA dado su índice
    public void cambiarPorNA(int indice) {
        if (indiceValido(indice)) { // Verifica que el índice sea válido
            celdas.set(indice, new CeldaNA()); // Cambia la celda por una celda NA
        }
    }

    // Cambia el contenido de una celda a NA dado su etiqueta
    public void cambiarPorNA(String etiqueta) {
        int indice = indiceDeEtiqueta(etiqueta); // Obtiene el índice a partir de la etiqueta
        cambiarPorNA(indice); // Cambia la celda a NA usando el índice
    }

    // Retorna el tamaño de la lista de celdas
    public Integer obtenerTamaño() {
        return celdas.size(); // Retorna el tamaño de la lista de celdas
    }

    // Asigna etiquetas a las celdas basado en un array de etiquetas
    public void asignarEtiquetas(String... listaEtiquetas) {
        if (listaEtiquetas.length != celdas.size()) {
            throw new IllegalArgumentException("La cantidad de etiquetas debe coincidir con el número de celdas."); // Verifica que el número de etiquetas coincida
        }

        etiquetas.clear(); // Limpia las etiquetas existentes

        // Asigna cada etiqueta a su respectivo índice
        for (int indice = 0; indice < celdas.size(); indice++) {
            etiquetas.put(indice, listaEtiquetas[indice]); // Clave: índice, Valor: etiqueta
        }
    }

    // Asigna etiquetas a las celdas basado en una lista de etiquetas
    public void asignarEtiquetas(List<String> listaEtiquetas) {
        asignarEtiquetas(listaEtiquetas.toArray(new String[0])); // Convierte la lista a un array y llama al método anterior
    }

    // Obtiene el índice correspondiente a una etiqueta
    private Integer indiceDeEtiqueta(String etiqueta) {
        for (HashMap.Entry<Integer, String> tempEtiqueta : etiquetas.entrySet()) {
            if (tempEtiqueta.getValue().equals(etiqueta)) {
                return tempEtiqueta.getKey(); // Devuelve el índice si se encuentra la etiqueta
            }
        }
        throw new IndexOutOfBoundsException("Etiqueta de la " + this.getClass().getName() + " desconocida: " + etiqueta); // Lanza excepción si no se encuentra
    }

    // Verifica si un índice es válido
    private Boolean indiceValido(int indice) {
        if (indice >= 0 && indice < celdas.size()) {
            return true; // Retorna verdadero si el índice es válido
        }
        throw new IndexOutOfBoundsException("Índice fuera de rango de la " + this.getClass().getName() + ": " + indice); // Lanza excepción si el índice no es válido
    }

    @Override
    // Imputa una nueva celda en todas las celdas que sean NA
    public <T extends Celda<?>> void imputarNA(T nuevaCelda) {
        for (int indice = 0; indice < celdas.size(); indice++) {
            if (celdas.get(indice) instanceof CeldaNA) {
                celdas.set(indice, nuevaCelda.copiaProfunda()); // Reemplaza la celda NA por la nueva celda
            }
        }
    }

    @Override //Llevar metodo a clase más especifica si no se implementa en todos los hijos
    public boolean equals(Object otroArrayCelda){
        if (otroArrayCelda == null || !(otroArrayCelda instanceof ArrayCelda)) {
            return false;
        }

        ArrayCelda casteado = (ArrayCelda) otroArrayCelda;
        return this.celdas.equals(casteado.celdas);

    }

    // Método para sobreescribir hashCode y generar un código hash para ArrayCelda
    @Override
    public int hashCode() {
        return 31 * celdas.hashCode() + etiquetas.hashCode(); // Genera un código hash basado en celdas y etiquetas
    }

    // Método para agregar un valor de tipo Number y asignar etiqueta por defecto
    public void agregarValor(Number valor) {
        celdas.add(new CeldaNumber(valor)); // Agrega una nueva celda de tipo número
        asignarEtiquetaPorDefecto(celdas.size() - 1); // Asigna etiqueta por defecto
    }

    // Método para agregar un valor de tipo Boolean y asignar etiqueta por defecto
    public void agregarValor(Boolean valor) {
        celdas.add(new CeldaBoolean(valor)); // Agrega una nueva celda de tipo booleano
        asignarEtiquetaPorDefecto(celdas.size() - 1); // Asigna etiqueta por defecto
    }

    // Método para agregar un valor de tipo String y asignar etiqueta por defecto
    public void agregarValor(String valor) {
        celdas.add(new CeldaString(valor)); // Agrega una nueva celda de tipo cadena
        asignarEtiquetaPorDefecto(celdas.size() - 1); // Asigna etiqueta por defecto
    }

    // Método para agregar una celda de tipo NA (no disponible) y asignar etiqueta por defecto
    public void agregarValor() {
        celdas.add(new CeldaNA()); // Agrega una nueva celda de tipo NA
        asignarEtiquetaPorDefecto(celdas.size() - 1); // Asigna etiqueta por defecto
    }

    // Asigna una etiqueta por defecto basada en el índice de la celda
    private void asignarEtiquetaPorDefecto(int indice) {
        etiquetas.put(indice, String.valueOf(indice)); // Asigna la etiqueta por defecto
    }

    // Obtiene la celda correspondiente a un índice dado
    public Celda<?> obtenerCelda(Integer etiqueta) {
        int indice = etiqueta; // Convertir etiqueta a índice
        indiceValido(indice); // Verifica que el índice sea válido
        return celdas.get(indice); // Retorna la celda en el índice
    }

}
