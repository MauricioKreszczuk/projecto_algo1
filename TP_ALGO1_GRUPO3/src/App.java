import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Array.Fila;
import Array.Columnas.Columna;
import Celda.Celda;
import Celda.CeldaNumber;
import Celda.CeldaString;

public class App {
    public static void main(String[] args) throws Exception {
        String pathLabo = "/home/tareas/Documents/TP_ALGO1_GRUPO3/grupo3Algoritmos1/TP_ALGO1_GRUPO3/src/testNoTanFeo.csv";
        String pathBat = "C:\\Users\\ariel\\OneDrive\\Escritorio\\Varios\\UNSAM\\Algoritmos_1\\TP_FINAL_REPO\\grupo3Algoritmos1\\TP_ALGO1_GRUPO3\\src\\testNoTanFeo.csv";
        String pathAle = "C:\\Users\\Lenovo\\Desktop\\cosas de Alejo\\Materias\\Algoritmos I\\a1\\grupo3Algoritmos1\\TP_ALGO1_GRUPO3\\src\\testNoTanFeo.csv"; 

        String delimitador = ",";
        Tabla tablaDesdeCSV = new Tabla(pathBat, true, delimitador);

        // tablaDesdeCSV.imprimirTabla();


    //     List<String> lista = new ArrayList<>();
    //     lista.add("Edad");
    //     tablaDesdeCSV.ordenarPorColumnas(lista, false);
    //     tablaDesdeCSV.imprimirTabla();


        // Tabla tablaDesdeCSV2 = new Tabla(pathLabo, true);
        // tablaDesdeCSV2.imprimirTabla(); // revisar, está fallando el metodo en la salida por pantalla.


        // Testeos
        
        // for (Columna<?> columna : tablaDesdeCSV.obtenerColumnas()){
        //     System.out.println(columna.obtenerNombre() +  String.valueOf(columna.getClass()) );
        //     for (Celda<?> celda : columna.obtenerCeldas()){
        //         System.out.println(celda.obtenerValor() + String.valueOf(celda.getClass()));
        //     }
        // }
        // System.out.println("\n");

        List<String> listaInt = new ArrayList<>();
        listaInt.add("Edad");
        listaInt.add("Peso");
        List<String> listaString = new ArrayList<>();
        listaString.add("Ciudad");
        listaString.add("Pais");
        tablaDesdeCSV.ordenarPorColumnas(listaString, true);


        // for (Columna<?> columna : tablaDesdeCSV.obtenerColumnas()){
        //     System.out.println(columna.obtenerNombre() +  String.valueOf(columna.getClass()) );
        //     for (Celda<?> celda : columna.obtenerCeldas()){
        //         System.out.println(celda.obtenerValor() + String.valueOf(celda.getClass()));
        //     }
        // }

        System.out.println("\n" + tablaDesdeCSV);
        System.out.println("");
        tablaDesdeCSV.head(5);



        // List<Columna<?>> columnas = tabla.getColumnas();

        // for (List<Columna> columna : tabla.getColumnas()){
        //     System.out.println("Entre");
        //     System.out.println(columna.obtenerNombre());
        //     for (Celda<?> celda : columna.obtenerCeldas()){
        //         System.out.println(celda.obtenerValor());
        //     }
        // }
        
        // ----------- ORDENAMIENTO ---------------------------------

        // IMPORTANTE: Esta funcionalidad habría que cambiarla para poder ordenar filas de formas ascendetne o descendente y cambiar la posición de las columnas, no hacer un multi ordenamiento porque es imposible
    //     Tabla tabla = new Tabla();
        
    //     tabla.obtenerEncabezados().addAll(Arrays.asList("ID", "Nombre", "Edad"));
        
    //     List<Celda<?>> fila1Celdas = Arrays.asList(new CeldaNumber(3), new CeldaString("Carlos"), new CeldaNumber(25));
    //     List<Celda<?>> fila2Celdas = Arrays.asList(new CeldaNumber(1), new CeldaString("Ana"), new CeldaNumber(30));
    //     List<Celda<?>> fila3Celdas = Arrays.asList(new CeldaNumber(2), new CeldaString("Beatriz"), new CeldaNumber(20));
        
    //     Fila fila1 = new Fila(fila1Celdas);
    //     Fila fila2 = new Fila(fila2Celdas);
    //     Fila fila3 = new Fila(fila3Celdas);
        
    //     tabla.obtenerFilas().addAll(Arrays.asList(fila1, fila2, fila3));
        
    //     System.out.println("Ordenar por Edad ascendente:");
    //     tabla.ordenarPorColumnas(Arrays.asList("Edad"), true);
    //     imprimirFilas(tabla.obtenerFilas());

    //     System.out.println("Ordenar por Nombre descendente:");
    //     tabla.ordenarPorColumnas(Arrays.asList("Nombre"), false);
    //     imprimirFilas(tabla.obtenerFilas());

    //     System.out.println("Ordenar por ID ascendente y Edad descendente:"); //Se rompe cuando influyen varias columnas al ordenamiento
    //     tabla.ordenarPorColumnas(Arrays.asList("ID", "Edad"), true);
    //     imprimirFilas(tabla.obtenerFilas());
    // }

        // private static void imprimirFilas(List<Fila> filas) {
        // for (Fila fila : tabla.obtenerFilas()) {
        //     System.out.print("Fila: ");
        //     for (Celda<?> celda : fila.obtenerCeldas()) {
        //         System.out.print(celda.obtenerValor() + " ");
        //     }
        //     System.out.println();
        // }
        // }
    }
}
        
