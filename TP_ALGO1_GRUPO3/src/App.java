public class App {
    public static void main(String[] args) throws Exception {
        String path = "/home/tareas/Documents/TP_ALGO1_GRUPO3/grupo3Algoritmos1/TP_ALGO1_GRUPO3/src/test.csv";

        Tabla tabla = new Tabla();
        tabla.cargarDesdeCSV(path, true);

        // // tabla.imprimirTabla();

        tabla.testeo();

        // System.out.println(tabla.getColumnas());

        // List<Columna<?>> columnas = tabla.getColumnas();

        // for (List<Columna> columna : tabla.getColumnas()){
        //     System.out.println("Entre");
        //     System.out.println(columna.obtenerNombre());
        //     for (Celda<?> celda : columna.obtenerCeldas()){
        //         System.out.println(celda.obtenerValor());
        //     }
        // }
}
}
