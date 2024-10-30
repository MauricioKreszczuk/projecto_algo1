// Archivo Identificadores.java
package Array;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Identificadores {
    private HashMap<Integer, String> etiquetas;

    public Identificadores() {
        this.etiquetas = new HashMap<>();
    }

    // Asigna etiquetas a cada índice de celda
    public void asignarEtiquetas(List<String> listaEtiquetas, int cantidadCeldas) {
        if (listaEtiquetas.size() != cantidadCeldas) {
            throw new IllegalArgumentException("La cantidad de etiquetas debe coincidir con el número de celdas.");
        }
        etiquetas.clear();
        for (int i = 0; i < listaEtiquetas.size(); i++) {
            etiquetas.put(i, listaEtiquetas.get(i));
        }
    }

    // Asigna una etiqueta por defecto
    public void asignarEtiquetaPorDefecto(int indice) {
        etiquetas.put(indice, String.valueOf(indice));
    }

    // Obtiene el índice correspondiente a una etiqueta
    public Integer indiceDeEtiqueta(String etiqueta) {
        for (HashMap.Entry<Integer, String> entry : etiquetas.entrySet()) {
            if (entry.getValue().equals(etiqueta)) {
                return entry.getKey();
            }
        }
        throw new IndexOutOfBoundsException("Etiqueta desconocida: " + etiqueta);
    }

    // Verifica si un índice es válido
    public Boolean indiceValido(int indice) { 
        if (indice >= 0 && indice < etiquetas.size()) { 
            return true;
        }
        throw new IndexOutOfBoundsException("Índice fuera de rango: " + indice);
    }
    

        // Método para obtener una lista de las etiquetas
    public List<String> obtenerEtiquetas() {
        return new ArrayList<>(etiquetas.values());
    }

    // Asigna o actualiza una etiqueta para un índice específico
    public void asignarEtiqueta(int indice, String etiqueta) {
        if (indiceValido(indice)) {
            etiquetas.put(indice, etiqueta);
        } else {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + indice);
        }
    }

    public void asignarEtiqueta(String etiquetaActual, String nuevaEtiqueta ){
        int indice = indiceDeEtiqueta(etiquetaActual);
        asignarEtiqueta(indice, nuevaEtiqueta);
    }

    // Método para obtener una etiqueta por su índice
    public String obtenerEtiqueta(int indice) {
        if (indiceValido(indice)) {
            return etiquetas.get(indice);
        }
        throw new IndexOutOfBoundsException("Índice fuera de rango: " + indice);
    }
}
