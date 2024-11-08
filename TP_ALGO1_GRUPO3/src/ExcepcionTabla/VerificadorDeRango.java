package ExcepcionTabla;

public class VerificadorDeRango {

    // Verifica el límite para los métodos head y tail
    public static void verificarLimite(int n, int maxfilas) throws IndiceFueraDeRangoExcepcion {
        if (n < 0 || n > maxfilas) {
            throw new IndiceFueraDeRangoExcepcion("El valor de n (" + n + ") está fuera del rango permitido");
        }
    }

    // Verifica el rango para el método mostrarRango
    public static void verificarRango(int comienzoRango, int finalRango, int maxfilas) throws IndiceFueraDeRangoExcepcion {
        if (comienzoRango < 0 || finalRango > maxfilas || comienzoRango > finalRango) {
            throw new IndiceFueraDeRangoExcepcion("El rango especificado (" + comienzoRango + " a " + finalRango + ") está fuera del rango permitido (0 a " + maxfilas + ")");
        }
    }
}