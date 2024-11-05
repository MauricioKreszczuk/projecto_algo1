import java.util.ArrayList;
import java.util.List;

import Array.Columnas.Columna;
import Array.Columnas.ColumnaNumber;
import Array.Columnas.ColumnaString;
import Celda.Celda;

public class TestTabla {
    public static void main(String[] args) {
        // Crear una instancia de tu clase Tabla
        Tabla tabla = new Tabla();

        
        // Agregar columnas a la tabla
        // tabla.agregarColumna(new ColumnaString("Nombre"));
        // tabla.agregarColumna(new ColumnaNumber("Edad"));
        // tabla.agregarColumna(new ColumnaString("Ciudad"));

        // Crear una matriz de datos



        Object[][] matriz = {
            {"Nombre", "Edad", "Ciudad"},
            {"Juan", 30, "Madrid"},
            {"Ana", null, "Barcelona"},
            {null, 25, "Valencia"},
            {"Luis", 28, null}
        };

        Tabla tablaDesdeMatriz = new Tabla();
        tablaDesdeMatriz.cargarDesdeMatriz(matriz, true);


        for (Columna<?> columna : tablaDesdeMatriz.obtenerColumnas()){
            System.out.println(columna.obtenerNombre() +  String.valueOf(columna.getClass()) );
            for (Celda<?> celda : columna.obtenerCeldas()){
                System.out.println(celda.obtenerValor() + String.valueOf(celda.getClass()));
            }
        }
        System.out.println("\n");

        List<String> listaInt = new ArrayList<>();
        listaInt.add("Edad");
        tablaDesdeMatriz.ordenarPorColumnas(listaInt, true);

        for (Columna<?> columna : tablaDesdeMatriz.obtenerColumnas()){
            System.out.println(columna.obtenerNombre() +  String.valueOf(columna.getClass()) );
            for (Celda<?> celda : columna.obtenerCeldas()){
                System.out.println(celda.obtenerValor() + String.valueOf(celda.getClass()));
            }
        }


        // System.out.println(matriz[0]);

        // System.out.println(matriz[0][0]);
        // System.out.println(matriz[3][1].getClass());

        // Cargar datos desde la matriz
        //tabla.cargarDesdeMatriz(matriz);

        // Imprimir la tabla, limitando a un número específico de filas y columnas
        // int limiteFilas = 5;  // Por ejemplo, imprimir las primeras 5 filas
        // int limiteColumnas = 3; // Imprimir las primeras 3 columnas
        // System.out.println(tabla);
    }
}
