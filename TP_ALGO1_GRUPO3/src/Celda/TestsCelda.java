package Celda;

public class TestsCelda { // Cambia "MainClass" al nombre que desees
    public static void main(String[] args) {

        // Pruebas para CeldaNumber
        System.out.println("=== PRUEBAS PARA CeldaNumber ===");
        CeldaNumber entero = new CeldaNumber(1);
        CeldaNumber flotante = new CeldaNumber(Float.valueOf(1));
        CeldaNumber doble = new CeldaNumber(Double.valueOf(1));

        // Comparación de igualdad entre diferentes tipos numéricos
        System.out.println("Comparación entero vs flotante: " + entero.equals(flotante));  // true
        System.out.println("Comparación entero vs doble: " + entero.equals(doble));        // true

        // Prueba de copia profunda
        CeldaNumber copiaEntero = entero.copiaProfunda();
        System.out.println("Copia profunda de entero: " + copiaEntero.obtenerValor()); // 1
        System.out.println("Comparación entre entero original y copia: " + entero.equals(copiaEntero));  // true

        // Pruebas para CeldaBoolean
        System.out.println("\n=== PRUEBAS PARA CeldaBoolean ===");
        CeldaBoolean verdadero = new CeldaBoolean(true);
        CeldaBoolean falso = new CeldaBoolean(false);
        CeldaBoolean copiaVerdadero = verdadero.copiaProfunda();

        // Comparación de igualdad
        System.out.println("Comparación verdadero vs falso: " + verdadero.equals(falso));  // false
        System.out.println("Comparación verdadero vs copia: " + verdadero.equals(copiaVerdadero));  // true

        // Pruebas para CeldaString
        System.out.println("\n=== PRUEBAS PARA CeldaString ===");
        CeldaString texto1 = new CeldaString("Hola");
        CeldaString texto2 = new CeldaString("Hola");
        CeldaString texto3 = new CeldaString("Mundo");

        // Comparación de igualdad
        System.out.println("Comparación texto1 vs texto2: " + texto1.equals(texto2));  // true
        System.out.println("Comparación texto1 vs texto3: " + texto1.equals(texto3));  // false

        // Prueba de copia profunda
        CeldaString copiaTexto1 = texto1.copiaProfunda();
        System.out.println("Copia profunda de texto1: " + copiaTexto1.obtenerValor()); // "Hola"
        System.out.println("Comparación texto1 vs copia: " + texto1.equals(copiaTexto1));  // true

        // Pruebas para CeldaNA
        System.out.println("\n=== PRUEBAS PARA CeldaNA ===");
        CeldaNA na1 = new CeldaNA();
        CeldaNA na2 = new CeldaNA();

        // Comparación de igualdad
        System.out.println("Comparación na1 vs na2: " + na1.equals(na2));  // true

        // Prueba de copia profunda
        CeldaNA copiaNA = na1.copiaProfunda();
        System.out.println("Comparación na1 vs copia: " + na1.equals(copiaNA));  // true

        // Pruebas adicionales: inserción de valores (CeldaBoolean)
        System.out.println("\n=== PRUEBAS DE MODIFICACIÓN ===");
        verdadero.establecerValor(false);
        System.out.println("Valor de CeldaBoolean modificado: " + verdadero.obtenerValor());  // false


        CeldaString s1 = new CeldaString("jaja");
        CeldaString s2 = new CeldaString("jadsja");
        CeldaString s3 = s2;

        System.out.println(s1.equals(s3));
    }
}
