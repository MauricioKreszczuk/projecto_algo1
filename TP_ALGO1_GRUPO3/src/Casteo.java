import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import Celda.*;
import ExcepcionTabla.ExcepcionTipoDeDatoInvalido;
import Array.ArrayCelda;
import Array.Columnas.*;;

public class Casteo {

    public static ColumnaNumber aNumber(Columna columna){
        ColumnaNumber nuevaColumna = new ColumnaNumber(columna.obtenerNombre());
        for (Celda<?> celda : columna.obtenerCeldas()){
            try{
                CeldaNumber nuevaCelda = new CeldaNumber(Double.valueOf(String.valueOf(celda.obtenerValor())), true);
                nuevaColumna.agregarCelda(nuevaCelda);
            }
            catch (ExcepcionTipoDeDatoInvalido e){
                nuevaColumna.agregarCelda(new CeldaNA());
            }
        }
        return nuevaColumna;
    }

    public static ColumnaString aString(Columna columna){
        ColumnaString nuevaColumna = new ColumnaString(columna.obtenerNombre());
        for (Celda<?> celda : columna.obtenerCeldas()){
            try {
                // Se intenta convertir el valor a un String (aunque en teoría ya es String)
                String valor = String.valueOf(celda.obtenerValor());
                nuevaColumna.agregarCelda(new CeldaString(valor));
            }
            catch (ExcepcionTipoDeDatoInvalido e) {
                // Si ocurre un error (en la mayoría de los casos no debería), se agrega una celda NA
                nuevaColumna.agregarCelda(new CeldaNA());
            }
        }
        return nuevaColumna;
    }
    
    public static ColumnaBoolean aBoolean(Columna columna){
        ColumnaBoolean nuevaColumna = new ColumnaBoolean(columna.obtenerNombre());
        for (Celda<?> celda : columna.obtenerCeldas()){
            try {
                // Intentamos convertir el valor a un Booleano. Si no es válido, lanzará una excepción
                Boolean valor = Boolean.valueOf(String.valueOf(celda.obtenerValor()));
                nuevaColumna.agregarCelda(new CeldaBoolean(valor));
            }
            catch (ExcepcionTipoDeDatoInvalido e) {
                // Si no es un valor booleano válido, agregamos una celda NA
                nuevaColumna.agregarCelda(new CeldaNA());
            }
        }
        return nuevaColumna;
    }
    
    // Método genérico para auto-castear la columna según el tipo de celda
    public static <T> Columna autoCasteoColumna(String nombre, List<Celda<?>> columna, Class<T> tipoCelda, Class<? extends Columna> columnaClase) {
        try {
            Columna columnaInstancia = columnaClase.getConstructor(String.class).newInstance(nombre);
    
            for (Celda<?> celda : columna) {
                if (celda instanceof CeldaNA) {
                    columnaInstancia.agregarCelda(new CeldaNA()); 
                } else if (tipoCelda.isInstance(celda)) {
                    columnaInstancia.agregarCelda(celda);  
                }
            }
    
            return columnaInstancia;

        } catch (Exception e) {
            System.out.println("Error en el auto-casteo de la columna: " + e.getMessage());
            e.printStackTrace();
            return null;  
        }
    }

    // Método de procesamiento general para cualquier columna
    public static Columna procesarColumna(ArrayCelda arrayCelda) {
        try {
            List<Celda<?>> celdas = arrayCelda.obtenerCeldas();

            if (esColumnaDeTipo(celdas, CeldaNA.class)) {
                return autoCasteoColumna(arrayCelda.obtenerNombre(), celdas, CeldaNA.class, ColumnaNA.class);
            }

            else if (esColumnaDeTipo(celdas, CeldaBoolean.class)) {
                return autoCasteoColumna(arrayCelda.obtenerNombre(), celdas, CeldaBoolean.class, ColumnaBoolean.class);
            }

            else if (esColumnaDeTipo(celdas, CeldaNumber.class)) {
                return autoCasteoColumna(arrayCelda.obtenerNombre(), celdas, CeldaNumber.class, ColumnaNumber.class);
            }

            else if (esColumnaDeTipo(celdas, CeldaString.class)) {
                return autoCasteoColumna(arrayCelda.obtenerNombre(), celdas, CeldaString.class, ColumnaString.class);
            }

            List<Celda<?>> celdasString = new ArrayList<>();
            for (Celda<?> celda : celdas) {
                if (celda instanceof CeldaNA) {
                    celdasString.add(new CeldaString("NA"));  
                } else {
                    celdasString.add(new CeldaString(String.valueOf(celda.toString())));  
                }
            }
            return autoCasteoColumna(arrayCelda.obtenerNombre(), celdasString, CeldaString.class, ColumnaString.class);

        } catch (Exception e) {
            System.out.println("Error al procesar la columna: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }   

    private static <T> boolean esColumnaDeTipo(List<Celda<?>> columna, Class<T> tipoCelda) {
        for (Celda<?> celda : columna) {
            // Si la celda no es del tipo especificado ni NA, es un tipo no esperado
            if (!(tipoCelda.isInstance(celda) || celda instanceof CeldaNA)) {
                return false;
            }
        }
        return true;
    }

    
    
    
    
    
}
