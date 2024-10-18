package Array;
import Celda.*;
import util.ImputarFaltantes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;


public class ArrayCelda implements util.ImputarFaltantes {

    private List<Celda<?>> celdas;
    private HashMap<Integer, String> etiquetas;
    int tamaño;


    public ArrayCelda(){
        this.celdas = new ArrayList<>();
        this.etiquetas = new HashMap<>();
    }

    public Celda<?> obtenerCelda(Integer etiqueta){
        int indice = etiqueta;
        indiceValido(indice);
        return celdas.get(indice);
    }

    public Celda<?> obtenerCelda(String etiqueta) {
        Integer index = indiceDeEtiqueta(etiqueta);
        return obtenerCelda(index);
    }

    public void eliminarCelda(int indice) {
        if (indiceValido(indice)) {
            celdas.set(indice, new CeldaNA());
        } 
    }
    
    public void eliminarCelda(String etiqueta){
        int indice = indiceDeEtiqueta(etiqueta);
        eliminarCelda(indice);
    }
    
    public <T extends Celda<?>> void agregarCelda(T celda) {
        celdas.add(celda);
        tamaño = celdas.size();
    }

    public Integer obtenerTamaño(){
        return this.tamaño;
    }
    
    
    public void asignarEtiquetas(List<String> listaEtiquetas) {

        if (listaEtiquetas.size() != celdas.size()) {
            throw new IllegalArgumentException("La cantidad de etiquetas debe coincidir con el número de celdas.");
        }
        
        etiquetas.clear();

        for (int indice = 0; indice < tamaño ; indice++) {
            etiquetas.put(indice, listaEtiquetas.get(indice)); // Clave: índice, Valor: etiqueta
        }
    }
    
    private Integer indiceDeEtiqueta(String etiqueta) {
        for (HashMap.Entry<Integer, String> tempEtiqueta : etiquetas.entrySet()) {
            if (tempEtiqueta.getValue().equals(etiqueta)) {
                return tempEtiqueta.getKey(); // Devuelve el índice si se encuentra la etiqueta
            }
        }
        // Si no encuentra ninguna coincidencia, lanza la excepción después de la búsqueda completa
        throw new IndexOutOfBoundsException("Etiqueta de la " + this.getClass().getName() + " desconocida: " + etiqueta);
    }
    

    private Boolean indiceValido(int indice){
        if (indice >= 0 && indice < tamaño){
            return true;
        }
        throw new IndexOutOfBoundsException("Índice fuera de rango de la " + this.getClass().getName() + ": " + indice);
    }


    @Override
    public <T extends Celda<?>> void imputarNA(T nuevaCelda) {
        for (int indice = 0; indice < tamaño; indice++) {
            if (celdas.get(indice) instanceof CeldaNA) {
                celdas.set(indice, nuevaCelda.copiaProfunda()); // Asegúrate de que nuevaCelda sea del tipo adecuado
            }
        }
    }
    

/* PARA HACER //////////////////////////////////////////////////////////////////////////////

    public ArrayCelda obtenerValores(){
        // Implementación
    }


    protected void castearArray(<String, Integer> etiqueta, <String, Integer, Boolean> nuevoTipo){
        // Hay que resolver con cuidado este método, seria de columna?

    }
*/

}
