public class App {
    public static void main(String[] args) throws Exception {
        String path = "/home/tareas/Documents/TP_Algo1_grupo3/grupo3_2/grupo3Algoritmos1/Organization_v3.csv";

        Tabla tabla = new Tabla();
        tabla.cargarDesdeCSV(path, true);

        tabla.imprimirTabla();
    }
}
