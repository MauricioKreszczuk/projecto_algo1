import Celda.Celda;
import Celda.CeldaNA;
import Celda.CeldaBoolean;
import Array.ArrayCelda;
import Array.Fila;
import Celda.CeldaNumber;
import Celda.CeldaString;
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

    public Tabla(List<Columna> columnas){
        

    }    

    public Tabla(Object[][] matriz){
    }

    private Celda<?> inferirTipoDesdeObject(Object valor){
        
        if (valor == null || valor.equals("na")) return new CeldaNA();
        
        if (valor instanceof Number) {
            Number valorNumber = (Number) valor;
            return new CeldaNumber(valorNumber);
        }

        if (valor instanceof Boolean) {
            Boolean valorBoolean = (Boolean) valor;
            return new CeldaBoolean(valorBoolean);
        }

        else {
            String valorString = (String) valor;
            return new CeldaString(valorString);
        }
    }

    public Tabla cargarDesdeMatriz(Object[][] matriz, Boolean tieneEncabezado) { 
        ArrayList<ArrayCelda> columnasCastear = new ArrayList<>();
        
        if (matriz.length == 0) return null; {// CrearExepcion
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

        return this;
    }

    public Tabla(HashMap Dict){
        //Implementar
    }

    public Tabla(ArrayList ArregloDeListas){
        //Implementar
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

    public Tabla cargarDesdeCSV(String path, boolean tieneEncabezado) throws IOException {
        String linea;
        String delimitador = ",";
        BufferedReader br = new BufferedReader(new FileReader(path));
        ArrayList<ArrayCelda> columnasCastear = new ArrayList<>();
    
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
        br.close();

        for (ArrayCelda columna : columnasCastear) {
            AutoCasteoColumna(columna);
            int indiceCol = this.obtenerColumnas().indexOf(columna);
            if (!(esConsistente(this.obtenerColumnas().get(indiceCol)))){
                reemplazarIncosistencias(this.obtenerColumnas().get(indiceCol));
            }
        }
            
        return this;
    }
    
    public Tabla cargarDesdeCSV(String path, boolean tieneEncabezado, String delimitador) throws IOException {
        String linea;
        BufferedReader br = new BufferedReader(new FileReader(path));
        ArrayList<ArrayCelda> columnasCastear = new ArrayList<>();
    
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
        br.close();

        for (ArrayCelda columna : columnasCastear) {
            AutoCasteoColumna(columna);
            int indiceCol = this.obtenerColumnas().indexOf(columna);
            if (!(esConsistente(this.obtenerColumnas().get(indiceCol)))){
                reemplazarIncosistencias(this.obtenerColumnas().get(indiceCol));
            }
        }
            
        return this;
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
        nuevaTabla.encabezados = this.encabezados;
        nuevaTabla.obtenerFilas().add(fila);

        for (Celda celda : fila.obtenerCeldas()){
            nuevaTabla.obtenerColumnas().get(indiceColumna).agregarCelda(celda);
        }

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
        
        if (valorCelda.length() == "".length() || valorCelda.toLowerCase() == "na"|| 
        valorCelda == null || valorCelda.toLowerCase() == "n/a"){ // infintas opciones mas
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