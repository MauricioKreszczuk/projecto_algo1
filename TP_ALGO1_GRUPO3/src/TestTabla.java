import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Array.Columnas.Columna;
import Array.Columnas.ColumnaNumber;
import Array.Columnas.ColumnaString;
import Celda.Celda;

public class TestTabla {
    public static void main(String[] args) {
        
        // Agregar columnas a la tabla
        // tabla.agregarColumna(new ColumnaString("Nombre"));
        // tabla.agregarColumna(new ColumnaNumber("Edad"));
        // tabla.agregarColumna(new ColumnaString("Ciudad"));

        // Cargar desde una matriz
        Object[][] matriz = {
            {"Nombre", "Edad", "Ciudad"},
            {"Juan", 30, "Madrid"},
            {"Ana", null, "Barcelona"},
            {null, 25, "Valencia"},
            {"Luis", 28, null}
        };

        Tabla tablaDesdeMatriz = new Tabla(matriz, true);

        // Verificacion del funcionamiento
        // for (Columna<?> columna : tablaDesdeMatriz.obtenerColumnas()){
        //     System.out.println(columna.obtenerNombre() +  String.valueOf(columna.getClass()) );
        //     for (Celda<?> celda : columna.obtenerCeldas()){
        //         System.out.println(celda.obtenerValor() + String.valueOf(celda.getClass()));
        //     }
        // }
        // System.out.println("\n");

        // List<String> listaInt = new ArrayList<>();
        // listaInt.add("Edad");
        // tablaDesdeMatriz.ordenarPorColumnas(listaInt, true);

        // for (Columna<?> columna : tablaDesdeMatriz.obtenerColumnas()){
        //     System.out.println(columna.obtenerNombre() +  String.valueOf(columna.getClass()) );
        //     for (Celda<?> celda : columna.obtenerCeldas()){
        //         System.out.println(celda.obtenerValor() + String.valueOf(celda.getClass()));
        //     }
        // }
        
        // System.out.println(matriz[0]);
        // System.out.println(matriz[0][0]);
        // System.out.println(matriz[3][1].getClass());


        // Cargar desde una secuencia lineal nativa de java
        List<List<Object>> listaDeFilas = new ArrayList<>();
        listaDeFilas.add(Arrays.asList("ID", "Nombre", "Edad", "Ciudad Autonoma de Buenos Aires"));
        listaDeFilas.add(Arrays.asList(1, "Alice", 30, true));
        listaDeFilas.add(Arrays.asList(2, "Bob", 25, true));
        listaDeFilas.add(Arrays.asList(3, "Charlie", 35, false));
        
        Tabla tablaDesdeSecuenciaLineal = new Tabla(listaDeFilas, true);

        // Verificacion del funcionamiento
        // for (Columna<?> columna : tablaDesdeSecuenciaLineal.obtenerColumnas()){
        //     System.out.println(columna.obtenerNombre() +  String.valueOf(columna.getClass()) );
        //     for (Celda<?> celda : columna.obtenerCeldas()){
        //         System.out.println(celda.obtenerValor() + String.valueOf(celda.getClass()));
        //     }
        // }
        // System.out.println("\n");

        List<String> listaInt = new ArrayList<>();
        listaInt.add("Edad");
        tablaDesdeSecuenciaLineal.ordenarPorColumnas(listaInt, true);

        // for (Columna<?> columna : tablaDesdeSecuenciaLineal.obtenerColumnas()){
        //     System.out.println(columna.obtenerNombre() +  String.valueOf(columna.getClass()) );
        //     for (Celda<?> celda : columna.obtenerCeldas()){
        //         System.out.println(celda.obtenerValor() + String.valueOf(celda.getClass()));
        //     }
        // }

        System.out.println("\n" + tablaDesdeSecuenciaLineal);

    }
}
