package ExcepcionTabla;

public class VerificadorDeRango {

    public static void verificarLimite(int n, int maxfilas) throws ExcepcionIndiceFueraDeRango {
        if (n < 0 || n > maxfilas) {
            throw new ExcepcionIndiceFueraDeRango("El valor de n (" + n + ") está fuera del rango permitido");
        }
    }

    public static void verificarRango(int comienzoRango, int finalRango, int maxfilas) throws ExcepcionIndiceFueraDeRango {
        if (comienzoRango < 0 || finalRango > maxfilas || comienzoRango > finalRango) {
            throw new ExcepcionIndiceFueraDeRango("El rango especificado (" + comienzoRango + " a " + finalRango + ") está fuera del rango permitido (0 a " + maxfilas + ")");
        }
    }
}