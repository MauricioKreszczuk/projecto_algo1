import Celda.Celda;
import Celda.CeldaNA;
import Celda.CeldaBoolean;
import Array.Fila;
import Celda.CeldaNumber;
import Celda.CeldaString;
import Array.Columnas.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
//Carga de CSV
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;




public class Tabla {
    private List<Fila> filas = new ArrayList<>();
    private List<Columna> columnas = new ArrayList<>();
    private List<String> encabezados = new ArrayList<>();


    // public Tabla cargarDesdeCSV(String path, boolean tieneEncabezado) throws IOException {
    //     Tabla tabla = new Tabla();
    //     String linea;
    //     BufferedReader br = new BufferedReader(new FileReader(path));

    //     if ((linea = br.readLine()) != null && tieneEncabezado) {
    //         String[] encabezados = linea.split(",");
    //         for (String encabezado : encabezados) {
    //             Columna columna = new Columna();
    //             columna.asignarNombre(encabezado);
    //             tabla.columnas.add(columna);
    //         }
    //     }

    //     while ((linea = br.readLine()) != null) {
    //         String[] valores = linea.split(",");
    //         Fila fila = new Fila();

    //         for (int i = 0; i < valores.length; i++) {
    //             String valor = valores[i];
    //             Celda<?> celda;

    //             if (valor.isEmpty() || valor.equalsIgnoreCase("null")) {
    //                 // celda = new CeldaNA();
    //                 valor = null;
    //             } else if (esBoolean(valor)) {
    //                 // celda = new CeldaBoolean(Boolean.parseBoolean(valor)); // Ver bien esto, como toma True/true/TRue, etc
    //                 // Boolean valor = Boolean.parseBoolean(valor); // Ver bien esto, como toma True/true/TRue, etc
    //             } else if (esEntero(valor)) {
    //                 // celda = new CeldaNumber(Integer.parseInt(valor)); // Ver bien radix 10
    //             } else if (esDecimal(valor)) {
    //                 // celda = new CeldaNumber(Double.parseDouble(valor));
    //             } else {
    //                 // celda = new CeldaString(valor);
    //                 continue;
    //             }

    //             fila.agregarValor(valor);
    //             if (tabla.columnas.size() > i) {
    //                 tabla.columnas.get(i).agregarValor(valor); //Hay que hacer bien la importacion desde Columna
    //             }
    //         }
    //         tabla.filas.add(fila);
    //     }
    //     br.close();
    //     return tabla;
    // }

    // private boolean esBoolean(String valor) {
    //     return valor.equalsIgnoreCase("true") || valor.equalsIgnoreCase("false");
    // }

    // private boolean esEntero(String valor) {
    //     try {
    //         Integer.parseInt(valor);
    //         return true;
    //     } catch (NumberFormatException e) {
    //         return false;
    //     }
    // }

    // private boolean esDecimal(String valor) {
    //     try {
    //         Double.parseDouble(valor);
    //         return true;
    //     } catch (NumberFormatException e) {
    //         return false;
    //     }
    // }

    // private inferirTipoDeDato(){

    // }

    public List<Fila> getFilas() {
        return filas;
    }

    public List<Columna> getColumnas() {
        return columnas;
    }

    public void testeo(){
        for (Columna<?> columna : columnas){
            System.out.println(columna.obtenerNombre());
            for (Celda<?> celda : columna.obtenerCeldas()){
                System.out.println(celda.obtenerValor());
            }
        }
    }

    public Tabla cargarDesdeCSV(String path, boolean tieneEncabezado) throws IOException {
        String linea;
        BufferedReader br = new BufferedReader(new FileReader(path));
    
        // Leer encabezado si existe, y crear columnas
        if (tieneEncabezado && (linea = br.readLine()) != null) {
            String[] encabezados = linea.split(",");
            for (String encabezado : encabezados) {
                ColumnaString columna = new ColumnaString(encabezado);
                columna.asignarNombre(encabezado);
                columnas.add(columna);
            }
        }
    
        // Leer datos y crear columnas si no hay encabezado
        int contador = 0;
        while ((linea = br.readLine()) != null) {
            String[] valores = linea.split(",");
    
            // Crear columnas si no se crearon con encabezado
            if (columnas.isEmpty()) {
                for (int i = 0; i < valores.length; i++) {
                    ColumnaString columna = new ColumnaString("Columna" + i);
                    columnas.add(columna);
                }
            }
    
            // Verificar que el número de valores coincida con el número de columnas
            if (valores.length != columnas.size()) {
                System.out.println(columnas.size());
                System.out.println(valores.length);
                throw new IOException("El número de valores no coincide con el número de columnas.");
            }
    
            // Asignar cada valor a la columna correspondiente
            for (int i = 0; i < valores.length; i++) {
                columnas.get(i).agregarValor(valores[i]);
            }
            contador++;
        }
        br.close();
        return this;
    }

    public void imprimirTabla() { //Arreglarlo
        
        // Imprimir encabezado
        for (Columna columna : columnas) {
            System.out.print(String.format("%-15s", columna) + " | ");
        }
        System.out.println();

        // Crear separador
        StringBuilder separador = new StringBuilder();
        int ancho = columnas.size() * 18;
        for (int i = 0; i < ancho; i++) {
            separador.append("-");
        }
        System.out.println(separador.toString());

        // Imprimir filas
        for (Fila fila : filas) {
            System.out.println(fila);
            System.out.println();
        }
    }

    public void agregarFila(Fila fila){
        filas.add(fila);
        // implementación
    }

    public void eliminarFila(int indice){
        // Implementación
    }

    public void agregarColumna(Columna columna){
        // Implementación
    }

    public void eliminarColumna(String nombre){
        // Implementación
    }


    public Tabla filtrar() {
        // Pendiente
        return null;
    }

    public void ordenar(String columna, Boolean ascendente, Boolean inplace) {
        // Implementación 
    }

    public Tabla concatenar(Tabla tabla1){
        // Faltan más argumentos
        // Implementación
        return null;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        if (!columnas.isEmpty()) {
            for (Columna columna : columnas) {
                builder.append(columna.obtenerNombre()).append("\t");
            }
            builder.append("\n");
        }

        for (Fila fila : filas) {
            
            

            // for (Celda<?> celda : fila.agregarValor() {
            //     builder.append(celda.toString()).append("\t");  // Agregar cada celda con tabulación
            // }
            builder.append(fila.toString()).append("\t");
            // builder.append("\n");  // Nueva línea después de cada fila
        }

        System.out.println(builder.toString());
        return builder.toString();
    }

    // public void imprimirTabla() {
    //     // Imprimir encabezado
    //     for (Columna columna : columnas) {
    //         System.out.print(String.format("%-15s", columna.obtenerNombre()) + " | ");
    //     }
    //     System.out.println();
    
    //     // Crear manualmente la línea de separación
    //     StringBuilder separador = new StringBuilder();
    //     int ancho = columnas.size() * 18; // Ajusta el ancho según el número de columnas y el formato
    //     for (int i = 0; i < ancho; i++) {
    //         separador.append("-");
    //     }
    //     System.out.println(separador.toString());
    
    //     // Imprimir cada fila
    //     for (Fila fila : filas) {
    //         System.out.println(fila);
    //     }
    // }

    public Fila[] muestreo(double porcentaje){
        // Implementación
        return null;
    }

    public Tabla crearVista() {
        // Implementación
        return null;
    }


    // @Override
    // public void imputarNA() {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'imputarNA'");
    // }

}