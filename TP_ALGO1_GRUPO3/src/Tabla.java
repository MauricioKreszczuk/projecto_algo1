import Celda.Celda;
import Celda.CeldaNA;
import Celda.CeldaBoolean;
import Array.ArrayCelda;
import Array.Fila;
import Celda.CeldaNumber;
import Celda.CeldaString;
import ExcepcionTabla.*;
//import util.ImputarFaltantes;
import Array.Columnas.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
//Carga de CSV
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Tabla{
    private List<Fila> filas = new ArrayList<>();
    private List<Columna> columnas = new ArrayList<>();
    private List<String> encabezados = new ArrayList<>();

    public Tabla(){
        
    }

    public Tabla(List<Columna> columnas, List<Fila> filas){
        this.columnas = columnas;
        this.filas = filas;
        for (Columna col : this.columnas){
            this.encabezados.add(col.obtenerNombre());
        } 
    }

    public Tabla(List<List<Object>> listaDeFilas, Boolean tieneEncabezado){
        // Hacer excepcion de si se pasa una lista vacia y si tienen distintos largos
        ArrayList<ArrayCelda> columnasCastear = new ArrayList<>();

        if (tieneEncabezado){
            Object[] encabezadosObject = listaDeFilas.get(0).toArray();
            for (Object encabezado : encabezadosObject) {
                String encabezadoString = (String) encabezado;
                this.encabezados.add(encabezadoString);
                ArrayCelda columna = new ArrayCelda(encabezadoString);
                columnasCastear.add(columna);
            }
        }
        else if (!(tieneEncabezado)){
            int numeroColumnas = listaDeFilas.get(0).size();
            for (int i = 0; i < numeroColumnas; i++) {
                this.encabezados.add("Columna" + i);
                ArrayCelda columna = new ArrayCelda("Columna" + i);
                columnasCastear.add(columna);
            }
        }

        int filaInicio = tieneEncabezado ? 1 : 0;
        for (int i = filaInicio; i < listaDeFilas.size(); i++) {
            if (listaDeFilas.get(i).size() != columnasCastear.size()) {
                throw new IllegalArgumentException("La fila " + i + " no coincide con el número de columnas.");
                //Modificar para que la excepcion diga el número de la fila
            }
    
            Fila fila = new Fila("Fila" + (filas.size() + 1));
            for (int j = 0; j < columnasCastear.size(); j++) {
                Object valor = listaDeFilas.get(i).get(j);
                Celda<?> celda = inferirTipoDesdeObject(valor);
                columnasCastear.get(j).agregarCelda(celda);
                fila.agregarCelda(celda);
            }
            filas.add(fila);
        }

        for (ArrayCelda columna : columnasCastear) {
            AutoCasteoColumna(columna);
            int indiceCol = this.obtenerColumnas().indexOf(columna);
            if (!esConsistente(this.obtenerColumnas().get(indiceCol))) {
                reemplazarIncosistencias(this.obtenerColumnas().get(indiceCol));
            }
        }
    }

    public Tabla(Object[][] matriz, Boolean tieneEncabezado) { 
        ArrayList<ArrayCelda> columnasCastear = new ArrayList<>();
        
        if (matriz.length == 0) {// CrearExepcion
        }

        if (tieneEncabezado) {
            Object[] encabezados = matriz[0];
            for (Object encabezadoObject : encabezados) {
                String encabezado = (String) encabezadoObject;
                ArrayCelda columna = new ArrayCelda(encabezado);
                columna.asignarNombre(encabezado);
                this.encabezados.add(encabezado);
                columnasCastear.add(columna);
            }
        }
        else if (!(tieneEncabezado)){
            int numeroColumnas = matriz[0].length;
            for (int i = 0; i < numeroColumnas; i++) {
                ArrayCelda columna = new ArrayCelda("Columna" + i);
                this.encabezados.add("Columna" + i);
                columnasCastear.add(columna);
            } 
        }
        int filaInicio = tieneEncabezado ? 1 : 0;
        for (int i = filaInicio; i < matriz.length; i++) {
            Object[] filaDatos = matriz[i];
            
            // Verificar que la fila tenga el mismo número de valores que el número de columnas
            if (filaDatos.length != columnasCastear.size()) {
                throw new IllegalArgumentException("La fila " + i + " no coincide con el número de columnas.");
            }
    
            Fila fila = new Fila("Fila" + (filas.size() + 1));
            for (int j = 0; j < filaDatos.length; j++) {
                Celda<?> celda = this.inferirTipoDesdeObject(filaDatos[j]);
                columnasCastear.get(j).agregarCelda(celda);
                fila.agregarCelda(celda);
            }
            this.filas.add(fila);
        }
    
        // Verificación de consistencia y auto-casteo de columnas
        for (ArrayCelda columna : columnasCastear) {
            AutoCasteoColumna(columna);
            int indiceCol = this.obtenerColumnas().indexOf(columna);
            if (!esConsistente(this.obtenerColumnas().get(indiceCol))) {
                reemplazarIncosistencias(this.obtenerColumnas().get(indiceCol));
            }
        }

    }

    private Celda<?> inferirTipoDesdeObject(Object valor){
        
        if (valor instanceof Number) {
            Number valorNumber = (Number) valor;
            return new CeldaNumber(valorNumber);
        }

        else if (valor instanceof Boolean) {
            Boolean valorBoolean = (Boolean) valor;
            return new CeldaBoolean(valorBoolean);
        }

        String valorString = (String) valor;
        if (valorString == null || valorString.equalsIgnoreCase("na") || 
        valorString.equalsIgnoreCase("n/a") || valorString.equals("")) {
            return new CeldaNA();
        }

        else {
            return new CeldaString(valorString);
        }
    }

    public void crearFilasPorColumnas(List<Columna> columnas) {

        int tamaño = columnas.get(0).obtenerTamaño();
        List<String> etiquetas = columnas.get(0).obtenerEtiquetas();
        // Iterar sobre los valores de cada columna para formar las filas
        for (int indice = 0; indice < tamaño; indice++) {
            String etiquetaFila = etiquetas.get(indice); 
            Fila filaTemporal = new Fila(etiquetaFila); // Asignar nombre a la fila
            for (Columna columna : columnas) {
                Celda<?> celda = columna.obtenerCeldas().get(indice); // Obtener la celda correspondiente en este índice
                filaTemporal.agregarCelda(celda); // Agregar la celda a la fila
            }
    
            // Una vez que la fila está completa, agregarla a la tabla
            this.filas.add(filaTemporal);
        }
    }

    private void actualizarFilas(){
        crearFilasPorColumnas(this.columnas);
    }
    
    public <T> T obtenerValor(int indiceColumna, int IndiceFila){
        return (T) columnas.get(indiceColumna).obtenerValor(IndiceFila);
    }


    public List<Fila> obtenerFilas() {
        return this.filas;
    }

    public List<Columna> obtenerColumnas() {
        return this.columnas;
    }

    public List<String> obtenerEncabezados(){
        return this.encabezados;
    }

    public Tabla(String path, boolean tieneEncabezado) throws IOException {
        String linea;
        String delimitador = ",";
        ArrayList<ArrayCelda> columnasCastear = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))){

            // Leer encabezado si existe, y crear columnas
            if (tieneEncabezado && (linea = br.readLine()) != null) {
                String[] encabezadosArray = linea.split(delimitador);
                for (String encabezado : encabezadosArray) {
                    ArrayCelda columna = new ArrayCelda(encabezado); // Creo arreglos que simulen las columnas para almecenar celdas, hasta tener la verificaciones de tipos hecha. Luego crearemos cada columna según el tipo correspondiente.
                    columna.asignarNombre(encabezado);
                    this.encabezados.add(encabezado);
                    columnasCastear.add(columna);
                    //columnas.add(columna);
                }
            }
        
            // Leer datos y crear columnas si no hay encabezado
            while ((linea = br.readLine()) != null) {
                String[] valores = linea.split(delimitador);
        
                // Crear columnas si no se crearon con encabezado
                if (tieneEncabezado == false) {
                    for (int i = 0; i < valores.length; i++) {
                        ArrayCelda columna = new ArrayCelda("Columna" + i);
                        this.encabezados.add("Columna" + i);
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
        
                // Asignar cada valor a la columna y fila correspondiente
                Fila fila = new Fila("Fila" + (filas.size() + 1));
                for (int i = 0; i < valores.length; i++) {
                    Celda<?> celda = this.inferirTipoDeDato(valores[i]);
                    columnasCastear.get(i).agregarCelda(celda);
                    fila.agregarCelda(celda);
                }
                this.filas.add(fila);
            }
        }

        for (ArrayCelda columna : columnasCastear) {
            AutoCasteoColumna(columna);
            int indiceCol = this.obtenerColumnas().indexOf(columna);
            if (!(esConsistente(this.obtenerColumnas().get(indiceCol)))){
                reemplazarIncosistencias(this.obtenerColumnas().get(indiceCol));
            }
        }
    }
    
    public Tabla(String path, boolean tieneEncabezado, String delimitador) throws IOException {
        String linea;
        ArrayList<ArrayCelda> columnasCastear = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))){
    
            // Leer encabezado si existe, y crear columnas
            if (tieneEncabezado && (linea = br.readLine()) != null) {
                String[] encabezadosArray = linea.split(delimitador);
                for (String encabezado : encabezadosArray) {
                    ArrayCelda columna = new ArrayCelda(encabezado); // Creo arreglos que simulen las columnas para almecenar celdas, hasta tener la verificaciones de tipos hecha. Luego crearemos cada columna según el tipo correspondiente.
                    columna.asignarNombre(encabezado);
                    this.encabezados.add(encabezado);
                    columnasCastear.add(columna);
                    //columnas.add(columna);
                }
            }
        
            // Leer datos y crear columnas si no hay encabezado
            while ((linea = br.readLine()) != null) {
                String[] valores = linea.split(delimitador);
        
                // Crear columnas si no se crearon con encabezado
                if (!(tieneEncabezado)) {
                    for (int i = 0; i < valores.length; i++) {
                        ArrayCelda columna = new ArrayCelda("Columna" + i);
                        this.encabezados.add("Columna" + i);
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
        
                // Asignar cada valor a la columna y fila correspondiente
                Fila fila = new Fila("Fila" + (filas.size() + 1));
                for (int i = 0; i < valores.length; i++) {
                    Celda<?> celda = this.inferirTipoDeDato(valores[i]);
                    columnasCastear.get(i).agregarCelda(celda);
                    fila.agregarCelda(celda);
                }
                this.filas.add(fila);
            }
        }

        for (ArrayCelda columna : columnasCastear) {
            AutoCasteoColumna(columna);
            int indiceCol = this.obtenerColumnas().indexOf(columna);
            if (!(esConsistente(this.obtenerColumnas().get(indiceCol)))){
                reemplazarIncosistencias(this.obtenerColumnas().get(indiceCol));
            }
        }
    }

    
    public void agregarFila(Fila fila){
        this.filas.add(fila);
    }

    public Tabla obtenerFila(int indice){
        actualizarFilas();
        Tabla nuevaTabla = new Tabla();
        Fila nuevaFila = (Fila) filas.get(indice).copiaProfunda();
        nuevaTabla.agregarFila(nuevaFila);
        return nuevaTabla;
    }

    public Tabla obtenerFila(String etiqueta){
        Fila fila = new Fila(etiqueta);
        int indiceColumna = 0;
        for (Columna columna : this.columnas){
        fila.agregarCelda(columna.obtenerCeldas().get(indiceColumna));
        indiceColumna++;
        }
        
        Tabla nuevaTabla = new Tabla();
        nuevaTabla.encabezados = this.encabezados;
        nuevaTabla.obtenerFilas().add(fila);
        
        for (Celda celda : fila.obtenerCeldas()){
        nuevaTabla.obtenerColumnas().get(indiceColumna).agregarCelda(celda);
        }
        
        return nuevaTabla;
    }

    public void eliminarFila(int indiceFila){
        this.filas.remove(indiceFila);
    }
    
    public void eliminarFila(String etiqueta){
        actualizarFilas();
        int indiceFila = this.obtenerIndiceDeFila(etiqueta);
        eliminarFila(indiceFila);
    }

    public int obtenerIndiceDeFila(String etiqueta){
        actualizarFilas();
        for(Fila fila : this.filas){
            if(fila.obtenerNombre().equals(etiqueta)){
                return this.filas.indexOf(fila);
            }
        }
        throw new IllegalArgumentException("No encontrada");
    }



    public void agregarColumna(Columna columna){
        this.columnas.add(columna);
    }

    public void insertarColumna(Tabla columna){
        if (columna.obtenerColumnas().size() == 1)
        {
            this.columnas.addAll(columna.obtenerColumnas());
        }
        else {
            throw new IllegalArgumentException("La tabla a insertar debe tener una sola columna.");
        }
    }

    public void insertarColumna(List<Object> columna){
        //CONTINUAR
    }

    public void eliminarColumna(int indiceColumna){
        this.columnas.remove(indiceColumna);
    }

    public void eliminarColumna(String nombre){
        int indiceColumna = this.obtenerIndiceDeColumna(nombre);
        eliminarColumna(indiceColumna);
    }

    

    public Tabla filtrar() {
        // Pendiente
        return null;
    }

    public void ordenarPorColumnas(List<String> nombresColumnas, boolean ascendente) {
        Comparator<Fila> comparator = (fila1, fila2) -> {
            for (String nombreColumna : nombresColumnas) {
                int indiceColumna = this.obtenerIndiceDeColumna(nombreColumna);
                if (indiceColumna == -1) {
                    throw new IllegalArgumentException("Columna " + nombreColumna + " no encontrada.");
                }

                if ((fila1.obtenerCelda(indiceColumna) instanceof CeldaNA && fila2.obtenerCelda(indiceColumna) instanceof CeldaNA)) {
                    continue; 
                } else if (fila1.obtenerCelda(indiceColumna) instanceof CeldaNA) {
                    return 1; 
                } else if (fila2.obtenerCelda(indiceColumna) instanceof CeldaNA) {
                    return -1; 
                }

                Comparable valor1 = (Comparable) fila1.obtenerCelda(indiceColumna).obtenerValor();
                Comparable valor2 = (Comparable) fila2.obtenerCelda(indiceColumna).obtenerValor();

                int resultadoComparacion = valor1.compareTo(valor2);
                if (resultadoComparacion != 0) {
                    return ascendente ? resultadoComparacion : -resultadoComparacion;
                }
            }
            return 0;
        };
    
        Collections.sort(this.filas, comparator);

        for (int i = 0; i < columnas.size(); i++) {
            Columna columna = columnas.get(i);
            List<Celda<?>> celdasOrdenadas = new ArrayList<>();
            for (Fila fila : this.filas) {
                celdasOrdenadas.add(fila.obtenerCelda(i));
            }
            columna.establecerCeldas(celdasOrdenadas);  
        }
    }
    
    // Método auxiliar para obtener el índice de una columna según su nombre
    private int obtenerIndiceDeColumna(String nombreColumna) {
        for (int i = 0; i < this.encabezados.size(); i++) {
            if (this.encabezados.get(i).equals(nombreColumna)) {
                return i;
            }  
        }
        return -1;
    }

    public Tabla obtenerColumna(int indice){
        Tabla nuevaTabla = new Tabla();
        nuevaTabla.agregarColumna(this.columnas.get(indice));
        return nuevaTabla;
    }

    public Tabla obtenerColumna(String etiqueta){
        int indice = obtenerIndiceDeColumna(etiqueta);
        return obtenerColumna(indice);
    }

    public <T> void definirValor(int indiceColumna, int indiceFila, T valor){
        actualizarFilas();
        if (this.columnas.get(indiceColumna) instanceof ColumnaNumber && valor instanceof Number){
           CeldaNumber celda =(CeldaNumber)this.columnas.get(indiceColumna).obtenerCeldas().get(indiceFila);
           celda.definirValor((Number)valor);
        }
        else if (this.columnas.get(indiceColumna) instanceof ColumnaBoolean && valor instanceof Boolean){
           CeldaBoolean celda =(CeldaBoolean)this.columnas.get(indiceColumna).obtenerCeldas().get(indiceFila);
           celda.definirValor((Boolean)valor);
        }
        else if (this.columnas.get(indiceColumna) instanceof ColumnaString && valor instanceof String){
           CeldaString celda =(CeldaString)this.columnas.get(indiceColumna).obtenerCeldas().get(indiceFila);
           celda.definirValor((String)valor);
        }
        else{
            throw new IllegalArgumentException("El tipo de dato no coincide con el tipo de la celda.");
        }
    }

    public void definirComoNA(int indiceColumna, int indiceFila){
        this.columnas.get(indiceColumna).obtenerCeldas().set(indiceFila, new CeldaNA());
    }

    

    public Tabla concatenar(Tabla tabla1, Tabla tabla2){
        Tabla copiaTabla1 = tabla1.copiaProfunda();
        Tabla copiaTabla2 = tabla2.copiaProfunda();
        Tabla concatenada = copiaTabla1.copiaProfunda();

        if (copiaTabla1.obtenerColumnas().size() != copiaTabla2.obtenerColumnas().size()){
            //throw new Excepción nuestra ("mensaje");
        }

        if (!(copiaTabla1.obtenerEncabezados().equals(copiaTabla2.obtenerEncabezados()))){
            //throw new Excepción nuestra ("mensaje");
        }

        for (Columna columna : copiaTabla2.obtenerColumnas()){
            int i = copiaTabla1.obtenerColumnas().indexOf(columna);
            for (Celda celda : copiaTabla2.obtenerColumnas().get(i).obtenerCeldas()){
                Celda copiaCelda = celda.copiaProfunda();
                concatenada.obtenerColumnas().get(i).agregarCelda(copiaCelda);
            }
        }

        for (Fila fila : copiaTabla2.obtenerFilas()){
            //Como uso la copia profunda de tabla2, no hace falta crear la copia de la fila, ya está copiada en profundidad.
            concatenada.filas.add(fila);
        }

        return concatenada;
    }

    private String formatearFilasParaImprimir(List<Fila> filas) {
        // Agregar Columnas al print

        if (filas.isEmpty()) {
            return ""; // Si no hay filas, devuelve una cadena vacía
        }
    
        int numColumnas = columnas.size();
        int[] maxAnchoPorColumna = new int[numColumnas];
    
        // Calcular el ancho máximo de cada columna en las filas proporcionadas
        for (Fila fila : filas) {
            for (int j = 0; j < numColumnas; j++) {
                String valor = fila.obtenerValor(j) != null ? fila.obtenerValor(j).toString() : "NA";
                maxAnchoPorColumna[j] = Math.max(maxAnchoPorColumna[j], valor.length());
            }
        }
    
        // Construir el string formateado con columnas alineadas
        StringBuilder sb = new StringBuilder();
        for (Fila fila : filas) {
            sb.append("| ");
            for (int j = 0; j < numColumnas; j++) {
                String valor = fila.obtenerValor(j) != null ? fila.obtenerValor(j).toString() : "NA";
                sb.append(String.format("%-" + maxAnchoPorColumna[j] + "s", valor)).append(" | ");
            }
            sb.append("\n"); // Salto de línea después de cada fila
        }
        
        return sb.toString();
    }
    

    public void info() {
        System.out.println("Información de la Tabla:");
        System.out.println("Cantidad de filas: " + filas.size());
        System.out.println("Cantidad de columnas: " + columnas.size());
        
        System.out.println("\nColumnas:");
        for (Columna columna : columnas) {
            if (columna instanceof ColumnaString){
                System.out.println("Etiqueta: " + columna.obtenerNombre()+ ", Tipo de dato: String");
            }
            else if (columna instanceof ColumnaNumber) {
                System.out.println("Etiqueta: " + columna.obtenerNombre()+ ", Tipo de dato: Numérico");
            }
            else if (columna instanceof ColumnaBoolean) {
                System.out.println("Etiqueta: " + columna.obtenerNombre()+ ", Tipo de dato: Booleano");
            }
            else if (columna instanceof ColumnaNA){
                System.out.println("Etiqueta: " + columna.obtenerNombre()+ ", Tipo de dato: NA");
            }
        }
    }

    // Método para mostrar las primeras n filas de la tabla
    public void head(int n) {
        try {
            VerificadorDeRango.verificarLimite(n, filas.size());
            List<Fila> filasParaMostrar = filas.subList(0, n);
            System.out.println("Primeras " + n + " filas de la Tabla:");
            System.out.print(formatearFilasParaImprimir(filasParaMostrar));
        } catch (IndiceFueraDeRangoExcepcion e) {
            System.out.println("Error en head: Índice fuera de rango - " + e.obtenerMensaje());
        }
    }

    // Método para mostrar las últimas n filas de la tabla
    public void tail(int n) {
        try {
            VerificadorDeRango.verificarLimite(n, filas.size());
            List<Fila> filasParaMostrar = filas.subList(Math.max(0, filas.size() - n), filas.size());
            System.out.println("Últimas " + n + " filas de la Tabla:");
            System.out.print(formatearFilasParaImprimir(filasParaMostrar));
        } catch (IndiceFueraDeRangoExcepcion e) {
            System.out.println("Error en tail: Índice fuera de rango - " + e.obtenerMensaje());
        }
    }

    // Método para mostrar un rango de filas entre los índices start y end
    public void seleccionar(int start, int end, List<String> columnas) {
        try {
            VerificadorDeRango.verificarRango(start, end, filas.size());
            
            // Obtener las filas en el rango especificado
            List<Fila> filasParaMostrar = filas.subList(start, Math.min(end, filas.size()));
            
            System.out.println("Filas de " + start + " a " + end + " de la Tabla:");
            System.out.print(formatearFilasParaImprimir(filasParaMostrar));
        } catch (IndiceFueraDeRangoExcepcion e) {
            System.out.println("Error en mostrarRango: índice fuera de rango - " + e.obtenerMensaje());
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        int[] maxWidths = new int[columnas.size()];

        // Máximo de caracteres
        for (int col = 0; col < columnas.size(); col++) {
            int maxWidth = columnas.get(col).obtenerNombre().length();
            for (Fila fila : filas) {
                String cellValue = fila.obtenerValor(col) != null ? fila.obtenerValor(col).toString() : "null";
                maxWidth = Math.max(maxWidth, cellValue.length());
            }
            maxWidths[col] = maxWidth;
        }

        // Encabezado
        if (!columnas.isEmpty()) {
            for (int col = 0; col < columnas.size(); col++) {
                String nombreColumna = columnas.get(col).obtenerNombre();
                builder.append(String.format("%-" + maxWidths[col] + "s", nombreColumna)).append(" | ");
            }
            builder.append("\n");
        }

        // Filas
        for (Fila fila : filas) {
            for (int col = 0; col < columnas.size(); col++) {
                String cellValue = fila.obtenerValor(col) != null ? fila.obtenerValor(col).toString() : "null";
                builder.append(String.format("%-" + maxWidths[col] + "s", cellValue)).append(" | ");
            }
            builder.append("\n");
        }

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

    public Tabla copiaProfunda(){ // Posible método para abarcar verificaciones de 
        Tabla copia = new Tabla();
        for (int i=0; i <= this.obtenerColumnas().size(); i++){
            if (this.obtenerColumnas().get(i) instanceof ColumnaBoolean){
                ColumnaBoolean nuevBoolean = (ColumnaBoolean) this.obtenerColumnas().get(i).copiaProfunda();
                copia.obtenerColumnas().add(nuevBoolean);
            }
            else if (this.obtenerColumnas().get(i) instanceof ColumnaNumber){
                ColumnaNumber nuevNumber = (ColumnaNumber) this.obtenerColumnas().get(i).copiaProfunda();
                copia.obtenerColumnas().add(nuevNumber);
            }
            else if (this.obtenerColumnas().get(i) instanceof ColumnaString){
                ColumnaString nuevString= (ColumnaString) this.obtenerColumnas().get(i).copiaProfunda();
                copia.obtenerColumnas().add(nuevString);
            }
            else if (this.obtenerColumnas().get(i) instanceof ColumnaNA){
                ColumnaNA nuevNA = (ColumnaNA) this.obtenerColumnas().get(i).copiaProfunda();
                copia.obtenerColumnas().add(nuevNA);
            }
        }
        
        for (Fila fila : this.filas){
            Fila copiaProf = (Fila) fila.copiaProfunda();
            copia.obtenerFilas().add(copiaProf);

        }
        return copia;
    }

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
        if (valorCelda.equals("") || valorCelda.equalsIgnoreCase("NA") || 
        valorCelda == null || valorCelda.equalsIgnoreCase("N/A")){ // infintas opciones mas
        return new CeldaNA();
        }
        
        return new CeldaString(valorCelda);            
    }

    private boolean esConsistente(Columna columna){
        if (columna instanceof ColumnaNA){
            for (Celda celda : columna.obtenerCeldas()){
                if (!(celda instanceof CeldaNA)){
                    return false;
                }
            }
        }

        int i = 1;
        for (Celda celda : columna.obtenerCeldas()){
            if ((!(celda instanceof CeldaNA)) && (celda.getClass() != columna.obtenerCeldas().get(i-1).getClass())){
                return false;
            }
            i++;
        }
        return true;
    }

    private void reemplazarIncosistencias(Columna columna){
        int indiceColumna = this.obtenerColumnas().indexOf(columna);
        if (columna instanceof ColumnaNA){
            for ( Celda celda : columna.obtenerCeldas()){
                if (!(celda instanceof CeldaNA)){
                    CeldaNA nuevaCeldaNA = new CeldaNA();
                    this.obtenerColumnas().get(indiceColumna).obtenerCeldas().set(indiceColumna, nuevaCeldaNA);
                }
            }
        }
        else if (columna instanceof ColumnaString){
            for (Celda celda : columna.obtenerCeldas()){
                if ((!(celda instanceof CeldaString)) && (!(celda instanceof CeldaNA))){
                    CeldaNA nuevaCeldaNA = new CeldaNA();
                    this.obtenerColumnas().get(indiceColumna).obtenerCeldas().set(indiceColumna, nuevaCeldaNA);
                }
            }
        }
        else if (columna instanceof ColumnaBoolean){
            for (Celda celda : columna.obtenerCeldas()){
                if ((!(celda instanceof CeldaBoolean)) && (!(celda instanceof CeldaNA))){
                    CeldaNA nuevaCeldaNA = new CeldaNA();
                    this.obtenerColumnas().get(indiceColumna).obtenerCeldas().set(indiceColumna, nuevaCeldaNA);
                }
            }
        }
        else if (columna instanceof ColumnaNumber){
            for (Celda celda : columna.obtenerCeldas()){
                if ((!(celda instanceof CeldaNumber)) && (!(celda instanceof CeldaNA))){
                    CeldaNA nuevaCeldaNA = new CeldaNA();
                    this.obtenerColumnas().get(indiceColumna).obtenerCeldas().set(indiceColumna, nuevaCeldaNA);
                }
            }
        }
    }

    // @Override
    // public void imputarNA(Number nuevaCelda) {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'imputarNA'");
    // }

    // @Override
    // public void imputarNA(Boolean nuevaCelda) {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'imputarNA'");
    // }

    // @Override
    // public void imputarNA(String nuevaCelda) {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'imputarNA'");
    // }


}