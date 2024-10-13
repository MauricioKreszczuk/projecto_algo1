// TODOs  
// 1) Hay algunos parámetros que no van a quedar así; atentos a medida que avancemos el tp a cambiar eso antes de implementar nada
// 2) Crear clases Fila y Columna para que no salte el compilador y poder trabajar con esta clase
// 3) Ver cómo se hace para dar la opción de tener dos retornos distintos en un método o cómo trabajar esto

public class Tabla {
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

}
