import Celda.Celda;
import Celda.CeldaNA;
import Celda.CeldaBoolean;
import Celda.CeldaNumber;
import Celda.CeldaString;
import Columnas.Columna;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
//Carga de CSV
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;




public class Tabla implements util.ImputarFaltantes {
    private List<Fila> filas = new ArrayList<>();
    private List<Columna> columnas = new ArrayList<>();


    public Tabla cargarDesdeCSV(String path, boolean tieneEncabezado) throws IOException {
        Tabla tabla = new Tabla();
        String linea;
        BufferedReader br = new BufferedReader(new FileReader(path));

        if ((linea = br.readLine()) != null && tieneEncabezado) {
            String[] encabezados = linea.split(",");
            for (String encabezado : encabezados) {
                Columna columna = new Columna(encabezado);
                tabla.columnas.add(columna);
            }
        }

        while ((linea = br.readLine()) != null) {
            String[] valores = linea.split(",");
            Fila fila = new Fila();

            for (int i = 0; i < valores.length; i++) {
                String valor = valores[i];
                Celda<?> celda;

                if (valor.isEmpty() || valor.equalsIgnoreCase("null")) {
                    celda = new CeldaNA();
                } else if (esBoolean(valor)) {
                    celda = new CeldaBoolean(Boolean.parseBoolean(valor)); // Ver bien esto, como toma True/true/TRue, etc
                } else if (esEntero(valor)) {
                    celda = new CeldaNumber(Integer.parseInt(valor)); // Ver bien radix 10
                } else if (esDecimal(valor)) {
                    celda = new CeldaNumber(Double.parseDouble(valor));
                } else {
                    celda = new CeldaString(valor);
                }

                fila.agregarCelda(celda);
                if (tabla.columnas.size() > i) {
                    tabla.columnas.get(i).agregarCelda(celda); //Hay que hacer bien la importacion desde Columna
                }
            }
            tabla.filas.add(fila);
        }
        br.close();
        return tabla;
    }

    private boolean esBoolean(String valor) {
        return valor.equalsIgnoreCase("true") || valor.equalsIgnoreCase("false");
    }

    private boolean esEntero(String valor) {
        try {
            Integer.parseInt(valor);
            return true;
        } catch (NumberFormatException e) {
            return false;
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

    public List<Fila> getFilas() {
        return filas;
    }

    public List<Columna> getColumnas() {
        return columnas;
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

    public Celda obtenerCelda(int fila, String columna) {
        // implementación
    }

    public Tabla filtrar() {
        // Pendiente
    }

    public void ordenar(String columna, Boolean ascendente, Boolean inplace) {
        // Implementación 
    }

    public Tabla concatenar(Tabla tabla1){
        // Faltan más argumentos
        // Implementación
    }

    public void imprimirTabla() {
        System.out.println("A definir y completar");
    }

    public Fila[] muestreo(double porcentaje){
        // Implementación
    }

    public Tabla crearVista() {
        // Implementación
    }

    @Override
    public void imputarNA() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'imputarNA'");
    }

}