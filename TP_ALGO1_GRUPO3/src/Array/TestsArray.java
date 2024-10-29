package Array;

import java.util.Arrays;
import java.util.List;

import Celda.*;

public class TestsArray { 
    public static void main(String[] args) {
        ArrayCelda lista = new ArrayCelda();

        /* // Creo lista de 3 elementos: entero, NA, Boolean
        lista.agregarCelda(new CeldaNumber(1));
        lista.agregarCelda(new CeldaNA());
        lista.agregarCelda(new CeldaBoolean(true));

        // Los imprimo uno a uno
        for (int indice = 0; indice < lista.obtenerTamaño(); indice++) {
            System.out.println(lista.obtenerCelda(indice));
            
        }

        // borro el ultimo y imprimo devuelta
        lista.eliminarCelda(2);
        System.out.println("___________________________________________________");
        for (int indice = 0; indice < lista.obtenerTamaño(); indice++) {
            System.out.println(lista.obtenerCelda(indice));
        }

        // Asigno etiquetas a las celdas
        List<String> etiquetas = Arrays.asList("entero", "NA", "AntesBoolean");
        lista.asignarEtiquetas(etiquetas);

        // Imprimo celdas usando etiquetas
        System.out.println("___________________________________________________");
        System.out.println(lista.obtenerCelda("entero"));
        System.out.println(lista.obtenerCelda("NA"));
        System.out.println(lista.obtenerCelda("AntesBoolean"));


        // Pruebo imputar NA
        lista.imputarNA(new CeldaNumber(2.323123123));
        System.out.println("___________________________________________________");
        System.out.println(lista.obtenerCelda("entero"));
        System.out.println(lista.obtenerCelda("NA"));
        System.out.println(lista.obtenerCelda("AntesBoolean"));
        System.out.println(lista.obtenerCelda("enteros"));
        /* */
        
        // Crear dos instancias vacías y probar igualdad
        ArrayCelda lista1 = new ArrayCelda();
        ArrayCelda lista2 = new ArrayCelda();
        
        System.out.println("Test 1 (Listas vacías): " + lista1.equals(lista2)); // Esperado: true

        // Agregar una celda NA y probar igualdad
        lista1.agregarCelda(new CeldaNA());
        lista2.agregarCelda(new CeldaNA());
        System.out.println("Test 2 (Una celda NA): " + lista1.equals(lista2)); // Esperado: true

        // Agregar más celdas y probar igualdad
        lista1.agregarCelda(new CeldaNumber(42));
        lista2.agregarCelda(new CeldaNumber(42));
        System.out.println("Test 3 (Celda NA y CeldaNumber con el mismo valor): " + lista1.equals(lista2)); // Esperado: true

        // Probar listas con celdas diferentes
        lista1.agregarCelda(new CeldaBoolean(true));
        lista2.agregarCelda(new CeldaBoolean(false));
        System.out.println("Test 4 (Listas con una diferencia): " + lista1.equals(lista2)); // Esperado: false

        // Probar igualdad después de eliminar una celda
        lista1.eliminarCelda(2); // Eliminando la última celda en lista1
        lista2.eliminarCelda(2); // Eliminando la última celda en lista2
        System.out.println("Test 5 (Listas tras eliminación de última celda): " + lista1.equals(lista2)); // Esperado: true

        // Crear un nuevo objeto para comparar con la lista existente
        ArrayCelda lista3 = new ArrayCelda();
        lista3.agregarCelda(new CeldaNA());
        System.out.println("Test 6 (Comparando con una lista con solo una celda NA): " + lista1.equals(lista3)); // Esperado: false
    }
}

