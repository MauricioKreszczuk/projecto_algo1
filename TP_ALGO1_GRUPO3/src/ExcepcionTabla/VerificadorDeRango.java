package ExcepcionTabla;

public class VerificadorDeRango {

    // Verifica el límite para los métodos head y tail
    public static void verificarLimite(int n, int max) throws IndiceFueraDeRangoExcepcion {
        if (n < 0 || n > max) {
            throw new IndiceFueraDeRangoExcepcion("El valor de n (" + n + ") está fuera del rango permitido");
        }
    }

    // Verifica el rango para el método mostrarRango
    public static void verificarRango(int start, int end, int max) throws IndiceFueraDeRangoExcepcion {
        if (start < 0 || end > max || start > end) {
            throw new IndiceFueraDeRangoExcepcion("El rango especificado (" + start + " a " + end + ") está fuera del rango permitido");
        }
    }
}