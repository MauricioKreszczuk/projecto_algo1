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
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
//Carga de CSV
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;

import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Tabla{
    private List<Fila> filas;
    private List<Columna> columnas;
    private List<String> encabezados;
    private String nombreIndex;


/// Construcores
    public Tabla(){
        this.filas = new ArrayList<>();
        this.columnas = new ArrayList<>();
        this.encabezados = new ArrayList<>();
        this.nombreIndex = "";
    }

    public Tabla(List<List<Object>> listaDeFilas, Boolean tieneEncabezado){
        this.filas = new ArrayList<>();
        this.columnas = new ArrayList<>();
        this.encabezados = new ArrayList<>();
        this.nombreIndex = "";
        
        if (listaDeFilas.size() == 0){
            throw new ExcepcionConstructorConSecuenciaVacia("El constructor recibió una secuencia lineal vacía, no se puede crear una tabla a partir de una estructura vacía");
        }

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
                throw new ExcepcionIncositenciaDeRango("La fila " + i + " no coincide con el número de columnas.");
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
        actualizarFilas();
    }

    public Tabla(Object[][] matriz, Boolean tieneEncabezado) { 
        this.filas = new ArrayList<>();
        this.columnas = new ArrayList<>();
        this.encabezados = new ArrayList<>();
        this.nombreIndex = "";

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
                throw new ExcepcionIncositenciaDeRango("La fila " + i + " no coincide con el número de columnas.");
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
        actualizarFilas();

    }

    public Tabla(String path, boolean tieneEncabezado) throws IOException {
        this.filas = new ArrayList<>();
        this.columnas = new ArrayList<>();
        this.encabezados = new ArrayList<>();
        this.nombreIndex = "";

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
        
                // Crear columnas dinámicamente si no hay encabezado
                if (!tieneEncabezado && columnasCastear.isEmpty()) {
                    for (int i = 0; i < valores.length; i++) {
                        ArrayCelda columna = new ArrayCelda(""+ i);
                        this.encabezados.add(""+i);
                        columnasCastear.add(columna);
                    }
                }
        
                // Verificar que el número de valores coincida con el número de columnas
                if (valores.length != columnasCastear.size()) {
                    System.out.println(columnas.size());
                    System.out.println(valores.length);
                    throw new ExcepcionIncositenciaDeRango("El número de valores no coincide con el número de columnas.");
                }
        
                // Asignar cada valor a la columna y fila correspondiente
                Fila fila = new Fila(String.valueOf( filas.size() ));
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
        this.filas = new ArrayList<>();
        this.columnas = new ArrayList<>();
        this.encabezados = new ArrayList<>();
        this.nombreIndex = "";

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
                    } 
                }
        
                // Verificar que el número de valores coincida con el número de columnas
                if (valores.length != columnasCastear.size()) {
                    System.out.println(columnas.size());
                    System.out.println(valores.length);
                    throw new ExcepcionIncositenciaDeRango("El número de valores no coincide con el número de columnas.");
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




// Getters
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

    @SuppressWarnings("unchecked")
    public <T> T obtenerValor(int indiceColumna, int IndiceFila) throws IndiceFueraDeRangoExcepcion{
        if (indiceColumna < 0 || indiceColumna >= columnas.size()) {
            throw new IndiceFueraDeRangoExcepcion("Índice de columna fuera de rango.");
        }
        if (IndiceFila < 0 || IndiceFila >= columnas.get(indiceColumna).obtenerTamaño()) {
            throw new IndiceFueraDeRangoExcepcion("Índice de fila fuera de rango.");
        }
        return (T) columnas.get(indiceColumna).obtenerValor(IndiceFila);
    }

    @SuppressWarnings("unchecked")
    public <T> T obtenerValor(String EtiquetaColumna, String EtiquetaFila) throws IndiceFueraDeRangoExcepcion{
        int indiceColumna = obtenerIndiceDeColumna(EtiquetaColumna);
        int indiceFila = obtenerIndiceDeFila(EtiquetaFila);

        if (indiceColumna < 0 || indiceColumna >= this.columnas.get(indiceColumna).obtenerTamaño()){
            throw new IndiceFueraDeRangoExcepcion("Índice de columna fuera de rango");
        }

        if (indiceFila < 0 || indiceFila >= this.filas.get(indiceFila).obtenerTamaño()){
            throw new IndiceFueraDeRangoExcepcion("Índice de fila fuera de rango");
        }

        return (T) obtenerValor(indiceColumna, indiceFila);
    }

    public <T> String obtenerValorString(int indiceColumna, int IndiceFila) throws IndiceFueraDeRangoExcepcion{

        if (indiceColumna < 0 || indiceColumna >= this.columnas.get(indiceColumna).obtenerTamaño()){
            throw new IndiceFueraDeRangoExcepcion("Índice de columna fuera de rango");
        }

        if (IndiceFila < 0 || IndiceFila >= this.filas.get(IndiceFila).obtenerTamaño()){
            throw new IndiceFueraDeRangoExcepcion("Índice de fila fuera de rango");
        }

        if(obtenerValor(indiceColumna, IndiceFila) instanceof String){
            return (String) obtenerValor(indiceColumna, IndiceFila);
        }
        T valor = obtenerValor(indiceColumna, IndiceFila);
        return String.valueOf(String.valueOf(valor));
    }

    public <T> String obtenerValorString(String nombreColumna, String nombreFila) throws IndiceFueraDeRangoExcepcion{
        int indiceColumna = obtenerIndiceDeColumna(nombreColumna);
        int indiceFila = obtenerIndiceDeFila(nombreFila);

        if (indiceColumna < 0 || indiceColumna >= this.columnas.get(indiceColumna).obtenerTamaño()){
            throw new IndiceFueraDeRangoExcepcion("Índice de columna fuera de rango");
        }

        if (indiceFila < 0 || indiceFila >= this.filas.get(indiceFila).obtenerTamaño()){
            throw new IndiceFueraDeRangoExcepcion("Índice de fila fuera de rango");
        }

        return obtenerValorString(indiceColumna, indiceFila);
    }
    
    public List<String> obtenerEncabezados(){
        actualizarEncabezados();
        return this.encabezados;
    }

    public List<String> obtenerLabels(){
        List<String> laList = new ArrayList<String>();
        for(Fila fila : this.filas){
            laList.add(fila.obtenerNombre());
        }
        return laList;
    }

    public int obtenerIndiceDeFila(String etiqueta){
        actualizarFilas();
        for(Fila fila : this.filas){
            if(fila.obtenerNombre().equals(etiqueta)){
                return this.filas.indexOf(fila);
            }
        }
        throw new IndiceFueraDeRangoExcepcion("Fila no encontrada");
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

    public int numeroFilas(){
        return this.columnas.get(0).obtenerTamaño();
    }
    
    public int numeroColumnas(){
        return this.columnas.size();
    }


//Setters
    public void asignarLabels(List<String> labels){
        if (labels.size()!= this.filas.size()) {
            throw new ExcepcionIncositenciaDeRango("El número de labels no coincide con el número de filas.");
        }
        for(Fila fila : this.filas){
            fila.asignarNombre(labels.get(this.filas.indexOf(fila)));
        }
    }

    public void asignarComoIndex(int indiceColumna) throws IndiceFueraDeRangoExcepcion{

        if (indiceColumna < 0 || indiceColumna >= this.columnas.get(indiceColumna).obtenerTamaño()){
            throw new IndiceFueraDeRangoExcepcion("Índice de columna fuera de rango");
        }
        
        this.nombreIndex = String.valueOf(columnas.get(indiceColumna).obtenerNombre());
        for (int indiceFila = 0; indiceFila < this.columnas.get(indiceColumna).obtenerTamaño(); indiceFila++){
            this.filas.get(indiceFila).asignarNombre(obtenerValorString(indiceColumna, indiceFila));
        }
        this.columnas.remove(indiceColumna);
        actualizarFilas();
        if(obtenerLabels().size() != obtenerLabels().stream().distinct().count()){
            resetearIndex();
            throw new ExcepcionEtiquetaRepetida("Error: No se permiten labels repetidos");
        }
    }

    public void asignarComoIndex(String encabezado) throws IndiceFueraDeRangoExcepcion{
        int indiceColumna = obtenerIndiceDeColumna(encabezado);

        if (indiceColumna < 0 || indiceColumna >= this.columnas.get(indiceColumna).obtenerTamaño()){
            throw new IndiceFueraDeRangoExcepcion("Índice de columna fuera de rango");
        }

        asignarComoIndex(indiceColumna);
    }

    public void crearFilasPorColumnas(List<Columna> columnas) {
        List<String> labels = obtenerLabels();
        int tamaño = columnas.get(0).obtenerTamaño();
        List<Fila> filasTemporal = new ArrayList<Fila>();
        // Iterar sobre los valores de cada columna para formar las filas
        for (int indice = 0; indice < tamaño; indice++) {
            //String etiquetaFila = etiquetas.get(indice); 
            Fila filaTemporal = new Fila("");
            if(labels.size() == tamaño){
                filaTemporal = new Fila(labels.get(indice));
            }
            else {
                filaTemporal = new Fila(String.valueOf(indice)); // Asignar nombre a la fila
            }
            for (Columna columna : columnas) {
                Celda<?> celda = columna.obtenerCeldas().get(indice); // Obtener la celda correspondiente en este índice
                filaTemporal.agregarCelda(celda); // Agregar la celda a la fila
            }
    
            // Ahora agrego la fila, cuando ya está completa
            filasTemporal.add(filaTemporal);
        }
        this.filas = filasTemporal;
    }

    public void actualizarFilas(){
        crearFilasPorColumnas(this.columnas);
    }
    
    public void actualizarEncabezados(){
        List<String> encabezados = new ArrayList<>();
        for (Columna columna : this.columnas) {
            encabezados.add(columna.obtenerNombre());
        } 
        this.encabezados = encabezados;
    }

    public void agregarFila(Fila fila){
        this.filas.add(fila);
    }

    public void eliminarFila(int indiceFila){
        this.filas.remove(indiceFila);
    }
    
    public void eliminarFila(String etiqueta){
        actualizarFilas();
        int indiceFila = this.obtenerIndiceDeFila(etiqueta);
        eliminarFila(indiceFila);
    }

    public void agregarColumna(Columna columna){
        this.columnas.add(columna);
    }

    public void insertarColumna(Tabla columna){
        if (columna.obtenerColumnas().size() == 1){
        this.columnas.addAll(columna.obtenerColumnas());
        }
        else {
            throw new IllegalArgumentException("La tabla a insertar debe tener una sola columna.");
        }
    }

    public void insertarColumna(List<Object> columna, boolean tieneEncabezado ){
        ArrayCelda nuevaColumna = new ArrayCelda("null");
        if (tieneEncabezado){
            nuevaColumna.asignarNombre(String.valueOf(columna.get(0)));
            columna.remove(0);
        }
        else{
            nuevaColumna.asignarNombre("Columna " + this.columnas.size());
        }

        for (Object cosa : columna){
            nuevaColumna.agregarCelda(inferirTipoDesdeObject(cosa)); 
        }
        this.AutoCasteoColumna(nuevaColumna);
        actualizarFilas();
    }

    public <T> void imputarNA(String nombreColumna, T valor) {
        int indiceColumna = obtenerIndiceDeColumna(nombreColumna);
        if (columnas.get(indiceColumna) instanceof ColumnaNumber){
            Number valo = Double.valueOf(String.valueOf(valor));
            ArrayCelda array = columnas.get(indiceColumna).copiaProfunda();
            array.imputarNA(valo);
            ColumnaNumber columna = new ColumnaNumber(array);
            for (int indice = 0; indice < columna.obtenerTamaño() ; indice++){
                if (columna.obtenerCeldas().get(indice) instanceof CeldaNA){
                    CeldaNumber celda = new CeldaNumber(Double.valueOf(String.valueOf(valor)));
                    columna.cambiarCelda(indice,celda);
                }
            }
            this.columnas.set(indiceColumna, columna);
        }
        else if( columnas.get(indiceColumna) instanceof ColumnaString){
            String valo = String.valueOf(valor);
            ArrayCelda array = columnas.get(indiceColumna).copiaProfunda();
            array.imputarNA(valo);
            ColumnaString columna = new ColumnaString(array);
            for (int indice = 0; indice < columna.obtenerTamaño() ; indice++){
                if (columna.obtenerCeldas().get(indice) instanceof CeldaNA){
                    CeldaString celda = new CeldaString(String.valueOf(valor));
                    columna.cambiarCelda(indice,celda);
                }
            }
            this.columnas.set(indiceColumna, columna);
        }
        else if(columnas.get(indiceColumna) instanceof ColumnaBoolean){
            Boolean valo = Boolean.valueOf(String.valueOf(valor));
            ArrayCelda array = columnas.get(indiceColumna).copiaProfunda();
            array.imputarNA(valo);
            ColumnaBoolean columna = new ColumnaBoolean(array);
            for (int indice = 0; indice < columna.obtenerTamaño() ; indice++){
                if (columna.obtenerCeldas().get(indice) instanceof CeldaNA){
                    CeldaBoolean celda = new CeldaBoolean(Boolean.valueOf(String.valueOf(valor)));
                    columna.cambiarCelda(indice,celda);
                }
            }
            this.columnas.set(indiceColumna, columna);
        }
        actualizarFilas();
    }

    public void eliminarColumna(int indiceColumna){
        this.columnas.remove(indiceColumna);
    }

    public void eliminarColumna(String nombre){
        int indiceColumna = this.obtenerIndiceDeColumna(nombre);
        eliminarColumna(indiceColumna);
    }

    public <T> void definirValor(int indiceColumna, int indiceFila, T valor) throws TipoDeDatoInvalidoExcepcion{
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
            throw new TipoDeDatoInvalidoExcepcion("El tipo de dato no coincide con el tipo de la columna");
        }
        actualizarFilas();
    }

    public void definirComoNA(int indiceColumna, int indiceFila){
        this.columnas.get(indiceColumna).obtenerCeldas().set(indiceFila, new CeldaNA());
    }

    public void reemplazarColumna(int indiceColumna, Tabla columna){
        if(columna.obtenerColumnas().size() != 0){
            throw new IllegalArgumentException("No se aceptan mas de una columna");
        }
        try {
            this.columnas.set(indiceColumna, columna.obtenerColumnas().get(0));
        }
        catch (IndexOutOfBoundsException e){
            throw new IllegalArgumentException("La cantidad de filas de no coinciden");
        }
    }

    public void or(List<Integer>... listas) {
        
        Set<Integer> conjuntoUnico = new HashSet<>();
    
        // Agregar todos los elementos de cada lista al conjunto
        for (List<Integer> lista : listas) {
            conjuntoUnico.addAll(lista);
        }
        List<Integer> indicesFiltrados = new ArrayList<>(conjuntoUnico);

        List<Columna> columnas = this.copiaProfunda().obtenerColumnas();
        this.columnas.clear();
        for (Columna col : columnas){
            Columna columnaFiltrada = new Columna<>(col.obtenerNombre());
            for (Integer indice : indicesFiltrados){
                columnaFiltrada.agregarCelda(col.obtenerCeldas().get(indice).copiaProfunda());
            }
            this.AutoCasteoColumna(columnaFiltrada);
        }
        this.actualizarFilas();        
    }

    public void and(List<Integer>... listas) {

        Set<Integer> conjuntoComun = new HashSet<>(listas[0]);

        for (int i = 1; i < listas.length; i++) {
            conjuntoComun.retainAll(listas[i]);
        }
    
        List<Integer> indicesFiltrados = new ArrayList<>(conjuntoComun);
        List<Columna> columnas = this.copiaProfunda().obtenerColumnas();
        this.columnas.clear();

        for (Columna col : columnas) {
            Columna columnaFiltrada = new Columna<>(col.obtenerNombre());
            for (Integer indice : indicesFiltrados) {
                columnaFiltrada.agregarCelda(col.obtenerCeldas().get(indice).copiaProfunda());
            }
            this.AutoCasteoColumna(columnaFiltrada);
        }
        this.actualizarFilas();
    }
    
    public List<Integer> condicion(int indiceColumna, String condicion, Object limite ){
        Predicate<Object> filtro = Tabla.condicion(condicion, limite);
        List<Integer> indicesFiltrados = Filtro.filtrar(this.obtenerColumnas().get(indiceColumna), filtro);
        return indicesFiltrados;
    }

    public List<Integer> condicion(String nombreColumna, String condicion, Object limite){
        int indiceColumna = obtenerIndiceDeColumna(nombreColumna);
        return this.condicion(indiceColumna, condicion, limite);
    }

    public void aNumerica (int indiceColumna){
        ColumnaNumber columna = Casteo.aNumber(this.obtenerColumnas().get(indiceColumna));
        this.columnas.set(indiceColumna,columna);
        actualizarFilas();
    }

    public void aNumerica(String nombreColumna){
        int indiceColumna = obtenerIndiceDeColumna(nombreColumna);
        this.aNumerica(indiceColumna);
    }

    public void aBooleana(int indiceColumna){
        ColumnaBoolean columna = Casteo.aBoolean(this.obtenerColumnas().get(indiceColumna));
        this.columnas.set(indiceColumna, columna);
        actualizarFilas();
    }

    public void aBooleana(String nombreColumna){
        int indiceColumna = obtenerIndiceDeColumna(nombreColumna);
        this.aBooleana(indiceColumna);
    }

    public void aString(int indiceColumna){
        ColumnaString columna = Casteo.aString(this.obtenerColumnas().get(indiceColumna));
        this.columnas.set(indiceColumna, columna);
        actualizarFilas();
    }

    public void aString(String nombreColumna){
        int indiceColumna = obtenerIndiceDeColumna(nombreColumna);
        this.aString(indiceColumna);
    }

    public Tabla obtenerColumnas(int... indices) {
        Tabla nuevaTabla = new Tabla();
        for (int indice : indices) {
            if (indice >= 0 && indice < this.columnas.size()) {
                nuevaTabla.agregarColumna(this.columnas.get(indice));
            } else {
                throw new IndexOutOfBoundsException("Índice de columna fuera de rango: " + indice);
            }
        }
        nuevaTabla.actualizarFilas();
        return nuevaTabla;
        
    }
    
    public Tabla obtenerColumnas(String... etiquetas) {
        Tabla nuevaTabla = new Tabla();
        for (String etiqueta : etiquetas) {
            int indice = obtenerIndiceDeColumna(etiqueta);
            if (indice != -1) {
                nuevaTabla.agregarColumna(this.columnas.get(indice));
            } else {
                throw new IllegalArgumentException("La columna con etiqueta '" + etiqueta + "' no existe.");
            }
        }
        nuevaTabla.actualizarFilas();
        return nuevaTabla;
    }



/////////////////////////////////////////////////

    public void resetearIndex(){
        actualizarFilas();
        this.nombreIndex = "";
        for(Fila fila : this.filas){
            fila.asignarNombre(String.valueOf(filas.indexOf(fila)));
        }
        actualizarFilas();
    }

    public void testeo() {
        for (Columna columna : this.columnas) {
            System.out.println("\nColumna: " + columna.obtenerNombre() + ", Tipo: " + columna.getClass());
            for (Celda<?> celda : columna.obtenerCeldas()) {
                System.out.println(celda + ", Tipo: " + celda.getClass());
            }
        }
    }
    
    public void guardarComoCSV(String rutaArchivo) throws IOException {
        try (FileWriter writer = new FileWriter(rutaArchivo)) {
            // Escribir encabezados separados por comas
            for (int i = 0; i < encabezados.size(); i++) {
                writer.append(encabezados.get(i));
                if (i < encabezados.size() - 1) {
                    writer.append(",");
                }
            }
            writer.append("\n");
    
            // Escribir datos de las filas
            for (Fila fila : filas) {
                List<Celda<?>> celdas = fila.obtenerCeldas();
                for (int j = 0; j < celdas.size(); j++) {
                    writer.append(celdas.get(j).toString());
                    if (j < celdas.size() - 1) {
                        writer.append(",");
                    }
                }
                writer.append("\n");
            }
        } catch (ExcepcionErrorDeEscrituraCSV e) {
            System.err.println("Error al escribir en el archivo CSV: " + e.getMessage());
            throw e;
        }
    }

    @SuppressWarnings("unchecked")
    public void ordenarPorColumnas(List<String> nombresColumnas, boolean ascendente) {
        Comparator<Fila> comparator = (fila1, fila2) -> {
            for (String nombreColumna : nombresColumnas) {
                int indiceColumna = this.obtenerIndiceDeColumna(nombreColumna);
                if (indiceColumna < 0) {
                    throw new IndiceFueraDeRangoExcepcion("Columna " + nombreColumna + " no encontrada.");
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
    
    public static Tabla concatenar(Tabla tabla1, Tabla tabla2){
        Tabla copiaTabla1 = tabla1.copiaProfunda();
        Tabla copiaTabla2 = tabla2.copiaProfunda();
        Tabla concatenada = new Tabla();

        if (copiaTabla1.numeroColumnas() != copiaTabla2.numeroColumnas()){
            throw new ExcepcionIncositenciaDeRango("Las tablas deben tener el mismo número de columnas.");
        }
        else if (copiaTabla1.numeroFilas() != copiaTabla2.numeroFilas()){
            throw new ExcepcionIncositenciaDeRango("Las tablas deben tener el mismo número de filas.");
        }
        for(int ncolumna = 0; ncolumna < copiaTabla1.numeroColumnas(); ncolumna++){
            if(copiaTabla1.obtenerColumnas().get(ncolumna).getClass() != copiaTabla2.obtenerColumnas().get(ncolumna).getClass()){
                throw new TipoDeDatoInvalidoExcepcion("Las columnas deben ser del mismo tipo");
            }
            ArrayCelda array = new ArrayCelda(copiaTabla1.obtenerColumnas().get(ncolumna).obtenerNombre());
            for(int i = 0; i < copiaTabla1.numeroFilas(); i++){
                array.agregarCelda(copiaTabla1.obtenerColumnas().get(ncolumna).obtenerCeldas().get(i));                    
            }
            for(int i = 0; i < copiaTabla2.numeroFilas(); i++){
                array.agregarCelda(copiaTabla2.obtenerColumnas().get(ncolumna).obtenerCeldas().get(i));                    
            }
            concatenada.AutoCasteoColumna(array);
        }
        concatenada.actualizarFilas();
        concatenada.resetearIndex();
        return concatenada;
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

    public void head(int n) {
        try {
            VerificadorDeRango.verificarLimite(n, filas.size());
            List<Fila> filasParaMostrar = filas.subList(0, n);
            System.out.println("Primeras " + n + " filas de la Tabla:");
            System.out.print(formatearFilasParaImprimir(filasParaMostrar, this.encabezados)); 
        } catch (IndiceFueraDeRangoExcepcion e) {
            System.out.println("Error en head: Índice fuera de rango - " + e.obtenerMensaje());
        }
    }

    public void tail(int n) {
        try {
            VerificadorDeRango.verificarLimite(n, filas.size());
            List<Fila> filasParaMostrar = filas.subList(Math.max(0, filas.size() - n), filas.size());
            System.out.println("Últimas " + n + " filas de la Tabla:");
            System.out.print(formatearFilasParaImprimir(filasParaMostrar, encabezados));
        } catch (IndiceFueraDeRangoExcepcion e) {
            System.out.println("Error en tail: Índice fuera de rango - " + e.obtenerMensaje());
        }
    }

    public void seleccionar(int comienzoRango, int finalRango, List<String> nombresColumnas) {
        try {
            VerificadorDeRango.verificarRango(comienzoRango, finalRango, filas.size());
            List<Fila> filasParaMostrar = filas.subList(comienzoRango, Math.min(finalRango, filas.size()));
            System.out.print(formatearFilasParaImprimir(filasParaMostrar, nombresColumnas));

        } catch (IndiceFueraDeRangoExcepcion e) {
            System.out.println("Error en mostrarRango: índice fuera de rango - " + e.obtenerMensaje());
        }
    }

    public void visualizar(int maxColumnas, int maxFilas, int maxCaracteres) {
        // Hacer excepcion de si los parámetros se pasan del máximo de columnas o filas.
        if (maxColumnas == 0) {
            maxColumnas = columnas.size();
        }
        if (maxFilas == 0) {
            maxFilas = filas.size(); // Asegúrate de obtener el número de filas si maxFilas es 0
        }
    
        StringBuilder builder = new StringBuilder();
    
        // Calcular el ancho máximo para cada columna de datos
        int[] maxAnchosPorColumna = new int[maxColumnas];
        
        // Primero, calcular los anchos máximos para cada columna
        for (int col = 0; col < maxColumnas; col++) {
            int maxAnchoPorColumna = columnas.get(col).obtenerNombre().length();
            for (int fila = 0; fila < maxFilas; fila++) {
                String valor = this.filas.get(fila).obtenerValor(col) != null ? this.filas.get(fila).obtenerValor(col).toString() : "null";
                maxAnchoPorColumna = Math.max(maxAnchoPorColumna, valor.length());
            }
            maxAnchoPorColumna = Math.min(maxAnchoPorColumna, maxCaracteres);
            maxAnchosPorColumna[col] = maxAnchoPorColumna;
        }
    
        // Calcular el ancho para la columna de "Filas" (nombreIndex)
        int maxAnchoFila = getMaxLabelWidth();
    
        // Encabezado (incluyendo nombreIndex)
        builder.append("| ");
        builder.append(String.format("%-" + maxAnchoFila + "s", nombreIndex)).append(" | "); // Agregar nombreIndex
    
        // Agregar encabezados de las columnas
        for (int col = 0; col < maxColumnas; col++) {
            String columnaAjustada = columnas.get(col).obtenerNombre().length() > maxAnchosPorColumna[col] ? columnas.get(col).obtenerNombre().substring(0, maxAnchosPorColumna[col]) : columnas.get(col).obtenerNombre();
            builder.append(String.format("%-" + maxAnchosPorColumna[col] + "s", columnaAjustada)).append(" | ");
        }
        builder.append("\n");
    
        // Línea separadora
        builder.append("| ");
        builder.append("-".repeat(maxAnchoFila));  // Espacio para la columna "Filas" (sin +2)
        builder.append(" |");  // Separador de la primera columna

        // Agregar los guiones para las otras columnas
        for (int j = 0; j < maxColumnas; j++) {
            builder.append("-".repeat(maxAnchosPorColumna[j] + 2)); // Agregar separador para cada columna
            if (j < maxColumnas - 1) {
                builder.append("|"); // Añadir separador entre columnas
            }
        }
        builder.append("|\n");

    
        // Filas
        for (int fila = 0; fila < maxFilas; fila++) {
            builder.append("| ");
            
            // Imprimir nombre de la fila (como en nombreIndex)
            builder.append(String.format("%-" + maxAnchoFila + "s", filas.get(fila).obtenerNombre())).append(" | ");
    
            // Valores de las celdas
            for (int col = 0; col < maxColumnas; col++) {
                String valor = this.filas.get(fila).obtenerValor(col) != null ? this.filas.get(fila).obtenerValor(col).toString() : "null";
                String valorAjustado = valor.length() > maxAnchosPorColumna[col] ? valor.substring(0, maxAnchosPorColumna[col]) : valor;
                builder.append(String.format("%-" + maxAnchosPorColumna[col] + "s", valorAjustado)).append(" | ");
            }
            builder.append("\n");
        }
    
        // Mostrar el resultado
        System.out.println(builder.toString());
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
    
        // Obtener el máximo ancho para cada columna, incluyendo la columna "Filas"
        int[] maxAnchosPorColumna = new int[columnas.size() + 1]; // +1 para la columna "Filas"
    
        // Cálculo del ancho máximo para la columna "Filas" (utilizando nombreIndex)
        maxAnchosPorColumna[0] = getMaxLabelWidth(); // Ancho de la columna "Filas"
        
        // Cálculo del ancho máximo para las demás columnas
        for (int col = 0; col < columnas.size(); col++) {
            int maxAnchoPorColumna = columnas.get(col).obtenerNombre().length();
            for (Fila fila : filas) {
                String valor = fila.obtenerValor(col) != null ? fila.obtenerValor(col).toString() : "null";
                maxAnchoPorColumna = Math.max(maxAnchoPorColumna, valor.length());
            }
            maxAnchosPorColumna[col + 1] = maxAnchoPorColumna; // Ajustar para la columna normal
        }
    
        // Encabezado (con la columna de nombres de fila)
        builder.append("| ");
        builder.append(String.format("%-" + maxAnchosPorColumna[0] + "s", nombreIndex)).append(" | "); // Columna de nombres de fila
        for (int col = 0; col < columnas.size(); col++) {
            String nombreColumna = columnas.get(col).obtenerNombre();
            builder.append(String.format("%-" + maxAnchosPorColumna[col + 1] + "s", nombreColumna)).append(" | ");
        }
        builder.append("\n");
    
        // Línea separadora
        builder.append("|");  // Empezamos con el separador para la columna "Filas"
        for (int j = 0; j < maxAnchosPorColumna.length; j++) {
            // Agregar los guiones para cada columna más un espacio adicional ( +2) para los márgenes
            builder.append("-".repeat(maxAnchosPorColumna[j] + 2));
            if (j < maxAnchosPorColumna.length - 1) {
                builder.append("|"); // Agregar "|" entre las columnas, no al final
            }
        }
        builder.append("|\n");  // Añadimos el último "|" para cerrar la línea separadora
    
        // Filas (incluyendo el nombre de la fila a la izquierda)
        for (int i = 0; i < filas.size(); i++) {
            Fila fila = filas.get(i);
            builder.append("| ");
            // Imprimir el nombre de la fila con el mismo ancho que el nombreIndex
            builder.append(String.format("%-" + maxAnchosPorColumna[0] + "s", fila.obtenerNombre()) + " | ");
            for (int col = 0; col < columnas.size(); col++) {
                String cellValue = fila.obtenerValor(col) != null ? fila.obtenerValor(col).toString() : "null";
                builder.append(String.format("%-" + maxAnchosPorColumna[col + 1] + "s", cellValue)).append(" | ");
            }
            builder.append("\n");
        }
    
        return builder.toString();
    }
        
    public Tabla copiaProfunda(){ 
        Tabla copia = new Tabla();
        for (int i=0; i < this.obtenerColumnas().size(); i++){
            if (this.obtenerColumnas().get(i) instanceof ColumnaBoolean){
                copia.AutoCasteoColumna(this.obtenerColumnas().get(i).copiaProfunda());
            }
            else if (this.obtenerColumnas().get(i) instanceof ColumnaNumber){
                copia.AutoCasteoColumna(this.obtenerColumnas().get(i).copiaProfunda());
            }
            else if (this.obtenerColumnas().get(i) instanceof ColumnaString){
                copia.AutoCasteoColumna(this.obtenerColumnas().get(i).copiaProfunda());
            }
            else if (this.obtenerColumnas().get(i) instanceof ColumnaNA){
                copia.AutoCasteoColumna(this.obtenerColumnas().get(i).copiaProfunda());
            }
        }
        
        for (Fila fila : this.filas){
            ArrayCelda copiaProf = fila.copiaProfunda();
            Fila filaTemp = new Fila (copiaProf);
            copia.agregarFila(filaTemp);

        }
        return copia;
    }

    public void procesarColumna(ArrayCelda arrayCelda) {
        this.agregarColumna(Casteo.procesarColumna(arrayCelda));
    }

    public void muestreo(int porcentaje){
        int porcentajeDeMuestreo = (int) Math.ceil(porcentaje * 0.01 * this.filas.size());
        Random random = new Random();

        List<Fila> listaFilas = new ArrayList<>();
        for(int indice = 0; indice < this.filas.size(); indice++){
            ArrayCelda array = this.filas.get(indice).copiaProfunda();
            Fila nuevaFila = new Fila(array);
            listaFilas.add(nuevaFila);
        }
        
        List<Fila> FilasAMostrar = new ArrayList<>();
        for(int i = 0; i < porcentajeDeMuestreo; i++){
            int indiceAleatorio = random.nextInt(listaFilas.size());
            Fila filaAMostrar = listaFilas.remove(indiceAleatorio);
            FilasAMostrar.add(filaAMostrar);
        }
        
        System.out.println("Muestreo de filas:");
        System.out.print(formatearFilasParaImprimir(FilasAMostrar, this.encabezados)); 
    }

    public void muestreo(double porcentaje){
        int porcentajeDeMuestreo = (int) Math.ceil(porcentaje * this.filas.size());
        Random random = new Random();

        List<Fila> listaFilas = new ArrayList<>();
        for(int indice = 0; indice < this.filas.size(); indice++){
            ArrayCelda array = this.filas.get(indice).copiaProfunda();
            Fila nuevaFila = new Fila(array);
            listaFilas.add(nuevaFila); 
        }
        
        List<Fila> FilasAMostrar = new ArrayList<>();
        for(int i = 0; i < porcentajeDeMuestreo; i++){
            int indiceAleatorio = random.nextInt(listaFilas.size());
            Fila filaAMostrar = listaFilas.remove(indiceAleatorio);
            FilasAMostrar.add(filaAMostrar);
        }
        
        System.out.println("Muestreo de filas:");
        System.out.print(formatearFilasParaImprimir(FilasAMostrar, this.encabezados)); 

    }

    public <T> Tabla filtrar(String nombreColumna, Predicate<Object> criterio) {
        Tabla filtrada = this.copiaProfunda();
        
        Columna columna = this.obtenerColumnas().get(this.obtenerIndiceDeColumna(nombreColumna));
        List<Integer> indicesFiltrados = Filtro.filtrar(columna, criterio);
        List<Columna> columnasFiltradas = new ArrayList<Columna>(); 
        filtrada.columnas = columnasFiltradas;
        for (Columna col : this.copiaProfunda().obtenerColumnas()){
            Columna columnaFiltrada = new Columna<>(col.obtenerNombre());
            for (Integer indice : indicesFiltrados){
                columnaFiltrada.agregarCelda(col.obtenerCeldas().get(indice).copiaProfunda());
            }
            filtrada.AutoCasteoColumna(columnaFiltrada);
        }
        filtrada.actualizarFilas();        
        return filtrada;
    }

    public <T> void filtrar(String nombreColumna, Predicate<Object> criterio, boolean inplace){
        Columna columna = this.obtenerColumnas().get(this.obtenerIndiceDeColumna(nombreColumna));
        List<Integer> indicesFiltrados = Filtro.filtrar(columna, criterio);
        
        this.columnas.clear();
        List<Columna> columnasFiltradas = new ArrayList<>();
        for (Columna col : this.obtenerColumnas()){
            Columna columnaFiltrada = new Columna<>(col.obtenerNombre());
            for (Integer indice : indicesFiltrados){
                columnaFiltrada.agregarCelda(col.obtenerCeldas().get(indice));
            }
            this.AutoCasteoColumna(col);
        }
        this.columnas = columnasFiltradas;

        List<Fila> filasFiltradas = new ArrayList<Fila>();
        for (Integer indice : indicesFiltrados){
            filasFiltradas.add(this.obtenerFilas().get(indice));
        }
        this.filas = filasFiltradas;
    }

    @Override
    public boolean equals(Object obj) {
        actualizarFilas();
        if (this == obj) {
            return true;
        }
    
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
    
        Tabla otraTabla = (Tabla) obj;
        otraTabla.actualizarFilas();
        
        return Objects.equals(this.filas, otraTabla.filas) &&
               Objects.equals(this.columnas, otraTabla.columnas) &&
               Objects.equals(this.nombreIndex, otraTabla.nombreIndex);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(filas, columnas, encabezados, nombreIndex);
    }
    
    public static Predicate<Object> condicion(String operador, Object limite) {
        return valor -> {
            // Manejar valores nulos de manera específica
            if (valor == null || limite == null) {
                if (operador.equals("==")) {
                    return valor == null && limite == null; // Ambos deben ser nulos para ser "iguales"
                } else if (operador.equals("!=")) {
                    return !(valor == null && limite == null); // Verdadero si uno es nulo y el otro no
                } else {
                    return false; // Otros operadores no aplican a valores nulos
                }
            }
    
            // Evaluar la condición según el operador si ambos valores no son nulos
            switch (operador) {
                case "<":
                    return compararValores(valor, limite) < 0;
                case ">":
                    return compararValores(valor, limite) > 0;
                case "==":
                    return compararValores(valor, limite) == 0;
                case "!=":
                    return compararValores(valor, limite) != 0;
                case "<=":
                    return compararValores(valor, limite) <= 0;
                case ">=":
                    return compararValores(valor, limite) >= 0;
                default:
                    throw new ExcepcionOperadorInvalido("Operador no válido: " + operador);
            }
        };
    }

    private List<Fila> obtenerFilas() {
        return this.filas;
    }

    private List<Columna> obtenerColumnas() {
        return this.columnas;
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

    private int getMaxLabelWidth() {
        int maxWidth = nombreIndex.length();  // Usar la variable nombreIndex para el ancho
        // Buscar el nombre más largo entre las filas
        for (Fila fila : filas) {
            maxWidth = Math.max(maxWidth, fila.obtenerNombre().length()); // Usar el nombre real de la fila
        }
        return maxWidth;
    }

    private String formatearFilasParaImprimir(List<Fila> filasParaMostrar, List<String> nombresColumnas) {
        // Validar si la lista de filas está vacía
        if (filasParaMostrar.isEmpty()) {
            return ""; // Hacer excepción o devolver mensaje si es necesario
        }
    
        // Si no se especifican nombres de columnas, se usan todas las columnas disponibles
        if (nombresColumnas == null || nombresColumnas.isEmpty()) {
            nombresColumnas = new ArrayList<>();
            for (Columna columna : this.columnas) {
                nombresColumnas.add(columna.obtenerNombre());
            }
        }
    
        // Obtener el número de columnas y preparar un arreglo para los anchos
        int numColumnas = nombresColumnas.size();
        int[] maxAnchoPorColumna = new int[numColumnas];
    
        // Calcular el ancho máximo de cada columna, considerando los datos y los encabezados
        for (Fila fila : filasParaMostrar) {
            for (int j = 0; j < numColumnas; j++) {
                String valor = fila.obtenerValor(j) != null ? fila.obtenerValor(j).toString() : "NA";
                maxAnchoPorColumna[j] = Math.max(maxAnchoPorColumna[j], valor.length());
            }
        }
    
        // Crear el StringBuilder para construir la tabla formateada
        StringBuilder sb = new StringBuilder();
    
        // Imprimir el encabezado (nombre del índice y los encabezados de las columnas)
        int maxAnchoFila = getMaxLabelWidth(); // Calcular el ancho máximo de la columna "Filas" (nombreIndex)
    
        // Imprimir la columna de nombres de fila (nombreIndex)
        sb.append("| ");
        sb.append(String.format("%-" + maxAnchoFila + "s", nombreIndex)).append(" | ");  // Nombre del índice (filas)
    
        // Imprimir los encabezados de las columnas
        for (int j = 0; j < numColumnas; j++) {
            maxAnchoPorColumna[j] = Math.max(maxAnchoPorColumna[j], columnas.get(j).obtenerNombre().length());
            sb.append(String.format("%-" + maxAnchoPorColumna[j] + "s", nombresColumnas.get(j))).append(" | ");
        }
        sb.append("\n");
    
        // Línea separadora
        sb.append("|");
        sb.append("-".repeat(maxAnchoFila + 2));  // Espacios para la columna "Filas"
        sb.append("|");
        for (int j = 0; j < numColumnas; j++) {
            sb.append("-".repeat(maxAnchoPorColumna[j] + 2)); // Ancho de cada columna + márgenes
            if (j < numColumnas - 1) {
                sb.append("|"); // Separador entre columnas
            }
        }
        sb.append("|\n");
    
        // Imprimir las filas seleccionadas
        for (Fila fila : filasParaMostrar) {
            sb.append("| ");
            sb.append(String.format("%-" + maxAnchoFila + "s", fila.obtenerNombre())).append(" | ");  // Nombre de la fila (fila.getNombre())
    
            // Valores de las celdas
            for (int j = 0; j < numColumnas; j++) {
                String valor = fila.obtenerValor(j) != null ? fila.obtenerValor(j).toString() : "NA";
                sb.append(String.format("%-" + maxAnchoPorColumna[j] + "s", valor)).append(" | ");
            }
            sb.append("\n");
        }
    
        return sb.toString();
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

        if (valorCelda.toLowerCase().equals("true")){
        return new CeldaBoolean(true);
        }
        else if (valorCelda.toLowerCase().equals( "false")){
        return new CeldaBoolean(false);
        }
        
        if (this.esDecimal(valorCelda)){
        return new CeldaNumber(Double.parseDouble(valorCelda), true);
        }
        if (valorCelda.equals("") || valorCelda.equalsIgnoreCase("NA") || 
        valorCelda == null || valorCelda.equalsIgnoreCase("N/A") || valorCelda.equalsIgnoreCase("null")){ // infintas opciones mas
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

    private int obtenerIndiceDeColumna(String nombreColumna) {
        for (int i = 0; i < obtenerEncabezados().size(); i++) {
            if (obtenerEncabezados().get(i).equals(nombreColumna)) {
                return i;
            }  
        }
        return -1;
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

        if (columnaAgregada == false){ 
            ColumnaNA CNA = new ColumnaNA(columna.obtenerNombre(), columna.obtenerCeldas());
            this.columnas.add(CNA);
        }
    }
    
    private static int compararValores(Object valor, Object limite) {
        if (valor instanceof Number && limite instanceof Number) {
            double valorDouble = ((Number) valor).doubleValue();
            double limiteDouble = ((Number) limite).doubleValue();
            return Double.compare(valorDouble, limiteDouble);
        } else if (valor instanceof String && limite instanceof String) {
            return ((String) valor).compareTo((String) limite);
        } else if (valor instanceof Boolean && limite instanceof Boolean) {
            return Boolean.compare((Boolean) valor, (Boolean) limite);
        } else {
            throw new TipoDeDatoInvalidoExcepcion("Tipos incompatibles: " + valor.getClass() + " y " + limite.getClass());
        }
    }


    


    
}