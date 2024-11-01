import Celda.Celda;
import Celda.CeldaNA;
import Celda.CeldaBoolean;
import Array.ArrayCelda;
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


    private boolean esBoolean(String valor) {
        return valor.equalsIgnoreCase("true") || valor.equalsIgnoreCase("false");
    }

    private boolean esDecimal(String valor) {
        try {
            Double.parseDouble(valor);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

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
            System.out.println(columna.obtenerNombre() +  String.valueOf(columna.getClass()) );
            for (Celda<?> celda : columna.obtenerCeldas()){
                System.out.println(celda.obtenerValor() + String.valueOf(celda.getClass()));
            }
        }
    }

    private Celda<?> inferirTipoDeDato(String valorCelda){
        if (valorCelda.toLowerCase() == "true"){
        return new CeldaBoolean(true);
        }
        else if (valorCelda.toLowerCase() == "false"){
        return new CeldaBoolean(false);
        }
        
        if (this.esDecimal(valorCelda)){
        return new CeldaNumber(Double.parseDouble(valorCelda), true);
        }
        
        if (valorCelda.length() == "".length() || valorCelda.toLowerCase() == "na"|| valorCelda == null ){ // infintas opciones mas
        return new CeldaNA();
        }
        
        return new CeldaString(valorCelda);
        
        
    }

    // public boolean esBoolean(ArrayCelda columna){
    //     boolean booleano = true;
    //     for (int i = 0; i < columna.obtenerTamaño(); i++){
    //         if (columna.obtenerCeldas().get(i) instanceof CeldaBoolean ||
    //         columna.obtenerCeldas().get(i) instanceof CeldaNA){
    //             booleano = true;
    //         }
    //         else{
    //             booleano = false;
    //         }
            
    //     return booleano
    // }

    private Columna<?> AutoCasteoColumna(Columna<?> columna){
        for (int i = 0; i < columna.obtenerTamaño(); i++){
            if (!(columna.obtenerCeldas().get(i) instanceof CeldaNA)){
                if (columna.obtenerCeldas().get(i) instanceof CeldaBoolean){
                    ColumnaBoolean cboolean = new ColumnaBoolean(columna.obtenerNombre());
                    for (CeldaBoolean bboolean : columna.obtenerCeldas()){
                        
                    }
                }
                else if (columna.obtenerCeldas().get(i) instanceof CeldaNumber){
                    Columna<Number> columnat = (Columna<Number>) columna;
                    ColumnaNumber columnaB = (ColumnaNumber) columnat;
                    return columnaB;
                }
                else if (columna.obtenerCeldas().get(i) instanceof CeldaString){
                    Columna<String> columnat = (Columna<String>) columna;
                    ColumnaString columnaB = (ColumnaString) columnat;
                    return columnaB;
                }
            }

        }
        return (ColumnaNA) columna;
    }

    // private Boolean esColumnaBoolean(Columna ){}

    // private Columna<?> obtenerTipoColumna(ArrayCelda columna){
    //     for (int i = 0; i < columna.obtenerTamaño(); i++){
    //         if (!(columna.obtenerCeldas().get(i) instanceof CeldaNA)){
    //             if (columna.obtenerCeldas().get(i) instanceof CeldaBoolean){
    //                 return new ColumnaBoolean("");
    //             }
    //             else if (columna.obtenerCeldas().get(i) instanceof CeldaNumber){
    //                 return (ColumnaNumber) columna;
    //             }
    //             else if (columna.obtenerCeldas().get(i) instanceof CeldaString){
    //                 return (ColumnaString) columna;
    //             }
    //         }

    //     }
    //     return (ColumnaNA) columna;
    // }



    public Tabla cargarDesdeCSV(String path, boolean tieneEncabezado) throws IOException {
        String linea;
        BufferedReader br = new BufferedReader(new FileReader(path));
    
        // Leer encabezado si existe, y crear columnas
        if (tieneEncabezado && (linea = br.readLine()) != null) {
            String[] encabezados = linea.split(",");
            for (String encabezado : encabezados) {
                Columna<?> columna = new Columna(encabezado);
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
                    Columna<?> columna = new Columna("Columna" + i);
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



                Celda<?> celda = this.inferirTipoDeDato(valores[i]);
                columnas.get(i).agregarCelda(celda);
            }
            contador++;
        }
        br.close();

        for (Columna<?> columna : this.columnas) {
            AutoCasteoColumna(columna);
            
        }
            
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