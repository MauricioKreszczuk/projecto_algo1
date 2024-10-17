package Array;

import java.util.Arrays;
import java.util.List;

import Celda.*;

public class TestsArray { 
    public static void main(String[] args) {
        ArrayCelda lista = new ArrayCelda();

        // Creo lista de 3 elementos: entero, NA, Boolean
        lista.agregarCelda(new CeldaNumber(1));
        lista.agregarCelda(new CeldaNA());
        lista.agregarCelda(new CeldaBoolean(true));

        // Los imprimo uno a uno
        for (int indice = 0; indice < lista.obtenerTamaño(); indice++) {
            System.out.println(lista.obtenerCelda(indice));
            
        }

        // borro el ultimo y imprimo devuelta
        lista.eliminarCelda(2);
        System.out.println("__________________________________________________");
        for (int indice = 0; indice < lista.obtenerTamaño(); indice++) {
            System.out.println(lista.obtenerCelda(indice));
        }

        // Asigno etiquetas a las celdas
        List<String> etiquetas = Arrays.asList("entero", "NA", "AntesBoolean");
        lista.asignarEtiquetas(etiquetas);

        // Imprimo celdas usando etiquetas
        System.out.println("__________________________________________________");
        System.out.println(lista.obtenerCelda("entero"));
        System.out.println(lista.obtenerCelda("NA"));
        System.out.println(lista.obtenerCelda("AntesBoolean"));

    }
}
