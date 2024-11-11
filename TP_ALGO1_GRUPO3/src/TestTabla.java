import java.io.IOException;

public class TestTabla {
    public static void main(String[] args) throws IOException {
        String pathbat = "C:\\Users\\MAURICIO\\Downloads\\mascosas\\grupo3Algoritmos1\\TP_ALGO1_GRUPO3\\src\\test.csv";
        Tabla tabla = new Tabla(pathbat,true);
        System.out.println(tabla);
        Tabla columnasSeleccionadas = tabla.obtenerColumnas("Edad","Ciudad");
        System.out.println(columnasSeleccionadas);
        


        // System.out.println(tabla);
        // tabla.guardarComoCSV("C:\\Users\\MAURICIO\\Downloads\\grupo3Algoritmos1\\TP_ALGO1_GRUPO3\\src\\escritura.csv");
        // String nombre = tabla.obtenerValor(0,0);
        // System.out.println(nombre);
        // tabla.imputarNA("Edad", "1");
        // tabla.imputarNA("Ciudad", "nada");
        // System.out.println(tabla);

        // List<Object> lista = new ArrayList<>();
        // lista.add("Edad");
        // lista.add(30);
        // lista.add(50);
        // lista.add(20);
        // lista.add(650);
        // lista.add(60);
        // lista.add(10);
        // tabla.insertarColumna(lista, true);

        // System.out.println(tabla);
        // tabla.imputarNA("Edad", 2);
        // tabla.asignarComoIndex(0);
        // System.out.println(tabla);
        // String valor = tabla.obtenerValor("Edad", "Alice");
        // System.out.println(valor);
        // tabla.resetearIndex();
        // System.out.println(tabla);

        // Tabla tabla2 = tabla.copiaProfunda();
        // System.out.println(tabla);
        // System.out.println(tabla2);
        // tabla2.definirValor(0, 0, "Juan");
        // System.out.println(tabla2);
        // System.out.println(tabla.equals(tabla2));
        // tabla2.asignarComoIndex("Nombre");
        // System.out.println(tabla2);
        // System.out.println(    tabla2.obtenerValorString("Edad", "Juan")      );





        // System.out.println(tabla);



        // // Agregar columnas a la tabla
        // // tabla.agregarColumna(new ColumnaString("Nombre"));
        // // tabla.agregarColumna(new ColumnaNumber("Edad"));
        // // tabla.agregarColumna(new ColumnaString("Ciudad"));

        // // Cargar desde una matriz
        // // Object[][] matriz = {
        // //     {"Nombre", "Edad", "Ciudad"},
        // //     {"Juan", 30, "Madrid"},
        // //     {"Ana", null, "Barcelona"},
        // //     {null, 25, "Valencia"},
        // //     {"Luis", 28, null}
        // // };

        // // Tabla tablaDesdeMatriz = new Tabla(matriz, true);

        // // Verificacion del funcionamiento
        // // for (Columna<?> columna : tablaDesdeMatriz.obtenerColumnas()){
        // //     System.out.println(columna.obtenerNombre() +  String.valueOf(columna.getClass()) );
        // //     for (Celda<?> celda : columna.obtenerCeldas()){
        // //         System.out.println(celda.obtenerValor() + String.valueOf(celda.getClass()));
        // //     }
        // // }
        // // System.out.println("\n");

        // // List<String> listaInt = new ArrayList<>();
        // // listaInt.add("Edad");
        // // tablaDesdeMatriz.ordenarPorColumnas(listaInt, true);

        // // for (Columna<?> columna : tablaDesdeMatriz.obtenerColumnas()){
        // //     System.out.println(columna.obtenerNombre() +  String.valueOf(columna.getClass()) );
        // //     for (Celda<?> celda : columna.obtenerCeldas()){
        // //         System.out.println(celda.obtenerValor() + String.valueOf(celda.getClass()));
        // //     }
        // // }
        
        // // System.out.println(matriz[0]);
        // // System.out.println(matriz[0][0]);
        // // System.out.println(matriz[3][1].getClass());


        // // Cargar desde una secuencia lineal nativa de java
        // List<List<Object>> listaDeFilas = new ArrayList<>();
        // listaDeFilas.add(Arrays.asList("Apellido", "ID", "Nombre", "Edad", "Vive", "Columna vacia"));
        // listaDeFilas.add(Arrays.asList("Henry",1, "Alice", 30, true, null));
        // listaDeFilas.add(Arrays.asList("Nazario",2, "Bob", 25, true, "NA"));
        // listaDeFilas.add(Arrays.asList("Domingo",3, "Charlie", 35, false, "na"));
        // listaDeFilas.add(Arrays.asList("Hernandez",4, "Roman", 35, false, null));
        // listaDeFilas.add(Arrays.asList("Se",5, "Juan", 40, true, ""));
        
        // Tabla tablaDesdeSecuenciaLineal = new Tabla(listaDeFilas, true);

        // // Verificacion del funcionamiento
        // // for (Columna<?> columna : tablaDesdeSecuenciaLineal.obtenerColumnas()){
        // //     System.out.println(columna.obtenerNombre() +  String.valueOf(columna.getClass()) );
        // //     for (Celda<?> celda : columna.obtenerCeldas()){
        // //         System.out.println(celda.obtenerValor() + String.valueOf(celda.getClass()));
        // //     }
        // // }
        // // System.out.println("\n");

        // List<String> listaInt = new ArrayList<>();
        // listaInt.add("Edad");
        // tablaDesdeSecuenciaLineal.ordenarPorColumnas(listaInt, true);

        // // tablaDesdeSecuenciaLineal.head(5);
        // tablaDesdeSecuenciaLineal.tail(2);
        // System.out.println("");
        // List<String> listaColumnas = new ArrayList<String>();
        // listaColumnas.add("Nombre");
        // listaColumnas.add("Apellido");
        // listaColumnas.add("Edad"); 
        // tablaDesdeSecuenciaLineal.seleccionar(1, 4, listaColumnas);
        // // tablaDesdeSecuenciaLineal.info();

    }
}
