import java.util.function.Predicate;

import Array.Fila;
import Celda.Celda;

public class testFiltrar {
        @SuppressWarnings("unchecked")
        public static void main(String[] args) throws Exception {
        String pathLabo = "C://Users//MAURICIO//grupo3Algoritmos1//TP_ALGO1_GRUPO3//src//testNoTanFeo.csv";
        String pathBat = "C://Users//ariel//OneDrive//Escritorio//Varios//UNSAM//Algoritmos_1//TP_FINAL_REPO//grupo3Algoritmos1//TP_ALGO1_GRUPO3//src//testNoTanFeo.csv";


        // String delimitador = ",";
        // Tabla tablaDesdeCSV = new Tabla(pathBat, true, delimitador);
        Tabla tabla = new Tabla("C:\\Users\\MAURICIO\\Downloads\\mascosas\\grupo3Algoritmos1\\TP_ALGO1_GRUPO3\\src\\carga.csv", true);

        System.out.println(tabla);
        Tabla tabla2 = tabla.copiaProfunda();

        // Tabla filtrada = tabla.filtrar("Pais", Tabla.condicion("==", "España"));
        // System.out.println(filtrada);z
        
        tabla.or(tabla.condicion("Edad", ">=",28),
                tabla.condicion("Ciudad","==","París"));

        System.out.println(tabla);

        tabla2.and(tabla2.condicion("Ciudad","==", "Nueva York"),
                    tabla2.condicion("Edad",">=", 30));
            
        System.out.println(tabla2);
        tabla2.testeo();

        // tablaDesdeCSV.imprimirTabla();


    //     List<String> lista = new ArrayList<>();
    //     lista.add("Ciudad");
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
        // System.out.println("//n");

        // List<String> listaInt = new ArrayList<>();
        // listaInt.add("Ciudad");
        // listaInt.add("Peso");
        // List<String> listaString = new ArrayList<>();
        // listaString.add("Ciudad");
        // listaString.add("Pais");
        // tablaDesdeCSV.ordenarPorColumnas(listaString, true);


        // for (Columna<?> columna : tablaDesdeCSV.obtenerColumnas()){
        //     System.out.println(columna.obtenerNombre() +  String.valueOf(columna.getClass()) );
        //     for (Celda<?> celda : columna.obtenerCeldas()){
        //         System.out.println(celda.obtenerValor() + String.valueOf(celda.getClass()));
        //     }
        // }

        // System.out.println("//n" + tablaDesdeCSV);
        // System.out.println("");
        // tablaDesdeCSV.head(5);



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

    //     tabla.obtenerEncabezados().addAll(Arrays.asList("ID", "Nombre", "Ciudad"));

    //     List<Celda<?>> fila1Celdas = Arrays.asList(new CeldaNumber(3), new CeldaString("Carlos"), new CeldaNumber(25));
    //     List<Celda<?>> fila2Celdas = Arrays.asList(new CeldaNumber(1), new CeldaString("Ana"), new CeldaNumber(30));
    //     List<Celda<?>> fila3Celdas = Arrays.asList(new CeldaNumber(2), new CeldaString("Beatriz"), new CeldaNumber(20));

    //     Fila fila1 = new Fila(fila1Celdas);
    //     Fila fila2 = new Fila(fila2Celdas);
    //     Fila fila3 = new Fila(fila3Celdas);

    //     tabla.obtenerFilas().addAll(Arrays.asList(fila1, fila2, fila3));

    //     System.out.println("Ordenar por Ciudad ascendente:");
    //     tabla.ordenarPorColumnas(Arrays.asList("Ciudad"), true);
    //     imprimirFilas(tabla.obtenerFilas());

    //     System.out.println("Ordenar por Nombre descendente:");
    //     tabla.ordenarPorColumnas(Arrays.asList("Nombre"), false);
    //     imprimirFilas(tabla.obtenerFilas());

    //     System.out.println("Ordenar por ID ascendente y Ciudad descendente:"); //Se rompe cuando influyen varias columnas al ordenamiento
    //     tabla.ordenarPorColumnas(Arrays.asList("ID", "Ciudad"), true);
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