import Celda.*;

import java.util.HashMap;
import java.util.List;


public class ArrayCelda implements ImputarFaltantes {
    private List<? extends Celda<?>> celdas;
    private HashMap<Integer, String> etiqueta;

    public ArrayCelda(){
        // Agregar args e implementación
    }

    public Celda<?> obtenerCelda(Integer etiqueta){
        // Implementación
    }

    public Celda<?> obtenerCelda(String etiqueta){
        // Implementación
    }
    
    public void agregarCelda(Celda celda){
        // Implementación
    }

    public void eliminarCelda(){
        // Revisar argumentos y agregar implementación
    }

    public void asignarEtiqueta(HashMap<String, Integer> etiqueta){
        // Implementación
    }

    public ArrayCelda obtenerValores(){
        // Implementación
    }

    protected void castearArray(<String, Integer> etiqueta, <String, Integer, Boolean> nuevoTipo){
        // Hay que resolver con cuidado este método

    }

    public void ImputarNA(){
        // Implementación
    }

    @Override
    public void imputarNA() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'imputarNA'");
    }

}
