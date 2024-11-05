package Array;

import Celda.*;
import java.util.ArrayList;
import java.util.List;



public class ArrayCelda implements util.ImputarFaltantes {
    protected List<Celda<?>> celdas;
    protected Identificadores identificadores;
    protected String nombre;

    public ArrayCelda(String nombre) {
        this.celdas = new ArrayList<>();
        this.identificadores = new Identificadores();
        this.nombre = nombre;
    }

    public ArrayCelda(List<Celda<?>> celdas){
        this.celdas = celdas;
    }

    @SuppressWarnings("unchecked") // Ya se limita lo que se obtiene por lo que se mete
    public <T> T obtenerValor(int indice) {
        identificadores.indiceValido(indice);
        Celda<?> celda = celdas.get(indice);
        if (celda instanceof CeldaNA) {
            return null;
        }
        return (T) celda.obtenerValor();
    }

    
    public void cambiarPorNA(int indice) {
        if (identificadores.indiceValido(indice)) {
            celdas.set(indice, new CeldaNA());
        }
    }
    
    public void cambiarPorNA(String etiqueta) {
        int indice = identificadores.indiceDeEtiqueta(etiqueta);
        cambiarPorNA(indice);
    }
    
    public Integer obtenerTamaño() {
        return celdas.size();
    }

    public void asignarNombre(String nombre) {
        this.nombre = nombre;
    }

    public String obtenerNombre() {
        return nombre;
    }
    
    public void asignarEtiquetas(String... listaEtiquetas) {
        identificadores.asignarEtiquetas(List.of(listaEtiquetas), celdas.size());
    }
    public void asignarEtiquetas(List<String> listaEtiquetas) {
        identificadores.asignarEtiquetas(listaEtiquetas, celdas.size());
    }
    
    public <T> T obtenerValor(String etiqueta) {
        int indice = identificadores.indiceDeEtiqueta(etiqueta);
        return obtenerValor(indice);
    }

    
    public void agregarCelda(Celda<?> celda){
        celdas.add(celda);
        identificadores.asignarEtiquetaPorDefecto(celdas.size() - 1);
    }
    
    // Método para agregar un valor de tipo Number y asignar etiqueta por defecto
    public void agregarValor(Number valor) {
        celdas.add(new CeldaNumber(valor)); // Agrega una nueva celda de tipo número
        identificadores.asignarEtiquetaPorDefecto(celdas.size() - 1); // Asigna etiqueta por defecto
    }

    // Método para agregar un valor de tipo Boolean y asignar etiqueta por defecto
    public void agregarValor(Boolean valor) {
        celdas.add(new CeldaBoolean(valor)); // Agrega una nueva celda de tipo booleano
        identificadores.asignarEtiquetaPorDefecto(celdas.size() - 1); // Asigna etiqueta por defecto
    }

    // Método para agregar un valor de tipo String y asignar etiqueta por defecto
    public void agregarValor(String valor) {
        
        celdas.add(new CeldaString(valor)); // Agrega una nueva celda de tipo cadena
        identificadores.asignarEtiquetaPorDefecto(celdas.size() - 1); // Asigna etiqueta por defecto
    }

    // Método para agregar una celda de tipo NA (no disponible) y asignar etiqueta por defecto
    public void agregarValor() {
        celdas.add(new CeldaNA()); // Agrega una nueva celda de tipo NA
        identificadores.asignarEtiquetaPorDefecto(celdas.size() - 1); // Asigna etiqueta por defecto
    }

    public ArrayCelda copiaProfunda() {
        ArrayCelda nuevaArray = new ArrayCelda(this.nombre);
        for (Celda<?> celda : celdas) {
            nuevaArray.celdas.add(celda.copiaProfunda());
        }
        nuevaArray.asignarEtiquetas(identificadores.obtenerEtiquetas()); // Suponiendo que Identificadores tiene un método copiaProfunda
        return nuevaArray;
    }

    public List<Celda<?>> obtenerCeldas(){
        return this.celdas;
    }

    public void imputarNA(Number nuevoValor) {
        for (int i = 0; i < celdas.size(); i++) {
            if (celdas.get(i) instanceof CeldaNA) {
                celdas.set(i, new CeldaNumber(nuevoValor));
            }
        }
    }

    public void imputarNA(Boolean nuevoValor) {
        for (int i = 0; i < celdas.size(); i++) {
            if (celdas.get(i) instanceof CeldaNA) {
                celdas.set(i, new CeldaBoolean(nuevoValor));
            }
        }
    }

    public void imputarNA(String nuevoValor) {
        for (int i = 0; i < celdas.size(); i++) {
            if (celdas.get(i) instanceof CeldaNA) {
                celdas.set(i, new CeldaString(nuevoValor));
            }
        }
    }

    public List<String> obtenerEtiquetas(){
        return identificadores.obtenerEtiquetas();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof ArrayCelda)) {
            return false;
        }
        ArrayCelda otroArrayCelda = (ArrayCelda) obj;
        return this.celdas.equals(otroArrayCelda.celdas);
    }

    @Override
    public int hashCode() {
        return 31 * celdas.hashCode() + identificadores.hashCode();
    }
}
