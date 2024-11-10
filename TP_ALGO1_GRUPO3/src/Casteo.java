import java.util.ArrayList;
import java.util.List;

import Celda.*;
import Array.ArrayCelda;
import Array.Columnas.*;;

public class Casteo {

    public static <T> boolean esColumnaDeTipo(List<Celda<?>> columna, Class<T> tipoCelda) {
        for (Celda<?> celda : columna) {
            // Si la celda no es del tipo especificado ni NA, es un tipo no esperado
            if (!(tipoCelda.isInstance(celda) || celda instanceof CeldaNA)) {
                return false;
            }
        }
        return true;
    }
    
    

    // Método genérico para auto-castear la columna según el tipo de celda
    public static <T> Columna autoCasteoColumna(String nombre, List<Celda<?>> columna, Class<T> tipoCelda, Class<? extends Columna> columnaClase) {
        try {
            // Crear la columna con el nombre adecuado usando reflexión
            Columna columnaInstancia = columnaClase.getConstructor(String.class).newInstance(nombre);
    
            for (Celda<?> celda : columna) {
                if (celda instanceof CeldaNA) {
                    columnaInstancia.agregarCelda(new CeldaNA());  // Agregar celdas NA
                } else if (tipoCelda.isInstance(celda)) {
                    // Agregar la celda directamente a la columna
                    columnaInstancia.agregarCelda(celda);  // Usamos agregarCelda en lugar de agregarValor
                }
            }
    
            return columnaInstancia;
    
        } catch (Exception e) {
            // Aquí puedes manejar la excepción, por ejemplo, imprimirla
            System.out.println("Error en el auto-casteo de la columna: " + e.getMessage());
            e.printStackTrace();
            return null;  // O puedes retornar un valor por defecto o nulo si ocurre un error
        }
    }
    
    


    // Método de procesamiento general para cualquier columna
    public static Columna procesarColumna(ArrayCelda arrayCelda) {
        try {
            List<Celda<?>> celdas = arrayCelda.obtenerCeldas();

            // Si todas las celdas son NA, casteamos a ColumnaNA
            if (esColumnaDeTipo(celdas, CeldaNA.class)) {
                return autoCasteoColumna(arrayCelda.obtenerNombre(), celdas, CeldaNA.class, ColumnaNA.class);
            }

            // Si todas las celdas son booleanas o NA, casteamos a ColumnaBoolean
            else if (esColumnaDeTipo(celdas, CeldaBoolean.class)) {
                return autoCasteoColumna(arrayCelda.obtenerNombre(), celdas, CeldaBoolean.class, ColumnaBoolean.class);
            }

            // Si todas las celdas son numéricas o NA, casteamos a ColumnaNumber
            else if (esColumnaDeTipo(celdas, CeldaNumber.class)) {
                return autoCasteoColumna(arrayCelda.obtenerNombre(), celdas, CeldaNumber.class, ColumnaNumber.class);
            }

            // Si todas las celdas son de tipo String o NA, casteamos a ColumnaString
            else if (esColumnaDeTipo(celdas, CeldaString.class)) {
                return autoCasteoColumna(arrayCelda.obtenerNombre(), celdas, CeldaString.class, ColumnaString.class);
            }

            // Si hay mezcla de tipos, casteamos todo a ColumnaString y CeldaString
            System.out.println("Columna " + arrayCelda.obtenerNombre() + " tiene tipos mixtos o celdas NA. Casteando como ColumnaString.");
            List<Celda<?>> celdasString = new ArrayList<>();
            for (Celda<?> celda : celdas) {
                if (celda instanceof CeldaNA) {
                    celdasString.add(new CeldaString("NA"));  // Convertimos CeldaNA a CeldaString
                } else {
                    celdasString.add(new CeldaString(String.valueOf(celda.toString())));  // Convertimos cualquier valor a String
                }
            }
            return autoCasteoColumna(arrayCelda.obtenerNombre(), celdasString, CeldaString.class, ColumnaString.class);

        } catch (Exception e) {
            System.out.println("Error al procesar la columna: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    
    
    
    
    
}
