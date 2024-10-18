package Columnas;

import Celda.CeldaBoolean;
import Celda.CeldaNA;
import Celda.CeldaNumber;
import Celda.CeldaString;

public class TestsColumna {

    public static void main(String[] args) {
        
        Columna columna = new Columna();
        // Creo lista de 3 elementos: entero, NA, Boolean
        columna.agregarCelda(new CeldaNumber(1));
        columna.agregarCelda(new CeldaNA());
        columna.agregarCelda(new CeldaBoolean(true));

        // Los imprimo uno a uno
        for (int indice = 0; indice < columna.obtenerTamaño(); indice++) {
            System.out.println(columna.obtenerCelda(indice));
            
        }

        System.out.println("---------------------------------------------------");
        columna.imputarNA(new CeldaString("relleno"));
        for (int indice = 0; indice < columna.obtenerTamaño(); indice++) {
            System.out.println(columna.obtenerCelda(indice));
            
        }
    }
    
}
