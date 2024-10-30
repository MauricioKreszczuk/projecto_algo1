package Array;

public class pruebaFila {
    public static void main(String[] args) {
        pruebaFila();
    }

    public static void pruebaFila() {
        // Crear una fila
        Fila fila = new Fila("Fila de Prueba");

        // Agregar valores a la fila
        fila.agregarValor(10);  // Agregar un valor numérico
        fila.agregarValor("Hola");  // Agregar una cadena
        fila.agregarValor(true);  // Agregar un valor booleano

        // Imprimir los valores en la fila

        System.out.println(fila);

    }
}
