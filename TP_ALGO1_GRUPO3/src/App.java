import Array.Columnas.Columna;
import Celda.Celda;

public class App {
    public static void main(String[] args) throws Exception {
        String path = "C:\\Users\\Lenovo\\Desktop\\cosas de Alejo\\Materias\\Algoritmos I\\a1\\grupo3Algoritmos1\\TP_ALGO1_GRUPO3\\src\\test.csv"; //"/home/tareas/Documents/TP_ALGO1_GRUPO3/grupo3Algoritmos1/TP_ALGO1_GRUPO3/src/test.csv"

        //Tabla tabla = new Tabla();
        //tabla.cargarDesdeCSV(path, true);

        Tabla prueba2 = new Tabla();
        prueba2.cargarDesdeCSV(path, true);

        // tabla.imprimirTabla(); // revisar, está fallando el metodo en la salida por pantalla.

        //  public void testeo(){
            
        //     for (Columna<?> columna : columnas){
        //         System.out.println(columna.obtenerNombre() +  String.valueOf(columna.getClass()) );
        //         for (Celda<?> celda : columna.obtenerCeldas()){
        //             System.out.println(celda.obtenerValor() + String.valueOf(celda.getClass()));
        //         }
        //     }
        // }

        // -- Chequeo las columnas y su contenido --

        System.out.println(prueba2.obtenerColumnas());
        for (Columna<?> columna : prueba2.obtenerColumnas()){
            System.out.println(columna.obtenerCeldas());
        }

        // -- Fin del chequeo ---


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
