package Array;

import Array.ArrayCelda;

public class ArrayCeldaTest {

    public static void main(String[] args) {
        pruebaAgregarYObtenerValores();
        pruebaAsignarYObtenerEtiquetas();
        pruebaCambiarPorNA();
        pruebaImputarValoresNA();
        pruebaCopiaProfunda();
        
        System.out.println("Todas las pruebas se han ejecutado.");
    }

    public static void pruebaAgregarYObtenerValores() {
        ArrayCelda arrayCelda = new ArrayCelda("TestArray");

        arrayCelda.agregarValor(42);
        arrayCelda.agregarValor(true);
        arrayCelda.agregarValor("Texto");

        assert arrayCelda.obtenerValor(0).equals(42) : "Error en agregar/obtener valor entero";
        assert arrayCelda.obtenerValor(1).equals(true) : "Error en agregar/obtener valor booleano";
        assert arrayCelda.obtenerValor(2).equals("Texto") : "Error en agregar/obtener valor de texto";

        System.out.println("pruebaAgregarYObtenerValores pasó exitosamente.");
    }

    public static void pruebaAsignarYObtenerEtiquetas() {
        ArrayCelda arrayCelda = new ArrayCelda("TestArray");

        arrayCelda.agregarValor(100);
        arrayCelda.agregarValor(false);
        arrayCelda.agregarValor("OtroTexto");

        arrayCelda.asignarEtiquetas("Etiqueta1", "Etiqueta2", "Etiqueta3");

        assert arrayCelda.obtenerValor("Etiqueta1").equals(100) : "Error en obtener valor por etiqueta (Etiqueta1)";
        assert arrayCelda.obtenerValor("Etiqueta2").equals(false) : "Error en obtener valor por etiqueta (Etiqueta2)";
        assert arrayCelda.obtenerValor("Etiqueta3").equals("OtroTexto") : "Error en obtener valor por etiqueta (Etiqueta3)";

        System.out.println("pruebaAsignarYObtenerEtiquetas pasó exitosamente.");
    }

    public static void pruebaCambiarPorNA() {
        ArrayCelda arrayCelda = new ArrayCelda("TestArray");

        arrayCelda.agregarValor(500);
        arrayCelda.cambiarPorNA(0);

        assert arrayCelda.obtenerValor(0) == null : "Error al cambiar valor a NA";

        System.out.println("pruebaCambiarPorNA pasó exitosamente.");
    }

    public static void pruebaImputarValoresNA() {
        ArrayCelda arrayCelda = new ArrayCelda("TestArray");

        arrayCelda.agregarValor();
        arrayCelda.agregarValor();
        arrayCelda.imputarNA(300);

        assert arrayCelda.obtenerValor(0).equals(300) : "Error en imputar valor NA a número";
        assert arrayCelda.obtenerValor(1).equals(300) : "Error en imputar valor NA a número";

        arrayCelda.cambiarPorNA(0);
        arrayCelda.imputarNA("Texto");

        assert arrayCelda.obtenerValor(0).equals("Texto") : "Error en imputar valor NA a texto";

        System.out.println("pruebaImputarValoresNA pasó exitosamente.");
    }

    public static void pruebaCopiaProfunda() {
        ArrayCelda arrayCelda = new ArrayCelda("TestArrayOriginal");
        arrayCelda.agregarValor(123);
        arrayCelda.agregarValor(false);

        ArrayCelda copiaArrayCelda = arrayCelda.copiaProfunda();

        assert copiaArrayCelda.obtenerValor(0).equals(123) : "Error en copia profunda (valor 123)";
        assert copiaArrayCelda.obtenerValor(1).equals(false) : "Error en copia profunda (valor false)";
        assert !copiaArrayCelda.equals(arrayCelda) : "Error en copia profunda, las instancias deberían ser diferentes";

        System.out.println("pruebaCopiaProfunda pasó exitosamente.");
    }
}
