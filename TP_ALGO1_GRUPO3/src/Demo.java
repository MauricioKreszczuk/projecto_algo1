import java.io.IOException;
import java.util.Arrays;

public class Demo {
    public static void main(String[] args) throws IOException {
        Tabla tabla = new Tabla("/home/tareas/Downloads/grupo3Algoritmos1/TP_ALGO1_GRUPO3/src/Plastic Waste Around the World.csv", true);
        // System.out.println(tabla);




        tabla.info(); // informacion basica


        ///////////// ACCESO INDEXADO
        Tabla indexar = tabla.copiaProfunda();
        Tabla fila = indexar.obtenerFila(1);
        Tabla columna = indexar.obtenerColumna("Country");
        String celda = indexar.obtenerValorString(0, 0);
        System.out.println("ACCESO INDEXADO");
        System.out.println(fila);
        System.out.println(columna);
        System.out.println(celda);



        ////////////SELECCION
        System.out.println("SELECCION");
        tabla.seleccionar(2, 4, null);

        // FILTRADO Y COPIA INDEPENDIENTE
        System.out.println("Filtrado y copia");
        Tabla tabla2 = tabla.copiaProfunda();
        tabla2.and(tabla2.condicion("Country","==", "China"),
        tabla2.condicion("Recycling_Rate",">=", 10));
        

        System.out.println(tabla2);

        Tabla filtrada = tabla.filtrar("Main_Sources", Tabla.condicion("==","Packaging_Industrial"));
        System.out.println(filtrada);
        
        /////// CONCATENACION
        System.out.println("CONCATENACION");
        Tabla concatenada = Tabla.concatenar(tabla, tabla);
        System.out.println(concatenada);


        ///////// ORDENAMIENTO
        System.out.println("ORDENAMIENTO");
        Tabla ordenada = tabla.copiaProfunda();
        ordenada.ordenarPorColumnas(Arrays.asList("Country"), false);
        System.out.println(ordenada);

        ///////// IMPUTACION
        System.out.println("IMPUTACION");
        Tabla imputada = tabla.copiaProfunda();
        imputada.imputarNA("Total_Plastic_Waste_MT", 0);
        System.out.println(imputada);


        //////////  MUESTREO
        System.out.println("MUESTREO");
        tabla.muestreo(5);

    }
}