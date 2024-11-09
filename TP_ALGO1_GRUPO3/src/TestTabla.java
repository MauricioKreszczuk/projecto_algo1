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
        listaDeFilas.add(Arrays.asList("Apellido", "ID", "Nombre", "Edad", "Vive", "Columna vacia"));
        listaDeFilas.add(Arrays.asList("Henry",1, "Alice", 30, true, null));
        listaDeFilas.add(Arrays.asList("Nazario",2, "Bob", 25, true, "NA"));
        listaDeFilas.add(Arrays.asList("Domingo",3, "Charlie", 35, false, "na"));
        listaDeFilas.add(Arrays.asList("Hernandez",4, "Roman", 35, false, null));
        listaDeFilas.add(Arrays.asList("Se",5, "Juan", 40, true, ""));
        
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

        // tablaDesdeSecuenciaLineal.head(5);
        tablaDesdeSecuenciaLineal.tail(2);
        System.out.println("");
        List<String> listaColumnas = new ArrayList<String>();
        listaColumnas.add("Nombre");
        listaColumnas.add("Apellido");
        listaColumnas.add("Edad"); 
        tablaDesdeSecuenciaLineal.seleccionar(1, 4, listaColumnas);
        // tablaDesdeSecuenciaLineal.info();

    }
}
