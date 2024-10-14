import Celda.Celda;
import Columna.Columna;

public class Tabla implements ImputarFaltantes {
    private Fila[] filas;
    private Columna[] columnas;

    public void agregarFila(Fila fila){
        // Implementación
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
