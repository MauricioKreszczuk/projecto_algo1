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
//Carga de CSV
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Tabla {
    private List<Fila> filas = new ArrayList<>();
    private List<Columna> columnas = new ArrayList<>();
    private List<String> encabezados = new ArrayList<>();

    public List<Fila> obtenerFilas() {
        return this.filas;
    }

    public List<Columna> obtenerColumnas() {
        return this.columnas;
    }

    public List<String> obtenerEncabezados(){
        return this.encabezados;
    }

    public Tabla cargarDesdeCSV(String path, boolean tieneEncabezado) throws IOException {
        String linea;
        BufferedReader br = new BufferedReader(new FileReader(path));
        ArrayList<ArrayCelda> columnasCastear = new ArrayList<>();
    
        // Leer encabezado si existe, y crear columnas
        if (tieneEncabezado && (linea = br.readLine()) != null) {
            String[] encabezados = linea.split(",");
            for (String encabezado : encabezados) {
                ArrayCelda columna = new ArrayCelda(encabezado); // Creo arreglos que simulen las columnas para almecenar celdas, hasta tener la verificaciones de tipos hecha. Luego crearemos cada columna según el tipo correspondiente.
                columna.asignarNombre(encabezado);
                columnasCastear.add(columna);
                //columnas.add(columna);
            }
        }
    
        // Leer datos y crear columnas si no hay encabezado
        int contador = 0;
        while ((linea = br.readLine()) != null) {
            String[] valores = linea.split(",");
    
            // Crear columnas si no se crearon con encabezado
            if (tieneEncabezado == false) {
                for (int i = 0; i < valores.length; i++) {
                    ArrayCelda columna = new ArrayCelda("Columna" + i);
                    columnasCastear.add(columna);
                    //columnas.add(columna);
                } 
            }
    
            // Verificar que el número de valores coincida con el número de columnas
            if (valores.length != columnasCastear.size()) {
                System.out.println(columnas.size());
                System.out.println(valores.length);
                throw new IOException("El número de valores no coincide con el número de columnas.");
            }
    
            // Asignar cada valor a la columna correspondiente

            for (int i = 0; i < valores.length; i++) {
                Celda<?> celda = this.inferirTipoDeDato(valores[i]);
                columnasCastear.get(i).agregarCelda(celda);
            }
            contador++;
        }
        br.close();

        for (ArrayCelda columna : columnasCastear) {
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
        this.filas.add(fila);
    }

    public Tabla obtenerFila(String etiqueta){
        Fila fila = new Fila(etiqueta);
        int indiceColumna = 0;
        for (Columna columna : this.columnas){
            fila.agregarCelda(columna.obtenerCeldas().get(indiceColumna));
            indiceColumna++;
        }

        Tabla nuevaTabla = new Tabla();
        nuevaTabla.obtenerFilas().add(fila);
        return nuevaTabla;
    }

    public void eliminarFila(int indice){
        this.filas.remove(indice);
    }

    public void agregarColumna(Columna columna){
        this.columnas.add(columna);
    }

    public void eliminarColumna(String nombre){
        this.columnas.remove(nombre);
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

    public Fila[] muestreo(double porcentaje){
        // Implementación
        return null;
    }

    public Tabla crearVista() { //Podríamos no hacer este método
        // Implementación
        return null;
    }

    // @Override
    // public void imputarNA() {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'imputarNA'");
    // }

    private void AutoCasteoColumna(ArrayCelda columna){
        boolean columnaAgregada = false; // Flag para evitar duplicado de columnas

        for (int i=0; i < columna.obtenerTamaño(); i++){
            if (!(columna.obtenerCeldas().get(i) instanceof CeldaNA)){
                if (columna.obtenerCeldas().get(i) instanceof CeldaBoolean){
                    ColumnaBoolean CBoolean = new ColumnaBoolean(columna.obtenerNombre(), columna.obtenerCeldas());
                    this.columnas.add(CBoolean);
                    columnaAgregada = true;
                    break;
                }
                else if (columna.obtenerCeldas().get(i) instanceof CeldaNumber){
                    ColumnaNumber CNumber = new ColumnaNumber(columna.obtenerNombre(), columna.obtenerCeldas());
                    this.columnas.add(CNumber);
                    columnaAgregada = true;
                    break;
                }
                else if (columna.obtenerCeldas().get(i) instanceof CeldaString){
                    ColumnaString CString = new ColumnaString(columna.obtenerNombre(), columna.obtenerCeldas());
                    this.columnas.add(CString);
                    columnaAgregada = true;
                    break;
                }
            }
        }

        if (columnaAgregada == false){ // Sí el estado no cambió, creo y agrego una columnaNA al atributo celdas de la Tabla. (No uso !columnaAgregada para evitar error de lógica.)
            ColumnaNA CNA = new ColumnaNA(columna.obtenerNombre(), columna.obtenerCeldas());
            this.columnas.add(CNA);
        }
    }

    private boolean esDecimal(String valor) {
        try {
            Double.parseDouble(valor);
            return true;
        } catch (NumberFormatException e) {
            return false;
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



}