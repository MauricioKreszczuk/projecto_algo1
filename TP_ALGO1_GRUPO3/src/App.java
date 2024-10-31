public class App {
    public static void main(String[] args) throws Exception {
        String path = "C:/Users/ariel/OneDrive/Escritorio/Varios/UNSAM/Algoritmos_1/TP_FINAL_REPO/grupo3Algoritmos1/sample.CSV";

        Tabla tabla = new Tabla();
        tabla.cargarDesdeCSV(path, false);

        // tabla.imprimirTabla();

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
